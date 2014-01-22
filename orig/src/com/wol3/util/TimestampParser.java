// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TimestampParser.java

package com.wol3.util;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TimestampParser
{

    public TimestampParser()
    {
        calendar = Calendar.getInstance();
        now = System.currentTimeMillis();
        timePattern = Pattern.compile("(\\d+)/(\\d+) (\\d+):(\\d+):(\\d+).(\\d+)");
    }

    public long parse(String timestamp)
    {
        if(timestamp.equals(lastTimestampString))
            return lastTimestamp;
        int index = 0;
        int numbers[] = new int[6];
        int offset = 0;
        for(char buffer[] = timestamp.toCharArray(); index < 6 && offset < buffer.length;)
        {
            char c = buffer[offset++];
            if(c >= '0' && c <= '9')
            {
                numbers[index] *= 10;
                numbers[index] += c - 48;
            } else
            {
                index++;
            }
        }

        calendar.set(calendar.get(1), numbers[0] - 1, numbers[1], numbers[2], numbers[3], numbers[4]);
        calendar.set(14, numbers[5]);
        lastTimestampString = timestamp;
        long newTimestamp = calendar.getTimeInMillis();
        if(newTimestamp < lastTimestamp)
        {
            calendar.set(1, calendar.get(1) + 1);
            newTimestamp = calendar.getTimeInMillis();
        }
        if(lastTimestamp > now)
        {
            calendar.set(1, calendar.get(1) - 1);
            lastTimestamp = calendar.getTimeInMillis();
        }
        lastTimestamp = newTimestamp;
        return lastTimestamp;
    }

    public long parseSlow(String timestamp)
    {
        if(timestamp.equals(lastTimestampString))
            return lastTimestamp;
        Matcher matcher = timePattern.matcher(timestamp);
        if(!matcher.matches())
            return -1L;
        int numbers[] = new int[6];
        for(int i = 0; i < 6; i++)
            numbers[i] = Integer.parseInt(matcher.group(i + 1));

        calendar.set(calendar.get(1), numbers[0] - 1, numbers[1], numbers[2], numbers[3], numbers[4]);
        calendar.set(14, numbers[5]);
        lastTimestampString = timestamp;
        long newTimestamp = calendar.getTimeInMillis();
        if(newTimestamp < lastTimestamp)
        {
            calendar.set(1, calendar.get(1) + 1);
            newTimestamp = calendar.getTimeInMillis();
        }
        if(newTimestamp > now + 0x5265c00L)
        {
            calendar.set(1, calendar.get(1) - 1);
            newTimestamp = calendar.getTimeInMillis();
        }
        lastTimestampString = timestamp;
        lastTimestamp = newTimestamp;
        return lastTimestamp;
    }

    private long lastTimestamp;
    private String lastTimestampString;
    private Calendar calendar;
    private long now;
    private Pattern timePattern;
}
