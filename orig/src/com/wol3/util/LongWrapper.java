// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LongWrapper.java

package com.wol3.util;


public class LongWrapper
{

    public LongWrapper(LongWrapper wrapper)
    {
        this(wrapper.value);
    }

    public LongWrapper(long value)
    {
        this.value = value;
    }

    public LongWrapper()
    {
        this(0L);
    }

    public LongWrapper set(long value)
    {
        this.value = value;
        return this;
    }

    public void set(int high, int low)
    {
        set((long)high << 32 | (long)low);
    }

    public int lowInt()
    {
        return (int)(value & -1L);
    }

    public int highInt()
    {
        return (int)(value >> 32);
    }

    public boolean equals(Object obj)
    {
        if(obj instanceof LongWrapper)
            return value == ((LongWrapper)obj).value;
        else
            return false;
    }

    public int hashCode()
    {
        return (int)(value ^ value >> 32);
    }

    public long value;
}
