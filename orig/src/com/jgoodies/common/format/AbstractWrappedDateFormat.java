// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   AbstractWrappedDateFormat.java

package com.jgoodies.common.format;

import java.text.AttributedCharacterIterator;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public abstract class AbstractWrappedDateFormat extends java.text.DateFormat
{

    public AbstractWrappedDateFormat(java.text.DateFormat delegate)
    {
        _flddelegate = delegate;
    }

    public abstract java.lang.StringBuffer format(java.util.Date date, java.lang.StringBuffer stringbuffer, java.text.FieldPosition fieldposition);

    public abstract java.util.Date parse(java.lang.String s, java.text.ParsePosition parseposition);

    public java.util.Calendar getCalendar()
    {
        return _flddelegate.getCalendar();
    }

    public void setCalendar(java.util.Calendar newCalendar)
    {
        _flddelegate.setCalendar(newCalendar);
    }

    public java.text.NumberFormat getNumberFormat()
    {
        return _flddelegate.getNumberFormat();
    }

    public void setNumberFormat(java.text.NumberFormat newNumberFormat)
    {
        _flddelegate.setNumberFormat(newNumberFormat);
    }

    public java.util.TimeZone getTimeZone()
    {
        return _flddelegate.getTimeZone();
    }

    public void setTimeZone(java.util.TimeZone zone)
    {
        _flddelegate.setTimeZone(zone);
    }

    public boolean isLenient()
    {
        return _flddelegate.isLenient();
    }

    public void setLenient(boolean lenient)
    {
        _flddelegate.setLenient(lenient);
    }

    public java.text.AttributedCharacterIterator formatToCharacterIterator(java.lang.Object obj)
    {
        return _flddelegate.formatToCharacterIterator(obj);
    }

    protected final java.text.DateFormat _flddelegate;
}
