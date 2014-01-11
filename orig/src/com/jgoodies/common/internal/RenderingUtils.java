// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   RenderingUtils.java

package com.jgoodies.common.internal;

import com.jgoodies.common.base.SystemUtils;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.PrintGraphics;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.print.PrinterGraphics;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.swing.JComponent;
import javax.swing.plaf.basic.BasicGraphicsUtils;

public final class RenderingUtils
{

    private RenderingUtils()
    {
    }

    public static void drawString(javax.swing.JComponent c, java.awt.Graphics g, java.lang.String text, int x, int y)
    {
        if (drawStringMethod != null)
            try
            {
                drawStringMethod.invoke(null, new java.lang.Object[] {
                    c, g, text, java.lang.Integer.valueOf(x), java.lang.Integer.valueOf(y)
                });
                return;
            }
            catch (java.lang.IllegalArgumentException e) { }
            catch (java.lang.IllegalAccessException e) { }
            catch (java.lang.reflect.InvocationTargetException e) { }
        java.awt.Graphics2D g2 = (java.awt.Graphics2D)g;
        java.util.Map oldRenderingHints = com.jgoodies.common.internal.RenderingUtils.installDesktopHints(g2);
        javax.swing.plaf.basic.BasicGraphicsUtils.drawStringUnderlineCharAt(g, text, -1, x, y);
        if (oldRenderingHints != null)
            g2.addRenderingHints(oldRenderingHints);
    }

    public static void drawStringUnderlineCharAt(javax.swing.JComponent c, java.awt.Graphics g, java.lang.String text, int underlinedIndex, int x, int y)
    {
        if (drawStringUnderlineCharAtMethod != null)
            try
            {
                drawStringUnderlineCharAtMethod.invoke(null, new java.lang.Object[] {
                    c, g, text, new Integer(underlinedIndex), new Integer(x), new Integer(y)
                });
                return;
            }
            catch (java.lang.IllegalArgumentException e) { }
            catch (java.lang.IllegalAccessException e) { }
            catch (java.lang.reflect.InvocationTargetException e) { }
        java.awt.Graphics2D g2 = (java.awt.Graphics2D)g;
        java.util.Map oldRenderingHints = com.jgoodies.common.internal.RenderingUtils.installDesktopHints(g2);
        javax.swing.plaf.basic.BasicGraphicsUtils.drawStringUnderlineCharAt(g, text, underlinedIndex, x, y);
        if (oldRenderingHints != null)
            g2.addRenderingHints(oldRenderingHints);
    }

    public static java.awt.FontMetrics getFontMetrics(javax.swing.JComponent c, java.awt.Graphics g)
    {
        if (getFontMetricsMethod != null)
            try
            {
                return (java.awt.FontMetrics)getFontMetricsMethod.invoke(null, new java.lang.Object[] {
                    c, g
                });
            }
            catch (java.lang.IllegalArgumentException e) { }
            catch (java.lang.IllegalAccessException e) { }
            catch (java.lang.reflect.InvocationTargetException e) { }
        return c.getFontMetrics(g.getFont());
    }

    private static java.lang.reflect.Method getMethodDrawString()
    {
        try
        {
            java.lang.Class clazz = java.lang.Class.forName(SWING_UTILITIES2_NAME);
            return clazz.getMethod("drawString", new java.lang.Class[] {
                javax/swing/JComponent, java/awt/Graphics, java/lang/String, java.lang.Integer.TYPE, java.lang.Integer.TYPE
            });
        }
        catch (java.lang.ClassNotFoundException e) { }
        catch (java.lang.SecurityException e) { }
        catch (java.lang.NoSuchMethodException e) { }
        return null;
    }

    private static java.lang.reflect.Method getMethodDrawStringUnderlineCharAt()
    {
        try
        {
            java.lang.Class clazz = java.lang.Class.forName(SWING_UTILITIES2_NAME);
            return clazz.getMethod("drawStringUnderlineCharAt", new java.lang.Class[] {
                javax/swing/JComponent, java/awt/Graphics, java/lang/String, java.lang.Integer.TYPE, java.lang.Integer.TYPE, java.lang.Integer.TYPE
            });
        }
        catch (java.lang.ClassNotFoundException e) { }
        catch (java.lang.SecurityException e) { }
        catch (java.lang.NoSuchMethodException e) { }
        return null;
    }

    private static java.lang.reflect.Method getMethodGetFontMetrics()
    {
        try
        {
            java.lang.Class clazz = java.lang.Class.forName(SWING_UTILITIES2_NAME);
            return clazz.getMethod("getFontMetrics", new java.lang.Class[] {
                javax/swing/JComponent, java/awt/Graphics
            });
        }
        catch (java.lang.ClassNotFoundException e) { }
        catch (java.lang.SecurityException e) { }
        catch (java.lang.NoSuchMethodException e) { }
        return null;
    }

    private static java.util.Map installDesktopHints(java.awt.Graphics2D g2)
    {
        java.util.Map oldRenderingHints = null;
        if (com.jgoodies.common.base.SystemUtils.IS_JAVA_6_OR_LATER)
        {
            java.util.Map desktopHints = com.jgoodies.common.internal.RenderingUtils.desktopHints(g2);
            if (desktopHints != null && !desktopHints.isEmpty())
            {
                oldRenderingHints = new HashMap(desktopHints.size());
                java.awt.RenderingHints.Key key;
                for (java.util.Iterator i = desktopHints.keySet().iterator(); i.hasNext(); oldRenderingHints.put(key, g2.getRenderingHint(key)))
                    key = (java.awt.RenderingHints.Key)i.next();

                g2.addRenderingHints(desktopHints);
            }
        }
        return oldRenderingHints;
    }

    private static java.util.Map desktopHints(java.awt.Graphics2D g2)
    {
        if (com.jgoodies.common.internal.RenderingUtils.isPrinting(g2))
            return null;
        java.awt.Toolkit toolkit = java.awt.Toolkit.getDefaultToolkit();
        java.awt.GraphicsDevice device = g2.getDeviceConfiguration().getDevice();
        java.util.Map desktopHints = (java.util.Map)toolkit.getDesktopProperty((new StringBuilder()).append("awt.font.desktophints.").append(device.getIDstring()).toString());
        if (desktopHints == null)
            desktopHints = (java.util.Map)toolkit.getDesktopProperty("awt.font.desktophints");
        if (desktopHints != null)
        {
            java.lang.Object aaHint = desktopHints.get(java.awt.RenderingHints.KEY_TEXT_ANTIALIASING);
            if (aaHint == java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_OFF || aaHint == java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_DEFAULT)
                desktopHints = null;
        }
        return desktopHints;
    }

    private static boolean isPrinting(java.awt.Graphics g)
    {
        return (g instanceof java.awt.PrintGraphics) || (g instanceof java.awt.print.PrinterGraphics);
    }

    private static final java.lang.String PROP_DESKTOPHINTS = "awt.font.desktophints";
    private static final java.lang.String SWING_UTILITIES2_NAME;
    private static java.lang.reflect.Method drawStringMethod = null;
    private static java.lang.reflect.Method drawStringUnderlineCharAtMethod = null;
    private static java.lang.reflect.Method getFontMetricsMethod = null;

    static 
    {
        SWING_UTILITIES2_NAME = com.jgoodies.common.base.SystemUtils.IS_JAVA_6_OR_LATER ? "sun.swing.SwingUtilities2" : "com.sun.java.swing.SwingUtilities2";
        drawStringMethod = com.jgoodies.common.internal.RenderingUtils.getMethodDrawString();
        drawStringUnderlineCharAtMethod = com.jgoodies.common.internal.RenderingUtils.getMethodDrawStringUnderlineCharAt();
        getFontMetricsMethod = com.jgoodies.common.internal.RenderingUtils.getMethodGetFontMetrics();
    }
}
