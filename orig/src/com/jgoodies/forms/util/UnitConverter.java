// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   UnitConverter.java

package com.jgoodies.forms.util;

import java.awt.Component;

public interface UnitConverter
{

    public abstract int inchAsPixel(double d, java.awt.Component component);

    public abstract int millimeterAsPixel(double d, java.awt.Component component);

    public abstract int centimeterAsPixel(double d, java.awt.Component component);

    public abstract int pointAsPixel(int i, java.awt.Component component);

    public abstract int dialogUnitXAsPixel(int i, java.awt.Component component);

    public abstract int dialogUnitYAsPixel(int i, java.awt.Component component);
}
