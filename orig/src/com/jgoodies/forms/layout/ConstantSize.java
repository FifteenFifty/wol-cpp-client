// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   ConstantSize.java

package com.jgoodies.forms.layout;

import com.jgoodies.common.base.Preconditions;
import java.awt.Component;
import java.awt.Container;
import java.io.Serializable;

// Referenced classes of package com.jgoodies.forms.layout:
//            Size, Sizes, FormLayout

public final class ConstantSize
    implements com.jgoodies.forms.layout.Size, java.io.Serializable
{
    public static final class Unit
        implements java.io.Serializable
    {

        static com.jgoodies.forms.layout.Unit valueOf(java.lang.String name, boolean horizontal)
        {
            if (name.length() == 0)
            {
                com.jgoodies.forms.layout.Unit defaultUnit = com.jgoodies.forms.layout.Sizes.getDefaultUnit();
                if (defaultUnit != null)
                    return defaultUnit;
                else
                    return horizontal ? com.jgoodies.forms.layout.ConstantSize.DIALOG_UNITS_X : com.jgoodies.forms.layout.ConstantSize.DIALOG_UNITS_Y;
            }
            if (name.equals("px"))
                return com.jgoodies.forms.layout.ConstantSize.PIXEL;
            if (name.equals("dlu"))
                return horizontal ? com.jgoodies.forms.layout.ConstantSize.DIALOG_UNITS_X : com.jgoodies.forms.layout.ConstantSize.DIALOG_UNITS_Y;
            if (name.equals("pt"))
                return com.jgoodies.forms.layout.ConstantSize.POINT;
            if (name.equals("in"))
                return com.jgoodies.forms.layout.ConstantSize.INCH;
            if (name.equals("mm"))
                return com.jgoodies.forms.layout.ConstantSize.MILLIMETER;
            if (name.equals("cm"))
                return com.jgoodies.forms.layout.ConstantSize.CENTIMETER;
            else
                throw new IllegalArgumentException((new StringBuilder()).append("Invalid unit name '").append(name).append("'. Must be one of: ").append("px, dlu, pt, mm, cm, in").toString());
        }

        public java.lang.String toString()
        {
            return name;
        }

        public java.lang.String encode()
        {
            return parseAbbreviation == null ? abbreviation : parseAbbreviation;
        }

        public java.lang.String abbreviation()
        {
            return abbreviation;
        }

        private java.lang.Object readResolve()
        {
            return com.jgoodies.forms.layout.ConstantSize.VALUES[ordinal];
        }

        private final transient java.lang.String name;
        private final transient java.lang.String abbreviation;
        private final transient java.lang.String parseAbbreviation;
        final transient boolean requiresIntegers;
        private static int nextOrdinal = 0;
        private final int ordinal;


        private Unit(java.lang.String name, java.lang.String abbreviation, java.lang.String parseAbbreviation, boolean requiresIntegers)
        {
            ordinal = nextOrdinal++;
            this.name = name;
            this.abbreviation = abbreviation;
            this.parseAbbreviation = parseAbbreviation;
            this.requiresIntegers = requiresIntegers;
        }

    }


    public ConstantSize(int value, com.jgoodies.forms.layout.Unit unit)
    {
        this.value = value;
        this.unit = unit;
    }

    public ConstantSize(double value, com.jgoodies.forms.layout.Unit unit)
    {
        this.value = value;
        this.unit = unit;
    }

    static com.jgoodies.forms.layout.ConstantSize valueOf(java.lang.String encodedValueAndUnit, boolean horizontal)
    {
        java.lang.String split[] = com.jgoodies.forms.layout.ConstantSize.splitValueAndUnit(encodedValueAndUnit);
        java.lang.String encodedValue = split[0];
        java.lang.String encodedUnit = split[1];
        com.jgoodies.forms.layout.Unit unit = com.jgoodies.forms.layout.Unit.valueOf(encodedUnit, horizontal);
        double value = java.lang.Double.parseDouble(encodedValue);
        if (unit.requiresIntegers)
            com.jgoodies.common.base.Preconditions.checkArgument(value == (double)(int)value, (new StringBuilder()).append(unit.toString()).append(" value ").append(encodedValue).append(" must be an integer.").toString());
        return new ConstantSize(value, unit);
    }

    static com.jgoodies.forms.layout.ConstantSize dluX(int value)
    {
        return new ConstantSize(value, DLUX);
    }

    static com.jgoodies.forms.layout.ConstantSize dluY(int value)
    {
        return new ConstantSize(value, DLUY);
    }

    public double getValue()
    {
        return value;
    }

    public com.jgoodies.forms.layout.Unit getUnit()
    {
        return unit;
    }

    public int getPixelSize(java.awt.Component component)
    {
        if (unit == PIXEL)
            return intValue();
        if (unit == POINT)
            return com.jgoodies.forms.layout.Sizes.pointAsPixel(intValue(), component);
        if (unit == INCH)
            return com.jgoodies.forms.layout.Sizes.inchAsPixel(value, component);
        if (unit == MILLIMETER)
            return com.jgoodies.forms.layout.Sizes.millimeterAsPixel(value, component);
        if (unit == CENTIMETER)
            return com.jgoodies.forms.layout.Sizes.centimeterAsPixel(value, component);
        if (unit == DIALOG_UNITS_X)
            return com.jgoodies.forms.layout.Sizes.dialogUnitXAsPixel(intValue(), component);
        if (unit == DIALOG_UNITS_Y)
            return com.jgoodies.forms.layout.Sizes.dialogUnitYAsPixel(intValue(), component);
        else
            throw new IllegalStateException((new StringBuilder()).append("Invalid unit ").append(unit).toString());
    }

    public int maximumSize(java.awt.Container container, java.util.List components, com.jgoodies.forms.layout.FormLayout.Measure minMeasure, com.jgoodies.forms.layout.FormLayout.Measure prefMeasure, com.jgoodies.forms.layout.FormLayout.Measure defaultMeasure)
    {
        return getPixelSize(container);
    }

    public boolean compressible()
    {
        return false;
    }

    public boolean equals(java.lang.Object o)
    {
        if (this == o)
            return true;
        if (!(o instanceof com.jgoodies.forms.layout.ConstantSize))
        {
            return false;
        } else
        {
            com.jgoodies.forms.layout.ConstantSize size = (com.jgoodies.forms.layout.ConstantSize)o;
            return value == size.value && unit == size.unit;
        }
    }

    public int hashCode()
    {
        return (new Double(value)).hashCode() + 37 * unit.hashCode();
    }

    public java.lang.String toString()
    {
        return value != (double)intValue() ? (new StringBuilder()).append(java.lang.Double.toString(value)).append(unit.abbreviation()).toString() : (new StringBuilder()).append(java.lang.Integer.toString(intValue())).append(unit.abbreviation()).toString();
    }

    public java.lang.String encode()
    {
        return value != (double)intValue() ? (new StringBuilder()).append(java.lang.Double.toString(value)).append(unit.encode()).toString() : (new StringBuilder()).append(java.lang.Integer.toString(intValue())).append(unit.encode()).toString();
    }

    private int intValue()
    {
        return (int)java.lang.Math.round(value);
    }

    private static java.lang.String[] splitValueAndUnit(java.lang.String encodedValueAndUnit)
    {
        java.lang.String result[] = new java.lang.String[2];
        int len = encodedValueAndUnit.length();
        int firstLetterIndex;
        for (firstLetterIndex = len; firstLetterIndex > 0 && java.lang.Character.isLetter(encodedValueAndUnit.charAt(firstLetterIndex - 1)); firstLetterIndex--);
        result[0] = encodedValueAndUnit.substring(0, firstLetterIndex);
        result[1] = encodedValueAndUnit.substring(firstLetterIndex);
        return result;
    }

    public static final com.jgoodies.forms.layout.Unit PIXEL;
    public static final com.jgoodies.forms.layout.Unit POINT;
    public static final com.jgoodies.forms.layout.Unit DIALOG_UNITS_X;
    public static final com.jgoodies.forms.layout.Unit DIALOG_UNITS_Y;
    public static final com.jgoodies.forms.layout.Unit MILLIMETER;
    public static final com.jgoodies.forms.layout.Unit CENTIMETER;
    public static final com.jgoodies.forms.layout.Unit INCH;
    public static final com.jgoodies.forms.layout.Unit PX;
    public static final com.jgoodies.forms.layout.Unit PT;
    public static final com.jgoodies.forms.layout.Unit DLUX;
    public static final com.jgoodies.forms.layout.Unit DLUY;
    public static final com.jgoodies.forms.layout.Unit MM;
    public static final com.jgoodies.forms.layout.Unit CM;
    public static final com.jgoodies.forms.layout.Unit IN;
    private static final com.jgoodies.forms.layout.Unit VALUES[];
    private final double value;
    private final com.jgoodies.forms.layout.Unit unit;

    static 
    {
        PIXEL = new Unit("Pixel", "px", null, true);
        POINT = new Unit("Point", "pt", null, true);
        DIALOG_UNITS_X = new Unit("Dialog units X", "dluX", "dlu", true);
        DIALOG_UNITS_Y = new Unit("Dialog units Y", "dluY", "dlu", true);
        MILLIMETER = new Unit("Millimeter", "mm", null, false);
        CENTIMETER = new Unit("Centimeter", "cm", null, false);
        INCH = new Unit("Inch", "in", null, false);
        PX = PIXEL;
        PT = POINT;
        DLUX = DIALOG_UNITS_X;
        DLUY = DIALOG_UNITS_Y;
        MM = MILLIMETER;
        CM = CENTIMETER;
        IN = INCH;
        VALUES = (new com.jgoodies.forms.layout.Unit[] {
            PIXEL, POINT, DIALOG_UNITS_X, DIALOG_UNITS_Y, MILLIMETER, CENTIMETER, INCH
        });
    }

}
