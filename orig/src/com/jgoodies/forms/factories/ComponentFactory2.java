// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   ComponentFactory2.java

package com.jgoodies.forms.factories;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;

// Referenced classes of package com.jgoodies.forms.factories:
//            ComponentFactory

public interface ComponentFactory2
    extends com.jgoodies.forms.factories.ComponentFactory
{

    public abstract javax.swing.JLabel createReadOnlyLabel(java.lang.String s);

    public abstract javax.swing.JButton createButton(javax.swing.Action action);
}
