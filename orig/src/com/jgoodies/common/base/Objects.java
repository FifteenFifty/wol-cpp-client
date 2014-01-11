// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   Objects.java

package com.jgoodies.common.base;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public final class Objects
{

    private Objects()
    {
    }

    public static java.io.Serializable deepCopy(java.io.Serializable original)
    {
        if (original == null)
            return null;
        try
        {
            java.io.ByteArrayOutputStream baos = new ByteArrayOutputStream(1024);
            java.io.ObjectOutputStream oas = new ObjectOutputStream(baos);
            oas.writeObject(original);
            oas.flush();
            java.io.ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            java.io.ObjectInputStream ois = new ObjectInputStream(bais);
            return (java.io.Serializable)ois.readObject();
        }
        catch (java.lang.Throwable e)
        {
            throw new RuntimeException("Deep copy failed", e);
        }
    }

    public static boolean equals(java.lang.Object o1, java.lang.Object o2)
    {
        return o1 == o2 || o1 != null && o1.equals(o2);
    }
}
