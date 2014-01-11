// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   StringPool.java

package com.wol3.client.data;

import java.util.ArrayList;
import java.util.HashMap;

public class StringPool
{

    public StringPool()
    {
        list = new ArrayList();
        map = new HashMap();
    }

    public int indexOf(java.lang.String s)
    {
        java.lang.Integer idx = (java.lang.Integer)map.get(s);
        if (idx == null)
        {
            idx = java.lang.Integer.valueOf(list.size());
            list.add(s);
            map.put(s, idx);
        }
        return idx.intValue();
    }

    public java.lang.String[] toArray()
    {
        return (java.lang.String[])list.toArray(new java.lang.String[list.size()]);
    }

    public java.util.ArrayList list;
    public java.util.HashMap map;
}
