// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   WDBFile.java

package com.wol3.client.wdb;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

// Referenced classes of package com.wol3.client.wdb:
//            WDBRow

public class WDBFile
{

    public WDBFile(java.io.File file)
        throws java.io.IOException
    {
        rows = new HashMap();
        this.file = file;
        java.io.FileInputStream fis = new FileInputStream(file);
        java.nio.channels.FileChannel channel = fis.getChannel();
        java.nio.ByteBuffer bb = java.nio.ByteBuffer.allocate((int)channel.size());
        bb.order(java.nio.ByteOrder.LITTLE_ENDIAN);
        channel.read(bb);
        bb.flip();
        data = new byte[bb.limit()];
        bb.get(data);
        bb.position(0);
        this.bb = bb;
        readHeaders();
        readRows();
    }

    private void readHeaders()
    {
        signature = getRevString(0, 4);
        version = getUnsignedInt(4);
        language = getRevString(8, 12);
        rowLength = getUnsignedInt(12);
        headerUnknown = getUnsignedInt(16);
    }

    private void readRows()
    {
        bb.position(20);
        do
        {
            int id = bb.getInt();
            int length = bb.getInt();
            if (id != 0)
            {
                byte rowData[] = new byte[length];
                bb.get(rowData);
                rows.put(java.lang.Integer.valueOf(id), new WDBRow(id, rowData));
            } else
            {
                return;
            }
        } while (true);
    }

    private java.lang.String getRevString(int from, int to)
    {
        byte stringData[] = java.util.Arrays.copyOfRange(data, from, to);
        java.lang.String string = new String(stringData, utf8);
        char c2[] = new char[string.length()];
        for (int i = 0; i < c2.length; i++)
            c2[c2.length - i - 1] = string.charAt(i);

        return new String(c2);
    }

    private long getUnsignedInt(int from)
    {
        bb.position(from);
        long m00 = bb.getInt();
        return m00 & 0xffffffffL;
    }

    public static void main(java.lang.String args[])
        throws java.io.IOException
    {
        java.lang.String file = "C:\\games\\World of Warcraft\\Cache\\WDB\\enGB\\itemcache.wdb";
        com.wol3.client.wdb.WDBFile wdb = new WDBFile(new File(file));
        wdb.printStats();
        com.wol3.client.wdb.WDBRow row;
        for (java.util.Iterator iterator = wdb.rows.values().iterator(); iterator.hasNext(); row.printItemData())
            row = (com.wol3.client.wdb.WDBRow)iterator.next();

    }

    private void printStats()
    {
        java.lang.System.out.printf("WDB File %s\n", new java.lang.Object[] {
            file.getAbsoluteFile()
        });
        java.lang.System.out.printf("Signature: %s, language: %s, version: %d, rowLength: %d\n", new java.lang.Object[] {
            signature, language, java.lang.Long.valueOf(version), java.lang.Long.valueOf(rowLength)
        });
        java.lang.System.out.printf("%d rows\n", new java.lang.Object[] {
            java.lang.Integer.valueOf(rows.size())
        });
    }

    java.io.File file;
    byte data[];
    java.nio.ByteBuffer bb;
    static java.nio.charset.Charset utf8;
    static java.nio.charset.CharsetDecoder utf8decoder;
    java.lang.String signature;
    long version;
    java.lang.String language;
    long rowLength;
    long headerUnknown;
    java.util.HashMap rows;

    static 
    {
        utf8 = java.nio.charset.Charset.forName("UTF-8");
        utf8decoder = utf8.newDecoder();
    }
}
