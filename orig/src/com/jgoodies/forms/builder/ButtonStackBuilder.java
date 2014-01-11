// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   ButtonStackBuilder.java

package com.jgoodies.forms.builder;

import com.jgoodies.forms.factories.ComponentFactory2;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.ConstantSize;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpec;
import com.jgoodies.forms.layout.RowSpec;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

// Referenced classes of package com.jgoodies.forms.builder:
//            PanelBuilder

public final class ButtonStackBuilder extends com.jgoodies.forms.builder.PanelBuilder
{

    public ButtonStackBuilder()
    {
        this(new JPanel(null));
    }

    public ButtonStackBuilder(javax.swing.JPanel panel)
    {
        this(new FormLayout(COL_SPECS, ROW_SPECS), panel);
    }

    public ButtonStackBuilder(com.jgoodies.forms.layout.FormLayout layout, javax.swing.JPanel panel)
    {
        super(layout, panel);
        setOpaque(false);
    }

    public void addButtons(javax.swing.JButton buttons[])
    {
        for (int i = 0; i < buttons.length; i++)
        {
            addGridded(buttons[i]);
            if (i < buttons.length - 1)
                addRelatedGap();
        }

    }

    public void addFixed(javax.swing.JComponent component)
    {
        getLayout().appendRow(com.jgoodies.forms.factories.FormFactory.PREF_ROWSPEC);
        add(component);
        nextRow();
    }

    public void addGridded(javax.swing.JComponent component)
    {
        getLayout().appendRow(com.jgoodies.forms.factories.FormFactory.PREF_ROWSPEC);
        getLayout().addGroupedRow(getRow());
        component.putClientProperty("jgoodies.isNarrow", java.lang.Boolean.TRUE);
        add(component);
        nextRow();
    }

    public void addGlue()
    {
        appendGlueRow();
        nextRow();
    }

    public void addRelatedGap()
    {
        appendRelatedComponentsGapRow();
        nextRow();
    }

    public void addUnrelatedGap()
    {
        appendUnrelatedComponentsGapRow();
        nextRow();
    }

    public void addStrut(com.jgoodies.forms.layout.ConstantSize size)
    {
        getLayout().appendRow(new RowSpec(com.jgoodies.forms.layout.RowSpec.TOP, size, 0.0D));
        nextRow();
    }

    public transient void addButton(javax.swing.JButton buttons[])
    {
        addButtons(buttons);
    }

    public transient void addButton(javax.swing.Action actions[])
    {
        javax.swing.JButton buttons[] = new javax.swing.JButton[actions.length];
        for (int i = 0; i < actions.length; i++)
            buttons[i] = createButton(actions[i]);

        addButtons(buttons);
    }

    private javax.swing.JButton createButton(javax.swing.Action action)
    {
        if (getComponentFactory() instanceof com.jgoodies.forms.factories.ComponentFactory2)
            return ((com.jgoodies.forms.factories.ComponentFactory2)getComponentFactory()).createButton(action);
        else
            return new JButton(action);
    }

    private static final com.jgoodies.forms.layout.ColumnSpec COL_SPECS[];
    private static final com.jgoodies.forms.layout.RowSpec ROW_SPECS[] = new com.jgoodies.forms.layout.RowSpec[0];
    private static final java.lang.String NARROW_KEY = "jgoodies.isNarrow";

    static 
    {
        COL_SPECS = (new com.jgoodies.forms.layout.ColumnSpec[] {
            com.jgoodies.forms.factories.FormFactory.BUTTON_COLSPEC
        });
    }
}
