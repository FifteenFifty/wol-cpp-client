// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   FormSpec.java

package com.jgoodies.forms.layout;

import com.jgoodies.common.base.Preconditions;
import java.awt.Container;
import java.io.Serializable;
import java.util.Locale;
import java.util.regex.Pattern;

// Referenced classes of package com.jgoodies.forms.layout:
//            BoundedSize, PrototypeSize, ConstantSize, Sizes, 
//            ColumnSpec, RowSpec, Size, FormLayout

public abstract class FormSpec
    implements java.io.Serializable
{
    public static final class DefaultAlignment
        implements java.io.Serializable
    {

        private static com.jgoodies.forms.layout.DefaultAlignment valueOf(java.lang.String str, boolean isHorizontal)
        {
            if (str.equals("f") || str.equals("fill"))
                return com.jgoodies.forms.layout.FormSpec.FILL_ALIGN;
            if (str.equals("c") || str.equals("center"))
                return com.jgoodies.forms.layout.FormSpec.CENTER_ALIGN;
            if (isHorizontal)
            {
                if (str.equals("r") || str.equals("right"))
                    return com.jgoodies.forms.layout.FormSpec.RIGHT_ALIGN;
                if (str.equals("l") || str.equals("left"))
                    return com.jgoodies.forms.layout.FormSpec.LEFT_ALIGN;
                else
                    return null;
            }
            if (str.equals("t") || str.equals("top"))
                return com.jgoodies.forms.layout.FormSpec.TOP_ALIGN;
            if (str.equals("b") || str.equals("bottom"))
                return com.jgoodies.forms.layout.FormSpec.BOTTOM_ALIGN;
            else
                return null;
        }

        public java.lang.String toString()
        {
            return name;
        }

        public char abbreviation()
        {
            return name.charAt(0);
        }

        private java.lang.Object readResolve()
        {
            return com.jgoodies.forms.layout.FormSpec.VALUES[ordinal];
        }

        private final transient java.lang.String name;
        private static int nextOrdinal = 0;
        private final int ordinal;



        private DefaultAlignment(java.lang.String name)
        {
            ordinal = nextOrdinal++;
            this.name = name;
        }

    }


    protected FormSpec(com.jgoodies.forms.layout.DefaultAlignment defaultAlignment, com.jgoodies.forms.layout.Size size, double resizeWeight)
    {
        com.jgoodies.common.base.Preconditions.checkNotNull(size, "The size must not be null.");
        com.jgoodies.common.base.Preconditions.checkArgument(resizeWeight >= 0.0D, "The resize weight must be non-negative.");
        this.defaultAlignment = defaultAlignment;
        this.size = size;
        this.resizeWeight = resizeWeight;
    }

    protected FormSpec(com.jgoodies.forms.layout.DefaultAlignment defaultAlignment, java.lang.String encodedDescription)
    {
        this(defaultAlignment, ((com.jgoodies.forms.layout.Size) (com.jgoodies.forms.layout.Sizes.DEFAULT)), 0.0D);
        parseAndInitValues(encodedDescription.toLowerCase(java.util.Locale.ENGLISH));
    }

    public final com.jgoodies.forms.layout.DefaultAlignment getDefaultAlignment()
    {
        return defaultAlignment;
    }

    public final com.jgoodies.forms.layout.Size getSize()
    {
        return size;
    }

    public final double getResizeWeight()
    {
        return resizeWeight;
    }

    final boolean canGrow()
    {
        return getResizeWeight() != 0.0D;
    }

    abstract boolean isHorizontal();

    void setDefaultAlignment(com.jgoodies.forms.layout.DefaultAlignment defaultAlignment)
    {
        this.defaultAlignment = defaultAlignment;
    }

    void setSize(com.jgoodies.forms.layout.Size size)
    {
        this.size = size;
    }

    void setResizeWeight(double resizeWeight)
    {
        this.resizeWeight = resizeWeight;
    }

    private void parseAndInitValues(java.lang.String encodedDescription)
    {
        com.jgoodies.common.base.Preconditions.checkNotBlank(encodedDescription, "The encoded form specification must not be null, empty or whitespace.");
        java.lang.String token[] = TOKEN_SEPARATOR_PATTERN.split(encodedDescription);
        com.jgoodies.common.base.Preconditions.checkArgument(token.length > 0, "The form spec must not be empty.");
        int nextIndex = 0;
        java.lang.String next = token[nextIndex++];
        com.jgoodies.forms.layout.DefaultAlignment alignment = com.jgoodies.forms.layout.DefaultAlignment.valueOf(next, isHorizontal());
        if (alignment != null)
        {
            setDefaultAlignment(alignment);
            com.jgoodies.common.base.Preconditions.checkArgument(token.length > 1, "The form spec must provide a size.");
            next = token[nextIndex++];
        }
        setSize(parseSize(next));
        if (nextIndex < token.length)
            setResizeWeight(com.jgoodies.forms.layout.FormSpec.parseResizeWeight(token[nextIndex]));
    }

    private com.jgoodies.forms.layout.Size parseSize(java.lang.String token)
    {
        if (token.startsWith("[") && token.endsWith("]"))
            return parseBoundedSize(token);
        if (token.startsWith("max(") && token.endsWith(")"))
            return parseOldBoundedSize(token, false);
        if (token.startsWith("min(") && token.endsWith(")"))
            return parseOldBoundedSize(token, true);
        else
            return parseAtomicSize(token);
    }

    private com.jgoodies.forms.layout.Size parseBoundedSize(java.lang.String token)
    {
        java.lang.String content = token.substring(1, token.length() - 1);
        java.lang.String subtoken[] = BOUNDS_SEPARATOR_PATTERN.split(content);
        com.jgoodies.forms.layout.Size basis = null;
        com.jgoodies.forms.layout.Size lower = null;
        com.jgoodies.forms.layout.Size upper = null;
        if (subtoken.length == 2)
        {
            com.jgoodies.forms.layout.Size size1 = parseAtomicSize(subtoken[0]);
            com.jgoodies.forms.layout.Size size2 = parseAtomicSize(subtoken[1]);
            if (com.jgoodies.forms.layout.FormSpec.isConstant(size1))
            {
                if (com.jgoodies.forms.layout.FormSpec.isConstant(size2))
                {
                    lower = size1;
                    basis = size2;
                    upper = size2;
                } else
                {
                    lower = size1;
                    basis = size2;
                }
            } else
            {
                basis = size1;
                upper = size2;
            }
        } else
        if (subtoken.length == 3)
        {
            lower = parseAtomicSize(subtoken[0]);
            basis = parseAtomicSize(subtoken[1]);
            upper = parseAtomicSize(subtoken[2]);
        }
        if ((lower == null || com.jgoodies.forms.layout.FormSpec.isConstant(lower)) && (upper == null || com.jgoodies.forms.layout.FormSpec.isConstant(upper)))
            return new BoundedSize(basis, lower, upper);
        else
            throw new IllegalArgumentException((new StringBuilder()).append("Illegal bounded size '").append(token).append("'. Must be one of:").append("\n[<constant size>,<logical size>]                 // lower bound").append("\n[<logical size>,<constant size>]                 // upper bound").append("\n[<constant size>,<logical size>,<constant size>] // lower and upper bound.").append("\nExamples:").append("\n[50dlu,pref]                                     // lower bound").append("\n[pref,200dlu]                                    // upper bound").append("\n[50dlu,pref,200dlu]                              // lower and upper bound.").toString());
    }

    private com.jgoodies.forms.layout.Size parseOldBoundedSize(java.lang.String token, boolean setMax)
    {
        int semicolonIndex = token.indexOf(';');
        java.lang.String sizeToken1 = token.substring(4, semicolonIndex);
        java.lang.String sizeToken2 = token.substring(semicolonIndex + 1, token.length() - 1);
        com.jgoodies.forms.layout.Size size1 = parseAtomicSize(sizeToken1);
        com.jgoodies.forms.layout.Size size2 = parseAtomicSize(sizeToken2);
        if (com.jgoodies.forms.layout.FormSpec.isConstant(size1))
            if (size2 instanceof com.jgoodies.forms.layout.Sizes.ComponentSize)
                return new BoundedSize(size2, setMax ? null : size1, setMax ? size1 : null);
            else
                throw new IllegalArgumentException("Bounded sizes must not be both constants.");
        if (com.jgoodies.forms.layout.FormSpec.isConstant(size2))
            return new BoundedSize(size1, setMax ? null : size2, setMax ? size2 : null);
        else
            throw new IllegalArgumentException("Bounded sizes must not be both logical.");
    }

    private com.jgoodies.forms.layout.Size parseAtomicSize(java.lang.String token)
    {
        java.lang.String trimmedToken = token.trim();
        if (trimmedToken.startsWith("'") && trimmedToken.endsWith("'"))
        {
            int length = trimmedToken.length();
            if (length < 2)
                throw new IllegalArgumentException("Missing closing \"'\" for prototype.");
            else
                return new PrototypeSize(trimmedToken.substring(1, length - 1));
        }
        com.jgoodies.forms.layout.Sizes.ComponentSize componentSize = com.jgoodies.forms.layout.Sizes.ComponentSize.valueOf(trimmedToken);
        if (componentSize != null)
            return componentSize;
        else
            return com.jgoodies.forms.layout.ConstantSize.valueOf(trimmedToken, isHorizontal());
    }

    private static double parseResizeWeight(java.lang.String token)
    {
        if (token.equals("g") || token.equals("grow"))
            return 1.0D;
        if (token.equals("n") || token.equals("nogrow") || token.equals("none"))
            return 0.0D;
        if ((token.startsWith("grow(") || token.startsWith("g(")) && token.endsWith(")"))
        {
            int leftParen = token.indexOf('(');
            int rightParen = token.indexOf(')');
            java.lang.String substring = token.substring(leftParen + 1, rightParen);
            return java.lang.Double.parseDouble(substring);
        } else
        {
            throw new IllegalArgumentException((new StringBuilder()).append("The resize argument '").append(token).append("' is invalid. ").append(" Must be one of: grow, g, none, n, grow(<double>), g(<double>)").toString());
        }
    }

    private static boolean isConstant(com.jgoodies.forms.layout.Size aSize)
    {
        return (aSize instanceof com.jgoodies.forms.layout.ConstantSize) || (aSize instanceof com.jgoodies.forms.layout.PrototypeSize);
    }

    public final java.lang.String toString()
    {
        java.lang.StringBuffer buffer = new StringBuffer();
        buffer.append(defaultAlignment);
        buffer.append(":");
        buffer.append(size.toString());
        buffer.append(':');
        if (resizeWeight == 0.0D)
            buffer.append("noGrow");
        else
        if (resizeWeight == 1.0D)
        {
            buffer.append("grow");
        } else
        {
            buffer.append("grow(");
            buffer.append(resizeWeight);
            buffer.append(')');
        }
        return buffer.toString();
    }

    public final java.lang.String toShortString()
    {
        java.lang.StringBuffer buffer = new StringBuffer();
        buffer.append(defaultAlignment.abbreviation());
        buffer.append(":");
        buffer.append(size.toString());
        buffer.append(':');
        if (resizeWeight == 0.0D)
            buffer.append("n");
        else
        if (resizeWeight == 1.0D)
        {
            buffer.append("g");
        } else
        {
            buffer.append("g(");
            buffer.append(resizeWeight);
            buffer.append(')');
        }
        return buffer.toString();
    }

    public final java.lang.String encode()
    {
        java.lang.StringBuffer buffer = new StringBuffer();
        com.jgoodies.forms.layout.DefaultAlignment alignmentDefault = isHorizontal() ? com.jgoodies.forms.layout.ColumnSpec.DEFAULT : com.jgoodies.forms.layout.RowSpec.DEFAULT;
        if (!alignmentDefault.equals(defaultAlignment))
        {
            buffer.append(defaultAlignment.abbreviation());
            buffer.append(":");
        }
        buffer.append(size.encode());
        if (resizeWeight != 0.0D)
            if (resizeWeight == 1.0D)
            {
                buffer.append(':');
                buffer.append("g");
            } else
            {
                buffer.append(':');
                buffer.append("g(");
                buffer.append(resizeWeight);
                buffer.append(')');
            }
        return buffer.toString();
    }

    final int maximumSize(java.awt.Container container, java.util.List components, com.jgoodies.forms.layout.FormLayout.Measure minMeasure, com.jgoodies.forms.layout.FormLayout.Measure prefMeasure, com.jgoodies.forms.layout.FormLayout.Measure defaultMeasure)
    {
        return size.maximumSize(container, components, minMeasure, prefMeasure, defaultMeasure);
    }

    static final com.jgoodies.forms.layout.DefaultAlignment LEFT_ALIGN;
    static final com.jgoodies.forms.layout.DefaultAlignment RIGHT_ALIGN;
    static final com.jgoodies.forms.layout.DefaultAlignment TOP_ALIGN;
    static final com.jgoodies.forms.layout.DefaultAlignment BOTTOM_ALIGN;
    static final com.jgoodies.forms.layout.DefaultAlignment CENTER_ALIGN;
    static final com.jgoodies.forms.layout.DefaultAlignment FILL_ALIGN;
    private static final com.jgoodies.forms.layout.DefaultAlignment VALUES[];
    public static final double NO_GROW = 0D;
    public static final double DEFAULT_GROW = 1D;
    private static final java.util.regex.Pattern TOKEN_SEPARATOR_PATTERN = java.util.regex.Pattern.compile(":");
    private static final java.util.regex.Pattern BOUNDS_SEPARATOR_PATTERN = java.util.regex.Pattern.compile("\\s*,\\s*");
    private com.jgoodies.forms.layout.DefaultAlignment defaultAlignment;
    private com.jgoodies.forms.layout.Size size;
    private double resizeWeight;

    static 
    {
        LEFT_ALIGN = new DefaultAlignment("left");
        RIGHT_ALIGN = new DefaultAlignment("right");
        TOP_ALIGN = new DefaultAlignment("top");
        BOTTOM_ALIGN = new DefaultAlignment("bottom");
        CENTER_ALIGN = new DefaultAlignment("center");
        FILL_ALIGN = new DefaultAlignment("fill");
        VALUES = (new com.jgoodies.forms.layout.DefaultAlignment[] {
            LEFT_ALIGN, RIGHT_ALIGN, TOP_ALIGN, BOTTOM_ALIGN, CENTER_ALIGN, FILL_ALIGN
        });
    }

}
