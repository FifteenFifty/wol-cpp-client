// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   Borders.java

package com.jgoodies.forms.factories;

import com.jgoodies.common.base.Preconditions;
import com.jgoodies.forms.layout.ConstantSize;
import com.jgoodies.forms.layout.Sizes;
import com.jgoodies.forms.util.LayoutStyle;
import java.awt.Component;
import java.awt.Insets;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public final class Borders
{
    public static final class EmptyBorder extends javax.swing.border.AbstractBorder
    {

        public java.awt.Insets getBorderInsets(java.awt.Component c, java.awt.Insets insets)
        {
            insets.top = top.getPixelSize(c);
            insets.left = left.getPixelSize(c);
            insets.bottom = bottom.getPixelSize(c);
            insets.right = right.getPixelSize(c);
            return insets;
        }

        public java.awt.Insets getBorderInsets(java.awt.Component c)
        {
            return getBorderInsets(c, new Insets(0, 0, 0, 0));
        }

        public com.jgoodies.forms.layout.ConstantSize top()
        {
            return top;
        }

        public com.jgoodies.forms.layout.ConstantSize left()
        {
            return left;
        }

        public com.jgoodies.forms.layout.ConstantSize bottom()
        {
            return bottom;
        }

        public com.jgoodies.forms.layout.ConstantSize right()
        {
            return right;
        }

        private final com.jgoodies.forms.layout.ConstantSize top;
        private final com.jgoodies.forms.layout.ConstantSize left;
        private final com.jgoodies.forms.layout.ConstantSize bottom;
        private final com.jgoodies.forms.layout.ConstantSize right;

        private EmptyBorder(com.jgoodies.forms.layout.ConstantSize top, com.jgoodies.forms.layout.ConstantSize left, com.jgoodies.forms.layout.ConstantSize bottom, com.jgoodies.forms.layout.ConstantSize right)
        {
            if (top == null || left == null || bottom == null || right == null)
            {
                throw new NullPointerException("The top, left, bottom, and right must not be null.");
            } else
            {
                this.top = top;
                this.left = left;
                this.bottom = bottom;
                this.right = right;
                return;
            }
        }

    }


    private Borders()
    {
    }

    public static javax.swing.border.Border createEmptyBorder(com.jgoodies.forms.layout.ConstantSize top, com.jgoodies.forms.layout.ConstantSize left, com.jgoodies.forms.layout.ConstantSize bottom, com.jgoodies.forms.layout.ConstantSize right)
    {
        return new EmptyBorder(top, left, bottom, right);
    }

    public static javax.swing.border.Border createEmptyBorder(java.lang.String encodedSizes)
    {
        java.lang.String token[] = encodedSizes.split("\\s*,\\s*");
        int tokenCount = token.length;
        com.jgoodies.common.base.Preconditions.checkArgument(token.length == 4, (new StringBuilder()).append("The border requires 4 sizes, but \"").append(encodedSizes).append("\" has ").append(tokenCount).append(".").toString());
        com.jgoodies.forms.layout.ConstantSize top = com.jgoodies.forms.layout.Sizes.constant(token[0], false);
        com.jgoodies.forms.layout.ConstantSize left = com.jgoodies.forms.layout.Sizes.constant(token[1], true);
        com.jgoodies.forms.layout.ConstantSize bottom = com.jgoodies.forms.layout.Sizes.constant(token[2], false);
        com.jgoodies.forms.layout.ConstantSize right = com.jgoodies.forms.layout.Sizes.constant(token[3], true);
        return com.jgoodies.forms.factories.Borders.createEmptyBorder(top, left, bottom, right);
    }

    public static final javax.swing.border.Border EMPTY_BORDER = new javax.swing.border.EmptyBorder(0, 0, 0, 0);
    public static final javax.swing.border.Border DLU2_BORDER;
    public static final javax.swing.border.Border DLU4_BORDER;
    public static final javax.swing.border.Border DLU7_BORDER;
    public static final javax.swing.border.Border DLU14_BORDER;
    public static final javax.swing.border.Border DLU21_BORDER;
    public static final javax.swing.border.Border BUTTON_BAR_GAP_BORDER = com.jgoodies.forms.factories.Borders.createEmptyBorder(com.jgoodies.forms.util.LayoutStyle.getCurrent().getButtonBarPad(), com.jgoodies.forms.layout.Sizes.dluX(0), com.jgoodies.forms.layout.Sizes.dluY(0), com.jgoodies.forms.layout.Sizes.dluX(0));
    public static final javax.swing.border.Border DIALOG_BORDER = com.jgoodies.forms.factories.Borders.createEmptyBorder(com.jgoodies.forms.util.LayoutStyle.getCurrent().getDialogMarginY(), com.jgoodies.forms.util.LayoutStyle.getCurrent().getDialogMarginX(), com.jgoodies.forms.util.LayoutStyle.getCurrent().getDialogMarginY(), com.jgoodies.forms.util.LayoutStyle.getCurrent().getDialogMarginX());
    public static final javax.swing.border.Border TABBED_DIALOG_BORDER = com.jgoodies.forms.factories.Borders.createEmptyBorder(com.jgoodies.forms.util.LayoutStyle.getCurrent().getTabbedDialogMarginY(), com.jgoodies.forms.util.LayoutStyle.getCurrent().getTabbedDialogMarginX(), com.jgoodies.forms.util.LayoutStyle.getCurrent().getTabbedDialogMarginY(), com.jgoodies.forms.util.LayoutStyle.getCurrent().getTabbedDialogMarginX());

    static 
    {
        DLU2_BORDER = com.jgoodies.forms.factories.Borders.createEmptyBorder(com.jgoodies.forms.layout.Sizes.DLUY2, com.jgoodies.forms.layout.Sizes.DLUX2, com.jgoodies.forms.layout.Sizes.DLUY2, com.jgoodies.forms.layout.Sizes.DLUX2);
        DLU4_BORDER = com.jgoodies.forms.factories.Borders.createEmptyBorder(com.jgoodies.forms.layout.Sizes.DLUY4, com.jgoodies.forms.layout.Sizes.DLUX4, com.jgoodies.forms.layout.Sizes.DLUY4, com.jgoodies.forms.layout.Sizes.DLUX4);
        DLU7_BORDER = com.jgoodies.forms.factories.Borders.createEmptyBorder(com.jgoodies.forms.layout.Sizes.DLUY7, com.jgoodies.forms.layout.Sizes.DLUX7, com.jgoodies.forms.layout.Sizes.DLUY7, com.jgoodies.forms.layout.Sizes.DLUX7);
        DLU14_BORDER = com.jgoodies.forms.factories.Borders.createEmptyBorder(com.jgoodies.forms.layout.Sizes.DLUY14, com.jgoodies.forms.layout.Sizes.DLUX14, com.jgoodies.forms.layout.Sizes.DLUY14, com.jgoodies.forms.layout.Sizes.DLUX14);
        DLU21_BORDER = com.jgoodies.forms.factories.Borders.createEmptyBorder(com.jgoodies.forms.layout.Sizes.DLUY21, com.jgoodies.forms.layout.Sizes.DLUX21, com.jgoodies.forms.layout.Sizes.DLUY21, com.jgoodies.forms.layout.Sizes.DLUX21);
    }
}
