// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   I15dPanelBuilder.java

package com.jgoodies.forms.builder;

import com.jgoodies.common.base.Preconditions;
import com.jgoodies.forms.layout.FormLayout;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.swing.JPanel;

// Referenced classes of package com.jgoodies.forms.builder:
//            AbstractI15dPanelBuilder

public class I15dPanelBuilder extends com.jgoodies.forms.builder.AbstractI15dPanelBuilder
{

    public I15dPanelBuilder(com.jgoodies.forms.layout.FormLayout layout, java.util.ResourceBundle bundle)
    {
        this(layout, bundle, new JPanel(null));
    }

    public I15dPanelBuilder(com.jgoodies.forms.layout.FormLayout layout, java.util.ResourceBundle bundle, javax.swing.JPanel container)
    {
        super(layout, container);
        this.bundle = bundle;
    }

    protected java.lang.String getI15dString(java.lang.String resourceKey)
    {
        com.jgoodies.common.base.Preconditions.checkState(bundle != null, "To use the internationalization support a ResourceBundle must be provided during the builder construction.");
        try
        {
            return bundle.getString(resourceKey);
        }
        catch (java.util.MissingResourceException mre)
        {
            return resourceKey;
        }
    }

    private final java.util.ResourceBundle bundle;
}
