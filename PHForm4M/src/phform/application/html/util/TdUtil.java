package phform.application.html.util;

import java.util.List;

import phform.application.html.FormObject;
import phform.application.html.PointObject;

public class TdUtil
{
    /**
     * diagnose the 2 objectes conflict or not
     * 
     * @param obj1
     * @param obj2
     * @return
     */
    public static boolean diagnoseConflict(FormObject obj1, FormObject obj2)
    {
        if (obj1.getLeftBottom().getY() <= obj2.getLeftTop().getY()
                || obj1.getLeftTop().getX() >= obj2.getRightTop().getX()
                || obj1.getLeftTop().getY() >= obj2.getLeftBottom().getY()
                || obj1.getRightTop().getX() <= obj2.getLeftTop().getX())
        {
            return false;
        }
        return true;
    }
    /**
     * diagnose the tdList2Merge is a whole rectangle or not
     * 
     * @param tdList2Merge
     * @return
     */
    public static boolean diagnoseMerge(List tdList2Merge)
    {
        boolean isRec = false;
        
        int left = -1;
        int right = -1;
        int top = -1;
        int bottom = -1;
        
        // diagnose the sum area equals the 4 point of the rectangle
        
        int sumArea = 0;
        
        // get the left, right, top and bottom points and the sumArea
        for (int i = 0; i < tdList2Merge.size(); i++)
        {
            FormObject formObj = (FormObject) tdList2Merge.get(i);
            sumArea += formObj.getArea();
            
            // left
            if (-1 == left)
            {
                left = formObj.getLeftTop().getX();
            }
            else if (left > formObj.getLeftTop().getX())
            {
                left = formObj.getLeftTop().getX();
            }
            
            // right
            if (-1 == right)
            {
                right = formObj.getRightTop().getX();
            }
            else if (right < formObj.getRightTop().getX())
            {
                right = formObj.getRightTop().getX();
            }
            
            // top
            if (-1 == top)
            {
                top = formObj.getLeftTop().getY();
            }
            else if (top > formObj.getLeftTop().getY())
            {
                top = formObj.getLeftTop().getY();
            }
            
            // bottom
            if (-1 == bottom)
            {
                bottom = formObj.getRightBottom().getY();
            }
            else if (bottom < formObj.getRightBottom().getY())
            {
                bottom = formObj.getRightBottom().getY();
            }
        }
        
        boolean haveLeftTop = false;
        boolean haveRightTop = false;
        boolean haveLeftBottom = false;
        boolean haveRightBottom = false;
        
        PointObject leftTop = new PointObject(left, top);
        PointObject leftBottom = new PointObject(left, bottom);
        PointObject rightTop = new PointObject(right, top);
        PointObject rightBottom = new PointObject(right, bottom);
        
        // diagnose the 4 points exist or not
        for (int i = 0; i < tdList2Merge.size(); i++)
        {
            FormObject formObj = (FormObject) tdList2Merge.get(i);
            
            if (!haveLeftTop && leftTop.equals(formObj.getLeftTop()))
            {
                haveLeftTop = true;
            }
            
            if (!haveRightTop && rightTop.equals(formObj.getRightTop()))
            {
                haveRightTop = true;
            }
            
            if (!haveLeftBottom && leftBottom.equals(formObj.getLeftBottom()))
            {
                haveLeftBottom = true;
            }
            
            if (!haveRightBottom
                    && rightBottom.equals(formObj.getRightBottom()))
            {
                haveRightBottom = true;
            }
            
            if (haveLeftTop && haveRightTop && haveLeftBottom
                    && haveRightBottom)
            {
                break;
            }
        }
        
        if (haveLeftTop && haveRightTop && haveLeftBottom && haveRightBottom)
        {
            int rectangleArea =
                    (rightTop.getX() - leftTop.getX())
                            * (rightBottom.getY() - rightTop.getY());
            isRec = rectangleArea == sumArea;
        }
        
        return isRec;
    }
}
