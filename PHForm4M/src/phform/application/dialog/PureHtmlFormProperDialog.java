package phform.application.dialog;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import phform.application.canvas.PureHtmlFormSingleCanvas;
import phform.application.frame.PureHtmlFormSingleFrame;

public class PureHtmlFormProperDialog extends JDialog
{
    private PureHtmlFormSingleFrame frame;
    
    private boolean isNew;
    
    private JTextField nameText = new JTextField();
    
    public PureHtmlFormProperDialog(PureHtmlFormSingleFrame f, boolean isNew)
            throws HeadlessException, UnsupportedEncodingException
    {
        // 表单属性
        super(f, new String(new byte[] { -24, -95, -88, -27, -115, -107, -27,
                -79, -98, -26, -128, -89 }, "UTF-8"), true);
        this.frame = f;
        this.isNew = isNew;
        if (null != frame.getCanvas() && !isNew)
        {
            nameText.setText(frame.getCanvas().getCanvasName());
        }
        GridBagConstraints gridbagConst;
        int gridx, gridy, gridwidth, gridheight, anchor, fill, ipadx, ipady;
        double weightx, weighty;
        Insets inset;
        
        GridBagLayout gridbag = new GridBagLayout();
        
        Container dialogPane = getContentPane();
        dialogPane.setLayout(gridbag);
        
        // 表单名称 :
        JLabel label =
            new JLabel(new String(new byte[] { -24, -95, -88, -27, -115, -107,
                    -27, -112, -115, -25, -89, -80, 32, 58, 32 }, "UTF-8"));
        // 第0列
        gridx = 0;
        // 第0行
        gridy = 0;
        // 占一单位宽度
        gridwidth = 1;
        // 占一单位高度
        gridheight = 1;
        // 窗口增大时组件宽度增大比率0
        weightx = 0;
        // 窗口增大时组件高度增大比率0
        weighty = 0;
        // 容器大于组件size时将组件置于容器中央
        anchor = GridBagConstraints.CENTER;
        // 窗口拉大时会填满水平与垂直空间
        fill = GridBagConstraints.BOTH;
        // 组件间间距
        inset = new Insets(5, 5, 0, 5);
        // 组件内水平宽度
        ipadx = 0;
        // 组件内垂直高度
        ipady = 0;
        gridbagConst =
            new GridBagConstraints(gridx, gridy, gridwidth, gridheight,
                    weightx, weighty, anchor, fill, inset, ipadx, ipady);
        gridbag.setConstraints(label, gridbagConst);
        dialogPane.add(label);
        
        gridx = 1;
        gridy = 0;
        gridwidth = 1;
        gridheight = 1;
        weightx = 1;
        weighty = 0;
        gridbagConst =
            new GridBagConstraints(gridx, gridy, gridwidth, gridheight,
                    weightx, weighty, anchor, fill, inset, ipadx, ipady);
        gridbag.setConstraints(nameText, gridbagConst);
        dialogPane.add(nameText);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        // 确定
        JButton confirmButton =
            new JButton(new String(
                    new byte[] { -25, -95, -82, -27, -82, -102 }, "UTF-8"));
        panel.add(confirmButton);
        // 取消
        JButton cancelButton =
            new JButton(new String(
                    new byte[] { -27, -113, -106, -26, -74, -120 }, "UTF-8"));
        panel.add(cancelButton);
        
        gridx = 0;
        gridy = 1;
        gridwidth = 2;
        weightx = 1;
        weighty = 1;
        gridbagConst =
            new GridBagConstraints(gridx, gridy, gridwidth, gridheight,
                    weightx, weighty, anchor, GridBagConstraints.NONE, inset,
                    ipadx, ipady);
        gridbag.setConstraints(panel, gridbagConst);
        dialogPane.add(panel);
        
        confirmButton.addActionListener(new ActionListener()
        {
            
            public void actionPerformed(ActionEvent e)
            {
                String nameTextValue = nameText.getText();
                if (null != nameTextValue && !"".equals(nameTextValue.trim()))
                {
                    if (null != frame.getCanvas() && !isNew())
                    {
                        frame.getCanvas().setCanvasName(nameTextValue.trim());
                        frame.getCanvas().setSaved(false);
                    }
                    else
                    {
                        PureHtmlFormSingleCanvas canvas =
                            new PureHtmlFormSingleCanvas(frame);
                        canvas.setCanvasName(nameTextValue.trim());
                        frame.setCanvas(canvas);
                        canvas.setSaved(false);
                    }
                    Component c = (Component) e.getSource();
                    while (null != c && !(c instanceof JDialog))
                    {
                        c = c.getParent();
                    }
                    ((JDialog) c).dispose();
                }
            }
        });
        
        cancelButton.addActionListener(new ActionListener()
        {
            
            public void actionPerformed(ActionEvent e)
            {
                Component c = (Component) e.getSource();
                while (null != c && !(c instanceof JDialog))
                {
                    c = c.getParent();
                }
                ((JDialog) c).dispose();
            }
        });
        
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        int screenW = (int) d.getWidth();
        int screenH = (int) d.getHeight();
        setBounds((screenW - 300) / 2, (screenH - 200) / 2, 300, 200);
    }
    
    public boolean isNew()
    {
        return isNew;
    }
    
}
