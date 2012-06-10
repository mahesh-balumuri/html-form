package phform.application.html.util;

import java.util.Comparator;

import phform.application.html.PointObject;

/**
 * y direction
 * 
 * @author Administrator
 * 
 */
public class LatitudePointComparator implements Comparator
{
    
    public int compare(Object o1, Object o2)
    {
        PointObject point1 = (PointObject) o1;
        PointObject point2 = (PointObject) o2;
        if (point1.getY() < point2.getY())
        {
            return -1;
        }
        else if (point1.getY() > point2.getY())
        {
            return 1;
        }
        return 0;
    }
    
}
