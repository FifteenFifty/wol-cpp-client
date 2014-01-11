// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   DefaultFormBuilder.java

package com.jgoodies.forms.builder;

import com.jgoodies.forms.factories.ComponentFactory;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ConstantSize;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.Component;
import java.util.ResourceBundle;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

// Referenced classes of package com.jgoodies.forms.builder:
//            I15dPanelBuilder

public final class DefaultFormBuilder extends com.jgoodies.forms.builder.I15dPanelBuilder
{

    public DefaultFormBuilder(com.jgoodies.forms.layout.FormLayout layout)
    {
        this(layout, new JPanel(null));
    }

    public DefaultFormBuilder(com.jgoodies.forms.layout.FormLayout layout, javax.swing.JPanel container)
    {
        this(layout, null, container);
    }

    public DefaultFormBuilder(com.jgoodies.forms.layout.FormLayout layout, java.util.ResourceBundle bundle)
    {
        this(layout, bundle, new JPanel(null));
    }

    public DefaultFormBuilder(com.jgoodies.forms.layout.FormLayout layout, java.util.ResourceBundle bundle, javax.swing.JPanel container)
    {
        super(layout, bundle, container);
        defaultRowSpec = com.jgoodies.forms.factories.FormFactory.PREF_ROWSPEC;
        lineGapSpec = com.jgoodies.forms.factories.FormFactory.LINE_GAP_ROWSPEC;
        paragraphGapSpec = com.jgoodies.forms.factories.FormFactory.PARAGRAPH_GAP_ROWSPEC;
        leadingColumnOffset = 0;
        rowGroupingEnabled = false;
    }

    public com.jgoodies.forms.layout.RowSpec getDefaultRowSpec()
    {
        return defaultRowSpec;
    }

    public void setDefaultRowSpec(com.jgoodies.forms.layout.RowSpec defaultRowSpec)
    {
        this.defaultRowSpec = defaultRowSpec;
    }

    public com.jgoodies.forms.layout.RowSpec getLineGapSpec()
    {
        return lineGapSpec;
    }

    public void setLineGapSize(com.jgoodies.forms.layout.ConstantSize lineGapSize)
    {
        com.jgoodies.forms.layout.RowSpec rowSpec = com.jgoodies.forms.layout.RowSpec.createGap(lineGapSize);
        lineGapSpec = rowSpec;
    }

    public void setParagraphGapSize(com.jgoodies.forms.layout.ConstantSize paragraphGapSize)
    {
        com.jgoodies.forms.layout.RowSpec rowSpec = com.jgoodies.forms.layout.RowSpec.createGap(paragraphGapSize);
        paragraphGapSpec = rowSpec;
    }

    public int getLeadingColumnOffset()
    {
        return leadingColumnOffset;
    }

    public void setLeadingColumnOffset(int columnOffset)
    {
        leadingColumnOffset = columnOffset;
    }

    public boolean isRowGroupingEnabled()
    {
        return rowGroupingEnabled;
    }

    public void setRowGroupingEnabled(boolean enabled)
    {
        rowGroupingEnabled = enabled;
    }

    public void append(java.awt.Component component)
    {
        append(component, 1);
    }

    public void append(java.awt.Component component, int columnSpan)
    {
        ensureCursorColumnInGrid();
        ensureHasGapRow(lineGapSpec);
        ensureHasComponentLine();
        add(component, createLeftAdjustedConstraints(columnSpan));
        nextColumn(columnSpan + 1);
    }

    public void append(java.awt.Component c1, java.awt.Component c2)
    {
        append(c1);
        append(c2);
    }

    public void append(java.awt.Component c1, java.awt.Component c2, java.awt.Component c3)
    {
        append(c1);
        append(c2);
        append(c3);
    }

    public javax.swing.JLabel append(java.lang.String textWithMnemonic)
    {
        javax.swing.JLabel label = getComponentFactory().createLabel(textWithMnemonic);
        append(((java.awt.Component) (label)));
        return label;
    }

    public javax.swing.JLabel append(java.lang.String textWithMnemonic, java.awt.Component component)
    {
        return append(textWithMnemonic, component, 1);
    }

    public javax.swing.JLabel append(java.lang.String textWithMnemonic, java.awt.Component c, boolean nextLine)
    {
        javax.swing.JLabel label = append(textWithMnemonic, c);
        if (nextLine)
            nextLine();
        return label;
    }

    public javax.swing.JLabel append(java.lang.String textWithMnemonic, java.awt.Component c, int columnSpan)
    {
        javax.swing.JLabel label = append(textWithMnemonic);
        label.setLabelFor(c);
        append(c, columnSpan);
        return label;
    }

    public javax.swing.JLabel append(java.lang.String textWithMnemonic, java.awt.Component c1, java.awt.Component c2)
    {
        javax.swing.JLabel label = append(textWithMnemonic, c1);
        append(c2);
        return label;
    }

    public javax.swing.JLabel append(java.lang.String textWithMnemonic, java.awt.Component c1, java.awt.Component c2, int colSpan)
    {
        javax.swing.JLabel label = append(textWithMnemonic, c1);
        append(c2, colSpan);
        return label;
    }

    public javax.swing.JLabel append(java.lang.String textWithMnemonic, java.awt.Component c1, java.awt.Component c2, java.awt.Component c3)
    {
        javax.swing.JLabel label = append(textWithMnemonic, c1, c2);
        append(c3);
        return label;
    }

    public javax.swing.JLabel append(java.lang.String textWithMnemonic, java.awt.Component c1, java.awt.Component c2, java.awt.Component c3, java.awt.Component c4)
    {
        javax.swing.JLabel label = append(textWithMnemonic, c1, c2, c3);
        append(c4);
        return label;
    }

    public javax.swing.JLabel appendI15d(java.lang.String resourceKey)
    {
        return append(getI15dString(resourceKey));
    }

    public javax.swing.JLabel appendI15d(java.lang.String resourceKey, java.awt.Component component)
    {
        return append(getI15dString(resourceKey), component, 1);
    }

    public javax.swing.JLabel appendI15d(java.lang.String resourceKey, java.awt.Component component, boolean nextLine)
    {
        return append(getI15dString(resourceKey), component, nextLine);
    }

    public javax.swing.JLabel appendI15d(java.lang.String resourceKey, java.awt.Component c, int columnSpan)
    {
        return append(getI15dString(resourceKey), c, columnSpan);
    }

    public javax.swing.JLabel appendI15d(java.lang.String resourceKey, java.awt.Component c1, java.awt.Component c2)
    {
        return append(getI15dString(resourceKey), c1, c2);
    }

    public javax.swing.JLabel appendI15d(java.lang.String resourceKey, java.awt.Component c1, java.awt.Component c2, int colSpan)
    {
        return append(getI15dString(resourceKey), c1, c2, colSpan);
    }

    public javax.swing.JLabel appendI15d(java.lang.String resourceKey, java.awt.Component c1, java.awt.Component c2, java.awt.Component c3)
    {
        return append(getI15dString(resourceKey), c1, c2, c3);
    }

    public javax.swing.JLabel appendI15d(java.lang.String resourceKey, java.awt.Component c1, java.awt.Component c2, java.awt.Component c3, java.awt.Component c4)
    {
        return append(getI15dString(resourceKey), c1, c2, c3, c4);
    }

    public javax.swing.JLabel appendTitle(java.lang.String textWithMnemonic)
    {
        javax.swing.JLabel titleLabel = getComponentFactory().createTitle(textWithMnemonic);
        append(titleLabel);
        return titleLabel;
    }

    public javax.swing.JLabel appendI15dTitle(java.lang.String resourceKey)
    {
        return appendTitle(getI15dString(resourceKey));
    }

    public javax.swing.JComponent appendSeparator()
    {
        return appendSeparator("");
    }

    public javax.swing.JComponent appendSeparator(java.lang.String text)
    {
        ensureCursorColumnInGrid();
        ensureHasGapRow(paragraphGapSpec);
        ensureHasComponentLine();
        setColumn(super.getLeadingColumn());
        int columnSpan = getColumnCount();
        setColumnSpan(getColumnCount());
        javax.swing.JComponent titledSeparator = addSeparator(text);
        setColumnSpan(1);
        nextColumn(columnSpan);
        return titledSeparator;
    }

    public javax.swing.JComponent appendI15dSeparator(java.lang.String resourceKey)
    {
        return appendSeparator(getI15dString(resourceKey));
    }

    protected int getLeadingColumn()
    {
        int column = super.getLeadingColumn();
        return column + getLeadingColumnOffset() * getColumnIncrementSign();
    }

    private void ensureCursorColumnInGrid()
    {
        if (isLeftToRight() && getColumn() > getColumnCount() || !isLeftToRight() && getColumn() < 1)
            nextLine();
    }

    private void ensureHasGapRow(com.jgoodies.forms.layout.RowSpec gapRowSpec)
    {
        if (getRow() == 1 || getRow() <= getRowCount())
            return;
        if (getRow() <= getRowCount())
        {
            com.jgoodies.forms.layout.RowSpec rowSpec = getCursorRowSpec();
            if (rowSpec == gapRowSpec)
                return;
        }
        appendRow(gapRowSpec);
        nextLine();
    }

    private void ensureHasComponentLine()
    {
        if (getRow() <= getRowCount())
            return;
        appendRow(getDefaultRowSpec());
        if (isRowGroupingEnabled())
            getLayout().addGroupedRow(getRow());
    }

    private com.jgoodies.forms.layout.RowSpec getCursorRowSpec()
    {
        return getLayout().getRowSpec(getRow());
    }

    private com.jgoodies.forms.layout.RowSpec defaultRowSpec;
    private com.jgoodies.forms.layout.RowSpec lineGapSpec;
    private com.jgoodies.forms.layout.RowSpec paragraphGapSpec;
    private int leadingColumnOffset;
    private boolean rowGroupingEnabled;
}
