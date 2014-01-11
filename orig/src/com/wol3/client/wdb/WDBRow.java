// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   WDBRow.java

package com.wol3.client.wdb;

import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

// Referenced classes of package com.wol3.client.wdb:
//            WDBFile

public class WDBRow
{

    public WDBRow(int id, byte data[])
    {
        this.id = id;
        this.data = data;
    }

    public void printItemData()
    {
        createBuffer();
        int classId = getInt();
        int subClassId = getInt();
        getInt();
        java.lang.String name = getString();
        java.lang.System.out.printf("Item %d - %s, class %d, subclass %d\n", new java.lang.Object[] {
            java.lang.Integer.valueOf(id), name, java.lang.Integer.valueOf(classId), java.lang.Integer.valueOf(subClassId)
        });
    }

    int getInt()
    {
        return bb.getInt();
    }

    java.lang.String getString()
    {
        int end = findNul();
        int start = bb.position();
        if (end == -1)
        {
            return null;
        } else
        {
            byte stringData[] = new byte[end - start];
            bb.get(stringData);
            return new String(stringData, com.wol3.client.wdb.WDBFile.utf8);
        }
    }

    int findNul()
    {
        for (int i = bb.position(); i < data.length; i++)
            if (data[i] == 0)
                return i;

        return -1;
    }

    void createBuffer()
    {
        if (bb == null)
        {
            bb = java.nio.ByteBuffer.wrap(data);
            bb.order(java.nio.ByteOrder.LITTLE_ENDIAN);
        }
    }

    public int id;
    public byte data[];
    java.nio.ByteBuffer bb;
}
