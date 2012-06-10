package phform.application.html.bean;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.image.ImageObserver;

import phform.application.html.Constants;
import phform.application.html.FormObject;
import phform.application.html.InputObject;
import phform.application.html.PointObject;
import phform.application.swing.PaintInterface;

public class CheckBoxBean extends InputObject implements PaintInterface
{

    private ImageObserver canvas;
    public CheckBoxBean()
    {
        super(Constants.TYPE_INPUT_CHECKBOX);
    }
    
    public CheckBoxBean(int positionLeft, int positionTop, ImageObserver canvas)
    {
        this();
        setWidth(Constants.CHECKBOXWHOLEWIDTH);
        setHeight(Constants.CHECKBOXWHOLEHEIGHT);
        setPositionLeft(positionLeft);
        setPositionTop(positionTop);
        this.canvas = canvas;
    }
    
    public void phPaint(Graphics g)
    {
        g
                .drawImage(
                        Constants.CHECKBOXIMAGE.getImage(),
                        this.positionLeft
                                + (Constants.CHECKBOXWHOLEWIDTH - Constants.CHECKBOXWIDTH)
                                / 2,
                        this.positionTop
                                + (Constants.CHECKBOXWHOLEHEIGHT - Constants.CHECKBOXHEIGHT)
                                / 2, Constants.CHECKBOXWIDTH,
                        Constants.CHECKBOXHEIGHT, canvas);
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
                this.width + 1, this.height + 1);
        g2d.setStroke(st);
    }
    
    public int getCursorDirectionByMove(PointObject pointNow)
    {
        return 0;
    }
    
    @Override
    protected String tagPrefix()
    {
        return super.tagPrefix() + "style=\"position:" + getPositionCss()
                + ";left:" + (getPositionLeft() - Constants.A4_LEFT)
                + "px;top:" + (getPositionTop() - Constants.A4_TOP) + "px;\" ";
    }
    
    public void cloneMe(FormObject formObj)
    {
        super.cloneMe(formObj);
        if (null != formObj)
        {
            this.canvas = ((CheckBoxBean) formObj).canvas;
        }
    }
}
