// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   InvalidLineException.java

package com.wol3.client.data;


public class InvalidLineException extends java.lang.Exception
{

    public InvalidLineException(int line)
    {
        this.line = line;
    }

    public InvalidLineException(int line, java.lang.String message)
    {
        super(message);
        this.line = line;
    }

    public InvalidLineException(int line, java.lang.Throwable cause)
    {
        super(cause);
        this.line = line;
    }

    public InvalidLineException(int line, java.lang.String message, java.lang.Throwable cause)
    {
        super(message, cause);
        this.line = line;
    }

    public java.lang.String toString()
    {
        java.lang.String s = getClass().getName();
        java.lang.String message = getLocalizedMessage();
        if (message == null)
            return (new StringBuilder(java.lang.String.valueOf(s))).append(" on line ").append(line).toString();
        else
            return (new StringBuilder(java.lang.String.valueOf(s))).append(" on line ").append(line).append(": ").append(message).toString();
    }

    private static final long serialVersionUID = 1L;
    protected int line;
}
