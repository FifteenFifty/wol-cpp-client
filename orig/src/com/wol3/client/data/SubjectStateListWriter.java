// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   SubjectStateListWriter.java

package com.wol3.client.data;

import com.digibites.util.BitArray;
import com.wol3.util.ByteBufferUtils;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.commons.collections.primitives.ArrayFloatList;
import org.apache.commons.collections.primitives.ArrayIntList;
import org.apache.commons.collections.primitives.ArrayLongList;
import org.apache.commons.collections.primitives.FloatList;
import org.apache.commons.collections.primitives.IntList;
import org.apache.commons.collections.primitives.LongList;

// Referenced classes of package com.wol3.client.data:
//            SubjectStateList, BinaryCombatLog, Actor

class SubjectStateListWriter
{
    private static final class AstPool
    {

        public int indexOf(int attackPower, int spellPower, int resourceType)
        {
            java.lang.Integer index = (java.lang.Integer)indices.get(key.set(attackPower, spellPower, resourceType));
            if (index != null)
            {
                return index.intValue();
            } else
            {
                int newIndex = objects.size();
                com.wol3.client.data.AstRef astRef = new AstRef(key);
                objects.add(astRef);
                indices.put(astRef, java.lang.Integer.valueOf(newIndex));
                return newIndex;
            }
        }

        public int size()
        {
            return objects.size();
        }

        public void writeToBuffer(java.nio.ByteBuffer buffer)
        {
            writeToBuffer(buffer, 0);
        }

        private void writeToBuffer(java.nio.ByteBuffer buffer, int offset)
        {
            int size = size();
            int attackPower[] = new int[size];
            int spellPower[] = new int[size];
            int resourceType[] = new int[size];
            for (int i = 0; i < size; i++)
            {
                com.wol3.client.data.AstRef astRef = (com.wol3.client.data.AstRef)objects.get(i);
                attackPower[i] = astRef.attackPower;
                spellPower[i] = astRef.spellPower;
                resourceType[i] = astRef.resourceType;
            }

            com.wol3.util.ByteBufferUtils.putInts(buffer, attackPower);
            com.wol3.util.ByteBufferUtils.putInts(buffer, spellPower);
            com.wol3.util.ByteBufferUtils.putInts(buffer, resourceType);
        }

        private com.wol3.client.data.AstRef key;
        final java.util.ArrayList objects;
        final java.util.HashMap indices;

        private AstPool()
        {
            key = new AstRef(0, 0, 0);
            objects = new ArrayList();
            indices = new HashMap();
        }

        AstPool(com.wol3.client.data.AstPool astpool)
        {
            this();
        }
    }

    private static final class AstRef
    {

        public com.wol3.client.data.AstRef set(int attackPower, int spellPower, int resourceType)
        {
            this.attackPower = attackPower;
            this.spellPower = spellPower;
            this.resourceType = resourceType;
            return this;
        }

        public int hashCode()
        {
            int prime = 31;
            int result = 1;
            result = 31 * result + attackPower;
            result = 31 * result + resourceType;
            result = 31 * result + spellPower;
            return result;
        }

        public boolean equals(java.lang.Object obj)
        {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            com.wol3.client.data.AstRef other = (com.wol3.client.data.AstRef)obj;
            if (attackPower != other.attackPower)
                return false;
            if (resourceType != other.resourceType)
                return false;
            return spellPower == other.spellPower;
        }

        public int attackPower;
        public int spellPower;
        public int resourceType;

        public AstRef(com.wol3.client.data.AstRef astRef)
        {
            this(astRef.attackPower, astRef.spellPower, astRef.resourceType);
        }

        public AstRef(int attackPower, int spellPower, int resourceType)
        {
            this.attackPower = attackPower;
            this.spellPower = spellPower;
            this.resourceType = resourceType;
        }
    }

    private static final class GuidRef extends java.lang.Enum
    {

        public void putBitPair(com.digibites.util.BitArray entryFlags, int index)
        {
            entryFlags.set(2 * index, high);
            entryFlags.set(2 * index + 1, low);
        }

        public static com.wol3.client.data.GuidRef[] values()
        {
            com.wol3.client.data.GuidRef aguidref[];
            int i;
            com.wol3.client.data.GuidRef aguidref1[];
            java.lang.System.arraycopy(aguidref = VALUES, 0, aguidref1 = new com.wol3.client.data.GuidRef[i = aguidref.length], 0, i);
            return aguidref1;
        }

        public static com.wol3.client.data.GuidRef valueOf(java.lang.String s)
        {
            return (com.wol3.client.data.GuidRef)java.lang.Enum.valueOf(com/wol3/client/data/SubjectStateListWriter$GuidRef, s);
        }

        public static final com.wol3.client.data.GuidRef NONE;
        public static final com.wol3.client.data.GuidRef SOURCE;
        public static final com.wol3.client.data.GuidRef TARGET;
        public static final com.wol3.client.data.GuidRef EXCEPTION;
        private final boolean high;
        private final boolean low;
        private static final com.wol3.client.data.GuidRef ENUM$VALUES[];

        static 
        {
            NONE = new GuidRef("NONE", 0);
            SOURCE = new GuidRef("SOURCE", 1);
            TARGET = new GuidRef("TARGET", 2);
            EXCEPTION = new GuidRef("EXCEPTION", 3);
            VALUES = (new com.wol3.client.data.GuidRef[] {
                NONE, SOURCE, TARGET, EXCEPTION
            });
        }

        private GuidRef(java.lang.String s, int i)
        {
            super(s, i);
            if (ordinal() > 3)
            {
                throw new RuntimeException("Invalid ordinal for 2-bit packing");
            } else
            {
                high = (ordinal() & 2) != 0;
                low = (ordinal() & 1) != 0;
                return;
            }
        }
    }

    private static final class GuidStateList
    {

        public void add(int astRefId, int health, int resourceAmount, float xPosition, float yPosition)
        {
            int index = astRefIdList.size();
            astRefIdList.add(astRefId);
            healthList.add(health);
            resourceAmountList.add(resourceAmount);
            if (addPosition(xPosition, yPosition))
                positionChangeFlags.set(index);
        }

        public int size()
        {
            return astRefIdList.size();
        }

        private boolean addPosition(float xPosition, float yPosition)
        {
            if (lastPositionEquals(xPosition, yPosition))
            {
                return false;
            } else
            {
                xPositionList.add(xPosition);
                yPositionList.add(yPosition);
                return true;
            }
        }

        private boolean lastPositionEquals(float xPosition, float yPosition)
        {
            if (xPositionList.isEmpty())
                return false;
            return java.lang.Float.compare(xPositionList.get(xPositionList.size() - 1), xPosition) == 0 && java.lang.Float.compare(yPositionList.get(yPositionList.size() - 1), yPosition) == 0;
        }

        public void writeToBuffer(java.nio.ByteBuffer buffer, com.wol3.util.ByteBufferUtils.IntArrayWriter intWriter)
        {
            buffer.putLong(guid);
            intWriter.writeIntArray(buffer, astRefIdList.toArray());
            intWriter.writeIntArray(buffer, healthList.toArray());
            intWriter.writeIntArray(buffer, resourceAmountList.toArray());
            com.digibites.util.BitArray.fromBitSet(positionChangeFlags, size()).writeToBuffer(buffer);
            com.wol3.util.ByteBufferUtils.putFloats(buffer, xPositionList);
            com.wol3.util.ByteBufferUtils.putFloats(buffer, yPositionList);
        }

        final long guid;
        final org.apache.commons.collections.primitives.ArrayIntList astRefIdList = new ArrayIntList();
        final org.apache.commons.collections.primitives.ArrayIntList healthList = new ArrayIntList();
        final org.apache.commons.collections.primitives.ArrayIntList resourceAmountList = new ArrayIntList();
        final java.util.BitSet positionChangeFlags = new BitSet();
        final org.apache.commons.collections.primitives.ArrayFloatList xPositionList = new ArrayFloatList();
        final org.apache.commons.collections.primitives.ArrayFloatList yPositionList = new ArrayFloatList();

        public GuidStateList(long guid)
        {
            this.guid = guid;
        }
    }


    SubjectStateListWriter(com.wol3.client.data.SubjectStateList stateList, com.wol3.client.data.BinaryCombatLog combatLog, int entryOffset)
    {
        this.stateList = stateList;
        this.combatLog = combatLog;
        this.entryOffset = entryOffset;
        entryFlags = new BitArray(2 * (stateList.entryCount() - entryOffset));
        initialize();
    }

    private void initialize()
    {
        for (int entryIndex = entryOffset; entryIndex < stateList.entryCount(); entryIndex++)
        {
            boolean hasSubjectState = doEntryFlags(entryIndex);
            if (hasSubjectState)
                doSubjectState(entryIndex);
        }

    }

    private boolean doEntryFlags(int entryIndex)
    {
        com.wol3.client.data.GuidRef guidRef = getGuidRef(entryIndex);
        if (guidRef == com.wol3.client.data.GuidRef.EXCEPTION)
            exceptionGuids.add(stateList.guidList.get(stateList.indices.get(entryIndex)));
        guidRef.putBitPair(entryFlags, entryIndex - entryOffset);
        return guidRef != com.wol3.client.data.GuidRef.NONE;
    }

    private void doSubjectState(int entryIndex)
    {
        int stateIndex = stateList.indices.get(entryIndex);
        java.lang.Long guid = java.lang.Long.valueOf(stateList.guidList.get(stateIndex));
        java.lang.Integer index = (java.lang.Integer)guidStateListIndices.get(guid);
        com.wol3.client.data.GuidStateList guidStateList;
        if (index != null)
        {
            guidStateList = (com.wol3.client.data.GuidStateList)guidStateLists.get(index.intValue());
        } else
        {
            index = java.lang.Integer.valueOf(guidStateLists.size());
            guidStateList = new GuidStateList(guid.longValue());
            guidStateLists.add(guidStateList);
            guidStateListIndices.put(guid, index);
        }
        int astRefId = astPool.indexOf(stateList.attackPowerList.get(stateIndex), stateList.spellPowerList.get(stateIndex), stateList.resourceTypeList.get(stateIndex));
        guidStateList.add(astRefId, stateList.healthList.get(stateIndex), stateList.resourceAmountList.get(stateIndex), stateList.xPositionList.get(stateIndex), stateList.yPositionList.get(stateIndex));
    }

    private com.wol3.client.data.GuidRef getGuidRef(int entryIndex)
    {
        int stateIndex = stateList.indices.get(entryIndex);
        if (stateIndex == -1)
            return com.wol3.client.data.GuidRef.NONE;
        long stateGuid = stateList.guidList.get(stateIndex);
        com.wol3.client.data.Actor targetActor = combatLog.getTargetActor(entryIndex);
        if (targetActor != null && targetActor.GUID == stateGuid)
            return com.wol3.client.data.GuidRef.TARGET;
        com.wol3.client.data.Actor sourceActor = combatLog.getTargetActor(entryIndex);
        if (sourceActor != null && sourceActor.GUID == stateGuid)
            return com.wol3.client.data.GuidRef.SOURCE;
        else
            return com.wol3.client.data.GuidRef.EXCEPTION;
    }

    public void writeToBuffer(java.nio.ByteBuffer buffer)
    {
        buffer.put(com.wol3.client.data.SubjectStateList.MAGIC);
        int sizePosition = buffer.position();
        buffer.putInt(-1);
        com.wol3.util.ByteBufferUtils.putLongs(buffer, exceptionGuids);
        astPool.writeToBuffer(buffer);
        entryFlags.writeToBuffer(buffer);
        com.wol3.util.ByteBufferUtils.IntArrayWriter intWriter = new com.wol3.util.ByteBufferUtils.IntArrayWriter();
        buffer.putInt(guidStateLists.size());
        com.wol3.client.data.GuidStateList guidStateList;
        for (java.util.Iterator iterator = guidStateLists.iterator(); iterator.hasNext(); guidStateList.writeToBuffer(buffer, intWriter))
            guidStateList = (com.wol3.client.data.GuidStateList)iterator.next();

        int endPosition = buffer.position();
        int contentSize = endPosition - sizePosition;
        buffer.position(sizePosition);
        buffer.putInt(contentSize);
        buffer.position(endPosition);
    }

    private final com.wol3.client.data.SubjectStateList stateList;
    private final com.wol3.client.data.BinaryCombatLog combatLog;
    private final int entryOffset;
    private final com.digibites.util.BitArray entryFlags;
    private final org.apache.commons.collections.primitives.LongList exceptionGuids = new ArrayLongList();
    private final com.wol3.client.data.AstPool astPool = new AstPool(null);
    private final java.util.List guidStateLists = new ArrayList();
    private final java.util.Map guidStateListIndices = new HashMap();
}
