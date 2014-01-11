// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   PanelBuilder.java

package com.jgoodies.forms.builder;

import com.jgoodies.common.base.Preconditions;
import com.jgoodies.forms.factories.Borders;
import com.jgoodies.forms.factories.ComponentFactory;
import com.jgoodies.forms.factories.ComponentFactory2;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.Color;
import java.awt.Component;
import java.lang.ref.WeakReference;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;
import javax.swing.border.Border;

// Referenced classes of package com.jgoodies.forms.builder:
//            AbstractFormBuilder

public class PanelBuilder extends com.jgoodies.forms.builder.AbstractFormBuilder
{

    public PanelBuilder(com.jgoodies.forms.layout.FormLayout layout)
    {
        this(layout, new JPanel(null));
    }

    public PanelBuilder(com.jgoodies.forms.layout.FormLayout layout, javax.swing.JPanel panel)
    {
        super(layout, panel);
        mostRecentlyAddedLabelReference = null;
        labelForFeatureEnabled = labelForFeatureEnabledDefault;
    }

    public static boolean getLabelForFeatureEnabledDefault()
    {
        return labelForFeatureEnabledDefault;
    }

    public static void setLabelForFeatureEnabledDefault(boolean b)
    {
        labelForFeatureEnabledDefault = b;
    }

    public boolean isLabelForFeatureEnabled()
    {
        return labelForFeatureEnabled;
    }

    public void setLabelForFeatureEnabled(boolean b)
    {
        labelForFeatureEnabled = b;
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

    public final void setDefaultDialogBorder()
    {
        setBorder(com.jgoodies.forms.factories.Borders.DIALOG_BORDER);
    }

    public final void setOpaque(boolean b)
    {
        getPanel().setOpaque(b);
    }

    public final javax.swing.JLabel addLabel(java.lang.String textWithMnemonic)
    {
        return addLabel(textWithMnemonic, cellConstraints());
    }

    public final javax.swing.JLabel addLabel(java.lang.String textWithMnemonic, com.jgoodies.forms.layout.CellConstraints constraints)
    {
        javax.swing.JLabel label = getComponentFactory().createLabel(textWithMnemonic);
        add(label, constraints);
        return label;
    }

    public final javax.swing.JLabel addLabel(java.lang.String textWithMnemonic, java.lang.String encodedConstraints)
    {
        return addLabel(textWithMnemonic, new CellConstraints(encodedConstraints));
    }

    public final javax.swing.JLabel addLabel(java.lang.String textWithMnemonic, com.jgoodies.forms.layout.CellConstraints labelConstraints, java.awt.Component component, com.jgoodies.forms.layout.CellConstraints componentConstraints)
    {
        if (labelConstraints == componentConstraints)
        {
            throw new IllegalArgumentException("You must provide two CellConstraints instances, one for the label and one for the component.\nConsider using the CC class. See the JavaDocs for details.");
        } else
        {
            javax.swing.JLabel label = addLabel(textWithMnemonic, labelConstraints);
            add(component, componentConstraints);
            label.setLabelFor(component);
            return label;
        }
    }

    public final javax.swing.JLabel addROLabel(java.lang.String textWithMnemonic)
    {
        return addROLabel(textWithMnemonic, cellConstraints());
    }

    public final javax.swing.JLabel addROLabel(java.lang.String textWithMnemonic, com.jgoodies.forms.layout.CellConstraints constraints)
    {
        com.jgoodies.forms.factories.ComponentFactory factory = getComponentFactory();
        com.jgoodies.forms.factories.ComponentFactory2 factory2;
        if (factory instanceof com.jgoodies.forms.factories.ComponentFactory2)
            factory2 = (com.jgoodies.forms.factories.ComponentFactory2)factory;
        else
            factory2 = com.jgoodies.forms.factories.DefaultComponentFactory.getInstance();
        javax.swing.JLabel label = factory2.createReadOnlyLabel(textWithMnemonic);
        add(label, constraints);
        return label;
    }

    public final javax.swing.JLabel addROLabel(java.lang.String textWithMnemonic, java.lang.String encodedConstraints)
    {
        return addROLabel(textWithMnemonic, new CellConstraints(encodedConstraints));
    }

    public final javax.swing.JLabel addROLabel(java.lang.String textWithMnemonic, com.jgoodies.forms.layout.CellConstraints labelConstraints, java.awt.Component component, com.jgoodies.forms.layout.CellConstraints componentConstraints)
    {
        checkConstraints(labelConstraints, componentConstraints);
        javax.swing.JLabel label = addROLabel(textWithMnemonic, labelConstraints);
        add(component, componentConstraints);
        label.setLabelFor(component);
        return label;
    }

    public final javax.swing.JLabel addTitle(java.lang.String textWithMnemonic)
    {
        return addTitle(textWithMnemonic, cellConstraints());
    }

    public final javax.swing.JLabel addTitle(java.lang.String textWithMnemonic, com.jgoodies.forms.layout.CellConstraints constraints)
    {
        javax.swing.JLabel titleLabel = getComponentFactory().createTitle(textWithMnemonic);
        add(titleLabel, constraints);
        return titleLabel;
    }

    public final javax.swing.JLabel addTitle(java.lang.String textWithMnemonic, java.lang.String encodedConstraints)
    {
        return addTitle(textWithMnemonic, new CellConstraints(encodedConstraints));
    }

    public final javax.swing.JComponent addSeparator(java.lang.String textWithMnemonic)
    {
        return addSeparator(textWithMnemonic, getLayout().getColumnCount());
    }

    public final javax.swing.JComponent addSeparator(java.lang.String textWithMnemonic, com.jgoodies.forms.layout.CellConstraints constraints)
    {
        int titleAlignment = isLeftToRight() ? 2 : 4;
        javax.swing.JComponent titledSeparator = getComponentFactory().createSeparator(textWithMnemonic, titleAlignment);
        add(titledSeparator, constraints);
        return titledSeparator;
    }

    public final javax.swing.JComponent addSeparator(java.lang.String textWithMnemonic, java.lang.String encodedConstraints)
    {
        return addSeparator(textWithMnemonic, new CellConstraints(encodedConstraints));
    }

    public final javax.swing.JComponent addSeparator(java.lang.String textWithMnemonic, int columnSpan)
    {
        return addSeparator(textWithMnemonic, createLeftAdjustedConstraints(columnSpan));
    }

    public final javax.swing.JLabel add(javax.swing.JLabel label, com.jgoodies.forms.layout.CellConstraints labelConstraints, java.awt.Component component, com.jgoodies.forms.layout.CellConstraints componentConstraints)
    {
        checkConstraints(labelConstraints, componentConstraints);
        add(((java.awt.Component) (label)), labelConstraints);
        add(component, componentConstraints);
        label.setLabelFor(component);
        return label;
    }

    public java.awt.Component add(java.awt.Component component, com.jgoodies.forms.layout.CellConstraints cellConstraints)
    {
        java.awt.Component result = super.add(component, cellConstraints);
        if (!isLabelForFeatureEnabled())
            return result;
        javax.swing.JLabel mostRecentlyAddedLabel = getMostRecentlyAddedLabel();
        if (mostRecentlyAddedLabel != null && isLabelForApplicable(mostRecentlyAddedLabel, component))
        {
            setLabelFor(mostRecentlyAddedLabel, component);
            clearMostRecentlyAddedLabel();
        }
        if (component instanceof javax.swing.JLabel)
            setMostRecentlyAddedLabel((javax.swing.JLabel)component);
        return result;
    }

    protected boolean isLabelForApplicable(javax.swing.JLabel label, java.awt.Component component)
    {
        if (label.getLabelFor() != null)
            return false;
        if (!component.isFocusable())
            return false;
        if (!(component instanceof javax.swing.JComponent))
        {
            return true;
        } else
        {
            javax.swing.JComponent c = (javax.swing.JComponent)component;
            return c.getClientProperty("labeledBy") == null;
        }
    }

    protected void setLabelFor(javax.swing.JLabel label, java.awt.Component component)
    {
        java.awt.Component labeledComponent;
        if (component instanceof javax.swing.JScrollPane)
        {
            javax.swing.JScrollPane scrollPane = (javax.swing.JScrollPane)component;
            labeledComponent = scrollPane.getViewport().getView();
        } else
        {
            labeledComponent = component;
        }
        label.setLabelFor(labeledComponent);
    }

    private javax.swing.JLabel getMostRecentlyAddedLabel()
    {
        if (mostRecentlyAddedLabelReference == null)
            return null;
        javax.swing.JLabel label = (javax.swing.JLabel)mostRecentlyAddedLabelReference.get();
        if (label == null)
            return null;
        else
            return label;
    }

    private void setMostRecentlyAddedLabel(javax.swing.JLabel label)
    {
        mostRecentlyAddedLabelReference = new WeakReference(label);
    }

    private void clearMostRecentlyAddedLabel()
    {
        mostRecentlyAddedLabelReference = null;
    }

    private void checkConstraints(com.jgoodies.forms.layout.CellConstraints c1, com.jgoodies.forms.layout.CellConstraints c2)
    {
        com.jgoodies.common.base.Preconditions.checkArgument(c1 != c2, "You must provide two CellConstraints instances, one for the label and one for the component.\nConsider using the CC factory. See the JavaDocs for details.");
    }

    private static final java.lang.String LABELED_BY_PROPERTY = "labeledBy";
    private static boolean labelForFeatureEnabledDefault = false;
    private boolean labelForFeatureEnabled;
    private java.lang.ref.WeakReference mostRecentlyAddedLabelReference;

}
