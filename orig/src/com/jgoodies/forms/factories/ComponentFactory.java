// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   ComponentFactory.java

package com.jgoodies.forms.factories;

import javax.swing.JComponent;
import javax.swing.JLabel;

public interface ComponentFactory
{

    public abstract javax.swing.JLabel createLabel(java.lang.String s);

    public abstract javax.swing.JLabel createTitle(java.lang.String s);

    public abstract javax.swing.JComponent createSeparator(java.lang.String s, int i);
}
