// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   SubjectInfoPoolEntryWriter.java

package com.wol3.client.data;

import com.wol3.util.ByteBufferUtils;
import java.nio.ByteBuffer;
import org.apache.commons.collections.primitives.ArrayByteList;
import org.apache.commons.collections.primitives.ArrayIntList;
import org.apache.commons.collections.primitives.ArrayShortList;
import org.apache.commons.collections.primitives.IntList;

// Referenced classes of package com.wol3.client.data:
//            SubjectInfoPool

/**
 * @deprecated Class SubjectInfoPoolEntryWriter is deprecated
 */

public class SubjectInfoPoolEntryWriter
{

    public SubjectInfoPoolEntryWriter()
    {
    }

    public void initialize(com.wol3.client.data.SubjectInfoPool sip, int fromIndex, int toIndex)
    {
        if (!$assertionsDisabled && (fromIndex < 0 || fromIndex > toIndex || toIndex > sip.pooledId.size()))
            throw new AssertionError();
        this.fromIndex = fromIndex;
        length = toIndex - fromIndex;
        formats = new byte[length];
        uBytes = new ArrayByteList();
        uShorts = new ArrayShortList();
        ints = new ArrayIntList();
        if (length == 0)
            return;
        for (int i = 0; i < length; i++)
        {
            int j = fromIndex + i;
            formats[i] |= save(sip.pooledId.get(j));
            formats[i] |= save(sip.health.get(j)) << 2;
            formats[i] |= save(sip.resourceAmount.get(j)) << 4;
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

    public void writeTo(java.nio.ByteBuffer bb)
    {
        bb.putInt(fromIndex);
        com.wol3.util.ByteBufferUtils.putBytes(bb, formats);
        com.wol3.util.ByteBufferUtils.putBytes(bb, uBytes.toArray());
        com.wol3.util.ByteBufferUtils.putShorts(bb, uShorts.toArray());
        com.wol3.util.ByteBufferUtils.putInts(bb, ints.toArray());
    }

    public static final byte TYPE_NONE = 0;
    public static final byte TYPE_UBYTE = 1;
    public static final byte TYPE_USHORT = 2;
    public static final byte TYPE_INT = 3;
    int fromIndex;
    int length;
    public byte formats[];
    public org.apache.commons.collections.primitives.ArrayByteList uBytes;
    public org.apache.commons.collections.primitives.ArrayShortList uShorts;
    public org.apache.commons.collections.primitives.ArrayIntList ints;
    static final boolean $assertionsDisabled = !com/wol3/client/data/SubjectInfoPoolEntryWriter.desiredAssertionStatus();

}
