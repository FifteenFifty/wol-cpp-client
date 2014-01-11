// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   DefaultComponentFactory.java

package com.jgoodies.forms.factories;

import com.jgoodies.common.base.Preconditions;
import com.jgoodies.common.base.Strings;
import com.jgoodies.common.swing.MnemonicUtils;
import com.jgoodies.forms.layout.Sizes;
import com.jgoodies.forms.util.FormUtils;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Insets;
import java.awt.LayoutManager;
import javax.accessibility.AccessibleContext;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.UIManager;

// Referenced classes of package com.jgoodies.forms.factories:
//            ComponentFactory2

public class DefaultComponentFactory
    implements com.jgoodies.forms.factories.ComponentFactory2
{
    private static final class TitledSeparatorLayout
        implements java.awt.LayoutManager
    {

        public void addLayoutComponent(java.lang.String s, java.awt.Component component)
        {
        }

        public void removeLayoutComponent(java.awt.Component component)
        {
        }

        public java.awt.Dimension minimumLayoutSize(java.awt.Container parent)
        {
            return preferredLayoutSize(parent);
        }

        public java.awt.Dimension preferredLayoutSize(java.awt.Container parent)
        {
            java.awt.Component label = getLabel(parent);
            java.awt.Dimension labelSize = label.getPreferredSize();
            java.awt.Insets insets = parent.getInsets();
            int width = labelSize.width + insets.left + insets.right;
            int height = labelSize.height + insets.top + insets.bottom;
            return new Dimension(width, height);
        }

        public void layoutContainer(java.awt.Container parent)
        {
            synchronized (parent.getTreeLock())
            {
                java.awt.Dimension size = parent.getSize();
                java.awt.Insets insets = parent.getInsets();
                int width = size.width - insets.left - insets.right;
                javax.swing.JLabel label = getLabel(parent);
                java.awt.Dimension labelSize = label.getPreferredSize();
                int labelWidth = labelSize.width;
                int labelHeight = labelSize.height;
                java.awt.Component separator1 = parent.getComponent(1);
                int separatorHeight = separator1.getPreferredSize().height;
                java.awt.FontMetrics metrics = label.getFontMetrics(label.getFont());
                int ascent = metrics.getMaxAscent();
                int hGapDlu = centerSeparators ? 3 : 1;
                int hGap = com.jgoodies.forms.layout.Sizes.dialogUnitXAsPixel(hGapDlu, label);
                int vOffset = centerSeparators ? 1 + (labelHeight - separatorHeight) / 2 : ascent - separatorHeight / 2;
                int alignment = label.getHorizontalAlignment();
                int y = insets.top;
                if (alignment == 2)
                {
                    int x = insets.left;
                    label.setBounds(x, y, labelWidth, labelHeight);
                    x += labelWidth;
                    x += hGap;
                    int separatorWidth = size.width - insets.right - x;
                    separator1.setBounds(x, y + vOffset, separatorWidth, separatorHeight);
                } else
                if (alignment == 4)
                {
                    int x = (insets.left + width) - labelWidth;
                    label.setBounds(x, y, labelWidth, labelHeight);
                    x -= hGap;
                    int separatorWidth = --x - insets.left;
                    separator1.setBounds(insets.left, y + vOffset, separatorWidth, separatorHeight);
                } else
                {
                    int xOffset = (width - labelWidth - 2 * hGap) / 2;
                    int x = insets.left;
                    separator1.setBounds(x, y + vOffset, xOffset - 1, separatorHeight);
                    x += xOffset;
                    x += hGap;
                    label.setBounds(x, y, labelWidth, labelHeight);
                    x += labelWidth;
                    x += hGap;
                    java.awt.Component separator2 = parent.getComponent(2);
                    int separatorWidth = size.width - insets.right - x;
                    separator2.setBounds(x, y + vOffset, separatorWidth, separatorHeight);
                }
            }
        }

        private javax.swing.JLabel getLabel(java.awt.Container parent)
        {
            return (javax.swing.JLabel)parent.getComponent(0);
        }

        private final boolean centerSeparators;

        private TitledSeparatorLayout(boolean centerSeparators)
        {
            this.centerSeparators = centerSeparators;
        }

    }

    private static final class TitleLabel extends com.jgoodies.forms.factories.FormsLabel
    {

        public void updateUI()
        {
            super.updateUI();
            java.awt.Color foreground = getTitleColor();
            if (foreground != null)
                setForeground(foreground);
            setFont(getTitleFont());
        }

        private java.awt.Color getTitleColor()
        {
            return javax.swing.UIManager.getColor("TitledBorder.titleColor");
        }

        private java.awt.Font getTitleFont()
        {
            return com.jgoodies.forms.util.FormUtils.isLafAqua() ? javax.swing.UIManager.getFont("Label.font").deriveFont(1) : javax.swing.UIManager.getFont("TitledBorder.font");
        }

        private TitleLabel()
        {
        }

    }

    private static final class ReadOnlyLabel extends com.jgoodies.forms.factories.FormsLabel
    {

        public void updateUI()
        {
            super.updateUI();
            setForeground(getDisabledForeground());
        }

        private java.awt.Color getDisabledForeground()
        {
            java.lang.String arr$[] = UIMANAGER_KEYS;
            int len$ = arr$.length;
            for (int i$ = 0; i$ < len$; i$++)
            {
                java.lang.String key = arr$[i$];
                java.awt.Color foreground = javax.swing.UIManager.getColor(key);
                if (foreground != null)
                    return foreground;
            }

            return null;
        }

        private static final java.lang.String UIMANAGER_KEYS[] = {
            "Label.disabledForeground", "Label.disabledText", "Label[Disabled].textForeground", "textInactiveText"
        };


        private ReadOnlyLabel()
        {
        }

    }

    private static class FormsLabel extends javax.swing.JLabel
    {
        private final class AccessibleFormsLabel extends javax.swing.JLabel.AccessibleJLabel
        {

            public java.lang.String getAccessibleName()
            {
                if (accessibleName != null)
                    return accessibleName;
                java.lang.String text = getText();
                if (text == null)
                    return super.getAccessibleName();
                else
                    return text.endsWith(":") ? text.substring(0, text.length() - 1) : text;
            }

            final com.jgoodies.forms.factories.FormsLabel this$0;

            private AccessibleFormsLabel()
            {
                _fld0 = com.jgoodies.forms.factories.FormsLabel.this;
                super(com.jgoodies.forms.factories.FormsLabel.this);
            }

        }


        public javax.accessibility.AccessibleContext getAccessibleContext()
        {
            if (accessibleContext == null)
                accessibleContext = new AccessibleFormsLabel();
            return accessibleContext;
        }

        private FormsLabel()
        {
        }

    }


    public DefaultComponentFactory()
    {
    }

    public static com.jgoodies.forms.factories.DefaultComponentFactory getInstance()
    {
        return INSTANCE;
    }

    public javax.swing.JLabel createLabel(java.lang.String textWithMnemonic)
    {
        javax.swing.JLabel label = new FormsLabel();
        com.jgoodies.common.swing.MnemonicUtils.configure(label, textWithMnemonic);
        return label;
    }

    public javax.swing.JLabel createReadOnlyLabel(java.lang.String textWithMnemonic)
    {
        javax.swing.JLabel label = new ReadOnlyLabel();
        com.jgoodies.common.swing.MnemonicUtils.configure(label, textWithMnemonic);
        return label;
    }

    public javax.swing.JButton createButton(javax.swing.Action action)
    {
        return new JButton(action);
    }

    public javax.swing.JLabel createTitle(java.lang.String textWithMnemonic)
    {
        javax.swing.JLabel label = new TitleLabel();
        com.jgoodies.common.swing.MnemonicUtils.configure(label, textWithMnemonic);
        label.setVerticalAlignment(0);
        return label;
    }

    public javax.swing.JComponent createSeparator(java.lang.String textWithMnemonic)
    {
        return createSeparator(textWithMnemonic, 2);
    }

    public javax.swing.JComponent createSeparator(java.lang.String textWithMnemonic, int alignment)
    {
        if (com.jgoodies.common.base.Strings.isBlank(textWithMnemonic))
        {
            return new JSeparator();
        } else
        {
            javax.swing.JLabel title = createTitle(textWithMnemonic);
            title.setHorizontalAlignment(alignment);
            return createSeparator(title);
        }
    }

    public javax.swing.JComponent createSeparator(javax.swing.JLabel label)
    {
        com.jgoodies.common.base.Preconditions.checkNotNull(label, "The label must not be null.");
        int horizontalAlignment = label.getHorizontalAlignment();
        com.jgoodies.common.base.Preconditions.checkArgument(horizontalAlignment == 2 || horizontalAlignment == 0 || horizontalAlignment == 4, "The label's horizontal alignment must be one of: LEFT, CENTER, RIGHT.");
        javax.swing.JPanel panel = new JPanel(new TitledSeparatorLayout(!com.jgoodies.forms.util.FormUtils.isLafAqua()));
        panel.setOpaque(false);
        panel.add(label);
        panel.add(new JSeparator());
        if (horizontalAlignment == 0)
            panel.add(new JSeparator());
        return panel;
    }

    /**
     * @deprecated Method setTextAndMnemonic is deprecated
     */

    public static void setTextAndMnemonic(javax.swing.JLabel label, java.lang.String textWithMnemonic)
    {
        com.jgoodies.common.swing.MnemonicUtils.configure(label, textWithMnemonic);
    }

    private static final com.jgoodies.forms.factories.DefaultComponentFactory INSTANCE = new DefaultComponentFactory();

}
