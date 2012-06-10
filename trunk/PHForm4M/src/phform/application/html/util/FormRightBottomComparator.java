package phform.application.html.util;

import java.util.Comparator;

import phform.application.html.FormObject;

public class FormRightBottomComparator implements Comparator
{
    
    public int compare(Object o1, Object o2)
    {
        FormObject formObj1 = (FormObject) o1;
        FormObject formObj2 = (FormObject) o2;
        
        if (formObj1.getRightBottom().getX() < formObj2.getRightBottom().getX())
        {
            return -1;
        }
        else if (formObj1.getRightBottom().getX() > formObj2.getRightBottom().getX())
        {
            return 1;
        }
        else
        {
            if (formObj1.getRightBottom().getY() < formObj2.getRightBottom().getY())
            {
                return -1;
            }
            else if (formObj1.getRightBottom().getY() > formObj2.getRightBottom().getY())
            {
                return 1;
            }
            else
            {
                return 0;
            }
        }
    }
    
}
