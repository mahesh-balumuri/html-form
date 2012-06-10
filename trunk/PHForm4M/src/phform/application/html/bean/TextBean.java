package phform.application.html.bean;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import phform.application.html.Constants;
import phform.application.html.InputObject;
import phform.application.html.PointObject;
import phform.application.swing.PaintInterface;

public class TextBean extends InputObject implements PaintInterface
{

    public TextBean()
    {
        super(Constants.TYPE_INPUT_TEXT);
    }

    public TextBean(int width, int positionLeft, int positionTop)
    {
        this();
        setWidth(width);
        setHeight(Constants.TEXTHEIGHT);
        setPositionLeft(positionLeft);
        setPositionTop(positionTop);
    }
    
    public void phPaint(Graphics g)
    {
        Color oldColor = g.getColor();
        g.setColor(Color.GRAY);
        g
                .drawRect(this.positionLeft, this.positionTop, this.width,
                        this.height);
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
    
    public int getCursorDirectionByMove(PointObject pointNow)
    {
        int superDiagnose = super.getCursorDirectionByMove(pointNow);
        if (superDiagnose == 2)
        {
            return superDiagnose;
        }
        else
        {
            return 0;
        }
    }
    
    @Override
    protected String tagPrefix()
    {
        return super.tagPrefix() + "class=\"textborder\" style=\"position:"
                + getPositionCss() + ";left:"
                + (getPositionLeft() - Constants.A4_LEFT) + "px;top:"
                + (getPositionTop() - Constants.A4_TOP) + "px;width:"
                + (getWidth() - 7) + "px;height:" + (getHeight() - 2)
                + "px;\" ";
    }

    @Override
    protected int getMinDragedWidth()
    {
        return Constants.FORM_OBJ_TEXT_DRAGINTERVAL;
    }
    
}
