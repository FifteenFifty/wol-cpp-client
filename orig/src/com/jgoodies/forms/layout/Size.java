// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   Size.java

package com.jgoodies.forms.layout;

import java.awt.Container;

// Referenced classes of package com.jgoodies.forms.layout:
//            FormLayout

public interface Size
{

    public abstract int maximumSize(java.awt.Container container, java.util.List list, com.jgoodies.forms.layout.FormLayout.Measure measure, com.jgoodies.forms.layout.FormLayout.Measure measure1, com.jgoodies.forms.layout.FormLayout.Measure measure2);

    public abstract boolean compressible();

    public abstract java.lang.String encode();
}
