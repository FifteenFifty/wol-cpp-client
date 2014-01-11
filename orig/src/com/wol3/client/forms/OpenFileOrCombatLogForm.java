// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   OpenFileOrCombatLogForm.java

package com.wol3.client.forms;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.wol3.client.Settings;
import com.wol3.util.UIHelpers;
import java.awt.Color;
import java.awt.Component;
import java.awt.Desktop;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Rectangle;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URI;
import java.net.URISyntaxException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.TransferHandler;
import javax.swing.border.CompoundBorder;
import javax.swing.filechooser.FileFilter;

// Referenced classes of package com.wol3.client.forms:
//            ConfigurationPanel, LogFileWindow, LiveReportForm, FileSplitter

public class OpenFileOrCombatLogForm extends javax.swing.JFrame
{

    public OpenFileOrCombatLogForm()
        throws java.awt.HeadlessException
    {
        super("World of Logs - Data Uploader");
        transferHandler = new javax.swing.TransferHandler() {

            public boolean canImport(javax.swing.TransferHandler.TransferSupport support)
            {
                return support.isDataFlavorSupported(java.awt.datatransfer.DataFlavor.javaFileListFlavor);
            }

            public boolean importData(javax.swing.TransferHandler.TransferSupport support)
            {
                java.awt.datatransfer.Transferable transferable;
                if (!canImport(support))
                    return false;
                transferable = support.getTransferable();
                java.util.List files = (java.util.List)transferable.getTransferData(java.awt.datatransfer.DataFlavor.javaFileListFlavor);
                if (files.isEmpty())
                    return false;
                try
                {
                    new LogFileWindow((java.io.File)files.get(0), _fld0);
                }
                catch (java.awt.datatransfer.UnsupportedFlavorException e)
                {
                    return false;
                }
                catch (java.io.IOException e)
                {
                    return false;
                }
                return true;
            }

            private static final long serialVersionUID = 1L;
            final com.wol3.client.forms.OpenFileOrCombatLogForm this$0;

            
            {
                _fld0 = com.wol3.client.forms.OpenFileOrCombatLogForm.this;
                super();
            }
        }
;
        setTransferHandler(transferHandler);
        createForm();
        setDefaultCloseOperation(3);
        setResizable(false);
        pack();
        center();
        instance = this;
    }

    private void createForm()
    {
        javax.swing.JPanel contentPane = (javax.swing.JPanel)getContentPane();
        contentPane.setOpaque(true);
        contentPane.setBackground(java.awt.Color.white);
        java.lang.String rowSpec = "4dlu, fill:[200px, pref], 4dlu:g, pref";
        java.lang.String colSpec = "4dlu, [300px, pref], 4dlu, [300px, pref], 4dlu, [300px, pref], 4dlu";
        com.jgoodies.forms.builder.PanelBuilder b = new PanelBuilder(new FormLayout(colSpec, rowSpec), (javax.swing.JPanel)getContentPane());
        javax.swing.ImageIcon wolIcon = new ImageIcon(getClass().getResource("/com/wol3/client/icons/filetype-wol.png"), "");
        javax.swing.ImageIcon wolSyncIcon = new ImageIcon(getClass().getResource("/com/wol3/client/icons/filetype-wol-sync.png"), "");
        openFileButton = new JButton("Open a file", wolIcon);
        openCurrentLogButton = new JButton("Open the WoW Log", wolIcon);
        openRTSessionButton = new JButton("Start a Live Report Session", wolSyncIcon);
        openFileButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                doOpenFile();
            }

            final com.wol3.client.forms.OpenFileOrCombatLogForm this$0;

            
            {
                _fld0 = com.wol3.client.forms.OpenFileOrCombatLogForm.this;
                super();
            }
        }
);
        openCurrentLogButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                doOpenWowLogs();
            }

            final com.wol3.client.forms.OpenFileOrCombatLogForm this$0;

            
            {
                _fld0 = com.wol3.client.forms.OpenFileOrCombatLogForm.this;
                super();
            }
        }
);
        openRTSessionButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                doOpenLiveReport();
            }

            final com.wol3.client.forms.OpenFileOrCombatLogForm this$0;

            
            {
                _fld0 = com.wol3.client.forms.OpenFileOrCombatLogForm.this;
                super();
            }
        }
);
        statusBar = new JLabel("Not authenticated");
        statusBar.setBorder(new CompoundBorder(javax.swing.BorderFactory.createLoweredBevelBorder(), javax.swing.BorderFactory.createEmptyBorder(1, 5, 1, 5)));
        com.jgoodies.forms.layout.CellConstraints cc = new CellConstraints();
        b.add(openFileButton, cc.xy(2, 2));
        b.add(openCurrentLogButton, cc.xy(4, 2));
        b.add(openRTSessionButton, cc.xy(6, 2));
        b.add(statusBar, cc.xyw(1, 4, 7));
        javax.swing.JMenu edit = new JMenu("Edit");
        javax.swing.JMenuItem prefs = new JMenuItem("Preferences");
        prefs.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                showPreferences();
            }

            final com.wol3.client.forms.OpenFileOrCombatLogForm this$0;

            
            {
                _fld0 = com.wol3.client.forms.OpenFileOrCombatLogForm.this;
                super();
            }
        }
);
        edit.add(prefs);
        javax.swing.JMenu tools = new JMenu("Tools");
        javax.swing.JMenuItem splitter = new JMenuItem("Split & zip logs");
        splitter.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                try
                {
                    (new FileSplitter()).run(_fld0);
                }
                catch (java.lang.Exception e2)
                {
                    e2.printStackTrace();
                }
            }

            final com.wol3.client.forms.OpenFileOrCombatLogForm this$0;

            
            {
                _fld0 = com.wol3.client.forms.OpenFileOrCombatLogForm.this;
                super();
            }
        }
);
        tools.add(splitter);
        javax.swing.JMenu help = new JMenu("Help");
        javax.swing.JMenuItem helpItem = new JMenuItem("Help");
        helpItem.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                try
                {
                    java.awt.Desktop.getDesktop().browse(new URI("http://worldoflogs.com/help/client/"));
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

            final com.wol3.client.forms.OpenFileOrCombatLogForm this$0;

            
            {
                _fld0 = com.wol3.client.forms.OpenFileOrCombatLogForm.this;
                super();
            }
        }
);
        help.add(helpItem);
        javax.swing.JMenuItem about = new JMenuItem("About");
        about.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                javax.swing.JOptionPane.showMessageDialog(_fld0, "World of Logs client v5421 - (c) 2009 world of logs.com. All rights reserved.\n\nContains parts of Nuvola Icon Pack, licensed under the LGPL.");
            }

            final com.wol3.client.forms.OpenFileOrCombatLogForm this$0;

            
            {
                _fld0 = com.wol3.client.forms.OpenFileOrCombatLogForm.this;
                super();
            }
        }
);
        help.add(about);
        javax.swing.JMenuBar menu = new JMenuBar();
        menu.add(edit);
        menu.add(tools);
        menu.add(javax.swing.Box.createHorizontalGlue());
        menu.add(help);
        setJMenuBar(menu);
    }

    protected void showPreferences()
    {
        javax.swing.JDialog frame = new JDialog(this, true);
        frame.setContentPane(new ConfigurationPanel());
        frame.pack();
        com.wol3.util.UIHelpers.center(frame);
        frame.setDefaultCloseOperation(2);
        frame.setVisible(true);
    }

    public static void main(java.lang.String args[])
    {
        com.wol3.client.Settings.getSettings();
        if (com.wol3.client.Settings.instance.getWoWLogsDir() == null)
        {
            java.io.File foundDir = com.wol3.client.Settings.findWoWLogsDir();
            if (foundDir != null)
            {
                int rs = javax.swing.JOptionPane.showConfirmDialog(null, (new StringBuilder("The World of Warcraft logs directory preference is not set yet, do you want to set it to ")).append(foundDir.getAbsolutePath()).append("?").toString(), "WoW Logs Directory", 0);
                if (rs == 0)
                    com.wol3.client.Settings.instance.setWoWLogsDir(foundDir);
            }
        }
        com.wol3.client.forms.OpenFileOrCombatLogForm form = new OpenFileOrCombatLogForm();
        form.setVisible(true);
        if (!com.wol3.client.Settings.instance.isLoginValid())
            form.showPreferences();
    }

    public void center()
    {
        java.awt.GraphicsEnvironment ge = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
        java.awt.Rectangle maxBounds = ge.getMaximumWindowBounds();
        java.awt.Rectangle current = getBounds();
        current.x = (maxBounds.width - current.width) / 2;
        current.y = (maxBounds.height - current.height) / 2;
        setBounds(current);
    }

    public static void centerFrame(java.awt.Component c)
    {
        java.awt.GraphicsEnvironment ge = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
        java.awt.Rectangle maxBounds = ge.getMaximumWindowBounds();
        java.awt.Rectangle current = c.getBounds();
        current.x = (maxBounds.width - current.width) / 2;
        current.y = (maxBounds.height - current.height) / 2;
        c.setBounds(current);
    }

    public void doOpenFile()
    {
        java.io.File logsDir = com.wol3.client.Settings.instance.getWoWLogsDir();
        javax.swing.JFileChooser jfc;
        if (logsDir != null)
            jfc = new JFileChooser(logsDir);
        else
            jfc = new JFileChooser();
        jfc.setFileFilter(com.wol3.client.forms.OpenFileOrCombatLogForm.getFileFilter());
        int rs = jfc.showOpenDialog(this);
        if (rs == 0 && jfc.getSelectedFile() != null)
            new LogFileWindow(jfc.getSelectedFile(), this);
    }

    public static javax.swing.filechooser.FileFilter getFileFilter()
    {
        return new javax.swing.filechooser.FileFilter() {

            public boolean accept(java.io.File pathname)
            {
                if (pathname.isDirectory())
                    return true;
                java.lang.String name = pathname.getAbsolutePath();
                return name.endsWith(".log") || name.endsWith(".txt");
            }

            public java.lang.String getDescription()
            {
                return "World of Warcraft log files";
            }

        }
;
    }

    public void doOpenWowLogs()
    {
        java.io.File logsDir = com.wol3.client.Settings.instance.getWoWLogsDir();
        if (logsDir == null)
        {
            logsDir = doSearchForLogsDir();
            if (logsDir == null)
            {
                javax.swing.JFileChooser jfc = new JFileChooser();
                jfc.setFileSelectionMode(1);
                javax.swing.JOptionPane.showMessageDialog(this, "Please select the World of Warcraft Logs directory on the next screen");
                int rs = jfc.showDialog(this, "Use this directory");
                java.io.File f = jfc.getSelectedFile();
                if (rs == 0 && f != null && f.isDirectory() && f.exists())
                {
                    com.wol3.client.Settings.instance.setWoWLogsDir(f);
                    logsDir = f;
                } else
                {
                    javax.swing.JOptionPane.showMessageDialog(this, "Uh oh: You have to choose a logs directory to continue.");
                    return;
                }
            }
        }
        java.io.File wowLog = new File((new StringBuilder()).append(logsDir).append(java.io.File.separator).append("WoWCombatLog.txt").toString());
        if (wowLog.canRead())
            new LogFileWindow(wowLog, this);
    }

    public void doOpenLiveReport()
    {
        java.io.File WoWLogsDir = com.wol3.client.Settings.instance.getWoWLogsDir();
        if (WoWLogsDir == null)
        {
            javax.swing.JOptionPane.showMessageDialog(this, "WoW Logs dir not configured");
            return;
        }
        java.io.File wowcombatlog = new File((new StringBuilder(java.lang.String.valueOf(WoWLogsDir.getAbsolutePath()))).append("/WoWCombatLog.txt").toString());
        if (!wowcombatlog.canRead())
        {
            javax.swing.JOptionPane.showMessageDialog(this, (new StringBuilder("Cannot read ")).append(wowcombatlog.getAbsolutePath()).toString());
            return;
        }
        if (!com.wol3.client.Settings.instance.isLoginValid())
        {
            java.lang.System.err.println("Invalid login");
            return;
        }
        try
        {
            com.wol3.client.forms.LiveReportForm f = new LiveReportForm(wowcombatlog);
            f.setVisible(true);
            setVisible(false);
        }
        catch (java.io.IOException e)
        {
            javax.swing.JOptionPane.showMessageDialog(this, (new StringBuilder("Failed to open live report form: ")).append(e.getMessage()).toString());
            e.printStackTrace();
        }
    }

    private java.io.File doSearchForLogsDir()
    {
        java.io.File foundDir = com.wol3.client.Settings.findWoWLogsDir();
        if (foundDir != null)
        {
            int rs = javax.swing.JOptionPane.showConfirmDialog(this, (new StringBuilder("The World of Warcraft logs directory preference is not set yet, do you want to set it to ")).append(foundDir.getAbsolutePath()).append("?").toString(), "WoW Logs Directory", 0);
            if (rs == 0)
            {
                com.wol3.client.Settings.instance.setWoWLogsDir(foundDir);
                return foundDir;
            }
        }
        return null;
    }

    private static final long serialVersionUID = 1L;
    private javax.swing.TransferHandler transferHandler;
    protected javax.swing.JButton openFileButton;
    protected javax.swing.JButton openCurrentLogButton;
    protected javax.swing.JButton openRTSessionButton;
    public javax.swing.JLabel statusBar;
    public static com.wol3.client.forms.OpenFileOrCombatLogForm instance;
}
