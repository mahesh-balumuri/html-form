package phform.application.html.util;

import java.util.Comparator;

import phform.application.html.LineObject;

/**
 * x direction
 * 
 * @author Administrator
 * 
 */
public class LongtitudeLineComparator implements Comparator
{
    
    public int compare(Object o1, Object o2)
    {
        LineObject line1 = (LineObject) o1;
        LineObject line2 = (LineObject) o2;
        if (line1.getStart().getX() < line2.getStart().getX())
        {
            return -1;
        }
        else if (line1.getStart().getX() > line2.getStart().getX())
        {
            return 1;
        }
        return 0;
    }
    
}
