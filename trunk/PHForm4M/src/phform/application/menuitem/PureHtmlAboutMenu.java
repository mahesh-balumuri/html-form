package phform.application.menuitem;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.io.UnsupportedEncodingException;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

public class PureHtmlAboutMenu extends AbstractAction
{
    private Component parentComponent;
    
    public PureHtmlAboutMenu(Component c) throws UnsupportedEncodingException
    {
        // 关于...
        super(new String(new byte[] { -27, -123, -77, -28, -70, -114, 46, 46,
                46 }, "UTF-8"));
        parentComponent = c;
    }
    
    public void actionPerformed(ActionEvent event)
    {
        JOptionPane.showMessageDialog(parentComponent,
                "JingJing Pure HTML Form", "About",
                JOptionPane.INFORMATION_MESSAGE);
    }
    
}
