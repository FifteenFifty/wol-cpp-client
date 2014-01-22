// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ByteBufferUtils.java

package com.wol3.util;

import com.digibites.util.BitArray;
import com.wol3.client.comm.ByteBufferProgressBody;
import java.io.*;
import java.nio.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.zip.CRC32;
import org.apache.commons.collections.primitives.*;

public class ByteBufferUtils
{
    public static final class IntArrayWriter
    {
        public static abstract class CompressionStrategy extends Enum
        {

            static byte[] identFromString(String identString)
            {
                for(int i = 0; i < identString.length(); i++)
                    if(identString.charAt(i) > '\177')
                        throw new IllegalArgumentException("Ident string must use only US-ASCII chars");

                byte ident[];
                ident = identString.getBytes("US-ASCII");
                if(ident.length != 4)
                    throw new IllegalArgumentException("Ident string must encode to 4 bytes US-ASCII");
                return ident;
                UnsupportedEncodingException e;
                e;
                throw new RuntimeException("Charset US-ASCII is not available", e);
            }

            public static CompressionStrategy forIdent(byte strategyIdent[])
            {
                CompressionStrategy acompressionstrategy[];
                int j = (acompressionstrategy = values()).length;
                for(int i = 0; i < j; i++)
                {
                    CompressionStrategy strategy = acompressionstrategy[i];
                    if(Arrays.equals(strategyIdent, strategy.ident))
                        return strategy;
                }

                throw new IllegalArgumentException(String.format("Unknown compression strategy ident: %X", new Object[] {
                    strategyIdent
                }));
            }

            public abstract int sizeOf(int ai[]);

            abstract int[] readFromBuffer(ByteBuffer bytebuffer);

            abstract void writeToBuffer(ByteBuffer bytebuffer, int ai[]);

            static int sizeOfItems(int itemCount)
            {
                return 4 + 4 * itemCount;
            }

            public static int[] deltaItems(int items[])
            {
                int deltaItems[] = (int[])items.clone();
                int i = 0;
                int currentItem;
                for(int lastItem = 0; i < deltaItems.length; lastItem = currentItem)
                {
                    deltaItems[i] = (currentItem = deltaItems[i]) - lastItem;
                    i++;
                }

                return deltaItems;
            }

            public static void undeltaItemsInline(int items[])
            {
                int i = 0;
                int currentItem;
                for(int lastItem = 0; i < items.length; lastItem = currentItem)
                {
                    currentItem = items[i] += lastItem;
                    i++;
                }

            }

            public static int itemChangeCount(int items[])
            {
                int itemChangeCount = 0;
                int i = 0;
                int currentItem;
                for(int lastItem = 0; i < items.length; lastItem = currentItem)
                {
                    currentItem = items[i];
                    if(lastItem != currentItem)
                        itemChangeCount++;
                    i++;
                }

                return itemChangeCount;
            }

            public static int itemDeltaChangeCount(int items[])
            {
                int itemDeltaChangeCount = 0;
                int i = 0;
                int lastItem = 0;
                int currentDelta;
                for(int lastDelta = 0; i < items.length; lastDelta = currentDelta)
                {
                    int currentItem = items[i];
                    currentDelta = currentItem - lastItem;
                    if(lastDelta != currentDelta)
                        itemDeltaChangeCount++;
                    i++;
                    lastItem = currentItem;
                }

                return itemDeltaChangeCount;
            }

            public static CompressionStrategy[] values()
            {
                CompressionStrategy acompressionstrategy[];
                int i;
                CompressionStrategy acompressionstrategy1[];
                System.arraycopy(acompressionstrategy = ENUM$VALUES, 0, acompressionstrategy1 = new CompressionStrategy[i = acompressionstrategy.length], 0, i);
                return acompressionstrategy1;
            }

            public static CompressionStrategy valueOf(String s)
            {
                return (CompressionStrategy)Enum.valueOf(com/wol3/util/ByteBufferUtils$IntArrayWriter$CompressionStrategy, s);
            }

            public static final CompressionStrategy NONE;
            public static final CompressionStrategy DELTA;
            public static final CompressionStrategy SPARSE;
            public static final CompressionStrategy SPARSE_DELTA;
            public final byte ident[];
            private static final CompressionStrategy ENUM$VALUES[];

            static 
            {
                NONE = new CompressionStrategy("NONE", 0, "NONE") {

                    public int sizeOf(int items[])
                    {
                        return sizeOfItems(items.length);
                    }

                    int[] readFromBuffer(ByteBuffer buffer)
                    {
                        return ByteBufferUtils.getInts(buffer);
                    }

                    void writeToBuffer(ByteBuffer buffer, int items[])
                    {
                        ByteBufferUtils.putInts(buffer, items);
                    }

                };
                DELTA = new CompressionStrategy("DELTA", 1, "DLTA") {

                    public int sizeOf(int items[])
                    {
                        return NONE.sizeOf(items);
                    }

                    int[] readFromBuffer(ByteBuffer buffer)
                    {
                        int items[] = NONE.readFromBuffer(buffer);
                        undeltaItemsInline(items);
                        return items;
                    }

                    void writeToBuffer(ByteBuffer buffer, int items[])
                    {
                        NONE.writeToBuffer(buffer, deltaItems(items));
                    }

                };
                SPARSE = new CompressionStrategy("SPARSE", 2, "SPRS") {

                    public int sizeOf(int items[])
                    {
                        return sizeOfItems(itemChangeCount(items)) + BitArray.bufferBytesRequired(items.length);
                    }

                    int[] readFromBuffer(ByteBuffer buffer)
                    {
                        BitArray itemChangeFlags = BitArray.readFromBuffer(buffer);
                        int items[] = new int[itemChangeFlags.size()];
                        int itemChangeCount = buffer.getInt();
                        int itemChangeOffset = items.length - itemChangeCount;
                        buffer.asIntBuffer().get(items, itemChangeOffset, itemChangeCount);
                        buffer.position(buffer.position() + 4 * itemChangeCount);
                        int i = 0;
                        int currentItem = 0;
                        for(; i < items.length; i++)
                        {
                            if(itemChangeFlags.get(i))
                                currentItem = items[itemChangeOffset++];
                            items[i] = currentItem;
                        }

                        return items;
                    }

                    void writeToBuffer(ByteBuffer buffer, int items[])
                    {
                        BitArray itemChangeFlags = new BitArray(items.length);
                        int changedItems[] = new int[itemChangeCount(items)];
                        int itemChangeCount = 0;
                        int i = 0;
                        int currentItem;
                        for(int lastItem = 0; i < items.length; lastItem = currentItem)
                        {
                            currentItem = items[i];
                            if(lastItem != currentItem)
                            {
                                itemChangeFlags.set(i);
                                changedItems[itemChangeCount++] = currentItem;
                            }
                            i++;
                        }

                        if(changedItems.length != itemChangeCount)
                        {
                            throw new RuntimeException(String.format("Expected item change count %d does not match actual count: %d", new Object[] {
                                Integer.valueOf(changedItems.length), Integer.valueOf(itemChangeCount)
                            }));
                        } else
                        {
                            itemChangeFlags.writeToBuffer(buffer);
                            NONE.writeToBuffer(buffer, changedItems);
                            return;
                        }
                    }

                };
                SPARSE_DELTA = new CompressionStrategy("SPARSE_DELTA", 3, "SPDT") {

                    public int sizeOf(int items[])
                    {
                        return sizeOfItems(itemDeltaChangeCount(items)) + BitArray.bufferBytesRequired(items.length);
                    }

                    int[] readFromBuffer(ByteBuffer buffer)
                    {
                        int items[] = SPARSE.readFromBuffer(buffer);
                        undeltaItemsInline(items);
                        return items;
                    }

                    void writeToBuffer(ByteBuffer buffer, int items[])
                    {
                        int deltaItems[] = deltaItems(items);
                        SPARSE.writeToBuffer(buffer, deltaItems);
                    }

                };
                ENUM$VALUES = (new CompressionStrategy[] {
                    NONE, DELTA, SPARSE, SPARSE_DELTA
                });
            }

            private CompressionStrategy(String s, int i, String ident)
            {
                super(s, i);
                this.ident = identFromString(ident);
            }

            CompressionStrategy(String s, int i, String s1, CompressionStrategy compressionstrategy)
            {
                this(s, i, s1);
            }
        }


        public void setStrategy(CompressionStrategy strategy)
        {
            this.strategy = strategy;
        }

        public void setItems(int items[])
        {
            this.items = items;
            clearBestStrategyState();
        }

        public CompressionStrategy getBestStrategy()
        {
            updateBestStrategyState();
            return bestStrategy;
        }

        public int getBestStrategySize()
        {
            updateBestStrategyState();
            return bestStrategySize;
        }

        public CompressionStrategy getCurrentStrategy()
        {
            return strategy == null ? getBestStrategy() : strategy;
        }

        public int[] readIntArray(ByteBuffer buffer)
        {
            byte strategyIdent[] = new byte[4];
            buffer.get(strategyIdent);
            CompressionStrategy strategy = CompressionStrategy.forIdent(strategyIdent);
            return strategy.readFromBuffer(buffer);
        }

        public void writeIntArray(ByteBuffer buffer, int items[])
        {
            setItems(items);
            writeIntArray(buffer);
        }

        public void writeIntArray(ByteBuffer buffer)
        {
            int startPosition = buffer.position();
            CompressionStrategy strategy = getCurrentStrategy();
            buffer.put(strategy.ident);
            strategy.writeToBuffer(buffer, items);
            int bytesWritten = buffer.position() - startPosition;
        }

        private void clearBestStrategyState()
        {
            bestStrategy = null;
            bestStrategySize = -1;
        }

        private void updateBestStrategyState()
        {
            if(bestStrategy != null)
                return;
            CompressionStrategy acompressionstrategy[];
            int j = (acompressionstrategy = CompressionStrategy.values()).length;
            for(int i = 0; i < j; i++)
            {
                CompressionStrategy strategy = acompressionstrategy[i];
                int strategySize = strategy.sizeOf(items);
                if(bestStrategy == null || strategySize < bestStrategySize)
                {
                    bestStrategy = strategy;
                    bestStrategySize = strategySize;
                }
            }

        }

        private CompressionStrategy strategy;
        private transient int items[];
        private transient CompressionStrategy bestStrategy;
        private transient int bestStrategySize;

        public IntArrayWriter()
        {
            this(null);
        }

        public IntArrayWriter(CompressionStrategy strategy)
        {
            this.strategy = null;
            items = null;
            bestStrategy = null;
            bestStrategySize = -1;
            this.strategy = strategy;
        }
    }


    public ByteBufferUtils()
    {
    }

    public static int putBits(ByteBuffer buffer, byte bits[], int bitCount)
    {
        if(8 * bits.length < bitCount)
        {
            throw new IllegalArgumentException("Not enough bits in byte array");
        } else
        {
            int byteCount = (bitCount + 7) / 8;
            buffer.put(bits, 0, byteCount);
            return byteCount;
        }
    }

    public static int putBytes(ByteBuffer b, byte bytes[])
    {
        b.putInt(bytes.length);
        b.put(bytes);
        return bytes.length;
    }

    public static byte[] getBytes(ByteBuffer b)
    {
        int length = b.getInt();
        byte dst[] = new byte[length];
        b.get(dst);
        return dst;
    }

    public static int putShorts(ByteBuffer b, short s[])
    {
        b.putInt(s.length);
        b.asShortBuffer().put(s);
        b.position(b.position() + s.length * 2);
        return s.length * 2;
    }

    public static short[] getShorts(ByteBuffer b)
    {
        int length = b.getInt();
        short dst[] = new short[length];
        b.asShortBuffer().get(dst);
        b.position(b.position() + length * 2);
        return dst;
    }

    public static int skipShorts(ByteBuffer buffer)
    {
        int count = buffer.getInt();
        int position = buffer.position();
        buffer.position(position + 2 * count);
        return count;
    }

    public static int putFloats(ByteBuffer b, float f[])
    {
        b.putInt(f.length);
        b.asFloatBuffer().put(f);
        b.position(b.position() + f.length * 4);
        return f.length * 4;
    }

    public static int putInts(ByteBuffer b, int i[])
    {
        b.putInt(i.length);
        b.asIntBuffer().put(i);
        b.position(b.position() + i.length * 4);
        return i.length * 4;
    }

    public static int putInts(ByteBuffer b, IntList intList, int offset)
    {
        int ints[] = new int[intList.size() - offset];
        int i = 0;
        for(int j = offset; i < ints.length; j++)
        {
            ints[i] = intList.get(j);
            i++;
        }

        return putInts(b, ints);
    }

    public static int[] getInts(ByteBuffer b)
    {
        int length = b.getInt();
        int dst[] = new int[length];
        b.asIntBuffer().get(dst);
        b.position(b.position() + length * 4);
        return dst;
    }

    public static int skipInts(ByteBuffer buffer)
    {
        int count = buffer.getInt();
        int position = buffer.position();
        buffer.position(position + 4 * count);
        return count;
    }

    public static int putLongs(ByteBuffer b, LongList longList, int offset)
    {
        return putLongs(b, longList.subList(offset, longList.size()));
    }

    public static int putLongs(ByteBuffer b, LongList longList)
    {
        return putLongs(b, longList.toArray());
    }

    public static int putLongs(ByteBuffer b, long l[])
    {
        b.putInt(l.length);
        b.asLongBuffer().put(l);
        b.position(b.position() + l.length * 8);
        return l.length * 8;
    }

    public static long[] getLongs(ByteBuffer b)
    {
        int length = b.getInt();
        long dst[] = new long[length];
        b.asLongBuffer().get(dst);
        b.position(b.position() + length * 8);
        return dst;
    }

    public static int skipLongs(ByteBuffer buffer)
    {
        int count = buffer.getInt();
        int position = buffer.position();
        buffer.position(position + 8 * count);
        return count;
    }

    public static int putFloats(ByteBuffer b, FloatList floatList, int offset)
    {
        return putFloats(b, floatList.subList(offset, floatList.size()));
    }

    public static int putFloats(ByteBuffer b, FloatList floatList)
    {
        return putFloats(b, floatList.toArray());
    }

    public static float[] getFloats(ByteBuffer b)
    {
        int length = b.getInt();
        float dst[] = new float[length];
        b.asFloatBuffer().get(dst);
        b.position(b.position() + length * 4);
        return dst;
    }

    public static int skipFloats(ByteBuffer b)
    {
        int length = b.getInt();
        b.position(b.position() + length * 4);
        return length;
    }

    public static byte[] stringArrayToBytes(String s[])
    {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(os);
        try
        {
            dos.writeInt(s.length);
            for(int i = 0; i < s.length; i++)
                dos.writeUTF(s[i]);

            dos.close();
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
        return os.toByteArray();
    }

    public static String[] byteArrayToStrings(byte data[])
    {
        DataInputStream dis;
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        dis = new DataInputStream(bis);
        String rs[];
        int count = dis.readInt();
        rs = new String[count];
        for(int i = 0; i < count; i++)
            rs[i] = dis.readUTF();

        return rs;
        Exception e;
        e;
        throw new RuntimeException(e);
    }

    public static int putStrings(ByteBuffer b, String s[])
    {
        byte data[] = stringArrayToBytes(s);
        return putBytes(b, data);
    }

    public static String[] getStrings(ByteBuffer b)
    {
        byte data[] = getBytes(b);
        return byteArrayToStrings(data);
    }

    public static int putString(ByteBuffer b, String s)
    {
        byte data[] = s.getBytes(utf8);
        b.putInt(data.length);
        b.put(data);
        return data.length + 4;
    }

    public static String getString(ByteBuffer b)
    {
        int length = b.getInt();
        byte data[] = new byte[length];
        b.get(data);
        return new String(data, utf8);
    }

    public static int getCRC32(ByteBuffer b)
    {
        CRC32 crc = new CRC32();
        ByteBuffer bb = b.asReadOnlyBuffer();
        byte buffer[] = new byte[8192];
        int len;
        for(; bb.hasRemaining(); crc.update(buffer, 0, len))
        {
            len = Math.min(bb.remaining(), buffer.length);
            bb.get(buffer, 0, len);
        }

        return (int)crc.getValue();
    }

    public static InputStream getInputStream(ByteBuffer b)
    {
        final ByteBuffer bb = b.asReadOnlyBuffer();
        return new InputStream() {

            public int read()
                throws IOException
            {
                if(bb.hasRemaining())
                    return bb.get() & 0xff;
                else
                    return -1;
            }

            public int read(byte b[], int off, int len)
                throws IOException
            {
                if(len == 0)
                    return 0;
                len = Math.min(len, bb.remaining());
                if(len == 0)
                {
                    return -1;
                } else
                {
                    bb.get(b, off, len);
                    return len;
                }
            }

            private final ByteBuffer val$bb;

            
            {
                bb = bytebuffer;
                super();
            }
        };
    }

    public static ByteBufferProgressBody getPartSource(ByteBuffer bb, String filename)
    {
        ByteBuffer fbb = bb.asReadOnlyBuffer();
        fbb.position(0);
        return new ByteBufferProgressBody(fbb, filename);
    }

    private static Charset utf8 = Charset.forName("UTF-8");

}
