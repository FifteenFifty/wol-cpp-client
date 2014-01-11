// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   Actor.java

package com.wol3.client.data;


public class Actor
{

    public Actor(long guid, java.lang.String name, int flags, int raidFlags)
    {
        GUID = guid;
        this.name = name;
        this.flags = flags;
        this.raidFlags = raidFlags;
    }

    public com.wol3.client.data.Actor set(long guid, java.lang.String name, int flags, int raidFlags)
    {
        GUID = guid;
        this.name = name;
        this.flags = flags;
        this.raidFlags = raidFlags;
        return this;
    }

    public boolean equals(java.lang.Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        com.wol3.client.data.Actor other = (com.wol3.client.data.Actor)obj;
        if (GUID != other.GUID)
            return false;
        if (flags != other.flags)
            return false;
        if (name == null)
        {
            if (other.name != null)
                return false;
        } else
        if (!name.equals(other.name))
            return false;
        return raidFlags == other.raidFlags;
    }

    public int hashCode()
    {
        return (int)(GUID ^ GUID >> 32) ^ flags ^ raidFlags ^ name.hashCode();
    }

    public int getType()
    {
        long type = GUID & 0xf0000000000000L;
        return (int)(type >> 52);
    }

    public java.lang.String toString()
    {
        return java.lang.String.format("<Actor(%s, 0x%016X, 0x%X, 0x%X)>", new java.lang.Object[] {
            name, java.lang.Long.valueOf(GUID), java.lang.Integer.valueOf(flags), java.lang.Integer.valueOf(raidFlags)
        });
    }

    public static final int TYPE_PLAYER = 0;
    public static final int TYPE_CREATURE = 3;
    public static final int TYPE_PET = 4;
    public int id;
    public long GUID;
    public java.lang.String name;
    public int flags;
    public int raidFlags;
}
