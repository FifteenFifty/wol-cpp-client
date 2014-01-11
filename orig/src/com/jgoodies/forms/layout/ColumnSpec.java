// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   ColumnSpec.java

package com.jgoodies.forms.layout;

import com.jgoodies.common.base.Preconditions;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

// Referenced classes of package com.jgoodies.forms.layout:
//            FormSpec, LayoutMap, FormSpecParser, Size, 
//            ConstantSize

public final class ColumnSpec extends com.jgoodies.forms.layout.FormSpec
{

    public ColumnSpec(com.jgoodies.forms.layout.FormSpec.DefaultAlignment defaultAlignment, com.jgoodies.forms.layout.Size size, double resizeWeight)
    {
        super(defaultAlignment, size, resizeWeight);
    }

    public ColumnSpec(com.jgoodies.forms.layout.Size size)
    {
        super(DEFAULT, size, 0.0D);
    }

    private ColumnSpec(java.lang.String encodedDescription)
    {
        super(DEFAULT, encodedDescription);
    }

    public static com.jgoodies.forms.layout.ColumnSpec createGap(com.jgoodies.forms.layout.ConstantSize gapWidth)
    {
        return new ColumnSpec(DEFAULT, gapWidth, 0.0D);
    }

    public static com.jgoodies.forms.layout.ColumnSpec decode(java.lang.String encodedColumnSpec)
    {
        return com.jgoodies.forms.layout.ColumnSpec.decode(encodedColumnSpec, com.jgoodies.forms.layout.LayoutMap.getRoot());
    }

    public static com.jgoodies.forms.layout.ColumnSpec decode(java.lang.String encodedColumnSpec, com.jgoodies.forms.layout.LayoutMap layoutMap)
    {
        com.jgoodies.common.base.Preconditions.checkNotBlank(encodedColumnSpec, "The encoded column specification must not be null, empty or whitespace.");
        com.jgoodies.common.base.Preconditions.checkNotNull(layoutMap, "The LayoutMap must not be null.");
        java.lang.String trimmed = encodedColumnSpec.trim();
        java.lang.String lower = trimmed.toLowerCase(java.util.Locale.ENGLISH);
        return com.jgoodies.forms.layout.ColumnSpec.decodeExpanded(layoutMap.expand(lower, true));
    }

    static com.jgoodies.forms.layout.ColumnSpec decodeExpanded(java.lang.String expandedTrimmedLowerCaseSpec)
    {
        com.jgoodies.forms.layout.ColumnSpec spec = (com.jgoodies.forms.layout.ColumnSpec)CACHE.get(expandedTrimmedLowerCaseSpec);
        if (spec == null)
        {
            spec = new ColumnSpec(expandedTrimmedLowerCaseSpec);
            CACHE.put(expandedTrimmedLowerCaseSpec, spec);
        }
        return spec;
    }

    public static com.jgoodies.forms.layout.ColumnSpec[] decodeSpecs(java.lang.String encodedColumnSpecs)
    {
        return com.jgoodies.forms.layout.ColumnSpec.decodeSpecs(encodedColumnSpecs, com.jgoodies.forms.layout.LayoutMap.getRoot());
    }

    public static com.jgoodies.forms.layout.ColumnSpec[] decodeSpecs(java.lang.String encodedColumnSpecs, com.jgoodies.forms.layout.LayoutMap layoutMap)
    {
        return com.jgoodies.forms.layout.FormSpecParser.parseColumnSpecs(encodedColumnSpecs, layoutMap);
    }

    protected boolean isHorizontal()
    {
        return true;
    }

    public static final com.jgoodies.forms.layout.FormSpec.DefaultAlignment LEFT;
    public static final com.jgoodies.forms.layout.FormSpec.DefaultAlignment CENTER;
    public static final com.jgoodies.forms.layout.FormSpec.DefaultAlignment MIDDLE;
    public static final com.jgoodies.forms.layout.FormSpec.DefaultAlignment RIGHT;
    public static final com.jgoodies.forms.layout.FormSpec.DefaultAlignment FILL;
    public static final com.jgoodies.forms.layout.FormSpec.DefaultAlignment DEFAULT;
    private static final java.util.Map CACHE = new HashMap();

    static 
    {
        LEFT = com.jgoodies.forms.layout.FormSpec.LEFT_ALIGN;
        CENTER = com.jgoodies.forms.layout.FormSpec.CENTER_ALIGN;
        MIDDLE = CENTER;
        RIGHT = com.jgoodies.forms.layout.FormSpec.RIGHT_ALIGN;
        FILL = com.jgoodies.forms.layout.FormSpec.FILL_ALIGN;
        DEFAULT = FILL;
    }
}
