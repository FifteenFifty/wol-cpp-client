// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   DefaultUnitConverter.java

package com.jgoodies.forms.util;

import com.jgoodies.common.base.Preconditions;
import java.awt.Component;
import java.awt.Font;
import java.awt.FontMetrics;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.UIManager;

// Referenced classes of package com.jgoodies.forms.util:
//            AbstractUnitConverter, FormUtils

public final class DefaultUnitConverter extends com.jgoodies.forms.util.AbstractUnitConverter
{
    private static final class DialogBaseUnits
    {

        public java.lang.String toString()
        {
            return (new StringBuilder()).append("DBU(x=").append(x).append("; y=").append(y).append(")").toString();
        }

        final double x;
        final double y;

        DialogBaseUnits(double dialogBaseUnitsX, double dialogBaseUnitsY)
        {
            x = dialogBaseUnitsX;
            y = dialogBaseUnitsY;
        }
    }


    private DefaultUnitConverter()
    {
        averageCharWidthTestString = "X";
        cachedGlobalDialogBaseUnits = null;
        cachedDialogBaseUnits = null;
        cachedFontMetrics = null;
        cachedDefaultDialogFont = null;
    }

    public static com.jgoodies.forms.util.DefaultUnitConverter getInstance()
    {
        if (instance == null)
            instance = new DefaultUnitConverter();
        return instance;
    }

    public java.lang.String getAverageCharacterWidthTestString()
    {
        return averageCharWidthTestString;
    }

    public void setAverageCharacterWidthTestString(java.lang.String newTestString)
    {
        com.jgoodies.common.base.Preconditions.checkNotBlank(newTestString, "The test string must not be null, empty, or whitespace.");
        java.lang.String oldTestString = averageCharWidthTestString;
        averageCharWidthTestString = newTestString;
        changeSupport.firePropertyChange("averageCharacterWidthTestString", oldTestString, newTestString);
    }

    public java.awt.Font getDefaultDialogFont()
    {
        return defaultDialogFont == null ? getCachedDefaultDialogFont() : defaultDialogFont;
    }

    public void setDefaultDialogFont(java.awt.Font newFont)
    {
        java.awt.Font oldFont = defaultDialogFont;
        defaultDialogFont = newFont;
        clearCache();
        changeSupport.firePropertyChange("defaultDialogFont", oldFont, newFont);
    }

    protected double getDialogBaseUnitsX(java.awt.Component component)
    {
        return getDialogBaseUnits(component).x;
    }

    protected double getDialogBaseUnitsY(java.awt.Component component)
    {
        return getDialogBaseUnits(component).y;
    }

    private com.jgoodies.forms.util.DialogBaseUnits getGlobalDialogBaseUnits()
    {
        if (cachedGlobalDialogBaseUnits == null)
            cachedGlobalDialogBaseUnits = computeGlobalDialogBaseUnits();
        return cachedGlobalDialogBaseUnits;
    }

    private com.jgoodies.forms.util.DialogBaseUnits getDialogBaseUnits(java.awt.Component c)
    {
        com.jgoodies.forms.util.FormUtils.ensureValidCache();
        if (c == null)
            return getGlobalDialogBaseUnits();
        java.awt.FontMetrics fm = c.getFontMetrics(getDefaultDialogFont());
        if (fm.equals(cachedFontMetrics))
        {
            return cachedDialogBaseUnits;
        } else
        {
            com.jgoodies.forms.util.DialogBaseUnits dialogBaseUnits = computeDialogBaseUnits(fm);
            cachedFontMetrics = fm;
            cachedDialogBaseUnits = dialogBaseUnits;
            return dialogBaseUnits;
        }
    }

    private com.jgoodies.forms.util.DialogBaseUnits computeDialogBaseUnits(java.awt.FontMetrics metrics)
    {
        double averageCharWidth = computeAverageCharWidth(metrics, averageCharWidthTestString);
        int ascent = metrics.getAscent();
        double height = ascent <= 14 ? ascent + (15 - ascent) / 3 : ascent;
        com.jgoodies.forms.util.DialogBaseUnits dialogBaseUnits = new DialogBaseUnits(averageCharWidth, height);
        if (LOGGER.isLoggable(java.util.logging.Level.CONFIG))
            LOGGER.config((new StringBuilder()).append("Computed dialog base units ").append(dialogBaseUnits).append(" for: ").append(metrics.getFont()).toString());
        return dialogBaseUnits;
    }

    private com.jgoodies.forms.util.DialogBaseUnits computeGlobalDialogBaseUnits()
    {
        LOGGER.config("Computing global dialog base units...");
        java.awt.Font dialogFont = getDefaultDialogFont();
        java.awt.FontMetrics metrics = createDefaultGlobalComponent().getFontMetrics(dialogFont);
        com.jgoodies.forms.util.DialogBaseUnits globalDialogBaseUnits = computeDialogBaseUnits(metrics);
        return globalDialogBaseUnits;
    }

    private java.awt.Font getCachedDefaultDialogFont()
    {
        com.jgoodies.forms.util.FormUtils.ensureValidCache();
        if (cachedDefaultDialogFont == null)
            cachedDefaultDialogFont = lookupDefaultDialogFont();
        return cachedDefaultDialogFont;
    }

    private java.awt.Font lookupDefaultDialogFont()
    {
        java.awt.Font buttonFont = javax.swing.UIManager.getFont("Button.font");
        return buttonFont == null ? (new JButton()).getFont() : buttonFont;
    }

    private java.awt.Component createDefaultGlobalComponent()
    {
        return new JPanel(null);
    }

    void clearCache()
    {
        cachedGlobalDialogBaseUnits = null;
        cachedFontMetrics = null;
        cachedDefaultDialogFont = null;
    }

    public synchronized void addPropertyChangeListener(java.beans.PropertyChangeListener listener)
    {
        changeSupport.addPropertyChangeListener(listener);
    }

    public synchronized void removePropertyChangeListener(java.beans.PropertyChangeListener listener)
    {
        changeSupport.removePropertyChangeListener(listener);
    }

    public synchronized void addPropertyChangeListener(java.lang.String propertyName, java.beans.PropertyChangeListener listener)
    {
        changeSupport.addPropertyChangeListener(propertyName, listener);
    }

    public synchronized void removePropertyChangeListener(java.lang.String propertyName, java.beans.PropertyChangeListener listener)
    {
        changeSupport.removePropertyChangeListener(propertyName, listener);
    }

    public static final java.lang.String PROPERTY_AVERAGE_CHARACTER_WIDTH_TEST_STRING = "averageCharacterWidthTestString";
    public static final java.lang.String PROPERTY_DEFAULT_DIALOG_FONT = "defaultDialogFont";
    public static final java.lang.String MODERN_AVERAGE_CHARACTER_TEST_STRING = "abcdefghijklmnopqrstuvwxyz0123456789";
    public static final java.lang.String BALANCED_AVERAGE_CHARACTER_TEST_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final java.util.logging.Logger LOGGER = java.util.logging.Logger.getLogger(com/jgoodies/forms/util/DefaultUnitConverter.getName());
    private static com.jgoodies.forms.util.DefaultUnitConverter instance;
    private java.lang.String averageCharWidthTestString;
    private java.awt.Font defaultDialogFont;
    private final java.beans.PropertyChangeSupport changeSupport = new PropertyChangeSupport(this);
    private com.jgoodies.forms.util.DialogBaseUnits cachedGlobalDialogBaseUnits;
    private com.jgoodies.forms.util.DialogBaseUnits cachedDialogBaseUnits;
    private java.awt.FontMetrics cachedFontMetrics;
    private java.awt.Font cachedDefaultDialogFont;

}
