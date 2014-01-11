// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   Strings.java

package com.jgoodies.common.base;


public class Strings
{

    protected Strings()
    {
    }

    public static boolean isBlank(java.lang.String str)
    {
        int length;
        if (str == null || (length = str.length()) == 0)
            return true;
        for (int i = length - 1; i >= 0; i--)
            if (!java.lang.Character.isWhitespace(str.charAt(i)))
                return false;

        return true;
    }

    public static boolean isNotBlank(java.lang.String str)
    {
        int length;
        if (str == null || (length = str.length()) == 0)
            return false;
        for (int i = length - 1; i >= 0; i--)
            if (!java.lang.Character.isWhitespace(str.charAt(i)))
                return true;

        return false;
    }

    public static boolean isEmpty(java.lang.String str)
    {
        return str == null || str.length() == 0;
    }

    public static boolean isNotEmpty(java.lang.String str)
    {
        return str != null && str.length() > 0;
    }

    public static boolean startsWithIgnoreCase(java.lang.String str, java.lang.String prefix)
    {
        if (str == null)
            return prefix == null;
        if (prefix == null)
            return false;
        else
            return str.regionMatches(true, 0, prefix, 0, prefix.length());
    }

    public static java.lang.String abbreviateCenter(java.lang.String str, int maxLength)
    {
        if (str == null)
            return null;
        int length = str.length();
        if (length <= maxLength)
        {
            return str;
        } else
        {
            int headLength = maxLength / 2;
            int tailLength = maxLength - headLength - 1;
            java.lang.String head = str.substring(0, headLength);
            java.lang.String tail = str.substring(length - tailLength, length);
            return (new StringBuilder()).append(head).append("\u2026").append(tail).toString();
        }
    }
}
