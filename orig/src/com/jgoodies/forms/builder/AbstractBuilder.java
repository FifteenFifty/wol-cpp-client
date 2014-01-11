// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   AbstractBuilder.java

package com.jgoodies.forms.builder;

import com.jgoodies.common.base.Preconditions;
import com.jgoodies.forms.factories.ComponentFactory;
import com.jgoodies.forms.factories.ComponentFactory2;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;
import java.awt.Container;

public abstract class AbstractBuilder
{

    protected AbstractBuilder(com.jgoodies.forms.layout.FormLayout layout, java.awt.Container container)
    {
        this.layout = (com.jgoodies.forms.layout.FormLayout)com.jgoodies.common.base.Preconditions.checkNotNull(layout, "The layout must not be null.");
        this.container = (java.awt.Container)com.jgoodies.common.base.Preconditions.checkNotNull(container, "The layout container must not be null.");
        container.setLayout(layout);
    }

    public static com.jgoodies.forms.factories.ComponentFactory2 getDefaultComponentFactory()
    {
        if (defaultComponentFactory == null)
            defaultComponentFactory = new DefaultComponentFactory();
        return defaultComponentFactory;
    }

    public static void setDefaultComponentFactory(com.jgoodies.forms.factories.ComponentFactory2 factory)
    {
        defaultComponentFactory = factory;
    }

    public final java.awt.Container getContainer()
    {
        return container;
    }

    public final com.jgoodies.forms.layout.FormLayout getLayout()
    {
        return layout;
    }

    public final int getColumnCount()
    {
        return getLayout().getColumnCount();
    }

    public final int getRowCount()
    {
        return getLayout().getRowCount();
    }

    public final com.jgoodies.forms.factories.ComponentFactory getComponentFactory()
    {
        if (componentFactory == null)
            componentFactory = createComponentFactory();
        return componentFactory;
    }

    public final void setComponentFactory(com.jgoodies.forms.factories.ComponentFactory newFactory)
    {
        componentFactory = newFactory;
    }

    protected com.jgoodies.forms.factories.ComponentFactory createComponentFactory()
    {
        return com.jgoodies.forms.builder.AbstractBuilder.getDefaultComponentFactory();
    }

    private static com.jgoodies.forms.factories.ComponentFactory2 defaultComponentFactory;
    private final java.awt.Container container;
    private final com.jgoodies.forms.layout.FormLayout layout;
    protected final com.jgoodies.forms.layout.CellConstraints currentCellConstraints = new CellConstraints();
    private com.jgoodies.forms.factories.ComponentFactory componentFactory;
}
