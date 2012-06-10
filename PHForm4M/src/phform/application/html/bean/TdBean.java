package phform.application.html.bean;

import java.awt.Color;
import java.awt.Graphics;

import phform.application.html.Constants;
import phform.application.html.FormObject;
import phform.application.html.OtherObject;
import phform.application.html.PointObject;
import phform.application.swing.PaintInterface;

public class TdBean extends OtherObject implements PaintInterface
{
    
    private int colspan = 1;
    
    private int rowspan = 1;
    
    private TrBean trBean;
    
    private TableBean tableBean;
    
    public TdBean()
    {
        super(Constants.TYPE_TD);
    }
    
    public TdBean(int width, int height, int positionLeft, int positionTop,
            TrBean trBean)
    {
        super(Constants.TYPE_TD, null, null, width, height,
                Constants.POSITION_CSS_ABSOLUTE, positionLeft, positionTop);
        this.trBean = trBean;
    }
    
    public TdBean(int width, int height, int positionLeft, int positionTop,
            TableBean tableBean)
    {
        super(Constants.TYPE_TD, null, null, width, height,
                Constants.POSITION_CSS_ABSOLUTE, positionLeft, positionTop);
        this.tableBean = tableBean;
    }
    
    public TdBean(int width, int height, int positionLeft, int positionTop)
    {
        super(Constants.TYPE_TD, null, null, width, height,
                Constants.POSITION_CSS_ABSOLUTE, positionLeft, positionTop);
    }
    
    public TdBean(TdBean tdBean)
    {
        this(tdBean.getWidth(), tdBean.getHeight(), tdBean.getPositionLeft(),
                tdBean.getPositionTop(), tdBean.getTableBean());
        setName(tdBean.getName());
        setId(tdBean.getId());
    }
    
    @Override
    protected String tagContent()
    {
        return null;
    }
    
    public int getColspan()
    {
        return colspan;
    }
    
    public void setColspan(int colspan)
    {
        this.colspan = colspan;
    }
    
    public int getRowspan()
    {
        return rowspan;
    }
    
    public void setRowspan(int rowspan)
    {
        this.rowspan = rowspan;
    }
    
    public TrBean getTrBean()
    {
        return trBean;
    }
    
    public void setTrBean(TrBean trBean)
    {
        this.trBean = trBean;
    }
    
    /**
     * diagnose td can split or not
     * @param splitCols
     * @param splitRows
     * @return
     */
    public boolean diagnoseSplit(int splitCols, int splitRows)
    {
        if (width / splitCols < Constants.TABLE_DEFAULT_DRAGINTERVAL
                || height / splitRows < Constants.TABLE_DEFAULT_DRAGINTERVAL)
        {
            return false;
        }
        return true;
    }

    public TableBean getTableBean()
    {
        return tableBean;
    }

    public void setTableBean(TableBean tableBean)
    {
        this.tableBean = tableBean;
    }

    public void phPaint(Graphics g)
    {
        g
                .drawRect(getPositionLeft(), getPositionTop(), getWidth(),
                        getHeight());
    }

    public void phSelectPaint(Graphics g)
    {
        Color oldColor = g.getColor();
        g.setColor(Color.BLUE);
        g.drawRect(getPositionLeft() + 2, getPositionTop() + 2, getWidth() - 4,
                getHeight() - 4);
        g.setColor(oldColor);
    }

    @Override
    public boolean diagnoseMoveCursor(PointObject pointNow)
    {
        return false;
    }

    @Override
    public boolean diagnoseSelectInner(PointObject pointNow)
    {
        return false;
    }

    @Override
    public boolean diagnoseMoveCursorInner(PointObject pointNow)
    {
        return false;
    }

    @Override
    public void dragBottomBorder(PointObject pointNow)
    {
    }

    @Override
    public void dragRightBorder(PointObject pointNow)
    {
    }

    @Override
    public void dragRightBottom(PointObject pointNow)
    {
    }

    @Override
    public int getCursorDirectionByMove(PointObject pointNow)
    {
        return 0;
    }

    @Override
    public void moveWholeObject(PointObject pointNow)
    {
    }
    
    public void cloneMe(FormObject formObj)
    {
        super.cloneMe(formObj);
        if (null != formObj)
        {
            TdBean td = (TdBean) formObj;
            this.colspan = td.colspan;
            this.rowspan = td.rowspan;
        }
    }
    
}
