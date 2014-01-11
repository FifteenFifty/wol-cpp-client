// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   LayoutMap.java

package com.jgoodies.forms.layout;

import com.jgoodies.common.base.Preconditions;
import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.util.LayoutStyle;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

// Referenced classes of package com.jgoodies.forms.layout:
//            ColumnSpec, Size, RowSpec, FormSpecParser

public final class LayoutMap
{

    public LayoutMap()
    {
        this(com.jgoodies.forms.layout.LayoutMap.getRoot());
    }

    public LayoutMap(com.jgoodies.forms.layout.LayoutMap parent)
    {
        this.parent = parent;
        columnMap = new HashMap();
        rowMap = new HashMap();
        columnMapCache = new HashMap();
        rowMapCache = new HashMap();
    }

    public static synchronized com.jgoodies.forms.layout.LayoutMap getRoot()
    {
        if (root == null)
            root = com.jgoodies.forms.layout.LayoutMap.createRoot();
        return root;
    }

    public boolean columnContainsKey(java.lang.String key)
    {
        java.lang.String resolvedKey = resolveColumnKey(key);
        return columnMap.containsKey(resolvedKey) || parent != null && parent.columnContainsKey(resolvedKey);
    }

    public java.lang.String columnGet(java.lang.String key)
    {
        java.lang.String resolvedKey = resolveColumnKey(key);
        java.lang.String cachedValue = (java.lang.String)columnMapCache.get(resolvedKey);
        if (cachedValue != null)
            return cachedValue;
        java.lang.String value = (java.lang.String)columnMap.get(resolvedKey);
        if (value == null && parent != null)
            value = parent.columnGet(resolvedKey);
        if (value == null)
        {
            return null;
        } else
        {
            java.lang.String expandedString = expand(value, true);
            columnMapCache.put(resolvedKey, expandedString);
            return expandedString;
        }
    }

    public java.lang.String columnPut(java.lang.String key, java.lang.String value)
    {
        com.jgoodies.common.base.Preconditions.checkNotNull(value, "The column expression value must not be null.");
        java.lang.String resolvedKey = resolveColumnKey(key);
        columnMapCache.clear();
        return (java.lang.String)columnMap.put(resolvedKey, value.toLowerCase(java.util.Locale.ENGLISH));
    }

    public java.lang.String columnPut(java.lang.String key, com.jgoodies.forms.layout.ColumnSpec value)
    {
        return columnPut(key, value.encode());
    }

    public java.lang.String columnPut(java.lang.String key, com.jgoodies.forms.layout.Size value)
    {
        return columnPut(key, value.encode());
    }

    public java.lang.String columnRemove(java.lang.String key)
    {
        java.lang.String resolvedKey = resolveColumnKey(key);
        columnMapCache.clear();
        return (java.lang.String)columnMap.remove(resolvedKey);
    }

    public boolean rowContainsKey(java.lang.String key)
    {
        java.lang.String resolvedKey = resolveRowKey(key);
        return rowMap.containsKey(resolvedKey) || parent != null && parent.rowContainsKey(resolvedKey);
    }

    public java.lang.String rowGet(java.lang.String key)
    {
        java.lang.String resolvedKey = resolveRowKey(key);
        java.lang.String cachedValue = (java.lang.String)rowMapCache.get(resolvedKey);
        if (cachedValue != null)
            return cachedValue;
        java.lang.String value = (java.lang.String)rowMap.get(resolvedKey);
        if (value == null && parent != null)
            value = parent.rowGet(resolvedKey);
        if (value == null)
        {
            return null;
        } else
        {
            java.lang.String expandedString = expand(value, false);
            rowMapCache.put(resolvedKey, expandedString);
            return expandedString;
        }
    }

    public java.lang.String rowPut(java.lang.String key, java.lang.String value)
    {
        com.jgoodies.common.base.Preconditions.checkNotNull(value, "The row expression value must not be null.");
        java.lang.String resolvedKey = resolveRowKey(key);
        rowMapCache.clear();
        return (java.lang.String)rowMap.put(resolvedKey, value.toLowerCase(java.util.Locale.ENGLISH));
    }

    public java.lang.String rowPut(java.lang.String key, com.jgoodies.forms.layout.RowSpec value)
    {
        return rowPut(key, value.encode());
    }

    public java.lang.String rowPut(java.lang.String key, com.jgoodies.forms.layout.Size value)
    {
        return rowPut(key, value.encode());
    }

    public java.lang.String rowRemove(java.lang.String key)
    {
        java.lang.String resolvedKey = resolveRowKey(key);
        rowMapCache.clear();
        return (java.lang.String)rowMap.remove(resolvedKey);
    }

    public java.lang.String toString()
    {
        java.lang.StringBuffer buffer = new StringBuffer(super.toString());
        buffer.append("\n  Column associations:");
        java.util.Map.Entry entry;
        for (java.util.Iterator i$ = columnMap.entrySet().iterator(); i$.hasNext(); buffer.append(entry.getValue()))
        {
            entry = (java.util.Map.Entry)i$.next();
            buffer.append("\n    ");
            buffer.append(entry.getKey());
            buffer.append("->");
        }

        buffer.append("\n  Row associations:");
        java.util.Map.Entry entry;
        for (java.util.Iterator i$ = rowMap.entrySet().iterator(); i$.hasNext(); buffer.append(entry.getValue()))
        {
            entry = (java.util.Map.Entry)i$.next();
            buffer.append("\n    ");
            buffer.append(entry.getKey());
            buffer.append("->");
        }

        return buffer.toString();
    }

    java.lang.String expand(java.lang.String expression, boolean horizontal)
    {
        int cursor = 0;
        int start = expression.indexOf('$', cursor);
        if (start == -1)
            return expression;
        java.lang.StringBuffer buffer = new StringBuffer();
        do
        {
            buffer.append(expression.substring(cursor, start));
            java.lang.String variableName = nextVariableName(expression, start);
            buffer.append(expansion(variableName, horizontal));
            cursor = start + variableName.length() + 1;
            start = expression.indexOf('$', cursor);
        } while (start != -1);
        buffer.append(expression.substring(cursor));
        return buffer.toString();
    }

    private java.lang.String nextVariableName(java.lang.String expression, int start)
    {
        int length = expression.length();
        if (length <= start)
            com.jgoodies.forms.layout.FormSpecParser.fail(expression, start, "Missing variable name after variable char '$'.");
        int end;
        if (expression.charAt(start + 1) == '{')
        {
            end = expression.indexOf('}', start + 1);
            if (end == -1)
                com.jgoodies.forms.layout.FormSpecParser.fail(expression, start, "Missing closing brace '}' for variable.");
            return expression.substring(start + 1, end + 1);
        }
        for (end = start + 1; end < length && java.lang.Character.isUnicodeIdentifierPart(expression.charAt(end)); end++);
        return expression.substring(start + 1, end);
    }

    private java.lang.String expansion(java.lang.String variableName, boolean horizontal)
    {
        java.lang.String key = com.jgoodies.forms.layout.LayoutMap.stripBraces(variableName);
        java.lang.String expansion = horizontal ? columnGet(key) : rowGet(key);
        if (expansion == null)
        {
            java.lang.String orientation = horizontal ? "column" : "row";
            throw new IllegalArgumentException((new StringBuilder()).append("Unknown ").append(orientation).append(" layout variable \"").append(key).append("\"").toString());
        } else
        {
            return expansion;
        }
    }

    private static java.lang.String stripBraces(java.lang.String variableName)
    {
        return variableName.charAt(0) != '{' ? variableName : variableName.substring(1, variableName.length() - 1);
    }

    private java.lang.String resolveColumnKey(java.lang.String key)
    {
        com.jgoodies.common.base.Preconditions.checkNotNull(key, "The column key must not be null.");
        java.lang.String lowercaseKey = key.toLowerCase(java.util.Locale.ENGLISH);
        java.lang.String defaultKey = (java.lang.String)COLUMN_ALIASES.get(lowercaseKey);
        return defaultKey != null ? defaultKey : lowercaseKey;
    }

    private java.lang.String resolveRowKey(java.lang.String key)
    {
        com.jgoodies.common.base.Preconditions.checkNotNull(key, "The row key must not be null.");
        java.lang.String lowercaseKey = key.toLowerCase(java.util.Locale.ENGLISH);
        java.lang.String defaultKey = (java.lang.String)ROW_ALIASES.get(lowercaseKey);
        return defaultKey != null ? defaultKey : lowercaseKey;
    }

    private static com.jgoodies.forms.layout.LayoutMap createRoot()
    {
        com.jgoodies.forms.layout.LayoutMap map = new LayoutMap(null);
        map.columnPut("label-component-gap", new java.lang.String[] {
            "lcg", "lcgap"
        }, com.jgoodies.forms.factories.FormFactory.LABEL_COMPONENT_GAP_COLSPEC);
        map.columnPut("related-gap", new java.lang.String[] {
            "rg", "rgap"
        }, com.jgoodies.forms.factories.FormFactory.RELATED_GAP_COLSPEC);
        map.columnPut("unrelated-gap", new java.lang.String[] {
            "ug", "ugap"
        }, com.jgoodies.forms.factories.FormFactory.UNRELATED_GAP_COLSPEC);
        map.columnPut("button", new java.lang.String[] {
            "b"
        }, com.jgoodies.forms.factories.FormFactory.BUTTON_COLSPEC);
        map.columnPut("growing-button", new java.lang.String[] {
            "gb"
        }, com.jgoodies.forms.factories.FormFactory.GROWING_BUTTON_COLSPEC);
        map.columnPut("dialog-margin", new java.lang.String[] {
            "dm", "dmargin"
        }, com.jgoodies.forms.layout.ColumnSpec.createGap(com.jgoodies.forms.util.LayoutStyle.getCurrent().getDialogMarginX()));
        map.columnPut("tabbed-dialog-margin", new java.lang.String[] {
            "tdm", "tdmargin"
        }, com.jgoodies.forms.layout.ColumnSpec.createGap(com.jgoodies.forms.util.LayoutStyle.getCurrent().getTabbedDialogMarginX()));
        map.columnPut("glue", com.jgoodies.forms.factories.FormFactory.GLUE_COLSPEC.toShortString());
        map.rowPut("label-component-gap", new java.lang.String[] {
            "lcg", "lcgap"
        }, com.jgoodies.forms.factories.FormFactory.LABEL_COMPONENT_GAP_ROWSPEC);
        map.rowPut("related-gap", new java.lang.String[] {
            "rg", "rgap"
        }, com.jgoodies.forms.factories.FormFactory.RELATED_GAP_ROWSPEC);
        map.rowPut("unrelated-gap", new java.lang.String[] {
            "ug", "ugap"
        }, com.jgoodies.forms.factories.FormFactory.UNRELATED_GAP_ROWSPEC);
        map.rowPut("narrow-line-gap", new java.lang.String[] {
            "nlg", "nlgap"
        }, com.jgoodies.forms.factories.FormFactory.NARROW_LINE_GAP_ROWSPEC);
        map.rowPut("line-gap", new java.lang.String[] {
            "lg", "lgap"
        }, com.jgoodies.forms.factories.FormFactory.LINE_GAP_ROWSPEC);
        map.rowPut("paragraph-gap", new java.lang.String[] {
            "pg", "pgap"
        }, com.jgoodies.forms.factories.FormFactory.PARAGRAPH_GAP_ROWSPEC);
        map.rowPut("dialog-margin", new java.lang.String[] {
            "dm", "dmargin"
        }, com.jgoodies.forms.layout.RowSpec.createGap(com.jgoodies.forms.util.LayoutStyle.getCurrent().getDialogMarginY()));
        map.rowPut("tabbed-dialog-margin", new java.lang.String[] {
            "tdm", "tdmargin"
        }, com.jgoodies.forms.layout.RowSpec.createGap(com.jgoodies.forms.util.LayoutStyle.getCurrent().getTabbedDialogMarginY()));
        map.rowPut("button", new java.lang.String[] {
            "b"
        }, com.jgoodies.forms.factories.FormFactory.BUTTON_ROWSPEC);
        map.rowPut("glue", com.jgoodies.forms.factories.FormFactory.GLUE_ROWSPEC);
        return map;
    }

    private void columnPut(java.lang.String key, java.lang.String aliases[], com.jgoodies.forms.layout.ColumnSpec value)
    {
        ensureLowerCase(key);
        columnPut(key, value);
        java.lang.String arr$[] = aliases;
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; i$++)
        {
            java.lang.String aliase = arr$[i$];
            ensureLowerCase(aliase);
            COLUMN_ALIASES.put(aliase, key);
        }

    }

    private void rowPut(java.lang.String key, java.lang.String aliases[], com.jgoodies.forms.layout.RowSpec value)
    {
        ensureLowerCase(key);
        rowPut(key, value);
        java.lang.String arr$[] = aliases;
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; i$++)
        {
            java.lang.String aliase = arr$[i$];
            ensureLowerCase(aliase);
            ROW_ALIASES.put(aliase, key);
        }

    }

    private void ensureLowerCase(java.lang.String str)
    {
        java.lang.String lowerCase = str.toLowerCase(java.util.Locale.ENGLISH);
        if (!lowerCase.equals(str))
            throw new IllegalArgumentException((new StringBuilder()).append("The string \"").append(str).append("\" should be lower case.").toString());
        else
            return;
    }

    private static final char VARIABLE_PREFIX_CHAR = 36;
    private static final java.util.Map COLUMN_ALIASES = new HashMap();
    private static final java.util.Map ROW_ALIASES = new HashMap();
    private static com.jgoodies.forms.layout.LayoutMap root = null;
    private final com.jgoodies.forms.layout.LayoutMap parent;
    private final java.util.Map columnMap;
    private final java.util.Map columnMapCache;
    private final java.util.Map rowMap;
    private final java.util.Map rowMapCache;

}
