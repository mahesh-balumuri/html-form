package phform.application.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.text.html.parser.ParserDelegator;

import phform.application.canvas.PureHtmlFormSingleCanvas;
import phform.application.dialog.PureHtmlFormProperDialog;
import phform.application.frame.PureHtmlFormSingleFrame;
import phform.application.html.util.FormUtil;

public class PureHtmlFormSingleFileMenu extends JMenu
{
    private PureHtmlFormSingleFrame frame;
    
    private JFileChooser chooser;
    
    private JFileChooser importChooser;
    
    private JMenuItem newMenuItem;
    
    private JMenuItem saveMenuItem;
    
    private JMenuItem loadMenuItem;
    
    private JMenuItem properMenuItem;
    
    private JMenuItem closeMenuItem;
    
    private JMenuItem exitMenuItem;
    
    public PureHtmlFormSingleFileMenu(PureHtmlFormSingleFrame c)
    {
        super("文件");
        frame = c;
        chooser = new JFileChooser();
        importChooser = new JFileChooser();
        PureHtmlFormFileFilter fileFilter = new PureHtmlFormFileFilter("html");
        PureHtmlFormFileFilter importFilter = new PureHtmlFormFileFilter("xls");
        chooser.setFileFilter(fileFilter);
        importChooser.setFileFilter(importFilter);
        
        newMenuItem = new JMenuItem("新建...");
        newMenuItem.addActionListener(new ActionListener()
        {
            
            public void actionPerformed(ActionEvent e)
            {
                boolean properShow = true;
                if (null != frame && null != frame.getCanvas()
                        && !frame.getCanvas().isSaved())
                {
                    int confirm =
                        showConfirmDialog(frame.getCanvas().getCanvasName()
                                + "尚未保存,是否保存?");
                    switch (confirm)
                    {
                    case JOptionPane.OK_OPTION:
                        if (!"".equals(frame.getCanvas().getFilePath()))
                        {
                            saveHtml(new File(frame.getCanvas().getFilePath()));
                            frame.setCanvas(null);
                        }
                        else
                        {
                            int returnVal = chooser.showSaveDialog(frame);
                            if (returnVal == JFileChooser.APPROVE_OPTION)
                            {
                                File choosedFile = chooser.getSelectedFile();
                                saveHtml(choosedFile);
                                frame.setCanvas(null);
                            }
                            else
                            {
                                properShow = false;
                            }
                        }
                        break;
                    case JOptionPane.NO_OPTION:
                        frame.setCanvas(null);
                        break;
                    case JOptionPane.CANCEL_OPTION:
                        properShow = false;
                        break;
                    default:
                    }
                }
                else if (null != frame && null != frame.getCanvas())
                {
                    frame.setCanvas(null);
                }
                
                if (properShow)
                {
                    FormUtil.resetMaxId();
                    PureHtmlFormProperDialog dialog =
                        new PureHtmlFormProperDialog(frame, true);
                    dialog.show();
                }
            }
        });
        add(newMenuItem);
        
        loadMenuItem = new JMenuItem("打开...");
        loadMenuItem.addActionListener(new ActionListener()
        {
            
            public void actionPerformed(ActionEvent e)
            {
                boolean open = true;
                if (null != frame && null != frame.getCanvas()
                        && !frame.getCanvas().isSaved())
                {
                    int confirm =
                        showConfirmDialog(frame.getCanvas().getCanvasName()
                                + "尚未保存,是否保存?");
                    switch (confirm)
                    {
                    case JOptionPane.OK_OPTION:
                        if (!"".equals(frame.getCanvas().getFilePath()))
                        {
                            saveHtml(new File(frame.getCanvas().getFilePath()));
                            frame.setCanvas(null);
                        }
                        else
                        {
                            int returnVal = chooser.showSaveDialog(frame);
                            if (returnVal == JFileChooser.APPROVE_OPTION)
                            {
                                File choosedFile = chooser.getSelectedFile();
                                saveHtml(choosedFile);
                                frame.setCanvas(null);
                            }
                            else
                            {
                                open = false;
                            }
                        }
                        break;
                    case JOptionPane.NO_OPTION:
                        frame.setCanvas(null);
                        break;
                    case JOptionPane.CANCEL_OPTION:
                        open = false;
                        break;
                    default:
                    }
                }
                else if (null != frame && null != frame.getCanvas())
                {
                    frame.setCanvas(null);
                }
                if (open)
                {
                    FormUtil.resetMaxId();
                    int returnVal = chooser.showOpenDialog(frame);
                    if (returnVal == JFileChooser.APPROVE_OPTION)
                    {
                        File choosedFile = chooser.getSelectedFile();
                        if (choosedFile.exists() && !choosedFile.isDirectory())
                        {
                            char[] c = openHtml(choosedFile);
                            frame
                                    .setCanvas(new PureHtmlFormSingleCanvas(
                                            frame));
                            frame.getCanvas().setFilePath(
                                    choosedFile.getAbsolutePath());
                            SingleCallback call =
                                new SingleCallback(frame.getCanvas(),
                                        new String(c));
                            Reader reader = null;
                            try
                            {
                                reader = new StringReader(new String(c));
                                new ParserDelegator().parse(reader, call, true);
                            }
                            catch (IOException e1)
                            {
                                e1.printStackTrace();
                            }
                            finally
                            {
                                if (null != reader)
                                {
                                    try
                                    {
                                        reader.close();
                                    }
                                    catch (IOException e1)
                                    {
                                        e1.printStackTrace();
                                    }
                                }
                            }
                            frame.getCanvas().setSaved(true);
                        }
                    }
                }
            }
        });
        add(loadMenuItem);
        
        saveMenuItem = new JMenuItem("保存");
        saveMenuItem.addActionListener(new ActionListener()
        {
            
            public void actionPerformed(ActionEvent e)
            {
                if (null != frame && null != frame.getCanvas()
                        && !frame.getCanvas().isSaved())
                {
                    if (!"".equals(frame.getCanvas().getFilePath()))
                    {
                        saveHtml(new File(frame.getCanvas().getFilePath()));
                        frame.getCanvas().setSaved(true);
                    }
                    else
                    {
                        int returnVal = chooser.showSaveDialog(frame);
                        if (returnVal == JFileChooser.APPROVE_OPTION)
                        {
                            File choosedFile = chooser.getSelectedFile();
                            saveHtml(choosedFile);
                            frame.getCanvas().setFilePath(
                                    choosedFile.getAbsolutePath());
                            frame.getCanvas().setSaved(true);
                        }
                    }
                }
            }
        });
        add(saveMenuItem);
        
        properMenuItem = new JMenuItem("属性...");
        properMenuItem.addActionListener(new ActionListener()
        {
            
            public void actionPerformed(ActionEvent e)
            {
                if (null != frame && null != frame.getCanvas())
                {
                    PureHtmlFormProperDialog dialog =
                        new PureHtmlFormProperDialog(frame, false);
                    dialog.show();
                }
            }
        });
        add(properMenuItem);
        
        closeMenuItem = new JMenuItem("关闭");
        closeMenuItem.addActionListener(new ActionListener()
        {
            
            public void actionPerformed(ActionEvent e)
            {
                if (null != frame && null != frame.getCanvas()
                        && !frame.getCanvas().isSaved())
                {
                    int confirm =
                        showConfirmDialog(frame.getCanvas().getCanvasName()
                                + "尚未保存,是否保存?");
                    switch (confirm)
                    {
                    case JOptionPane.OK_OPTION:
                        if (!"".equals(frame.getCanvas().getFilePath()))
                        {
                            saveHtml(new File(frame.getCanvas().getFilePath()));
                            frame.setCanvas(null);
                        }
                        else
                        {
                            int returnVal = chooser.showSaveDialog(frame);
                            if (returnVal == JFileChooser.APPROVE_OPTION)
                            {
                                File choosedFile = chooser.getSelectedFile();
                                saveHtml(choosedFile);
                                frame.setCanvas(null);
                            }
                        }
                        break;
                    case JOptionPane.NO_OPTION:
                        frame.setCanvas(null);
                        break;
                    case JOptionPane.CANCEL_OPTION:
                        break;
                    default:
                    }
                }
                else if (null != frame && null != frame.getCanvas())
                {
                    frame.setCanvas(null);
                }
            }
        });
        add(closeMenuItem);
        
        exitMenuItem = new JMenuItem("退出");
        exitMenuItem.addActionListener(new ActionListener()
        {
            
            public void actionPerformed(ActionEvent e)
            {
                boolean exitFlag = true;
                if (null != frame && null != frame.getCanvas()
                        && !frame.getCanvas().isSaved())
                {
                    int confirm =
                        showConfirmDialog(frame.getCanvas().getCanvasName()
                                + "尚未保存,是否保存?");
                    switch (confirm)
                    {
                    case JOptionPane.OK_OPTION:
                        if (!"".equals(frame.getCanvas().getFilePath()))
                        {
                            saveHtml(new File(frame.getCanvas().getFilePath()));
                            frame.setCanvas(null);
                        }
                        else
                        {
                            int returnVal = chooser.showSaveDialog(frame);
                            if (returnVal == JFileChooser.APPROVE_OPTION)
                            {
                                File choosedFile = chooser.getSelectedFile();
                                saveHtml(choosedFile);
                                frame.setCanvas(null);
                            }
                            else
                            {
                                exitFlag = false;
                            }
                        }
                        break;
                    case JOptionPane.NO_OPTION:
                        frame.setCanvas(null);
                        break;
                    case JOptionPane.CANCEL_OPTION:
                        exitFlag = false;
                        break;
                    default:
                    }
                }
                else if (null != frame && null != frame.getCanvas())
                {
                    frame.setCanvas(null);
                }
                
                if (exitFlag)
                {
                    frame.dispose();
                    System.exit(0);
                }
            }
        });
        add(exitMenuItem);
        setSaveMenuItemEnable(false);
        setProperMenuItemEnable(false);
        setCloseMenuItemEnable(false);
    }
    
    private int showConfirmDialog(String confirmStr)
    {
        return JOptionPane.showConfirmDialog(frame, confirmStr);
    }
    
    private void saveHtml(File path)
    {
        String str = frame.getCanvas().tagHtml();
        Writer writer = null;
        try
        {
            if (!path.getAbsolutePath().endsWith(".html"))
            {
                path = new File(path.getAbsoluteFile() + ".html");
            }
            writer =
                new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                        path), Charset.forName("UTF-8")));
            writer.write(str);
            writer.flush();
        }
        catch (UnsupportedEncodingException e1)
        {
            e1.printStackTrace();
        }
        catch (FileNotFoundException e2)
        {
            e2.printStackTrace();
        }
        catch (IOException e3)
        {
            e3.printStackTrace();
        }
        finally
        {
            if (null != writer)
            {
                try
                {
                    writer.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
    }
    
    private char[] openHtml(File path)
    {
        Reader reader = null;
        char[] c = new char[4096];
        List ret = new ArrayList();
        List retSum = new ArrayList();
        int sum = 0;
        try
        {
            reader =
                new BufferedReader(new InputStreamReader(new FileInputStream(
                        path), Charset.forName("UTF-8")));
            int k = -1;
            while (-1 != (k = reader.read(c)))
            {
                // for (int i = 0; i < c.length; i++)
                // {
                // if(c[i] != 0)
                // {
                // ret.add(c[i]);
                // }
                // else
                // {
                // break;
                // }
                // }
                ret.add(c);
                retSum.add(k);
                sum += k;
                c = new char[4096];
            }
        }
        catch (FileNotFoundException e1)
        {
            e1.printStackTrace();
        }
        catch (IOException e2)
        {
            e2.printStackTrace();
        }
        finally
        {
            if (null != reader)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
        c = new char[sum];
        int k = 0;
        for (int i = 0; i < ret.size(); i++)
        {
            char[] tmp = (char[]) ret.get(i);
            int tmpLength = Integer.parseInt(retSum.get(i).toString());
            for (int j = 0; j < tmpLength; j++)
            {
                c[k++] = tmp[j];
            }
        }
        
        ret.clear();
        retSum.clear();
        return c;
    }
    
    public void setCloseMenuItemEnable(boolean bool)
    {
        this.closeMenuItem.setEnabled(bool);
    }
    
    public void setExitMenuItemEnable(boolean bool)
    {
        this.exitMenuItem.setEnabled(bool);
    }
    
    public void setLoadMenuItemEnable(boolean bool)
    {
        this.loadMenuItem.setEnabled(bool);
    }
    
    public void setNewMenuItemEnable(boolean bool)
    {
        this.newMenuItem.setEnabled(bool);
    }
    
    public void setProperMenuItemEnable(boolean bool)
    {
        this.properMenuItem.setEnabled(bool);
    }
    
    public void setSaveMenuItemEnable(boolean bool)
    {
        this.saveMenuItem.setEnabled(bool);
    }
    
    public JMenuItem getSaveMenuItem()
    {
        return saveMenuItem;
    }
    
}
