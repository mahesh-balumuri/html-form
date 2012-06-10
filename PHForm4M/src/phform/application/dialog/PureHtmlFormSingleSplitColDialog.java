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
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import phform.application.canvas.PureHtmlFormSingleCanvas;
import phform.application.frame.PureHtmlFormSingleFrame;
import phform.application.html.bean.TdBean;

public class PureHtmlFormSingleSplitColDialog extends JDialog
{
    private PureHtmlFormSingleFrame frame;
    
    private JTextField colText = new JTextField("1");
    
    public PureHtmlFormSingleSplitColDialog(PureHtmlFormSingleFrame f)
            throws HeadlessException
    {
        super(f, "拆分列", true);
        frame = f;
        GridBagConstraints gridbagConst;
        int gridx, gridy, gridwidth, gridheight, anchor, fill, ipadx, ipady;
        double weightx, weighty;
        Insets inset;
        
        GridBagLayout gridbag = new GridBagLayout();
        
        Container dialogPane = getContentPane();
        dialogPane.setLayout(gridbag);
        
        JLabel label = new JLabel("列 : ");
        gridx = 0; // 第0列
        gridy = 0; // 第0行
        gridwidth = 1; // 占一单位宽度
        gridheight = 1; // 占一单位高度
        weightx = 0; // 窗口增大时组件宽度增大比率0
        weighty = 0; // 窗口增大时组件高度增大比率0
        anchor = GridBagConstraints.CENTER; // 容器大于组件size时将组件置于容器中央
        fill = GridBagConstraints.BOTH; // 窗口拉大时会填满水平与垂直空间
        inset = new Insets(5, 5, 0, 5); // 组件间间距
        ipadx = 0; // 组件内水平宽度
        ipady = 0; // 组件内垂直高度
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
        gridbag.setConstraints(colText, gridbagConst);
        dialogPane.add(colText);
        
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 2));
        JButton confirmButton = new JButton("确定");
        panel.add(confirmButton);
        JButton cancelButton = new JButton("取消");
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
                String colTextValue = colText.getText();
                try
                {
                    int splitCols = Integer.parseInt(colTextValue.trim());
                    PureHtmlFormSingleCanvas canvas = frame.getCanvas();
                    List beanSelected = canvas.getBeanSelected();
                    if (null != beanSelected && !beanSelected.isEmpty())
                    {
                        if (beanSelected.size() == 1)
                        {
                            Object selected = beanSelected.get(0);
                            if (selected instanceof TdBean
                                    && ((TdBean) selected).getTableBean()
                                            .diagnoseSplitCol(
                                                    (TdBean) selected,
                                                    splitCols))
                            {
                                ((TdBean) selected).getTableBean().splitCol(
                                        (TdBean) selected, splitCols);
                                canvas.setSaved(false);
                                canvas.getBeanSelected().clear();
                                canvas.repaint();
                                
                                Component c = (Component) e.getSource();
                                while (null != c && !(c instanceof JDialog))
                                {
                                    c = c.getParent();
                                }
                                ((JDialog) c).dispose();
                            }
                        }
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
