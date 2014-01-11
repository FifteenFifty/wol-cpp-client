// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   AbstractButtonPanelBuilder.java

package com.jgoodies.forms.builder;

import com.jgoodies.forms.factories.ComponentFactory2;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;

// Referenced classes of package com.jgoodies.forms.builder:
//            AbstractBuilder

public abstract class AbstractButtonPanelBuilder extends com.jgoodies.forms.builder.AbstractBuilder
{

    protected AbstractButtonPanelBuilder(com.jgoodies.forms.layout.FormLayout layout, javax.swing.JPanel container)
    {
        super(layout, container);
        setOpaque(false);
        java.awt.ComponentOrientation orientation = container.getComponentOrientation();
        leftToRight = orientation.isLeftToRight() || !orientation.isHorizontal();
    }

    public final javax.swing.JPanel getPanel()
    {
        return (javax.swing.JPanel)getContainer();
    }

    public final void setBackground(java.awt.Color background)
    {
        getPanel().setBackground(background);
    }

    public final void setBorder(javax.swing.border.Border border)
    {
        getPanel().setBorder(border);
    }

    public final void setOpaque(boolean b)
    {
        getPanel().setOpaque(b);
    }

    public final boolean isLeftToRight()
    {
        return leftToRight;
    }

    public final void setLeftToRight(boolean b)
    {
        leftToRight = b;
    }

    protected final void nextColumn()
    {
        nextColumn(1);
    }

    private void nextColumn(int columns)
    {
        currentCellConstraints.gridX += columns * getColumnIncrementSign();
    }

    protected int getColumn()
    {
        return currentCellConstraints.gridX;
    }

    protected final void nextRow()
    {
        nextRow(1);
    }

    private void nextRow(int rows)
    {
        currentCellConstraints.gridY += rows;
    }

    protected final void appendColumn(com.jgoodies.forms.layout.ColumnSpec columnSpec)
    {
        getLayout().appendColumn(columnSpec);
    }

    protected final void appendGlueColumn()
    {
        appendColumn(com.jgoodies.forms.factories.FormFactory.GLUE_COLSPEC);
    }

    protected final void appendRelatedComponentsGapColumn()
    {
        appendColumn(com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC);
    }

    protected final void appendUnrelatedComponentsGapColumn()
    {
        appendColumn(com.jgoodies.forms.factories.FormFactory.UNRELATED_GAP_COLSPEC);
    }

    protected final void appendRow(com.jgoodies.forms.layout.RowSpec rowSpec)
    {
        getLayout().appendRow(rowSpec);
    }

    protected final void appendGlueRow()
    {
        appendRow(com.jgoodies.forms.factories.FormFactory.GLUE_ROWSPEC);
    }

    protected final void appendRelatedComponentsGapRow()
    {
        appendRow(com.jgoodies.forms.factories.FormFactory.RELATED_GAP_ROWSPEC);
    }

    protected final void appendUnrelatedComponentsGapRow()
    {
        appendRow(com.jgoodies.forms.factories.FormFactory.UNRELATED_GAP_ROWSPEC);
    }

    protected final java.awt.Component add(java.awt.Component component)
    {
        getContainer().add(component, currentCellConstraints);
        return component;
    }

    protected javax.swing.JButton createButton(javax.swing.Action action)
    {
        if (getComponentFactory() instanceof com.jgoodies.forms.factories.ComponentFactory2)
            return ((com.jgoodies.forms.factories.ComponentFactory2)getComponentFactory()).createButton(action);
        else
            return new JButton(action);
    }

    private int getColumnIncrementSign()
    {
        return isLeftToRight() ? 1 : -1;
    }

    protected static final java.lang.String NARROW_KEY = "jgoodies.isNarrow";
    private boolean leftToRight;
}
