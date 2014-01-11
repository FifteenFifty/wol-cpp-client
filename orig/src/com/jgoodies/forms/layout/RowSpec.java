// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   RowSpec.java

package com.jgoodies.forms.layout;

import com.jgoodies.common.base.Preconditions;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

// Referenced classes of package com.jgoodies.forms.layout:
//            FormSpec, LayoutMap, FormSpecParser, Size, 
//            ConstantSize

public final class RowSpec extends com.jgoodies.forms.layout.FormSpec
{

    public RowSpec(com.jgoodies.forms.layout.FormSpec.DefaultAlignment defaultAlignment, com.jgoodies.forms.layout.Size size, double resizeWeight)
    {
        super(defaultAlignment, size, resizeWeight);
    }

    public RowSpec(com.jgoodies.forms.layout.Size size)
    {
        super(DEFAULT, size, 0.0D);
    }

    private RowSpec(java.lang.String encodedDescription)
    {
        super(DEFAULT, encodedDescription);
    }

    public static com.jgoodies.forms.layout.RowSpec createGap(com.jgoodies.forms.layout.ConstantSize gapHeight)
    {
        return new RowSpec(DEFAULT, gapHeight, 0.0D);
    }

    public static com.jgoodies.forms.layout.RowSpec decode(java.lang.String encodedRowSpec)
    {
        return com.jgoodies.forms.layout.RowSpec.decode(encodedRowSpec, com.jgoodies.forms.layout.LayoutMap.getRoot());
    }

    public static com.jgoodies.forms.layout.RowSpec decode(java.lang.String encodedRowSpec, com.jgoodies.forms.layout.LayoutMap layoutMap)
    {
        com.jgoodies.common.base.Preconditions.checkNotBlank(encodedRowSpec, "The encoded row specification must not be null, empty or whitespace.");
        com.jgoodies.common.base.Preconditions.checkNotNull(layoutMap, "The LayoutMap must not be null.");
        java.lang.String trimmed = encodedRowSpec.trim();
        java.lang.String lower = trimmed.toLowerCase(java.util.Locale.ENGLISH);
        return com.jgoodies.forms.layout.RowSpec.decodeExpanded(layoutMap.expand(lower, false));
    }

    static com.jgoodies.forms.layout.RowSpec decodeExpanded(java.lang.String expandedTrimmedLowerCaseSpec)
    {
        com.jgoodies.forms.layout.RowSpec spec = (com.jgoodies.forms.layout.RowSpec)CACHE.get(expandedTrimmedLowerCaseSpec);
        if (spec == null)
        {
            spec = new RowSpec(expandedTrimmedLowerCaseSpec);
            CACHE.put(expandedTrimmedLowerCaseSpec, spec);
        }
        return spec;
    }

    public static com.jgoodies.forms.layout.RowSpec[] decodeSpecs(java.lang.String encodedRowSpecs)
    {
        return com.jgoodies.forms.layout.RowSpec.decodeSpecs(encodedRowSpecs, com.jgoodies.forms.layout.LayoutMap.getRoot());
    }

    public static com.jgoodies.forms.layout.RowSpec[] decodeSpecs(java.lang.String encodedRowSpecs, com.jgoodies.forms.layout.LayoutMap layoutMap)
    {
        return com.jgoodies.forms.layout.FormSpecParser.parseRowSpecs(encodedRowSpecs, layoutMap);
    }

    protected boolean isHorizontal()
    {
        return false;
    }

    public static final com.jgoodies.forms.layout.FormSpec.DefaultAlignment TOP;
    public static final com.jgoodies.forms.layout.FormSpec.DefaultAlignment CENTER;
    public static final com.jgoodies.forms.layout.FormSpec.DefaultAlignment BOTTOM;
    public static final com.jgoodies.forms.layout.FormSpec.DefaultAlignment FILL;
    public static final com.jgoodies.forms.layout.FormSpec.DefaultAlignment DEFAULT;
    private static final java.util.Map CACHE = new HashMap();

    static 
    {
        TOP = com.jgoodies.forms.layout.FormSpec.TOP_ALIGN;
        CENTER = com.jgoodies.forms.layout.FormSpec.CENTER_ALIGN;
        BOTTOM = com.jgoodies.forms.layout.FormSpec.BOTTOM_ALIGN;
        FILL = com.jgoodies.forms.layout.FormSpec.FILL_ALIGN;
        DEFAULT = CENTER;
    }
}
