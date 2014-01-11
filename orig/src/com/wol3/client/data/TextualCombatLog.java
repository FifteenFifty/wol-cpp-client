// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   TextualCombatLog.java

package com.wol3.client.data;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.Observable;

public class TextualCombatLog extends java.util.Observable
{
    protected class FileSizePoller extends java.lang.Thread
    {

        public void run()
        {
            try
            {
                while (!stop) 
                {
                    if (channel.size() > size)
                        fileSizeChanged();
                    try
                    {
                        java.lang.Thread.sleep(com.wol3.client.data.TextualCombatLog.INTERVAL);
                    }
                    catch (java.lang.InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                }
            }
            catch (java.io.IOException e)
            {
                e.printStackTrace();
            }
        }

        protected volatile boolean stop;
        final com.wol3.client.data.TextualCombatLog this$0;

        protected FileSizePoller()
        {
            _fld0 = com.wol3.client.data.TextualCombatLog.this;
            super();
            setDaemon(true);
        }
    }


    public TextualCombatLog(java.io.File file)
        throws java.io.IOException
    {
        channel = (new FileInputStream(file)).getChannel();
        poller = new FileSizePoller();
        size = 0L;
        end = 0L;
        if (channel.size() > 0x100000L)
            end = channel.size() - 0x100000L;
        updateLastValidPosition();
    }

    public long getSize()
    {
        return size;
    }

    public void start()
    {
        if (poller == null)
            poller = new FileSizePoller();
        poller.start();
    }

    public void stop()
    {
        poller.stop = true;
        poller.interrupt();
        poller = null;
    }

    protected void updateLastValidPosition()
        throws java.io.IOException
    {
label0:
        {
            size = channel.size();
            java.nio.ByteBuffer buffer = java.nio.ByteBuffer.allocate((int)(size - end));
            channel.position(end);
            channel.read(buffer);
            buffer.flip();
            buffer.order(java.nio.ByteOrder.LITTLE_ENDIAN);
            for (int i = buffer.limit(); i > 0; i--)
            {
                byte b = buffer.get(i - 1);
                if (b != 10 && b != 13)
                    continue;
                end += i;
                break label0;
            }

            return;
        }
    }

    protected void fileSizeChanged()
        throws java.io.IOException
    {
        updateLastValidPosition();
        setChanged();
        notifyObservers();
        clearChanged();
    }

    public java.io.InputStream getInputStream(long start, long end)
        throws java.io.IOException
    {
        java.nio.ByteBuffer buffer = getBuffer(start, end);
        byte data[] = new byte[buffer.limit()];
        buffer.get(data);
        return new ByteArrayInputStream(data);
    }

    public java.nio.ByteBuffer getBuffer(long start, long end)
        throws java.io.IOException
    {
        java.nio.ByteBuffer buffer = channel.map(java.nio.channels.FileChannel.MapMode.READ_ONLY, start, end - start);
        buffer.order(java.nio.ByteOrder.LITTLE_ENDIAN);
        return buffer;
    }

    public long getEnd()
    {
        return end;
    }

    public static long INTERVAL = 1000L;
    protected java.nio.channels.FileChannel channel;
    protected com.wol3.client.data.FileSizePoller poller;
    protected long size;
    protected long end;

}
