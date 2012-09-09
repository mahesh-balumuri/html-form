package phform.application.main;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import phform.application.frame.PureHtmlFormSingleFrame;
import phform.application.html.util.ImageIconUtil;
import phform.application.menu.PureHtmlFormFileFilter;

public class PureFormApp
{
    
    private static void launch(JFrame f, String title, int width, int height,
            int left, int top)
    {
        f.setTitle(title);
        f.setBounds(left, top, width, height);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.addWindowListener(new WindowAdapter()
        {
            public void windowClosing(WindowEvent e)
            {
                PureHtmlFormSingleFrame frame =
                    (PureHtmlFormSingleFrame) e.getSource();
                boolean exitFlag = true;
                if (null != frame && null != frame.getCanvas()
                        && !frame.getCanvas().isSaved())
                {
                    // 尚未保存,是否保存?
                    int confirm = -99;
                    try
                    {
                        confirm =
                            JOptionPane.showConfirmDialog(frame, frame
                                    .getCanvas().getCanvasName()
                                    + new String(new byte[] { -27, -80, -102,
                                            -26, -100, -86, -28, -65, -99, -27,
                                            -83, -104, 44, -26, -104, -81, -27,
                                            -112, -90, -28, -65, -99, -27, -83,
                                            -104, 63 }, "UTF-8"));
                    }
                    catch (HeadlessException e1)
                    {
                        e1.printStackTrace();
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
                            saveHtml(frame, new File(frame.getCanvas()
                                    .getFilePath()));
                            frame.setCanvas(null);
                        }
                        else
                        {
                            JFileChooser chooser = new JFileChooser();
                            PureHtmlFormFileFilter fileFilter =
                                new PureHtmlFormFileFilter("html");
                            chooser.setFileFilter(fileFilter);
                            int returnVal = chooser.showSaveDialog(frame);
                            if (returnVal == JFileChooser.APPROVE_OPTION)
                            {
                                File choosedFile = chooser.getSelectedFile();
                                saveHtml(frame, choosedFile);
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
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                }
                else
                {
                    frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
                }
            }
            
            private void saveHtml(PureHtmlFormSingleFrame frame, File path)
            {
                if (!path.getAbsolutePath().endsWith(".html"))
                {
                    path = new File(path.getAbsoluteFile() + ".html");
                }
                String str = frame.getCanvas().tagHtml();
                Writer writer = null;
                try
                {
                    writer =
                        new BufferedWriter(new OutputStreamWriter(
                                new FileOutputStream(path), Charset
                                        .forName("UTF-8")));
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
            
            public void windowClosed(WindowEvent e)
            {
                ((JFrame) e.getSource()).dispose();
                System.exit(0);
            }
        });
    }
    
	private static void launch(JFrame f, String title, boolean fullScreen)
	{
		if (fullScreen)
		{
			f.setExtendedState(JFrame.MAXIMIZED_BOTH);
			launch(f, title, 0, 0, 0, 0);
		}
		else
		{
			Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
			int screenW = (int) d.getWidth();
			int screenH = (int) d.getHeight();

			int frameW = screenW / 2;
			int frameH = screenH / 2;

			launch(f, title, frameW, frameH, (screenW - frameW) / 2,
					(screenH - frameH) / 2);
		}
	}
    
    private static String parseLic()
    {
        String licStr = "";
        BufferedReader read = null;
        try
        {
            File lic = ImageIconUtil.getLic();
            read = new BufferedReader(new FileReader(lic));
            char[] c = new char[4096];
            int i;
            while ((i = read.read(c, 0, 4096)) != -1)
            {
                for (int j = 0; j < i; j++)
                {
                    if (c[j] != '\n' && c[j] != '\r')
                    {
                        licStr += c[j];
                    }
                }
            }
        }
        catch (Exception e)
        {
        }
        finally
        {
            if (null != read)
            {
                try
                {
                    read.close();
                }
                catch (IOException e)
                {
                }
            }
        }
        return licStr;
    }
    
    /**
     * @param args
     */
    public static void main(String[] args)
    {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e)
        {
        }
        // launch(new PureHtmlFormFrame(), "Pure HTML Form Tool", true);
        launch(new PureHtmlFormSingleFrame(), "Pure HTML Form Tool", true);
    }
    
}
