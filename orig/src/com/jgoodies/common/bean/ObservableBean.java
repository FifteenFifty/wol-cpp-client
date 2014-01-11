// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   ObservableBean.java

package com.jgoodies.common.bean;

import java.beans.PropertyChangeListener;

public interface ObservableBean
{

    public abstract void addPropertyChangeListener(java.beans.PropertyChangeListener propertychangelistener);

    public abstract void removePropertyChangeListener(java.beans.PropertyChangeListener propertychangelistener);
}
