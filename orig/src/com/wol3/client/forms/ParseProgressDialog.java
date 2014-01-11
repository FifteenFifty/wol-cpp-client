// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   ParseProgressDialog.java

package com.wol3.client.forms;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

public class ParseProgressDialog extends javax.swing.JDialog
{

    public ParseProgressDialog(java.awt.Frame owner)
    {
        super(owner);
        createComponents();
        pack();
        java.awt.GraphicsEnvironment ge = java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
        java.awt.Rectangle maxBounds = ge.getMaximumWindowBounds();
        java.awt.Rectangle current = getBounds();
        current.x = (maxBounds.width - current.width) / 2;
        current.y = (maxBounds.height - current.height) / 2;
        setBounds(current);
        setDefaultCloseOperation(2);
    }

    private void createComponents()
    {
        progressBar = new JProgressBar(0, 1000);
        progressBar.setValue(0);
        progressBar.setStringPainted(true);
        text = new JLabel();
        javax.swing.JPanel panel = (javax.swing.JPanel)getContentPane();
        com.jgoodies.forms.builder.PanelBuilder b = new PanelBuilder(new FormLayout("p", "p, 3dlu, p"), panel);
        com.jgoodies.forms.layout.CellConstraints cc = new CellConstraints();
        b.add(text, cc.xy(1, 1));
        b.add(progressBar, cc.xy(1, 3));
        b.setDefaultDialogBorder();
    }

    public void updateText(java.lang.String text)
    {
        this.text.setText(text);
        pack();
        if (!isVisible())
            setVisible(true);
    }

    public void updateProgress(int value)
    {
        progressBar.setValue(value);
    }

    public void startUpdateThread()
    {
        stop = false;
        timer = new java.lang.Thread() {

            public void run()
            {
                do
                {
                    int progress;
                    do
                    {
                        if (stop)
                            return;
                        try
                        {
                            java.lang.Thread.sleep(100L);
                        }
                        catch (java.lang.InterruptedException interruptedexception) { }
                        value = progressBar.getValue();
                        progress = getProgress();
                    } while (progress == value);
                    value = progress;
                    java.lang.Runnable r = new java.lang.Runnable() {

                        public void run()
                        {
                            progressBar.setValue(value);
                        }

                        final com.wol3.client.forms._cls1 this$1;

                    
                    {
                        _fld1 = com.wol3.client.forms._cls1.this;
                        super();
                    }
                    }
;
                    javax.swing.SwingUtilities.invokeLater(r);
                } while (true);
            }

            final com.wol3.client.forms.ParseProgressDialog this$0;


            
            {
                _fld0 = com.wol3.client.forms.ParseProgressDialog.this;
                super();
            }
        }
;
        timer.start();
    }

    public void stopUpdateThread()
    {
        stop = true;
    }

    public int getProgress()
    {
        return 0;
    }

    private static final long serialVersionUID = 1L;
    public javax.swing.JLabel text;
    public javax.swing.JProgressBar progressBar;
    java.lang.Thread timer;
    volatile int value;
    boolean stop;
}
