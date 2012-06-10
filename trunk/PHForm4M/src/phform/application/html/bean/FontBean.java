package phform.application.html.bean;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import phform.application.html.Constants;
import phform.application.html.FormObject;
import phform.application.html.OtherObject;
import phform.application.html.PointObject;
import phform.application.html.util.FormUtil;
import phform.application.swing.PaintInterface;

public class FontBean extends OtherObject implements PaintInterface
{
    // contain the string list in element
    // as: "aaaaAAA" in first element AND "bbbbBBB" in the second element
    private List stringList = new ArrayList();
    private List tmpList = new ArrayList();
    private List startList = new ArrayList();
    private String innerString = "";
    private Font font = null;
    private FontMetrics fm = null;
    
    public FontBean()
    {
        super(Constants.TYPE_FONT);
    }
    
    public FontBean(int width, int height, int positionLeft, int positionTop)
    {
        this();
        setWidth(width < getMinDragedWidth() ? getMinDragedWidth() : width);
        setHeight(height < getMinDragedHeight() ? getMinDragedHeight() : height);
        setPositionLeft(positionLeft);
        setPositionTop(positionTop);
    }
    
    private void parseFontString()
    {
        if(null != innerString && !"".equals(innerString))
        {
            tmpList.clear();
            String tmpStr = innerString;
            int lineEnd = tmpStr.indexOf(Constants.BR);
            while (lineEnd != -1)
            {
                String tmp = tmpStr.substring(0, lineEnd);
                if ("".equals(tmp))
                {
                    tmpList.add(Constants.BR);
                }
                else
                {
                    tmpList.add(tmp);
                }
                try
                {
                    tmpStr = tmpStr.substring(lineEnd + Constants.BR.length());
                    lineEnd = tmpStr.indexOf(Constants.BR);
                }
                catch (Exception e)
                {
                    break;
                }
            }
            if(lineEnd == -1)
            {
                tmpList.add(tmpStr);
            }
        }
    }
    
    public void parseFontPosition()
    {
        if(null != innerString && !"".equals(innerString))
        {
            stringList.clear();
            startList.clear();
            
            int fontHeight = fm.getHeight();
            int fontAscent = fm.getAscent();
//            int fontDescent = fm.getDescent();
//            int fontLeading = fm.getLeading();
            
            for(int i = 0; i < tmpList.size(); i++)
            {
                String tmp = (String)tmpList.get(i);
                if (tmp.equals(Constants.BR))
                {
                    stringList.add(Constants.BR);
                }
                else
                {
                    int startIndex = 0;
                    for (int j = 0; j < tmp.length(); j++)
                    {
                        if (fm.stringWidth(tmp.substring(startIndex, j + 1)) == this.width)
                        {
//                            System.out.println(tmp.substring(startIndex, j + 1));
//                            System.out.println(fm.stringWidth(tmp.substring(startIndex, j + 1)));
                            stringList.add(tmp.substring(startIndex, j + 1));
                            startIndex = j + 1;
                            continue;
                        }
                        else if(fm.stringWidth(tmp.substring(startIndex, j + 1)) > this.width)
                        {
                            if (j + 1 - startIndex == 1)
                            {
                                break;
                            }
                            stringList.add(tmp.substring(startIndex, j));
                            startIndex = j;
                            j--;
                            continue;
                        }
                        
                        if (j == tmp.length() - 1)
                        {
                            stringList.add(tmp.substring(startIndex, tmp
                                    .length()));
                        }
                    }
                }
            }
            for (int i = 0; i < stringList.size(); i++)
            {
                startList.add(new PointObject(this.positionLeft,
                        this.positionTop + fontAscent + fontHeight * i));
            }
        }
    }
    
    public void setFont(String fontFamily, int fontStyle, int fontSize)
    {
        this.font = new Font(fontFamily, fontStyle, fontSize);
        this.fm = null;
    }
    
    public void setFont(Font font)
    {
        this.font = font;
        this.fm = null;
    }
    
    public Font getFont()
    {
        return this.font;
    }

    public String getInnerString()
    {
        return innerString == null ? "" : innerString;
    }

    public void setInnerString(String innerString)
    {
        this.innerString = innerString;
        tmpList.clear();
    }

    public void phPaint(Graphics g)
    {
        Color oldColor = g.getColor();
        g.setColor(Color.BLACK);
        Font oldFont = g.getFont();
        if (!"".equals(innerString))
        {
            boolean reCalc = false;
            if (tmpList.isEmpty() || null == fm)
            {
                reCalc = true;
            }
            if (tmpList.isEmpty())
            {
                parseFontString();
            }
            if (null == fm)
            {
                fm = g.getFontMetrics(font);
            }
            if (reCalc)
            {
                parseFontPosition();
            }
            g.setFont(font);
            int fontHeight = fm.getHeight();
            for (int i = 0; i < stringList.size(); i++)
            {
                if (fontHeight * (i + 1) > this.height)
                {
                    break;
                }
                String tmp = (String) stringList.get(i);
                if (!tmp.equals(Constants.BR))
                {
                    PointObject start = (PointObject)startList.get(i);
                    g.drawString(tmp, start.getX(), start.getY());
                }
            }
        }
        g.setColor(oldColor);
        g.setFont(oldFont);
    }

    public void phSelectPaint(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        Stroke st = g2d.getStroke();
        Stroke bs =
            new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
                    new float[] { 2, 2 }, 0);
        g2d.setStroke(bs);
        g2d.drawRect(this.positionLeft, this.positionTop, this.width,
                this.height);
        g2d.setStroke(st);
    }
    
    @Override
    protected String tagContent()
    {
        return innerString.replace(" ", "&nbsp;").replace(Constants.BR, "<br>");
    }
    
    @Override
    protected String tagPrefix()
    {
        return super.tagPrefix() + "style=\"position:" + getPositionCss()
                + ";left:" + (getPositionLeft() - Constants.A4_LEFT)
                + "px;top:" + (getPositionTop() - Constants.A4_TOP)
                + "px;width:" + getWidth() + "px;height:" + getHeight()
                + "px;font-family:" + font.getFamily() + ";font-size:"
                + font.getSize() + "px;font-weight:"
                + (font.getStyle() == Font.BOLD ? "bold;" : "normal;")
                + "word-wrap:break-word;\" ";
    }
    
    public void textJustify()
    {
        if (!"".equals(innerString))
        {
            int fontHeight = fm.getHeight();
            int finalWidth = 0;
            int finalHeight = stringList.size() * fontHeight;
            for (int i = 0; i < stringList.size(); i++)
            {
                String tmp = (String) stringList.get(i);
                if (!tmp.equals(Constants.BR))
                {
                    int w = fm.stringWidth(tmp);
                    if (w > finalWidth)
                    {
                        finalWidth = w;
                    }
                }
            }
            if (finalWidth != 0)
            {
                setWidth(finalWidth + 2);
            }
            if (finalHeight != 0)
            {
                setHeight(finalHeight);
            }
        }
    }
    public void cloneMe(FormObject formObj)
    {
        super.cloneMe(formObj);
        if (null != formObj)
        {
            FontBean b = (FontBean) formObj;
            FormUtil.copyList(this.stringList, b.stringList);
            FormUtil.copyList(this.tmpList, b.tmpList);
            FormUtil.copyList(this.startList, b.startList);
            //Collections.copy(this.stringList, b.stringList);
            //Collections.copy(this.tmpList, b.tmpList);
            //Collections.copy(this.startList, b.startList);
            this.innerString = b.innerString;
            this.font = b.font;
        }
    }

    @Override
    public void bottomJustify(FormObject objBottom)
    {
        super.bottomJustify(objBottom);
        setInnerString(innerString);
    }

    @Override
    public void centerJustify(FormObject objCenter)
    {
        super.centerJustify(objCenter);
        setInnerString(innerString);
    }

    @Override
    public void leftJustify(FormObject objLeft)
    {
        super.leftJustify(objLeft);
        setInnerString(innerString);
    }

    @Override
    public void middleJustify(FormObject objMiddle)
    {
        super.middleJustify(objMiddle);
        setInnerString(innerString);
    }

    @Override
    public void rightJustify(FormObject objRight)
    {
        super.rightJustify(objRight);
        setInnerString(innerString);
    }

    @Override
    public void topJustify(FormObject objTop)
    {
        super.topJustify(objTop);
        setInnerString(innerString);
    }
    
    
}
