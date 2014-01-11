// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   EmptyNumberFormat.java

package com.jgoodies.common.format;

import com.jgoodies.common.base.Objects;
import com.jgoodies.common.base.Strings;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.ParsePosition;

public final class EmptyNumberFormat extends java.text.NumberFormat
{

    public EmptyNumberFormat(java.text.NumberFormat delegate)
    {
        this(delegate, ((java.lang.Number) (null)));
    }

    public EmptyNumberFormat(java.text.NumberFormat delegate, int emptyValue)
    {
        this(delegate, ((java.lang.Number) (java.lang.Integer.valueOf(emptyValue))));
    }

    public EmptyNumberFormat(java.text.NumberFormat delegate, java.lang.Number emptyValue)
    {
        _flddelegate = delegate;
        this.emptyValue = emptyValue;
    }

    public java.lang.StringBuffer format(java.lang.Object obj, java.lang.StringBuffer toAppendTo, java.text.FieldPosition pos)
    {
        return com.jgoodies.common.base.Objects.equals(obj, emptyValue) ? toAppendTo : _flddelegate.format(obj, toAppendTo, pos);
    }

    public java.lang.StringBuffer format(double number, java.lang.StringBuffer toAppendTo, java.text.FieldPosition pos)
    {
        return _flddelegate.format(number, toAppendTo, pos);
    }

    public java.lang.StringBuffer format(long number, java.lang.StringBuffer toAppendTo, java.text.FieldPosition pos)
    {
        return _flddelegate.format(number, toAppendTo, pos);
    }

    public java.lang.Object parseObject(java.lang.String source)
        throws java.text.ParseException
    {
        return com.jgoodies.common.base.Strings.isBlank(source) ? emptyValue : super.parseObject(source);
    }

    public java.lang.Number parse(java.lang.String source)
        throws java.text.ParseException
    {
        return com.jgoodies.common.base.Strings.isBlank(source) ? emptyValue : super.parse(source);
    }

    public java.lang.Number parse(java.lang.String source, java.text.ParsePosition pos)
    {
        return _flddelegate.parse(source, pos);
    }

    private final java.text.NumberFormat _flddelegate;
    private final java.lang.Number emptyValue;
}
