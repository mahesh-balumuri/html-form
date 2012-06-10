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

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import phform.application.frame.PureHtmlFormSingleFrame;
import phform.application.html.FormObject;

public class PureHtmlFormEleProperDialog extends JDialog
{
    private PureHtmlFormSingleFrame frame;
    
    private JTextField idText = new JTextField();
    
    private JTextField nameText = new JTextField();
    
    public PureHtmlFormEleProperDialog(PureHtmlFormSingleFrame f)
            throws HeadlessException
    {
        super(f, "元素属性", true);
        this.frame = f;
        if (null != frame.getCanvas()
                && null != frame.getCanvas().getBeanSelected()
                && frame.getCanvas().getBeanSelected().size() == 1)
        {
            FormObject obj =
                (FormObject) frame.getCanvas().getBeanSelected().get(0);
            idText.setText(obj.getId());
            nameText.setText(obj.getName());
        }
        GridBagConstraints gridbagConst;
        int gridx, gridy, gridwidth, gridheight, anchor, fill, ipadx, ipady;
        double weightx, weighty;
        Insets inset;
        
        GridBagLayout gridbag = new GridBagLayout();
        
        Container dialogPane = getContentPane();
        dialogPane.setLayout(gridbag);
        
        JLabel label = new JLabel("ID : ");
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
        gridbag.setConstraints(idText, gridbagConst);
        dialogPane.add(idText);
        
        JLabel labelName = new JLabel("Name : ");
        gridx = 0;
        gridy = 1;
        gridwidth = 1;
        gridheight = 1;
        weightx = 0;
        weighty = 0;
        gridbagConst =
            new GridBagConstraints(gridx, gridy, gridwidth, gridheight,
                    weightx, weighty, anchor, fill, inset, ipadx, ipady);
        gridbag.setConstraints(labelName, gridbagConst);
        dialogPane.add(labelName);
        
        gridx = 1;
        gridy = 1;
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
        JButton confirmButton = new JButton("确定");
        panel.add(confirmButton);
        JButton cancelButton = new JButton("取消");
        panel.add(cancelButton);
        
        gridx = 0;
        gridy = 2;
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
                String idTextValue = idText.getText();
                if (null != nameTextValue && !"".equals(nameTextValue.trim())
                        && null != idTextValue
                        && !"".equals(idTextValue.trim()))
                {
                    if (null != frame.getCanvas()
                            && null != frame.getCanvas().getBeanSelected()
                            && frame.getCanvas().getBeanSelected().size() == 1)
                    {
                        FormObject obj =
                            (FormObject) frame.getCanvas().getBeanSelected()
                                    .get(0);
                        obj.setId(idTextValue.trim());
                        obj.setName(nameTextValue.trim());
                        frame.getCanvas().setSaved(false);
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
    
}
