package phform.application.menuitem;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JOptionPane;

public class PureHtmlAboutMenu extends AbstractAction
{
    private Component parentComponent;
    
    public PureHtmlAboutMenu(Component c)
    {
        super("关于...");
        parentComponent = c;
    }
    
    public void actionPerformed(ActionEvent event)
    {
        JOptionPane.showMessageDialog(parentComponent,
                "JingJing Pure HTML Form", "About",
                JOptionPane.INFORMATION_MESSAGE);
    }
    
}
