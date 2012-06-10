package phform.application.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import phform.application.canvas.PureHtmlFormSingleCanvas;
import phform.application.menubar.PureHtmlSingleMenuBar;
import phform.application.toolbar.PureHtmlFormSingleToolBar;

public class PureHtmlFormSingleFrame extends JFrame
{
    private PureHtmlSingleMenuBar phMenuBar;
    
    private PureHtmlFormSingleToolBar phToolBar;
    
    private Container phPane;
    
    private JRootPane phRootPane;
    
    private PureHtmlFormSingleCanvas canvas;
    
    private JTabbedPane right;
    
    public PureHtmlFormSingleFrame()
    {
        phRootPane = getRootPane();
        phMenuBar = new PureHtmlSingleMenuBar(this);
        phRootPane.setJMenuBar(phMenuBar);
        
        phPane = getContentPane();
        phToolBar = new PureHtmlFormSingleToolBar(this);
        phPane.add(phToolBar, BorderLayout.NORTH);
        phPane.setBackground(Color.GRAY);
        right = new JTabbedPane();
        right.setTabPlacement(JTabbedPane.TOP);
        right.setBackground(Color.GRAY);
        
        phPane.add(right, BorderLayout.CENTER);
    }
    
    public PureHtmlSingleMenuBar getPhMenuBar()
    {
        return phMenuBar;
    }
    
    public Container getPhPane()
    {
        return phPane;
    }
    
    public JRootPane getPhRootPane()
    {
        return phRootPane;
    }
    
    public PureHtmlFormSingleToolBar getPhToolBar()
    {
        return phToolBar;
    }
    
    public PureHtmlFormSingleCanvas getCanvas()
    {
        return canvas;
    }
    
    public void setCanvas(PureHtmlFormSingleCanvas canvas)
    {
        this.canvas = canvas;
        if (null != canvas)
        {
            right.removeAll();
            JScrollPane jsp = new JScrollPane(canvas);
            jsp.getVerticalScrollBar().setUnitIncrement(50);
            right.add(jsp, canvas.getCanvasName());
            
            phMenuBar.getFileMenu().setSaveMenuItemEnable(true);
            phMenuBar.getFileMenu().setProperMenuItemEnable(true);
            phMenuBar.getFileMenu().setCloseMenuItemEnable(true);
            
            phToolBar.enableInsertCheckBoxBut(true);
            phToolBar.enableInsertStringBut(true);
            phToolBar.enableInsertTableBut(true);
            phToolBar.enableInsertTextAreaBut(true);
            phToolBar.enableInsertTextFieldBut(true);
            phToolBar.enableAutoInputBut(true);
            phToolBar.enableCenterJustifyBut(true);
            phToolBar.enableLeftJustifyBut(true);
            phToolBar.enableRightJustifyBut(true);
            phToolBar.enableTextJustifyBut(true);
            phToolBar.enableUndoBut(true);
            phToolBar.enableRedoBut(true);
            phToolBar.enableMiddleJustifyBut(true);
            phToolBar.enableTopJustifyBut(true);
            phToolBar.enableBottomJustifyBut(true);
        }
        else
        {
            right.removeAll();
            
            phMenuBar.getFileMenu().setSaveMenuItemEnable(false);
            phMenuBar.getFileMenu().setProperMenuItemEnable(false);
            phMenuBar.getFileMenu().setCloseMenuItemEnable(false);
            
            phToolBar.enableInsertCheckBoxBut(false);
            phToolBar.enableInsertStringBut(false);
            phToolBar.enableInsertTableBut(false);
            phToolBar.enableInsertTextAreaBut(false);
            phToolBar.enableInsertTextFieldBut(false);
            phToolBar.enableAutoInputBut(false);
            phToolBar.enableCenterJustifyBut(false);
            phToolBar.enableLeftJustifyBut(false);
            phToolBar.enableRightJustifyBut(false);
            phToolBar.enableTextJustifyBut(false);
            phToolBar.enableUndoBut(false);
            phToolBar.enableRedoBut(false);
            phToolBar.enableMiddleJustifyBut(false);
            phToolBar.enableTopJustifyBut(false);
            phToolBar.enableBottomJustifyBut(false);
        }
    }
    
    public JTabbedPane getRight()
    {
        return right;
    }
    
}
