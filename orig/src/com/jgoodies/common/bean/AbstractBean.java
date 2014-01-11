// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   AbstractBean.java

package com.jgoodies.common.bean;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyVetoException;
import java.beans.VetoableChangeListener;
import java.beans.VetoableChangeSupport;
import java.io.Serializable;

// Referenced classes of package com.jgoodies.common.bean:
//            ObservableBean2

public abstract class AbstractBean
    implements java.io.Serializable, com.jgoodies.common.bean.ObservableBean2
{

    public AbstractBean()
    {
    }

    public final synchronized void addPropertyChangeListener(java.beans.PropertyChangeListener listener)
    {
        if (listener == null)
            return;
        if (changeSupport == null)
            changeSupport = createPropertyChangeSupport(this);
        changeSupport.addPropertyChangeListener(listener);
    }

    public final synchronized void removePropertyChangeListener(java.beans.PropertyChangeListener listener)
    {
        if (listener == null || changeSupport == null)
        {
            return;
        } else
        {
            changeSupport.removePropertyChangeListener(listener);
            return;
        }
    }

    public final synchronized void addPropertyChangeListener(java.lang.String propertyName, java.beans.PropertyChangeListener listener)
    {
        if (listener == null)
            return;
        if (changeSupport == null)
            changeSupport = createPropertyChangeSupport(this);
        changeSupport.addPropertyChangeListener(propertyName, listener);
    }

    public final synchronized void removePropertyChangeListener(java.lang.String propertyName, java.beans.PropertyChangeListener listener)
    {
        if (listener == null || changeSupport == null)
        {
            return;
        } else
        {
            changeSupport.removePropertyChangeListener(propertyName, listener);
            return;
        }
    }

    public final synchronized void addVetoableChangeListener(java.beans.VetoableChangeListener listener)
    {
        if (listener == null)
            return;
        if (vetoSupport == null)
            vetoSupport = new VetoableChangeSupport(this);
        vetoSupport.addVetoableChangeListener(listener);
    }

    public final synchronized void removeVetoableChangeListener(java.beans.VetoableChangeListener listener)
    {
        if (listener == null || vetoSupport == null)
        {
            return;
        } else
        {
            vetoSupport.removeVetoableChangeListener(listener);
            return;
        }
    }

    public final synchronized void addVetoableChangeListener(java.lang.String propertyName, java.beans.VetoableChangeListener listener)
    {
        if (listener == null)
            return;
        if (vetoSupport == null)
            vetoSupport = new VetoableChangeSupport(this);
        vetoSupport.addVetoableChangeListener(propertyName, listener);
    }

    public final synchronized void removeVetoableChangeListener(java.lang.String propertyName, java.beans.VetoableChangeListener listener)
    {
        if (listener == null || vetoSupport == null)
        {
            return;
        } else
        {
            vetoSupport.removeVetoableChangeListener(propertyName, listener);
            return;
        }
    }

    public final synchronized java.beans.PropertyChangeListener[] getPropertyChangeListeners()
    {
        if (changeSupport == null)
            return new java.beans.PropertyChangeListener[0];
        else
            return changeSupport.getPropertyChangeListeners();
    }

    public final synchronized java.beans.PropertyChangeListener[] getPropertyChangeListeners(java.lang.String propertyName)
    {
        if (changeSupport == null)
            return new java.beans.PropertyChangeListener[0];
        else
            return changeSupport.getPropertyChangeListeners(propertyName);
    }

    public final synchronized java.beans.VetoableChangeListener[] getVetoableChangeListeners()
    {
        if (vetoSupport == null)
            return new java.beans.VetoableChangeListener[0];
        else
            return vetoSupport.getVetoableChangeListeners();
    }

    public final synchronized java.beans.VetoableChangeListener[] getVetoableChangeListeners(java.lang.String propertyName)
    {
        if (vetoSupport == null)
            return new java.beans.VetoableChangeListener[0];
        else
            return vetoSupport.getVetoableChangeListeners(propertyName);
    }

    protected java.beans.PropertyChangeSupport createPropertyChangeSupport(java.lang.Object bean)
    {
        return new PropertyChangeSupport(bean);
    }

    protected final void firePropertyChange(java.beans.PropertyChangeEvent event)
    {
        java.beans.PropertyChangeSupport aChangeSupport = changeSupport;
        if (aChangeSupport == null)
        {
            return;
        } else
        {
            aChangeSupport.firePropertyChange(event);
            return;
        }
    }

    protected final void firePropertyChange(java.lang.String propertyName, java.lang.Object oldValue, java.lang.Object newValue)
    {
        java.beans.PropertyChangeSupport aChangeSupport = changeSupport;
        if (aChangeSupport == null)
        {
            return;
        } else
        {
            aChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
            return;
        }
    }

    protected final void firePropertyChange(java.lang.String propertyName, boolean oldValue, boolean newValue)
    {
        java.beans.PropertyChangeSupport aChangeSupport = changeSupport;
        if (aChangeSupport == null)
        {
            return;
        } else
        {
            aChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
            return;
        }
    }

    protected final void firePropertyChange(java.lang.String propertyName, double oldValue, double newValue)
    {
        firePropertyChange(propertyName, java.lang.Double.valueOf(oldValue), java.lang.Double.valueOf(newValue));
    }

    protected final void firePropertyChange(java.lang.String propertyName, float oldValue, float newValue)
    {
        firePropertyChange(propertyName, java.lang.Float.valueOf(oldValue), java.lang.Float.valueOf(newValue));
    }

    protected final void firePropertyChange(java.lang.String propertyName, int oldValue, int newValue)
    {
        java.beans.PropertyChangeSupport aChangeSupport = changeSupport;
        if (aChangeSupport == null)
        {
            return;
        } else
        {
            aChangeSupport.firePropertyChange(propertyName, java.lang.Integer.valueOf(oldValue), java.lang.Integer.valueOf(newValue));
            return;
        }
    }

    protected final void firePropertyChange(java.lang.String propertyName, long oldValue, long newValue)
    {
        firePropertyChange(propertyName, java.lang.Long.valueOf(oldValue), java.lang.Long.valueOf(newValue));
    }

    protected final void fireMultiplePropertiesChanged()
    {
        firePropertyChange(null, null, null);
    }

    protected final void fireIndexedPropertyChange(java.lang.String propertyName, int index, java.lang.Object oldValue, java.lang.Object newValue)
    {
        java.beans.PropertyChangeSupport aChangeSupport = changeSupport;
        if (aChangeSupport == null)
        {
            return;
        } else
        {
            aChangeSupport.fireIndexedPropertyChange(propertyName, index, oldValue, newValue);
            return;
        }
    }

    protected final void fireIndexedPropertyChange(java.lang.String propertyName, int index, int oldValue, int newValue)
    {
        if (oldValue == newValue)
        {
            return;
        } else
        {
            fireIndexedPropertyChange(propertyName, index, java.lang.Integer.valueOf(oldValue), java.lang.Integer.valueOf(newValue));
            return;
        }
    }

    protected final void fireIndexedPropertyChange(java.lang.String propertyName, int index, boolean oldValue, boolean newValue)
    {
        if (oldValue == newValue)
        {
            return;
        } else
        {
            fireIndexedPropertyChange(propertyName, index, java.lang.Boolean.valueOf(oldValue), java.lang.Boolean.valueOf(newValue));
            return;
        }
    }

    protected final void fireVetoableChange(java.beans.PropertyChangeEvent event)
        throws java.beans.PropertyVetoException
    {
        java.beans.VetoableChangeSupport aVetoSupport = vetoSupport;
        if (aVetoSupport == null)
        {
            return;
        } else
        {
            aVetoSupport.fireVetoableChange(event);
            return;
        }
    }

    protected final void fireVetoableChange(java.lang.String propertyName, java.lang.Object oldValue, java.lang.Object newValue)
        throws java.beans.PropertyVetoException
    {
        java.beans.VetoableChangeSupport aVetoSupport = vetoSupport;
        if (aVetoSupport == null)
        {
            return;
        } else
        {
            aVetoSupport.fireVetoableChange(propertyName, oldValue, newValue);
            return;
        }
    }

    protected final void fireVetoableChange(java.lang.String propertyName, boolean oldValue, boolean newValue)
        throws java.beans.PropertyVetoException
    {
        java.beans.VetoableChangeSupport aVetoSupport = vetoSupport;
        if (aVetoSupport == null)
        {
            return;
        } else
        {
            aVetoSupport.fireVetoableChange(propertyName, oldValue, newValue);
            return;
        }
    }

    protected final void fireVetoableChange(java.lang.String propertyName, double oldValue, double newValue)
        throws java.beans.PropertyVetoException
    {
        fireVetoableChange(propertyName, java.lang.Double.valueOf(oldValue), java.lang.Double.valueOf(newValue));
    }

    protected final void fireVetoableChange(java.lang.String propertyName, int oldValue, int newValue)
        throws java.beans.PropertyVetoException
    {
        java.beans.VetoableChangeSupport aVetoSupport = vetoSupport;
        if (aVetoSupport == null)
        {
            return;
        } else
        {
            aVetoSupport.fireVetoableChange(propertyName, java.lang.Integer.valueOf(oldValue), java.lang.Integer.valueOf(newValue));
            return;
        }
    }

    protected final void fireVetoableChange(java.lang.String propertyName, float oldValue, float newValue)
        throws java.beans.PropertyVetoException
    {
        fireVetoableChange(propertyName, java.lang.Float.valueOf(oldValue), java.lang.Float.valueOf(newValue));
    }

    protected final void fireVetoableChange(java.lang.String propertyName, long oldValue, long newValue)
        throws java.beans.PropertyVetoException
    {
        fireVetoableChange(propertyName, java.lang.Long.valueOf(oldValue), java.lang.Long.valueOf(newValue));
    }

    protected java.beans.PropertyChangeSupport changeSupport;
    private java.beans.VetoableChangeSupport vetoSupport;
}
