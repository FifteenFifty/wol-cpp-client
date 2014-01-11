// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   AbstractUnitConverter.java

package com.jgoodies.forms.util;

import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.Toolkit;

// Referenced classes of package com.jgoodies.forms.util:
//            UnitConverter

public abstract class AbstractUnitConverter
    implements com.jgoodies.forms.util.UnitConverter
{

    public AbstractUnitConverter()
    {
    }

    public int inchAsPixel(double in, java.awt.Component component)
    {
        return inchAsPixel(in, getScreenResolution(component));
    }

    public int millimeterAsPixel(double mm, java.awt.Component component)
    {
        return millimeterAsPixel(mm, getScreenResolution(component));
    }

    public int centimeterAsPixel(double cm, java.awt.Component component)
    {
        return centimeterAsPixel(cm, getScreenResolution(component));
    }

    public int pointAsPixel(int pt, java.awt.Component component)
    {
        return pointAsPixel(pt, getScreenResolution(component));
    }

    public int dialogUnitXAsPixel(int dluX, java.awt.Component c)
    {
        return dialogUnitXAsPixel(dluX, getDialogBaseUnitsX(c));
    }

    public int dialogUnitYAsPixel(int dluY, java.awt.Component c)
    {
        return dialogUnitYAsPixel(dluY, getDialogBaseUnitsY(c));
    }

    protected abstract double getDialogBaseUnitsX(java.awt.Component component);

    protected abstract double getDialogBaseUnitsY(java.awt.Component component);

    protected final int inchAsPixel(double in, int dpi)
    {
        return (int)java.lang.Math.round((double)dpi * in);
    }

    protected final int millimeterAsPixel(double mm, int dpi)
    {
        return (int)java.lang.Math.round(((double)dpi * mm * 10D) / 254D);
    }

    protected final int centimeterAsPixel(double cm, int dpi)
    {
        return (int)java.lang.Math.round(((double)dpi * cm * 100D) / 254D);
    }

    protected final int pointAsPixel(int pt, int dpi)
    {
        return java.lang.Math.round((dpi * pt) / 72);
    }

    protected int dialogUnitXAsPixel(int dluX, double dialogBaseUnitsX)
    {
        return (int)java.lang.Math.round(((double)dluX * dialogBaseUnitsX) / 4D);
    }

    protected int dialogUnitYAsPixel(int dluY, double dialogBaseUnitsY)
    {
        return (int)java.lang.Math.round(((double)dluY * dialogBaseUnitsY) / 8D);
    }

    protected double computeAverageCharWidth(java.awt.FontMetrics metrics, java.lang.String testString)
    {
        int width = metrics.stringWidth(testString);
        double average = (double)width / (double)testString.length();
        return average;
    }

    protected int getScreenResolution(java.awt.Component c)
    {
        if (c == null)
        {
            return getDefaultScreenResolution();
        } else
        {
            java.awt.Toolkit toolkit = c.getToolkit();
            return toolkit == null ? getDefaultScreenResolution() : toolkit.getScreenResolution();
        }
    }

    protected int getDefaultScreenResolution()
    {
        if (defaultScreenResolution == -1)
            defaultScreenResolution = java.awt.Toolkit.getDefaultToolkit().getScreenResolution();
        return defaultScreenResolution;
    }

    private static final int DTP_RESOLUTION = 72;
    private static int defaultScreenResolution = -1;

}
