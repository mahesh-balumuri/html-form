package phform.application.menubar;

import java.io.UnsupportedEncodingException;

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
        try
        {
            fileMenu = new PureHtmlFormSingleFileMenu(frame);
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        add(fileMenu);
        try
        {
            add(new PureHtmlFormHelpMenu(frame));
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
    }
    
    public PureHtmlFormSingleFileMenu getFileMenu()
    {
        return fileMenu;
    }
}
