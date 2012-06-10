package phform.application.toolbar;

import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;

import phform.application.dialog.PureHtmlFormSingleTableDialog;
import phform.application.frame.PureHtmlFormSingleFrame;
import phform.application.html.Constants;
import phform.application.html.util.ImageIconUtil;

public class PureHtmlFormSingleToolBar extends JToolBar
{
    private PureHtmlFormSingleFrame frame;
    
    private JButton insertTableBut =
        new JButton(ImageIconUtil.getImageIcon("InsertTable.gif"));
    
    private JToggleButton insertStringBut =
        new JToggleButton(ImageIconUtil.getImageIcon("font-string.gif"));
    
    private JToggleButton insertCheckBoxBut =
        new JToggleButton(ImageIconUtil.getImageIcon("check-box.gif"));
    
    private JToggleButton insertTextAreaBut =
        new JToggleButton(ImageIconUtil.getImageIcon("text-area.gif"));
    
    private JToggleButton insertTextFieldBut =
        new JToggleButton(ImageIconUtil.getImageIcon("text-field.gif"));
    
    private JButton autoInputBut = 
        new JButton(ImageIconUtil.getImageIcon("form.gif"));
    
    private JButton centerJustifyBut =
        new JButton(ImageIconUtil.getImageIcon("center-justify.gif"));
    
    private JButton leftJustifyBut =
        new JButton(ImageIconUtil.getImageIcon("left-justify.gif"));
    
    private JButton rightJustifyBut =
        new JButton(ImageIconUtil.getImageIcon("right-justify.gif"));
    
    private JButton textJustifyBut =
        new JButton(ImageIconUtil.getImageIcon("page-properties.gif"));
    
    private JButton middleJustifyBut =
        new JButton(ImageIconUtil.getImageIcon("middle-justify.gif"));
    
    private JButton topJustifyBut =
        new JButton(ImageIconUtil.getImageIcon("top-justify.gif"));
    
    private JButton bottomJustifyBut =
        new JButton(ImageIconUtil.getImageIcon("bottom-justify.gif"));
    
    private JButton undoBut =
        new JButton(ImageIconUtil.getImageIcon("undo.gif"));
    
    private JButton redoBut =
        new JButton(ImageIconUtil.getImageIcon("redo.gif"));
    
    public PureHtmlFormSingleToolBar(PureHtmlFormSingleFrame c)
    {
        super();
        frame = c;
        insertTableBut.setToolTipText("插入表格");
        insertStringBut.setToolTipText("插入文字");
        insertCheckBoxBut.setToolTipText("插入复选框");
        insertTextAreaBut.setToolTipText("插入多行文本框");
        insertTextFieldBut.setToolTipText("插入单行文本框");
        autoInputBut.setToolTipText("填充文本框");
        centerJustifyBut.setToolTipText("左右中间对齐");
        leftJustifyBut.setToolTipText("左对齐");
        rightJustifyBut.setToolTipText("右对齐");
        textJustifyBut.setToolTipText("文本自适应");
        middleJustifyBut.setToolTipText("上下中间对齐");
        topJustifyBut.setToolTipText("上对齐");
        bottomJustifyBut.setToolTipText("下对齐");
        undoBut.setToolTipText("撤销");
        redoBut.setToolTipText("重做");
        add(insertTableBut);
        add(insertStringBut);
        add(insertCheckBoxBut);
        add(insertTextAreaBut);
        add(insertTextFieldBut);
        add(autoInputBut);
        add(centerJustifyBut);
        add(leftJustifyBut);
        add(rightJustifyBut);
        add(middleJustifyBut);
        add(topJustifyBut);
        add(bottomJustifyBut);
        add(textJustifyBut);
        add(undoBut);
        add(redoBut);
        enableInsertTableBut(false);
        enableInsertStringBut(false);
        enableInsertTextAreaBut(false);
        enableInsertCheckBoxBut(false);
        enableInsertTextFieldBut(false);
        enableAutoInputBut(false);
        enableCenterJustifyBut(false);
        enableLeftJustifyBut(false);
        enableRightJustifyBut(false);
        enableTextJustifyBut(false);
        enableMiddleJustifyBut(false);
        enableTopJustifyBut(false);
        enableBottomJustifyBut(false);
        enableUndoBut(false);
        enableRedoBut(false);
        autoInputBut.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                frame.getCanvas().autoGenerateInput();
            }
        });
        centerJustifyBut.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                frame.getCanvas().centerJustify();
            }
        });
        leftJustifyBut.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                frame.getCanvas().leftJustify();
            }
        });
        rightJustifyBut.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                frame.getCanvas().rightJustify();
            }
        });
        textJustifyBut.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                frame.getCanvas().textJustify();
            }
        });
        middleJustifyBut.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                frame.getCanvas().middleJustify();
            }
        });
        topJustifyBut.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                frame.getCanvas().topJustify();
            }
        });
        bottomJustifyBut.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                frame.getCanvas().bottomJustify();
            }
        });
        undoBut.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                frame.getCanvas().undo();
            }
        });
        redoBut.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent arg0)
            {
                frame.getCanvas().redo();
            }
        });
        insertTableBut.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                toggleAllBut();
                frame.getCanvas().setCursorStyle(0);
                
                PureHtmlFormSingleTableDialog dialog =
                    new PureHtmlFormSingleTableDialog(frame);
                dialog.show();
            }
        });
        
        insertStringBut.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                JToggleButton jtb = (JToggleButton) e.getSource();
                if (jtb.isSelected())
                {
                    frame.getCanvas().setCursorStyle(Cursor.CROSSHAIR_CURSOR);
                    frame.getCanvas().setControlElement(Constants.FONTELEMENT);
                    
                    toggleStringBut(true);
                }
                else
                {
                    frame.getCanvas().setCursorStyle(0);
                }
            }
        });
        
        insertCheckBoxBut.addActionListener(new ActionListener()
        {
            
            public void actionPerformed(ActionEvent e)
            {
                JToggleButton jtb = (JToggleButton) e.getSource();
                if (jtb.isSelected())
                {
                    frame.getCanvas().setCursorStyle(Cursor.CROSSHAIR_CURSOR);
                    frame.getCanvas().setControlElement(
                            Constants.CHECKBOXELEMENT);
                    
                    toggleCheckBoxBut(true);
                }
                else
                {
                    frame.getCanvas().setCursorStyle(0);
                }
            }
        });
        
        insertTextAreaBut.addActionListener(new ActionListener()
        {
            
            public void actionPerformed(ActionEvent e)
            {
                JToggleButton jtb = (JToggleButton) e.getSource();
                if (jtb.isSelected())
                {
                    frame.getCanvas().setCursorStyle(Cursor.CROSSHAIR_CURSOR);
                    frame.getCanvas().setControlElement(
                            Constants.TEXTAREAELEMENT);
                    
                    toggleTextAreaBut(true);
                }
                else
                {
                    frame.getCanvas().setCursorStyle(0);
                }
            }
        });
        
        insertTextFieldBut.addActionListener(new ActionListener()
        {
            
            public void actionPerformed(ActionEvent e)
            {
                JToggleButton jtb = (JToggleButton) e.getSource();
                if (jtb.isSelected())
                {
                    frame.getCanvas().setCursorStyle(Cursor.CROSSHAIR_CURSOR);
                    frame.getCanvas().setControlElement(Constants.TEXTELEMENT);
                    
                    toggleTextFieldBut(true);
                }
                else
                {
                    frame.getCanvas().setCursorStyle(0);
                }
            }
        });
    }
    
    public void enableInsertTableBut(boolean enable)
    {
        insertTableBut.setEnabled(enable);
    }
    
    public void enableInsertStringBut(boolean enable)
    {
        insertStringBut.setEnabled(enable);
    }
    
    public void enableInsertTextAreaBut(boolean enable)
    {
        insertTextAreaBut.setEnabled(enable);
    }
    
    public void enableInsertCheckBoxBut(boolean enable)
    {
        insertCheckBoxBut.setEnabled(enable);
    }
    
    public void enableInsertTextFieldBut(boolean enable)
    {
        insertTextFieldBut.setEnabled(enable);
    }
    
    public void enableAutoInputBut(boolean enable)
    {
        autoInputBut.setEnabled(enable);
    }
    
    public void enableCenterJustifyBut(boolean enable)
    {
        centerJustifyBut.setEnabled(enable);
    }
    
    public void enableLeftJustifyBut(boolean enable)
    {
        leftJustifyBut.setEnabled(enable);
    }
    
    public void enableRightJustifyBut(boolean enable)
    {
        rightJustifyBut.setEnabled(enable);
    }
    
    public void enableTextJustifyBut(boolean enable)
    {
        textJustifyBut.setEnabled(enable);
    }
    
    public void enableMiddleJustifyBut(boolean enable)
    {
        middleJustifyBut.setEnabled(enable);
    }

    public void enableTopJustifyBut(boolean enable)
    {
        topJustifyBut.setEnabled(enable);
    }
    
    public void enableBottomJustifyBut(boolean enable)
    {
        bottomJustifyBut.setEnabled(enable);
    }
    
    public void enableRedoBut(boolean enable)
    {
        redoBut.setEnabled(enable);
    }
    
    public void enableUndoBut(boolean enable)
    {
        undoBut.setEnabled(enable);
    }
    
    public void toggleTextAreaBut(boolean toggle)
    {
        insertTextAreaBut.setSelected(toggle);
        if (toggle)
        {
            toggleStringBut(false);
            toggleCheckBoxBut(false);
            toggleTextFieldBut(false);
        }
    }
    
    public void toggleStringBut(boolean toggle)
    {
        insertStringBut.setSelected(toggle);
        if (toggle)
        {
            toggleTextAreaBut(false);
            toggleCheckBoxBut(false);
            toggleTextFieldBut(false);
        }
    }
    
    public void toggleCheckBoxBut(boolean toggle)
    {
        insertCheckBoxBut.setSelected(toggle);
        if (toggle)
        {
            toggleStringBut(false);
            toggleTextAreaBut(false);
            toggleTextFieldBut(false);
        }
    }
    
    public void toggleTextFieldBut(boolean toggle)
    {
        insertTextFieldBut.setSelected(toggle);
        if (toggle)
        {
            toggleStringBut(false);
            toggleCheckBoxBut(false);
            toggleTextAreaBut(false);
        }
    }
    
    public void toggleAllBut()
    {
        toggleTextAreaBut(false);
        toggleStringBut(false);
        toggleCheckBoxBut(false);
        toggleTextFieldBut(false);
    }
}
