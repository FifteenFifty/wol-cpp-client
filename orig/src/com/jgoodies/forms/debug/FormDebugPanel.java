// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   FormDebugPanel.java

package com.jgoodies.forms.debug;

import com.jgoodies.forms.layout.FormLayout;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

// Referenced classes of package com.jgoodies.forms.debug:
//            FormDebugUtils

public class FormDebugPanel extends javax.swing.JPanel
{

    public FormDebugPanel()
    {
        this(null);
    }

    public FormDebugPanel(com.jgoodies.forms.layout.FormLayout layout)
    {
        this(layout, false, false);
    }

    public FormDebugPanel(boolean paintInBackground, boolean paintDiagonals)
    {
        this(null, paintInBackground, paintDiagonals);
    }

    public FormDebugPanel(com.jgoodies.forms.layout.FormLayout layout, boolean paintInBackground, boolean paintDiagonals)
    {
        super(layout);
        paintRows = paintRowsDefault;
        gridColor = DEFAULT_GRID_COLOR;
        setPaintInBackground(paintInBackground);
        setPaintDiagonals(paintDiagonals);
        setGridColor(DEFAULT_GRID_COLOR);
    }

    public void setPaintInBackground(boolean b)
    {
        paintInBackground = b;
    }

    public void setPaintDiagonals(boolean b)
    {
        paintDiagonals = b;
    }

    public void setPaintRows(boolean b)
    {
        paintRows = b;
    }

    public void setGridColor(java.awt.Color color)
    {
        gridColor = color;
    }

    protected void paintComponent(java.awt.Graphics g)
    {
        super.paintComponent(g);
        if (paintInBackground)
            paintGrid(g);
    }

    public void paint(java.awt.Graphics g)
    {
        super.paint(g);
        if (!paintInBackground)
            paintGrid(g);
    }

    private void paintGrid(java.awt.Graphics g)
    {
        if (!(getLayout() instanceof com.jgoodies.forms.layout.FormLayout))
            return;
        com.jgoodies.forms.layout.FormLayout.LayoutInfo layoutInfo = com.jgoodies.forms.debug.FormDebugUtils.getLayoutInfo(this);
        int left = layoutInfo.getX();
        int top = layoutInfo.getY();
        int width = layoutInfo.getWidth();
        int height = layoutInfo.getHeight();
        g.setColor(gridColor);
        int last = layoutInfo.columnOrigins.length - 1;
        for (int col = 0; col <= last; col++)
        {
            boolean firstOrLast = col == 0 || col == last;
            int x = layoutInfo.columnOrigins[col];
            int start = firstOrLast ? 0 : top;
            int stop = firstOrLast ? getHeight() : top + height;
            for (int i = start; i < stop; i += 5)
            {
                int length = java.lang.Math.min(3, stop - i);
                g.fillRect(x, i, 1, length);
            }

        }

        last = layoutInfo.rowOrigins.length - 1;
        for (int row = 0; row <= last; row++)
        {
            boolean firstOrLast = row == 0 || row == last;
            int y = layoutInfo.rowOrigins[row];
            int start = firstOrLast ? 0 : left;
            int stop = firstOrLast ? getWidth() : left + width;
            if (!firstOrLast && !paintRows)
                continue;
            for (int i = start; i < stop; i += 5)
            {
                int length = java.lang.Math.min(3, stop - i);
                g.fillRect(i, y, length, 1);
            }

        }

        if (paintDiagonals)
        {
            g.drawLine(left, top, left + width, top + height);
            g.drawLine(left, top + height, left + width, top);
        }
    }

    public static boolean paintRowsDefault = true;
    private static final java.awt.Color DEFAULT_GRID_COLOR;
    private boolean paintInBackground;
    private boolean paintDiagonals;
    private boolean paintRows;
    private java.awt.Color gridColor;

    static 
    {
        DEFAULT_GRID_COLOR = java.awt.Color.red;
    }
}
