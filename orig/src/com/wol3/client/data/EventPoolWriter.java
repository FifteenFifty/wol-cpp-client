// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   EventPoolWriter.java

package com.wol3.client.data;

import com.wol3.util.ByteBufferUtils;
import java.nio.ByteBuffer;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.collections.primitives.ArrayByteList;
import org.apache.commons.collections.primitives.ArrayIntList;
import org.apache.commons.collections.primitives.ArrayShortList;

// Referenced classes of package com.wol3.client.data:
//            EventPool, StringPool, Event

public class EventPoolWriter
{

    public EventPoolWriter(com.wol3.client.data.EventPool events)
    {
        this(events, 0, events.list.size());
    }

    public EventPoolWriter(com.wol3.client.data.EventPool events, int fromIndex, int toIndex)
    {
        this(events.list, fromIndex, toIndex);
    }

    public EventPoolWriter(java.util.List events, int fromIndex, int toIndex)
    {
        offset = -1;
        bytes = new ArrayByteList();
        shorts = new ArrayShortList();
        ints = new ArrayIntList();
        stringIndices = new ArrayShortList();
        strings = new StringPool();
        typeIndices = new ArrayShortList();
        types = new StringPool();
        eventTypeIndices = new ArrayByteList();
        eventTypes = new StringPool();
        typeBuilder = new StringBuilder(32);
        addEvents(events, fromIndex, toIndex);
    }

    protected void addEvents(java.util.List events, int fromIndex, int toIndex)
    {
        offset = fromIndex;
        java.util.List subList = events.subList(fromIndex, toIndex);
        com.wol3.client.data.Event e;
        for (java.util.Iterator iterator = subList.iterator(); iterator.hasNext(); addEvent(e))
            e = (com.wol3.client.data.Event)iterator.next();

    }

    protected void addEvent(com.wol3.client.data.Event e)
    {
        typeBuilder.setLength(0);
        eventTypeIndices.add((byte)eventTypes.indexOf(e.type));
        for (int i = 0; i < e.fields.length; i++)
        {
            java.lang.Object f = e.fields[i];
            if (f == null)
                typeBuilder.append('n');
            else
            if (f instanceof java.lang.Integer)
            {
                int num = ((java.lang.Integer)f).intValue();
                switch (num)
                {
                case 0: // '\0'
                    typeBuilder.append('0');
                    break;

                case 1: // '\001'
                    typeBuilder.append('1');
                    break;

                case 2: // '\002'
                    typeBuilder.append('!');
                    break;

                case 4: // '\004'
                    typeBuilder.append('@');
                    break;

                case 8: // '\b'
                    typeBuilder.append('#');
                    break;

                case 16: // '\020'
                    typeBuilder.append('$');
                    break;

                case 32: // ' '
                    typeBuilder.append('%');
                    break;

                case 64: // '@'
                    typeBuilder.append('^');
                    break;

                default:
                    if (num <= 255 && num >= 0)
                    {
                        typeBuilder.append('b');
                        bytes.add((byte)num);
                    } else
                    if (num <= 65535 && num >= 0)
                    {
                        typeBuilder.append('h');
                        shorts.add((short)num);
                    } else
                    {
                        typeBuilder.append('i');
                        ints.add(num);
                    }
                    break;
                }
            } else
            {
                typeBuilder.append('t');
                stringIndices.add((short)strings.indexOf((java.lang.String)f));
            }
        }

        typeIndices.add((short)types.indexOf(typeBuilder.toString()));
    }

    public void writeTo(java.nio.ByteBuffer b)
    {
        b.putLong(0xaeaeaeaeaeaeaeaeL);
        b.putInt(offset);
        com.wol3.util.ByteBufferUtils.putBytes(b, bytes.toArray());
        com.wol3.util.ByteBufferUtils.putShorts(b, shorts.toArray());
        com.wol3.util.ByteBufferUtils.putInts(b, ints.toArray());
        com.wol3.util.ByteBufferUtils.putShorts(b, stringIndices.toArray());
        com.wol3.util.ByteBufferUtils.putStrings(b, strings.toArray());
        com.wol3.util.ByteBufferUtils.putShorts(b, typeIndices.toArray());
        com.wol3.util.ByteBufferUtils.putStrings(b, types.toArray());
        com.wol3.util.ByteBufferUtils.putBytes(b, eventTypeIndices.toArray());
        com.wol3.util.ByteBufferUtils.putStrings(b, eventTypes.toArray());
    }

    protected org.apache.commons.collections.primitives.ArrayByteList bytes;
    protected org.apache.commons.collections.primitives.ArrayShortList shorts;
    protected org.apache.commons.collections.primitives.ArrayIntList ints;
    protected org.apache.commons.collections.primitives.ArrayShortList stringIndices;
    protected com.wol3.client.data.StringPool strings;
    protected org.apache.commons.collections.primitives.ArrayShortList typeIndices;
    protected com.wol3.client.data.StringPool types;
    protected org.apache.commons.collections.primitives.ArrayByteList eventTypeIndices;
    protected com.wol3.client.data.StringPool eventTypes;
    protected java.lang.StringBuilder typeBuilder;
    int readPosition;
    protected int offset;
}
