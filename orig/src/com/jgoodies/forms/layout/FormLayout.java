// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   FormLayout.java

package com.jgoodies.forms.layout;

import com.jgoodies.common.base.Objects;
import com.jgoodies.common.base.Preconditions;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import javax.swing.JComponent;

// Referenced classes of package com.jgoodies.forms.layout:
//            ColumnSpec, RowSpec, CellConstraints, FormSpec, 
//            LayoutMap, Size

public final class FormLayout
    implements java.awt.LayoutManager2, java.io.Serializable
{
    public static final class LayoutInfo
    {

        public int getX()
        {
            return columnOrigins[0];
        }

        public int getY()
        {
            return rowOrigins[0];
        }

        public int getWidth()
        {
            return columnOrigins[columnOrigins.length - 1] - columnOrigins[0];
        }

        public int getHeight()
        {
            return rowOrigins[rowOrigins.length - 1] - rowOrigins[0];
        }

        public final int columnOrigins[];
        public final int rowOrigins[];

        private LayoutInfo(int xOrigins[], int yOrigins[])
        {
            columnOrigins = xOrigins;
            rowOrigins = yOrigins;
        }

    }

    private static final class ComponentSizeCache
        implements java.io.Serializable
    {

        void invalidate()
        {
            minimumSizes.clear();
            preferredSizes.clear();
        }

        java.awt.Dimension getMinimumSize(java.awt.Component component)
        {
            java.awt.Dimension size = (java.awt.Dimension)minimumSizes.get(component);
            if (size == null)
            {
                size = component.getMinimumSize();
                minimumSizes.put(component, size);
            }
            return size;
        }

        java.awt.Dimension getPreferredSize(java.awt.Component component)
        {
            java.awt.Dimension size = (java.awt.Dimension)preferredSizes.get(component);
            if (size == null)
            {
                size = component.getPreferredSize();
                preferredSizes.put(component, size);
            }
            return size;
        }

        void removeEntry(java.awt.Component component)
        {
            minimumSizes.remove(component);
            preferredSizes.remove(component);
        }

        private final java.util.Map minimumSizes;
        private final java.util.Map preferredSizes;

        private ComponentSizeCache(int initialCapacity)
        {
            minimumSizes = new HashMap(initialCapacity);
            preferredSizes = new HashMap(initialCapacity);
        }

    }

    private static final class PreferredHeightMeasure extends com.jgoodies.forms.layout.CachingMeasure
    {

        public int sizeOf(java.awt.Component c)
        {
            return cache.getPreferredSize(c).height;
        }

        private PreferredHeightMeasure(com.jgoodies.forms.layout.ComponentSizeCache cache)
        {
            super(cache);
        }

    }

    private static final class PreferredWidthMeasure extends com.jgoodies.forms.layout.CachingMeasure
    {

        public int sizeOf(java.awt.Component c)
        {
            return cache.getPreferredSize(c).width;
        }

        private PreferredWidthMeasure(com.jgoodies.forms.layout.ComponentSizeCache cache)
        {
            super(cache);
        }

    }

    private static final class MinimumHeightMeasure extends com.jgoodies.forms.layout.CachingMeasure
    {

        public int sizeOf(java.awt.Component c)
        {
            return cache.getMinimumSize(c).height;
        }

        private MinimumHeightMeasure(com.jgoodies.forms.layout.ComponentSizeCache cache)
        {
            super(cache);
        }

    }

    private static final class MinimumWidthMeasure extends com.jgoodies.forms.layout.CachingMeasure
    {

        public int sizeOf(java.awt.Component c)
        {
            return cache.getMinimumSize(c).width;
        }

        private MinimumWidthMeasure(com.jgoodies.forms.layout.ComponentSizeCache cache)
        {
            super(cache);
        }

    }

    private static abstract class CachingMeasure
        implements com.jgoodies.forms.layout.Measure, java.io.Serializable
    {

        protected final com.jgoodies.forms.layout.ComponentSizeCache cache;

        private CachingMeasure(com.jgoodies.forms.layout.ComponentSizeCache cache)
        {
            this.cache = cache;
        }

    }

    public static interface Measure
    {

        public abstract int sizeOf(java.awt.Component component);
    }


    public FormLayout()
    {
        this(new com.jgoodies.forms.layout.ColumnSpec[0], new com.jgoodies.forms.layout.RowSpec[0]);
    }

    public FormLayout(java.lang.String encodedColumnSpecs)
    {
        this(encodedColumnSpecs, com.jgoodies.forms.layout.LayoutMap.getRoot());
    }

    public FormLayout(java.lang.String encodedColumnSpecs, com.jgoodies.forms.layout.LayoutMap layoutMap)
    {
        this(com.jgoodies.forms.layout.ColumnSpec.decodeSpecs(encodedColumnSpecs, layoutMap), new com.jgoodies.forms.layout.RowSpec[0]);
    }

    public FormLayout(java.lang.String encodedColumnSpecs, java.lang.String encodedRowSpecs)
    {
        this(encodedColumnSpecs, encodedRowSpecs, com.jgoodies.forms.layout.LayoutMap.getRoot());
    }

    public FormLayout(java.lang.String encodedColumnSpecs, java.lang.String encodedRowSpecs, com.jgoodies.forms.layout.LayoutMap layoutMap)
    {
        this(com.jgoodies.forms.layout.ColumnSpec.decodeSpecs(encodedColumnSpecs, layoutMap), com.jgoodies.forms.layout.RowSpec.decodeSpecs(encodedRowSpecs, layoutMap));
    }

    public FormLayout(com.jgoodies.forms.layout.ColumnSpec colSpecs[])
    {
        this(colSpecs, new com.jgoodies.forms.layout.RowSpec[0]);
    }

    public FormLayout(com.jgoodies.forms.layout.ColumnSpec colSpecs[], com.jgoodies.forms.layout.RowSpec rowSpecs[])
    {
        honorsVisibility = true;
        com.jgoodies.common.base.Preconditions.checkNotNull(colSpecs, "The column specifications must not be null.");
        com.jgoodies.common.base.Preconditions.checkNotNull(rowSpecs, "The row specifications must not be null.");
        this.colSpecs = new ArrayList(java.util.Arrays.asList(colSpecs));
        this.rowSpecs = new ArrayList(java.util.Arrays.asList(rowSpecs));
        colGroupIndices = new int[0][];
        rowGroupIndices = new int[0][];
        int initialCapacity = (colSpecs.length * rowSpecs.length) / 4;
        constraintMap = new HashMap(initialCapacity);
        componentSizeCache = new ComponentSizeCache(initialCapacity);
        minimumWidthMeasure = new MinimumWidthMeasure(componentSizeCache);
        minimumHeightMeasure = new MinimumHeightMeasure(componentSizeCache);
        preferredWidthMeasure = new PreferredWidthMeasure(componentSizeCache);
        preferredHeightMeasure = new PreferredHeightMeasure(componentSizeCache);
    }

    public int getColumnCount()
    {
        return colSpecs.size();
    }

    public com.jgoodies.forms.layout.ColumnSpec getColumnSpec(int columnIndex)
    {
        return (com.jgoodies.forms.layout.ColumnSpec)colSpecs.get(columnIndex - 1);
    }

    public void setColumnSpec(int columnIndex, com.jgoodies.forms.layout.ColumnSpec columnSpec)
    {
        com.jgoodies.common.base.Preconditions.checkNotNull(columnSpec, "The column spec must not be null.");
        colSpecs.set(columnIndex - 1, columnSpec);
    }

    public void appendColumn(com.jgoodies.forms.layout.ColumnSpec columnSpec)
    {
        com.jgoodies.common.base.Preconditions.checkNotNull(columnSpec, "The column spec must not be null.");
        colSpecs.add(columnSpec);
    }

    public void insertColumn(int columnIndex, com.jgoodies.forms.layout.ColumnSpec columnSpec)
    {
        if (columnIndex < 1 || columnIndex > getColumnCount())
        {
            throw new IndexOutOfBoundsException((new StringBuilder()).append("The column index ").append(columnIndex).append("must be in the range [1, ").append(getColumnCount()).append("].").toString());
        } else
        {
            colSpecs.add(columnIndex - 1, columnSpec);
            shiftComponentsHorizontally(columnIndex, false);
            adjustGroupIndices(colGroupIndices, columnIndex, false);
            return;
        }
    }

    public void removeColumn(int columnIndex)
    {
        if (columnIndex < 1 || columnIndex > getColumnCount())
        {
            throw new IndexOutOfBoundsException((new StringBuilder()).append("The column index ").append(columnIndex).append(" must be in the range [1, ").append(getColumnCount()).append("].").toString());
        } else
        {
            colSpecs.remove(columnIndex - 1);
            shiftComponentsHorizontally(columnIndex, true);
            adjustGroupIndices(colGroupIndices, columnIndex, true);
            return;
        }
    }

    public int getRowCount()
    {
        return rowSpecs.size();
    }

    public com.jgoodies.forms.layout.RowSpec getRowSpec(int rowIndex)
    {
        return (com.jgoodies.forms.layout.RowSpec)rowSpecs.get(rowIndex - 1);
    }

    public void setRowSpec(int rowIndex, com.jgoodies.forms.layout.RowSpec rowSpec)
    {
        com.jgoodies.common.base.Preconditions.checkNotNull(rowSpec, "The row spec must not be null.");
        rowSpecs.set(rowIndex - 1, rowSpec);
    }

    public void appendRow(com.jgoodies.forms.layout.RowSpec rowSpec)
    {
        com.jgoodies.common.base.Preconditions.checkNotNull(rowSpec, "The row spec must not be null.");
        rowSpecs.add(rowSpec);
    }

    public void insertRow(int rowIndex, com.jgoodies.forms.layout.RowSpec rowSpec)
    {
        if (rowIndex < 1 || rowIndex > getRowCount())
        {
            throw new IndexOutOfBoundsException((new StringBuilder()).append("The row index ").append(rowIndex).append(" must be in the range [1, ").append(getRowCount()).append("].").toString());
        } else
        {
            rowSpecs.add(rowIndex - 1, rowSpec);
            shiftComponentsVertically(rowIndex, false);
            adjustGroupIndices(rowGroupIndices, rowIndex, false);
            return;
        }
    }

    public void removeRow(int rowIndex)
    {
        if (rowIndex < 1 || rowIndex > getRowCount())
        {
            throw new IndexOutOfBoundsException((new StringBuilder()).append("The row index ").append(rowIndex).append("must be in the range [1, ").append(getRowCount()).append("].").toString());
        } else
        {
            rowSpecs.remove(rowIndex - 1);
            shiftComponentsVertically(rowIndex, true);
            adjustGroupIndices(rowGroupIndices, rowIndex, true);
            return;
        }
    }

    private void shiftComponentsHorizontally(int columnIndex, boolean remove)
    {
        int offset = remove ? -1 : 1;
        java.util.Iterator i = constraintMap.entrySet().iterator();
        do
        {
            if (!i.hasNext())
                break;
            java.util.Map.Entry entry = (java.util.Map.Entry)i.next();
            com.jgoodies.forms.layout.CellConstraints constraints = (com.jgoodies.forms.layout.CellConstraints)entry.getValue();
            int x1 = constraints.gridX;
            int w = constraints.gridWidth;
            int x2 = (x1 + w) - 1;
            if (x1 == columnIndex && remove)
                throw new IllegalStateException((new StringBuilder()).append("The removed column ").append(columnIndex).append(" must not contain component origins.\n").append("Illegal component=").append(entry.getKey()).toString());
            if (x1 >= columnIndex)
                constraints.gridX += offset;
            else
            if (x2 >= columnIndex)
                constraints.gridWidth += offset;
        } while (true);
    }

    private void shiftComponentsVertically(int rowIndex, boolean remove)
    {
        int offset = remove ? -1 : 1;
        java.util.Iterator i = constraintMap.entrySet().iterator();
        do
        {
            if (!i.hasNext())
                break;
            java.util.Map.Entry entry = (java.util.Map.Entry)i.next();
            com.jgoodies.forms.layout.CellConstraints constraints = (com.jgoodies.forms.layout.CellConstraints)entry.getValue();
            int y1 = constraints.gridY;
            int h = constraints.gridHeight;
            int y2 = (y1 + h) - 1;
            if (y1 == rowIndex && remove)
                throw new IllegalStateException((new StringBuilder()).append("The removed row ").append(rowIndex).append(" must not contain component origins.\n").append("Illegal component=").append(entry.getKey()).toString());
            if (y1 >= rowIndex)
                constraints.gridY += offset;
            else
            if (y2 >= rowIndex)
                constraints.gridHeight += offset;
        } while (true);
    }

    private void adjustGroupIndices(int allGroupIndices[][], int modifiedIndex, boolean remove)
    {
        int offset = remove ? -1 : 1;
        int arr$[][] = allGroupIndices;
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; i$++)
        {
            int allGroupIndice[] = arr$[i$];
            int groupIndices[] = allGroupIndice;
            for (int i = 0; i < groupIndices.length; i++)
            {
                int index = groupIndices[i];
                if (index == modifiedIndex && remove)
                    throw new IllegalStateException((new StringBuilder()).append("The removed index ").append(modifiedIndex).append(" must not be grouped.").toString());
                if (index >= modifiedIndex)
                    groupIndices[i] += offset;
            }

        }

    }

    public com.jgoodies.forms.layout.CellConstraints getConstraints(java.awt.Component component)
    {
        return (com.jgoodies.forms.layout.CellConstraints)getConstraints0(component).clone();
    }

    private com.jgoodies.forms.layout.CellConstraints getConstraints0(java.awt.Component component)
    {
        com.jgoodies.common.base.Preconditions.checkNotNull(component, "The component must not be null.");
        com.jgoodies.forms.layout.CellConstraints constraints = (com.jgoodies.forms.layout.CellConstraints)constraintMap.get(component);
        com.jgoodies.common.base.Preconditions.checkState(constraints != null, "The component has not been added to the container.");
        return constraints;
    }

    public void setConstraints(java.awt.Component component, com.jgoodies.forms.layout.CellConstraints constraints)
    {
        com.jgoodies.common.base.Preconditions.checkNotNull(component, "The component must not be null.");
        com.jgoodies.common.base.Preconditions.checkNotNull(constraints, "The constraints must not be null.");
        constraints.ensureValidGridBounds(getColumnCount(), getRowCount());
        constraintMap.put(component, constraints.clone());
    }

    private void removeConstraints(java.awt.Component component)
    {
        constraintMap.remove(component);
        componentSizeCache.removeEntry(component);
    }

    public int[][] getColumnGroups()
    {
        return deepClone(colGroupIndices);
    }

    public void setColumnGroups(int colGroupIndices[][])
    {
        int maxColumn = getColumnCount();
        boolean usedIndices[] = new boolean[maxColumn + 1];
        for (int group = 0; group < colGroupIndices.length; group++)
        {
            for (int j = 0; j < colGroupIndices[group].length; j++)
            {
                int colIndex = colGroupIndices[group][j];
                if (colIndex < 1 || colIndex > maxColumn)
                    throw new IndexOutOfBoundsException((new StringBuilder()).append("Invalid column group index ").append(colIndex).append(" in group ").append(group + 1).toString());
                if (usedIndices[colIndex])
                    throw new IllegalArgumentException((new StringBuilder()).append("Column index ").append(colIndex).append(" must not be used in multiple column groups.").toString());
                usedIndices[colIndex] = true;
            }

        }

        this.colGroupIndices = deepClone(colGroupIndices);
    }

    public void addGroupedColumn(int columnIndex)
    {
        int newColGroups[][] = getColumnGroups();
        if (newColGroups.length == 0)
        {
            newColGroups = (new int[][] {
                new int[] {
                    columnIndex
                }
            });
        } else
        {
            int lastGroupIndex = newColGroups.length - 1;
            int lastGroup[] = newColGroups[lastGroupIndex];
            int groupSize = lastGroup.length;
            int newLastGroup[] = new int[groupSize + 1];
            java.lang.System.arraycopy(lastGroup, 0, newLastGroup, 0, groupSize);
            newLastGroup[groupSize] = columnIndex;
            newColGroups[lastGroupIndex] = newLastGroup;
        }
        setColumnGroups(newColGroups);
    }

    public int[][] getRowGroups()
    {
        return deepClone(rowGroupIndices);
    }

    public void setRowGroups(int rowGroupIndices[][])
    {
        int rowCount = getRowCount();
        boolean usedIndices[] = new boolean[rowCount + 1];
        for (int i = 0; i < rowGroupIndices.length; i++)
        {
            for (int j = 0; j < rowGroupIndices[i].length; j++)
            {
                int rowIndex = rowGroupIndices[i][j];
                if (rowIndex < 1 || rowIndex > rowCount)
                    throw new IndexOutOfBoundsException((new StringBuilder()).append("Invalid row group index ").append(rowIndex).append(" in group ").append(i + 1).toString());
                if (usedIndices[rowIndex])
                    throw new IllegalArgumentException((new StringBuilder()).append("Row index ").append(rowIndex).append(" must not be used in multiple row groups.").toString());
                usedIndices[rowIndex] = true;
            }

        }

        this.rowGroupIndices = deepClone(rowGroupIndices);
    }

    public void addGroupedRow(int rowIndex)
    {
        int newRowGroups[][] = getRowGroups();
        if (newRowGroups.length == 0)
        {
            newRowGroups = (new int[][] {
                new int[] {
                    rowIndex
                }
            });
        } else
        {
            int lastGroupIndex = newRowGroups.length - 1;
            int lastGroup[] = newRowGroups[lastGroupIndex];
            int groupSize = lastGroup.length;
            int newLastGroup[] = new int[groupSize + 1];
            java.lang.System.arraycopy(lastGroup, 0, newLastGroup, 0, groupSize);
            newLastGroup[groupSize] = rowIndex;
            newRowGroups[lastGroupIndex] = newLastGroup;
        }
        setRowGroups(newRowGroups);
    }

    public boolean getHonorsVisibility()
    {
        return honorsVisibility;
    }

    public void setHonorsVisibility(boolean b)
    {
        boolean oldHonorsVisibility = getHonorsVisibility();
        if (oldHonorsVisibility == b)
            return;
        honorsVisibility = b;
        java.util.Set componentSet = constraintMap.keySet();
        if (componentSet.isEmpty())
        {
            return;
        } else
        {
            java.awt.Component firstComponent = (java.awt.Component)componentSet.iterator().next();
            java.awt.Container container = firstComponent.getParent();
            com.jgoodies.forms.layout.FormLayout.invalidateAndRepaint(container);
            return;
        }
    }

    public void setHonorsVisibility(java.awt.Component component, java.lang.Boolean b)
    {
        com.jgoodies.forms.layout.CellConstraints constraints = getConstraints0(component);
        if (com.jgoodies.common.base.Objects.equals(b, constraints.honorsVisibility))
        {
            return;
        } else
        {
            constraints.honorsVisibility = b;
            com.jgoodies.forms.layout.FormLayout.invalidateAndRepaint(component.getParent());
            return;
        }
    }

    public void addLayoutComponent(java.lang.String name, java.awt.Component component)
    {
        throw new UnsupportedOperationException("Use #addLayoutComponent(Component, Object) instead.");
    }

    public void addLayoutComponent(java.awt.Component comp, java.lang.Object constraints)
    {
        com.jgoodies.common.base.Preconditions.checkNotNull(constraints, "The constraints must not be null.");
        if (constraints instanceof java.lang.String)
            setConstraints(comp, new CellConstraints((java.lang.String)constraints));
        else
        if (constraints instanceof com.jgoodies.forms.layout.CellConstraints)
            setConstraints(comp, (com.jgoodies.forms.layout.CellConstraints)constraints);
        else
            throw new IllegalArgumentException((new StringBuilder()).append("Illegal constraint type ").append(constraints.getClass()).toString());
    }

    public void removeLayoutComponent(java.awt.Component comp)
    {
        removeConstraints(comp);
    }

    public java.awt.Dimension minimumLayoutSize(java.awt.Container parent)
    {
        return computeLayoutSize(parent, minimumWidthMeasure, minimumHeightMeasure);
    }

    public java.awt.Dimension preferredLayoutSize(java.awt.Container parent)
    {
        return computeLayoutSize(parent, preferredWidthMeasure, preferredHeightMeasure);
    }

    public java.awt.Dimension maximumLayoutSize(java.awt.Container target)
    {
        return new Dimension(0x7fffffff, 0x7fffffff);
    }

    public float getLayoutAlignmentX(java.awt.Container parent)
    {
        return 0.5F;
    }

    public float getLayoutAlignmentY(java.awt.Container parent)
    {
        return 0.5F;
    }

    public void invalidateLayout(java.awt.Container target)
    {
        invalidateCaches();
    }

    public void layoutContainer(java.awt.Container parent)
    {
        synchronized (parent.getTreeLock())
        {
            initializeColAndRowComponentLists();
            java.awt.Dimension size = parent.getSize();
            java.awt.Insets insets = parent.getInsets();
            int totalWidth = size.width - insets.left - insets.right;
            int totalHeight = size.height - insets.top - insets.bottom;
            int x[] = computeGridOrigins(parent, totalWidth, insets.left, colSpecs, colComponents, colGroupIndices, minimumWidthMeasure, preferredWidthMeasure);
            int y[] = computeGridOrigins(parent, totalHeight, insets.top, rowSpecs, rowComponents, rowGroupIndices, minimumHeightMeasure, preferredHeightMeasure);
            layoutComponents(x, y);
        }
    }

    private void initializeColAndRowComponentLists()
    {
        colComponents = new java.util.List[getColumnCount()];
        for (int i = 0; i < getColumnCount(); i++)
            colComponents[i] = new ArrayList();

        rowComponents = new java.util.List[getRowCount()];
        for (int i = 0; i < getRowCount(); i++)
            rowComponents[i] = new ArrayList();

        java.util.Iterator i = constraintMap.entrySet().iterator();
        do
        {
            if (!i.hasNext())
                break;
            java.util.Map.Entry entry = (java.util.Map.Entry)i.next();
            java.awt.Component component = (java.awt.Component)entry.getKey();
            com.jgoodies.forms.layout.CellConstraints constraints = (com.jgoodies.forms.layout.CellConstraints)entry.getValue();
            if (takeIntoAccount(component, constraints))
            {
                if (constraints.gridWidth == 1)
                    colComponents[constraints.gridX - 1].add(component);
                if (constraints.gridHeight == 1)
                    rowComponents[constraints.gridY - 1].add(component);
            }
        } while (true);
    }

    private java.awt.Dimension computeLayoutSize(java.awt.Container parent, com.jgoodies.forms.layout.Measure defaultWidthMeasure, com.jgoodies.forms.layout.Measure defaultHeightMeasure)
    {
        java.lang.Object obj = parent.getTreeLock();
        JVM INSTR monitorenter ;
        initializeColAndRowComponentLists();
        int colWidths[] = maximumSizes(parent, colSpecs, colComponents, minimumWidthMeasure, preferredWidthMeasure, defaultWidthMeasure);
        int rowHeights[] = maximumSizes(parent, rowSpecs, rowComponents, minimumHeightMeasure, preferredHeightMeasure, defaultHeightMeasure);
        int groupedWidths[] = groupedSizes(colGroupIndices, colWidths);
        int groupedHeights[] = groupedSizes(rowGroupIndices, rowHeights);
        int xOrigins[] = computeOrigins(groupedWidths, 0);
        int yOrigins[] = computeOrigins(groupedHeights, 0);
        int width1 = com.jgoodies.forms.layout.FormLayout.sum(groupedWidths);
        int height1 = com.jgoodies.forms.layout.FormLayout.sum(groupedHeights);
        int maxWidth = width1;
        int maxHeight = height1;
        int maxFixedSizeColsTable[] = computeMaximumFixedSpanTable(colSpecs);
        int maxFixedSizeRowsTable[] = computeMaximumFixedSpanTable(rowSpecs);
        java.util.Iterator i = constraintMap.entrySet().iterator();
        do
        {
            if (!i.hasNext())
                break;
            java.util.Map.Entry entry = (java.util.Map.Entry)i.next();
            java.awt.Component component = (java.awt.Component)entry.getKey();
            com.jgoodies.forms.layout.CellConstraints constraints = (com.jgoodies.forms.layout.CellConstraints)entry.getValue();
            if (takeIntoAccount(component, constraints))
            {
                if (constraints.gridWidth > 1 && constraints.gridWidth > maxFixedSizeColsTable[constraints.gridX - 1])
                {
                    int compWidth = defaultWidthMeasure.sizeOf(component);
                    int gridX1 = constraints.gridX - 1;
                    int gridX2 = gridX1 + constraints.gridWidth;
                    int lead = xOrigins[gridX1];
                    int trail = width1 - xOrigins[gridX2];
                    int myWidth = lead + compWidth + trail;
                    if (myWidth > maxWidth)
                        maxWidth = myWidth;
                }
                if (constraints.gridHeight > 1 && constraints.gridHeight > maxFixedSizeRowsTable[constraints.gridY - 1])
                {
                    int compHeight = defaultHeightMeasure.sizeOf(component);
                    int gridY1 = constraints.gridY - 1;
                    int gridY2 = gridY1 + constraints.gridHeight;
                    int lead = yOrigins[gridY1];
                    int trail = height1 - yOrigins[gridY2];
                    int myHeight = lead + compHeight + trail;
                    if (myHeight > maxHeight)
                        maxHeight = myHeight;
                }
            }
        } while (true);
        java.awt.Insets insets = parent.getInsets();
        int width = maxWidth + insets.left + insets.right;
        int height = maxHeight + insets.top + insets.bottom;
        return new Dimension(width, height);
        java.lang.Exception exception;
        exception;
        throw exception;
    }

    private int[] computeGridOrigins(java.awt.Container container, int totalSize, int offset, java.util.List formSpecs, java.util.List componentLists[], int groupIndices[][], com.jgoodies.forms.layout.Measure minMeasure, 
            com.jgoodies.forms.layout.Measure prefMeasure)
    {
        int minSizes[] = maximumSizes(container, formSpecs, componentLists, minMeasure, prefMeasure, minMeasure);
        int prefSizes[] = maximumSizes(container, formSpecs, componentLists, minMeasure, prefMeasure, prefMeasure);
        int groupedMinSizes[] = groupedSizes(groupIndices, minSizes);
        int groupedPrefSizes[] = groupedSizes(groupIndices, prefSizes);
        int totalMinSize = com.jgoodies.forms.layout.FormLayout.sum(groupedMinSizes);
        int totalPrefSize = com.jgoodies.forms.layout.FormLayout.sum(groupedPrefSizes);
        int compressedSizes[] = compressedSizes(formSpecs, totalSize, totalMinSize, totalPrefSize, groupedMinSizes, prefSizes);
        int groupedSizes[] = groupedSizes(groupIndices, compressedSizes);
        int totalGroupedSize = com.jgoodies.forms.layout.FormLayout.sum(groupedSizes);
        int sizes[] = distributedSizes(formSpecs, totalSize, totalGroupedSize, groupedSizes);
        return computeOrigins(sizes, offset);
    }

    private int[] computeOrigins(int sizes[], int offset)
    {
        int count = sizes.length;
        int origins[] = new int[count + 1];
        origins[0] = offset;
        for (int i = 1; i <= count; i++)
            origins[i] = origins[i - 1] + sizes[i - 1];

        return origins;
    }

    private void layoutComponents(int x[], int y[])
    {
        java.awt.Rectangle cellBounds = new Rectangle();
        java.awt.Component component;
        com.jgoodies.forms.layout.CellConstraints constraints;
        for (java.util.Iterator i = constraintMap.entrySet().iterator(); i.hasNext(); constraints.setBounds(component, this, cellBounds, minimumWidthMeasure, minimumHeightMeasure, preferredWidthMeasure, preferredHeightMeasure))
        {
            java.util.Map.Entry entry = (java.util.Map.Entry)i.next();
            component = (java.awt.Component)entry.getKey();
            constraints = (com.jgoodies.forms.layout.CellConstraints)entry.getValue();
            int gridX = constraints.gridX - 1;
            int gridY = constraints.gridY - 1;
            int gridWidth = constraints.gridWidth;
            int gridHeight = constraints.gridHeight;
            cellBounds.x = x[gridX];
            cellBounds.y = y[gridY];
            cellBounds.width = x[gridX + gridWidth] - cellBounds.x;
            cellBounds.height = y[gridY + gridHeight] - cellBounds.y;
        }

    }

    private void invalidateCaches()
    {
        componentSizeCache.invalidate();
    }

    private int[] maximumSizes(java.awt.Container container, java.util.List formSpecs, java.util.List componentLists[], com.jgoodies.forms.layout.Measure minMeasure, com.jgoodies.forms.layout.Measure prefMeasure, com.jgoodies.forms.layout.Measure defaultMeasure)
    {
        int size = formSpecs.size();
        int result[] = new int[size];
        for (int i = 0; i < size; i++)
        {
            com.jgoodies.forms.layout.FormSpec formSpec = (com.jgoodies.forms.layout.FormSpec)formSpecs.get(i);
            result[i] = formSpec.maximumSize(container, componentLists[i], minMeasure, prefMeasure, defaultMeasure);
        }

        return result;
    }

    private int[] compressedSizes(java.util.List formSpecs, int totalSize, int totalMinSize, int totalPrefSize, int minSizes[], int prefSizes[])
    {
        if (totalSize < totalMinSize)
            return minSizes;
        if (totalSize >= totalPrefSize)
            return prefSizes;
        int count = formSpecs.size();
        int sizes[] = new int[count];
        double totalCompressionSpace = totalPrefSize - totalSize;
        double maxCompressionSpace = totalPrefSize - totalMinSize;
        double compressionFactor = totalCompressionSpace / maxCompressionSpace;
        for (int i = 0; i < count; i++)
        {
            com.jgoodies.forms.layout.FormSpec formSpec = (com.jgoodies.forms.layout.FormSpec)formSpecs.get(i);
            sizes[i] = prefSizes[i];
            if (formSpec.getSize().compressible())
                sizes[i] -= (int)java.lang.Math.round((double)(prefSizes[i] - minSizes[i]) * compressionFactor);
        }

        return sizes;
    }

    private int[] groupedSizes(int groups[][], int rawSizes[])
    {
        if (groups == null || groups.length == 0)
            return rawSizes;
        int sizes[] = new int[rawSizes.length];
        for (int i = 0; i < sizes.length; i++)
            sizes[i] = rawSizes[i];

        int arr$[][] = groups;
        int len$ = arr$.length;
        for (int i$ = 0; i$ < len$; i$++)
        {
            int groupIndices[] = arr$[i$];
            int groupMaxSize = 0;
            int arr$[] = groupIndices;
            int len$ = arr$.length;
            for (int i$ = 0; i$ < len$; i$++)
            {
                int groupIndice = arr$[i$];
                int index = groupIndice - 1;
                groupMaxSize = java.lang.Math.max(groupMaxSize, sizes[index]);
            }

            arr$ = groupIndices;
            len$ = arr$.length;
            for (int i$ = 0; i$ < len$; i$++)
            {
                int groupIndice = arr$[i$];
                int index = groupIndice - 1;
                sizes[index] = groupMaxSize;
            }

        }

        return sizes;
    }

    private int[] distributedSizes(java.util.List formSpecs, int totalSize, int totalPrefSize, int inputSizes[])
    {
        double totalFreeSpace = totalSize - totalPrefSize;
        if (totalFreeSpace < 0.0D)
            return inputSizes;
        int count = formSpecs.size();
        double totalWeight = 0.0D;
        for (int i = 0; i < count; i++)
        {
            com.jgoodies.forms.layout.FormSpec formSpec = (com.jgoodies.forms.layout.FormSpec)formSpecs.get(i);
            totalWeight += formSpec.getResizeWeight();
        }

        if (totalWeight == 0.0D)
            return inputSizes;
        int sizes[] = new int[count];
        double restSpace = totalFreeSpace;
        int roundedRestSpace = (int)totalFreeSpace;
        for (int i = 0; i < count; i++)
        {
            com.jgoodies.forms.layout.FormSpec formSpec = (com.jgoodies.forms.layout.FormSpec)formSpecs.get(i);
            double weight = formSpec.getResizeWeight();
            if (weight == 0.0D)
            {
                sizes[i] = inputSizes[i];
            } else
            {
                double roundingCorrection = restSpace - (double)roundedRestSpace;
                double extraSpace = (totalFreeSpace * weight) / totalWeight;
                double correctedExtraSpace = extraSpace - roundingCorrection;
                int roundedExtraSpace = (int)java.lang.Math.round(correctedExtraSpace);
                sizes[i] = inputSizes[i] + roundedExtraSpace;
                restSpace -= extraSpace;
                roundedRestSpace -= roundedExtraSpace;
            }
        }

        return sizes;
    }

    private int[] computeMaximumFixedSpanTable(java.util.List formSpecs)
    {
        int size = formSpecs.size();
        int table[] = new int[size];
        int maximumFixedSpan = 0x7fffffff;
        for (int i = size - 1; i >= 0; i--)
        {
            com.jgoodies.forms.layout.FormSpec spec = (com.jgoodies.forms.layout.FormSpec)formSpecs.get(i);
            if (spec.canGrow())
                maximumFixedSpan = 0;
            table[i] = maximumFixedSpan;
            if (maximumFixedSpan < 0x7fffffff)
                maximumFixedSpan++;
        }

        return table;
    }

    private static int sum(int sizes[])
    {
        int sum = 0;
        for (int i = sizes.length - 1; i >= 0; i--)
            sum += sizes[i];

        return sum;
    }

    private static void invalidateAndRepaint(java.awt.Container container)
    {
        if (container == null)
            return;
        if (container instanceof javax.swing.JComponent)
            ((javax.swing.JComponent)container).revalidate();
        else
            container.invalidate();
        container.repaint();
    }

    private boolean takeIntoAccount(java.awt.Component component, com.jgoodies.forms.layout.CellConstraints cc)
    {
        return component.isVisible() || cc.honorsVisibility == null && !getHonorsVisibility() || java.lang.Boolean.FALSE.equals(cc.honorsVisibility);
    }

    public com.jgoodies.forms.layout.LayoutInfo getLayoutInfo(java.awt.Container parent)
    {
        java.lang.Object obj = parent.getTreeLock();
        JVM INSTR monitorenter ;
        initializeColAndRowComponentLists();
        java.awt.Dimension size = parent.getSize();
        java.awt.Insets insets = parent.getInsets();
        int totalWidth = size.width - insets.left - insets.right;
        int totalHeight = size.height - insets.top - insets.bottom;
        int x[] = computeGridOrigins(parent, totalWidth, insets.left, colSpecs, colComponents, colGroupIndices, minimumWidthMeasure, preferredWidthMeasure);
        int y[] = computeGridOrigins(parent, totalHeight, insets.top, rowSpecs, rowComponents, rowGroupIndices, minimumHeightMeasure, preferredHeightMeasure);
        return new LayoutInfo(x, y);
        java.lang.Exception exception;
        exception;
        throw exception;
    }

    private int[][] deepClone(int array[][])
    {
        int result[][] = new int[array.length][];
        for (int i = 0; i < result.length; i++)
            result[i] = (int[])array[i].clone();

        return result;
    }

    private void writeObject(java.io.ObjectOutputStream out)
        throws java.io.IOException
    {
        invalidateCaches();
        out.defaultWriteObject();
    }

    private final java.util.List colSpecs;
    private final java.util.List rowSpecs;
    private int colGroupIndices[][];
    private int rowGroupIndices[][];
    private final java.util.Map constraintMap;
    private boolean honorsVisibility;
    private transient java.util.List colComponents[];
    private transient java.util.List rowComponents[];
    private final com.jgoodies.forms.layout.ComponentSizeCache componentSizeCache;
    private final com.jgoodies.forms.layout.Measure minimumWidthMeasure;
    private final com.jgoodies.forms.layout.Measure minimumHeightMeasure;
    private final com.jgoodies.forms.layout.Measure preferredWidthMeasure;
    private final com.jgoodies.forms.layout.Measure preferredHeightMeasure;
}
