// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   BoundedSize.java

package com.jgoodies.forms.layout;

import com.jgoodies.common.base.Preconditions;
import java.awt.Container;
import java.io.Serializable;

// Referenced classes of package com.jgoodies.forms.layout:
//            Size, FormLayout

public final class BoundedSize
    implements com.jgoodies.forms.layout.Size, java.io.Serializable
{

    public BoundedSize(com.jgoodies.forms.layout.Size basis, com.jgoodies.forms.layout.Size lowerBound, com.jgoodies.forms.layout.Size upperBound)
    {
        this.basis = (com.jgoodies.forms.layout.Size)com.jgoodies.common.base.Preconditions.checkNotNull(basis, "The basis must not be null.");
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        if (lowerBound == null && upperBound == null)
            throw new IllegalArgumentException("A bounded size must have a non-null lower or upper bound.");
        else
            return;
    }

    public com.jgoodies.forms.layout.Size getBasis()
    {
        return basis;
    }

    public com.jgoodies.forms.layout.Size getLowerBound()
    {
        return lowerBound;
    }

    public com.jgoodies.forms.layout.Size getUpperBound()
    {
        return upperBound;
    }

    public int maximumSize(java.awt.Container container, java.util.List components, com.jgoodies.forms.layout.FormLayout.Measure minMeasure, com.jgoodies.forms.layout.FormLayout.Measure prefMeasure, com.jgoodies.forms.layout.FormLayout.Measure defaultMeasure)
    {
        int size = basis.maximumSize(container, components, minMeasure, prefMeasure, defaultMeasure);
        if (lowerBound != null)
            size = java.lang.Math.max(size, lowerBound.maximumSize(container, components, minMeasure, prefMeasure, defaultMeasure));
        if (upperBound != null)
            size = java.lang.Math.min(size, upperBound.maximumSize(container, components, minMeasure, prefMeasure, defaultMeasure));
        return size;
    }

    public boolean compressible()
    {
        return getBasis().compressible();
    }

    public boolean equals(java.lang.Object object)
    {
        if (this == object)
            return true;
        if (!(object instanceof com.jgoodies.forms.layout.BoundedSize))
        {
            return false;
        } else
        {
            com.jgoodies.forms.layout.BoundedSize size = (com.jgoodies.forms.layout.BoundedSize)object;
            return basis.equals(size.basis) && (lowerBound == null && size.lowerBound == null || lowerBound != null && lowerBound.equals(size.lowerBound)) && (upperBound == null && size.upperBound == null || upperBound != null && upperBound.equals(size.upperBound));
        }
    }

    public int hashCode()
    {
        int hashValue = basis.hashCode();
        if (lowerBound != null)
            hashValue = hashValue * 37 + lowerBound.hashCode();
        if (upperBound != null)
            hashValue = hashValue * 37 + upperBound.hashCode();
        return hashValue;
    }

    public java.lang.String toString()
    {
        return encode();
    }

    public java.lang.String encode()
    {
        java.lang.StringBuffer buffer = new StringBuffer("[");
        if (lowerBound != null)
        {
            buffer.append(lowerBound.encode());
            buffer.append(',');
        }
        buffer.append(basis.encode());
        if (upperBound != null)
        {
            buffer.append(',');
            buffer.append(upperBound.encode());
        }
        buffer.append(']');
        return buffer.toString();
    }

    private final com.jgoodies.forms.layout.Size basis;
    private final com.jgoodies.forms.layout.Size lowerBound;
    private final com.jgoodies.forms.layout.Size upperBound;
}
