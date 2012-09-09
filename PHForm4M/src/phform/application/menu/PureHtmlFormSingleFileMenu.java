package phform.application.menu;

import java.awt.HeadlessException;
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
            throws UnsupportedEncodingException
    {
        // 文件
        super(
                new String(new byte[] { -26, -106, -121, -28, -69, -74 },
                        "UTF-8"));
        frame = c;
        chooser = new JFileChooser();
        importChooser = new JFileChooser();
        PureHtmlFormFileFilter fileFilter = new PureHtmlFormFileFilter("html");
        PureHtmlFormFileFilter importFilter = new PureHtmlFormFileFilter("xls");
        chooser.setFileFilter(fileFilter);
        importChooser.setFileFilter(importFilter);
        
        // 新建...
        newMenuItem =
            new JMenuItem(new String(new byte[] { -26, -106, -80, -27, -69,
                    -70, 46, 46, 46 }, "UTF-8"));
        newMenuItem.addActionListener(new ActionListener()
        {
            
            public void actionPerformed(ActionEvent e)
            {
                boolean properShow = true;
                if (null != frame && null != frame.getCanvas()
                        && !frame.getCanvas().isSaved())
                {
                    // 尚未保存,是否保存?
                    int confirm = -99;
                    try
                    {
                        confirm =
                            showConfirmDialog(frame.getCanvas().getCanvasName()
                                    + new String(new byte[] { -27, -80, -102,
                                            -26, -100, -86, -28, -65, -99, -27,
                                            -83, -104, 44, -26, -104, -81, -27,
                                            -112, -90, -28, -65, -99, -27, -83,
                                            -104, 63 }, "UTF-8"));
                    }
                    catch (UnsupportedEncodingException e1)
                    {
                        e1.printStackTrace();
                    }
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
                    PureHtmlFormProperDialog dialog;
                    try
                    {
                        dialog = new PureHtmlFormProperDialog(frame, true);
                        dialog.show();
                    }
                    catch (HeadlessException e1)
                    {
                        e1.printStackTrace();
                    }
                    catch (UnsupportedEncodingException e1)
                    {
                        e1.printStackTrace();
                    }
                }
            }
        });
        add(newMenuItem);
        
        // 打开...
        loadMenuItem =
            new JMenuItem(new String(new byte[] { -26, -119, -109, -27, -68,
                    -128, 46, 46, 46 }, "UTF-8"));
        loadMenuItem.addActionListener(new ActionListener()
        {
            
            public void actionPerformed(ActionEvent e)
            {
                boolean open = true;
                if (null != frame && null != frame.getCanvas()
                        && !frame.getCanvas().isSaved())
                {
                    // 尚未保存,是否保存?
                    int confirm = -99;
                    try
                    {
                        confirm =
                            showConfirmDialog(frame.getCanvas().getCanvasName()
                                    + new String(new byte[] { -27, -80, -102,
                                            -26, -100, -86, -28, -65, -99, -27,
                                            -83, -104, 44, -26, -104, -81, -27,
                                            -112, -90, -28, -65, -99, -27, -83,
                                            -104, 63 }, "UTF-8"));
                    }
                    catch (UnsupportedEncodingException e1)
                    {
                        e1.printStackTrace();
                    }
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
        
        // 保存
        saveMenuItem =
            new JMenuItem(new String(
                    new byte[] { -28, -65, -99, -27, -83, -104 }, "UTF-8"));
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
        
        // 属性...
        properMenuItem =
            new JMenuItem(new String(new byte[] { -27, -79, -98, -26, -128,
                    -89, 46, 46, 46 }, "UTF-8"));
        properMenuItem.addActionListener(new ActionListener()
        {
            
            public void actionPerformed(ActionEvent e)
            {
                if (null != frame && null != frame.getCanvas())
                {
                    PureHtmlFormProperDialog dialog;
                    try
                    {
                        dialog = new PureHtmlFormProperDialog(frame, false);
                        dialog.show();
                    }
                    catch (HeadlessException e1)
                    {
                        e1.printStackTrace();
                    }
                    catch (UnsupportedEncodingException e1)
                    {
                        e1.printStackTrace();
                    }
                }
            }
        });
        add(properMenuItem);
        
        // 关闭
        closeMenuItem =
            new JMenuItem(new String(new byte[] { -27, -123, -77, -23, -105,
                    -83 }, "UTF-8"));
        closeMenuItem.addActionListener(new ActionListener()
        {
            
            public void actionPerformed(ActionEvent e)
            {
                if (null != frame && null != frame.getCanvas()
                        && !frame.getCanvas().isSaved())
                {
                    // 尚未保存,是否保存?
                    int confirm = -99;
                    try
                    {
                        confirm =
                            showConfirmDialog(frame.getCanvas().getCanvasName()
                                    + new String(new byte[] { -27, -80, -102,
                                            -26, -100, -86, -28, -65, -99, -27,
                                            -83, -104, 44, -26, -104, -81, -27,
                                            -112, -90, -28, -65, -99, -27, -83,
                                            -104, 63 }, "UTF-8"));
                    }
                    catch (UnsupportedEncodingException e1)
                    {
                        e1.printStackTrace();
                    }
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
        
        // 退出
        exitMenuItem =
            new JMenuItem(new String(new byte[] { -23, -128, -128, -27, -121,
                    -70 }, "UTF-8"));
        exitMenuItem.addActionListener(new ActionListener()
        {
            
            public void actionPerformed(ActionEvent e)
            {
                boolean exitFlag = true;
                if (null != frame && null != frame.getCanvas()
                        && !frame.getCanvas().isSaved())
                {
                    // 尚未保存,是否保存?
                    int confirm = -99;
                    try
                    {
                        confirm =
                            showConfirmDialog(frame.getCanvas().getCanvasName()
                                    + new String(new byte[] { -27, -80, -102,
                                            -26, -100, -86, -28, -65, -99, -27,
                                            -83, -104, 44, -26, -104, -81, -27,
                                            -112, -90, -28, -65, -99, -27, -83,
                                            -104, 63 }, "UTF-8"));
                    }
                    catch (UnsupportedEncodingException e1)
                    {
                        e1.printStackTrace();
                    }
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
