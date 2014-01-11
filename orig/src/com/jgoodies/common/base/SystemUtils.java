// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   SystemUtils.java

package com.jgoodies.common.base;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.util.logging.Logger;

public class SystemUtils
{

    protected SystemUtils()
    {
    }

    protected static java.lang.String getSystemProperty(java.lang.String key)
    {
        try
        {
            return java.lang.System.getProperty(key);
        }
        catch (java.lang.SecurityException e)
        {
            java.util.logging.Logger.getLogger(com/jgoodies/common/base/SystemUtils.getName()).warning((new StringBuilder()).append("Can't access the System property ").append(key).append(".").toString());
        }
        return "";
    }

    protected static boolean startsWith(java.lang.String str, java.lang.String prefix)
    {
        return str != null && str.startsWith(prefix);
    }

    private static boolean hasModernRasterizer()
    {
        try
        {
            java.lang.Class.forName("com.sun.awt.AWTUtilities");
            return true;
        }
        catch (java.lang.ClassNotFoundException e)
        {
            return false;
        }
    }

    private static boolean isWindowsXPLafEnabled()
    {
        return IS_OS_WINDOWS && java.lang.Boolean.TRUE.equals(java.awt.Toolkit.getDefaultToolkit().getDesktopProperty("win.xpstyle.themeActive")) && com.jgoodies.common.base.SystemUtils.getSystemProperty("swing.noxp") == null;
    }

    private static boolean isLowResolution()
    {
        try
        {
            return java.awt.Toolkit.getDefaultToolkit().getScreenResolution() < 120;
        }
        catch (java.awt.HeadlessException e)
        {
            return true;
        }
    }

    protected static final java.lang.String OS_NAME = com.jgoodies.common.base.SystemUtils.getSystemProperty("os.name");
    protected static final java.lang.String OS_VERSION = com.jgoodies.common.base.SystemUtils.getSystemProperty("os.version");
    protected static final java.lang.String JAVA_VERSION = com.jgoodies.common.base.SystemUtils.getSystemProperty("java.version");
    public static final boolean IS_OS_LINUX = com.jgoodies.common.base.SystemUtils.startsWith(OS_NAME, "Linux") || com.jgoodies.common.base.SystemUtils.startsWith(OS_NAME, "LINUX");
    public static final boolean IS_OS_MAC = com.jgoodies.common.base.SystemUtils.startsWith(OS_NAME, "Mac OS");
    public static final boolean IS_OS_SOLARIS = com.jgoodies.common.base.SystemUtils.startsWith(OS_NAME, "Solaris");
    public static final boolean IS_OS_WINDOWS = com.jgoodies.common.base.SystemUtils.startsWith(OS_NAME, "Windows");
    public static final boolean IS_OS_WINDOWS_98 = com.jgoodies.common.base.SystemUtils.startsWith(OS_NAME, "Windows 9") && com.jgoodies.common.base.SystemUtils.startsWith(OS_VERSION, "4.1");
    public static final boolean IS_OS_WINDOWS_ME = com.jgoodies.common.base.SystemUtils.startsWith(OS_NAME, "Windows") && com.jgoodies.common.base.SystemUtils.startsWith(OS_VERSION, "4.9");
    public static final boolean IS_OS_WINDOWS_2000 = com.jgoodies.common.base.SystemUtils.startsWith(OS_NAME, "Windows") && com.jgoodies.common.base.SystemUtils.startsWith(OS_VERSION, "5.0");
    public static final boolean IS_OS_WINDOWS_XP = com.jgoodies.common.base.SystemUtils.startsWith(OS_NAME, "Windows") && com.jgoodies.common.base.SystemUtils.startsWith(OS_VERSION, "5.1");
    public static final boolean IS_OS_WINDOWS_VISTA = com.jgoodies.common.base.SystemUtils.startsWith(OS_NAME, "Windows") && com.jgoodies.common.base.SystemUtils.startsWith(OS_VERSION, "6.0");
    public static final boolean IS_OS_WINDOWS_6_OR_LATER = com.jgoodies.common.base.SystemUtils.startsWith(OS_NAME, "Windows") && com.jgoodies.common.base.SystemUtils.startsWith(OS_VERSION, "6.");
    public static final boolean IS_JAVA_5;
    public static final boolean IS_JAVA_6;
    public static final boolean IS_JAVA_6_OR_LATER;
    public static final boolean IS_JAVA_7 = com.jgoodies.common.base.SystemUtils.startsWith(JAVA_VERSION, "1.7");
    public static final boolean IS_JAVA_7_OR_LATER;
    public static final boolean HAS_MODERN_RASTERIZER = com.jgoodies.common.base.SystemUtils.hasModernRasterizer();
    public static final boolean IS_LAF_WINDOWS_XP_ENABLED = com.jgoodies.common.base.SystemUtils.isWindowsXPLafEnabled();
    public static final boolean IS_LOW_RESOLUTION = com.jgoodies.common.base.SystemUtils.isLowResolution();
    private static final java.lang.String AWT_UTILITIES_CLASS_NAME = "com.sun.awt.AWTUtilities";

    static 
    {
        IS_JAVA_5 = com.jgoodies.common.base.SystemUtils.startsWith(JAVA_VERSION, "1.5");
        IS_JAVA_6 = com.jgoodies.common.base.SystemUtils.startsWith(JAVA_VERSION, "1.6");
        IS_JAVA_6_OR_LATER = !IS_JAVA_5;
        IS_JAVA_7_OR_LATER = !IS_JAVA_5 && !IS_JAVA_6;
    }
}
