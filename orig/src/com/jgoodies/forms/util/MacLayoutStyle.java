// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   MacLayoutStyle.java

package com.jgoodies.forms.util;

import com.jgoodies.forms.layout.ConstantSize;
import com.jgoodies.forms.layout.Size;
import com.jgoodies.forms.layout.Sizes;

// Referenced classes of package com.jgoodies.forms.util:
//            LayoutStyle

final class MacLayoutStyle extends com.jgoodies.forms.util.LayoutStyle
{

    private MacLayoutStyle()
    {
    }

    public com.jgoodies.forms.layout.Size getDefaultButtonWidth()
    {
        return BUTTON_WIDTH;
    }

    public com.jgoodies.forms.layout.Size getDefaultButtonHeight()
    {
        return BUTTON_HEIGHT;
    }

    public com.jgoodies.forms.layout.ConstantSize getDialogMarginX()
    {
        return DIALOG_MARGIN_X;
    }

    public com.jgoodies.forms.layout.ConstantSize getDialogMarginY()
    {
        return DIALOG_MARGIN_Y;
    }

    public com.jgoodies.forms.layout.ConstantSize getTabbedDialogMarginX()
    {
        return TABBED_DIALOG_MARGIN_X;
    }

    public com.jgoodies.forms.layout.ConstantSize getTabbedDialogMarginY()
    {
        return TABBED_DIALOG_MARGIN_Y;
    }

    public com.jgoodies.forms.layout.ConstantSize getLabelComponentPadX()
    {
        return LABEL_COMPONENT_PADX;
    }

    public com.jgoodies.forms.layout.ConstantSize getLabelComponentPadY()
    {
        return LABEL_COMPONENT_PADY;
    }

    public com.jgoodies.forms.layout.ConstantSize getRelatedComponentsPadX()
    {
        return RELATED_COMPONENTS_PADX;
    }

    public com.jgoodies.forms.layout.ConstantSize getRelatedComponentsPadY()
    {
        return RELATED_COMPONENTS_PADY;
    }

    public com.jgoodies.forms.layout.ConstantSize getUnrelatedComponentsPadX()
    {
        return UNRELATED_COMPONENTS_PADX;
    }

    public com.jgoodies.forms.layout.ConstantSize getUnrelatedComponentsPadY()
    {
        return UNRELATED_COMPONENTS_PADY;
    }

    public com.jgoodies.forms.layout.ConstantSize getNarrowLinePad()
    {
        return NARROW_LINE_PAD;
    }

    public com.jgoodies.forms.layout.ConstantSize getLinePad()
    {
        return LINE_PAD;
    }

    public com.jgoodies.forms.layout.ConstantSize getParagraphPad()
    {
        return PARAGRAPH_PAD;
    }

    public com.jgoodies.forms.layout.ConstantSize getButtonBarPad()
    {
        return BUTTON_BAR_PAD;
    }

    public boolean isLeftToRightButtonOrder()
    {
        return false;
    }

    static final com.jgoodies.forms.util.MacLayoutStyle INSTANCE = new MacLayoutStyle();
    private static final com.jgoodies.forms.layout.Size BUTTON_WIDTH = com.jgoodies.forms.layout.Sizes.dluX(54);
    private static final com.jgoodies.forms.layout.Size BUTTON_HEIGHT = com.jgoodies.forms.layout.Sizes.dluY(14);
    private static final com.jgoodies.forms.layout.ConstantSize DIALOG_MARGIN_X;
    private static final com.jgoodies.forms.layout.ConstantSize DIALOG_MARGIN_Y;
    private static final com.jgoodies.forms.layout.ConstantSize TABBED_DIALOG_MARGIN_X;
    private static final com.jgoodies.forms.layout.ConstantSize TABBED_DIALOG_MARGIN_Y;
    private static final com.jgoodies.forms.layout.ConstantSize LABEL_COMPONENT_PADX;
    private static final com.jgoodies.forms.layout.ConstantSize RELATED_COMPONENTS_PADX;
    private static final com.jgoodies.forms.layout.ConstantSize UNRELATED_COMPONENTS_PADX;
    private static final com.jgoodies.forms.layout.ConstantSize LABEL_COMPONENT_PADY;
    private static final com.jgoodies.forms.layout.ConstantSize RELATED_COMPONENTS_PADY;
    private static final com.jgoodies.forms.layout.ConstantSize UNRELATED_COMPONENTS_PADY;
    private static final com.jgoodies.forms.layout.ConstantSize NARROW_LINE_PAD;
    private static final com.jgoodies.forms.layout.ConstantSize LINE_PAD;
    private static final com.jgoodies.forms.layout.ConstantSize PARAGRAPH_PAD;
    private static final com.jgoodies.forms.layout.ConstantSize BUTTON_BAR_PAD;

    static 
    {
        DIALOG_MARGIN_X = com.jgoodies.forms.layout.Sizes.DLUX9;
        DIALOG_MARGIN_Y = com.jgoodies.forms.layout.Sizes.DLUY9;
        TABBED_DIALOG_MARGIN_X = com.jgoodies.forms.layout.Sizes.DLUX4;
        TABBED_DIALOG_MARGIN_Y = com.jgoodies.forms.layout.Sizes.DLUY4;
        LABEL_COMPONENT_PADX = com.jgoodies.forms.layout.Sizes.DLUX3;
        RELATED_COMPONENTS_PADX = com.jgoodies.forms.layout.Sizes.DLUX4;
        UNRELATED_COMPONENTS_PADX = com.jgoodies.forms.layout.Sizes.DLUX8;
        LABEL_COMPONENT_PADY = com.jgoodies.forms.layout.Sizes.DLUY2;
        RELATED_COMPONENTS_PADY = com.jgoodies.forms.layout.Sizes.DLUY3;
        UNRELATED_COMPONENTS_PADY = com.jgoodies.forms.layout.Sizes.DLUY6;
        NARROW_LINE_PAD = com.jgoodies.forms.layout.Sizes.DLUY2;
        LINE_PAD = com.jgoodies.forms.layout.Sizes.DLUY3;
        PARAGRAPH_PAD = com.jgoodies.forms.layout.Sizes.DLUY9;
        BUTTON_BAR_PAD = com.jgoodies.forms.layout.Sizes.DLUY4;
    }
}
