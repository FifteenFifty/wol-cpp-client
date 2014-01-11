// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   EmptyFormat.java

package com.jgoodies.common.format;

import com.jgoodies.common.base.Objects;
import com.jgoodies.common.base.Strings;
import java.text.AttributedCharacterIterator;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;

public class EmptyFormat extends java.text.Format
{

    public EmptyFormat(java.text.Format format)
    {
        this(format, null);
    }

    public EmptyFormat(java.text.Format format, java.lang.Object emptyValue)
    {
        _flddelegate = format;
        this.emptyValue = emptyValue;
    }

    public java.lang.StringBuffer format(java.lang.Object obj, java.lang.StringBuffer toAppendTo, java.text.FieldPosition pos)
    {
        return com.jgoodies.common.base.Objects.equals(obj, emptyValue) ? toAppendTo : _flddelegate.format(obj, toAppendTo, pos);
    }

    public java.lang.Object parseObject(java.lang.String source)
        throws java.text.ParseException
    {
        return com.jgoodies.common.base.Strings.isBlank(source) ? emptyValue : super.parseObject(source);
    }

    public final java.lang.Object parseObject(java.lang.String source, java.text.ParsePosition pos)
    {
        return _flddelegate.parseObject(source, pos);
    }

    public final java.text.AttributedCharacterIterator formatToCharacterIterator(java.lang.Object obj)
    {
        return _flddelegate.formatToCharacterIterator(obj);
    }

    private final java.text.Format _flddelegate;
    private final java.lang.Object emptyValue;
}
