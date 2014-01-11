// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   AbstractI15dPanelBuilder.java

package com.jgoodies.forms.builder;

import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.Component;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

// Referenced classes of package com.jgoodies.forms.builder:
//            PanelBuilder

public abstract class AbstractI15dPanelBuilder extends com.jgoodies.forms.builder.PanelBuilder
{

    protected AbstractI15dPanelBuilder(com.jgoodies.forms.layout.FormLayout layout)
    {
        super(layout);
    }

    protected AbstractI15dPanelBuilder(com.jgoodies.forms.layout.FormLayout layout, javax.swing.JPanel container)
    {
        super(layout, container);
    }

    private static boolean getDebugToolTipSystemProperty()
    {
        try
        {
            java.lang.String value = java.lang.System.getProperty("I15dPanelBuilder.debugToolTipsEnabled");
            return "true".equalsIgnoreCase(value);
        }
        catch (java.lang.SecurityException e)
        {
            return false;
        }
    }

    public static boolean isDebugToolTipsEnabled()
    {
        return debugToolTipsEnabled;
    }

    public static void setDebugToolTipsEnabled(boolean b)
    {
        debugToolTipsEnabled = b;
    }

    public final javax.swing.JLabel addI15dLabel(java.lang.String resourceKey, com.jgoodies.forms.layout.CellConstraints constraints)
    {
        javax.swing.JLabel label = addLabel(getI15dString(resourceKey), constraints);
        if (com.jgoodies.forms.builder.AbstractI15dPanelBuilder.isDebugToolTipsEnabled())
            label.setToolTipText(resourceKey);
        return label;
    }

    public final javax.swing.JLabel addI15dLabel(java.lang.String resourceKey, java.lang.String encodedConstraints)
    {
        return addI15dLabel(resourceKey, new CellConstraints(encodedConstraints));
    }

    public final javax.swing.JLabel addI15dLabel(java.lang.String resourceKey, com.jgoodies.forms.layout.CellConstraints labelConstraints, java.awt.Component component, com.jgoodies.forms.layout.CellConstraints componentConstraints)
    {
        javax.swing.JLabel label = addLabel(getI15dString(resourceKey), labelConstraints, component, componentConstraints);
        if (com.jgoodies.forms.builder.AbstractI15dPanelBuilder.isDebugToolTipsEnabled())
            label.setToolTipText(resourceKey);
        return label;
    }

    public final javax.swing.JLabel addI15dROLabel(java.lang.String resourceKey, com.jgoodies.forms.layout.CellConstraints constraints)
    {
        javax.swing.JLabel label = addROLabel(getI15dString(resourceKey), constraints);
        if (com.jgoodies.forms.builder.AbstractI15dPanelBuilder.isDebugToolTipsEnabled())
            label.setToolTipText(resourceKey);
        return label;
    }

    public final javax.swing.JLabel addI15dROLabel(java.lang.String resourceKey, java.lang.String encodedConstraints)
    {
        return addI15dROLabel(resourceKey, new CellConstraints(encodedConstraints));
    }

    public final javax.swing.JLabel addI15dROLabel(java.lang.String resourceKey, com.jgoodies.forms.layout.CellConstraints labelConstraints, java.awt.Component component, com.jgoodies.forms.layout.CellConstraints componentConstraints)
    {
        javax.swing.JLabel label = addROLabel(getI15dString(resourceKey), labelConstraints, component, componentConstraints);
        if (com.jgoodies.forms.builder.AbstractI15dPanelBuilder.isDebugToolTipsEnabled())
            label.setToolTipText(resourceKey);
        return label;
    }

    public final javax.swing.JComponent addI15dSeparator(java.lang.String resourceKey, com.jgoodies.forms.layout.CellConstraints constraints)
    {
        javax.swing.JComponent component = addSeparator(getI15dString(resourceKey), constraints);
        if (com.jgoodies.forms.builder.AbstractI15dPanelBuilder.isDebugToolTipsEnabled())
            component.setToolTipText(resourceKey);
        return component;
    }

    public final javax.swing.JComponent addI15dSeparator(java.lang.String resourceKey, java.lang.String encodedConstraints)
    {
        return addI15dSeparator(resourceKey, new CellConstraints(encodedConstraints));
    }

    public final javax.swing.JLabel addI15dTitle(java.lang.String resourceKey, com.jgoodies.forms.layout.CellConstraints constraints)
    {
        javax.swing.JLabel label = addTitle(getI15dString(resourceKey), constraints);
        if (com.jgoodies.forms.builder.AbstractI15dPanelBuilder.isDebugToolTipsEnabled())
            label.setToolTipText(resourceKey);
        return label;
    }

    public final javax.swing.JLabel addI15dTitle(java.lang.String resourceKey, java.lang.String encodedConstraints)
    {
        return addI15dTitle(resourceKey, new CellConstraints(encodedConstraints));
    }

    protected abstract java.lang.String getI15dString(java.lang.String s);

    private static final java.lang.String DEBUG_TOOL_TIPS_ENABLED_KEY = "I15dPanelBuilder.debugToolTipsEnabled";
    private static boolean debugToolTipsEnabled = com.jgoodies.forms.builder.AbstractI15dPanelBuilder.getDebugToolTipSystemProperty();

}
