// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   FormFactory.java

package com.jgoodies.forms.factories;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormSpec;
import com.jgoodies.forms.layout.RowSpec;
import com.jgoodies.forms.layout.Sizes;
import com.jgoodies.forms.util.LayoutStyle;

public final class FormFactory
{

    private FormFactory()
    {
    }

    public static final com.jgoodies.forms.layout.ColumnSpec MIN_COLSPEC;
    public static final com.jgoodies.forms.layout.ColumnSpec PREF_COLSPEC;
    public static final com.jgoodies.forms.layout.ColumnSpec DEFAULT_COLSPEC;
    public static final com.jgoodies.forms.layout.ColumnSpec GLUE_COLSPEC;
    public static final com.jgoodies.forms.layout.ColumnSpec LABEL_COMPONENT_GAP_COLSPEC = com.jgoodies.forms.layout.ColumnSpec.createGap(com.jgoodies.forms.util.LayoutStyle.getCurrent().getLabelComponentPadX());
    public static final com.jgoodies.forms.layout.ColumnSpec RELATED_GAP_COLSPEC = com.jgoodies.forms.layout.ColumnSpec.createGap(com.jgoodies.forms.util.LayoutStyle.getCurrent().getRelatedComponentsPadX());
    public static final com.jgoodies.forms.layout.ColumnSpec UNRELATED_GAP_COLSPEC = com.jgoodies.forms.layout.ColumnSpec.createGap(com.jgoodies.forms.util.LayoutStyle.getCurrent().getUnrelatedComponentsPadX());
    public static final com.jgoodies.forms.layout.ColumnSpec BUTTON_COLSPEC;
    public static final com.jgoodies.forms.layout.ColumnSpec GROWING_BUTTON_COLSPEC;
    public static final com.jgoodies.forms.layout.RowSpec MIN_ROWSPEC;
    public static final com.jgoodies.forms.layout.RowSpec PREF_ROWSPEC;
    public static final com.jgoodies.forms.layout.RowSpec DEFAULT_ROWSPEC;
    public static final com.jgoodies.forms.layout.RowSpec GLUE_ROWSPEC;
    public static final com.jgoodies.forms.layout.RowSpec LABEL_COMPONENT_GAP_ROWSPEC = com.jgoodies.forms.layout.RowSpec.createGap(com.jgoodies.forms.util.LayoutStyle.getCurrent().getLabelComponentPadY());
    public static final com.jgoodies.forms.layout.RowSpec RELATED_GAP_ROWSPEC = com.jgoodies.forms.layout.RowSpec.createGap(com.jgoodies.forms.util.LayoutStyle.getCurrent().getRelatedComponentsPadY());
    public static final com.jgoodies.forms.layout.RowSpec UNRELATED_GAP_ROWSPEC = com.jgoodies.forms.layout.RowSpec.createGap(com.jgoodies.forms.util.LayoutStyle.getCurrent().getUnrelatedComponentsPadY());
    public static final com.jgoodies.forms.layout.RowSpec NARROW_LINE_GAP_ROWSPEC = com.jgoodies.forms.layout.RowSpec.createGap(com.jgoodies.forms.util.LayoutStyle.getCurrent().getNarrowLinePad());
    public static final com.jgoodies.forms.layout.RowSpec LINE_GAP_ROWSPEC = com.jgoodies.forms.layout.RowSpec.createGap(com.jgoodies.forms.util.LayoutStyle.getCurrent().getLinePad());
    public static final com.jgoodies.forms.layout.RowSpec PARAGRAPH_GAP_ROWSPEC = com.jgoodies.forms.layout.RowSpec.createGap(com.jgoodies.forms.util.LayoutStyle.getCurrent().getParagraphPad());
    public static final com.jgoodies.forms.layout.RowSpec BUTTON_ROWSPEC;

    static 
    {
        MIN_COLSPEC = new ColumnSpec(com.jgoodies.forms.layout.Sizes.MINIMUM);
        PREF_COLSPEC = new ColumnSpec(com.jgoodies.forms.layout.Sizes.PREFERRED);
        DEFAULT_COLSPEC = new ColumnSpec(com.jgoodies.forms.layout.Sizes.DEFAULT);
        GLUE_COLSPEC = new ColumnSpec(com.jgoodies.forms.layout.ColumnSpec.DEFAULT, com.jgoodies.forms.layout.Sizes.ZERO, 1.0D);
        BUTTON_COLSPEC = new ColumnSpec(com.jgoodies.forms.layout.Sizes.bounded(com.jgoodies.forms.layout.Sizes.PREFERRED, com.jgoodies.forms.util.LayoutStyle.getCurrent().getDefaultButtonWidth(), null));
        GROWING_BUTTON_COLSPEC = new ColumnSpec(com.jgoodies.forms.layout.ColumnSpec.DEFAULT, BUTTON_COLSPEC.getSize(), 1.0D);
        MIN_ROWSPEC = new RowSpec(com.jgoodies.forms.layout.Sizes.MINIMUM);
        PREF_ROWSPEC = new RowSpec(com.jgoodies.forms.layout.Sizes.PREFERRED);
        DEFAULT_ROWSPEC = new RowSpec(com.jgoodies.forms.layout.Sizes.DEFAULT);
        GLUE_ROWSPEC = new RowSpec(com.jgoodies.forms.layout.RowSpec.DEFAULT, com.jgoodies.forms.layout.Sizes.ZERO, 1.0D);
        BUTTON_ROWSPEC = new RowSpec(com.jgoodies.forms.layout.Sizes.bounded(com.jgoodies.forms.layout.Sizes.PREFERRED, com.jgoodies.forms.util.LayoutStyle.getCurrent().getDefaultButtonHeight(), null));
    }
}
