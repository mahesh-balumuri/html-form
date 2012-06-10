package phform.application.canvas;

import java.util.ArrayList;
import java.util.List;

import phform.application.html.FormObject;
import phform.application.html.bean.CheckBoxBean;
import phform.application.html.bean.FontBean;
import phform.application.html.bean.TableBean;
import phform.application.html.bean.TextAreaBean;
import phform.application.html.bean.TextBean;
import phform.application.html.util.FormUtil;

public class PureHtmlFormSingleCanvasRedo
{
    private List redoList = new ArrayList();
    
    private int redoCursor = -1;
    
    public void newOper(List tableList, List htmlBeanList)
    {
        int size = redoList.size();
        int clearLength = size - redoCursor - 1;
        for (int i = 0; i < clearLength; i++)
        {
            redoList.remove(redoList.size() - 1);
        }
        redoCursor++;
        FormUtil.lockClone();
        redoList.add(new RedoBean(tableList, htmlBeanList));
        FormUtil.releaseClone();
    }
    
    public boolean canRedo()
    {
        if (redoCursor + 1 <= redoList.size() - 1)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public boolean canUndo()
    {
        if (redoCursor - 1 >= 0)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    public RedoBean redoOper()
    {
        return (RedoBean) redoList.get(++redoCursor);
    }
    
    public RedoBean undoOper()
    {
        return (RedoBean) redoList.get(--redoCursor);
    }
}

class RedoBean
{
    private List tableList = new ArrayList();
    
    private List htmlBeanList = new ArrayList();
    
    protected RedoBean(List tableList, List htmlBeanList)
    {
        if (null == tableList)
        {
            this.tableList = null;
        }
        else
        {
            for (int i = 0; i < tableList.size(); i++)
            {
                TableBean table = new TableBean();
                table.cloneMe((FormObject) tableList.get(i));
                this.tableList.add(table);
            }
        }
        if (null == htmlBeanList)
        {
            this.htmlBeanList = null;
        }
        else
        {
            for (int i = 0; i < htmlBeanList.size(); i++)
            {
                if (htmlBeanList.get(i) instanceof CheckBoxBean)
                {
                    CheckBoxBean cb = new CheckBoxBean();
                    cb.cloneMe((FormObject) htmlBeanList.get(i));
                    this.htmlBeanList.add(cb);
                }
                else if (htmlBeanList.get(i) instanceof FontBean)
                {
                    FontBean fb = new FontBean();
                    fb.cloneMe((FormObject) htmlBeanList.get(i));
                    this.htmlBeanList.add(fb);
                }
                else if (htmlBeanList.get(i) instanceof TextAreaBean)
                {
                    TextAreaBean ta = new TextAreaBean();
                    ta.cloneMe((FormObject) htmlBeanList.get(i));
                    this.htmlBeanList.add(ta);
                }
                else if (htmlBeanList.get(i) instanceof TextBean)
                {
                    TextBean tb = new TextBean();
                    tb.cloneMe((FormObject) htmlBeanList.get(i));
                    this.htmlBeanList.add(tb);
                }
            }
        }
    }

    public List getHtmlBeanList()
    {
        List _htmlBeanList = new ArrayList();
        for (int i = 0; i < htmlBeanList.size(); i++)
        {
            if (htmlBeanList.get(i) instanceof CheckBoxBean)
            {
                CheckBoxBean cb = new CheckBoxBean();
                cb.cloneMe((FormObject) htmlBeanList.get(i));
                _htmlBeanList.add(cb);
            }
            else if (htmlBeanList.get(i) instanceof FontBean)
            {
                FontBean fb = new FontBean();
                fb.cloneMe((FormObject) htmlBeanList.get(i));
                _htmlBeanList.add(fb);
            }
            else if (htmlBeanList.get(i) instanceof TextAreaBean)
            {
                TextAreaBean ta = new TextAreaBean();
                ta.cloneMe((FormObject) htmlBeanList.get(i));
                _htmlBeanList.add(ta);
            }
            else if (htmlBeanList.get(i) instanceof TextBean)
            {
                TextBean tb = new TextBean();
                tb.cloneMe((FormObject) htmlBeanList.get(i));
                _htmlBeanList.add(tb);
            }
        }
        return _htmlBeanList;
    }

    public List getTableList()
    {
        List _tableList = new ArrayList();
        for (int i = 0; i < tableList.size(); i++)
        {
            TableBean table = new TableBean();
            table.cloneMe((FormObject) tableList.get(i));
            _tableList.add(table);
        }
        return _tableList;
    }
}