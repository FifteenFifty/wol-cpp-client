// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   ButtonBarBuilder.java

package com.jgoodies.forms.builder;

import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.ConstantSize;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.util.LayoutStyle;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

// Referenced classes of package com.jgoodies.forms.builder:
//            PanelBuilder

/**
 * @deprecated Class ButtonBarBuilder is deprecated
 */

public final class ButtonBarBuilder extends com.jgoodies.forms.builder.PanelBuilder
{

    public ButtonBarBuilder()
    {
        this(new JPanel(null));
    }

    public ButtonBarBuilder(javax.swing.JPanel panel)
    {
        super(new FormLayout(COL_SPECS, ROW_SPECS), panel);
        leftToRight = com.jgoodies.forms.util.LayoutStyle.getCurrent().isLeftToRightButtonOrder();
    }

    public static com.jgoodies.forms.builder.ButtonBarBuilder createLeftToRightBuilder()
    {
        com.jgoodies.forms.builder.ButtonBarBuilder builder = new ButtonBarBuilder();
        builder.setLeftToRightButtonOrder(true);
        return builder;
    }

    public boolean isLeftToRightButtonOrder()
    {
        return leftToRight;
    }

    public void setLeftToRightButtonOrder(boolean newButtonOrder)
    {
        leftToRight = newButtonOrder;
    }

    public void setDefaultButtonBarGapBorder()
    {
        setBorder(com.jgoodies.forms.factories.Borders.BUTTON_BAR_GAP_BORDER);
    }

    public void addGriddedButtons(javax.swing.JButton buttons[])
    {
        int length = buttons.length;
        for (int i = 0; i < length; i++)
        {
            int index = leftToRight ? i : length - 1 - i;
            addGridded(buttons[index]);
            if (i < buttons.length - 1)
                addRelatedGap();
        }

    }

    public void addGriddedGrowingButtons(javax.swing.JButton buttons[])
    {
        int length = buttons.length;
        for (int i = 0; i < length; i++)
        {
            int index = leftToRight ? i : length - 1 - i;
            addGriddedGrowing(buttons[index]);
            if (i < buttons.length - 1)
                addRelatedGap();
        }

    }

    public void addFixed(javax.swing.JComponent component)
    {
        getLayout().appendColumn(com.jgoodies.forms.factories.FormFactory.PREF_COLSPEC);
        add(component);
        nextColumn();
    }

    public void addFixedNarrow(javax.swing.JComponent component)
    {
        component.putClientProperty("jgoodies.isNarrow", java.lang.Boolean.TRUE);
        addFixed(component);
    }

    public void addGridded(javax.swing.JComponent component)
    {
        getLayout().appendColumn(com.jgoodies.forms.factories.FormFactory.BUTTON_COLSPEC);
        getLayout().addGroupedColumn(getColumn());
        component.putClientProperty("jgoodies.isNarrow", java.lang.Boolean.TRUE);
        add(component);
        nextColumn();
    }

    public void addGriddedGrowing(javax.swing.JComponent component)
    {
        getLayout().appendColumn(com.jgoodies.forms.factories.FormFactory.GROWING_BUTTON_COLSPEC);
        getLayout().addGroupedColumn(getColumn());
        component.putClientProperty("jgoodies.isNarrow", java.lang.Boolean.TRUE);
        add(component);
        nextColumn();
    }

    public void addGlue()
    {
        appendGlueColumn();
        nextColumn();
    }

    public void addRelatedGap()
    {
        appendRelatedComponentsGapColumn();
        nextColumn();
    }

    public void addUnrelatedGap()
    {
        appendUnrelatedComponentsGapColumn();
        nextColumn();
    }

    public void addStrut(com.jgoodies.forms.layout.ConstantSize width)
    {
        getLayout().appendColumn(com.jgoodies.forms.layout.ColumnSpec.createGap(width));
        nextColumn();
    }

    private static final com.jgoodies.forms.layout.ColumnSpec COL_SPECS[] = new com.jgoodies.forms.layout.ColumnSpec[0];
    private static final com.jgoodies.forms.layout.RowSpec ROW_SPECS[] = {
        com.jgoodies.forms.layout.RowSpec.decode("center:pref")
    };
    private static final java.lang.String NARROW_KEY = "jgoodies.isNarrow";
    private boolean leftToRight;

}
