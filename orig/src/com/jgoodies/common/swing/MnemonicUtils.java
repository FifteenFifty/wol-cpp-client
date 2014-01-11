// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   MnemonicUtils.java

package com.jgoodies.common.swing;

import com.jgoodies.common.base.Preconditions;
import com.jgoodies.common.base.Strings;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.JLabel;

public final class MnemonicUtils
{
    private static final class MnemonicText
    {

        private static int indexOfEntityEnd(java.lang.String htmlText, int start)
        {
            java.text.CharacterIterator sci = new StringCharacterIterator(htmlText, start);
            char c;
            do
            {
                c = sci.next();
                if (c == ';')
                    return sci.getIndex();
                if (!java.lang.Character.isLetterOrDigit(c))
                    return -1;
            } while (c != '\uFFFF');
            return -1;
        }

        private static int mnemonicKey(char c)
        {
            int vk = c;
            if (vk >= 97 && vk <= 122)
                vk -= 32;
            return vk;
        }

        java.lang.String text;
        int key;
        int index;

        private MnemonicText(java.lang.String markedText, char marker)
        {
            int i;
            if (markedText == null || markedText.length() <= 1 || (i = markedText.indexOf(marker)) == -1)
            {
                text = markedText;
                key = 0;
                index = -1;
                return;
            }
            boolean html = com.jgoodies.common.base.Strings.startsWithIgnoreCase(markedText, "<html>");
            java.lang.StringBuilder builder = new StringBuilder();
            int begin = 0;
            int quotedMarkers = 0;
            int markerIndex = -1;
            boolean marked = false;
            char markedChar = '\0';
            java.text.CharacterIterator sci = new StringCharacterIterator(markedText);
            do
            {
                builder.append(markedText.substring(begin, i));
                char current = sci.setIndex(i);
                char next = sci.next();
                if (html)
                {
                    int entityEnd = com.jgoodies.common.swing.MnemonicText.indexOfEntityEnd(markedText, i);
                    if (entityEnd == -1)
                    {
                        marked = true;
                        builder.append("<u>").append(next).append("</u>");
                        begin = i + 2;
                        markedChar = next;
                    } else
                    {
                        builder.append(markedText.substring(i, entityEnd));
                        begin = entityEnd;
                    }
                } else
                if (next == marker)
                {
                    builder.append(next);
                    begin = i + 2;
                    quotedMarkers++;
                } else
                if (java.lang.Character.isWhitespace(next))
                {
                    builder.append(current).append(next);
                    begin = i + 2;
                } else
                if (next == '\uFFFF')
                {
                    builder.append(current);
                    begin = i + 2;
                } else
                {
                    builder.append(next);
                    begin = i + 2;
                    markerIndex = i - quotedMarkers;
                    marked = true;
                    markedChar = next;
                }
                i = markedText.indexOf(marker, begin);
            } while (i != -1 && !marked);
            if (begin < markedText.length())
                builder.append(markedText.substring(begin));
            text = builder.toString();
            index = markerIndex;
            if (marked)
                key = com.jgoodies.common.swing.MnemonicText.mnemonicKey(markedChar);
            else
                key = 0;
        }

    }


    private MnemonicUtils()
    {
    }

    public static void configure(javax.swing.AbstractButton target, java.lang.String markedText)
    {
        com.jgoodies.common.base.Preconditions.checkNotNull(target, "target must not be null.");
        com.jgoodies.common.swing.MnemonicUtils.configure0(target, new MnemonicText(markedText, '&'));
    }

    public static void configure(javax.swing.Action target, java.lang.String markedText)
    {
        com.jgoodies.common.base.Preconditions.checkNotNull(target, "target must not be null.");
        com.jgoodies.common.swing.MnemonicUtils.configure0(target, new MnemonicText(markedText, '&'));
    }

    public static void configure(javax.swing.JLabel target, java.lang.String markedText)
    {
        com.jgoodies.common.base.Preconditions.checkNotNull(target, "target must not be null.");
        com.jgoodies.common.swing.MnemonicUtils.configure0(target, new MnemonicText(markedText, '&'));
    }

    public static java.lang.String plainText(java.lang.String markedText)
    {
        return (new MnemonicText(markedText, '&')).text;
    }

    static int mnemonic(java.lang.String markedText)
    {
        return (new MnemonicText(markedText, '&')).key;
    }

    static int mnemonicIndex(java.lang.String markedText)
    {
        return (new MnemonicText(markedText, '&')).index;
    }

    private static void configure0(javax.swing.AbstractButton button, com.jgoodies.common.swing.MnemonicText mnemonicText)
    {
        button.setText(mnemonicText.text);
        button.setMnemonic(mnemonicText.key);
        button.setDisplayedMnemonicIndex(mnemonicText.index);
    }

    private static void configure0(javax.swing.Action action, com.jgoodies.common.swing.MnemonicText mnemonicText)
    {
        java.lang.Integer keyValue = java.lang.Integer.valueOf(mnemonicText.key);
        java.lang.Integer indexValue = mnemonicText.index != -1 ? java.lang.Integer.valueOf(mnemonicText.index) : null;
        action.putValue("Name", mnemonicText.text);
        action.putValue("MnemonicKey", keyValue);
        action.putValue("SwingDisplayedMnemonicIndexKey", indexValue);
    }

    private static void configure0(javax.swing.JLabel label, com.jgoodies.common.swing.MnemonicText mnemonicText)
    {
        label.setText(mnemonicText.text);
        label.setDisplayedMnemonic(mnemonicText.key);
        label.setDisplayedMnemonicIndex(mnemonicText.index);
    }

    static final char MNEMONIC_MARKER = 38;
    private static final java.lang.String DISPLAYED_MNEMONIC_INDEX_KEY = "SwingDisplayedMnemonicIndexKey";
}
