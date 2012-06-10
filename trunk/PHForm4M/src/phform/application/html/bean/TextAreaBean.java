package phform.application.html.bean;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.ImageObserver;

import phform.application.html.Constants;
import phform.application.html.FormObject;
import phform.application.html.OtherObject;
import phform.application.swing.PaintInterface;

public class TextAreaBean extends OtherObject implements PaintInterface
{

    private ImageObserver canvas;
    
    public TextAreaBean()
    {
        super(Constants.TYPE_TEXTAREA);
    }

    public TextAreaBean(int width, int height, int positionLeft,
            int positionTop, ImageObserver canvas)
    {
        this();
        setWidth(width < getMinDragedWidth() ? getMinDragedWidth() : width);
        setHeight(height < getMinDragedHeight() ? getMinDragedHeight() : height);
        setPositionLeft(positionLeft);
        setPositionTop(positionTop);
        this.canvas = canvas;
    }
    
    @Override
    protected String tagContent()
    {
        return "";
    }

    public void phPaint(Graphics g)
    {
        Color oldColor = g.getColor();
        g.setColor(Color.GRAY);
        g.drawRect(this.positionLeft, this.positionTop, this.width,
                        this.height);
        g.setColor(Color.LIGHT_GRAY);
        g.drawLine(
                this.positionLeft + this.width - Constants.TEXTAREAWIDTH - 1,
                this.positionTop, this.positionLeft + this.width
                        - Constants.TEXTAREAWIDTH - 1, this.positionTop
                        + this.height);
        
        if (getHeight() >= Constants.TEXTAREAHEIGHT * 2)
        {
            g.drawImage(Constants.TEXTAREATOPIMAGE.getImage(),
                    this.positionLeft + this.width - Constants.TEXTAREAWIDTH,
                    this.positionTop + 1, Constants.TEXTAREAWIDTH,
                    Constants.TEXTAREAHEIGHT, canvas);
            g.drawImage(Constants.TEXTAREABOTTOMIMAGE.getImage(),
                    this.positionLeft + this.width - Constants.TEXTAREAWIDTH,
                    this.positionTop + this.height - Constants.TEXTAREAHEIGHT,
                    Constants.TEXTAREAWIDTH, Constants.TEXTAREAHEIGHT, canvas);
        }
        else
        {
            g.drawImage(Constants.TEXTAREATOPIMAGE.getImage(),
                    this.positionLeft + this.width - Constants.TEXTAREAWIDTH,
                    this.positionTop + 1, Constants.TEXTAREAWIDTH,
                    getHeight() / 2, canvas);
            g.drawImage(Constants.TEXTAREABOTTOMIMAGE.getImage(),
                    this.positionLeft + this.width - Constants.TEXTAREAWIDTH,
                    this.positionTop + this.height - getHeight() / 2,
                    Constants.TEXTAREAWIDTH, getHeight() / 2, canvas);
        }
        
        g.setColor(oldColor);
    }

    public void phSelectPaint(Graphics g)
    {
        Graphics2D g2d = (Graphics2D) g;
        Stroke st = g2d.getStroke();
        Stroke bs =
            new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0,
                    new float[] { 2, 2 }, 0);
        g2d.setStroke(bs);
        g2d.drawRect(this.positionLeft - 1, this.positionTop - 1,
                this.width + 2, this.height + 2);
        g2d.setStroke(st);
    }
    
    @Override
    protected String tagPrefix()
    {
        return super.tagPrefix() + "class=\"textareaborder\" style=\"position:"
                + getPositionCss() + ";left:"
                + (getPositionLeft() - Constants.A4_LEFT) + "px;top:"
                + (getPositionTop() - Constants.A4_TOP) + "px;width:"
                + (getWidth() - 7) + "px;height:" + (getHeight() - 2)
                + "px;\" ";
    }

    @Override
    protected int getMinDragedHeight()
    {
        return Constants.FORM_OBJ_TEXTAREA_DRAGINTERVAL_H;
    }

    @Override
    protected int getMinDragedWidth()
    {
        return Constants.FORM_OBJ_TEXTAREA_DRAGINTERVAL_W;
    }
    
    public void cloneMe(FormObject formObj)
    {
        super.cloneMe(formObj);
        if(null != formObj)
        {
            this.canvas = ((TextAreaBean) formObj).canvas;
        }
    }
}
