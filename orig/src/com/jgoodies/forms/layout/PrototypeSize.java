// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   PrototypeSize.java

package com.jgoodies.forms.layout;

import com.jgoodies.forms.util.DefaultUnitConverter;
import java.awt.Container;
import java.awt.FontMetrics;
import java.io.Serializable;

// Referenced classes of package com.jgoodies.forms.layout:
//            Size, FormLayout

public final class PrototypeSize
    implements com.jgoodies.forms.layout.Size, java.io.Serializable
{

    public PrototypeSize(java.lang.String prototype)
    {
        this.prototype = prototype;
    }

    public java.lang.String getPrototype()
    {
        return prototype;
    }

    public int maximumSize(java.awt.Container container, java.util.List components, com.jgoodies.forms.layout.FormLayout.Measure minMeasure, com.jgoodies.forms.layout.FormLayout.Measure prefMeasure, com.jgoodies.forms.layout.FormLayout.Measure defaultMeasure)
    {
        java.awt.Font font = com.jgoodies.forms.util.DefaultUnitConverter.getInstance().getDefaultDialogFont();
        java.awt.FontMetrics fm = container.getFontMetrics(font);
        return fm.stringWidth(getPrototype());
    }

    public boolean compressible()
    {
        return false;
    }

    public java.lang.String encode()
    {
        return (new StringBuilder()).append("'").append(prototype).append("'").toString();
    }

    public boolean equals(java.lang.Object o)
    {
        if (this == o)
            return true;
        if (!(o instanceof com.jgoodies.forms.layout.PrototypeSize))
        {
            return false;
        } else
        {
            com.jgoodies.forms.layout.PrototypeSize size = (com.jgoodies.forms.layout.PrototypeSize)o;
            return prototype.equals(size.prototype);
        }
    }

    public int hashCode()
    {
        return prototype.hashCode();
    }

    public java.lang.String toString()
    {
        return encode();
    }

    private final java.lang.String prototype;
}
