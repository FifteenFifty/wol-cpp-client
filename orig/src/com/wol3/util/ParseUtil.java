// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ParseUtil.java

package com.wol3.util;

import java.io.PrintStream;

public class ParseUtil
{

    public ParseUtil()
    {
    }

    public static String[] splitByComma(String s)
    {
        return splitByChar(s, ',');
    }

    public static String[] splitByTab(String s)
    {
        return splitByChar(s, '\t');
    }

    public static String[] splitByChar(String s, char c)
    {
        String buffer[] = new String[Math.min(s.length(), 32)];
        int offset = 0;
        int segment = 0;
        for(int nextOffset = s.indexOf(c); nextOffset != -1; nextOffset = s.indexOf(c, offset))
        {
            buffer[segment++] = s.substring(offset, nextOffset);
            offset = nextOffset + 1;
        }

        if(offset <= s.length())
            buffer[segment++] = s.substring(offset, s.length());
        String rs[] = new String[segment];
        System.arraycopy(buffer, 0, rs, 0, segment);
        return rs;
    }

    public static String[] splitCSV(String s)
    {
        String buffer[] = new String[32];
        int segment = 0;
        int len = s.length();
        int segmentStart = 0;
        boolean inQuotes = false;
        int offset;
        for(offset = 0; offset < len; offset++)
        {
            char c = s.charAt(offset);
            if(c == '"')
                inQuotes = !inQuotes;
            if(c == ',' && !inQuotes)
            {
                buffer[segment++] = s.substring(segmentStart, offset);
                segmentStart = offset + 1;
            }
        }

        if(segmentStart < len)
            buffer[segment] = s.substring(segmentStart, offset);
        else
            segment--;
        String rs[] = new String[segment + 1];
        System.arraycopy(buffer, 0, rs, 0, segment + 1);
        return rs;
    }

    public static int splitCSV(String s, String buffer[])
    {
        int segment = 0;
        int len = s.length();
        int segmentStart = 0;
        boolean inQuotes = false;
        int offset;
        for(offset = 0; offset < len; offset++)
        {
            char c = s.charAt(offset);
            if(c == '"')
                inQuotes = !inQuotes;
            if(c == ',' && !inQuotes)
            {
                buffer[segment++] = s.substring(segmentStart, offset);
                segmentStart = offset + 1;
            }
        }

        if(segmentStart < len)
            buffer[segment] = s.substring(segmentStart, offset);
        else
            segment--;
        return segment + 1;
    }

    public static int parseHexInt(String s)
    {
        int offset = !s.startsWith("0x") && !s.startsWith("0X") ? 0 : 2;
        int total = 0;
        int l = s.length();
        int val;
        for(int idx = offset; idx < l - 1; idx++)
        {
            val = hexDict[s.charAt(idx)];
            total += val;
            total <<= 4;
        }

        val = hexDict[s.charAt(l - 1)];
        total += val;
        return total;
    }

    public static long parseHexLong(String s)
    {
        int offset = !s.startsWith("0x") && !s.startsWith("0X") ? 0 : 2;
        long total = 0L;
        int l = s.length();
        int val;
        for(int idx = offset; idx < l - 1; idx++)
        {
            val = hexDict[s.charAt(idx)];
            total += val;
            total <<= 4;
        }

        val = hexDict[s.charAt(l - 1)];
        total += val;
        return total;
    }

    public static long toLong(Object o)
    {
        if(o instanceof Byte)
            return ((Byte)o).longValue();
        if(o instanceof Short)
            return ((Short)o).longValue();
        if(o instanceof Integer)
            return ((Integer)o).longValue();
        else
            return ((Long)o).longValue();
    }

    public static int toInt(Object o)
    {
        if(o instanceof Byte)
            return ((Byte)o).intValue();
        if(o instanceof Short)
            return ((Short)o).intValue();
        if(o instanceof Integer)
            return ((Integer)o).intValue();
        Long l = (Long)o;
        if(l.longValue() >= 0xffffffff80000000L && l.longValue() <= 0x7fffffffL)
        {
            System.err.println((new StringBuilder("Error: an int is saved as long! (")).append(l).append(")").toString());
            return l.intValue();
        } else
        {
            throw new RuntimeException((new StringBuilder("Value cannot be rendered as integer: ")).append(l).toString());
        }
    }

    private static int hexDict[];

    static 
    {
        hexDict = new int[256];
        for(int i = 48; i <= 57; i++)
            hexDict[i] = i - 48;

        for(int i = 97; i <= 102; i++)
            hexDict[i] = (i - 97) + 10;

        for(int i = 65; i <= 70; i++)
            hexDict[i] = (i - 65) + 10;

    }
}
