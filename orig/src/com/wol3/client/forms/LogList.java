// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   LogList.java

package com.wol3.client.forms;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class LogList extends javax.swing.JList
{
    class ArrayListHandler extends java.util.logging.Handler
        implements javax.swing.ListModel
    {

        public void close()
            throws java.lang.SecurityException
        {
        }

        public void flush()
        {
        }

        public void publish(java.util.logging.LogRecord record)
        {
            records.add(record);
            javax.swing.SwingUtilities.invokeLater(new java.lang.Runnable() {

                public void run()
                {
                    javax.swing.event.ListDataListener l;
                    for (java.util.Iterator iterator = listeners.iterator(); iterator.hasNext(); l.contentsChanged(new ListDataEvent(this, 0, 0, getSize())))
                        l = (javax.swing.event.ListDataListener)iterator.next();

                    scrollToBottom();
                }

                final com.wol3.client.forms.ArrayListHandler this$1;

                
                {
                    _fld1 = com.wol3.client.forms.ArrayListHandler.this;
                    super();
                }
            }
);
        }

        public void scrollToBottom()
        {
            if (scrolling)
            {
                return;
            } else
            {
                scrolling = true;
                java.lang.Thread thread = new java.lang.Thread() {

                    public void run()
                    {
                        try
                        {
                            java.lang.Thread.sleep(200L);
                        }
                        catch (java.lang.InterruptedException e)
                        {
                            e.printStackTrace();
                        }
                        javax.swing.SwingUtilities.invokeLater(new java.lang.Runnable() {

                            public void run()
                            {
                                java.lang.System.out.println((new StringBuilder("ensureIndexIsVisible(")).append(getSize()).append(")").toString());
                                ensureIndexIsVisible(getSize() - 1);
                                scrolling = false;
                            }

                            final com.wol3.client.forms._cls2 this$2;

                        
                        {
                            _fld2 = com.wol3.client.forms._cls2.this;
                            super();
                        }
                        }
);
                    }

                    final com.wol3.client.forms.ArrayListHandler this$1;


                
                {
                    _fld1 = com.wol3.client.forms.ArrayListHandler.this;
                    super();
                }
                }
;
                thread.start();
                return;
            }
        }

        public void addListDataListener(javax.swing.event.ListDataListener l)
        {
            listeners.add(l);
        }

        public java.lang.Object getElementAt(int index)
        {
            return records.get(index);
        }

        public int getSize()
        {
            return records.size();
        }

        public void removeListDataListener(javax.swing.event.ListDataListener l)
        {
            listeners.remove(l);
        }

        public java.util.ArrayList records;
        public java.util.ArrayList listeners;
        private boolean scrolling;
        final com.wol3.client.forms.LogList this$0;



        ArrayListHandler()
        {
            _fld0 = com.wol3.client.forms.LogList.this;
            super();
            records = new ArrayList();
            listeners = new ArrayList();
            scrolling = false;
        }
    }

    class LogCellRenderer
        implements javax.swing.ListCellRenderer
    {

        public java.awt.Component getListCellRendererComponent(javax.swing.JList list, java.lang.Object value, int index, boolean isSelected, boolean cellHasFocus)
        {
            java.util.logging.LogRecord record = (java.util.logging.LogRecord)value;
            timeLabel.setText(dateFormat.format(new Date(record.getMillis())));
            timeLabel.setIcon(getIcon(record.getLevel()));
            messageLabel.setText(record.getMessage());
            if (isSelected)
                panel.setOpaque(true);
            else
                panel.setOpaque(false);
            return panel;
        }

        public javax.swing.Icon getIcon(java.util.logging.Level level)
        {
            if (level.equals(java.util.logging.Level.SEVERE))
                return (javax.swing.Icon)icons.get("ledred.png");
            if (level.equals(java.util.logging.Level.WARNING))
                return (javax.swing.Icon)icons.get("ledyellow.png");
            if (level.equals(java.util.logging.Level.INFO))
                return (javax.swing.Icon)icons.get("ledgreen.png");
            else
                return (javax.swing.Icon)icons.get("ledblue.png");
        }

        private void initIcon(java.lang.String image)
        {
            java.lang.System.out.println((new StringBuilder("/com/wol3/client/icons/")).append(image).toString());
            java.net.URL url = getClass().getResource((new StringBuilder("/com/wol3/client/icons/")).append(image).toString());
            java.awt.Image i = java.awt.Toolkit.getDefaultToolkit().getImage(url);
            icons.put(image, new ImageIcon(i));
        }

        private void initIcons()
        {
            java.lang.String as[];
            int k = (as = (new java.lang.String[] {
                "ledred.png", "ledyellow.png", "ledgreen.png", "ledblue.png"
            })).length;
            for (int j = 0; j < k; j++)
            {
                java.lang.String i = as[j];
                initIcon(i);
            }

        }

        javax.swing.JLabel timeLabel;
        javax.swing.JLabel messageLabel;
        javax.swing.JPanel panel;
        java.util.HashMap icons;
        final com.wol3.client.forms.LogList this$0;

        public LogCellRenderer()
        {
            _fld0 = com.wol3.client.forms.LogList.this;
            super();
            timeLabel = new JLabel();
            messageLabel = new JLabel();
            icons = new HashMap();
            com.jgoodies.forms.builder.PanelBuilder pb = new PanelBuilder(new FormLayout("180px,3dlu,400px:g", "p:g"));
            com.jgoodies.forms.layout.CellConstraints cc = new CellConstraints();
            pb.add(timeLabel, cc.xy(1, 1));
            pb.add(messageLabel, cc.xy(3, 1));
            panel = pb.getPanel();
            panel.setBackground(new Color(128, 255, 255));
            initIcons();
        }
    }


    public LogList()
    {
        dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss Z");
        setCellRenderer(new LogCellRenderer());
        model = new ArrayListHandler();
        model.setLevel(java.util.logging.Level.FINEST);
        setModel(model);
    }

    public static void main(java.lang.String args[])
        throws java.lang.InterruptedException
    {
        javax.swing.JFrame f = new JFrame();
        java.awt.Container contentPane = f.getContentPane();
        final com.wol3.client.forms.LogList ll = new LogList();
        javax.swing.JScrollPane sp = new JScrollPane(ll);
        sp.setPreferredSize(new Dimension(400, 300));
        contentPane.add(sp);
        f.setDefaultCloseOperation(3);
        f.pack();
        f.setVisible(true);
        final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(com/wol3/client/forms/LogList.getName());
        logger.setLevel(java.util.logging.Level.FINEST);
        logger.addHandler(ll.model);
        javax.swing.SwingUtilities.invokeLater(new java.lang.Runnable() {

            public void run()
            {
                for (int i = 0; i < 10; i++)
                {
                    logger.finest("finest");
                    logger.finer("finer");
                    logger.fine("fine");
                    logger.info("info");
                    logger.warning("warning");
                    logger.severe("severe");
                    ll.ensureIndexIsVisible(ll.model.getSize() - 1);
                }

            }

            private final java.util.logging.Logger val$logger;
            private final com.wol3.client.forms.LogList val$ll;

            
            {
                logger = logger1;
                ll = loglist;
                super();
            }
        }
);
    }

    public java.lang.String getMessagesAsString()
    {
        java.io.StringWriter sw = new StringWriter(80 * model.getSize());
        java.io.PrintWriter out = new PrintWriter(sw);
        for (int i = 0; i < model.getSize(); i++)
        {
            java.util.logging.LogRecord r = (java.util.logging.LogRecord)model.getElementAt(i);
            out.print(dateFormat.format(new Date(r.getMillis())));
            out.print(" - ");
            out.println(r.getMessage());
            java.lang.Throwable thrown = r.getThrown();
            if (thrown != null)
                thrown.printStackTrace(out);
        }

        return sw.toString();
    }

    private static final long serialVersionUID = 1L;
    com.wol3.client.forms.ArrayListHandler model;
    java.text.SimpleDateFormat dateFormat;
}
