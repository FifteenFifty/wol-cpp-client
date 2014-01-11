// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   InvalidTimeException.java

package com.wol3.client.data;


// Referenced classes of package com.wol3.client.data:
//            InvalidLineException

public class InvalidTimeException extends com.wol3.client.data.InvalidLineException
{

    public InvalidTimeException(int line)
    {
        super(line);
    }

    public InvalidTimeException(int line, java.lang.String message)
    {
        super(line, message);
    }

    public InvalidTimeException(int line, java.lang.Throwable cause)
    {
        super(line, cause);
    }

    public InvalidTimeException(int line, java.lang.String message, java.lang.Throwable cause)
    {
        super(line, message, cause);
    }

    private static final long serialVersionUID = 1L;
}
