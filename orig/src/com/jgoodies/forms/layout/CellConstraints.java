// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   CellConstraints.java

package com.jgoodies.forms.layout;

import com.jgoodies.common.base.Preconditions;
import java.awt.Component;
import java.awt.Insets;
import java.awt.Rectangle;
import java.io.Serializable;
import java.util.Locale;
import java.util.StringTokenizer;

// Referenced classes of package com.jgoodies.forms.layout:
//            FormLayout, FormSpec, ColumnSpec, RowSpec, 
//            Sizes

public final class CellConstraints
    implements java.lang.Cloneable, java.io.Serializable
{
    public static final class Alignment
        implements java.io.Serializable
    {

        static com.jgoodies.forms.layout.Alignment valueOf(java.lang.String nameOrAbbreviation)
        {
            java.lang.String str = nameOrAbbreviation.toLowerCase(java.util.Locale.ENGLISH);
            if (str.equals("d") || str.equals("default"))
                return com.jgoodies.forms.layout.CellConstraints.DEFAULT;
            if (str.equals("f") || str.equals("fill"))
                return com.jgoodies.forms.layout.CellConstraints.FILL;
            if (str.equals("c") || str.equals("center"))
                return com.jgoodies.forms.layout.CellConstraints.CENTER;
            if (str.equals("l") || str.equals("left"))
                return com.jgoodies.forms.layout.CellConstraints.LEFT;
            if (str.equals("r") || str.equals("right"))
                return com.jgoodies.forms.layout.CellConstraints.RIGHT;
            if (str.equals("t") || str.equals("top"))
                return com.jgoodies.forms.layout.CellConstraints.TOP;
            if (str.equals("b") || str.equals("bottom"))
                return com.jgoodies.forms.layout.CellConstraints.BOTTOM;
            else
                throw new IllegalArgumentException((new StringBuilder()).append("Invalid alignment ").append(nameOrAbbreviation).append(". Must be one of: left, center, right, top, bottom, ").append("fill, default, l, c, r, t, b, f, d.").toString());
        }

        public java.lang.String toString()
        {
            return name;
        }

        public char abbreviation()
        {
            return name.charAt(0);
        }

        private boolean isHorizontal()
        {
            return orientation != 1;
        }

        private boolean isVertical()
        {
            return orientation != 0;
        }

        private java.lang.Object readResolve()
        {
            return com.jgoodies.forms.layout.CellConstraints.VALUES[ordinal];
        }

        private static final int HORIZONTAL = 0;
        private static final int VERTICAL = 1;
        private static final int BOTH = 2;
        private final transient java.lang.String name;
        private final transient int orientation;
        private static int nextOrdinal = 0;
        private final int ordinal;




        private Alignment(java.lang.String name, int orientation)
        {
            ordinal = nextOrdinal++;
            this.name = name;
            this.orientation = orientation;
        }

    }


    public CellConstraints()
    {
        this(1, 1);
    }

    public CellConstraints(int gridX, int gridY)
    {
        this(gridX, gridY, 1, 1);
    }

    public CellConstraints(int gridX, int gridY, com.jgoodies.forms.layout.Alignment hAlign, com.jgoodies.forms.layout.Alignment vAlign)
    {
        this(gridX, gridY, 1, 1, hAlign, vAlign, EMPTY_INSETS);
    }

    public CellConstraints(int gridX, int gridY, int gridWidth, int gridHeight)
    {
        this(gridX, gridY, gridWidth, gridHeight, DEFAULT, DEFAULT);
    }

    public CellConstraints(int gridX, int gridY, int gridWidth, int gridHeight, com.jgoodies.forms.layout.Alignment hAlign, com.jgoodies.forms.layout.Alignment vAlign)
    {
        this(gridX, gridY, gridWidth, gridHeight, hAlign, vAlign, EMPTY_INSETS);
    }

    public CellConstraints(int gridX, int gridY, int gridWidth, int gridHeight, com.jgoodies.forms.layout.Alignment hAlign, com.jgoodies.forms.layout.Alignment vAlign, java.awt.Insets insets)
    {
        this.gridX = gridX;
        this.gridY = gridY;
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.hAlign = hAlign;
        this.vAlign = vAlign;
        this.insets = insets;
        if (gridX <= 0)
            throw new IndexOutOfBoundsException("The grid x must be a positive number.");
        if (gridY <= 0)
            throw new IndexOutOfBoundsException("The grid y must be a positive number.");
        if (gridWidth <= 0)
            throw new IndexOutOfBoundsException("The grid width must be a positive number.");
        if (gridHeight <= 0)
        {
            throw new IndexOutOfBoundsException("The grid height must be a positive number.");
        } else
        {
            com.jgoodies.common.base.Preconditions.checkNotNull(hAlign, "The horizontal alignment must not be null.");
            com.jgoodies.common.base.Preconditions.checkNotNull(vAlign, "The vertical alignment must not be null.");
            ensureValidOrientations(hAlign, vAlign);
            return;
        }
    }

    public CellConstraints(java.lang.String encodedConstraints)
    {
        this();
        initFromConstraints(encodedConstraints);
    }

    public com.jgoodies.forms.layout.CellConstraints xy(int col, int row)
    {
        return xywh(col, row, 1, 1);
    }

    public com.jgoodies.forms.layout.CellConstraints xy(int col, int row, java.lang.String encodedAlignments)
    {
        return xywh(col, row, 1, 1, encodedAlignments);
    }

    public com.jgoodies.forms.layout.CellConstraints xy(int col, int row, com.jgoodies.forms.layout.Alignment colAlign, com.jgoodies.forms.layout.Alignment rowAlign)
    {
        return xywh(col, row, 1, 1, colAlign, rowAlign);
    }

    public com.jgoodies.forms.layout.CellConstraints xyw(int col, int row, int colSpan)
    {
        return xywh(col, row, colSpan, 1, DEFAULT, DEFAULT);
    }

    public com.jgoodies.forms.layout.CellConstraints xyw(int col, int row, int colSpan, java.lang.String encodedAlignments)
    {
        return xywh(col, row, colSpan, 1, encodedAlignments);
    }

    public com.jgoodies.forms.layout.CellConstraints xyw(int col, int row, int colSpan, com.jgoodies.forms.layout.Alignment colAlign, com.jgoodies.forms.layout.Alignment rowAlign)
    {
        return xywh(col, row, colSpan, 1, colAlign, rowAlign);
    }

    public com.jgoodies.forms.layout.CellConstraints xywh(int col, int row, int colSpan, int rowSpan)
    {
        return xywh(col, row, colSpan, rowSpan, DEFAULT, DEFAULT);
    }

    public com.jgoodies.forms.layout.CellConstraints xywh(int col, int row, int colSpan, int rowSpan, java.lang.String encodedAlignments)
    {
        com.jgoodies.forms.layout.CellConstraints result = xywh(col, row, colSpan, rowSpan);
        result.setAlignments(encodedAlignments, true);
        return result;
    }

    public com.jgoodies.forms.layout.CellConstraints xywh(int col, int row, int colSpan, int rowSpan, com.jgoodies.forms.layout.Alignment colAlign, com.jgoodies.forms.layout.Alignment rowAlign)
    {
        gridX = col;
        gridY = row;
        gridWidth = colSpan;
        gridHeight = rowSpan;
        hAlign = colAlign;
        vAlign = rowAlign;
        ensureValidOrientations(hAlign, vAlign);
        return this;
    }

    public com.jgoodies.forms.layout.CellConstraints rc(int row, int col)
    {
        return rchw(row, col, 1, 1);
    }

    public com.jgoodies.forms.layout.CellConstraints rc(int row, int col, java.lang.String encodedAlignments)
    {
        return rchw(row, col, 1, 1, encodedAlignments);
    }

    public com.jgoodies.forms.layout.CellConstraints rc(int row, int col, com.jgoodies.forms.layout.Alignment rowAlign, com.jgoodies.forms.layout.Alignment colAlign)
    {
        return rchw(row, col, 1, 1, rowAlign, colAlign);
    }

    public com.jgoodies.forms.layout.CellConstraints rcw(int row, int col, int colSpan)
    {
        return rchw(row, col, 1, colSpan, DEFAULT, DEFAULT);
    }

    public com.jgoodies.forms.layout.CellConstraints rcw(int row, int col, int colSpan, java.lang.String encodedAlignments)
    {
        return rchw(row, col, 1, colSpan, encodedAlignments);
    }

    public com.jgoodies.forms.layout.CellConstraints rcw(int row, int col, int colSpan, com.jgoodies.forms.layout.Alignment rowAlign, com.jgoodies.forms.layout.Alignment colAlign)
    {
        return rchw(row, col, 1, colSpan, rowAlign, colAlign);
    }

    public com.jgoodies.forms.layout.CellConstraints rchw(int row, int col, int rowSpan, int colSpan)
    {
        return rchw(row, col, rowSpan, colSpan, DEFAULT, DEFAULT);
    }

    public com.jgoodies.forms.layout.CellConstraints rchw(int row, int col, int rowSpan, int colSpan, java.lang.String encodedAlignments)
    {
        com.jgoodies.forms.layout.CellConstraints result = rchw(row, col, rowSpan, colSpan);
        result.setAlignments(encodedAlignments, false);
        return result;
    }

    public com.jgoodies.forms.layout.CellConstraints rchw(int row, int col, int rowSpan, int colSpan, com.jgoodies.forms.layout.Alignment rowAlign, com.jgoodies.forms.layout.Alignment colAlign)
    {
        return xywh(col, row, colSpan, rowSpan, colAlign, rowAlign);
    }

    private void initFromConstraints(java.lang.String encodedConstraints)
    {
        java.util.StringTokenizer tokenizer = new StringTokenizer(encodedConstraints, " ,");
        int argCount = tokenizer.countTokens();
        com.jgoodies.common.base.Preconditions.checkArgument(argCount == 2 || argCount == 4 || argCount == 6, "You must provide 2, 4 or 6 arguments.");
        java.lang.Integer nextInt = decodeInt(tokenizer.nextToken());
        com.jgoodies.common.base.Preconditions.checkArgument(nextInt != null, "First cell constraint element must be a number.");
        gridX = nextInt.intValue();
        com.jgoodies.common.base.Preconditions.checkArgument(gridX > 0, "The grid x must be a positive number.");
        nextInt = decodeInt(tokenizer.nextToken());
        com.jgoodies.common.base.Preconditions.checkArgument(nextInt != null, "Second cell constraint element must be a number.");
        gridY = nextInt.intValue();
        com.jgoodies.common.base.Preconditions.checkArgument(gridY > 0, "The grid y must be a positive number.");
        if (!tokenizer.hasMoreTokens())
            return;
        java.lang.String token = tokenizer.nextToken();
        nextInt = decodeInt(token);
        if (nextInt != null)
        {
            gridWidth = nextInt.intValue();
            if (gridWidth <= 0)
                throw new IndexOutOfBoundsException("The grid width must be a positive number.");
            nextInt = decodeInt(tokenizer.nextToken());
            if (nextInt == null)
                throw new IllegalArgumentException("Fourth cell constraint element must be like third.");
            gridHeight = nextInt.intValue();
            if (gridHeight <= 0)
                throw new IndexOutOfBoundsException("The grid height must be a positive number.");
            if (!tokenizer.hasMoreTokens())
                return;
            token = tokenizer.nextToken();
        }
        hAlign = decodeAlignment(token);
        vAlign = decodeAlignment(tokenizer.nextToken());
        ensureValidOrientations(hAlign, vAlign);
    }

    private void setAlignments(java.lang.String encodedAlignments, boolean horizontalThenVertical)
    {
        java.util.StringTokenizer tokenizer = new StringTokenizer(encodedAlignments, " ,");
        com.jgoodies.forms.layout.Alignment first = decodeAlignment(tokenizer.nextToken());
        com.jgoodies.forms.layout.Alignment second = decodeAlignment(tokenizer.nextToken());
        hAlign = horizontalThenVertical ? first : second;
        vAlign = horizontalThenVertical ? second : first;
        ensureValidOrientations(hAlign, vAlign);
    }

    private java.lang.Integer decodeInt(java.lang.String token)
    {
        try
        {
            return java.lang.Integer.decode(token);
        }
        catch (java.lang.NumberFormatException e)
        {
            return null;
        }
    }

    private com.jgoodies.forms.layout.Alignment decodeAlignment(java.lang.String encodedAlignment)
    {
        return com.jgoodies.forms.layout.Alignment.valueOf(encodedAlignment);
    }

    void ensureValidGridBounds(int colCount, int rowCount)
    {
        if (gridX <= 0)
            throw new IndexOutOfBoundsException((new StringBuilder()).append("The column index ").append(gridX).append(" must be positive.").toString());
        if (gridX > colCount)
            throw new IndexOutOfBoundsException((new StringBuilder()).append("The column index ").append(gridX).append(" must be less than or equal to ").append(colCount).append(".").toString());
        if ((gridX + gridWidth) - 1 > colCount)
            throw new IndexOutOfBoundsException((new StringBuilder()).append("The grid width ").append(gridWidth).append(" must be less than or equal to ").append((colCount - gridX) + 1).append(".").toString());
        if (gridY <= 0)
            throw new IndexOutOfBoundsException((new StringBuilder()).append("The row index ").append(gridY).append(" must be positive.").toString());
        if (gridY > rowCount)
            throw new IndexOutOfBoundsException((new StringBuilder()).append("The row index ").append(gridY).append(" must be less than or equal to ").append(rowCount).append(".").toString());
        if ((gridY + gridHeight) - 1 > rowCount)
            throw new IndexOutOfBoundsException((new StringBuilder()).append("The grid height ").append(gridHeight).append(" must be less than or equal to ").append((rowCount - gridY) + 1).append(".").toString());
        else
            return;
    }

    private void ensureValidOrientations(com.jgoodies.forms.layout.Alignment horizontalAlignment, com.jgoodies.forms.layout.Alignment verticalAlignment)
    {
        if (!horizontalAlignment.isHorizontal())
            throw new IllegalArgumentException("The horizontal alignment must be one of: left, center, right, fill, default.");
        if (!verticalAlignment.isVertical())
            throw new IllegalArgumentException("The vertical alignment must be one of: top, center, botto, fill, default.");
        else
            return;
    }

    void setBounds(java.awt.Component c, com.jgoodies.forms.layout.FormLayout layout, java.awt.Rectangle cellBounds, com.jgoodies.forms.layout.FormLayout.Measure minWidthMeasure, com.jgoodies.forms.layout.FormLayout.Measure minHeightMeasure, com.jgoodies.forms.layout.FormLayout.Measure prefWidthMeasure, com.jgoodies.forms.layout.FormLayout.Measure prefHeightMeasure)
    {
        com.jgoodies.forms.layout.ColumnSpec colSpec = gridWidth != 1 ? null : layout.getColumnSpec(gridX);
        com.jgoodies.forms.layout.RowSpec rowSpec = gridHeight != 1 ? null : layout.getRowSpec(gridY);
        com.jgoodies.forms.layout.Alignment concreteHAlign = concreteAlignment(hAlign, colSpec);
        com.jgoodies.forms.layout.Alignment concreteVAlign = concreteAlignment(vAlign, rowSpec);
        java.awt.Insets concreteInsets = insets == null ? EMPTY_INSETS : insets;
        int cellX = cellBounds.x + concreteInsets.left;
        int cellY = cellBounds.y + concreteInsets.top;
        int cellW = cellBounds.width - concreteInsets.left - concreteInsets.right;
        int cellH = cellBounds.height - concreteInsets.top - concreteInsets.bottom;
        int compW = componentSize(c, colSpec, cellW, minWidthMeasure, prefWidthMeasure);
        int compH = componentSize(c, rowSpec, cellH, minHeightMeasure, prefHeightMeasure);
        int x = origin(concreteHAlign, cellX, cellW, compW);
        int y = origin(concreteVAlign, cellY, cellH, compH);
        int w = extent(concreteHAlign, cellW, compW);
        int h = extent(concreteVAlign, cellH, compH);
        c.setBounds(x, y, w, h);
    }

    private com.jgoodies.forms.layout.Alignment concreteAlignment(com.jgoodies.forms.layout.Alignment cellAlignment, com.jgoodies.forms.layout.FormSpec formSpec)
    {
        return formSpec != null ? usedAlignment(cellAlignment, formSpec) : cellAlignment != DEFAULT ? cellAlignment : FILL;
    }

    private com.jgoodies.forms.layout.Alignment usedAlignment(com.jgoodies.forms.layout.Alignment cellAlignment, com.jgoodies.forms.layout.FormSpec formSpec)
    {
        if (cellAlignment != DEFAULT)
            return cellAlignment;
        com.jgoodies.forms.layout.FormSpec.DefaultAlignment defaultAlignment = formSpec.getDefaultAlignment();
        if (defaultAlignment == com.jgoodies.forms.layout.FormSpec.FILL_ALIGN)
            return FILL;
        if (defaultAlignment == com.jgoodies.forms.layout.ColumnSpec.LEFT)
            return LEFT;
        if (defaultAlignment == com.jgoodies.forms.layout.FormSpec.CENTER_ALIGN)
            return CENTER;
        if (defaultAlignment == com.jgoodies.forms.layout.ColumnSpec.RIGHT)
            return RIGHT;
        if (defaultAlignment == com.jgoodies.forms.layout.RowSpec.TOP)
            return TOP;
        else
            return BOTTOM;
    }

    private int componentSize(java.awt.Component component, com.jgoodies.forms.layout.FormSpec formSpec, int cellSize, com.jgoodies.forms.layout.FormLayout.Measure minMeasure, com.jgoodies.forms.layout.FormLayout.Measure prefMeasure)
    {
        if (formSpec == null)
            return prefMeasure.sizeOf(component);
        if (formSpec.getSize() == com.jgoodies.forms.layout.Sizes.MINIMUM)
            return minMeasure.sizeOf(component);
        if (formSpec.getSize() == com.jgoodies.forms.layout.Sizes.PREFERRED)
            return prefMeasure.sizeOf(component);
        else
            return java.lang.Math.min(cellSize, prefMeasure.sizeOf(component));
    }

    private int origin(com.jgoodies.forms.layout.Alignment alignment, int cellOrigin, int cellSize, int componentSize)
    {
        if (alignment == RIGHT || alignment == BOTTOM)
            return (cellOrigin + cellSize) - componentSize;
        if (alignment == CENTER)
            return cellOrigin + (cellSize - componentSize) / 2;
        else
            return cellOrigin;
    }

    private int extent(com.jgoodies.forms.layout.Alignment alignment, int cellSize, int componentSize)
    {
        return alignment != FILL ? componentSize : cellSize;
    }

    public java.lang.Object clone()
    {
        try
        {
            com.jgoodies.forms.layout.CellConstraints c = (com.jgoodies.forms.layout.CellConstraints)super.clone();
            c.insets = (java.awt.Insets)insets.clone();
            return c;
        }
        catch (java.lang.CloneNotSupportedException e)
        {
            throw new InternalError();
        }
    }

    public java.lang.String toString()
    {
        java.lang.StringBuffer buffer = new StringBuffer("CellConstraints");
        buffer.append("[x=");
        buffer.append(gridX);
        buffer.append("; y=");
        buffer.append(gridY);
        buffer.append("; w=");
        buffer.append(gridWidth);
        buffer.append("; h=");
        buffer.append(gridHeight);
        buffer.append("; hAlign=");
        buffer.append(hAlign);
        buffer.append("; vAlign=");
        buffer.append(vAlign);
        if (!EMPTY_INSETS.equals(insets))
        {
            buffer.append("; insets=");
            buffer.append(insets);
        }
        buffer.append("; honorsVisibility=");
        buffer.append(honorsVisibility);
        buffer.append(']');
        return buffer.toString();
    }

    public java.lang.String toShortString()
    {
        return toShortString(null);
    }

    public java.lang.String toShortString(com.jgoodies.forms.layout.FormLayout layout)
    {
        java.lang.StringBuffer buffer = new StringBuffer("(");
        buffer.append(formatInt(gridX));
        buffer.append(", ");
        buffer.append(formatInt(gridY));
        buffer.append(", ");
        buffer.append(formatInt(gridWidth));
        buffer.append(", ");
        buffer.append(formatInt(gridHeight));
        buffer.append(", \"");
        buffer.append(hAlign.abbreviation());
        if (hAlign == DEFAULT && layout != null)
        {
            buffer.append('=');
            com.jgoodies.forms.layout.ColumnSpec colSpec = gridWidth != 1 ? null : layout.getColumnSpec(gridX);
            buffer.append(concreteAlignment(hAlign, colSpec).abbreviation());
        }
        buffer.append(", ");
        buffer.append(vAlign.abbreviation());
        if (vAlign == DEFAULT && layout != null)
        {
            buffer.append('=');
            com.jgoodies.forms.layout.RowSpec rowSpec = gridHeight != 1 ? null : layout.getRowSpec(gridY);
            buffer.append(concreteAlignment(vAlign, rowSpec).abbreviation());
        }
        buffer.append("\"");
        if (!EMPTY_INSETS.equals(insets))
        {
            buffer.append(", ");
            buffer.append(insets);
        }
        if (honorsVisibility != null)
            buffer.append(honorsVisibility.booleanValue() ? "honors visibility" : "ignores visibility");
        buffer.append(')');
        return buffer.toString();
    }

    private java.lang.String formatInt(int number)
    {
        java.lang.String str = java.lang.Integer.toString(number);
        return number >= 10 ? str : (new StringBuilder()).append(" ").append(str).toString();
    }

    public static final com.jgoodies.forms.layout.Alignment DEFAULT;
    public static final com.jgoodies.forms.layout.Alignment FILL;
    public static final com.jgoodies.forms.layout.Alignment LEFT;
    public static final com.jgoodies.forms.layout.Alignment RIGHT;
    public static final com.jgoodies.forms.layout.Alignment CENTER;
    public static final com.jgoodies.forms.layout.Alignment TOP;
    public static final com.jgoodies.forms.layout.Alignment BOTTOM;
    private static final com.jgoodies.forms.layout.Alignment VALUES[];
    private static final java.awt.Insets EMPTY_INSETS = new Insets(0, 0, 0, 0);
    public int gridX;
    public int gridY;
    public int gridWidth;
    public int gridHeight;
    public com.jgoodies.forms.layout.Alignment hAlign;
    public com.jgoodies.forms.layout.Alignment vAlign;
    public java.awt.Insets insets;
    public java.lang.Boolean honorsVisibility;

    static 
    {
        DEFAULT = new Alignment("default", 2);
        FILL = new Alignment("fill", 2);
        LEFT = new Alignment("left", 0);
        RIGHT = new Alignment("right", 0);
        CENTER = new Alignment("center", 2);
        TOP = new Alignment("top", 1);
        BOTTOM = new Alignment("bottom", 1);
        VALUES = (new com.jgoodies.forms.layout.Alignment[] {
            DEFAULT, FILL, LEFT, RIGHT, CENTER, TOP, BOTTOM
        });
    }

}
