// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   BinaryCombatLogSplitter.java

package com.wol3.client.data;

import java.util.ArrayList;
import java.util.Properties;
import org.apache.commons.collections.primitives.ArrayIntList;
import org.apache.commons.collections.primitives.ArrayLongList;

// Referenced classes of package com.wol3.client.data:
//            BinaryCombatLog, EntryList, ActorPool, EventPool, 
//            SubjectStateList, Event, Actor

public class BinaryCombatLogSplitter
{

    public BinaryCombatLogSplitter()
    {
    }

    public static com.wol3.client.data.BinaryCombatLog split(com.wol3.client.data.BinaryCombatLog log, int fromIndex, int toIndex)
    {
        if (!$assertionsDisabled && (fromIndex < 0 || toIndex > log.entryList.size()))
            throw new AssertionError();
        com.wol3.client.data.ActorPool actorPool = new ActorPool();
        com.wol3.client.data.EventPool eventPool = new EventPool();
        com.wol3.client.data.EntryList entryList = new EntryList();
        com.wol3.client.data.SubjectStateList stateList = log.stateList.subList(fromIndex, toIndex);
        int lines = (toIndex - fromIndex) + 1;
        entryList.timestamps.ensureCapacity(lines);
        entryList.sources.ensureCapacity(lines);
        entryList.targets.ensureCapacity(lines);
        entryList.events.ensureCapacity(lines);
        for (int i = fromIndex; i <= toIndex; i++)
        {
            entryList.timestamps.add(log.entryList.timestamps.get(i));
            int eventIdx = log.entryList.events.get(i);
            com.wol3.client.data.Event event = log.eventPool.get(eventIdx);
            int newEventIndex = eventPool.indexOf(event.type, event.data);
            entryList.events.add(newEventIndex);
            int sourceId = log.entryList.sources.get(i);
            if (sourceId != -1)
            {
                com.wol3.client.data.Actor source = (com.wol3.client.data.Actor)log.actorPool.list.get(log.entryList.sources.get(i));
                entryList.sources.add(actorPool.indexOf(source));
            } else
            {
                entryList.sources.add(-1);
            }
            int targetId = log.entryList.targets.get(i);
            if (targetId != -1)
            {
                com.wol3.client.data.Actor target = (com.wol3.client.data.Actor)log.actorPool.list.get(log.entryList.targets.get(i));
                entryList.targets.add(actorPool.indexOf(target));
            } else
            {
                entryList.targets.add(-1);
            }
        }

        com.wol3.client.data.BinaryCombatLog rs = new BinaryCombatLog();
        rs.properties = new Properties();
        rs.properties.putAll(log.properties);
        rs.format = log.format;
        rs.actorPool = actorPool;
        rs.eventPool = eventPool;
        rs.entryList = entryList;
        rs.stateList = stateList;
        rs.finish();
        return rs;
    }

    static final boolean $assertionsDisabled = !com/wol3/client/data/BinaryCombatLogSplitter.desiredAssertionStatus();

}
