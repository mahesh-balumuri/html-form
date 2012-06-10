package phform.application.html.util;

import java.io.File;

import javax.swing.ImageIcon;

public class ImageIconUtil
{
    public static ImageIcon getImageIcon(String imageIconName)
    {
        return new ImageIcon(ImageIconUtil.class.getResource("/").getPath()
                + "/icons/" + imageIconName);
    }
    public static File getLic()
    {
        return new File(ImageIconUtil.class.getResource("/").getPath()
                + "/lic.txt");
    }
}
