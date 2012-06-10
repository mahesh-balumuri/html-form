package phform.application.html.util;

import java.util.Comparator;

import phform.application.html.PointObject;

/**
 * x direction
 * 
 * @author Administrator
 * 
 */
public class LongtitudePointComparator implements Comparator
{
    
    public int compare(Object o1, Object o2)
    {
        PointObject point1 = (PointObject) o1;
        PointObject point2 = (PointObject) o2;
        if (point1.getX() < point2.getX())
        {
            return -1;
        }
        else if (point1.getX() > point2.getX())
        {
            return 1;
        }
        return 0;
    }
    
}
