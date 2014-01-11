// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   FormUtils.java

package com.jgoodies.forms.util;

import javax.swing.LookAndFeel;
import javax.swing.UIManager;

// Referenced classes of package com.jgoodies.forms.util:
//            DefaultUnitConverter

public final class FormUtils
{

    private FormUtils()
    {
    }

    public static boolean isLafAqua()
    {
        com.jgoodies.forms.util.FormUtils.ensureValidCache();
        if (cachedIsLafAqua == null)
            cachedIsLafAqua = java.lang.Boolean.valueOf(com.jgoodies.forms.util.FormUtils.computeIsLafAqua());
        return cachedIsLafAqua.booleanValue();
    }

    public static void clearLookAndFeelBasedCaches()
    {
        cachedIsLafAqua = null;
        com.jgoodies.forms.util.DefaultUnitConverter.getInstance().clearCache();
    }

    private static boolean computeIsLafAqua()
    {
        return javax.swing.UIManager.getLookAndFeel().getID().equals("Aqua");
    }

    static void ensureValidCache()
    {
        javax.swing.LookAndFeel currentLookAndFeel = javax.swing.UIManager.getLookAndFeel();
        if (currentLookAndFeel != cachedLookAndFeel)
        {
            com.jgoodies.forms.util.FormUtils.clearLookAndFeelBasedCaches();
            cachedLookAndFeel = currentLookAndFeel;
        }
    }

    private static javax.swing.LookAndFeel cachedLookAndFeel;
    private static java.lang.Boolean cachedIsLafAqua;
}
