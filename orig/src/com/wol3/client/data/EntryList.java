// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   EntryList.java

package com.wol3.client.data;

import java.nio.ByteBuffer;
import org.apache.commons.collections.primitives.ArrayIntList;
import org.apache.commons.collections.primitives.ArrayLongList;

// Referenced classes of package com.wol3.client.data:
//            EntryListWriter, ActorPool

public class EntryList
{

    public EntryList()
    {
        timestamps = new ArrayLongList();
        sources = new ArrayIntList();
        targets = new ArrayIntList();
        events = new ArrayIntList();
    }

    public void add(long timestamp, int source, int target, int event)
    {
        timestamps.add(timestamp);
        sources.add(source);
        targets.add(target);
        events.add(event);
    }

    public int size()
    {
        return timestamps.size();
    }

    public void writeTo(java.nio.ByteBuffer bb, com.wol3.client.data.ActorPool actorPool, int fromIndex, int toIndex)
    {
        (new EntryListWriter(actorPool, this, fromIndex, toIndex)).writeTo(bb);
    }

    public void writeTo(java.nio.ByteBuffer bb, com.wol3.client.data.ActorPool actorPool, int fromIndex)
    {
        writeTo(bb, actorPool, fromIndex, size());
    }

    public void trim()
    {
        timestamps.trimToSize();
        sources.trimToSize();
        targets.trimToSize();
        events.trimToSize();
    }

    public org.apache.commons.collections.primitives.ArrayLongList timestamps;
    public org.apache.commons.collections.primitives.ArrayIntList sources;
    public org.apache.commons.collections.primitives.ArrayIntList targets;
    public org.apache.commons.collections.primitives.ArrayIntList events;
}
