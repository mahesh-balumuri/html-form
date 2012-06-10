package phform.application.menubar;

import javax.swing.JMenuBar;

import phform.application.frame.PureHtmlFormSingleFrame;
import phform.application.menu.PureHtmlFormHelpMenu;
import phform.application.menu.PureHtmlFormSingleFileMenu;

public class PureHtmlSingleMenuBar extends JMenuBar
{
    private PureHtmlFormSingleFrame frame;
    
    private PureHtmlFormSingleFileMenu fileMenu;
    
    public PureHtmlSingleMenuBar(PureHtmlFormSingleFrame c)
    {
        super();
        frame = c;
        fileMenu = new PureHtmlFormSingleFileMenu(frame);
        add(fileMenu);
        add(new PureHtmlFormHelpMenu(frame));
    }
    
    public PureHtmlFormSingleFileMenu getFileMenu()
    {
        return fileMenu;
    }
}
