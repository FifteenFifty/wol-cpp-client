// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   LiveReportForm.java

package com.wol3.client.forms;

import com.jgoodies.forms.builder.ButtonBarBuilder2;
import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.wol3.client.Settings;
import com.wol3.client.comm.HttpConnector;
import com.wol3.client.data.ActorPool;
import com.wol3.client.data.BinaryCombatLog;
import com.wol3.client.data.EntryList;
import com.wol3.client.data.EventPool;
import com.wol3.client.data.TextualCombatLog;
import com.wol3.client.data.TextualCombatLogParser;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import org.apache.commons.collections.primitives.ArrayLongList;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;

// Referenced classes of package com.wol3.client.forms:
//            OpenFileOrCombatLogForm, LogList

public class LiveReportForm extends javax.swing.JFrame
    implements java.util.Observer, java.lang.Runnable, java.awt.datatransfer.ClipboardOwner
{

    public LiveReportForm(java.io.File file)
        throws java.io.IOException
    {
        this(file, false);
    }

    public LiveReportForm(java.io.File file, boolean nosync)
        throws java.io.IOException
    {
        super("World of Logs - Live Report Updater");
        minFileUpdateWait = 15000;
        minSyncWait = 30000;
        dirty = false;
        logger = java.util.logging.Logger.getLogger(getClass().getName());
        this.nosync = nosync;
        this.file = file;
        startFileMtime = file.lastModified();
        lastValidPosition = 0L;
        lastParsedPosition = 0L;
        lastUpdateTime = 0L;
        username = com.wol3.client.Settings.instance.getUsername();
        password = com.wol3.client.Settings.instance.getPassword();
        http = new HttpConnector();
        bcl = new BinaryCombatLog();
        tlog = new TextualCombatLog(file);
        tlog.addObserver(this);
        lastParsedPosition = tlog.getEnd();
        setDefaultCloseOperation(3);
        initComponents();
        logger.setLevel(java.util.logging.Level.FINEST);
        addWindowListener(new java.awt.event.WindowAdapter() {

            public void windowClosed(java.awt.event.WindowEvent e)
            {
                tlog.stop();
                logger.removeHandler(logList.model);
                timer.stop();
            }

            final com.wol3.client.forms.LiveReportForm this$0;

            
            {
                _fld0 = com.wol3.client.forms.LiveReportForm.this;
                super();
            }
        }
);
        start();
        logger.info("Live log report initialized. Waiting for events");
        logger.info((new StringBuilder("Watching file ")).append(file).toString());
        if (lastParsedPosition > 0x40000000L)
        {
            long mbs = lastParsedPosition / 1024L / 1024L;
            logger.warning((new StringBuilder("Log file is ")).append(mbs).append(" MB, don't forget to remove or archive it periodically").toString());
            logger.warning("To archive the file, quit WoW and restart the WoL client, choose Tools -> Split & Zip Logs");
        }
        pack();
        com.wol3.client.forms.OpenFileOrCombatLogForm.centerFrame(this);
    }

    private void createLiveReport()
        throws java.io.IOException
    {
        org.apache.http.HttpResponse rs = http.createLiveReport(username, password);
        if (rs.getStatusLine().getStatusCode() != 200)
        {
            org.apache.http.util.EntityUtils.consumeQuietly(rs.getEntity());
            throw new IOException((new StringBuilder("Error in creating new live report - ")).append(rs.getStatusLine().getStatusCode()).append(": ").append(rs.getStatusLine().getReasonPhrase()).toString());
        } else
        {
            id = org.apache.http.util.EntityUtils.toString(rs.getEntity());
            openBrowserButton.setEnabled(true);
            logger.info((new StringBuilder("Registered log with the server - id is ")).append(id).toString());
            return;
        }
    }

    private void initComponents()
    {
        java.lang.String cols = "p,3dlu,f:p:g";
        java.lang.String rows = "p,3dlu,p,3dlu,p,3dlu,f:p:g,3dlu,p";
        com.jgoodies.forms.builder.PanelBuilder pb = new PanelBuilder(new FormLayout(cols, rows));
        com.jgoodies.forms.layout.CellConstraints cc = new CellConstraints();
        com.jgoodies.forms.layout.CellConstraints cc2 = new CellConstraints();
        startTimeLabel = new JTextField();
        lastFileUpdateLabel = new JTextField();
        lastSyncLabel = new JTextField();
        startTimeLabel.setEditable(false);
        lastFileUpdateLabel.setEditable(false);
        lastSyncLabel.setEditable(false);
        startTimeLabel.setBackground(java.awt.Color.white);
        lastFileUpdateLabel.setBackground(java.awt.Color.white);
        lastSyncLabel.setBackground(java.awt.Color.white);
        pb.addLabel("Start Time", cc.xy(1, 1), startTimeLabel, cc2.xy(3, 1));
        pb.addLabel("Last change", cc.xy(1, 3), lastFileUpdateLabel, cc2.xy(3, 3));
        pb.addLabel("Last sync", cc.xy(1, 5), lastSyncLabel, cc2.xy(3, 5));
        logList = new LogList();
        logger.addHandler(logList.model);
        javax.swing.JScrollPane sp = new JScrollPane(logList);
        pb.add(sp, cc.xyw(1, 7, 3));
        copyMessagesButton = new JButton("Copy messages");
        syncButton = new JButton("Sync now");
        openBrowserButton = new JButton("Open report");
        com.jgoodies.forms.builder.ButtonBarBuilder2 bbb2 = new ButtonBarBuilder2();
        bbb2.addGlue();
        bbb2.addButton(copyMessagesButton);
        bbb2.addRelatedGap();
        bbb2.addButton(syncButton);
        bbb2.addRelatedGap();
        bbb2.addButton(openBrowserButton);
        pb.add(bbb2.getPanel(), cc.xyw(1, 9, 3));
        syncButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                syncIfAllowed();
            }

            final com.wol3.client.forms.LiveReportForm this$0;

            
            {
                _fld0 = com.wol3.client.forms.LiveReportForm.this;
                super();
            }
        }
);
        openBrowserButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                try
                {
                    java.awt.Desktop.getDesktop().browse(new URI((new StringBuilder(java.lang.String.valueOf(com.wol3.client.Settings.instance.getServerURL()))).append("reports/").append(id).toString()));
                }
                catch (java.io.IOException e1)
                {
                    e1.printStackTrace();
                }
                catch (java.net.URISyntaxException e1)
                {
                    e1.printStackTrace();
                }
            }

            final com.wol3.client.forms.LiveReportForm this$0;

            
            {
                _fld0 = com.wol3.client.forms.LiveReportForm.this;
                super();
            }
        }
);
        openBrowserButton.setEnabled(false);
        copyMessagesButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                javax.swing.JDialog dialog = new JDialog(_fld0);
                javax.swing.JPanel contentPane = (javax.swing.JPanel)dialog.getContentPane();
                javax.swing.JTextArea text = new JTextArea();
                text.setFont(new Font("SansSerif", 0, 12));
                java.lang.String messages = logList.getMessagesAsString();
                text.setText(messages);
                java.awt.datatransfer.StringSelection s = new StringSelection(messages);
                getToolkit().getSystemClipboard().setContents(s, _fld0);
                contentPane.add(new JScrollPane(text));
                dialog.setSize(800, 500);
                com.wol3.client.forms.OpenFileOrCombatLogForm.centerFrame(dialog);
                dialog.setVisible(true);
            }

            final com.wol3.client.forms.LiveReportForm this$0;

            
            {
                _fld0 = com.wol3.client.forms.LiveReportForm.this;
                super();
            }
        }
);
        pb.setDefaultDialogBorder();
        setContentPane(pb.getPanel());
        setSize(new Dimension(500, 350));
        timer = new Timer(5000, new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                checkForUpdates();
            }

            final com.wol3.client.forms.LiveReportForm this$0;

            
            {
                _fld0 = com.wol3.client.forms.LiveReportForm.this;
                super();
            }
        }
);
        timer.start();
    }

    private void checkForUpdates()
    {
        if (dirty && canSync() && java.lang.System.currentTimeMillis() - lastFileUpdateTime > (long)minFileUpdateWait)
            syncIfAllowed();
        if (lastFileUpdateTime != 0L && lastFileUpdateTime != startFileMtime && java.lang.System.currentTimeMillis() - lastFileUpdateTime > 0x36ee80L)
        {
            logger.info((new StringBuilder("File ")).append(file).append(" has been idle for 1 hour - stopped parser.").toString());
            tlog.stop();
            logger.removeHandler(logList.model);
            timer.stop();
        }
    }

    public void start()
    {
        tlog.start();
    }

    public void stop()
    {
        tlog.stop();
    }

    public void update(java.util.Observable o, java.lang.Object arg)
    {
        if (o instanceof com.wol3.client.data.TextualCombatLog)
        {
            com.wol3.client.data.TextualCombatLog log = (com.wol3.client.data.TextualCombatLog)o;
            lastValidPosition = log.getEnd();
            javax.swing.SwingUtilities.invokeLater(this);
        }
    }

    public void run()
    {
        logger.info("Event: file changed. Processing Live data.");
        long start = lastParsedPosition;
        long end = lastParsedPosition = lastValidPosition;
        long prevFileUpdateTime = lastFileUpdateTime;
        lastFileUpdateTime = java.lang.System.currentTimeMillis();
        if (end == start)
        {
            logger.fine("No new data found in run");
            return;
        }
        com.wol3.client.data.TextualCombatLogParser p = new TextualCombatLogParser();
        try
        {
            p.parse(tlog.getInputStream(start, end), bcl, logger);
            logger.info(java.lang.String.format("Parsed %d bytes of text.%n", new java.lang.Object[] {
                java.lang.Long.valueOf(end - start)
            }));
            dirty = true;
            lastFileUpdateLabel.setText((new Date()).toString());
            lastSyncLabel.setBackground(new Color(255, 255, 196));
            if (bcl.entryList.timestamps.size() > 0)
                startTimeLabel.setText((new Date(bcl.entryList.timestamps.get(0))).toString());
        }
        catch (java.io.IOException e1)
        {
            logger.logp(java.util.logging.Level.SEVERE, getClass().getName(), "run", "Error parsing chunk", e1);
            return;
        }
        if (lastFileUpdateTime - prevFileUpdateTime > (long)minFileUpdateWait)
            syncIfAllowed();
        else
            logger.fine("Battle in progress - delaying sync");
    }

    private void syncIfAllowed()
    {
        if (nosync)
            return;
        if (bcl.entryList.size() == 0)
        {
            logger.info("Still waiting for the first line...");
            return;
        }
        if (id == null)
            try
            {
                createLiveReport();
            }
            catch (java.io.IOException e)
            {
                logger.logp(java.util.logging.Level.SEVERE, getClass().getName(), "syncIfAllowed", "Error registering report", e);
            }
        if (id == null)
            return;
        if (canSync())
            sync();
        else
            logger.fine(java.lang.String.format("Going to wait for a bit longer... Last update was only %.1f seconds ago.%n", new java.lang.Object[] {
                java.lang.Double.valueOf((double)(java.lang.System.currentTimeMillis() - lastUpdateTime) * 0.001D)
            }));
    }

    private boolean canSync()
    {
        long now = java.lang.System.currentTimeMillis();
        return now - lastUpdateTime > (long)minSyncWait;
    }

    private void sync()
    {
        dirty = false;
        lastSyncLabel.setText((new Date()).toString());
        lastSyncLabel.setBackground(java.awt.Color.white);
        org.apache.http.HttpResponse rs;
        int o[];
        long now = java.lang.System.currentTimeMillis();
        lastUpdateTime = now;
        rs = http.getOffsets(username, password, id);
        java.lang.String result = null;
        if (rs.getEntity() != null)
            result = org.apache.http.util.EntityUtils.toString(rs.getEntity());
        if (rs.getStatusLine().getStatusCode() != 200)
            break MISSING_BLOCK_LABEL_394;
        o = com.wol3.client.comm.HttpConnector.parseOffsets(result);
        if (bcl.entryList.size() - o[2] == 0)
        {
            logger.fine("Server already up to date");
            return;
        }
        java.io.ByteArrayOutputStream out = new ByteArrayOutputStream();
        logger.fine(java.lang.String.format("Sync init - going to upload %d actors, %d events, %d entries.", new java.lang.Object[] {
            java.lang.Integer.valueOf(bcl.actorPool.size() - o[0]), java.lang.Integer.valueOf(bcl.eventPool.size() - o[1]), java.lang.Integer.valueOf(bcl.entryList.size() - o[2])
        }));
        bcl.writeCompressedTo(out, o[0], o[1], o[2]);
        logger.fine(java.lang.String.format("Uploading %d bytes.", new java.lang.Object[] {
            java.lang.Integer.valueOf(out.size())
        }));
        rs = http.updateLiveReport(username, password, id, out.toByteArray());
        org.apache.http.util.EntityUtils.consumeQuietly(rs.getEntity());
        if (rs.getStatusLine().getStatusCode() == 200)
            logger.info("Update succesful");
        else
            throw new IOException((new StringBuilder("Error updating live report - ")).append(rs.getStatusLine().getStatusCode()).append(": ").append(rs.getStatusLine().getReasonPhrase()).toString());
        break MISSING_BLOCK_LABEL_489;
        logger.logp(java.util.logging.Level.SEVERE, getClass().getName(), "sync", (new StringBuilder("Error gettings offsets - ")).append(rs.getStatusLine().getStatusCode()).append(": ").append(rs.getStatusLine().getReasonPhrase()).toString());
        break MISSING_BLOCK_LABEL_489;
        java.io.IOException e;
        e;
        logger.logp(java.util.logging.Level.SEVERE, getClass().getName(), "sync", "Error syncing with server", e);
    }

    public void lostOwnership(java.awt.datatransfer.Clipboard clipboard1, java.awt.datatransfer.Transferable transferable)
    {
    }

    private static final long serialVersionUID = 1L;
    public int minFileUpdateWait;
    public int minSyncWait;
    public long lastValidPosition;
    public long lastParsedPosition;
    public long lastUpdateTime;
    public long lastFileUpdateTime;
    public long startFileMtime;
    private boolean dirty;
    private java.lang.String username;
    private java.lang.String password;
    private com.wol3.client.comm.HttpConnector http;
    public com.wol3.client.data.BinaryCombatLog bcl;
    private com.wol3.client.data.TextualCombatLog tlog;
    private java.lang.String id;
    private boolean nosync;
    private java.io.File file;
    private javax.swing.JButton syncButton;
    private javax.swing.JButton openBrowserButton;
    private javax.swing.JButton copyMessagesButton;
    private javax.swing.JTextField startTimeLabel;
    private javax.swing.JTextField lastFileUpdateLabel;
    private javax.swing.JTextField lastSyncLabel;
    private com.wol3.client.forms.LogList logList;
    private javax.swing.Timer timer;
    java.util.logging.Logger logger;






}
