// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   LogFileWindow.java

package com.wol3.client.forms;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.wol3.client.Settings;
import com.wol3.client.comm.ByteBufferProgressBody;
import com.wol3.client.comm.HttpConnector;
import com.wol3.client.data.Actor;
import com.wol3.client.data.ActorPool;
import com.wol3.client.data.BinaryCombatLog;
import com.wol3.client.data.BinaryCombatLogSplitter;
import com.wol3.client.data.EntryList;
import com.wol3.client.data.InvalidLineException;
import com.wol3.client.data.TextualCombatLogParser;
import com.wol3.util.UIHelpers;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Date;
import java.util.Properties;
import java.util.TimeZone;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.html.HTMLEditorKit;
import org.apache.commons.collections.primitives.ArrayLongList;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;

// Referenced classes of package com.wol3.client.forms:
//            ParseProgressDialog, ActivityGraph, FileSplitter

public class LogFileWindow extends javax.swing.JFrame
    implements java.lang.Runnable
{

    public LogFileWindow(java.io.File file, java.awt.Frame parent)
    {
        progress = 0;
        this.file = file;
        final java.awt.Component p = parent;
        progressDialog = new ParseProgressDialog(parent);
        progressDialog.setVisible(true);
        java.lang.Runnable r = new java.lang.Runnable() {

            public void run()
            {
                boolean success = initializeLog(p);
                progressDialog.setVisible(false);
                progressDialog.dispose();
                if (!success)
                {
                    return;
                } else
                {
                    initForm();
                    return;
                }
            }

            final com.wol3.client.forms.LogFileWindow this$0;
            private final java.awt.Component val$p;

            
            {
                _fld0 = com.wol3.client.forms.LogFileWindow.this;
                p = component;
                super();
            }
        }
;
        java.lang.Thread t = new Thread(r);
        t.start();
    }

    private void initForm()
    {
        createComponents();
        createForm();
        updateStats();
        pack();
        if (getWidth() > 1024)
            setSize(1024, getHeight());
        setSize(getWidth(), getHeight() + 16);
        com.wol3.util.UIHelpers.center(this);
        setDefaultCloseOperation(1);
        addWindowListener(new java.awt.event.WindowAdapter() {

            public void windowClosing(java.awt.event.WindowEvent e)
            {
                logFile = null;
                if (progressDialog != null)
                {
                    progressDialog.stopUpdateThread();
                    progressDialog.dispose();
                    progressDialog = null;
                }
                java.lang.System.gc();
            }

            final com.wol3.client.forms.LogFileWindow this$0;

            
            {
                _fld0 = com.wol3.client.forms.LogFileWindow.this;
                super();
            }
        }
);
        setVisible(true);
    }

    private boolean initializeLog(java.awt.Component parent)
    {
        java.io.FileInputStream in;
        int num;
        in = null;
        num = 0;
        org.apache.commons.collections.primitives.ArrayLongList timestamps;
        in = new FileInputStream(file);
        com.wol3.client.data.TextualCombatLogParser p = new TextualCombatLogParser();
        logFile = new BinaryCombatLog();
        int size = (int)file.length();
        java.nio.channels.FileChannel channel = in.getChannel();
        java.io.BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
        java.lang.String line;
        while ((line = reader.readLine()) != null) 
        {
            if (!line.trim().equals(""))
                p.parseLine(logFile, line, num + 1);
            if (++num % 1000 == 0)
            {
                progress = (int)((channel.position() * 1000L) / (long)size);
                javax.swing.SwingUtilities.invokeLater(this);
            }
        }
        logFile.finish();
        timestamps = logFile.entryList.timestamps;
        if (timestamps.size() >= 100)
            break MISSING_BLOCK_LABEL_196;
        javax.swing.JOptionPane.showMessageDialog(parent, "Log file is too short.");
        try
        {
            in.close();
        }
        catch (java.io.IOException e)
        {
            e.printStackTrace();
        }
        return false;
        long duration = timestamps.get(timestamps.size() - 1) - timestamps.get(0);
        if (duration <= 0x240c8400L)
            break MISSING_BLOCK_LABEL_368;
        javax.swing.JOptionPane.showMessageDialog(parent, "Log file cannot be longer than a week, please split it before parsing.");
        try
        {
            in.close();
        }
        catch (java.io.IOException e)
        {
            e.printStackTrace();
        }
        return false;
        java.io.IOException e;
        e;
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(parent, (new StringBuilder("Failed to read file: ")).append(e).toString());
        try
        {
            in.close();
        }
        catch (java.io.IOException e)
        {
            e.printStackTrace();
        }
        return false;
        e;
        e.printStackTrace();
        javax.swing.JOptionPane.showMessageDialog(parent, (new StringBuilder("Failed to read file, error parsing line ")).append(num + 1).append(": ").append(e).toString());
        try
        {
            in.close();
        }
        catch (java.io.IOException e)
        {
            e.printStackTrace();
        }
        return false;
        java.lang.Exception exception;
        exception;
        try
        {
            in.close();
        }
        catch (java.io.IOException e)
        {
            e.printStackTrace();
        }
        throw exception;
        try
        {
            in.close();
        }
        catch (java.io.IOException e)
        {
            e.printStackTrace();
        }
        return true;
    }

    private void createComponents()
    {
        logFileTextField = new JTextField();
        logFileTextField.setEditable(false);
        commentTextField = new JTextField();
        linesTextField = new JTextField();
        linesTextField.setEditable(false);
        linesPerSecondTextField = new JTextField();
        linesPerSecondTextField.setEditable(false);
        mobsTextField = new JTextField();
        mobsTextField.setEditable(false);
        mobsPerHourTextField = new JTextField();
        mobsPerHourTextField.setEditable(false);
        logDurationTextField = new JTextField();
        logDurationTextField.setEditable(false);
        startTimeTextField = new JTextField();
        startTimeTextField.setEditable(false);
        endTimeTextField = new JTextField();
        endTimeTextField.setEditable(false);
        timeZoneTextField = new JTextField();
        timeZoneTextField.setEditable(false);
        selectedStartTimeTextField = new JTextField();
        selectedStartTimeTextField.setEditable(false);
        selectedEndTimeTextField = new JTextField();
        selectedEndTimeTextField.setEditable(false);
        linesSelectedTextField = new JTextField();
        linesSelectedTextField.setEditable(false);
        activityGraphPlaceHolder = new JTextArea();
        activityGraphPlaceHolder.setText("Placeholder for graph");
        uploadButton = new JButton("Upload");
        uploadButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                uploadReport();
            }

            final com.wol3.client.forms.LogFileWindow this$0;

            
            {
                _fld0 = com.wol3.client.forms.LogFileWindow.this;
                super();
            }
        }
);
        graph = new ActivityGraph(logFile, this);
    }

    private void createForm()
    {
        java.lang.String colspec = "right:p, 3dlu, [250px,p]:g, 7dlu, right:p, 3dlu, [250px,p]:g";
        java.lang.String rowspec = "p, 3dlu, p, 3dlu, p, 9dlu, p, 3dlu, p, 3dlu, p, 3dlu, p, 3dlu, p, 9dlu, p, 3dlu, f:p:g, 3dlu, p, 3dlu, p, 3dlu, p, 3dlu, p";
        com.jgoodies.forms.layout.FormLayout layout = new FormLayout(colspec, rowspec);
        layout.setColumnGroups(new int[][] {
            new int[] {
                1, 5
            }, new int[] {
                3, 7
            }
        });
        com.jgoodies.forms.builder.PanelBuilder b = new PanelBuilder(layout, (javax.swing.JPanel)getContentPane());
        b.setDefaultDialogBorder();
        com.jgoodies.forms.layout.CellConstraints cc = new CellConstraints();
        com.jgoodies.forms.layout.CellConstraints cc2 = new CellConstraints();
        b.addSeparator("File", cc.xyw(1, 1, 7));
        b.addLabel("Log file", cc.xy(1, 3), logFileTextField, cc2.xyw(3, 3, 5));
        b.addLabel("Comment", cc.xy(1, 5), commentTextField, cc2.xyw(3, 5, 5));
        b.addSeparator("Stats", cc.xyw(1, 7, 7));
        b.addLabel("Start time", cc.xy(1, 9), startTimeTextField, cc2.xy(3, 9));
        b.addLabel("End time", cc.xy(5, 9), endTimeTextField, cc2.xy(7, 9));
        b.addLabel("Duration", cc.xy(1, 11), logDurationTextField, cc2.xy(3, 11));
        b.addLabel("Timezone", cc.xy(5, 11), timeZoneTextField, cc2.xy(7, 11));
        b.addLabel("Lines", cc.xy(1, 13), linesTextField, cc2.xy(3, 13));
        b.addLabel("Lines/second", cc.xy(5, 13), linesPerSecondTextField, cc2.xy(7, 13));
        b.addLabel("Creatures seen", cc.xy(1, 15), mobsTextField, cc2.xy(3, 15));
        b.addLabel("Creatures/hour", cc.xy(5, 15), mobsPerHourTextField, cc2.xy(7, 15));
        b.addSeparator("Activity && Range Selection", cc.xyw(1, 17, 7));
        b.add(new JScrollPane(graph), cc.xyw(1, 19, 7));
        b.addLabel("Range start", cc.xy(1, 21), selectedStartTimeTextField, cc2.xyw(3, 21, 5));
        b.addLabel("Range end", cc.xy(1, 23), selectedEndTimeTextField, cc2.xyw(3, 23, 5));
        b.addLabel("Lines", cc.xy(1, 25), linesSelectedTextField, cc2.xyw(3, 25, 5));
        b.add(uploadButton, cc.rcw(27, 7, 1, "default, right"));
    }

    private void updateStats()
    {
        logFileTextField.setText(file.getAbsolutePath());
        org.apache.commons.collections.primitives.ArrayLongList timestamps = logFile.entryList.timestamps;
        int durationInMs = (int)(timestamps.get(timestamps.size() - 1) - timestamps.get(0));
        int lines = logFile.entryList.size();
        double linesPerSecond = ((double)lines * 1000D) / (double)durationInMs;
        linesTextField.setText((new StringBuilder()).append(lines).toString());
        linesPerSecondTextField.setText(java.lang.String.format("%.2f", new java.lang.Object[] {
            java.lang.Double.valueOf(linesPerSecond)
        }));
        int creatures = 0;
        com.wol3.client.data.Actor aactor[];
        long startTime = (aactor = logFile.actorPool.toArray()).length;
        for (int i = 0; i < startTime; i++)
        {
            com.wol3.client.data.Actor a = aactor[i];
            if (a.getType() != 0 && a.getType() != 4)
                creatures++;
        }

        double creaturesPerhour = ((double)creatures * 1000D * 3600D) / (double)durationInMs;
        mobsTextField.setText((new StringBuilder()).append(creatures).toString());
        mobsPerHourTextField.setText(java.lang.String.format("%.2f", new java.lang.Object[] {
            java.lang.Double.valueOf(creaturesPerhour)
        }));
        logDurationTextField.setText(java.lang.String.format("%.3f seconds", new java.lang.Object[] {
            java.lang.Double.valueOf((double)durationInMs * 0.001D)
        }));
        startTime = timestamps.get(0);
        long endTime = timestamps.get(timestamps.size() - 1);
        startTimeTextField.setText((new Date(startTime)).toString());
        endTimeTextField.setText((new Date(endTime)).toString());
        java.util.TimeZone tz = java.util.TimeZone.getTimeZone(logFile.properties.getProperty("timezone"));
        timeZoneTextField.setText((new StringBuilder(java.lang.String.valueOf(tz.getDisplayName()))).append(", id: ").append(tz.getID()).toString());
    }

    private void uploadReport()
    {
        if (graph.validateTimeRange())
        {
            com.wol3.client.data.BinaryCombatLog bcl = com.wol3.client.data.BinaryCombatLogSplitter.split(logFile, graph.firstLine, graph.lastLine);
            com.wol3.client.comm.HttpConnector c = new HttpConnector();
            java.io.ByteArrayOutputStream out = new ByteArrayOutputStream();
            try
            {
                bcl.writeCompressedTo(out, 0, 0, 0);
            }
            catch (java.io.IOException e)
            {
                throw new RuntimeException(e);
            }
            java.nio.ByteBuffer bb = java.nio.ByteBuffer.wrap(out.toByteArray());
            com.wol3.client.comm.ByteBufferProgressBody filePart = c.createReportUploadData(bb);
            progressDialog = new com.wol3.client.forms.ParseProgressDialog(filePart) {

                public int getProgress()
                {
                    return (int)((filePart.sent * 1000L) / filePart.total);
                }

                final com.wol3.client.forms.LogFileWindow this$0;
                private final com.wol3.client.comm.ByteBufferProgressBody val$filePart;

            
            {
                _fld0 = com.wol3.client.forms.LogFileWindow.this;
                filePart = bytebufferprogressbody;
                super($anonymous0);
            }
            }
;
            progressDialog.setDefaultCloseOperation(2);
            progressDialog.setVisible(true);
            progressDialog.startUpdateThread();
            uploadButton.setEnabled(false);
            doUpload(c, bb, filePart);
        }
    }

    private void doUpload(final com.wol3.client.comm.HttpConnector c, final java.nio.ByteBuffer bb, final com.wol3.client.comm.ByteBufferProgressBody filePart)
    {
        java.lang.System.out.println((new StringBuilder()).append(new Date()).append(" doUpload()").toString());
        java.lang.Runnable r = new java.lang.Runnable() {

            public void run()
            {
                try
                {
                    java.lang.System.out.println((new StringBuilder()).append(new Date()).append(" started upload").toString());
                    org.apache.http.HttpResponse rs = c.createReport(com.wol3.client.Settings.instance.getUsername(), com.wol3.client.Settings.instance.getPassword(), bb, commentTextField.getText(), filePart);
                    java.lang.String result = org.apache.http.util.EntityUtils.toString(rs.getEntity());
                    java.lang.System.out.println((new StringBuilder()).append(new Date()).append(" finished upload").toString());
                    switch (rs.getStatusLine().getStatusCode())
                    {
                    case 200: 
                        if (java.awt.Desktop.isDesktopSupported() && java.awt.Desktop.getDesktop().isSupported(java.awt.Desktop.Action.BROWSE))
                            java.awt.Desktop.getDesktop().browse(new URI((new StringBuilder(java.lang.String.valueOf(com.wol3.client.Settings.instance.getServerURL()))).append("reports/").append(result).append("/").toString()));
                        else
                            javax.swing.JOptionPane.showMessageDialog(_fld0, new JTextField((new StringBuilder("Desktop API not supported.\nPlease browse to ")).append(com.wol3.client.Settings.instance.getServerURL()).append("reports/").append(result).append("/ to index your report").toString()));
                        javax.swing.JOptionPane.showMessageDialog(_fld0, "Report sucessfully uploaded!");
                        break;

                    default:
                        javax.swing.JEditorPane htmlPane = new JEditorPane();
                        javax.swing.text.html.HTMLEditorKit editorKit = new HTMLEditorKit();
                        htmlPane.setEditorKit(editorKit);
                        htmlPane.getDocument().putProperty("IgnoreCharsetDirective", new Boolean(true));
                        javax.swing.text.Document doc = editorKit.createDefaultDocument();
                        doc.putProperty("IgnoreCharsetDirective", java.lang.Boolean.valueOf(true));
                        try
                        {
                            editorKit.read(new StringReader(result), doc, 0);
                        }
                        catch (javax.swing.text.BadLocationException e)
                        {
                            e.printStackTrace();
                        }
                        htmlPane.setDocument(doc);
                        javax.swing.JScrollPane scrollPane = new JScrollPane(htmlPane);
                        scrollPane.setPreferredSize(new Dimension(900, 400));
                        javax.swing.JOptionPane.showMessageDialog(_fld0, scrollPane, "Failed to upload the report: ", 2);
                        break;
                    }
                }
                catch (java.io.IOException e)
                {
                    javax.swing.JOptionPane.showMessageDialog(_fld0, e, "Failed to upload the report", 2);
                    e.printStackTrace();
                }
                catch (java.net.URISyntaxException e)
                {
                    e.printStackTrace();
                }
                try
                {
                    javax.swing.SwingUtilities.invokeAndWait(new java.lang.Runnable() {

                        public void run()
                        {
                            uploadButton.setEnabled(true);
                            if (progressDialog != null)
                            {
                                progressDialog.stopUpdateThread();
                                progressDialog.setVisible(false);
                                progressDialog.dispose();
                            }
                        }

                        final com.wol3.client.forms._cls5 this$1;

                    
                    {
                        _fld1 = com.wol3.client.forms._cls5.this;
                        super();
                    }
                    }
);
                }
                catch (java.lang.InterruptedException e1)
                {
                    e1.printStackTrace();
                }
                catch (java.lang.reflect.InvocationTargetException e1)
                {
                    e1.printStackTrace();
                }
                int rs = javax.swing.JOptionPane.showConfirmDialog(_fld0, "Do you want to split and archive (zip) the uploaded file?", "WoL Client: Auto archive", 0);
                if (rs == 0)
                    try
                    {
                        (new FileSplitter()).splitWithProgress(_fld0, file);
                    }
                    catch (java.lang.Exception e)
                    {
                        e.printStackTrace();
                    }
            }

            final com.wol3.client.forms.LogFileWindow this$0;
            private final com.wol3.client.comm.HttpConnector val$c;
            private final java.nio.ByteBuffer val$bb;
            private final com.wol3.client.comm.ByteBufferProgressBody val$filePart;


            
            {
                _fld0 = com.wol3.client.forms.LogFileWindow.this;
                c = httpconnector;
                bb = bytebuffer;
                filePart = bytebufferprogressbody;
                super();
            }
        }
;
        java.lang.Thread t = new Thread(r);
        t.start();
        java.lang.System.out.println((new StringBuilder()).append(new Date()).append(" started thread").toString());
    }

    public void run()
    {
        progressDialog.updateProgress(progress);
    }

    private static final long serialVersionUID = 1L;
    com.wol3.client.data.BinaryCombatLog logFile;
    java.io.File file;
    com.wol3.client.forms.ParseProgressDialog progressDialog;
    int progress;
    javax.swing.JTextField logFileTextField;
    javax.swing.JTextField commentTextField;
    javax.swing.JTextField linesTextField;
    javax.swing.JTextField linesPerSecondTextField;
    javax.swing.JTextField mobsTextField;
    javax.swing.JTextField mobsPerHourTextField;
    javax.swing.JTextField logDurationTextField;
    javax.swing.JTextField startTimeTextField;
    javax.swing.JTextField endTimeTextField;
    javax.swing.JTextField timeZoneTextField;
    javax.swing.JTextField selectedStartTimeTextField;
    javax.swing.JTextField selectedEndTimeTextField;
    javax.swing.JTextField linesSelectedTextField;
    javax.swing.JButton uploadButton;
    javax.swing.JTextArea activityGraphPlaceHolder;
    com.wol3.client.forms.ActivityGraph graph;



}
