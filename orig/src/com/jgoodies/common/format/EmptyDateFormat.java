// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   EmptyDateFormat.java

package com.jgoodies.common.format;

import com.jgoodies.common.base.Objects;
import com.jgoodies.common.base.Strings;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.util.Date;

// Referenced classes of package com.jgoodies.common.format:
//            AbstractWrappedDateFormat

public final class EmptyDateFormat extends com.jgoodies.common.format.AbstractWrappedDateFormat
{

    public EmptyDateFormat(java.text.DateFormat delegate)
    {
        this(delegate, null);
    }

    public EmptyDateFormat(java.text.DateFormat delegate, java.util.Date emptyValue)
    {
        super(delegate);
        this.emptyValue = emptyValue;
    }

    public java.lang.StringBuffer format(java.util.Date date, java.lang.StringBuffer toAppendTo, java.text.FieldPosition pos)
    {
        return com.jgoodies.common.base.Objects.equals(date, emptyValue) ? toAppendTo : _flddelegate.format(date, toAppendTo, pos);
    }

    public java.util.Date parse(java.lang.String source, java.text.ParsePosition pos)
    {
        if (com.jgoodies.common.base.Strings.isBlank(source))
        {
            pos.setIndex(1);
            return emptyValue;
        } else
        {
            return _flddelegate.parse(source, pos);
        }
    }

    private final java.util.Date emptyValue;
}
