package phform.application.menu;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class PureHtmlFormFileFilter extends FileFilter
{
    
    private String filePostFix;
    
    public PureHtmlFormFileFilter(String filePostFix)
    {
        this.filePostFix = filePostFix;
    }
    
    public boolean accept(File file)
    {
        if (null != file)
        {
            if (file.isDirectory())
            {
                return true;
            }
            String extension = getExtension(file);
            if (extension != null && extension.trim().equals(getFilePostFix()))
            {
                return true;
            }
        }
        return false;
    }
    
    public String getExtension(File f)
    {
        if (f != null)
        {
            String filename = f.getName();
            int i = filename.lastIndexOf('.');
            if (i > 0 && i < filename.length() - 1)
            {
                return filename.substring(i + 1).toLowerCase();
            }
        }
        return null;
    }
    
    public String getDescription()
    {
        return "." + getFilePostFix();
    }
    
    public String getFilePostFix()
    {
        return filePostFix == null ? "" : filePostFix.trim().toLowerCase();
    }
    
}
