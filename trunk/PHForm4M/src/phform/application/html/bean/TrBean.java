package phform.application.html.bean;

import java.util.ArrayList;
import java.util.List;

import phform.application.html.Constants;
import phform.application.html.OtherObject;

public class TrBean extends OtherObject
{
    private List tdList = new ArrayList();
    
    private int columns = 1;
    
    private TableBean tableBean;
    
    public TrBean()
    {
        super(Constants.TYPE_TR);
    }
    
    public TrBean(int columns, int width, int height, int positionLeft,
            int positionTop, TableBean tableBean)
    {
        super(Constants.TYPE_TR, null, null, width, height,
                Constants.POSITION_CSS_ABSOLUTE, positionLeft, positionTop);
        
        this.columns = columns;
        this.tableBean = tableBean;
        
        initBean();
    }
    
    private void initBean()
    {
        int _width = width / columns;
        for (int i = 0; i < columns; i++)
        {
            int left = this.positionLeft + _width * i;
            int top = this.positionTop;
            tdList.add(new TdBean(_width, this.height, left, top, this));
        }
    }
    
    @Override
    protected String tagContent()
    {
        return null;
    }
    
    public List getTdList()
    {
        return tdList;
    }
    
    public void setTdList(List tdList)
    {
        this.tdList = tdList;
    }
    
    public int getColumns()
    {
        return columns;
    }
    
    public void setColumns(int columns)
    {
        this.columns = columns;
    }
    
    public TableBean getTableBean()
    {
        return tableBean;
    }
    
    public void setTableBean(TableBean tableBean)
    {
        this.tableBean = tableBean;
    }
    
}
