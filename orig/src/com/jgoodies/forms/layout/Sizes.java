// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   Sizes.java

package com.jgoodies.forms.layout;

import com.jgoodies.forms.util.DefaultUnitConverter;
import com.jgoodies.forms.util.UnitConverter;
import java.awt.Component;
import java.awt.Container;
import java.io.Serializable;
import java.util.Iterator;
import java.util.Locale;

// Referenced classes of package com.jgoodies.forms.layout:
//            ConstantSize, BoundedSize, Size, FormLayout

public final class Sizes
{
    static final class ComponentSize
        implements com.jgoodies.forms.layout.Size, java.io.Serializable
    {

        static com.jgoodies.forms.layout.ComponentSize valueOf(java.lang.String str)
        {
            if (str.equals("m") || str.equals("min"))
                return com.jgoodies.forms.layout.Sizes.MINIMUM;
            if (str.equals("p") || str.equals("pref"))
                return com.jgoodies.forms.layout.Sizes.PREFERRED;
            if (str.equals("d") || str.equals("default"))
                return com.jgoodies.forms.layout.Sizes.DEFAULT;
            else
                return null;
        }

        public int maximumSize(java.awt.Container container, java.util.List components, com.jgoodies.forms.layout.FormLayout.Measure minMeasure, com.jgoodies.forms.layout.FormLayout.Measure prefMeasure, com.jgoodies.forms.layout.FormLayout.Measure defaultMeasure)
        {
            com.jgoodies.forms.layout.FormLayout.Measure measure = this != com.jgoodies.forms.layout.Sizes.MINIMUM ? this != com.jgoodies.forms.layout.Sizes.PREFERRED ? defaultMeasure : prefMeasure : minMeasure;
            int maximum = 0;
            for (java.util.Iterator i = components.iterator(); i.hasNext();)
            {
                java.awt.Component c = (java.awt.Component)i.next();
                maximum = java.lang.Math.max(maximum, measure.sizeOf(c));
            }

            return maximum;
        }

        public boolean compressible()
        {
            return this == com.jgoodies.forms.layout.Sizes.DEFAULT;
        }

        public java.lang.String toString()
        {
            return encode();
        }

        public java.lang.String encode()
        {
            return name.substring(0, 1);
        }

        private java.lang.Object readResolve()
        {
            return com.jgoodies.forms.layout.Sizes.VALUES[ordinal];
        }

        private final transient java.lang.String name;
        private static int nextOrdinal = 0;
        private final int ordinal;


        private ComponentSize(java.lang.String name)
        {
            ordinal = nextOrdinal++;
            this.name = name;
        }

    }


    private Sizes()
    {
    }

    public static com.jgoodies.forms.layout.ConstantSize constant(java.lang.String encodedValueAndUnit, boolean horizontal)
    {
        java.lang.String lowerCase = encodedValueAndUnit.toLowerCase(java.util.Locale.ENGLISH);
        java.lang.String trimmed = lowerCase.trim();
        return com.jgoodies.forms.layout.ConstantSize.valueOf(trimmed, horizontal);
    }

    public static com.jgoodies.forms.layout.ConstantSize dluX(int value)
    {
        return com.jgoodies.forms.layout.ConstantSize.dluX(value);
    }

    public static com.jgoodies.forms.layout.ConstantSize dluY(int value)
    {
        return com.jgoodies.forms.layout.ConstantSize.dluY(value);
    }

    public static com.jgoodies.forms.layout.ConstantSize pixel(int value)
    {
        return new ConstantSize(value, com.jgoodies.forms.layout.ConstantSize.PIXEL);
    }

    public static com.jgoodies.forms.layout.Size bounded(com.jgoodies.forms.layout.Size basis, com.jgoodies.forms.layout.Size lowerBound, com.jgoodies.forms.layout.Size upperBound)
    {
        return new BoundedSize(basis, lowerBound, upperBound);
    }

    public static int inchAsPixel(double in, java.awt.Component component)
    {
        return in != 0.0D ? com.jgoodies.forms.layout.Sizes.getUnitConverter().inchAsPixel(in, component) : 0;
    }

    public static int millimeterAsPixel(double mm, java.awt.Component component)
    {
        return mm != 0.0D ? com.jgoodies.forms.layout.Sizes.getUnitConverter().millimeterAsPixel(mm, component) : 0;
    }

    public static int centimeterAsPixel(double cm, java.awt.Component component)
    {
        return cm != 0.0D ? com.jgoodies.forms.layout.Sizes.getUnitConverter().centimeterAsPixel(cm, component) : 0;
    }

    public static int pointAsPixel(int pt, java.awt.Component component)
    {
        return pt != 0 ? com.jgoodies.forms.layout.Sizes.getUnitConverter().pointAsPixel(pt, component) : 0;
    }

    public static int dialogUnitXAsPixel(int dluX, java.awt.Component component)
    {
        return dluX != 0 ? com.jgoodies.forms.layout.Sizes.getUnitConverter().dialogUnitXAsPixel(dluX, component) : 0;
    }

    public static int dialogUnitYAsPixel(int dluY, java.awt.Component component)
    {
        return dluY != 0 ? com.jgoodies.forms.layout.Sizes.getUnitConverter().dialogUnitYAsPixel(dluY, component) : 0;
    }

    public static com.jgoodies.forms.util.UnitConverter getUnitConverter()
    {
        if (unitConverter == null)
            unitConverter = com.jgoodies.forms.util.DefaultUnitConverter.getInstance();
        return unitConverter;
    }

    public static void setUnitConverter(com.jgoodies.forms.util.UnitConverter newUnitConverter)
    {
        unitConverter = newUnitConverter;
    }

    public static com.jgoodies.forms.layout.ConstantSize.Unit getDefaultUnit()
    {
        return defaultUnit;
    }

    public static void setDefaultUnit(com.jgoodies.forms.layout.ConstantSize.Unit unit)
    {
        if (unit == com.jgoodies.forms.layout.ConstantSize.DLUX || unit == com.jgoodies.forms.layout.ConstantSize.DLUY)
        {
            throw new IllegalArgumentException("The unit must not be DLUX or DLUY. To use DLU as default unit, invoke this method with null.");
        } else
        {
            defaultUnit = unit;
            return;
        }
    }

    public static final com.jgoodies.forms.layout.ConstantSize ZERO = com.jgoodies.forms.layout.Sizes.pixel(0);
    public static final com.jgoodies.forms.layout.ConstantSize DLUX1 = com.jgoodies.forms.layout.Sizes.dluX(1);
    public static final com.jgoodies.forms.layout.ConstantSize DLUX2 = com.jgoodies.forms.layout.Sizes.dluX(2);
    public static final com.jgoodies.forms.layout.ConstantSize DLUX3 = com.jgoodies.forms.layout.Sizes.dluX(3);
    public static final com.jgoodies.forms.layout.ConstantSize DLUX4 = com.jgoodies.forms.layout.Sizes.dluX(4);
    public static final com.jgoodies.forms.layout.ConstantSize DLUX5 = com.jgoodies.forms.layout.Sizes.dluX(5);
    public static final com.jgoodies.forms.layout.ConstantSize DLUX6 = com.jgoodies.forms.layout.Sizes.dluX(6);
    public static final com.jgoodies.forms.layout.ConstantSize DLUX7 = com.jgoodies.forms.layout.Sizes.dluX(7);
    public static final com.jgoodies.forms.layout.ConstantSize DLUX8 = com.jgoodies.forms.layout.Sizes.dluX(8);
    public static final com.jgoodies.forms.layout.ConstantSize DLUX9 = com.jgoodies.forms.layout.Sizes.dluX(9);
    public static final com.jgoodies.forms.layout.ConstantSize DLUX11 = com.jgoodies.forms.layout.Sizes.dluX(11);
    public static final com.jgoodies.forms.layout.ConstantSize DLUX14 = com.jgoodies.forms.layout.Sizes.dluX(14);
    public static final com.jgoodies.forms.layout.ConstantSize DLUX21 = com.jgoodies.forms.layout.Sizes.dluX(21);
    public static final com.jgoodies.forms.layout.ConstantSize DLUY1 = com.jgoodies.forms.layout.Sizes.dluY(1);
    public static final com.jgoodies.forms.layout.ConstantSize DLUY2 = com.jgoodies.forms.layout.Sizes.dluY(2);
    public static final com.jgoodies.forms.layout.ConstantSize DLUY3 = com.jgoodies.forms.layout.Sizes.dluY(3);
    public static final com.jgoodies.forms.layout.ConstantSize DLUY4 = com.jgoodies.forms.layout.Sizes.dluY(4);
    public static final com.jgoodies.forms.layout.ConstantSize DLUY5 = com.jgoodies.forms.layout.Sizes.dluY(5);
    public static final com.jgoodies.forms.layout.ConstantSize DLUY6 = com.jgoodies.forms.layout.Sizes.dluY(6);
    public static final com.jgoodies.forms.layout.ConstantSize DLUY7 = com.jgoodies.forms.layout.Sizes.dluY(7);
    public static final com.jgoodies.forms.layout.ConstantSize DLUY8 = com.jgoodies.forms.layout.Sizes.dluY(8);
    public static final com.jgoodies.forms.layout.ConstantSize DLUY9 = com.jgoodies.forms.layout.Sizes.dluY(9);
    public static final com.jgoodies.forms.layout.ConstantSize DLUY11 = com.jgoodies.forms.layout.Sizes.dluY(11);
    public static final com.jgoodies.forms.layout.ConstantSize DLUY14 = com.jgoodies.forms.layout.Sizes.dluY(14);
    public static final com.jgoodies.forms.layout.ConstantSize DLUY21 = com.jgoodies.forms.layout.Sizes.dluY(21);
    public static final com.jgoodies.forms.layout.ComponentSize MINIMUM;
    public static final com.jgoodies.forms.layout.ComponentSize PREFERRED;
    public static final com.jgoodies.forms.layout.ComponentSize DEFAULT;
    private static final com.jgoodies.forms.layout.ComponentSize VALUES[];
    private static com.jgoodies.forms.util.UnitConverter unitConverter;
    private static com.jgoodies.forms.layout.ConstantSize.Unit defaultUnit;

    static 
    {
        MINIMUM = new ComponentSize("minimum");
        PREFERRED = new ComponentSize("preferred");
        DEFAULT = new ComponentSize("default");
        VALUES = (new com.jgoodies.forms.layout.ComponentSize[] {
            MINIMUM, PREFERRED, DEFAULT
        });
        defaultUnit = com.jgoodies.forms.layout.ConstantSize.PIXEL;
    }

}
