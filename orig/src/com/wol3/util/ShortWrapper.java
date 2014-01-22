// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ShortWrapper.java

package com.wol3.util;


public class ShortWrapper
    implements Comparable
{

    public ShortWrapper(ShortWrapper wrapper)
    {
        this(wrapper.value);
    }

    public ShortWrapper(short value)
    {
        setValue(value);
    }

    public short getValue()
    {
        return value;
    }

    public void setValue(short value)
    {
        this.value = value;
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof ShortWrapper)
            return value == ((ShortWrapper)obj).value;
        else
            return false;
    }

    public int hashCode()
    {
        return value;
    }

    public String toString()
    {
        return String.valueOf(value);
    }

    public int compareTo(ShortWrapper o)
    {
        return getValue() - o.getValue();
    }

    public volatile int compareTo(Object obj)
    {
        return compareTo((ShortWrapper)obj);
    }

    protected short value;
}
