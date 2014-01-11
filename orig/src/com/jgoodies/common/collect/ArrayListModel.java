// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   ArrayListModel.java

package com.jgoodies.common.collect;

import java.util.ArrayList;
import java.util.Collection;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

// Referenced classes of package com.jgoodies.common.collect:
//            ObservableList

public class ArrayListModel extends java.util.ArrayList
    implements com.jgoodies.common.collect.ObservableList
{

    public ArrayListModel()
    {
        this(10);
    }

    public ArrayListModel(int initialCapacity)
    {
        super(initialCapacity);
    }

    public ArrayListModel(java.util.Collection c)
    {
        super(c);
    }

    public final void add(int index, java.lang.Object element)
    {
        super.add(index, element);
        fireIntervalAdded(index, index);
    }

    public final boolean add(java.lang.Object e)
    {
        int newIndex = size();
        super.add(e);
        fireIntervalAdded(newIndex, newIndex);
        return true;
    }

    public final boolean addAll(int index, java.util.Collection c)
    {
        boolean changed = super.addAll(index, c);
        if (changed)
        {
            int lastIndex = (index + c.size()) - 1;
            fireIntervalAdded(index, lastIndex);
        }
        return changed;
    }

    public final boolean addAll(java.util.Collection c)
    {
        int firstIndex = size();
        boolean changed = super.addAll(c);
        if (changed)
        {
            int lastIndex = (firstIndex + c.size()) - 1;
            fireIntervalAdded(firstIndex, lastIndex);
        }
        return changed;
    }

    public final void clear()
    {
        if (isEmpty())
        {
            return;
        } else
        {
            int oldLastIndex = size() - 1;
            super.clear();
            fireIntervalRemoved(0, oldLastIndex);
            return;
        }
    }

    public final java.lang.Object remove(int index)
    {
        java.lang.Object removedElement = super.remove(index);
        fireIntervalRemoved(index, index);
        return removedElement;
    }

    public final boolean remove(java.lang.Object o)
    {
        int index = indexOf(o);
        boolean contained = index != -1;
        if (contained)
            remove(index);
        return contained;
    }

    protected final void removeRange(int fromIndex, int toIndex)
    {
        super.removeRange(fromIndex, toIndex);
        fireIntervalRemoved(fromIndex, toIndex - 1);
    }

    public final java.lang.Object set(int index, java.lang.Object element)
    {
        java.lang.Object previousElement = super.set(index, element);
        fireContentsChanged(index, index);
        return previousElement;
    }

    public final void addListDataListener(javax.swing.event.ListDataListener l)
    {
        getEventListenerList().add(javax/swing/event/ListDataListener, l);
    }

    public final void removeListDataListener(javax.swing.event.ListDataListener l)
    {
        getEventListenerList().remove(javax/swing/event/ListDataListener, l);
    }

    public final java.lang.Object getElementAt(int index)
    {
        return get(index);
    }

    public final int getSize()
    {
        return size();
    }

    public final void fireContentsChanged(int index)
    {
        fireContentsChanged(index, index);
    }

    public final javax.swing.event.ListDataListener[] getListDataListeners()
    {
        return (javax.swing.event.ListDataListener[])getEventListenerList().getListeners(javax/swing/event/ListDataListener);
    }

    private void fireContentsChanged(int index0, int index1)
    {
        java.lang.Object listeners[] = getEventListenerList().getListenerList();
        javax.swing.event.ListDataEvent e = null;
        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] != javax/swing/event/ListDataListener)
                continue;
            if (e == null)
                e = new ListDataEvent(this, 0, index0, index1);
            ((javax.swing.event.ListDataListener)listeners[i + 1]).contentsChanged(e);
        }

    }

    private void fireIntervalAdded(int index0, int index1)
    {
        java.lang.Object listeners[] = getEventListenerList().getListenerList();
        javax.swing.event.ListDataEvent e = null;
        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] != javax/swing/event/ListDataListener)
                continue;
            if (e == null)
                e = new ListDataEvent(this, 1, index0, index1);
            ((javax.swing.event.ListDataListener)listeners[i + 1]).intervalAdded(e);
        }

    }

    private void fireIntervalRemoved(int index0, int index1)
    {
        java.lang.Object listeners[] = getEventListenerList().getListenerList();
        javax.swing.event.ListDataEvent e = null;
        for (int i = listeners.length - 2; i >= 0; i -= 2)
        {
            if (listeners[i] != javax/swing/event/ListDataListener)
                continue;
            if (e == null)
                e = new ListDataEvent(this, 2, index0, index1);
            ((javax.swing.event.ListDataListener)listeners[i + 1]).intervalRemoved(e);
        }

    }

    private javax.swing.event.EventListenerList getEventListenerList()
    {
        if (listenerList == null)
            listenerList = new EventListenerList();
        return listenerList;
    }

    private static final long serialVersionUID = 0xaa6f1d40b19c0346L;
    private javax.swing.event.EventListenerList listenerList;
}
