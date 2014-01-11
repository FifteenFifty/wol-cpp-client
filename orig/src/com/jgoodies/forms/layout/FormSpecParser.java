// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   FormSpecParser.java

package com.jgoodies.forms.layout;

import com.jgoodies.common.base.Preconditions;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Referenced classes of package com.jgoodies.forms.layout:
//            ColumnSpec, RowSpec, LayoutMap

public final class FormSpecParser
{
    static final class Multiplier
    {

        final int multiplier;
        final java.lang.String expression;
        final int offset;

        Multiplier(int multiplier, java.lang.String expression, int offset)
        {
            this.multiplier = multiplier;
            this.expression = expression;
            this.offset = offset;
        }
    }

    public static final class FormLayoutParseException extends java.lang.RuntimeException
    {

        FormLayoutParseException(java.lang.String message)
        {
            super(message);
        }

        FormLayoutParseException(java.lang.String message, java.lang.Throwable cause)
        {
            super(message, cause);
        }
    }


    private FormSpecParser(java.lang.String source, java.lang.String description, com.jgoodies.forms.layout.LayoutMap layoutMap, boolean horizontal)
    {
        com.jgoodies.common.base.Preconditions.checkNotNull(source, "The %S must not be null.", new java.lang.Object[] {
            description
        });
        com.jgoodies.common.base.Preconditions.checkNotNull(layoutMap, "The LayoutMap must not be null.");
        this.layoutMap = layoutMap;
        this.source = this.layoutMap.expand(source, horizontal);
    }

    static com.jgoodies.forms.layout.ColumnSpec[] parseColumnSpecs(java.lang.String encodedColumnSpecs, com.jgoodies.forms.layout.LayoutMap layoutMap)
    {
        com.jgoodies.forms.layout.FormSpecParser parser = new FormSpecParser(encodedColumnSpecs, "encoded column specifications", layoutMap, true);
        return parser.parseColumnSpecs();
    }

    static com.jgoodies.forms.layout.RowSpec[] parseRowSpecs(java.lang.String encodedRowSpecs, com.jgoodies.forms.layout.LayoutMap layoutMap)
    {
        com.jgoodies.forms.layout.FormSpecParser parser = new FormSpecParser(encodedRowSpecs, "encoded row specifications", layoutMap, false);
        return parser.parseRowSpecs();
    }

    private com.jgoodies.forms.layout.ColumnSpec[] parseColumnSpecs()
    {
        java.util.List encodedColumnSpecs = split(source, 0);
        int columnCount = encodedColumnSpecs.size();
        com.jgoodies.forms.layout.ColumnSpec columnSpecs[] = new com.jgoodies.forms.layout.ColumnSpec[columnCount];
        for (int i = 0; i < columnCount; i++)
        {
            java.lang.String encodedSpec = (java.lang.String)encodedColumnSpecs.get(i);
            columnSpecs[i] = com.jgoodies.forms.layout.ColumnSpec.decodeExpanded(encodedSpec);
        }

        return columnSpecs;
    }

    private com.jgoodies.forms.layout.RowSpec[] parseRowSpecs()
    {
        java.util.List encodedRowSpecs = split(source, 0);
        int rowCount = encodedRowSpecs.size();
        com.jgoodies.forms.layout.RowSpec rowSpecs[] = new com.jgoodies.forms.layout.RowSpec[rowCount];
        for (int i = 0; i < rowCount; i++)
        {
            java.lang.String encodedSpec = (java.lang.String)encodedRowSpecs.get(i);
            rowSpecs[i] = com.jgoodies.forms.layout.RowSpec.decodeExpanded(encodedSpec);
        }

        return rowSpecs;
    }

    private java.util.List split(java.lang.String expression, int offset)
    {
        java.util.List encodedSpecs = new ArrayList();
        int parenthesisLevel = 0;
        int bracketLevel = 0;
        int quoteLevel = 0;
        int length = expression.length();
        int specStart = 0;
        boolean lead = true;
        for (int i = 0; i < length; i++)
        {
            char c = expression.charAt(i);
            if (lead && java.lang.Character.isWhitespace(c))
            {
                specStart++;
                continue;
            }
            lead = false;
            if (c == ',' && parenthesisLevel == 0 && bracketLevel == 0 && quoteLevel == 0)
            {
                java.lang.String token = expression.substring(specStart, i);
                addSpec(encodedSpecs, token, offset + specStart);
                specStart = i + 1;
                lead = true;
                continue;
            }
            if (c == '(')
            {
                if (bracketLevel > 0)
                    fail(offset + i, "illegal '(' in [...]");
                parenthesisLevel++;
                continue;
            }
            if (c == ')')
            {
                if (bracketLevel > 0)
                    fail(offset + i, "illegal ')' in [...]");
                if (--parenthesisLevel < 0)
                    fail(offset + i, "missing '('");
                continue;
            }
            if (c == '[')
            {
                if (bracketLevel > 0)
                    fail(offset + i, "too many '['");
                bracketLevel++;
                continue;
            }
            if (c == ']')
            {
                if (--bracketLevel < 0)
                    fail(offset + i, "missing '['");
                continue;
            }
            if (c != '\'')
                continue;
            if (quoteLevel == 0)
            {
                quoteLevel++;
                continue;
            }
            if (quoteLevel == 1)
                quoteLevel--;
        }

        if (parenthesisLevel > 0)
            fail(offset + length, "missing ')'");
        if (bracketLevel > 0)
            fail(offset + length, "missing ']");
        if (specStart < length)
        {
            java.lang.String token = expression.substring(specStart);
            addSpec(encodedSpecs, token, offset + specStart);
        }
        return encodedSpecs;
    }

    private void addSpec(java.util.List encodedSpecs, java.lang.String expression, int offset)
    {
        java.lang.String trimmedExpression = expression.trim();
        com.jgoodies.forms.layout.Multiplier multiplier = multiplier(trimmedExpression, offset);
        if (multiplier == null)
        {
            encodedSpecs.add(trimmedExpression);
            return;
        }
        java.util.List subTokenList = split(multiplier.expression, offset + multiplier.offset);
        for (int i = 0; i < multiplier.multiplier; i++)
            encodedSpecs.addAll(subTokenList);

    }

    private com.jgoodies.forms.layout.Multiplier multiplier(java.lang.String expression, int offset)
    {
        java.util.regex.Matcher matcher = MULTIPLIER_PREFIX_PATTERN.matcher(expression);
        if (!matcher.find())
            return null;
        if (matcher.start() > 0)
            fail(offset + matcher.start(), "illegal multiplier position");
        java.util.regex.Matcher digitMatcher = DIGIT_PATTERN.matcher(expression);
        if (!digitMatcher.find())
            return null;
        java.lang.String digitStr = expression.substring(0, digitMatcher.end());
        int number = 0;
        try
        {
            number = java.lang.Integer.parseInt(digitStr);
        }
        catch (java.lang.NumberFormatException e)
        {
            fail(offset, e);
        }
        if (number <= 0)
            fail(offset, "illegal 0 multiplier");
        java.lang.String subexpression = expression.substring(matcher.end(), expression.length() - 1);
        return new Multiplier(number, subexpression, matcher.end());
    }

    public static void fail(java.lang.String source, int index, java.lang.String description)
    {
        throw new FormLayoutParseException(com.jgoodies.forms.layout.FormSpecParser.message(source, index, description));
    }

    private void fail(int index, java.lang.String description)
    {
        throw new FormLayoutParseException(com.jgoodies.forms.layout.FormSpecParser.message(source, index, description));
    }

    private void fail(int index, java.lang.NumberFormatException cause)
    {
        throw new FormLayoutParseException(com.jgoodies.forms.layout.FormSpecParser.message(source, index, "Invalid multiplier"), cause);
    }

    private static java.lang.String message(java.lang.String source, int index, java.lang.String description)
    {
        java.lang.StringBuffer buffer = new StringBuffer(10);
        buffer.append('\n');
        buffer.append(source);
        buffer.append('\n');
        for (int i = 0; i < index; i++)
            buffer.append(' ');

        buffer.append('^');
        buffer.append(description);
        java.lang.String message = buffer.toString();
        throw new FormLayoutParseException(message);
    }

    private static final java.util.regex.Pattern MULTIPLIER_PREFIX_PATTERN = java.util.regex.Pattern.compile("\\d+\\s*\\*\\s*\\(");
    private static final java.util.regex.Pattern DIGIT_PATTERN = java.util.regex.Pattern.compile("\\d+");
    private final java.lang.String source;
    private final com.jgoodies.forms.layout.LayoutMap layoutMap;

}
