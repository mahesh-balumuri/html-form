package phform.application.menu;

import java.awt.Component;
import java.io.UnsupportedEncodingException;

import javax.swing.JMenu;

import phform.application.menuitem.PureHtmlAboutMenu;

public class PureHtmlFormHelpMenu extends JMenu
{
    private Component parentComponent;
    
    public PureHtmlFormHelpMenu(Component c)
            throws UnsupportedEncodingException
    {
        // 帮助
        super(new String(new byte[] { -27, -72, -82, -27, -118, -87 }, "UTF-8"));
        parentComponent = c;
        add(new PureHtmlAboutMenu(parentComponent));
    }
}
