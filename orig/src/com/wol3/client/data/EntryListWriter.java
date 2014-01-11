// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   EntryListWriter.java

package com.wol3.client.data;

import com.wol3.util.ByteBufferUtils;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Comparator;
import org.apache.commons.collections.primitives.ArrayByteList;
import org.apache.commons.collections.primitives.ArrayIntList;
import org.apache.commons.collections.primitives.ArrayLongList;
import org.apache.commons.collections.primitives.ArrayShortList;

// Referenced classes of package com.wol3.client.data:
//            EntryList, ActorPool, Actor

public class EntryListWriter
{
    class HotActorComparator
        implements java.util.Comparator
    {

        public int compare(com.wol3.client.data.Actor o1, com.wol3.client.data.Actor o2)
        {
            return counts[o2.id] - counts[o1.id];
        }

        public volatile int compare(java.lang.Object obj, java.lang.Object obj1)
        {
            return compare((com.wol3.client.data.Actor)obj, (com.wol3.client.data.Actor)obj1);
        }

        protected int counts[];
        final com.wol3.client.data.EntryListWriter this$0;

        public HotActorComparator(com.wol3.client.data.ActorPool actorPool, com.wol3.client.data.EntryList entryList)
        {
            _fld0 = com.wol3.client.data.EntryListWriter.this;
            super();
            counts = new int[actorPool.size()];
            for (int i = 0; i < entryList.size(); i++)
            {
                if (entryList.sources.get(i) != -1)
                    counts[entryList.sources.get(i)]++;
                if (entryList.targets.get(i) != -1)
                    counts[entryList.targets.get(i)]++;
            }

        }
    }


    public EntryListWriter(com.wol3.client.data.ActorPool actorPool, com.wol3.client.data.EntryList entryList)
    {
        this(actorPool, entryList, 0, entryList.size());
    }

    public EntryListWriter(com.wol3.client.data.ActorPool actorPool, com.wol3.client.data.EntryList entryList, int fromIndex, int toIndex)
    {
        initialize(actorPool, entryList, fromIndex, toIndex);
    }

    public void initialize(com.wol3.client.data.ActorPool actorPool, com.wol3.client.data.EntryList entryList, int fromIndex, int toIndex)
    {
        if (!$assertionsDisabled && (fromIndex < 0 || fromIndex > toIndex || toIndex > entryList.size()))
            throw new AssertionError();
        this.actorPool = actorPool;
        this.entryList = entryList;
        this.fromIndex = fromIndex;
        length = toIndex - fromIndex;
        com.wol3.client.data.Actor actors[] = actorPool.toArray();
        java.util.Arrays.sort(actors, new HotActorComparator(actorPool, entryList));
        hotActorIds = new int[java.lang.Math.min(256, actors.length)];
        hotActorIndices = new int[actorPool.size()];
        java.util.Arrays.fill(hotActorIndices, -1);
        for (int i = 0; i < hotActorIds.length; i++)
        {
            hotActorIds[i] = actors[i].id;
            hotActorIndices[actors[i].id] = i;
        }

        formats = new byte[length];
        uBytes = new ArrayByteList();
        uShorts = new ArrayShortList();
        ints = new ArrayIntList();
        if (length == 0)
            return;
        long prevTime = startTime = entryList.timestamps.get(fromIndex);
        for (int i = 0; i < length; i++)
        {
            int j = fromIndex + i;
            long delta = entryList.timestamps.get(j) - prevTime;
            if (delta < 0L)
                delta = 0L;
            if (delta > 0x7fffffffL)
                throw new AssertionError(java.lang.String.format("Time value out of range: %d > 2^31-1", new java.lang.Object[] {
                    java.lang.Long.valueOf(delta)
                }));
            formats[i] |= save((int)delta);
            formats[i] |= saveActor(entryList.sources.get(j)) << 2;
            formats[i] |= saveActor(entryList.targets.get(j)) << 4;
            formats[i] |= save(entryList.events.get(j)) << 6;
            prevTime = entryList.timestamps.get(j);
        }

    }

    public byte getType(int value)
    {
        if (value == 0)
            return 0;
        if (value >= 0 && value < 256)
            return 1;
        return ((byte)(value < 0 || value >= 0x10000 ? 3 : 2));
    }

    public byte getActorType(int actor)
    {
        if (actor == -1)
            return 0;
        if (actor >= 0)
        {
            if (hotActorIndices[actor] != -1)
                return 1;
            return ((byte)(actor >= 0x10000 ? 3 : 2));
        } else
        {
            throw new RuntimeException((new StringBuilder("Invalid actor ID: ")).append(actor).toString());
        }
    }

    public byte save(byte type, int value)
    {
        if (!$assertionsDisabled && (type & 3) != type)
            throw new AssertionError();
        switch (type)
        {
        case 1: // '\001'
            uBytes.add((byte)value);
            break;

        case 2: // '\002'
            uShorts.add((short)value);
            break;

        case 3: // '\003'
            ints.add(value);
            break;
        }
        return type;
    }

    public byte save(int value)
    {
        return save(getType(value), value);
    }

    public byte saveActor(int actor)
    {
        byte type = getActorType(actor);
        if (type == 1)
            actor = hotActorIndices[actor];
        return save(type, actor);
    }

    public void writeTo(java.nio.ByteBuffer bb)
    {
        bb.putInt(fromIndex);
        bb.putLong(startTime);
        com.wol3.util.ByteBufferUtils.putInts(bb, hotActorIds);
        com.wol3.util.ByteBufferUtils.putBytes(bb, formats);
        com.wol3.util.ByteBufferUtils.putBytes(bb, uBytes.toArray());
        com.wol3.util.ByteBufferUtils.putShorts(bb, uShorts.toArray());
        com.wol3.util.ByteBufferUtils.putInts(bb, ints.toArray());
    }

    public static final byte TYPE_NONE = 0;
    public static final byte TYPE_UBYTE = 1;
    public static final byte TYPE_USHORT = 2;
    public static final byte TYPE_INT = 3;
    public com.wol3.client.data.ActorPool actorPool;
    public com.wol3.client.data.EntryList entryList;
    int fromIndex;
    int length;
    long startTime;
    int hotActorIds[];
    int hotActorIndices[];
    public byte formats[];
    public org.apache.commons.collections.primitives.ArrayByteList uBytes;
    public org.apache.commons.collections.primitives.ArrayShortList uShorts;
    public org.apache.commons.collections.primitives.ArrayIntList ints;
    static final boolean $assertionsDisabled = !com/wol3/client/data/EntryListWriter.desiredAssertionStatus();

}
