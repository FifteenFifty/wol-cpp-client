// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   AbstractFormBuilder.java

package com.jgoodies.forms.builder;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Container;

// Referenced classes of package com.jgoodies.forms.builder:
//            AbstractBuilder

public abstract class AbstractFormBuilder extends com.jgoodies.forms.builder.AbstractBuilder
{

    public AbstractFormBuilder(com.jgoodies.forms.layout.FormLayout layout, java.awt.Container container)
    {
        super(layout, container);
        java.awt.ComponentOrientation orientation = container.getComponentOrientation();
        leftToRight = orientation.isLeftToRight() || !orientation.isHorizontal();
    }

    public final boolean isLeftToRight()
    {
        return leftToRight;
    }

    public final void setLeftToRight(boolean b)
    {
        leftToRight = b;
    }

    public final int getColumn()
    {
        return currentCellConstraints.gridX;
    }

    public final void setColumn(int column)
    {
        currentCellConstraints.gridX = column;
    }

    public final int getRow()
    {
        return currentCellConstraints.gridY;
    }

    public final void setRow(int row)
    {
        currentCellConstraints.gridY = row;
    }

    public final void setColumnSpan(int columnSpan)
    {
        currentCellConstraints.gridWidth = columnSpan;
    }

    public final void setRowSpan(int rowSpan)
    {
        currentCellConstraints.gridHeight = rowSpan;
    }

    public final void setOrigin(int column, int row)
    {
        setColumn(column);
        setRow(row);
    }

    public final void setExtent(int columnSpan, int rowSpan)
    {
        setColumnSpan(columnSpan);
        setRowSpan(rowSpan);
    }

    public final void setBounds(int column, int row, int columnSpan, int rowSpan)
    {
        setColumn(column);
        setRow(row);
        setColumnSpan(columnSpan);
        setRowSpan(rowSpan);
    }

    public final void setHAlignment(com.jgoodies.forms.layout.CellConstraints.Alignment alignment)
    {
        cellConstraints().hAlign = alignment;
    }

    public final void setVAlignment(com.jgoodies.forms.layout.CellConstraints.Alignment alignment)
    {
        cellConstraints().vAlign = alignment;
    }

    public final void setAlignment(com.jgoodies.forms.layout.CellConstraints.Alignment hAlign, com.jgoodies.forms.layout.CellConstraints.Alignment vAlign)
    {
        setHAlignment(hAlign);
        setVAlignment(vAlign);
    }

    public final void nextColumn()
    {
        nextColumn(1);
    }

    public final void nextColumn(int columns)
    {
        cellConstraints().gridX += columns * getColumnIncrementSign();
    }

    public final void nextRow()
    {
        nextRow(1);
    }

    public final void nextRow(int rows)
    {
        cellConstraints().gridY += rows;
    }

    public final void nextLine()
    {
        nextLine(1);
    }

    public final void nextLine(int lines)
    {
        nextRow(lines);
        setColumn(getLeadingColumn());
    }

    public final void appendColumn(com.jgoodies.forms.layout.ColumnSpec columnSpec)
    {
        getLayout().appendColumn(columnSpec);
    }

    public final void appendColumn(java.lang.String encodedColumnSpec)
    {
        appendColumn(com.jgoodies.forms.layout.ColumnSpec.decode(encodedColumnSpec));
    }

    public final void appendGlueColumn()
    {
        appendColumn(com.jgoodies.forms.factories.FormFactory.GLUE_COLSPEC);
    }

    public final void appendLabelComponentsGapColumn()
    {
        appendColumn(com.jgoodies.forms.factories.FormFactory.LABEL_COMPONENT_GAP_COLSPEC);
    }

    public final void appendRelatedComponentsGapColumn()
    {
        appendColumn(com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC);
    }

    public final void appendUnrelatedComponentsGapColumn()
    {
        appendColumn(com.jgoodies.forms.factories.FormFactory.UNRELATED_GAP_COLSPEC);
    }

    public final void appendRow(com.jgoodies.forms.layout.RowSpec rowSpec)
    {
        getLayout().appendRow(rowSpec);
    }

    public final void appendRow(java.lang.String encodedRowSpec)
    {
        appendRow(com.jgoodies.forms.layout.RowSpec.decode(encodedRowSpec));
    }

    public final void appendGlueRow()
    {
        appendRow(com.jgoodies.forms.factories.FormFactory.GLUE_ROWSPEC);
    }

    public final void appendRelatedComponentsGapRow()
    {
        appendRow(com.jgoodies.forms.factories.FormFactory.RELATED_GAP_ROWSPEC);
    }

    public final void appendUnrelatedComponentsGapRow()
    {
        appendRow(com.jgoodies.forms.factories.FormFactory.UNRELATED_GAP_ROWSPEC);
    }

    public final void appendParagraphGapRow()
    {
        appendRow(com.jgoodies.forms.factories.FormFactory.PARAGRAPH_GAP_ROWSPEC);
    }

    public java.awt.Component add(java.awt.Component component, com.jgoodies.forms.layout.CellConstraints cellConstraints)
    {
        getContainer().add(component, cellConstraints);
        return component;
    }

    public final java.awt.Component add(java.awt.Component component, java.lang.String encodedCellConstraints)
    {
        getContainer().add(component, new CellConstraints(encodedCellConstraints));
        return component;
    }

    public final java.awt.Component add(java.awt.Component component)
    {
        add(component, cellConstraints());
        return component;
    }

    protected final com.jgoodies.forms.layout.CellConstraints cellConstraints()
    {
        return currentCellConstraints;
    }

    protected int getLeadingColumn()
    {
        return isLeftToRight() ? 1 : getColumnCount();
    }

    protected final int getColumnIncrementSign()
    {
        return isLeftToRight() ? 1 : -1;
    }

    protected final com.jgoodies.forms.layout.CellConstraints createLeftAdjustedConstraints(int columnSpan)
    {
        int firstColumn = isLeftToRight() ? getColumn() : (getColumn() + 1) - columnSpan;
        return new CellConstraints(firstColumn, getRow(), columnSpan, cellConstraints().gridHeight);
    }

    private boolean leftToRight;
}
