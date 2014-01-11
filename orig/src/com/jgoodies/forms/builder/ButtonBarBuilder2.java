// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   ButtonBarBuilder2.java

package com.jgoodies.forms.builder;

import com.jgoodies.common.base.Preconditions;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.ConstantSize;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.util.LayoutStyle;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

// Referenced classes of package com.jgoodies.forms.builder:
//            AbstractButtonPanelBuilder

public class ButtonBarBuilder2 extends com.jgoodies.forms.builder.AbstractButtonPanelBuilder
{

    public ButtonBarBuilder2()
    {
        this(new JPanel(null));
    }

    public ButtonBarBuilder2(javax.swing.JPanel panel)
    {
        super(new FormLayout(COL_SPECS, ROW_SPECS), panel);
        leftToRight = com.jgoodies.forms.util.LayoutStyle.getCurrent().isLeftToRightButtonOrder();
    }

    public static com.jgoodies.forms.builder.ButtonBarBuilder2 createLeftToRightBuilder()
    {
        com.jgoodies.forms.builder.ButtonBarBuilder2 builder = new ButtonBarBuilder2();
        builder.setLeftToRightButtonOrder(true);
        return builder;
    }

    public boolean isLeftToRightButtonOrder()
    {
        return leftToRight;
    }

    public com.jgoodies.forms.builder.ButtonBarBuilder2 setLeftToRightButtonOrder(boolean newButtonOrder)
    {
        leftToRight = newButtonOrder;
        return this;
    }

    public com.jgoodies.forms.builder.ButtonBarBuilder2 setDefaultButtonBarGapBorder()
    {
        setBorder(com.jgoodies.forms.factories.Borders.BUTTON_BAR_GAP_BORDER);
        return this;
    }

    public com.jgoodies.forms.builder.ButtonBarBuilder2 addGlue()
    {
        appendGlueColumn();
        nextColumn();
        return this;
    }

    public com.jgoodies.forms.builder.ButtonBarBuilder2 addRelatedGap()
    {
        appendRelatedComponentsGapColumn();
        nextColumn();
        return this;
    }

    public com.jgoodies.forms.builder.ButtonBarBuilder2 addUnrelatedGap()
    {
        appendUnrelatedComponentsGapColumn();
        nextColumn();
        return this;
    }

    public com.jgoodies.forms.builder.ButtonBarBuilder2 addStrut(com.jgoodies.forms.layout.ConstantSize width)
    {
        getLayout().appendColumn(com.jgoodies.forms.layout.ColumnSpec.createGap(width));
        nextColumn();
        return this;
    }

    public com.jgoodies.forms.builder.ButtonBarBuilder2 addButton(javax.swing.JComponent button)
    {
        button.putClientProperty("jgoodies.isNarrow", java.lang.Boolean.TRUE);
        getLayout().appendColumn(com.jgoodies.forms.factories.FormFactory.BUTTON_COLSPEC);
        add(button);
        nextColumn();
        return this;
    }

    public transient com.jgoodies.forms.builder.ButtonBarBuilder2 addButton(javax.swing.JComponent buttons[])
    {
        com.jgoodies.common.base.Preconditions.checkNotNull(buttons, "The button array must not be null.");
        int length = buttons.length;
        com.jgoodies.common.base.Preconditions.checkArgument(length > 0, "The button array must not be empty.");
        for (int i = 0; i < length; i++)
        {
            int index = leftToRight ? i : length - 1 - i;
            addButton(buttons[index]);
            if (i < buttons.length - 1)
                addRelatedGap();
        }

        return this;
    }

    public com.jgoodies.forms.builder.ButtonBarBuilder2 addButton(javax.swing.Action action)
    {
        com.jgoodies.common.base.Preconditions.checkNotNull(action, "The button Action must not be null.");
        return addButton(((javax.swing.JComponent) (createButton(action))));
    }

    public transient com.jgoodies.forms.builder.ButtonBarBuilder2 addButton(javax.swing.Action actions[])
    {
        com.jgoodies.common.base.Preconditions.checkNotNull(actions, "The Action array must not be null.");
        int length = actions.length;
        com.jgoodies.common.base.Preconditions.checkArgument(length > 0, "The Action array must not be empty.");
        javax.swing.JButton buttons[] = new javax.swing.JButton[length];
        for (int i = 0; i < length; i++)
            buttons[i] = createButton(actions[i]);

        return addButton(((javax.swing.JComponent []) (buttons)));
    }

    public com.jgoodies.forms.builder.ButtonBarBuilder2 addGrowing(javax.swing.JComponent component)
    {
        getLayout().appendColumn(com.jgoodies.forms.factories.FormFactory.GROWING_BUTTON_COLSPEC);
        component.putClientProperty("jgoodies.isNarrow", java.lang.Boolean.TRUE);
        add(component);
        nextColumn();
        return this;
    }

    public transient com.jgoodies.forms.builder.ButtonBarBuilder2 addGrowing(javax.swing.JComponent buttons[])
    {
        int length = buttons.length;
        for (int i = 0; i < length; i++)
        {
            int index = leftToRight ? i : length - 1 - i;
            addGrowing(buttons[index]);
            if (i < buttons.length - 1)
                addRelatedGap();
        }

        return this;
    }

    public com.jgoodies.forms.builder.ButtonBarBuilder2 addFixed(javax.swing.JComponent component)
    {
        component.putClientProperty("jgoodies.isNarrow", java.lang.Boolean.TRUE);
        getLayout().appendColumn(com.jgoodies.forms.factories.FormFactory.PREF_COLSPEC);
        add(component);
        nextColumn();
        return this;
    }

    private static final com.jgoodies.forms.layout.ColumnSpec COL_SPECS[] = new com.jgoodies.forms.layout.ColumnSpec[0];
    private static final com.jgoodies.forms.layout.RowSpec ROW_SPECS[] = {
        com.jgoodies.forms.layout.RowSpec.decode("center:pref")
    };
    private boolean leftToRight;

}
