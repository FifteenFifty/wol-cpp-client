// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   LinkedListModel.java

package com.jgoodies.common.collect;

import java.util.Collection;
import java.util.LinkedList;
import java.util.ListIterator;
import javax.swing.event.EventListenerList;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

// Referenced classes of package com.jgoodies.common.collect:
//            ObservableList

public class LinkedListModel extends java.util.LinkedList
    implements com.jgoodies.common.collect.ObservableList
{
    private final class ReportingListIterator
        implements java.util.ListIterator
    {

        public boolean hasNext()
        {
            return _flddelegate.hasNext();
        }

        public java.lang.Object next()
        {
            lastReturnedIndex = nextIndex();
            return _flddelegate.next();
        }

        public boolean hasPrevious()
        {
            return _flddelegate.hasPrevious();
        }

        public java.lang.Object previous()
        {
            lastReturnedIndex = previousIndex();
            return _flddelegate.previous();
        }

        public int nextIndex()
        {
            return _flddelegate.nextIndex();
        }

        public int previousIndex()
        {
            return _flddelegate.previousIndex();
        }

        public void remove()
        {
            int oldSize = size();
            _flddelegate.remove();
            int newSize = size();
            if (newSize < oldSize)
                fireIntervalRemoved(lastReturnedIndex, lastReturnedIndex);
        }

        public void set(java.lang.Object e)
        {
            _flddelegate.set(e);
            fireContentsChanged(lastReturnedIndex);
        }

        public void add(java.lang.Object e)
        {
            _flddelegate.add(e);
            int newIndex = previousIndex();
            fireIntervalAdded(newIndex, newIndex);
            lastReturnedIndex = -1;
        }

        private final java.util.ListIterator _flddelegate;
        private int lastReturnedIndex;
        final com.jgoodies.common.collect.LinkedListModel this$0;

        ReportingListIterator(java.util.ListIterator delegate)
        {
            _fld0 = com.jgoodies.common.collect.LinkedListModel.this;
            super();
            _flddelegate = delegate;
            lastReturnedIndex = -1;
        }
    }


    public LinkedListModel()
    {
    }

    public LinkedListModel(java.util.Collection c)
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

    public final void addFirst(java.lang.Object e)
    {
        super.addFirst(e);
        fireIntervalAdded(0, 0);
    }

    public final void addLast(java.lang.Object e)
    {
        int newIndex = size();
        super.addLast(e);
        fireIntervalAdded(newIndex, newIndex);
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
        if (index == -1)
        {
            return false;
        } else
        {
            remove(index);
            return true;
        }
    }

    public final java.lang.Object removeFirst()
    {
        java.lang.Object first = super.removeFirst();
        fireIntervalRemoved(0, 0);
        return first;
    }

    public final java.lang.Object removeLast()
    {
        int lastIndex = size() - 1;
        java.lang.Object last = super.removeLast();
        fireIntervalRemoved(lastIndex, lastIndex);
        return last;
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

    public final java.util.ListIterator listIterator(int index)
    {
        return new ReportingListIterator(super.listIterator(index));
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

    private static final long serialVersionUID = 0x4fd81ae95a03c4e5L;
    private javax.swing.event.EventListenerList listenerList;


}
