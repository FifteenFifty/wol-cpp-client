// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   Event.java

package com.wol3.client.data;

import com.wol3.util.ParseUtil;
import java.util.HashMap;

public class Event
{

    public Event(int id, java.lang.String type, java.lang.String data)
    {
        this.id = id;
        this.type = type;
        this.data = data;
        parseFields();
    }

    protected void parseFields()
    {
        if (data != null)
            synchronized (com/wol3/client/data/Event)
            {
                int fieldCount = com.wol3.util.ParseUtil.splitCSV(data, splitBuffer);
                fields = new java.lang.Object[fieldCount];
                for (int i = 0; i < fieldCount; i++)
                    fields[i] = com.wol3.client.data.Event.parseField(splitBuffer[i]);

            }
    }

    public static java.lang.Object parseField(java.lang.String f)
    {
        if (f.equals(""))
            return null;
        if (f.equals("nil"))
            return null;
        if (f.charAt(0) == '"' && f.charAt(f.length() - 1) == '"')
            return f.substring(1, f.length() - 1).intern();
        try
        {
            java.lang.Integer i;
            if (f.startsWith("0x"))
                i = java.lang.Integer.valueOf(f.substring(2), 16);
            else
                i = java.lang.Integer.valueOf(f);
            if (!integers.containsKey(i))
                integers.put(i, i);
            else
                i = (java.lang.Integer)integers.get(i);
            return i;
        }
        catch (java.lang.NumberFormatException e)
        {
            return f;
        }
    }

    public java.lang.String toString()
    {
        java.lang.StringBuilder b = new StringBuilder();
        b.append((new StringBuilder("<Event ")).append(id).append(", ").append(type).append(", [").toString());
        if (fields.length > 0)
        {
            b.append(fields[0]);
            for (int i = 1; i < fields.length; i++)
            {
                b.append(", ");
                b.append(fields[i]);
            }

        }
        b.append("]>");
        return b.toString();
    }

    public static void finish()
    {
        integers = new HashMap();
    }

    private static java.lang.String splitBuffer[] = new java.lang.String[32];
    public int id;
    public java.lang.String type;
    public java.lang.String data;
    public java.lang.Object fields[];
    private static java.util.HashMap integers = new HashMap();

}
