// Decompiled by Jad v1.5.8e. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.geocities.com/kpdus/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   UIHelpers.java

package com.wol3.util;

import java.awt.*;

public class UIHelpers
{

    public UIHelpers()
    {
    }

    public static void center(Component frame)
    {
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        Rectangle maxBounds = ge.getMaximumWindowBounds();
        Rectangle current = frame.getBounds();
        current.x = (maxBounds.width - current.width) / 2;
        current.y = (maxBounds.height - current.height) / 2;
        frame.setBounds(current);
    }
}
