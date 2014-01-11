// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   CC.java

package com.jgoodies.forms.factories;

import com.jgoodies.forms.layout.CellConstraints;
import java.io.Serializable;

public final class CC
    implements java.lang.Cloneable, java.io.Serializable
{

    public CC()
    {
    }

    public static com.jgoodies.forms.layout.CellConstraints xy(int col, int row)
    {
        return com.jgoodies.forms.factories.CC.xywh(col, row, 1, 1);
    }

    public static com.jgoodies.forms.layout.CellConstraints xy(int col, int row, java.lang.String encodedAlignments)
    {
        return com.jgoodies.forms.factories.CC.xywh(col, row, 1, 1, encodedAlignments);
    }

    public static com.jgoodies.forms.layout.CellConstraints xy(int col, int row, com.jgoodies.forms.layout.CellConstraints.Alignment colAlign, com.jgoodies.forms.layout.CellConstraints.Alignment rowAlign)
    {
        return com.jgoodies.forms.factories.CC.xywh(col, row, 1, 1, colAlign, rowAlign);
    }

    public static com.jgoodies.forms.layout.CellConstraints xyw(int col, int row, int colSpan)
    {
        return com.jgoodies.forms.factories.CC.xywh(col, row, colSpan, 1, com.jgoodies.forms.layout.CellConstraints.DEFAULT, com.jgoodies.forms.layout.CellConstraints.DEFAULT);
    }

    public static com.jgoodies.forms.layout.CellConstraints xyw(int col, int row, int colSpan, java.lang.String encodedAlignments)
    {
        return com.jgoodies.forms.factories.CC.xywh(col, row, colSpan, 1, encodedAlignments);
    }

    public static com.jgoodies.forms.layout.CellConstraints xyw(int col, int row, int colSpan, com.jgoodies.forms.layout.CellConstraints.Alignment colAlign, com.jgoodies.forms.layout.CellConstraints.Alignment rowAlign)
    {
        return com.jgoodies.forms.factories.CC.xywh(col, row, colSpan, 1, colAlign, rowAlign);
    }

    public static com.jgoodies.forms.layout.CellConstraints xywh(int col, int row, int colSpan, int rowSpan)
    {
        return com.jgoodies.forms.factories.CC.xywh(col, row, colSpan, rowSpan, com.jgoodies.forms.layout.CellConstraints.DEFAULT, com.jgoodies.forms.layout.CellConstraints.DEFAULT);
    }

    public static com.jgoodies.forms.layout.CellConstraints xywh(int col, int row, int colSpan, int rowSpan, java.lang.String encodedAlignments)
    {
        return (new CellConstraints()).xywh(col, row, colSpan, rowSpan, encodedAlignments);
    }

    public static com.jgoodies.forms.layout.CellConstraints xywh(int col, int row, int colSpan, int rowSpan, com.jgoodies.forms.layout.CellConstraints.Alignment colAlign, com.jgoodies.forms.layout.CellConstraints.Alignment rowAlign)
    {
        return new CellConstraints(col, row, colSpan, rowSpan, colAlign, rowAlign);
    }

    public static com.jgoodies.forms.layout.CellConstraints rc(int row, int col)
    {
        return com.jgoodies.forms.factories.CC.rchw(row, col, 1, 1);
    }

    public static com.jgoodies.forms.layout.CellConstraints rc(int row, int col, java.lang.String encodedAlignments)
    {
        return com.jgoodies.forms.factories.CC.rchw(row, col, 1, 1, encodedAlignments);
    }

    public static com.jgoodies.forms.layout.CellConstraints rc(int row, int col, com.jgoodies.forms.layout.CellConstraints.Alignment rowAlign, com.jgoodies.forms.layout.CellConstraints.Alignment colAlign)
    {
        return com.jgoodies.forms.factories.CC.rchw(row, col, 1, 1, rowAlign, colAlign);
    }

    public static com.jgoodies.forms.layout.CellConstraints rcw(int row, int col, int colSpan)
    {
        return com.jgoodies.forms.factories.CC.rchw(row, col, 1, colSpan, com.jgoodies.forms.layout.CellConstraints.DEFAULT, com.jgoodies.forms.layout.CellConstraints.DEFAULT);
    }

    public static com.jgoodies.forms.layout.CellConstraints rcw(int row, int col, int colSpan, java.lang.String encodedAlignments)
    {
        return com.jgoodies.forms.factories.CC.rchw(row, col, 1, colSpan, encodedAlignments);
    }

    public static com.jgoodies.forms.layout.CellConstraints rcw(int row, int col, int colSpan, com.jgoodies.forms.layout.CellConstraints.Alignment rowAlign, com.jgoodies.forms.layout.CellConstraints.Alignment colAlign)
    {
        return com.jgoodies.forms.factories.CC.rchw(row, col, 1, colSpan, rowAlign, colAlign);
    }

    public static com.jgoodies.forms.layout.CellConstraints rchw(int row, int col, int rowSpan, int colSpan)
    {
        return com.jgoodies.forms.factories.CC.rchw(row, col, rowSpan, colSpan, com.jgoodies.forms.layout.CellConstraints.DEFAULT, com.jgoodies.forms.layout.CellConstraints.DEFAULT);
    }

    public static com.jgoodies.forms.layout.CellConstraints rchw(int row, int col, int rowSpan, int colSpan, java.lang.String encodedAlignments)
    {
        return (new CellConstraints()).rchw(row, col, rowSpan, colSpan, encodedAlignments);
    }

    public static com.jgoodies.forms.layout.CellConstraints rchw(int row, int col, int rowSpan, int colSpan, com.jgoodies.forms.layout.CellConstraints.Alignment rowAlign, com.jgoodies.forms.layout.CellConstraints.Alignment colAlign)
    {
        return com.jgoodies.forms.factories.CC.xywh(col, row, colSpan, rowSpan, colAlign, rowAlign);
    }

    public static final com.jgoodies.forms.layout.CellConstraints.Alignment DEFAULT;
    public static final com.jgoodies.forms.layout.CellConstraints.Alignment FILL;
    public static final com.jgoodies.forms.layout.CellConstraints.Alignment LEFT;
    public static final com.jgoodies.forms.layout.CellConstraints.Alignment RIGHT;
    public static final com.jgoodies.forms.layout.CellConstraints.Alignment CENTER;
    public static final com.jgoodies.forms.layout.CellConstraints.Alignment TOP;
    public static final com.jgoodies.forms.layout.CellConstraints.Alignment BOTTOM;

    static 
    {
        DEFAULT = com.jgoodies.forms.layout.CellConstraints.DEFAULT;
        FILL = com.jgoodies.forms.layout.CellConstraints.FILL;
        LEFT = com.jgoodies.forms.layout.CellConstraints.LEFT;
        RIGHT = com.jgoodies.forms.layout.CellConstraints.RIGHT;
        CENTER = com.jgoodies.forms.layout.CellConstraints.CENTER;
        TOP = com.jgoodies.forms.layout.CellConstraints.TOP;
        BOTTOM = com.jgoodies.forms.layout.CellConstraints.BOTTOM;
    }
}
