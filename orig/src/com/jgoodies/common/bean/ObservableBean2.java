// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   ObservableBean2.java

package com.jgoodies.common.bean;

import java.beans.PropertyChangeListener;

// Referenced classes of package com.jgoodies.common.bean:
//            ObservableBean

public interface ObservableBean2
    extends com.jgoodies.common.bean.ObservableBean
{

    public abstract void addPropertyChangeListener(java.lang.String s, java.beans.PropertyChangeListener propertychangelistener);

    public abstract void removePropertyChangeListener(java.lang.String s, java.beans.PropertyChangeListener propertychangelistener);

    public abstract java.beans.PropertyChangeListener[] getPropertyChangeListeners();

    public abstract java.beans.PropertyChangeListener[] getPropertyChangeListeners(java.lang.String s);
}
