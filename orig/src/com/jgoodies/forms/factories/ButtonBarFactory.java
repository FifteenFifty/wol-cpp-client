// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: fullnames space 
// Source File Name:   ButtonBarFactory.java

package com.jgoodies.forms.factories;

import com.jgoodies.forms.builder.ButtonBarBuilder2;
import javax.swing.JButton;
import javax.swing.JPanel;

// Referenced classes of package com.jgoodies.forms.factories:
//            CC

public final class ButtonBarFactory
{
    private static final class MyButtonBarBuilder2 extends com.jgoodies.forms.builder.ButtonBarBuilder2
    {

        public int getColumn()
        {
            return super.getColumn();
        }

        private MyButtonBarBuilder2()
        {
        }

    }


    private ButtonBarFactory()
    {
    }

    public static javax.swing.JPanel buildLeftAlignedBar(javax.swing.JButton button1)
    {
        return com.jgoodies.forms.factories.ButtonBarFactory.buildLeftAlignedBar(new javax.swing.JButton[] {
            button1
        });
    }

    public static transient javax.swing.JPanel buildLeftAlignedBar(javax.swing.JButton buttons[])
    {
        com.jgoodies.forms.builder.ButtonBarBuilder2 builder = new ButtonBarBuilder2();
        builder.addButton(buttons);
        builder.addGlue();
        return builder.getPanel();
    }

    public static javax.swing.JPanel buildLeftAlignedBar(javax.swing.JButton buttons[], boolean leftToRightButtonOrder)
    {
        com.jgoodies.forms.builder.ButtonBarBuilder2 builder = new ButtonBarBuilder2();
        builder.setLeftToRightButtonOrder(leftToRightButtonOrder);
        builder.addButton(buttons);
        builder.addGlue();
        return builder.getPanel();
    }

    public static javax.swing.JPanel buildCenteredBar(javax.swing.JButton button1)
    {
        return com.jgoodies.forms.factories.ButtonBarFactory.buildCenteredBar(new javax.swing.JButton[] {
            button1
        });
    }

    public static transient javax.swing.JPanel buildCenteredBar(javax.swing.JButton buttons[])
    {
        com.jgoodies.forms.builder.ButtonBarBuilder2 builder = new ButtonBarBuilder2();
        builder.addGlue();
        builder.addButton(buttons);
        builder.addGlue();
        return builder.getPanel();
    }

    public static javax.swing.JPanel buildGrowingBar(javax.swing.JButton button1)
    {
        return com.jgoodies.forms.factories.ButtonBarFactory.buildGrowingBar(new javax.swing.JButton[] {
            button1
        });
    }

    public static transient javax.swing.JPanel buildGrowingBar(javax.swing.JButton buttons[])
    {
        com.jgoodies.forms.builder.ButtonBarBuilder2 builder = new ButtonBarBuilder2();
        builder.addGrowing(buttons);
        return builder.getPanel();
    }

    public static javax.swing.JPanel buildRightAlignedBar(javax.swing.JButton button1)
    {
        return com.jgoodies.forms.factories.ButtonBarFactory.buildRightAlignedBar(new javax.swing.JButton[] {
            button1
        });
    }

    public static transient javax.swing.JPanel buildRightAlignedBar(javax.swing.JButton buttons[])
    {
        com.jgoodies.forms.builder.ButtonBarBuilder2 builder = new ButtonBarBuilder2();
        builder.addGlue();
        builder.addButton(buttons);
        return builder.getPanel();
    }

    public static javax.swing.JPanel buildRightAlignedBar(javax.swing.JButton buttons[], boolean leftToRightButtonOrder)
    {
        com.jgoodies.forms.builder.ButtonBarBuilder2 builder = new ButtonBarBuilder2();
        builder.setLeftToRightButtonOrder(leftToRightButtonOrder);
        builder.addGlue();
        builder.addButton(buttons);
        return builder.getPanel();
    }

    public static javax.swing.JPanel buildHelpBar(javax.swing.JButton help, javax.swing.JButton button1)
    {
        return com.jgoodies.forms.factories.ButtonBarFactory.buildHelpBar(help, new javax.swing.JButton[] {
            button1
        });
    }

    public static transient javax.swing.JPanel buildHelpBar(javax.swing.JButton help, javax.swing.JButton buttons[])
    {
        com.jgoodies.forms.builder.ButtonBarBuilder2 builder = new ButtonBarBuilder2();
        builder.addButton(help);
        builder.addUnrelatedGap();
        builder.addGlue();
        builder.addButton(buttons);
        return builder.getPanel();
    }

    public static javax.swing.JPanel buildCloseBar(javax.swing.JButton close)
    {
        return com.jgoodies.forms.factories.ButtonBarFactory.buildRightAlignedBar(close);
    }

    public static javax.swing.JPanel buildOKBar(javax.swing.JButton ok)
    {
        return com.jgoodies.forms.factories.ButtonBarFactory.buildRightAlignedBar(ok);
    }

    public static javax.swing.JPanel buildOKCancelBar(javax.swing.JButton ok, javax.swing.JButton cancel)
    {
        return com.jgoodies.forms.factories.ButtonBarFactory.buildRightAlignedBar(new javax.swing.JButton[] {
            ok, cancel
        });
    }

    public static javax.swing.JPanel buildOKCancelApplyBar(javax.swing.JButton ok, javax.swing.JButton cancel, javax.swing.JButton apply)
    {
        return com.jgoodies.forms.factories.ButtonBarFactory.buildRightAlignedBar(new javax.swing.JButton[] {
            ok, cancel, apply
        });
    }

    public static javax.swing.JPanel buildHelpCloseBar(javax.swing.JButton help, javax.swing.JButton close)
    {
        return com.jgoodies.forms.factories.ButtonBarFactory.buildHelpBar(help, close);
    }

    public static javax.swing.JPanel buildHelpOKBar(javax.swing.JButton help, javax.swing.JButton ok)
    {
        return com.jgoodies.forms.factories.ButtonBarFactory.buildHelpBar(help, ok);
    }

    public static javax.swing.JPanel buildHelpOKCancelBar(javax.swing.JButton help, javax.swing.JButton ok, javax.swing.JButton cancel)
    {
        return com.jgoodies.forms.factories.ButtonBarFactory.buildHelpBar(help, new javax.swing.JButton[] {
            ok, cancel
        });
    }

    public static javax.swing.JPanel buildHelpOKCancelApplyBar(javax.swing.JButton help, javax.swing.JButton ok, javax.swing.JButton cancel, javax.swing.JButton apply)
    {
        return com.jgoodies.forms.factories.ButtonBarFactory.buildHelpBar(help, new javax.swing.JButton[] {
            ok, cancel, apply
        });
    }

    public static javax.swing.JPanel buildCloseHelpBar(javax.swing.JButton close, javax.swing.JButton help)
    {
        return com.jgoodies.forms.factories.ButtonBarFactory.buildRightAlignedBar(new javax.swing.JButton[] {
            close, help
        });
    }

    public static javax.swing.JPanel buildOKHelpBar(javax.swing.JButton ok, javax.swing.JButton help)
    {
        return com.jgoodies.forms.factories.ButtonBarFactory.buildRightAlignedBar(new javax.swing.JButton[] {
            ok, help
        });
    }

    public static javax.swing.JPanel buildOKCancelHelpBar(javax.swing.JButton ok, javax.swing.JButton cancel, javax.swing.JButton help)
    {
        return com.jgoodies.forms.factories.ButtonBarFactory.buildRightAlignedBar(new javax.swing.JButton[] {
            ok, cancel, help
        });
    }

    public static javax.swing.JPanel buildOKCancelApplyHelpBar(javax.swing.JButton ok, javax.swing.JButton cancel, javax.swing.JButton apply, javax.swing.JButton help)
    {
        return com.jgoodies.forms.factories.ButtonBarFactory.buildRightAlignedBar(new javax.swing.JButton[] {
            ok, cancel, apply, help
        });
    }

    public static javax.swing.JPanel buildAddRemoveLeftBar(javax.swing.JButton add, javax.swing.JButton remove)
    {
        return com.jgoodies.forms.factories.ButtonBarFactory.buildLeftAlignedBar(new javax.swing.JButton[] {
            add, remove
        });
    }

    public static javax.swing.JPanel buildAddRemoveBar(javax.swing.JButton add, javax.swing.JButton remove)
    {
        return com.jgoodies.forms.factories.ButtonBarFactory.buildGrowingBar(new javax.swing.JButton[] {
            add, remove
        });
    }

    public static javax.swing.JPanel buildAddRemoveRightBar(javax.swing.JButton add, javax.swing.JButton remove)
    {
        return com.jgoodies.forms.factories.ButtonBarFactory.buildRightAlignedBar(new javax.swing.JButton[] {
            add, remove
        });
    }

    public static javax.swing.JPanel buildAddRemovePropertiesLeftBar(javax.swing.JButton add, javax.swing.JButton remove, javax.swing.JButton properties)
    {
        return com.jgoodies.forms.factories.ButtonBarFactory.buildLeftAlignedBar(new javax.swing.JButton[] {
            add, remove, properties
        });
    }

    public static javax.swing.JPanel buildAddRemovePropertiesBar(javax.swing.JButton add, javax.swing.JButton remove, javax.swing.JButton properties)
    {
        com.jgoodies.forms.builder.ButtonBarBuilder2 builder = new ButtonBarBuilder2();
        builder.addButton(add);
        builder.addRelatedGap();
        builder.addButton(remove);
        builder.addRelatedGap();
        builder.addButton(properties);
        return builder.getPanel();
    }

    public static javax.swing.JPanel buildAddRemovePropertiesRightBar(javax.swing.JButton add, javax.swing.JButton remove, javax.swing.JButton properties)
    {
        return com.jgoodies.forms.factories.ButtonBarFactory.buildRightAlignedBar(new javax.swing.JButton[] {
            add, remove, properties
        });
    }

    public static javax.swing.JPanel buildWizardBar(javax.swing.JButton back, javax.swing.JButton next, javax.swing.JButton finish, javax.swing.JButton cancel)
    {
        return com.jgoodies.forms.factories.ButtonBarFactory.buildWizardBar(back, next, new javax.swing.JButton[] {
            finish, cancel
        });
    }

    public static javax.swing.JPanel buildWizardBar(javax.swing.JButton help, javax.swing.JButton back, javax.swing.JButton next, javax.swing.JButton finish, javax.swing.JButton cancel)
    {
        return com.jgoodies.forms.factories.ButtonBarFactory.buildWizardBar(new javax.swing.JButton[] {
            help
        }, back, next, new javax.swing.JButton[] {
            finish, cancel
        });
    }

    public static javax.swing.JPanel buildWizardBar(javax.swing.JButton back, javax.swing.JButton next, javax.swing.JButton rightAlignedButtons[])
    {
        return com.jgoodies.forms.factories.ButtonBarFactory.buildWizardBar(((javax.swing.JButton []) (null)), back, next, rightAlignedButtons);
    }

    public static javax.swing.JPanel buildWizardBar(javax.swing.JButton leftAlignedButtons[], javax.swing.JButton back, javax.swing.JButton next, javax.swing.JButton rightAlignedButtons[])
    {
        return com.jgoodies.forms.factories.ButtonBarFactory.buildWizardBar(leftAlignedButtons, back, next, null, rightAlignedButtons);
    }

    public static javax.swing.JPanel buildWizardBar(javax.swing.JButton leftAlignedButtons[], javax.swing.JButton back, javax.swing.JButton next, javax.swing.JButton overlaidFinish, javax.swing.JButton rightAlignedButtons[])
    {
        com.jgoodies.forms.factories.MyButtonBarBuilder2 builder = new MyButtonBarBuilder2();
        if (leftAlignedButtons != null)
        {
            builder.addButton(leftAlignedButtons);
            builder.addRelatedGap();
        }
        builder.addGlue();
        builder.addButton(back);
        builder.addButton(next);
        if (overlaidFinish != null)
            builder.getPanel().add(overlaidFinish, com.jgoodies.forms.factories.CC.xy(builder.getColumn(), 1));
        if (rightAlignedButtons != null)
        {
            builder.addRelatedGap();
            builder.addButton(rightAlignedButtons);
        }
        return builder.getPanel();
    }
}
