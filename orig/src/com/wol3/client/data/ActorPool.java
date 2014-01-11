// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   ActorPool.java

package com.wol3.client.data;

import com.wol3.util.ByteBufferUtils;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;

// Referenced classes of package com.wol3.client.data:
//            Actor, StringPool, BinaryCombatLog

public class ActorPool
{

    public ActorPool()
    {
        key = new Actor(0L, "", 0, 0);
        map = new HashMap();
        list = new ArrayList();
    }

    public int indexOf(com.wol3.client.data.Actor actor)
    {
        java.lang.Integer index = (java.lang.Integer)map.get(actor);
        if (index == null)
        {
            index = java.lang.Integer.valueOf(list.size());
            actor.id = index.intValue();
            map.put(actor, index);
            list.add(actor);
            if (key == actor)
                key = new Actor(0L, "", 0, 0);
        } else
        {
            com.wol3.client.data.Actor a = (com.wol3.client.data.Actor)list.get(index.intValue());
            if (a.name.isEmpty() || a.name.equals("Unknown"))
                a.name = actor.name;
        }
        return index.intValue();
    }

    public int indexOf(long GUID, java.lang.String name, int flags, int raidFlags)
    {
        return indexOf(key.set(GUID, name, flags, raidFlags));
    }

    public int size()
    {
        return list.size();
    }

    public com.wol3.client.data.Actor[] toArray()
    {
        return (com.wol3.client.data.Actor[])list.toArray(new com.wol3.client.data.Actor[size()]);
    }

    public void writeTo(java.nio.ByteBuffer bb, com.wol3.client.data.BinaryCombatLog.Format format)
    {
        writeTo(bb, format, 0);
    }

    public void writeTo(java.nio.ByteBuffer bb, com.wol3.client.data.BinaryCombatLog.Format format, int fromIndex)
    {
        writeTo(bb, format, fromIndex, size());
    }

    public void writeTo(java.nio.ByteBuffer bb, com.wol3.client.data.BinaryCombatLog.Format format, int fromIndex, int toIndex)
    {
        bb.putLong(0xff00ff00ff00ff00L);
        int length = toIndex - fromIndex;
        com.wol3.client.data.StringPool namePool = new StringPool();
        long GUIDs[] = new long[length];
        int names[] = new int[length];
        int flags[] = new int[length];
        int raidFlags[] = new int[length];
        for (int i = 0; i < length; i++)
        {
            com.wol3.client.data.Actor actor = (com.wol3.client.data.Actor)list.get(fromIndex + i);
            GUIDs[i] = actor.GUID;
            names[i] = namePool.indexOf(actor.name);
            flags[i] = actor.flags;
            raidFlags[i] = actor.raidFlags;
        }

        bb.putInt(fromIndex);
        com.wol3.util.ByteBufferUtils.putStrings(bb, namePool.toArray());
        com.wol3.util.ByteBufferUtils.putLongs(bb, GUIDs);
        com.wol3.util.ByteBufferUtils.putInts(bb, names);
        com.wol3.util.ByteBufferUtils.putInts(bb, flags);
        if (format.hasRaidFlags)
            com.wol3.util.ByteBufferUtils.putInts(bb, raidFlags);
    }

    public java.util.ArrayList list;
    protected java.util.HashMap map;
    private com.wol3.client.data.Actor key;
}
