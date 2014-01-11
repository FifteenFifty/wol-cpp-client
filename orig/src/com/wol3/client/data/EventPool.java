// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   EventPool.java

package com.wol3.client.data;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Referenced classes of package com.wol3.client.data:
//            Event, EventPoolWriter

public class EventPool
{
    protected class Entry
    {

        public boolean equals(java.lang.Object obj)
        {
            com.wol3.client.data.Entry other = (com.wol3.client.data.Entry)obj;
            return type.equals(other.type) && data.equals(other.data);
        }

        public int hashCode()
        {
            return type.hashCode() ^ data.hashCode();
        }

        public java.lang.String type;
        public java.lang.String data;
        final com.wol3.client.data.EventPool this$0;

        public Entry(java.lang.String type, java.lang.String data)
        {
            _fld0 = com.wol3.client.data.EventPool.this;
            super();
            this.type = type;
            this.data = data;
        }
    }


    public EventPool()
    {
        this(((java.util.List) (new ArrayList())));
    }

    public EventPool(java.util.List events)
    {
        list = events;
        map = new HashMap();
    }

    public com.wol3.client.data.Event get(int index)
    {
        return (com.wol3.client.data.Event)list.get(index);
    }

    protected int indexOf(com.wol3.client.data.Entry entry)
    {
        java.lang.Integer index = (java.lang.Integer)map.get(entry);
        if (index == null)
        {
            index = java.lang.Integer.valueOf(list.size());
            entry.data = entry.data.intern();
            com.wol3.client.data.Event event = new Event(index.intValue(), entry.type, entry.data);
            list.add(event);
            map.put(entry, index);
        }
        return index.intValue();
    }

    public int indexOf(java.lang.String type, java.lang.String data)
    {
        return indexOf(new Entry(type, data));
    }

    public int size()
    {
        return list.size();
    }

    public void writeTo(java.nio.ByteBuffer bb)
    {
        writeTo(bb, 0);
    }

    public void writeTo(java.nio.ByteBuffer bb, int fromIndex)
    {
        writeTo(bb, fromIndex, size());
    }

    public void writeTo(java.nio.ByteBuffer b, int fromIndex, int toIndex)
    {
        com.wol3.client.data.EventPoolWriter epw = new EventPoolWriter(this, fromIndex, toIndex);
        epw.writeTo(b);
    }

    public void finish()
    {
        map = null;
    }

    protected java.util.List list;
    protected java.util.Map map;
}
