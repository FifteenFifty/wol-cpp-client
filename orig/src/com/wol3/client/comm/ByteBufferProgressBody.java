// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   ByteBufferProgressBody.java

package com.wol3.client.comm;

import com.wol3.util.ByteBufferUtils;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import org.apache.http.entity.mime.content.InputStreamBody;

public class ByteBufferProgressBody extends org.apache.http.entity.mime.content.InputStreamBody
{
    private class ProgressOutputStream extends java.io.OutputStream
    {

        public void write(int b)
            throws java.io.IOException
        {
            out.write(b);
            sent++;
        }

        public void write(byte b[])
            throws java.io.IOException
        {
            out.write(b);
            sent += b.length;
        }

        public void write(byte b[], int off, int len)
            throws java.io.IOException
        {
            out.write(b, off, len);
            sent += len;
        }

        public void flush()
            throws java.io.IOException
        {
            out.flush();
        }

        public void close()
            throws java.io.IOException
        {
            out.close();
        }

        private java.io.OutputStream out;
        final com.wol3.client.comm.ByteBufferProgressBody this$0;

        public ProgressOutputStream(java.io.OutputStream out)
        {
            _fld0 = com.wol3.client.comm.ByteBufferProgressBody.this;
            super();
            this.out = out;
        }
    }


    public ByteBufferProgressBody(java.nio.ByteBuffer buffer, java.lang.String name)
    {
        super(com.wol3.util.ByteBufferUtils.getInputStream(buffer), name);
        sent = 0L;
        total = 0L;
        total = buffer.remaining();
    }

    public void writeTo(java.io.OutputStream out)
        throws java.io.IOException
    {
        super.writeTo(new ProgressOutputStream(out));
    }

    public long getContentLength()
    {
        return total;
    }

    public volatile long sent;
    public volatile long total;
}
