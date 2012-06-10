package phform.application.html.util;

import java.util.Comparator;

import phform.application.html.LineObject;

/**
 * y direction
 * 
 * @author Administrator
 * 
 */
public class LatitudeLineComparator implements Comparator
{
    
    public int compare(Object o1, Object o2)
    {
        LineObject line1 = (LineObject) o1;
        LineObject line2 = (LineObject) o2;
        if (line1.getStart().getY() < line2.getStart().getY())
        {
            return -1;
        }
        else if (line1.getStart().getY() > line2.getStart().getY())
        {
            return 1;
        }
        return 0;
    }
    
}
