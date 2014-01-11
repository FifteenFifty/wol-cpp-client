// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   ActivityGraph.java

package com.wol3.client.forms;

import com.wol3.client.data.BinaryCombatLog;
import com.wol3.client.data.EntryList;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.PrintStream;
import java.util.Date;
import javax.swing.JComponent;
import javax.swing.JTextField;
import org.apache.commons.collections.primitives.ArrayLongList;

// Referenced classes of package com.wol3.client.forms:
//            LogFileWindow

public class ActivityGraph extends javax.swing.JComponent
{

    public ActivityGraph(com.wol3.client.data.BinaryCombatLog log, com.wol3.client.forms.LogFileWindow window)
    {
        startLocation = 0;
        endLocation = -1;
        this.log = log;
        this.window = window;
        java.awt.event.MouseAdapter mouseAdapter = new java.awt.event.MouseAdapter() {

            public void mousePressed(java.awt.event.MouseEvent e)
            {
                startLocation = e.getX();
                if (startLocation < 5)
                    startLocation = 0;
                endLocation = -1;
                repaint();
            }

            public void mouseReleased(java.awt.event.MouseEvent e)
            {
                endLocation = e.getX();
                if (endLocation < lines.length && lines.length - endLocation < 6)
                    endLocation = lines.length;
                repaint();
            }

            public void mouseDragged(java.awt.event.MouseEvent e)
            {
                endLocation = e.getX();
                if (endLocation < lines.length && lines.length - endLocation < 6)
                    endLocation = lines.length;
                repaint();
            }

            final com.wol3.client.forms.ActivityGraph this$0;

            
            {
                _fld0 = com.wol3.client.forms.ActivityGraph.this;
                super();
            }
        }
;
        addMouseMotionListener(mouseAdapter);
        addMouseListener(mouseAdapter);
        createChartData();
        startLocation = 0;
        endLocation = lines.length;
    }

    public void paint(java.awt.Graphics g)
    {
        java.awt.Dimension size = getSize();
        java.awt.Graphics2D g2d = (java.awt.Graphics2D)g;
        g2d.setColor(java.awt.Color.white);
        g2d.fillRect(0, 0, size.width, size.height);
        if (endLocation != -1)
        {
            g2d.setColor(new Color(200, 230, 255));
            g2d.fillRect(java.lang.Math.min(startLocation, endLocation), 0, java.lang.Math.abs(startLocation - endLocation), size.height);
        }
        float scalar = ((float)peak / (float)size.height) * 1.1F;
        g2d.setColor(new Color(0, 128, 255));
        for (int i = 1; i < lines.length; i++)
        {
            int y1 = size.height - java.lang.Math.round((float)lines[i - 1] / scalar);
            int y2 = size.height - java.lang.Math.round((float)lines[i] / scalar);
            g2d.drawLine(i - 1, y1, i, y2);
        }

        updateSelectionTextFields();
    }

    private boolean updateSelectionTextFields()
    {
        int min = java.lang.Math.min(startLocation, endLocation);
        int max = java.lang.Math.max(startLocation, endLocation);
        int minTimeOffset = min * 15 * 1000;
        int maxTimeOffset = (max + 1) * 15 * 1000;
        firstLine = -1;
        lastLine = -1;
        long start = log.entryList.timestamps.get(0);
        for (int i = 0; i < log.entryList.size(); i++)
        {
            if (log.entryList.timestamps.get(i) - start < (long)minTimeOffset)
                continue;
            firstLine = i;
            break;
        }

        for (int i = log.entryList.size() - 1; i >= 0; i--)
        {
            if (log.entryList.timestamps.get(i) - start >= (long)maxTimeOffset)
                continue;
            lastLine = i;
            break;
        }

        window.linesSelectedTextField.setBackground(null);
        if (firstLine == -1 || lastLine == -1 || lastLine < firstLine)
        {
            window.selectedStartTimeTextField.setText("");
            window.selectedEndTimeTextField.setText("");
            window.linesSelectedTextField.setText("Invalid selection range...");
            window.linesSelectedTextField.setBackground(java.awt.Color.red);
            return false;
        }
        long startTime = log.entryList.timestamps.get(firstLine);
        long endTime = log.entryList.timestamps.get(lastLine);
        window.selectedStartTimeTextField.setText((new Date(startTime)).toString());
        window.selectedEndTimeTextField.setText((new Date(endTime)).toString());
        window.linesSelectedTextField.setText((new StringBuilder()).append((lastLine - firstLine) + 1).toString());
        if (endTime - startTime > 0L && endTime - startTime < 0x2255100L)
        {
            return true;
        } else
        {
            window.linesSelectedTextField.setText((new StringBuilder(java.lang.String.valueOf(window.linesSelectedTextField.getText()))).append(" (too long)").toString());
            window.linesSelectedTextField.setBackground(java.awt.Color.red);
            return false;
        }
    }

    public int getGraphWidth()
    {
        double durationInMs = log.entryList.timestamps.get(log.entryList.size() - 1) - log.entryList.timestamps.get(0);
        return (int)java.lang.Math.ceil(durationInMs / 1000D / 15D) + 1;
    }

    public java.awt.Dimension getPreferredSize()
    {
        int width = getGraphWidth();
        java.awt.Dimension d = new Dimension(width, 50);
        return d;
    }

    public void createChartData()
    {
        long start = log.entryList.timestamps.get(0);
        lines = new int[getGraphWidth()];
        for (int i = 0; i < log.entryList.size(); i++)
        {
            int offset = (int)((log.entryList.timestamps.get(i) - start) / 15000L);
            if (offset < lines.length && offset >= 0)
                lines[offset]++;
            else
                java.lang.System.out.println((new StringBuilder("Invalid offset: ")).append(offset).toString());
        }

        peak = 0;
        int ai[];
        int k = (ai = lines).length;
        for (int j = 0; j < k; j++)
        {
            int i = ai[j];
            if (i > peak)
                peak = i;
        }

    }

    public boolean validateTimeRange()
    {
        return updateSelectionTextFields();
    }

    private static final long serialVersionUID = 1L;
    com.wol3.client.data.BinaryCombatLog log;
    int startLocation;
    int endLocation;
    com.wol3.client.forms.LogFileWindow window;
    int firstLine;
    int lastLine;
    int lines[];
    int peak;
}
