// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   ConfigurationPanel.java

package com.wol3.client.forms;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import com.wol3.client.Settings;
import com.wol3.client.comm.HttpConnector;
import com.wol3.util.UIHelpers;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.util.EntityUtils;

public class ConfigurationPanel extends javax.swing.JPanel
{

    public ConfigurationPanel()
    {
        createComponents();
        createForm();
    }

    private void createComponents()
    {
        final javax.swing.JPanel thisref = this;
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        java.lang.String user = com.wol3.client.Settings.instance.getUsername();
        if (user != null)
            usernameField.setText(user);
        java.lang.String password = com.wol3.client.Settings.instance.getPassword();
        if (password != null)
            passwordField.setText(password);
        validateUserButton = new JButton("Test account settings");
        validateUserButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                com.wol3.client.comm.HttpConnector c = new HttpConnector();
                try
                {
                    java.lang.String username = usernameField.getText();
                    java.lang.String password = new String(passwordField.getPassword());
                    org.apache.http.HttpResponse login = c.login(username, password);
                    org.apache.http.util.EntityUtils.consumeQuietly(login.getEntity());
                    if (login.getStatusLine().getStatusCode() == 200)
                    {
                        com.wol3.client.Settings.instance.setUsername(username);
                        com.wol3.client.Settings.instance.setPassword(password);
                        com.wol3.client.Settings.instance.saveSettings();
                        javax.swing.JOptionPane.showMessageDialog(thisref, "The username and password are valid, options saved");
                    } else
                    {
                        javax.swing.JOptionPane.showMessageDialog(thisref, "The username/password was not valid, the settings are not saved", "Invalid login", 2);
                    }
                }
                catch (java.io.IOException ex)
                {
                    javax.swing.JOptionPane.showMessageDialog(thisref, ex, "Failed to login to server", 2);
                    ex.printStackTrace();
                }
            }

            final com.wol3.client.forms.ConfigurationPanel this$0;
            private final javax.swing.JPanel val$thisref;

            
            {
                _fld0 = com.wol3.client.forms.ConfigurationPanel.this;
                thisref = jpanel;
                super();
            }
        }
);
        logsDirField = new JTextField();
        setLogsDirectoryButton = new JButton("Find/Test and save");
        setLogsDirectoryButton.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent e)
            {
                java.lang.String dirField = logsDirField.getText();
                java.io.File dir = new File(dirField);
                if ("".equals(dirField))
                    dir = com.wol3.client.Settings.findWoWLogsDir();
                if (dir.isDirectory())
                {
                    com.wol3.client.Settings.instance.setWoWLogsDir(dir);
                    javax.swing.JOptionPane.showMessageDialog(thisref, (new StringBuilder("WoW Logs dir set to ")).append(dir.getAbsolutePath()).append(".").toString());
                    logsDirField.setText(dir.getAbsolutePath());
                } else
                {
                    javax.swing.JOptionPane.showMessageDialog(thisref, (new StringBuilder("Could not save setting: ")).append(dir).append(" is not a directory.").toString());
                }
            }

            final com.wol3.client.forms.ConfigurationPanel this$0;
            private final javax.swing.JPanel val$thisref;

            
            {
                _fld0 = com.wol3.client.forms.ConfigurationPanel.this;
                thisref = jpanel;
                super();
            }
        }
);
        java.io.File logsDir = com.wol3.client.Settings.instance.getWoWLogsDir();
        if (logsDir != null)
            logsDirField.setText(logsDir.getAbsolutePath());
    }

    private void createForm()
    {
        com.jgoodies.forms.builder.PanelBuilder b = new PanelBuilder(new FormLayout("right:pref, 3dlu, [100px, pref]:grow, pref", "pref, 3dlu, pref, 3dlu, pref, 3dlu, pref, 9dlu, pref, 3dlu, pref, 3dlu, pref"), this);
        com.jgoodies.forms.layout.CellConstraints cc = new CellConstraints();
        com.jgoodies.forms.layout.CellConstraints cc2 = new CellConstraints();
        b.addSeparator("User Account", cc.xyw(1, 1, 4));
        b.addLabel("&Username", cc.xy(1, 3), usernameField, cc2.xyw(3, 3, 2));
        b.addLabel("&Password", cc.xy(1, 5), passwordField, cc2.xyw(3, 5, 2));
        b.add(validateUserButton, cc.xy(4, 7));
        b.addSeparator("World of Warcraft", cc.xyw(1, 9, 4));
        b.addLabel("&Logs directory", cc.xy(1, 11), logsDirField, cc2.xyw(3, 11, 2));
        b.add(setLogsDirectoryButton, cc.xy(4, 13));
        setBorder(new EmptyBorder(5, 5, 5, 5));
    }

    public static void main(java.lang.String args[])
    {
        com.wol3.client.Settings.getSettings();
        javax.swing.JFrame frame = new JFrame("World of Logs - Settings");
        frame.setContentPane(new ConfigurationPanel());
        frame.pack();
        com.wol3.util.UIHelpers.center(frame);
        frame.setDefaultCloseOperation(3);
        frame.setVisible(true);
    }

    private static final long serialVersionUID = 1L;
    javax.swing.JTextField usernameField;
    javax.swing.JPasswordField passwordField;
    javax.swing.JButton validateUserButton;
    javax.swing.JTextField logsDirField;
    javax.swing.JButton setLogsDirectoryButton;
}
