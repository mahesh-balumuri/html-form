package phform.application.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.UnsupportedEncodingException;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import phform.application.frame.PureHtmlFormSingleFrame;
import phform.application.html.bean.FontBean;

public class PureHtmlFormFontProperDialog extends JDialog
{
    private PureHtmlFormSingleFrame frame;
    
    private JTextArea fontString = new JTextArea();
    
    private JComboBox familyList = new JComboBox();
    
    private JComboBox sizeList = new JComboBox();
    
    private JCheckBox boldCheck = new JCheckBox("Bold");
    
    private FontBean fontBean = null;
    
    public PureHtmlFormFontProperDialog(PureHtmlFormSingleFrame f,
            FontBean fontBean) throws HeadlessException,
            UnsupportedEncodingException
    {
        // 文本属性
        super(f, new String(new byte[] { -26, -106, -121, -26, -100, -84, -27,
                -79, -98, -26, -128, -89 }, "UTF-8"), true);
        this.frame = f;
        this.fontBean = fontBean;
        initControlPanel();
        
        JPanel fontPanel = new JPanel();
        fontPanel.add(familyList);
        fontPanel.add(sizeList);
        fontPanel.add(boldCheck);
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(fontPanel, BorderLayout.NORTH);
        
        JScrollPane jsp = new JScrollPane(fontString);
        jsp.setPreferredSize(new Dimension(500, 500));
        getContentPane().add(jsp, BorderLayout.CENTER);
        
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int screenW = (int) d.getWidth();
        int screenH = (int) d.getHeight();
        setBounds((screenW - 500) / 2, (screenH - 400) / 2, 500, 400);
    }
    
    private void initControlPanel() throws UnsupportedEncodingException
    {
        String fontNames[] =
            GraphicsEnvironment.getLocalGraphicsEnvironment()
                    .getAvailableFontFamilyNames();
        for (int i = fontNames.length; i > 0; i--)
        {
            familyList.addItem(fontNames[i - 1]);
        }
        String sizes[] =
            { "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20",
                    "21", "22", "23", "24", "25", "26", "27", "28", "36", "48",
                    "72" };
        
        // sizeList.setEditable(true);
        for (int i = 0; i < sizes.length; ++i)
        {
            sizeList.addItem(sizes[i]);
        }
        
        fontString.setWrapStyleWord(true);
        // 宋体
        fontString.setFont(new Font(new String(new byte[] { -27, -82, -117,
                -28, -67, -109 }, "UTF-8"), Font.PLAIN, 12));
        
        if (null != fontBean)
        {
            if (null == fontBean.getFont())
            {
                // 宋体
                fontBean.setFont(new Font(new String(new byte[] { -27, -82,
                        -117, -28, -67, -109 }, "UTF-8"), Font.PLAIN, 12));
            }
            
            if (null != fontBean.getFont())
            {
                familyList.setSelectedItem(fontBean.getFont().getFamily());
                sizeList.setSelectedItem(String.valueOf(fontBean.getFont()
                        .getSize()));
                boldCheck
                        .setSelected(fontBean.getFont().getStyle() == Font.BOLD ? true
                                : false);
            }
            if (null != fontBean.getInnerString())
            {
                fontString.setText(fontBean.getInnerString());
            }
        }
        
        familyList.addItemListener(new ItemListener()
        {
            public void itemStateChanged(ItemEvent e)
            {
                String family = e.getItem().toString();
                if (!family.equals(fontBean.getFont().getFamily()))
                {
                    fontBean.setFont(getControlFont());
                    frame.getCanvas().setSaved(false);
                    frame.getCanvas().repaint();
                }
            }
        });
        
        sizeList.addItemListener(new ItemListener()
        {
            
            public void itemStateChanged(ItemEvent e)
            {
                String size = e.getItem().toString();
                if (!size.equals(String.valueOf(fontBean.getFont().getSize())))
                {
                    fontBean.setFont(getControlFont());
                    frame.getCanvas().setSaved(false);
                    frame.getCanvas().repaint();
                }
            }
        });
        
        boldCheck.addItemListener(new ItemListener()
        {
            
            public void itemStateChanged(ItemEvent e)
            {
                boolean boldFlag = ((JCheckBox) e.getItem()).isSelected();
                int fontStyle = boldFlag ? Font.BOLD : Font.PLAIN;
                if (fontStyle != fontBean.getFont().getStyle())
                {
                    fontBean.setFont(getControlFont());
                    frame.getCanvas().setSaved(false);
                    frame.getCanvas().repaint();
                }
            }
        });
        
        fontString.getDocument().addDocumentListener(new DocumentListener()
        {
            
            public void changedUpdate(DocumentEvent e)
            {
                valueChanged(e);
            }
            
            public void insertUpdate(DocumentEvent e)
            {
                valueChanged(e);
            }
            
            public void removeUpdate(DocumentEvent e)
            {
                valueChanged(e);
            }
            
            private void valueChanged(DocumentEvent e)
            {
                fontBean.setInnerString(fontString.getText());
                frame.getCanvas().setSaved(false);
                frame.getCanvas().repaint();
            }
        });
    }
    
    private Font getControlFont()
    {
        String family = familyList.getSelectedItem().toString();
        String size = sizeList.getSelectedItem().toString();
        boolean boldFlag = boldCheck.isSelected();
        return new Font(family, boldFlag ? Font.BOLD : Font.PLAIN, Integer
                .parseInt(size));
    }
    
}
