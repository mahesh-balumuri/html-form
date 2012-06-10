package phform.application.menu;

import java.awt.Component;

import javax.swing.JMenu;

import phform.application.menuitem.PureHtmlAboutMenu;

public class PureHtmlFormHelpMenu extends JMenu
{
    private Component parentComponent;
    
    public PureHtmlFormHelpMenu(Component c)
    {
        super("帮助");
        parentComponent = c;
        add(new PureHtmlAboutMenu(parentComponent));
    }
}
