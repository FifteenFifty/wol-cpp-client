// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   StringReader.java

package com.wol3.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringReader
{
    public static class InvalidStringException extends RuntimeException
    {

        private static final long serialVersionUID = 1L;

        private InvalidStringException(String message)
        {
            super(message);
        }

        InvalidStringException(String s, InvalidStringException invalidstringexception)
        {
            this(s);
        }
    }


    public StringReader()
    {
        remainingBuffer = new StringBuilder();
        out = new StringBuilder();
    }

    public void setString(String s)
    {
        string = s;
        len = s.length();
        offset = 0;
        remaining = null;
        subjectInfo = null;
    }

    public int available()
    {
        return len - offset;
    }

    public String getRemaining()
    {
        if(remaining != null)
        {
            return remaining;
        } else
        {
            initSubjectInfo();
            return remaining;
        }
    }

    public String getSubjectInfo()
    {
        if(remaining != null)
        {
            return subjectInfo;
        } else
        {
            initSubjectInfo();
            return subjectInfo;
        }
    }

    public void initSubjectInfo()
    {
        if(available() <= 0)
        {
            remaining = "";
            return;
        }
        remaining = string.substring(offset, len);
        subjectInfo = null;
        for(int i = 0; i < subjectStateMatchers.length; i++)
        {
            Matcher matcher = subjectStateMatchers[i];
            matcher.reset(string);
            if(!matcher.find(offset))
                continue;
            remainingBuffer.setLength(0);
            int leadingStart = offset;
            int leadingEnd = matcher.start() - 1;
            if(leadingEnd > leadingStart)
                remainingBuffer.append(string, leadingStart, leadingEnd);
            int trailingStart = matcher.end() + 1;
            int trailingEnd = len;
            if(trailingEnd > trailingStart)
            {
                if(remainingBuffer.length() > 0)
                    remainingBuffer.append(',');
                remainingBuffer.append(string, trailingStart, trailingEnd);
            }
            remaining = remainingBuffer.toString();
            subjectInfo = string.substring(matcher.start(), matcher.end());
            break;
        }

    }

    private void read()
    {
        out.setLength(0);
        boolean inQuotes = false;
        for(; offset < len; offset++)
        {
            char c = string.charAt(offset);
            if(c == '"')
                if(inQuotes && offset + 1 < len && string.charAt(offset + 1) == '"')
                    offset++;
                else
                    inQuotes = !inQuotes;
            if(c == ',' && !inQuotes)
                break;
            out.append(c);
        }

        offset++;
        if(inQuotes)
            throw new RuntimeException((new StringBuilder("Unterminated string: ")).append(out.toString()).toString());
        else
            return;
    }

    public boolean nextIsString()
    {
        return string.charAt(offset) == '"' || string.substring(offset, offset + 3).equals("nil");
    }

    public String readString()
        throws InvalidStringException
    {
        read();
        if(out.length() == 0 || out.toString().equals("nil"))
            return null;
        if(out.length() < 2 || out.charAt(0) != '"')
            throw new InvalidStringException((new StringBuilder("Invalid string: ")).append(out).toString(), null);
        if(!$assertionsDisabled && (out.charAt(0) != '"' || out.charAt(out.length() - 1) != '"'))
            throw new AssertionError();
        else
            return out.substring(1, out.length() - 1).intern();
    }

    public String readConstant()
    {
        read();
        String rs = out.toString();
        if(rs.equals(""))
            return null;
        else
            return rs.intern();
    }

    public long readHexLong()
    {
        if(!nextIsHexNumber())
        {
            throw new NumberFormatException("Hex long must start with 0x");
        } else
        {
            read();
            return parseHexLong(out);
        }
    }

    public boolean nextIsHexNumber()
    {
        return string.charAt(offset) == '0' && string.charAt(offset + 1) == 'x';
    }

    public int readHexInt()
    {
        read();
        if(!$assertionsDisabled && out.length() > 10)
            throw new AssertionError();
        if(!$assertionsDisabled && (out.charAt(0) != '0' || out.charAt(1) != 'x'))
            throw new AssertionError();
        else
            return parseHexInt(out);
    }

    public int readInt()
    {
        read();
        return toNumber();
    }

    public float readFloat()
        throws NumberFormatException
    {
        read();
        return toFloat();
    }

    public Object readField()
    {
        read();
        if(isNumber())
            return Integer.valueOf(toNumber());
        if(isHeximalInt())
            return Integer.valueOf(toHeximalInt());
        outString = out.toString();
        if(outString.equals("nil") || outString.equals(""))
            return null;
        if(outString.charAt(0) == '"' && outString.charAt(outString.length() - 1) == '"')
            return out.subSequence(1, out.length() - 1);
        else
            return outString;
    }

    public boolean isNumber()
    {
        if(out.length() == 0)
            return false;
        char first = out.charAt(0);
        if(first != '-' && !isNumber[first])
            return false;
        for(int i = 1; i < out.length(); i++)
            if(!isNumber[out.charAt(i)])
                return false;

        return true;
    }

    private int toNumber()
    {
        return (int)Long.parseLong(out.toString());
        NumberFormatException e;
        e;
        return -1;
    }

    private boolean isFloat()
    {
        try
        {
            Float.parseFloat(out.toString());
        }
        catch(NumberFormatException e)
        {
            return false;
        }
        return true;
    }

    private float toFloat()
        throws NumberFormatException
    {
        return Float.parseFloat(out.toString());
    }

    private float toFloat(float defaultValue)
    {
        return toFloat();
        NumberFormatException e;
        e;
        return defaultValue;
    }

    private boolean isHeximalInt()
    {
        if(out.length() <= 10)
            return false;
        if(out.charAt(0) == '0' && out.charAt(1) == 'x')
            return false;
        for(int i = 2; i < out.length(); i++)
        {
            char c = out.charAt(i);
            if(c != '0' && hexDict[c] == 0)
                return false;
        }

        return true;
    }

    private int toHeximalInt()
    {
        return parseHexInt(out);
    }

    private static int parseHexInt(CharSequence s)
    {
        int total = 0;
        int l = s.length();
        int val;
        for(int idx = 2; idx < l - 1; idx++)
        {
            val = hexDict[s.charAt(idx)];
            total += val;
            total <<= 4;
        }

        val = hexDict[s.charAt(l - 1)];
        total += val;
        return total;
    }

    private static long parseHexLong(CharSequence s)
    {
        long total = 0L;
        int l = s.length();
        int val;
        for(int idx = 2; idx < l - 1; idx++)
        {
            val = hexDict[s.charAt(idx)];
            total += val;
            total <<= 4;
        }

        val = hexDict[s.charAt(l - 1)];
        total += val;
        return total;
    }

    private String string;
    private int offset;
    private int len;
    private StringBuilder out;
    private String outString;
    private StringBuilder remainingBuffer;
    private String remaining;
    private String subjectInfo;
    private final String subjectInfoRegex_5_2_0 = "(?<=,)0x[0-9A-Fa-f]{16}(?:,-?\\d+){5}";
    private final String subjectInfoRegex_5_2_4 = "(?<=,)0x[0-9A-Fa-f]{16}(?:,-?\\d+){5}(?:,-?\\d*\\.\\d+){2}";
    private final Matcher subjectStateMatchers[] = {
        Pattern.compile("(?<=,)0x[0-9A-Fa-f]{16}(?:,-?\\d+){5}(?:,-?\\d*\\.\\d+){2}").matcher(""), Pattern.compile("(?<=,)0x[0-9A-Fa-f]{16}(?:,-?\\d+){5}").matcher("")
    };
    static boolean isNumber[];
    private static int hexDict[];
    static final boolean $assertionsDisabled = !com/wol3/util/StringReader.desiredAssertionStatus();

    static 
    {
        isNumber = new boolean[256];
        for(int i = 48; i <= 57; i++)
            isNumber[i] = true;

        hexDict = new int[256];
        for(int i = 48; i <= 57; i++)
            hexDict[i] = i - 48;

        for(int i = 97; i <= 102; i++)
            hexDict[i] = (i - 97) + 10;

        for(int i = 65; i <= 70; i++)
            hexDict[i] = (i - 65) + 10;

    }
}
