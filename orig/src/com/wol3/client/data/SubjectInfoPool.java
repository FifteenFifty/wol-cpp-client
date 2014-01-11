// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   SubjectInfoPool.java

package com.wol3.client.data;

import com.wol3.util.ByteBufferUtils;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import org.apache.commons.collections.primitives.ArrayFloatList;
import org.apache.commons.collections.primitives.ArrayIntList;
import org.apache.commons.collections.primitives.FloatList;
import org.apache.commons.collections.primitives.IntList;
import org.apache.commons.collections.primitives.decorators.UnmodifiableFloatList;
import org.apache.commons.collections.primitives.decorators.UnmodifiableIntList;

// Referenced classes of package com.wol3.client.data:
//            BinaryCombatLog

public class SubjectInfoPool
{
    public static final class PooledSubjectInfo
    {

        public com.wol3.client.data.PooledSubjectInfo set(long guid, int attackPower, int spellPower, short resourceType)
        {
            this.guid = guid;
            this.attackPower = attackPower;
            this.spellPower = spellPower;
            this.resourceType = resourceType;
            return this;
        }

        public int hashCode()
        {
            return (int)(guid ^ guid >>> 32) ^ attackPower ^ spellPower ^ resourceType;
        }

        public boolean equals(java.lang.Object obj)
        {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            com.wol3.client.data.PooledSubjectInfo other = (com.wol3.client.data.PooledSubjectInfo)obj;
            if (attackPower != other.attackPower)
                return false;
            if (guid != other.guid)
                return false;
            if (resourceType != other.resourceType)
                return false;
            return spellPower == other.spellPower;
        }

        public java.lang.String toString()
        {
            java.lang.StringBuilder builder = new StringBuilder();
            builder.append("PooledSubjectInfo [guid=").append(guid).append(", attackPower=").append(attackPower).append(", spellPower=").append(spellPower).append(", resourceType=").append(resourceType).append("]");
            return builder.toString();
        }

        public long guid;
        public int attackPower;
        public int spellPower;
        public short resourceType;

        public PooledSubjectInfo()
        {
        }

        public PooledSubjectInfo(long guid, int attackPower, int spellPower, short resourceType)
        {
            set(guid, attackPower, spellPower, resourceType);
        }
    }


    public SubjectInfoPool()
    {
        pooledObjectList = new ArrayList();
        pooledIndices = new HashMap();
        pooledId = new ArrayIntList();
        health = new ArrayIntList();
        resourceAmount = new ArrayIntList();
        key = new PooledSubjectInfo();
        positionX = new ArrayFloatList();
        positionY = new ArrayFloatList();
    }

    public void add(long subjectInfoGuid, int health, int attackPower, int spellPower, short resourceType, int resourceAmount, 
            float posX, float posY)
    {
        key.set(subjectInfoGuid, attackPower, spellPower, resourceType);
        java.lang.Integer index = (java.lang.Integer)pooledIndices.get(key);
        if (index == null)
        {
            index = java.lang.Integer.valueOf(pooledObjectList.size());
            pooledObjectList.add(key);
            pooledIndices.put(key, index);
            key = new PooledSubjectInfo();
        }
        pooledId.add(index.intValue());
        this.health.add(health);
        this.resourceAmount.add(resourceAmount);
        positionX.add(posX);
        positionY.add(posY);
    }

    public int hashCode()
    {
        int prime = 31;
        int result = 1;
        result = 31 * result + (health != null ? health.hashCode() : 0);
        result = 31 * result + (pooledId != null ? pooledId.hashCode() : 0);
        result = 31 * result + (pooledIndices != null ? pooledIndices.hashCode() : 0);
        result = 31 * result + (pooledObjectList != null ? pooledObjectList.hashCode() : 0);
        result = 31 * result + (positionX != null ? positionX.hashCode() : 0);
        result = 31 * result + (positionY != null ? positionY.hashCode() : 0);
        result = 31 * result + (resourceAmount != null ? resourceAmount.hashCode() : 0);
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
        com.wol3.client.data.SubjectInfoPool other = (com.wol3.client.data.SubjectInfoPool)obj;
        if (health == null)
        {
            if (other.health != null)
                return false;
        } else
        if (!health.equals(other.health))
            return false;
        if (pooledId == null)
        {
            if (other.pooledId != null)
                return false;
        } else
        if (!pooledId.equals(other.pooledId))
            return false;
        if (pooledIndices == null)
        {
            if (other.pooledIndices != null)
                return false;
        } else
        if (!pooledIndices.equals(other.pooledIndices))
            return false;
        if (pooledObjectList == null)
        {
            if (other.pooledObjectList != null)
                return false;
        } else
        if (!pooledObjectList.equals(other.pooledObjectList))
            return false;
        if (positionX == null)
        {
            if (other.positionX != null)
                return false;
        } else
        if (!positionX.equals(other.positionX))
            return false;
        if (positionY == null)
        {
            if (other.positionY != null)
                return false;
        } else
        if (!positionY.equals(other.positionY))
            return false;
        if (resourceAmount == null)
        {
            if (other.resourceAmount != null)
                return false;
        } else
        if (!resourceAmount.equals(other.resourceAmount))
            return false;
        return true;
    }

    public void writeTo(java.nio.ByteBuffer bb, com.wol3.client.data.BinaryCombatLog.Format format, int fromIndex, int fromEntry)
    {
        bb.putLong(0xfedcba00000001L);
        int contentLengthPosition = bb.position();
        bb.putInt(-1);
        int startPosition = bb.position();
        int poolEntriesToWrite = pooledObjectList.size() - fromIndex;
        long guid[] = new long[poolEntriesToWrite];
        int attackPower[] = new int[poolEntriesToWrite];
        int spellPower[] = new int[poolEntriesToWrite];
        short resourceType[] = new short[poolEntriesToWrite];
        for (int i = 0; i < poolEntriesToWrite; i++)
        {
            com.wol3.client.data.PooledSubjectInfo p = (com.wol3.client.data.PooledSubjectInfo)pooledObjectList.get(fromIndex + i);
            guid[i] = p.guid;
            attackPower[i] = p.attackPower;
            spellPower[i] = p.spellPower;
            resourceType[i] = p.resourceType;
        }

        bb.putInt(fromIndex);
        com.wol3.util.ByteBufferUtils.putLongs(bb, guid);
        com.wol3.util.ByteBufferUtils.putInts(bb, attackPower);
        com.wol3.util.ByteBufferUtils.putInts(bb, spellPower);
        com.wol3.util.ByteBufferUtils.putShorts(bb, resourceType);
        bb.putInt(fromEntry);
        com.wol3.util.ByteBufferUtils.putInts(bb, pooledId, fromEntry);
        com.wol3.util.ByteBufferUtils.putInts(bb, health, fromEntry);
        com.wol3.util.ByteBufferUtils.putInts(bb, resourceAmount, fromEntry);
        if (format.hasSubjectPosition)
        {
            com.wol3.util.ByteBufferUtils.putFloats(bb, positionX, fromEntry);
            com.wol3.util.ByteBufferUtils.putFloats(bb, positionY, fromEntry);
        }
        int endPosition = bb.position();
        int contentLength = endPosition - startPosition;
        bb.position(contentLengthPosition);
        bb.putInt(contentLength);
        bb.position(endPosition);
    }

    public int size()
    {
        return pooledObjectList.size();
    }

    public com.wol3.client.data.SubjectInfoPool slice(int fromIndex, int toIndex)
    {
        com.wol3.client.data.SubjectInfoPool subjectInfoPool = new SubjectInfoPool();
        for (int i = fromIndex; i <= toIndex; i++)
        {
            int refId = pooledId.get(i);
            com.wol3.client.data.PooledSubjectInfo refObj = (com.wol3.client.data.PooledSubjectInfo)pooledObjectList.get(refId);
            java.lang.Integer newRefId = (java.lang.Integer)subjectInfoPool.pooledIndices.get(refObj);
            if (newRefId == null)
            {
                newRefId = java.lang.Integer.valueOf(subjectInfoPool.pooledObjectList.size());
                subjectInfoPool.pooledObjectList.add(refObj);
                subjectInfoPool.pooledIndices.put(refObj, newRefId);
            }
            subjectInfoPool.pooledId.add(newRefId.intValue());
        }

        subjectInfoPool.health = org.apache.commons.collections.primitives.decorators.UnmodifiableIntList.wrap(health.subList(fromIndex, toIndex + 1));
        subjectInfoPool.resourceAmount = org.apache.commons.collections.primitives.decorators.UnmodifiableIntList.wrap(resourceAmount.subList(fromIndex, toIndex + 1));
        subjectInfoPool.positionX = org.apache.commons.collections.primitives.decorators.UnmodifiableFloatList.wrap(positionX.subList(fromIndex, toIndex + 1));
        subjectInfoPool.positionY = org.apache.commons.collections.primitives.decorators.UnmodifiableFloatList.wrap(positionY.subList(fromIndex, toIndex + 1));
        return subjectInfoPool;
    }

    public static final long MAGIC = 0xfedcba00000001L;
    public java.util.ArrayList pooledObjectList;
    public java.util.HashMap pooledIndices;
    public org.apache.commons.collections.primitives.IntList pooledId;
    public org.apache.commons.collections.primitives.IntList health;
    public org.apache.commons.collections.primitives.IntList resourceAmount;
    transient com.wol3.client.data.PooledSubjectInfo key;
    public org.apache.commons.collections.primitives.FloatList positionX;
    public org.apache.commons.collections.primitives.FloatList positionY;
}
