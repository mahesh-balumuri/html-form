package phform.application.html.util;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;

import phform.application.html.Constants;
import phform.application.html.PointObject;
import phform.application.main.PureFormApp;

public class FormUtil
{
    private static long maxId = 0;
    private static boolean cloneFlag = false;
    
    private static boolean textJustifyFlag = false;
    
    public static void lockClone()
    {
        cloneFlag = true;
    }
    
    public static void releaseClone()
    {
        cloneFlag = false;
    }
    
    public static void lockTextJustify()
    {
        textJustifyFlag = true;
    }
    
    public static void releaseTextJustify()
    {
        textJustifyFlag = false;
    }
    
    public static boolean getTextJustifyFlag()
    {
        return textJustifyFlag;
    }
    /**
     * generate UUID
     * 
     * @return
     */
    public static String generateUUID()
    {
        // return UUID.randomUUID().toString().replace("-", "");
        if(cloneFlag)
        {
            return maxId + "";
        }
        else
        {
            return ++maxId + "";
        }
    }
    
    /**
     * diagnose the input columns and rows validate or not
     * @param columns
     * @param rows
     * @return
     */
    public static boolean diagnoseInitTable(int columns, int rows)
    {
        if (Constants.A4_DOC_WIDTH / columns < Constants.TABLE_DEFAULT_DRAGINTERVAL
                || Constants.A4_DOC_HEIGHT / rows < Constants.TABLE_DEFAULT_DRAGINTERVAL)
        {
            return false;
        }
        return true;
    }
    
    public static boolean diagnoseInnerDoc(PointObject pointNow)
    {
        if (null != pointNow)
        {
            if (pointNow.getX() < Constants.A4_DOC_LEFT
                    || pointNow.getX() > Constants.A4_DOC_RIGHT
                    || pointNow.getY() < Constants.A4_DOC_TOP
                    || pointNow.getY() > Constants.A4_DOC_BOTTOM)
            {
                return false;
            }
        }
        return true;
    }

    public static void paintStrokeLine(Graphics g, PointObject lastPoint1,
            PointObject lastPoint2, PointObject nowPoint1, PointObject nowPoint2)
    {
        Graphics2D g2d = (Graphics2D) g;
        Stroke st = g2d.getStroke();
        Color oldColor = g2d.getColor();
        
        Stroke bs =
            new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
                    new float[] { 2, 2 }, 0);
        g2d.setStroke(bs);
        g.setXORMode(oldColor);
        g.drawLine(lastPoint1.getX(), lastPoint1.getY(), lastPoint2.getX(),
                lastPoint2.getY());
        g.setPaintMode();
        g.drawLine(nowPoint1.getX(), nowPoint1.getY(), nowPoint2.getX(),
                nowPoint2.getY());
        
        g2d.setStroke(st);
    }
    
    public static void paintStrokeRec(Graphics g, PointObject lastRect1,
            PointObject lastRect2, PointObject nowRect1, PointObject nowRect2)
    {
        Graphics2D g2d = (Graphics2D) g;
        Stroke st = g2d.getStroke();
        Color oldColor = g2d.getColor();
        
        Stroke bs =
            new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
                    new float[] { 2, 2 }, 0);
        g2d.setStroke(bs);
        g.setXORMode(oldColor);
        g.drawRect(lastRect1.getX(), lastRect1.getY(), lastRect2.getX()
                - lastRect1.getX(), lastRect2.getY() - lastRect1.getY());
        g.setPaintMode();
        g.drawRect(nowRect1.getX(), nowRect1.getY(), nowRect2.getX()
                - nowRect1.getX(), nowRect2.getY() - nowRect1.getY());
        
        g2d.setStroke(st);
    }
    
    public static void paintStrokeRec(Graphics g, PointObject lastRect,
            PointObject nowRect, int widthRect, int heightRect)
    {
        Graphics2D g2d = (Graphics2D) g;
        Stroke st = g2d.getStroke();
        Color oldColor = g2d.getColor();
        
        Stroke bs =
            new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
                    new float[] { 2, 2 }, 0);
        g2d.setStroke(bs);
        g.setXORMode(oldColor);
        g.drawRect(lastRect.getX(), lastRect.getY(), widthRect, heightRect);
        g.setPaintMode();
        g.drawRect(nowRect.getX(), nowRect.getY(), widthRect, heightRect);
        
        g2d.setStroke(st);
    }

    public static void paintBackground(Graphics g)
    {
        // ===================================
        g.drawRect(0, 0, Constants.A4_SCREEN_WIDTH, Constants.A4_SCREEN_HEIGHT);
        
        // ===================================
        
        Color preColor = g.getColor();
        
        g.setColor(Color.LIGHT_GRAY);
        
        // left top
        g.drawLine(Constants.A4_LEFT - Constants.CORNER, Constants.A4_TOP,
                Constants.A4_LEFT, Constants.A4_TOP);
        g.drawLine(Constants.A4_LEFT, Constants.A4_TOP - Constants.CORNER,
                Constants.A4_LEFT, Constants.A4_TOP);
        
        // right top
        g.drawLine(Constants.A4_SCREEN_WIDTH - Constants.A4_LEFT,
                Constants.A4_TOP, Constants.A4_SCREEN_WIDTH - Constants.A4_LEFT
                        + Constants.CORNER, Constants.A4_TOP);
        g.drawLine(Constants.A4_SCREEN_WIDTH - Constants.A4_LEFT,
                Constants.A4_TOP - Constants.CORNER, Constants.A4_SCREEN_WIDTH
                        - Constants.A4_LEFT, Constants.A4_TOP);
        
        // left bottom
        g.drawLine(Constants.A4_LEFT - Constants.CORNER,
                Constants.A4_SCREEN_HEIGHT - Constants.A4_TOP,
                Constants.A4_LEFT, Constants.A4_SCREEN_HEIGHT
                        - Constants.A4_TOP);
        g.drawLine(Constants.A4_LEFT, Constants.A4_SCREEN_HEIGHT
                - Constants.A4_TOP + Constants.CORNER, Constants.A4_LEFT,
                Constants.A4_SCREEN_HEIGHT - Constants.A4_TOP);
        
        // right bottom
        g.drawLine(Constants.A4_SCREEN_WIDTH - Constants.A4_LEFT,
                Constants.A4_SCREEN_HEIGHT - Constants.A4_TOP,
                Constants.A4_SCREEN_WIDTH - Constants.A4_LEFT
                        + Constants.CORNER, Constants.A4_SCREEN_HEIGHT
                        - Constants.A4_TOP);
        g.drawLine(Constants.A4_SCREEN_WIDTH - Constants.A4_LEFT,
                Constants.A4_SCREEN_HEIGHT - Constants.A4_TOP,
                Constants.A4_SCREEN_WIDTH - Constants.A4_LEFT,
                Constants.A4_SCREEN_HEIGHT - Constants.A4_TOP
                        + Constants.CORNER);
        
        g.setColor(preColor);
    }
    
    public static void parseStyleAtt(Map map, MutableAttributeSet attSet)
    {
        if (null != attSet && null != attSet.getAttribute(HTML.Attribute.STYLE))
        {
            String att = attSet.getAttribute(HTML.Attribute.STYLE).toString();
            if (null != att && !"".equals(att.trim()))
            {
                String[] attes = att.split(";");
                if (null != attes)
                {
                    for (int i = 0; i < attes.length; i++)
                    {
                        String attribute = attes[i];
                        String[] attKeyValue = attribute.split(":");
                        map.put(attKeyValue[0].toString().trim(),
                                attKeyValue[1].toString().trim());
                    }
                }
            }
        }
    }
    
    public static String gatherHtmlPrefix(String title)
    {
        String str =
            Constants.HTML_DOC_TYPE
                    + Constants.LINE_SEPARATOR
                    + Constants.HTML_HTMLTAG
                    + Constants.LINE_SEPARATOR
                    + Constants.HEADSPACEPREFIX
                    + "<head>"
                    + Constants.LINE_SEPARATOR
                    + Constants.TITLESPACEPREFIX
                    + "<title>"
                    + title
                    + "</title>"
                    + Constants.LINE_SEPARATOR
                    + Constants.METASPACEPREFIX
                    + Constants.HTML_METATAG
                    + Constants.LINE_SEPARATOR
                    + Constants.METASPACEPREFIX
                    + Constants.HTML_METATAG2
                    + Constants.LINE_SEPARATOR
                    + Constants.STYLESPACEPREFIX
                    + "<style type=\"text/css\">"
                    + Constants.LINE_SEPARATOR
                    + Constants.STYLESPACEPREFIX
                    + "table{border:#000000 solid;border-width:0 0 0 0;}"
                    + Constants.LINE_SEPARATOR
                    + Constants.STYLESPACEPREFIX
                    + "table td{border:#000000 solid;border-width:0 1px 1px 0;}"
                    + Constants.LINE_SEPARATOR
                    + Constants.STYLESPACEPREFIX
                    + ".textborder{border:solid 1px #000000;border-left-width:0px; border-top-width:0px; border-right-width:0px;}"
                    + Constants.LINE_SEPARATOR
                    + Constants.STYLESPACEPREFIX
                    + ".textareaborder{border:solid 1px #000000;border-left-width:0px; border-top-width:0px; border-right-width:0px; border-bottom-width:0px;overflow-y:auto;}"
                    + Constants.LINE_SEPARATOR 
                    + Constants.STYLESPACEPREFIX
                    + "</style>" + Constants.LINE_SEPARATOR
                    + Constants.HEADSPACEPREFIX + "</head>"
                    + Constants.LINE_SEPARATOR + Constants.BODYSPACEPREFIX
                    + "<body>" + Constants.LINE_SEPARATOR
                    + Constants.DIVSPACEPREFIX + "<div style=\"width: "
                    + Constants.A4_DOC_WIDTH + "px;height: "
                    + Constants.A4_DOC_HEIGHT + "px;position:absolute;\">"
                    + Constants.LINE_SEPARATOR;
        return str;
    }
    
    public static String gatherHtmlPostfix()
    {
        String str =
            Constants.DIVSPACEPREFIX + "</div>" + Constants.LINE_SEPARATOR
                    + Constants.BODYSPACEPREFIX + "</body>"
                    + Constants.LINE_SEPARATOR + "</html>";
        return str;
    }
    
    public static void copyList(List dest, List src)
    {
        if (null != dest && null != src)
        {
            for (int i = 0; i < src.size(); i++)
            {
                dest.add(src.get(i));
            }
        }
    }
    
    public static PointObject calcLeftTop(PointObject p, int xDistance,
            int yDistance)
    {
        return new PointObject(p.getX() - xDistance, p.getY() - yDistance);
    }

    public static long getMaxId()
    {
        return maxId;
    }

    public static void setMaxId(long maxId)
    {
        FormUtil.maxId = maxId;
    }
    
    public static void resetMaxId()
    {
        maxId = 0;
    }
}
