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
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import phform.application.frame.PureHtmlFormSingleFrame;
import phform.application.html.bean.TableBean;
import phform.application.html.util.FormUtil;

public class PureHtmlFormSingleTableDialog extends JDialog
{
    private PureHtmlFormSingleFrame frame;
    
    private JTextField rowText = new JTextField("1");
    
    private JTextField colText = new JTextField("1");
    
    // 自动扩展整个页面
    private JCheckBox wholePageCheck = null;
    
    {
        try
        {
            this.wholePageCheck =
                new JCheckBox(new String(new byte[] { -24, -121, -86, -27,
                        -118, -88, -26, -119, -87, -27, -79, -107, -26, -107,
                        -76, -28, -72, -86, -23, -95, -75, -23, -99, -94 },
                        "UTF-8"));
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
    }
    
    public PureHtmlFormSingleTableDialog(PureHtmlFormSingleFrame f)
            throws HeadlessException, UnsupportedEncodingException
    {
        // 增加表格
        super(f, new String(new byte[] { -27, -94, -98, -27, -118, -96, -24,
                -95, -88, -26, -96, -68 }, "UTF-8"), true);
        frame = f;
        GridBagConstraints gridbagConst;
        int gridx, gridy, gridwidth, gridheight, anchor, fill, ipadx, ipady;
        double weightx, weighty;
        Insets inset;
        
        GridBagLayout gridbag = new GridBagLayout();
        
        Container dialogPane = getContentPane();
        dialogPane.setLayout(gridbag);
        
        // 行 :
        JLabel label =
            new JLabel(new String(new byte[] { -24, -95, -116, 32, 58, 32 },
                    "UTF-8"));
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
        
        // 列 :
        label =
            new JLabel(new String(new byte[] { -27, -120, -105, 32, 58, 32 },
                    "UTF-8"));
        gridx = 0;
        gridy = 1;
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
        gridbag.setConstraints(rowText, gridbagConst);
        dialogPane.add(rowText);
        
        gridy = 1;
        gridbagConst =
            new GridBagConstraints(gridx, gridy, gridwidth, gridheight,
                    weightx, weighty, anchor, fill, inset, ipadx, ipady);
        gridbag.setConstraints(colText, gridbagConst);
        dialogPane.add(colText);
        
        gridx = 1;
        gridy = 2;
        gridwidth = 1;
        gridbagConst =
            new GridBagConstraints(gridx, gridy, gridwidth, gridheight,
                    weightx, weighty, anchor, fill, inset, ipadx, ipady);
        gridbag.setConstraints(wholePageCheck, gridbagConst);
        dialogPane.add(wholePageCheck);
        
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
        gridy = 3;
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
                String rowTextValue = rowText.getText();
                String colTextValue = colText.getText();
                try
                {
                    int rows = Integer.parseInt(rowTextValue.trim());
                    int columns = Integer.parseInt(colTextValue.trim());
                    if (FormUtil.diagnoseInitTable(columns, rows))
                    {
                        TableBean table =
                            new TableBean(columns, rows, wholePageCheck
                                    .isSelected());
                        
                        frame.getCanvas().addTable(table);
                        frame.getCanvas().setSaved(false);
                        Component c = (Component) e.getSource();
                        while (null != c && !(c instanceof JDialog))
                        {
                            c = c.getParent();
                        }
                        
                        ((JDialog) c).dispose();
                    }
                }
                catch (Exception e1)
                {
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
    
}
