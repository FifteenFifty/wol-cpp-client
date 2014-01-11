// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   SimpleProfiler.java

package com.wol3.util.perf;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class SimpleProfiler
{
    class TimeEntry
    {

        public java.lang.String name;
        public long time;
        final com.wol3.util.perf.SimpleProfiler this$0;

        public TimeEntry(java.lang.String name, long time)
        {
            _fld0 = com.wol3.util.perf.SimpleProfiler.this;
            super();
            this.name = name;
            this.time = time;
        }
    }


    public SimpleProfiler()
    {
        stack = new ArrayDeque();
        times = new LinkedHashMap();
    }

    public void enter(java.lang.String name)
    {
        stack.push(new TimeEntry(name, java.lang.System.nanoTime()));
    }

    public void exit()
    {
        com.wol3.util.perf.TimeEntry entry = (com.wol3.util.perf.TimeEntry)stack.pop();
        long elapsed = java.lang.System.nanoTime() - entry.time;
        addTime(entry.name, elapsed);
    }

    public java.util.HashMap getTimes()
    {
        java.util.HashMap rs = new LinkedHashMap();
        java.util.Map.Entry o;
        for (java.util.Iterator iterator = times.entrySet().iterator(); iterator.hasNext(); rs.put((java.lang.String)o.getKey(), java.lang.Double.valueOf((double)((java.lang.Long)o.getValue()).longValue() / 1000000D)))
            o = (java.util.Map.Entry)iterator.next();

        return rs;
    }

    private void addTime(java.lang.String name, long elapsed)
    {
        java.lang.Long time = (java.lang.Long)times.get(name);
        if (time != null)
            times.put(name, java.lang.Long.valueOf(time.longValue() + elapsed));
        else
            times.put(name, java.lang.Long.valueOf(elapsed));
    }

    public void reset(boolean resetStack)
    {
        times.clear();
        if (resetStack)
            stack.clear();
    }

    public static void staticEnter(java.lang.String name)
    {
        ((com.wol3.util.perf.SimpleProfiler)tlProfiler.get()).enter(name);
    }

    public static void staticExit()
    {
        ((com.wol3.util.perf.SimpleProfiler)tlProfiler.get()).exit();
    }

    public static void staticReset(boolean resetStack)
    {
        ((com.wol3.util.perf.SimpleProfiler)tlProfiler.get()).reset(resetStack);
    }

    public static java.util.HashMap staticGetTimes()
    {
        return ((com.wol3.util.perf.SimpleProfiler)tlProfiler.get()).getTimes();
    }

    java.util.ArrayDeque stack;
    java.util.HashMap times;
    private static final java.lang.ThreadLocal tlProfiler = new java.lang.ThreadLocal() {

        protected com.wol3.util.perf.SimpleProfiler initialValue()
        {
            return new SimpleProfiler();
        }

        protected volatile java.lang.Object initialValue()
        {
            return initialValue();
        }

    }
;

}
