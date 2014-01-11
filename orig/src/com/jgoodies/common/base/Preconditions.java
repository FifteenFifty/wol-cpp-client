// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   Preconditions.java

package com.jgoodies.common.base;


// Referenced classes of package com.jgoodies.common.base:
//            Strings

public final class Preconditions
{

    private Preconditions()
    {
    }

    public static void checkArgument(boolean expression, java.lang.String message)
    {
        if (!expression)
            throw new IllegalArgumentException(message);
        else
            return;
    }

    public static transient void checkArgument(boolean expression, java.lang.String messageFormat, java.lang.Object messageArgs[])
    {
        if (!expression)
            throw new IllegalArgumentException(com.jgoodies.common.base.Preconditions.format(messageFormat, messageArgs));
        else
            return;
    }

    public static java.lang.Object checkNotNull(java.lang.Object reference, java.lang.String message)
    {
        if (reference == null)
            throw new NullPointerException(message);
        else
            return reference;
    }

    public static transient java.lang.Object checkNotNull(java.lang.Object reference, java.lang.String messageFormat, java.lang.Object messageArgs[])
    {
        if (reference == null)
            throw new NullPointerException(com.jgoodies.common.base.Preconditions.format(messageFormat, messageArgs));
        else
            return reference;
    }

    public static void checkState(boolean expression, java.lang.String message)
    {
        if (!expression)
            throw new IllegalStateException(message);
        else
            return;
    }

    public static transient void checkState(boolean expression, java.lang.String messageFormat, java.lang.Object messageArgs[])
    {
        if (!expression)
            throw new IllegalStateException(com.jgoodies.common.base.Preconditions.format(messageFormat, messageArgs));
        else
            return;
    }

    public static java.lang.String checkNotBlank(java.lang.String str, java.lang.String message)
    {
        com.jgoodies.common.base.Preconditions.checkNotNull(str, message);
        com.jgoodies.common.base.Preconditions.checkArgument(com.jgoodies.common.base.Strings.isNotBlank(str), message);
        return str;
    }

    public static transient java.lang.String checkNotBlank(java.lang.String str, java.lang.String messageFormat, java.lang.Object messageArgs[])
    {
        com.jgoodies.common.base.Preconditions.checkNotNull(str, messageFormat, messageArgs);
        com.jgoodies.common.base.Preconditions.checkArgument(com.jgoodies.common.base.Strings.isNotBlank(str), messageFormat, messageArgs);
        return str;
    }

    static transient java.lang.String format(java.lang.String messageFormat, java.lang.Object messageArgs[])
    {
        return java.lang.String.format(messageFormat, messageArgs);
    }
}
