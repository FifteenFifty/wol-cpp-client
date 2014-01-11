// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   FormDebugUtils.java

package com.jgoodies.forms.debug;

import com.jgoodies.common.base.Preconditions;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;
import java.awt.Component;
import java.awt.Container;
import java.io.PrintStream;
import javax.swing.JLabel;

public final class FormDebugUtils
{

    private FormDebugUtils()
    {
    }

    public static void dumpAll(java.awt.Container container)
    {
        if (!(container.getLayout() instanceof com.jgoodies.forms.layout.FormLayout))
        {
            java.lang.System.out.println("The container's layout is not a FormLayout.");
            return;
        } else
        {
            com.jgoodies.forms.layout.FormLayout layout = (com.jgoodies.forms.layout.FormLayout)container.getLayout();
            com.jgoodies.forms.debug.FormDebugUtils.dumpColumnSpecs(layout);
            com.jgoodies.forms.debug.FormDebugUtils.dumpRowSpecs(layout);
            java.lang.System.out.println();
            com.jgoodies.forms.debug.FormDebugUtils.dumpColumnGroups(layout);
            com.jgoodies.forms.debug.FormDebugUtils.dumpRowGroups(layout);
            java.lang.System.out.println();
            com.jgoodies.forms.debug.FormDebugUtils.dumpConstraints(container);
            com.jgoodies.forms.debug.FormDebugUtils.dumpGridBounds(container);
            return;
        }
    }

    public static void dumpColumnSpecs(com.jgoodies.forms.layout.FormLayout layout)
    {
        java.lang.System.out.print("COLUMN SPECS:");
        for (int col = 1; col <= layout.getColumnCount(); col++)
        {
            com.jgoodies.forms.layout.ColumnSpec colSpec = layout.getColumnSpec(col);
            java.lang.System.out.print(colSpec.toShortString());
            if (col < layout.getColumnCount())
                java.lang.System.out.print(", ");
        }

        java.lang.System.out.println();
    }

    public static void dumpRowSpecs(com.jgoodies.forms.layout.FormLayout layout)
    {
        java.lang.System.out.print("ROW SPECS:   ");
        for (int row = 1; row <= layout.getRowCount(); row++)
        {
            com.jgoodies.forms.layout.RowSpec rowSpec = layout.getRowSpec(row);
            java.lang.System.out.print(rowSpec.toShortString());
            if (row < layout.getRowCount())
                java.lang.System.out.print(", ");
        }

        java.lang.System.out.println();
    }

    public static void dumpColumnGroups(com.jgoodies.forms.layout.FormLayout layout)
    {
        com.jgoodies.forms.debug.FormDebugUtils.dumpGroups("COLUMN GROUPS: ", layout.getColumnGroups());
    }

    public static void dumpRowGroups(com.jgoodies.forms.layout.FormLayout layout)
    {
        com.jgoodies.forms.debug.FormDebugUtils.dumpGroups("ROW GROUPS:    ", layout.getRowGroups());
    }

    public static void dumpGridBounds(java.awt.Container container)
    {
        java.lang.System.out.println("GRID BOUNDS");
        com.jgoodies.forms.debug.FormDebugUtils.dumpGridBounds(com.jgoodies.forms.debug.FormDebugUtils.getLayoutInfo(container));
    }

    public static void dumpGridBounds(com.jgoodies.forms.layout.FormLayout.LayoutInfo layoutInfo)
    {
        java.lang.System.out.print("COLUMN ORIGINS: ");
        int arr$[] = layoutInfo.columnOrigins;
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; i$++)
        {
            int columnOrigin = arr$[i$];
            java.lang.System.out.print((new StringBuilder()).append(columnOrigin).append(" ").toString());
        }

        java.lang.System.out.println();
        java.lang.System.out.print("ROW ORIGINS:    ");
        arr$ = layoutInfo.rowOrigins;
        len$ = arr$.length;
        for (int i$ = 0; i$ < len$; i$++)
        {
            int rowOrigin = arr$[i$];
            java.lang.System.out.print((new StringBuilder()).append(rowOrigin).append(" ").toString());
        }

        java.lang.System.out.println();
    }

    public static void dumpConstraints(java.awt.Container container)
    {
        java.lang.System.out.println("COMPONENT CONSTRAINTS");
        if (!(container.getLayout() instanceof com.jgoodies.forms.layout.FormLayout))
        {
            java.lang.System.out.println("The container's layout is not a FormLayout.");
            return;
        }
        com.jgoodies.forms.layout.FormLayout layout = (com.jgoodies.forms.layout.FormLayout)container.getLayout();
        int childCount = container.getComponentCount();
        for (int i = 0; i < childCount; i++)
        {
            java.awt.Component child = container.getComponent(i);
            com.jgoodies.forms.layout.CellConstraints cc = layout.getConstraints(child);
            java.lang.String ccString = cc != null ? cc.toShortString(layout) : "no constraints";
            java.lang.System.out.print(ccString);
            java.lang.System.out.print("; ");
            java.lang.String childType = child.getClass().getName();
            java.lang.System.out.print(childType);
            if (child instanceof javax.swing.JLabel)
            {
                javax.swing.JLabel label = (javax.swing.JLabel)child;
                java.lang.System.out.print((new StringBuilder()).append("      \"").append(label.getText()).append("\"").toString());
            }
            if (child.getName() != null)
            {
                java.lang.System.out.print("; name=");
                java.lang.System.out.print(child.getName());
            }
            java.lang.System.out.println();
        }

        java.lang.System.out.println();
    }

    private static void dumpGroups(java.lang.String title, int allGroups[][])
    {
        java.lang.System.out.print((new StringBuilder()).append(title).append(" {").toString());
        for (int group = 0; group < allGroups.length; group++)
        {
            int groupIndices[] = allGroups[group];
            java.lang.System.out.print(" {");
            for (int i = 0; i < groupIndices.length; i++)
            {
                java.lang.System.out.print(groupIndices[i]);
                if (i < groupIndices.length - 1)
                    java.lang.System.out.print(", ");
            }

            java.lang.System.out.print("} ");
            if (group < allGroups.length - 1)
                java.lang.System.out.print(", ");
        }

        java.lang.System.out.println("}");
    }

    public static com.jgoodies.forms.layout.FormLayout.LayoutInfo getLayoutInfo(java.awt.Container container)
    {
        com.jgoodies.common.base.Preconditions.checkNotNull(container, "The container must not be null.");
        com.jgoodies.common.base.Preconditions.checkArgument(container.getLayout() instanceof com.jgoodies.forms.layout.FormLayout, "The container must use an instance of FormLayout.");
        com.jgoodies.forms.layout.FormLayout layout = (com.jgoodies.forms.layout.FormLayout)container.getLayout();
        return layout.getLayoutInfo(container);
    }
}
