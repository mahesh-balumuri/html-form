package phform.application.html.util;

import java.util.Comparator;

import phform.application.html.FormObject;

public class FormComparator implements Comparator
{
    
    public int compare(Object o1, Object o2)
    {
        FormObject formObj1 = (FormObject) o1;
        FormObject formObj2 = (FormObject) o2;
        
        if (formObj1.getPositionTop() < formObj2.getPositionTop())
        {
            return -1;
        }
        else if (formObj1.getPositionTop() > formObj2.getPositionTop())
        {
            return 1;
        }
        else
        {
            if (formObj1.getPositionLeft() < formObj2.getPositionLeft())
            {
                return -1;
            }
            else if (formObj1.getPositionLeft() > formObj2.getPositionLeft())
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
