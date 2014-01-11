// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   LayoutStyle.java

package com.jgoodies.forms.util;

import com.jgoodies.common.base.SystemUtils;
import com.jgoodies.forms.layout.ConstantSize;
import com.jgoodies.forms.layout.Size;

// Referenced classes of package com.jgoodies.forms.util:
//            MacLayoutStyle, WindowsLayoutStyle

public abstract class LayoutStyle
{

    public LayoutStyle()
    {
    }

    private static com.jgoodies.forms.util.LayoutStyle initialLayoutStyle()
    {
        return ((com.jgoodies.forms.util.LayoutStyle) (com.jgoodies.common.base.SystemUtils.IS_OS_MAC ? com.jgoodies.forms.util.MacLayoutStyle.INSTANCE : com.jgoodies.forms.util.WindowsLayoutStyle.INSTANCE));
    }

    public static com.jgoodies.forms.util.LayoutStyle getCurrent()
    {
        return current;
    }

    public static void setCurrent(com.jgoodies.forms.util.LayoutStyle newLayoutStyle)
    {
        current = newLayoutStyle;
    }

    public abstract com.jgoodies.forms.layout.Size getDefaultButtonWidth();

    public abstract com.jgoodies.forms.layout.Size getDefaultButtonHeight();

    public abstract com.jgoodies.forms.layout.ConstantSize getDialogMarginX();

    public abstract com.jgoodies.forms.layout.ConstantSize getDialogMarginY();

    public abstract com.jgoodies.forms.layout.ConstantSize getTabbedDialogMarginX();

    public abstract com.jgoodies.forms.layout.ConstantSize getTabbedDialogMarginY();

    public abstract com.jgoodies.forms.layout.ConstantSize getLabelComponentPadX();

    public abstract com.jgoodies.forms.layout.ConstantSize getLabelComponentPadY();

    public abstract com.jgoodies.forms.layout.ConstantSize getRelatedComponentsPadX();

    public abstract com.jgoodies.forms.layout.ConstantSize getRelatedComponentsPadY();

    public abstract com.jgoodies.forms.layout.ConstantSize getUnrelatedComponentsPadX();

    public abstract com.jgoodies.forms.layout.ConstantSize getUnrelatedComponentsPadY();

    public abstract com.jgoodies.forms.layout.ConstantSize getNarrowLinePad();

    public abstract com.jgoodies.forms.layout.ConstantSize getLinePad();

    public abstract com.jgoodies.forms.layout.ConstantSize getParagraphPad();

    public abstract com.jgoodies.forms.layout.ConstantSize getButtonBarPad();

    public abstract boolean isLeftToRightButtonOrder();

    private static com.jgoodies.forms.util.LayoutStyle current = com.jgoodies.forms.util.LayoutStyle.initialLayoutStyle();

}
