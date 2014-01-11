// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   FileSplitter.java

package com.wol3.client.forms;

import java.io.OutputStream;
import java.util.zip.ZipOutputStream;

class FastZipOutputStream extends java.util.zip.ZipOutputStream
{

    public FastZipOutputStream(java.io.OutputStream s)
    {
        super(s);
        buf = new byte[4096];
    }
}
