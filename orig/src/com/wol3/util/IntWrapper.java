// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   IntWrapper.java

package com.wol3.util;


public class IntWrapper
    implements Comparable
{

    public IntWrapper(IntWrapper wrapper)
    {
        this(wrapper.value);
    }

    public IntWrapper(int value)
    {
        this.value = value;
    }

    public IntWrapper()
    {
        this(0);
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof IntWrapper)
            return value == ((IntWrapper)obj).value;
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

    public int compareTo(IntWrapper o)
    {
        if(value < o.value)
            return -1;
        return value <= o.value ? 0 : 1;
    }

    public IntWrapper set(int value)
    {
        this.value = value;
        return this;
    }

    public volatile int compareTo(Object obj)
    {
        return compareTo((IntWrapper)obj);
    }

    public int value;
}
