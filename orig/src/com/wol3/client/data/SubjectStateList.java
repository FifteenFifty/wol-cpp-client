// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   SubjectStateList.java

package com.wol3.client.data;

import java.nio.ByteBuffer;
import java.util.Formatter;
import org.apache.commons.collections.primitives.ArrayFloatList;
import org.apache.commons.collections.primitives.ArrayIntList;
import org.apache.commons.collections.primitives.ArrayLongList;
import org.apache.commons.collections.primitives.FloatList;
import org.apache.commons.collections.primitives.IntList;
import org.apache.commons.collections.primitives.LongList;

// Referenced classes of package com.wol3.client.data:
//            SubjectStateListWriter, BinaryCombatLog

public class SubjectStateList
{
    public class Cursor
    {

        public void setEntryIndex(int entryIndex)
        {
            this.entryIndex = entryIndex;
            subjectStateIndex = indices.get(entryIndex);
        }

        public int getEntryIndex()
        {
            return entryIndex;
        }

        public boolean hasState()
        {
            return subjectStateIndex != -1;
        }

        public long getGuid()
        {
            return guidList.get(subjectStateIndex);
        }

        public int getHealth()
        {
            return healthList.get(subjectStateIndex);
        }

        public int getAttackPower()
        {
            return attackPowerList.get(subjectStateIndex);
        }

        public int getSpellPower()
        {
            return spellPowerList.get(subjectStateIndex);
        }

        public int getResourceType()
        {
            return resourceTypeList.get(subjectStateIndex);
        }

        public int getResourceAmount()
        {
            return resourceAmountList.get(subjectStateIndex);
        }

        public float getXPosition()
        {
            return xPositionList.get(subjectStateIndex);
        }

        public float getYPosition()
        {
            return yPositionList.get(subjectStateIndex);
        }

        public void toCanonicalFormat(java.util.Formatter formatter)
        {
            if (!hasState())
                formatter.format("%n", new java.lang.Object[0]);
            else
                formatter.format("0x%016X,%d,%d,%d,%d,%d,%.2f,%.2f%n", new java.lang.Object[] {
                    java.lang.Long.valueOf(getGuid()), java.lang.Integer.valueOf(getHealth()), java.lang.Integer.valueOf(getAttackPower()), java.lang.Integer.valueOf(getSpellPower()), java.lang.Integer.valueOf(getResourceType()), java.lang.Integer.valueOf(getResourceAmount()), java.lang.Float.valueOf(getXPosition()), java.lang.Float.valueOf(getYPosition())
                });
        }

        private int entryIndex;
        private int subjectStateIndex;
        final com.wol3.client.data.SubjectStateList this$0;

        public Cursor()
        {
            _fld0 = com.wol3.client.data.SubjectStateList.this;
            super();
        }
    }


    public SubjectStateList()
    {
        stateCount = 0;
    }

    public void addEmpty()
    {
        indices.add(-1);
        checkState();
    }

    public void add(com.wol3.client.data.Cursor cursor)
    {
        if (!cursor.hasState())
            addEmpty();
        else
            add(cursor.getGuid(), cursor.getHealth(), cursor.getAttackPower(), cursor.getSpellPower(), cursor.getResourceType(), cursor.getResourceAmount(), cursor.getXPosition(), cursor.getYPosition());
    }

    public void add(long guid, int health, int attackPower, int spellPower, int resourceType, int resourceAmount, 
            float xPosition, float yPosition)
    {
        indices.add(stateCount++);
        guidList.add(guid);
        healthList.add(health);
        attackPowerList.add(attackPower);
        spellPowerList.add(spellPower);
        resourceTypeList.add(resourceType);
        resourceAmountList.add(resourceAmount);
        xPositionList.add(xPosition);
        yPositionList.add(yPosition);
        checkState();
    }

    public int entryCount()
    {
        return indices.size();
    }

    public com.wol3.client.data.Cursor getCursor()
    {
        return new Cursor();
    }

    public com.wol3.client.data.SubjectStateList subList(int fromIndex, int toIndex)
    {
        com.wol3.client.data.SubjectStateList stateList = new SubjectStateList();
        com.wol3.client.data.Cursor cursor = getCursor();
        for (int i = fromIndex; i <= toIndex; i++)
        {
            cursor.setEntryIndex(i);
            stateList.add(cursor);
        }

        return stateList;
    }

    private void checkState()
        throws java.lang.IllegalStateException
    {
        checkStateCount("GUID", guidList.size());
        checkStateCount("health", healthList.size());
        checkStateCount("attack power", attackPowerList.size());
        checkStateCount("spell power", spellPowerList.size());
        checkStateCount("resource type", resourceTypeList.size());
        checkStateCount("resource amount", resourceAmountList.size());
        checkStateCount("x-position", xPositionList.size());
        checkStateCount("y-position", yPositionList.size());
    }

    private void checkStateCount(java.lang.String name, int count)
        throws java.lang.IllegalStateException
    {
        if (stateCount != count)
            throw new IllegalStateException(java.lang.String.format("State count mismatch (%s): expected %d, found %d", new java.lang.Object[] {
                name, java.lang.Integer.valueOf(stateCount), java.lang.Integer.valueOf(count)
            }));
        else
            return;
    }

    public void writeToBuffer(java.nio.ByteBuffer buffer, com.wol3.client.data.BinaryCombatLog combatLog, int entryOffset)
    {
        com.wol3.client.data.SubjectStateListWriter writer = new SubjectStateListWriter(this, combatLog, entryOffset);
        writer.writeToBuffer(buffer);
    }

    public static final byte MAGIC[] = {
        83, 85, 66, 73, 78, 70, 79, 50
    };
    private int stateCount;
    final org.apache.commons.collections.primitives.IntList indices = new ArrayIntList();
    final org.apache.commons.collections.primitives.LongList guidList = new ArrayLongList();
    final org.apache.commons.collections.primitives.IntList healthList = new ArrayIntList();
    final org.apache.commons.collections.primitives.IntList attackPowerList = new ArrayIntList();
    final org.apache.commons.collections.primitives.IntList spellPowerList = new ArrayIntList();
    final org.apache.commons.collections.primitives.IntList resourceTypeList = new ArrayIntList();
    final org.apache.commons.collections.primitives.IntList resourceAmountList = new ArrayIntList();
    final org.apache.commons.collections.primitives.FloatList xPositionList = new ArrayFloatList();
    final org.apache.commons.collections.primitives.FloatList yPositionList = new ArrayFloatList();

}
