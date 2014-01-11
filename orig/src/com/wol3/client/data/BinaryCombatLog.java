// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   BinaryCombatLog.java

package com.wol3.client.data;

import com.wol3.util.ByteBufferUtils;
import com.wol3.util.perf.SimpleProfiler;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.Properties;
import java.util.TimeZone;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import org.apache.commons.collections.primitives.ArrayIntList;
import org.tukaani.xz.LZMA2Options;
import org.tukaani.xz.XZOutputStream;

// Referenced classes of package com.wol3.client.data:
//            ActorPool, EventPool, EntryList, SubjectStateList, 
//            Actor, Event

public class BinaryCombatLog
{
    public static final class CompressFormat extends java.lang.Enum
    {

        public static com.wol3.client.data.CompressFormat getFormatForFilename(java.lang.String filename)
        {
            java.lang.String lcFilename = filename.toLowerCase();
            if (lcFilename.endsWith(".w4z") || lcFilename.endsWith(".w4c"))
                return DEFLATE;
            if (lcFilename.endsWith(".w4.xz") || lcFilename.endsWith(".w4c.xz"))
            {
                return XZ;
            } else
            {
                java.lang.System.err.println((new StringBuilder("Unknown file extension for filename: ")).append(filename).toString());
                return DEFLATE;
            }
        }

        public static com.wol3.client.data.CompressFormat[] values()
        {
            com.wol3.client.data.CompressFormat acompressformat[];
            int i;
            com.wol3.client.data.CompressFormat acompressformat1[];
            java.lang.System.arraycopy(acompressformat = VALUES, 0, acompressformat1 = new com.wol3.client.data.CompressFormat[i = acompressformat.length], 0, i);
            return acompressformat1;
        }

        public static com.wol3.client.data.CompressFormat valueOf(java.lang.String s)
        {
            return (com.wol3.client.data.CompressFormat)java.lang.Enum.valueOf(com/wol3/client/data/BinaryCombatLog$CompressFormat, s);
        }

        public static final com.wol3.client.data.CompressFormat DEFLATE;
        public static final com.wol3.client.data.CompressFormat XZ;
        private static final com.wol3.client.data.CompressFormat ENUM$VALUES[];

        static 
        {
            DEFLATE = new CompressFormat("DEFLATE", 0);
            XZ = new CompressFormat("XZ", 1);
            VALUES = (new com.wol3.client.data.CompressFormat[] {
                DEFLATE, XZ
            });
        }

        private CompressFormat(java.lang.String s, int i)
        {
            super(s, i);
        }
    }

    public static final class Format extends java.lang.Enum
    {

        public boolean isBefore(com.wol3.client.data.Format format)
        {
            return compareTo(format) < 0;
        }

        public boolean isAtLeast(com.wol3.client.data.Format format)
        {
            return !isBefore(format);
        }

        public static com.wol3.client.data.Format[] values()
        {
            com.wol3.client.data.Format aformat[];
            int i;
            com.wol3.client.data.Format aformat1[];
            java.lang.System.arraycopy(aformat = VALUES, 0, aformat1 = new com.wol3.client.data.Format[i = aformat.length], 0, i);
            return aformat1;
        }

        public static com.wol3.client.data.Format valueOf(java.lang.String s)
        {
            return (com.wol3.client.data.Format)java.lang.Enum.valueOf(com/wol3/client/data/BinaryCombatLog$Format, s);
        }

        public static final com.wol3.client.data.Format BASE;
        public static final com.wol3.client.data.Format PATCH_4_2;
        /**
         * @deprecated Field PATCH_5_2 is deprecated
         */
        public static final com.wol3.client.data.Format PATCH_5_2;
        public static final com.wol3.client.data.Format PATCH_5_2_NEW;
        public static final com.wol3.client.data.Format PATCH_5_4_2;
        public final boolean hasRaidFlags;
        /**
         * @deprecated Field hasSubjectStates is deprecated
         */
        public final boolean hasSubjectStates;
        public final boolean hasNewSubjectStates;
        public final boolean hasSubjectPosition;
        private static final com.wol3.client.data.Format ENUM$VALUES[];

        static 
        {
            BASE = new Format("BASE", 0, false, false, false, false);
            PATCH_4_2 = new Format("PATCH_4_2", 1, true, false, false, false);
            PATCH_5_2 = new Format("PATCH_5_2", 2, true, true, false, false);
            PATCH_5_2_NEW = new Format("PATCH_5_2_NEW", 3, true, false, true, false);
            PATCH_5_4_2 = new Format("PATCH_5_4_2", 4, true, false, true, true);
            VALUES = (new com.wol3.client.data.Format[] {
                BASE, PATCH_4_2, PATCH_5_2, PATCH_5_2_NEW, PATCH_5_4_2
            });
        }

        private Format(java.lang.String s, int i, boolean hasRaidFlags, boolean hasSubjectStates, boolean hasSubjectStatesNew, boolean hasSubjectPosition)
        {
            super(s, i);
            this.hasRaidFlags = hasRaidFlags;
            this.hasSubjectStates = hasSubjectStates;
            hasNewSubjectStates = hasSubjectStatesNew;
            this.hasSubjectPosition = hasSubjectPosition;
            if (hasSubjectStates && hasSubjectStatesNew)
                throw new IllegalArgumentException("Subject state formats are mutually exclusive");
            else
                return;
        }
    }


    public BinaryCombatLog()
    {
        properties = new Properties();
        actorPool = new ActorPool();
        eventPool = new EventPool();
        entryList = new EntryList();
        stateList = new SubjectStateList();
        properties.setProperty("timezone", java.util.TimeZone.getDefault().getID());
        properties.setProperty("client.version", java.lang.Integer.toString(5421));
    }

    public com.wol3.client.data.Actor getActor(int actorId)
    {
        if (actorId == -1)
            return null;
        else
            return (com.wol3.client.data.Actor)actorPool.list.get(actorId);
    }

    public com.wol3.client.data.Actor getSourceActor(int entryIndex)
    {
        return getActor(entryList.sources.get(entryIndex));
    }

    public com.wol3.client.data.Actor getTargetActor(int entryIndex)
    {
        return getActor(entryList.targets.get(entryIndex));
    }

    public void writeTo(java.nio.ByteBuffer bb, int fromActor, int fromEvent, int fromEntry)
    {
        com.wol3.util.perf.SimpleProfiler.staticEnter("BCL:writeTo");
        bb.putLong(0x200902131029L);
        java.io.ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
        try
        {
            properties.setProperty("format", format.name());
            properties.store(bytesOut, "");
        }
        catch (java.io.IOException e)
        {
            throw new RuntimeException(e);
        }
        com.wol3.util.ByteBufferUtils.putBytes(bb, bytesOut.toByteArray());
        actorPool.writeTo(bb, format, fromActor);
        eventPool.writeTo(bb, fromEvent);
        entryList.writeTo(bb, actorPool, fromEntry);
        if (format.hasNewSubjectStates)
            stateList.writeToBuffer(bb, this, fromEntry);
        com.wol3.util.perf.SimpleProfiler.staticExit();
    }

    public void writeTo(java.nio.ByteBuffer bb)
    {
        writeTo(bb, 0, 0, 0);
    }

    public void writeCompressedTo(java.io.ByteArrayOutputStream out, int fromActor, int fromEvent, int fromEntry)
        throws java.io.IOException
    {
        writeCompressedTo(out, fromActor, fromEvent, fromEntry, com.wol3.client.data.CompressFormat.XZ, 6);
    }

    public void writeCompressedTo(java.io.ByteArrayOutputStream out, int fromActor, int fromEvent, int fromEntry, com.wol3.client.data.CompressFormat format, int level)
        throws java.io.IOException
    {
        java.nio.ByteBuffer buffer = java.nio.ByteBuffer.allocate(0x4000000);
        buffer.order(java.nio.ByteOrder.LITTLE_ENDIAN);
        java.io.OutputStream compressedOut;
        switch (com.wol3.client.data.BinaryCombatLog.$SWITCH_TABLE$com$wol3$client$data$BinaryCombatLog$CompressFormat()[format.ordinal()])
        {
        case 2: // '\002'
            compressedOut = new XZOutputStream(out, new LZMA2Options(level));
            break;

        case 1: // '\001'
            java.util.zip.Deflater deflater = new Deflater(level);
            compressedOut = new DeflaterOutputStream(out, deflater, 8192);
            break;

        default:
            throw new RuntimeException((new StringBuilder("Unknown compression format ")).append(format).toString());
        }
        writeTo(buffer, fromActor, fromEvent, fromEntry);
        long start = java.lang.System.currentTimeMillis();
        java.lang.System.out.println((new StringBuilder("[Debug] Uncompressed size: ")).append(buffer.position()).toString());
        com.wol3.util.perf.SimpleProfiler.staticEnter("BCL:writeCompressedTo");
        buffer.flip();
        byte bytes[] = new byte[buffer.limit()];
        buffer.get(bytes);
        compressedOut.write(bytes);
        compressedOut.flush();
        compressedOut.close();
        int size = out.size();
        java.lang.System.out.printf("[Debug] Compressed size: %d, compression ratio: %.2f%% in %d ms%n", new java.lang.Object[] {
            java.lang.Integer.valueOf(size), java.lang.Double.valueOf((100D * (double)size) / (double)bytes.length), java.lang.Long.valueOf(java.lang.System.currentTimeMillis() - start)
        });
        com.wol3.util.perf.SimpleProfiler.staticExit();
    }

    public void writeCompressedTo(java.io.File file, int fromActor, int fromEvent, int fromEntry)
        throws java.io.IOException
    {
        writeCompressedTo(file, fromActor, fromEvent, fromEntry, com.wol3.client.data.CompressFormat.XZ, 6);
    }

    public void writeCompressedTo(java.io.File file, int fromActor, int fromEvent, int fromEntry, com.wol3.client.data.CompressFormat format, int level)
        throws java.io.IOException
    {
        java.io.ByteArrayOutputStream out;
        java.io.FileOutputStream fileOut;
        out = new ByteArrayOutputStream();
        fileOut = null;
        fileOut = new FileOutputStream(file);
        writeCompressedTo(out, fromActor, fromEvent, fromEntry, format, level);
        fileOut.write(out.toByteArray());
        break MISSING_BLOCK_LABEL_64;
        java.lang.Exception exception;
        exception;
        if (fileOut != null)
            fileOut.close();
        throw exception;
        if (fileOut != null)
            fileOut.close();
        return;
    }

    public void finish()
    {
        eventPool.finish();
        com.wol3.client.data.Event.finish();
        entryList.trim();
    }

    static int[] $SWITCH_TABLE$com$wol3$client$data$BinaryCombatLog$CompressFormat()
    {
        $SWITCH_TABLE$com$wol3$client$data$BinaryCombatLog$CompressFormat;
        if ($SWITCH_TABLE$com$wol3$client$data$BinaryCombatLog$CompressFormat == null) goto _L2; else goto _L1
_L1:
        return;
_L2:
        JVM INSTR pop ;
        int ai[] = new int[com.wol3.client.data.CompressFormat.values().length];
        try
        {
            ai[com.wol3.client.data.CompressFormat.DEFLATE.ordinal()] = 1;
        }
        catch (java.lang.NoSuchFieldError _ex) { }
        try
        {
            ai[com.wol3.client.data.CompressFormat.XZ.ordinal()] = 2;
        }
        catch (java.lang.NoSuchFieldError _ex) { }
        return $SWITCH_TABLE$com$wol3$client$data$BinaryCombatLog$CompressFormat = ai;
    }

    public static final long MAGIC = 0x200902131029L;
    public java.util.Properties properties;
    public com.wol3.client.data.Format format;
    public com.wol3.client.data.ActorPool actorPool;
    public com.wol3.client.data.EventPool eventPool;
    public com.wol3.client.data.EntryList entryList;
    public com.wol3.client.data.SubjectStateList stateList;
    private static int $SWITCH_TABLE$com$wol3$client$data$BinaryCombatLog$CompressFormat[];
}
