package phform.application.html.bean;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import phform.application.html.Constants;
import phform.application.html.FormObject;
import phform.application.html.LineObject;
import phform.application.html.OtherObject;
import phform.application.html.PointObject;
import phform.application.html.util.FormLeftTopComparator;
import phform.application.html.util.FormRightBottomComparator;
import phform.application.html.util.FormUtil;
import phform.application.html.util.LatitudeLineComparator;
import phform.application.html.util.LongtitudeLineComparator;
import phform.application.swing.PaintInterface;

public class TableBean extends OtherObject implements PaintInterface
{
    private List trList = new ArrayList();
    
    private List tdList = new ArrayList();
    
    private int columns = 1;
    
    private int rows = 1;
    
    private List leftList = new ArrayList();
    
    private List topList = new ArrayList();
    
    private PointObject standardPoint = new PointObject(-1, -1);
    
    public List getLeftList()
    {
        return leftList;
    }
    
    public List getTopList()
    {
        return topList;
    }
    
    public TableBean()
    {
        super(Constants.TYPE_TABLE);
    }
    
    public TableBean(boolean wholePage)
    {
        this();
        
        setWidth(Constants.A4_DOC_WIDTH);
        if (wholePage)
        {
            setHeight(Constants.A4_DOC_HEIGHT);
        }
        setPositionLeft(Constants.A4_LEFT);
        setPositionTop(Constants.A4_TOP);
    }
    
    public TableBean(int columns, int rows)
    {
        this();
        this.columns = columns;
        this.rows = rows;
        
        setWidth(Constants.A4_DOC_WIDTH);
        setHeight(Constants.A4_DOC_HEIGHT);
        setPositionLeft(Constants.A4_LEFT);
        setPositionTop(Constants.A4_TOP);
        
        initBean();
    }
    
    /**
     * @param columns
     * @param rows
     * @param wholePage:
     *            table extends whole page or not
     */
    public TableBean(int columns, int rows, boolean wholePage)
    {
        this();
        this.columns = columns;
        this.rows = rows;
        
        setWidth(Constants.A4_DOC_WIDTH);
        if (wholePage)
        {
            setHeight(Constants.A4_DOC_HEIGHT);
        }
        else
        {
            setHeight(Constants.TABLE_DEFAULT_HEIGHT * rows);
        }
        setPositionLeft(Constants.A4_LEFT);
        setPositionTop(Constants.A4_TOP);
        
        initBean();
    }
    
    /**
     * tdList value:tdBean
     */
    private void initBean()
    {
        int _height = height / rows;
        int _width = width / columns;
        for (int i = 0; i < rows; i++)
        {
            int top = this.positionTop + _height * i;
            for (int j = 0; j < columns; j++)
            {
                int left = this.positionLeft + _width * j;
                int heightTmp = _height;
                int widthTmp = _width;
                if (i == rows - 1 && rows - 1 > 0)
                {
                    heightTmp = height - _height * (rows - 1);
                }
                if (j == columns - 1 && columns - 1 > 0)
                {
                    widthTmp = width - _width * (columns - 1);
                }
                tdList.add(new TdBean(widthTmp, heightTmp, left, top, this));
            }
        }
    }
    
    /**
     * calc the basic td in rows and cols
     */
    private void parseLastTrTd()
    {
        leftList.clear();
        topList.clear();
        Map leftMap = new HashMap();
        Map topMap = new HashMap();
        for (int i = 0; i < tdList.size(); i++)
        {
            TdBean tdBean = (TdBean) tdList.get(i);
            leftMap.put(tdBean.getPositionLeft(), "");
            topMap.put(tdBean.getPositionTop(), "");
        }
        
        for (Iterator it = leftMap.keySet().iterator(); it.hasNext();)
        {
            leftList.add(it.next());
        }
        
        for (Iterator it = topMap.keySet().iterator(); it.hasNext();)
        {
            topList.add(it.next());
        }
        
        Collections.sort(leftList);
        Collections.sort(topList);
    }
    
    /**
     * 
     * set td properties:colspan and rowspan
     */
    private void parseTdSpan()
    {
        parseLastTrTd();
        int span;
        for (int i = 0; i < tdList.size(); i++)
        {
            TdBean tdBean = (TdBean) tdList.get(i);
            
            span = 0;
            for (int j = 0; j < leftList.size(); j++)
            {
                int tmp = Integer.parseInt(leftList.get(j).toString());
                if (tdBean.getPositionLeft() <= tmp
                        && tmp < tdBean.getPositionLeft() + tdBean.getWidth())
                {
                    span++;
                }
            }
            tdBean.setColspan(span);
            
            span = 0;
            for (int j = 0; j < topList.size(); j++)
            {
                int tmp = Integer.parseInt(topList.get(j).toString());
                if (tdBean.getPositionTop() <= tmp
                        && tmp < tdBean.getPositionTop() + tdBean.getHeight())
                {
                    span++;
                }
            }
            tdBean.setRowspan(span);
        }
    }
    
    /**
     * split the td to splitCols columns and splitRows rows
     * 
     * @param tdBean
     * @param splitCols
     * @param splitRows
     */
    public void splitTd(TdBean tdBean, int splitCols, int splitRows)
    {
        tdBean = new TdBean(tdBean);
        TdBean tdBeanExist = (TdBean) tdList.get(tdList.indexOf(tdBean));
        int _width = tdBeanExist.getWidth() / splitCols;
        int _height = tdBeanExist.getHeight() / splitRows;
        
        tdBeanExist.setWidth(_width);
        tdBeanExist.setHeight(_height);
        
        for (int i = 0; i < splitRows; i++)
        {
            int top = tdBeanExist.getPositionTop() + i * _height;
            for (int j = 0; j < splitCols; j++)
            {
                if (i == 0 && j == 0)
                {
                    continue;
                }
                
                int left = tdBeanExist.getPositionLeft() + j * _width;
                
                int heightTmp = _height;
                int widthTmp = _width;
                if (i == splitRows - 1 && splitRows - 1 > 0)
                {
                    heightTmp = tdBean.getHeight() - _height * (splitRows - 1);
                }
                if (j == splitCols - 1 && splitCols - 1 > 0)
                {
                    widthTmp = tdBean.getWidth() - _width * (splitCols - 1);
                }
                tdList.add(new TdBean(widthTmp, heightTmp, left, top, this));
            }
        }
    }
    
    /**
     * merge td
     * 
     * @param tdList2Merge
     */
    public void mergeTd(List tdList2Merge)
    {
        parseLeftTopRightBottom(tdList2Merge);
        
        TdBean tdBean =
            (TdBean) tdList.get(tdList.indexOf(tdList2Merge.get(0)));
        TdBean lastTd =
            (TdBean) tdList.get(tdList.indexOf(tdList2Merge.get(tdList2Merge
                    .size() - 1)));
        for (int i = 1; i < tdList2Merge.size(); i++)
        {
            if (tdList.indexOf(tdList2Merge.get(i)) != -1)
            {
                // remove first
                tdList.remove(tdList2Merge.get(i));
            }
        }
        
        tdBean.setWidth(lastTd.getPositionLeft() + lastTd.getWidth()
                - tdBean.getPositionLeft());
        tdBean.setHeight(lastTd.getPositionTop() + lastTd.getHeight()
                - tdBean.getPositionTop());
    }
    
    /**
     * get the tdBean by the mouse click point
     * 
     * @param pointObj
     * @return
     */
    public TdBean getTdByClick(PointObject pointObj)
    {
        TdBean tdBean = null;
        
        for (int i = 0; i < tdList.size(); i++)
        {
            TdBean tdExist = (TdBean) tdList.get(i);
            if (pointObj.getX() > tdExist.getLeftTop().getX()
                    && pointObj.getX() < tdExist.getRightTop().getX()
                    && pointObj.getY() > tdExist.getLeftTop().getY()
                    && pointObj.getY() < tdExist.getLeftBottom().getY())
            {
                tdBean = tdExist;
                break;
            }
        }
        
        return tdBean;
    }
    
    /**
     * get the cursor direction(x or y) by mouse over the td border
     * 
     * @param pointObj
     * @return 0:none 1:x like:= 2:y like:||
     */
    public int getCursorDirectionByMove(PointObject pointObj)
    {
        int direction = 0;
        
        for (int i = 0; i < tdList.size(); i++)
        {
            TdBean tdBean = (TdBean) tdList.get(i);
            if (/* pointObj.getX() == tdBean.getLeftTop().getX() */
            pointObj.getX() >= (tdBean.getLeftTop().getX() - 1)
                    && pointObj.getX() <= (tdBean.getLeftTop().getX() + 1)
                    && pointObj.getY() > (tdBean.getLeftTop().getY())
                    && pointObj.getY() < (tdBean.getLeftBottom().getY()))
            {
                if (tdBean.getPositionLeft() == Constants.A4_DOC_LEFT)
                {
                    break;
                }
                // standardPoint.setX(tdBean.getLeftTop().getX());
                // standardPoint.setY(pointObj.getY());
                direction = 2;
                break;
            }
            else if (/* pointObj.getX() == tdBean.getRightTop().getX() */
            pointObj.getX() >= (tdBean.getRightTop().getX() - 1)
                    && pointObj.getX() <= (tdBean.getRightTop().getX() + 1)
                    && pointObj.getY() > (tdBean.getRightTop().getY())
                    && pointObj.getY() < (tdBean.getRightBottom().getY()))
            {
                if (tdBean.getRightTop().getX() == Constants.A4_DOC_RIGHT)
                {
                    break;
                }
                // standardPoint.setX(tdBean.getRightTop().getX());
                // standardPoint.setY(pointObj.getY());
                direction = 2;
                break;
            }
            else if (/* pointObj.getY() == tdBean.getLeftTop().getY() */
            pointObj.getY() >= (tdBean.getLeftTop().getY() - 1)
                    && pointObj.getY() <= (tdBean.getLeftTop().getY() + 1)
                    && pointObj.getX() > (tdBean.getLeftTop().getX())
                    && pointObj.getX() < (tdBean.getRightTop().getX()))
            {
                // standardPoint.setY(tdBean.getLeftTop().getY());
                // standardPoint.setX(pointObj.getX());
                direction = 1;
                break;
            }
            else if (/* pointObj.getY() == tdBean.getLeftBottom().getY() */
            pointObj.getY() >= (tdBean.getLeftBottom().getY() - 1)
                    && pointObj.getY() <= (tdBean.getLeftBottom().getY() + 1)
                    && pointObj.getX() > (tdBean.getLeftBottom().getX())
                    && pointObj.getX() < (tdBean.getRightBottom().getX()))
            {
                // standardPoint.setY(tdBean.getLeftBottom().getY());
                // standardPoint.setX(pointObj.getX());
                direction = 1;
                break;
            }
        }
        
        return direction;
    }
    
    /**
     * get the draged td border by mouse click point
     * 
     * @param pointObj
     * @param cursorDirection
     *            1:x 2:y
     * @return List:line with start and end
     */
    public List getMiniDragedTdLineByClick(PointObject pointObj,
            int cursorDirection)
    {
        analyseTableHeight();
        if (/* positionTop + height == pointObj.getY() */
        ((positionTop + height - 1) <= pointObj.getY() && (positionTop + height + 1) >= pointObj
                .getY())
                || /* positionTop == pointObj.getY() */((positionTop - 1) <= pointObj
                        .getY() && (positionTop + 1) >= pointObj.getY()))
        {
            return getMaxDragedTdLineByClick(pointObj, cursorDirection);
        }
        PointObject tmpPoint = new PointObject(pointObj);
        PointObject start = new PointObject(-1, -1);
        PointObject end = new PointObject(-1, -1);
        
        List lineList = new ArrayList();
        // when cursorDirection is x
        if (cursorDirection == 1)
        {
            for (int i = 0; i < tdList.size(); i++)
            {
                TdBean tdBean = (TdBean) tdList.get(i);
                if (/* pointObj.getY() == tdBean.getLeftTop().getY() */pointObj
                        .getY() >= (tdBean.getLeftTop().getY() - 1)
                        && pointObj.getY() <= (tdBean.getLeftTop().getY() + 1))
                {
                    lineList.add(new LineObject(tdBean.getLeftTop(), tdBean
                            .getRightTop()));
                    tmpPoint.setY(tdBean.getLeftTop().getY());
                }
                else if (/* pointObj.getY() == tdBean.getLeftBottom().getY() */pointObj
                        .getY() >= (tdBean.getLeftBottom().getY() - 1)
                        && pointObj.getY() <= (tdBean.getLeftBottom().getY() + 1))
                {
                    lineList.add(new LineObject(tdBean.getLeftBottom(), tdBean
                            .getRightBottom()));
                    tmpPoint.setY(tdBean.getLeftBottom().getY());
                }
            }
            standardPoint.setX(tmpPoint.getX());
            standardPoint.setY(tmpPoint.getY());
            
            Map startPoint = new HashMap();
            Map endPoint = new HashMap();
            for (int i = 0; i < lineList.size(); i++)
            {
                LineObject line = (LineObject) lineList.get(i);
                if (startPoint.containsKey(line.getStart().getX()))
                {
                    startPoint.put(line.getStart().getX(), 2);
                }
                else
                {
                    startPoint.put(line.getStart().getX(), 1);
                }
                
                if (endPoint.containsKey(line.getEnd().getX()))
                {
                    endPoint.put(line.getEnd().getX(), 2);
                }
                else
                {
                    endPoint.put(line.getEnd().getX(), 1);
                }
            }
            
            start.setY(tmpPoint.getY());
            end.setY(tmpPoint.getY());
            end.setX(Integer.MAX_VALUE);
            
            for (Iterator it = startPoint.entrySet().iterator(); it.hasNext();)
            {
                Entry entry = (Entry) it.next();
                if (Integer.parseInt(entry.getValue().toString()) > 1)
                {
                    int tmp = Integer.parseInt(entry.getKey().toString());
                    if (tmp < tmpPoint.getX() && tmp > start.getX())
                    {
                        start.setX(tmp);
                    }
                }
            }
            
            for (Iterator it = endPoint.entrySet().iterator(); it.hasNext();)
            {
                Entry entry = (Entry) it.next();
                if (Integer.parseInt(entry.getValue().toString()) > 1)
                {
                    int tmp = Integer.parseInt(entry.getKey().toString());
                    if (tmp > tmpPoint.getX() && tmp < end.getX())
                    {
                        end.setX(tmp);
                    }
                }
            }
        }
        // when cursorDirection is y
        else if (cursorDirection == 2)
        {
            for (int i = 0; i < tdList.size(); i++)
            {
                TdBean tdBean = (TdBean) tdList.get(i);
                if (/* pointObj.getX() == tdBean.getLeftTop().getX() */pointObj
                        .getX() >= (tdBean.getLeftTop().getX() - 1)
                        && pointObj.getX() <= (tdBean.getLeftTop().getX() + 1))
                {
                    lineList.add(new LineObject(tdBean.getLeftTop(), tdBean
                            .getLeftBottom()));
                    tmpPoint.setX(tdBean.getLeftTop().getX());
                }
                else if (/* pointObj.getX() == tdBean.getRightTop().getX() */pointObj
                        .getX() >= (tdBean.getRightTop().getX() - 1)
                        && pointObj.getX() <= (tdBean.getRightTop().getX() + 1))
                {
                    lineList.add(new LineObject(tdBean.getRightTop(), tdBean
                            .getRightBottom()));
                    tmpPoint.setX(tdBean.getRightTop().getX());
                }
            }
            standardPoint.setX(tmpPoint.getX());
            standardPoint.setY(tmpPoint.getY());
            
            Map startPoint = new HashMap();
            Map endPoint = new HashMap();
            for (int i = 0; i < lineList.size(); i++)
            {
                LineObject line = (LineObject) lineList.get(i);
                if (startPoint.containsKey(line.getStart().getY()))
                {
                    startPoint.put(line.getStart().getY(), 2);
                }
                else
                {
                    startPoint.put(line.getStart().getY(), 1);
                }
                
                if (endPoint.containsKey(line.getEnd().getY()))
                {
                    endPoint.put(line.getEnd().getY(), 2);
                }
                else
                {
                    endPoint.put(line.getEnd().getY(), 1);
                }
            }
            
            start.setX(tmpPoint.getX());
            end.setX(tmpPoint.getX());
            end.setY(Integer.MAX_VALUE);
            
            for (Iterator it = startPoint.entrySet().iterator(); it.hasNext();)
            {
                Entry entry = (Entry) it.next();
                if (Integer.parseInt(entry.getValue().toString()) > 1)
                {
                    int tmp = Integer.parseInt(entry.getKey().toString());
                    if (tmp < tmpPoint.getY() && tmp > start.getY())
                    {
                        start.setY(tmp);
                    }
                }
            }
            
            for (Iterator it = endPoint.entrySet().iterator(); it.hasNext();)
            {
                Entry entry = (Entry) it.next();
                if (Integer.parseInt(entry.getValue().toString()) > 1)
                {
                    int tmp = Integer.parseInt(entry.getKey().toString());
                    if (tmp > tmpPoint.getY() && tmp < end.getY())
                    {
                        end.setY(tmp);
                    }
                }
            }
        }
        
        List resultLineList = new ArrayList();
        resultLineList.add(new LineObject(start, end));
        return resultLineList;
    }
    
    /**
     * get the draged td border by mouse click point where the ctrl key is
     * pressed
     * 
     * @param pointObj
     * @param cursorDirection
     *            1:x 2:y
     * @return lines with start and end
     */
    public List getMaxDragedTdLineByClick(PointObject pointObj,
            int cursorDirection)
    {
        List lineList = new ArrayList();
        standardPoint.setX(pointObj.getX());
        standardPoint.setY(pointObj.getY());
        // when cursorDirection is x
        if (cursorDirection == 1)
        {
            for (int i = 0; i < tdList.size(); i++)
            {
                TdBean tdBean = (TdBean) tdList.get(i);
                if (/* pointObj.getY() == tdBean.getLeftTop().getY() */pointObj
                        .getY() >= (tdBean.getLeftTop().getY() - 1)
                        && pointObj.getY() <= (tdBean.getLeftTop().getY() + 1))
                {
                    lineList.add(new LineObject(tdBean.getLeftTop(), tdBean
                            .getRightTop()));
                    standardPoint.setY(tdBean.getLeftTop().getY());
                }
                else if (/* pointObj.getY() == tdBean.getLeftBottom().getY() */pointObj
                        .getY() >= (tdBean.getLeftBottom().getY() - 1)
                        && pointObj.getY() <= (tdBean.getLeftBottom().getY() + 1))
                {
                    lineList.add(new LineObject(tdBean.getLeftBottom(), tdBean
                            .getRightBottom()));
                    standardPoint.setY(tdBean.getLeftBottom().getY());
                }
            }
            
            Map startPoint = new HashMap();
            for (int i = 0; i < lineList.size(); i++)
            {
                LineObject line = (LineObject) lineList.get(i);
                if (startPoint.containsKey(line.getStart().getX()))
                {
                    if (line.getLength() > ((LineObject) startPoint.get(line
                            .getStart().getX())).getLength())
                    {
                        startPoint.put(line.getStart().getX(), line);
                    }
                }
                else
                {
                    startPoint.put(line.getStart().getX(), line);
                }
            }
            
            lineList =
                Arrays.asList(startPoint.values().toArray(new Object[0]));
            Collections.sort(lineList, new LongtitudeLineComparator());
            
            LineObject lineLast = null;
            List result = new ArrayList();
            
            for (int i = 0; i < lineList.size(); i++)
            {
                if (null == lineLast)
                {
                    lineLast = (LineObject) lineList.get(i);
                }
                else
                {
                    LineObject tmp = (LineObject) lineList.get(i);
                    if (tmp.getStart().getX() == lineLast.getEnd().getX())
                    {
                        lineLast.setEnd(tmp.getEnd());
                    }
                    else if (tmp.getStart().getX() < lineLast.getEnd().getX())
                    {
                        if (tmp.getEnd().getX() > lineLast.getEnd().getX())
                        {
                            lineLast.setEnd(tmp.getEnd());
                        }
                    }
                    else if (tmp.getStart().getX() > lineLast.getEnd().getX())
                    {
                        result.add(lineLast);
                        lineLast = tmp;
                    }
                }
            }
            result.add(lineLast);
            lineList = result;
        }
        // when cursorDirection is y
        else if (cursorDirection == 2)
        {
            for (int i = 0; i < tdList.size(); i++)
            {
                TdBean tdBean = (TdBean) tdList.get(i);
                if (/* pointObj.getX() == tdBean.getLeftTop().getX() */pointObj
                        .getX() >= (tdBean.getLeftTop().getX() - 1)
                        && pointObj.getX() <= (tdBean.getLeftTop().getX() + 1))
                {
                    lineList.add(new LineObject(tdBean.getLeftTop(), tdBean
                            .getLeftBottom()));
                    standardPoint.setX(tdBean.getLeftTop().getX());
                }
                else if (/* pointObj.getX() == tdBean.getRightTop().getX() */pointObj
                        .getX() >= (tdBean.getRightTop().getX() - 1)
                        && pointObj.getX() <= (tdBean.getRightTop().getX() + 1))
                {
                    lineList.add(new LineObject(tdBean.getRightTop(), tdBean
                            .getRightBottom()));
                    standardPoint.setX(tdBean.getRightTop().getX());
                }
            }
            
            Map startPoint = new HashMap();
            for (int i = 0; i < lineList.size(); i++)
            {
                LineObject line = (LineObject) lineList.get(i);
                if (startPoint.containsKey(line.getStart().getY()))
                {
                    if (line.getLength() > ((LineObject) startPoint.get(line
                            .getStart().getY())).getLength())
                    {
                        startPoint.put(line.getStart().getY(), line);
                    }
                }
                else
                {
                    startPoint.put(line.getStart().getY(), line);
                }
            }
            
            lineList =
                Arrays.asList(startPoint.values().toArray(new Object[0]));
            Collections.sort(lineList, new LatitudeLineComparator());
            
            LineObject lineLast = null;
            List result = new ArrayList();
            
            for (int i = 0; i < lineList.size(); i++)
            {
                if (null == lineLast)
                {
                    lineLast = (LineObject) lineList.get(i);
                }
                else
                {
                    LineObject tmp = (LineObject) lineList.get(i);
                    if (tmp.getStart().getY() == lineLast.getEnd().getY())
                    {
                        lineLast.setEnd(tmp.getEnd());
                    }
                    else if (tmp.getStart().getY() < lineLast.getEnd().getY())
                    {
                        if (tmp.getEnd().getY() > lineLast.getEnd().getY())
                        {
                            lineLast.setEnd(tmp.getEnd());
                        }
                    }
                    else if (tmp.getStart().getY() > lineLast.getEnd().getY())
                    {
                        result.add(lineLast);
                        lineLast = tmp;
                    }
                }
            }
            result.add(lineLast);
            lineList = result;
        }
        
        return lineList;
    }
    
    /**
     * @param startPoint
     * @param endPoint
     * @param lineObjects
     * @param direction
     *            1:x 2:y
     */
    public void dragTdBorder(PointObject startPoint, PointObject endPoint,
            List lineObjects, int direction)
    {
        if (direction == 2)
        {
            int distancePlus = endPoint.getX() - startPoint.getX();
            for (int i = 0; i < tdList.size(); i++)
            {
                TdBean tdBean = (TdBean) tdList.get(i);
                for (int j = 0; j < lineObjects.size(); j++)
                {
                    LineObject line = (LineObject) lineObjects.get(j);
                    if (tdBean.getRightTop().getX() == line.getStart().getX()
                            && tdBean.getRightTop().getY() >= line.getStart()
                                    .getY()
                            && tdBean.getRightBottom().getY() <= line.getEnd()
                                    .getY())
                    {
                        tdBean.setWidth(tdBean.getWidth() + distancePlus);
                    }
                    else if (tdBean.getLeftTop().getX() == line.getStart()
                            .getX()
                            && tdBean.getLeftTop().getY() >= line.getStart()
                                    .getY()
                            && tdBean.getLeftBottom().getY() <= line.getEnd()
                                    .getY())
                    {
                        tdBean.setWidth(tdBean.getWidth() - distancePlus);
                        tdBean.setPositionLeft(tdBean.getPositionLeft()
                                + distancePlus);
                    }
                }
            }
        }
        else if (direction == 1)
        {
            int distancePlus = endPoint.getY() - startPoint.getY();
            for (int i = 0; i < tdList.size(); i++)
            {
                TdBean tdBean = (TdBean) tdList.get(i);
                for (int j = 0; j < lineObjects.size(); j++)
                {
                    LineObject line = (LineObject) lineObjects.get(j);
                    if (tdBean.getLeftBottom().getY() == line.getStart().getY()
                            && tdBean.getLeftBottom().getX() >= line.getStart()
                                    .getX()
                            && tdBean.getRightBottom().getX() <= line.getEnd()
                                    .getX())
                    {
                        tdBean.setHeight(tdBean.getHeight() + distancePlus);
                    }
                    else if (tdBean.getLeftTop().getY() == line.getStart()
                            .getY()
                            && tdBean.getLeftTop().getX() >= line.getStart()
                                    .getX()
                            && tdBean.getRightTop().getX() <= line.getEnd()
                                    .getX())
                    {
                        tdBean.setHeight(tdBean.getHeight() - distancePlus);
                        tdBean.setPositionTop(tdBean.getPositionTop()
                                + distancePlus);
                    }
                }
            }
        }
    }
    
    /**
     * @param lineObjects
     * @param endPoint
     * @param direction
     *            1:x 2:y
     * @return reach the max distance can drag or not
     */
    public PointObject diagnoseDragTdBorder(List lineObjects,
            PointObject endPoint, int direction)
    {
        PointObject ret = null;
        if (direction == 2 && null != lineObjects && !lineObjects.isEmpty())
        {
            PointObject linePoint =
                (PointObject) ((LineObject) lineObjects.get(0)).getStart();
            
            if (linePoint.getX() == Constants.A4_DOC_LEFT
                    || linePoint.getX() == Constants.A4_DOC_RIGHT)
            {
                if (endPoint.getX() < Constants.A4_DOC_LEFT)
                {
                    ret = new PointObject();
                    ret.setX(Constants.A4_DOC_LEFT);
                    ret.setY(endPoint.getY());
                    return ret;
                }
                else if (endPoint.getX() > Constants.A4_DOC_RIGHT)
                {
                    ret = new PointObject();
                    ret.setX(Constants.A4_DOC_RIGHT);
                    ret.setY(endPoint.getY());
                    return ret;
                }
            }
            if (endPoint.getX() > linePoint.getX())
            {
                TdBean tmp = null;
                for (int i = 0; i < tdList.size(); i++)
                {
                    TdBean tdBean = (TdBean) tdList.get(i);
                    for (int j = 0; j < lineObjects.size(); j++)
                    {
                        LineObject line = (LineObject) lineObjects.get(j);
                        if (tdBean.getLeftTop().getY() >= line.getStart()
                                .getY()
                                && tdBean.getLeftBottom().getY() <= line
                                        .getEnd().getY())
                        {
                            if (tdBean.getLeftTop().getX() == linePoint.getX())
                            {
                                if (endPoint.getX()
                                        + Constants.TABLE_DEFAULT_DRAGINTERVAL > tdBean
                                        .getRightTop().getX())
                                {
                                    if (tmp == null)
                                    {
                                        tmp = tdBean;
                                    }
                                    else if (tmp.getWidth() > tdBean.getWidth())
                                    {
                                        tmp = tdBean;
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
                if (null != tmp)
                {
                    ret = new PointObject();
                    ret.setX(tmp.getRightTop().getX()
                            - Constants.TABLE_DEFAULT_DRAGINTERVAL);
                    ret.setY(endPoint.getY());
                }
                else
                {
                    ret = new PointObject(endPoint);
                }
            }
            else if (endPoint.getX() < linePoint.getX())
            {
                TdBean tmp = null;
                for (int i = 0; i < tdList.size(); i++)
                {
                    TdBean tdBean = (TdBean) tdList.get(i);
                    for (int j = 0; j < lineObjects.size(); j++)
                    {
                        LineObject line = (LineObject) lineObjects.get(j);
                        if (tdBean.getLeftTop().getY() >= line.getStart()
                                .getY()
                                && tdBean.getLeftBottom().getY() <= line
                                        .getEnd().getY())
                        {
                            if (tdBean.getRightTop().getX() == linePoint.getX())
                            {
                                if (endPoint.getX()
                                        - Constants.TABLE_DEFAULT_DRAGINTERVAL < tdBean
                                        .getLeftTop().getX())
                                {
                                    if (tmp == null)
                                    {
                                        tmp = tdBean;
                                    }
                                    else if (tmp.getWidth() > tdBean.getWidth())
                                    {
                                        tmp = tdBean;
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
                if (null != tmp)
                {
                    ret = new PointObject();
                    ret.setX(tmp.getLeftTop().getX()
                            + Constants.TABLE_DEFAULT_DRAGINTERVAL);
                    ret.setY(endPoint.getY());
                }
                else
                {
                    ret = new PointObject(endPoint);
                }
            }
        }
        else if (direction == 1 && null != lineObjects
                && !lineObjects.isEmpty())
        {
            PointObject linePoint =
                (PointObject) ((LineObject) lineObjects.get(0)).getStart();
            analyseTableHeight();
            if (linePoint.getY() == positionTop
                    || linePoint.getY() == positionTop + height)
            {
                if (endPoint.getY() > Constants.A4_DOC_BOTTOM)
                {
                    ret = new PointObject();
                    ret.setX(endPoint.getX());
                    ret.setY(Constants.A4_DOC_BOTTOM);
                    return ret;
                }
                else if (endPoint.getY() < Constants.A4_DOC_TOP)
                {
                    ret = new PointObject();
                    ret.setX(endPoint.getX());
                    ret.setY(Constants.A4_DOC_TOP);
                    return ret;
                }
            }
            
            if (endPoint.getY() > linePoint.getY())
            {
                TdBean tmp = null;
                for (int i = 0; i < tdList.size(); i++)
                {
                    TdBean tdBean = (TdBean) tdList.get(i);
                    for (int j = 0; j < lineObjects.size(); j++)
                    {
                        LineObject line = (LineObject) lineObjects.get(j);
                        if (tdBean.getLeftTop().getX() >= line.getStart()
                                .getX()
                                && tdBean.getRightTop().getX() <= line.getEnd()
                                        .getX())
                        {
                            if (tdBean.getLeftTop().getY() == linePoint.getY())
                            {
                                if (endPoint.getY()
                                        + Constants.TABLE_DEFAULT_DRAGINTERVAL > tdBean
                                        .getLeftBottom().getY())
                                {
                                    if (tmp == null)
                                    {
                                        tmp = tdBean;
                                    }
                                    else if (tmp.getHeight() > tdBean
                                            .getHeight())
                                    {
                                        tmp = tdBean;
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
                if (null != tmp)
                {
                    ret = new PointObject();
                    ret.setX(endPoint.getX());
                    ret.setY(tmp.getLeftBottom().getY()
                            - Constants.TABLE_DEFAULT_DRAGINTERVAL);
                }
                else
                {
                    ret = new PointObject(endPoint);
                }
            }
            else if (endPoint.getY() < linePoint.getY())
            {
                TdBean tmp = null;
                for (int i = 0; i < tdList.size(); i++)
                {
                    TdBean tdBean = (TdBean) tdList.get(i);
                    for (int j = 0; j < lineObjects.size(); j++)
                    {
                        LineObject line = (LineObject) lineObjects.get(j);
                        if (tdBean.getLeftTop().getX() >= line.getStart()
                                .getX()
                                && tdBean.getRightTop().getX() <= line.getEnd()
                                        .getX())
                        {
                            if (tdBean.getLeftBottom().getY() == linePoint
                                    .getY())
                            {
                                if (endPoint.getY()
                                        - Constants.TABLE_DEFAULT_DRAGINTERVAL < tdBean
                                        .getLeftTop().getY())
                                {
                                    if (tmp == null)
                                    {
                                        tmp = tdBean;
                                    }
                                    else if (tmp.getHeight() > tdBean
                                            .getHeight())
                                    {
                                        tmp = tdBean;
                                    }
                                }
                            }
                            break;
                        }
                    }
                }
                if (null != tmp)
                {
                    ret = new PointObject();
                    ret.setX(endPoint.getX());
                    ret.setY(tmp.getLeftTop().getY()
                            + Constants.TABLE_DEFAULT_DRAGINTERVAL);
                }
                else
                {
                    ret = new PointObject(endPoint);
                }
            }
        }
        return ret;
    }
    
    /**
     * diagnose can add tr above or not
     * 
     * @param tdBean
     * @return
     */
    public boolean diagnoseAddTrAbove(TdBean tdBean)
    {
        analyseTableHeight();
        if (positionTop - Constants.TABLE_DEFAULT_HEIGHT < Constants.A4_DOC_TOP)
        {
            return false;
        }
        return true;
    }
    
    /**
     * diagnose can add tr below or not
     * 
     * @param tdBean
     * @return
     */
    public boolean diagnoseAddTrBelow(TdBean tdBean)
    {
        analyseTableHeight();
        if (positionTop + height + Constants.TABLE_DEFAULT_HEIGHT > Constants.A4_DOC_BOTTOM)
        {
            return false;
        }
        return true;
    }
    
    /**
     * add the tr above
     * 
     * @param tdBean
     */
    public void addTrAbove(TdBean tdBean)
    {
        tdBean = new TdBean(tdBean);
        analyseTableHeight();
        List td2Add = new ArrayList();
        for (int i = 0; i < tdList.size(); i++)
        {
            TdBean tmp = (TdBean) tdList.get(i);
            
            if (tdBean.getLeftTop().getY() <= positionTop)
            {
                if (tmp.getLeftTop().getY() == tdBean.getLeftTop().getY())
                {
                    td2Add.add(new TdBean(tmp.getWidth(),
                            Constants.TABLE_DEFAULT_HEIGHT, tmp.getLeftTop()
                                    .getX(), tmp.getLeftTop().getY()
                                    - Constants.TABLE_DEFAULT_HEIGHT, this));
                }
            }
            else
            {
                if (tmp.getLeftBottom().getY() == tdBean.getLeftTop().getY())
                {
                    td2Add.add(new TdBean(tmp.getWidth(),
                            Constants.TABLE_DEFAULT_HEIGHT, tmp.getLeftBottom()
                                    .getX(), tmp.getLeftBottom().getY()
                                    - Constants.TABLE_DEFAULT_HEIGHT, this));
                    tmp.setPositionTop(tmp.getPositionTop()
                            - Constants.TABLE_DEFAULT_HEIGHT);
                }
                else if (tmp.getLeftBottom().getY() < tdBean.getLeftTop()
                        .getY())
                {
                    tmp.setPositionTop(tmp.getPositionTop()
                            - Constants.TABLE_DEFAULT_HEIGHT);
                }
                else if (tmp.getLeftBottom().getY() > tdBean.getLeftTop()
                        .getY()
                        && tmp.getLeftTop().getY() < tdBean.getLeftTop().getY())
                {
                    tmp.setPositionTop(tmp.getPositionTop()
                            - Constants.TABLE_DEFAULT_HEIGHT);
                    tmp.setHeight(tmp.getHeight()
                            + Constants.TABLE_DEFAULT_HEIGHT);
                }
            }
        }
        
        for (int i = 0; i < td2Add.size(); i++)
        {
            tdList.add(td2Add.get(i));
        }
        analyseTableHeight();
    }
    
    /**
     * 
     * add the tr bellow
     * 
     * @param tdBean
     */
    public void addTrBelow(TdBean tdBean)
    {
        tdBean = new TdBean(tdBean);
        analyseTableHeight();
        List td2Add = new ArrayList();
        for (int i = 0; i < tdList.size(); i++)
        {
            TdBean tmp = (TdBean) tdList.get(i);
            
            if (tdBean.getLeftBottom().getY() >= positionTop + height)
            {
                if (tmp.getLeftBottom().getY() == tdBean.getLeftBottom().getY())
                {
                    td2Add.add(new TdBean(tmp.getWidth(),
                            Constants.TABLE_DEFAULT_HEIGHT, tmp.getLeftBottom()
                                    .getX(), tmp.getLeftBottom().getY(), this));
                }
            }
            else
            {
                if (tmp.getLeftTop().getY() == tdBean.getLeftBottom().getY())
                {
                    td2Add.add(new TdBean(tmp.getWidth(),
                            Constants.TABLE_DEFAULT_HEIGHT, tmp.getLeftTop()
                                    .getX(), tmp.getLeftTop().getY(), this));
                    tmp.setPositionTop(tmp.getPositionTop()
                            + Constants.TABLE_DEFAULT_HEIGHT);
                }
                else if (tmp.getLeftTop().getY() > tdBean.getLeftBottom()
                        .getY())
                {
                    tmp.setPositionTop(tmp.getPositionTop()
                            + Constants.TABLE_DEFAULT_HEIGHT);
                }
                else if (tmp.getLeftBottom().getY() > tdBean.getLeftBottom()
                        .getY()
                        && tmp.getLeftTop().getY() < tdBean.getLeftBottom()
                                .getY())
                {
                    tmp.setHeight(tmp.getHeight()
                            + Constants.TABLE_DEFAULT_HEIGHT);
                }
            }
        }
        
        for (int i = 0; i < td2Add.size(); i++)
        {
            tdList.add(td2Add.get(i));
        }
        analyseTableHeight();
    }
    
    /**
     * @param tdBean
     * @return
     */
    public boolean diagnoseTrDelete(TdBean tdBean)
    {
        tdBean = new TdBean(tdBean);
        for (int i = 0; i < tdList.size(); i++)
        {
            TdBean td = (TdBean) tdList.get(i);
            if (td.getLeftTop().getY() <= tdBean.getLeftTop().getY()
                    && td.getLeftBottom().getY() >= tdBean.getLeftBottom()
                            .getY())
            {
                if (td.getHeight() - tdBean.getHeight() > 0
                        && td.getHeight() - tdBean.getHeight() < Constants.TABLE_DEFAULT_DRAGINTERVAL)
                {
                    return false;
                }
            }
            else if (td.getLeftTop().getY() > tdBean.getLeftTop().getY()
                    && td.getLeftTop().getY() < tdBean.getLeftBottom().getY()
                    && td.getLeftBottom().getY() >= tdBean.getLeftBottom()
                            .getY())
            {
                if (td.getHeight()
                        - (tdBean.getLeftBottom().getY() - td.getLeftTop()
                                .getY()) > 0
                        && td.getHeight()
                                - (tdBean.getLeftBottom().getY() - td
                                        .getLeftTop().getY()) < Constants.TABLE_DEFAULT_DRAGINTERVAL)
                {
                    return false;
                }
            }
            else if (td.getLeftTop().getY() <= tdBean.getLeftTop().getY()
                    && td.getLeftBottom().getY() < tdBean.getLeftBottom()
                            .getY()
                    && td.getLeftBottom().getY() > tdBean.getLeftTop().getY())
            {
                if (td.getHeight()
                        - (td.getLeftBottom().getY() - tdBean.getLeftTop()
                                .getY()) > 0
                        && td.getHeight()
                                - (td.getLeftBottom().getY() - tdBean
                                        .getLeftTop().getY()) < Constants.TABLE_DEFAULT_DRAGINTERVAL)
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * delete the tr clicked
     * 
     * @param tdBean
     */
    public void deleteTr(TdBean tdBean)
    {
        tdBean = new TdBean(tdBean);
        for (int i = 0; i < tdList.size(); i++)
        {
            TdBean td = (TdBean) tdList.get(i);
            if (td.getLeftTop().getY() <= tdBean.getLeftTop().getY()
                    && td.getLeftBottom().getY() >= tdBean.getLeftBottom()
                            .getY())
            {
                td.setHeight(td.getHeight() - tdBean.getHeight());
            }
            else if (td.getLeftTop().getY() > tdBean.getLeftTop().getY()
                    && td.getLeftTop().getY() < tdBean.getLeftBottom().getY()
                    && td.getLeftBottom().getY() >= tdBean.getLeftBottom()
                            .getY())
            {
                td.setHeight(td.getHeight()
                        - (tdBean.getLeftBottom().getY() - td.getLeftTop()
                                .getY()));
                td.setPositionTop(tdBean.getLeftTop().getY());
            }
            else if (td.getLeftTop().getY() <= tdBean.getLeftTop().getY()
                    && td.getLeftBottom().getY() < tdBean.getLeftBottom()
                            .getY()
                    && td.getLeftBottom().getY() > tdBean.getLeftTop().getY())
            {
                td.setHeight(td.getHeight()
                        - (td.getLeftBottom().getY() - tdBean.getLeftTop()
                                .getY()));
            }
            else if (td.getLeftTop().getY() >= tdBean.getLeftTop().getY()
                    && td.getLeftBottom().getY() <= tdBean.getLeftBottom()
                            .getY())
            {
                td.setHeight(td.getHeight() - tdBean.getHeight());
            }
            else if (td.getLeftTop().getY() >= tdBean.getLeftBottom().getY())
            {
                td.setPositionTop(td.getPositionTop() - tdBean.getHeight());
            }
        }
        
        for (int i = 0; i < tdList.size(); i++)
        {
            TdBean tmp = (TdBean) tdList.get(i);
            if (tmp.getHeight() <= 0)
            {
                tdList.remove(i);
                i--;
            }
        }
    }
    
    /**
     * move the table in y direction
     * 
     * @param endPoint
     */
    public void moveTable(PointObject endPoint)
    {
        analyseTableHeight();
        int distancePlus = endPoint.getY() - this.positionTop;
        for (int i = 0; i < tdList.size(); i++)
        {
            TdBean tdBean = (TdBean) tdList.get(i);
            tdBean.setPositionTop(tdBean.getPositionTop() + distancePlus);
        }
        analyseTableHeight();
    }
    
    /**
     * diagnose the cursor can select the whole table
     * 
     * @param endPoint
     * @return
     */
    public boolean diagnoseTabelLeftTop(PointObject endPoint)
    {
        parseLeftTopRightBottom(tdList);
        if (!tdList.isEmpty())
        {
            TdBean tdBean = (TdBean) tdList.get(0);
            if (endPoint.getX() >= tdBean.getLeftTop().getX() - 2
                    && endPoint.getX() <= tdBean.getLeftTop().getX() + 2
                    && endPoint.getY() >= tdBean.getLeftTop().getY() - 2
                    && endPoint.getY() <= tdBean.getLeftTop().getY() + 2)
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * diagnose the cursor can select the whole table
     * 
     * @param endPoint
     * @return
     */
    public boolean diagnoseTabelRightBottom(PointObject endPoint)
    {
        parseLeftTopRightBottom(tdList);
        if (!tdList.isEmpty())
        {
            TdBean tdBean = (TdBean) tdList.get(tdList.size() - 1);
            if (endPoint.getX() >= tdBean.getRightBottom().getX() - 2
                    && endPoint.getX() <= tdBean.getRightBottom().getX() + 2
                    && endPoint.getY() >= tdBean.getRightBottom().getY() - 2
                    && endPoint.getY() <= tdBean.getRightBottom().getY() + 2)
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * set the table leftTop and the table height
     */
    public void analyseTableHeight()
    {
        parseLeftTopRightBottom(tdList);
        if (!tdList.isEmpty())
        {
            int minY = ((TdBean) tdList.get(0)).getLeftTop().getY();
            int maxY =
                ((TdBean) tdList.get(tdList.size() - 1)).getRightBottom()
                        .getY();
            
            height = maxY - minY;
            positionTop = minY;
        }
    }
    
    /**
     * diagnose the table move area
     * 
     * @param endPoint
     * @return
     */
    public PointObject diagnoseMoveTable(PointObject endPoint)
    {
        analyseTableHeight();
        PointObject ret = null;
        if (endPoint.getY() < Constants.A4_DOC_TOP)
        {
            ret = new PointObject();
            ret.setX(endPoint.getX());
            ret.setY(Constants.A4_DOC_TOP);
        }
        else if (endPoint.getY() + height > Constants.A4_DOC_BOTTOM)
        {
            ret = new PointObject();
            ret.setX(endPoint.getX());
            ret.setY(Constants.A4_DOC_BOTTOM - height);
        }
        else
        {
            ret = new PointObject(endPoint);
        }
        return ret;
    }
    
    private void parseLeftTopRightBottom(List list2Sort)
    {
        if (null != list2Sort)
        {
            Collections.sort(list2Sort, new FormLeftTopComparator());
            if (list2Sort.size() > 1)
            {
                TdBean tdLeftTop = (TdBean) list2Sort.get(0);
                Collections.sort(list2Sort, new FormRightBottomComparator());
                TdBean tdRightBottom =
                    (TdBean) list2Sort.get(list2Sort.size() - 1);
                
                TdBean tdFirst = (TdBean) list2Sort.get(0);
                list2Sort.set(list2Sort.indexOf(tdLeftTop), tdFirst);
                list2Sort.set(0, tdLeftTop);
                
                TdBean tdLast = (TdBean) list2Sort.get(list2Sort.size() - 1);
                list2Sort.set(list2Sort.indexOf(tdRightBottom), tdLast);
                list2Sort.set(list2Sort.size() - 1, tdRightBottom);
            }
        }
    }
    
    /**
     * diagnose the row can split or not
     * 
     * @param rows
     * @return
     */
    public boolean diagnoseSplitRow(TdBean tdBean, int rowsSplit)
    {
        if (tdBean.getHeight() / rowsSplit < Constants.TABLE_DEFAULT_DRAGINTERVAL)
        {
            return false;
        }
        else
        {
            for (int i = 0; i < tdList.size(); i++)
            {
                TdBean tmp = (TdBean) tdList.get(i);
                if (tmp.getLeftBottom().getY() <= tdBean.getLeftTop().getY()
                        || tmp.getLeftTop().getY() >= tdBean.getLeftBottom()
                                .getY())
                {
                    continue;
                }
                else if ((tmp.getLeftTop().getY() > tdBean.getLeftTop().getY() && tmp
                        .getLeftTop().getY() < tdBean.getLeftBottom().getY())
                        || (tmp.getLeftBottom().getY() > tdBean.getLeftTop()
                                .getY() && tmp.getLeftBottom().getY() < tdBean
                                .getLeftBottom().getY()))
                {
                    return false;
                }
                else if (tmp.getLeftTop().getY() <= tdBean.getLeftTop().getY()
                        && tmp.getLeftBottom().getY() >= tdBean.getLeftBottom()
                                .getY() && tmp.getHeight() > tdBean.getHeight())
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * diagnose the column can split or not
     * 
     * @param tdBean
     * @param cols
     * @return
     */
    public boolean diagnoseSplitCol(TdBean tdBean, int colSplit)
    {
        if (tdBean.getWidth() / colSplit < Constants.TABLE_DEFAULT_DRAGINTERVAL)
        {
            return false;
        }
        else
        {
            for (int i = 0; i < tdList.size(); i++)
            {
                TdBean tmp = (TdBean) tdList.get(i);
                if (tmp.getRightTop().getX() <= tdBean.getLeftTop().getX()
                        || tmp.getLeftTop().getX() >= tdBean.getRightTop()
                                .getX())
                {
                    continue;
                }
                else if ((tmp.getLeftTop().getX() > tdBean.getLeftTop().getX() && tmp
                        .getLeftTop().getX() < tdBean.getRightTop().getX())
                        || (tmp.getRightTop().getX() > tdBean.getLeftTop()
                                .getX() && tmp.getRightTop().getX() < tdBean
                                .getRightTop().getX()))
                {
                    return false;
                }
                else if (tmp.getLeftTop().getX() <= tdBean.getLeftTop().getX()
                        && tmp.getRightTop().getX() >= tdBean.getRightTop()
                                .getX() && tmp.getWidth() > tdBean.getWidth())
                {
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * split row
     * 
     * @param tdBean
     * @param rows
     */
    public void splitRow(TdBean tdBean, int rowsSplit)
    {
        tdBean = new TdBean(tdBean);
        int _height = tdBean.getHeight() / rowsSplit;
        List tdList2Add = new ArrayList();
        for (int i = 0; i < tdList.size(); i++)
        {
            TdBean tmp = (TdBean) tdList.get(i);
            if (tmp.getLeftTop().getY() == tdBean.getLeftTop().getY()
                    && tmp.getLeftBottom().getY() == tdBean.getLeftBottom()
                            .getY())
            {
                tmp.setHeight(_height);
                for (int j = 0; j < rowsSplit; j++)
                {
                    if (j == 0)
                    {
                        continue;
                    }
                    int top = tmp.getLeftTop().getY() + j * _height;
                    int heightTmp = _height;
                    if (j == rowsSplit - 1 && rowsSplit - 1 > 0)
                    {
                        heightTmp =
                            tdBean.getHeight() - _height * (rowsSplit - 1);
                    }
                    tdList2Add.add(new TdBean(tmp.getWidth(), heightTmp, tmp
                            .getLeftTop().getX(), top, this));
                }
            }
        }
        for (int i = 0; i < tdList2Add.size(); i++)
        {
            tdList.add(tdList2Add.get(i));
        }
    }
    
    /**
     * split col
     * 
     * @param tdBean
     * @param cols
     */
    public void splitCol(TdBean tdBean, int colSplit)
    {
        tdBean = new TdBean(tdBean);
        int _width = tdBean.getHeight() / colSplit;
        List tdList2Add = new ArrayList();
        for (int i = 0; i < tdList.size(); i++)
        {
            TdBean tmp = (TdBean) tdList.get(i);
            if (tmp.getLeftTop().getX() == tdBean.getLeftTop().getX()
                    && tmp.getRightTop().getX() == tdBean.getRightTop().getX())
            {
                tmp.setWidth(_width);
                for (int j = 0; j < colSplit; j++)
                {
                    if (j == 0)
                    {
                        continue;
                    }
                    int left = tmp.getLeftTop().getX() + j * _width;
                    int widthTmp = _width;
                    if (j == colSplit - 1 && colSplit - 1 > 0)
                    {
                        widthTmp = tdBean.getWidth() - _width * (colSplit - 1);
                    }
                    tdList2Add.add(new TdBean(widthTmp, tmp.getHeight(), left,
                            tmp.getLeftTop().getY(), this));
                }
            }
        }
        for (int i = 0; i < tdList2Add.size(); i++)
        {
            tdList.add(tdList2Add.get(i));
        }
    }
    
    /**
     * gather the trList
     */
    private void parseTrGather()
    {
        trList.clear();
        parseTdSpan();
        Map topMap = new HashMap();
        for (int i = 0; i < topList.size(); i++)
        {
            topMap.put(topList.get(i), new ArrayList());
        }
        for (int i = 0; i < tdList.size(); i++)
        {
            TdBean tdBean = (TdBean) tdList.get(i);
            List tr = (List) topMap.get(tdBean.getLeftTop().getY());
            tr.add(tdBean);
        }
        for (int i = 0; i < topList.size(); i++)
        {
            List tr = (List) topMap.get(topList.get(i));
            if (!tr.isEmpty())
            {
                Collections.sort(tr, new FormLeftTopComparator());
                trList.add(tr);
            }
        }
    }
    
    /**
     * drag the whole table percentage
     * 
     * @param endPoint
     */
    public void dragWholeTableBottom(PointObject endPoint)
    {
        parseLastTrTd();
        analyseTableHeight();
        Map topMap = new HashMap();
        int minHeight = this.height;
        for (int i = 0; i < topList.size(); i++)
        {
            if (i == topList.size() - 1)
            {
                topMap.put(topList.get(i), this.positionTop + this.height
                        - Integer.parseInt(topList.get(i).toString()));
            }
            else
            {
                topMap.put(topList.get(i), Integer.parseInt(topList.get(i + 1)
                        .toString())
                        - Integer.parseInt(topList.get(i).toString()));
            }
            if (Integer.parseInt(topMap.get(topList.get(i)).toString()) < minHeight)
            {
                minHeight =
                    Integer.parseInt(topMap.get(topList.get(i)).toString());
            }
        }
        
        int newHeight =
            ((endPoint.getY() > Constants.A4_DOC_BOTTOM) ? (Constants.A4_DOC_BOTTOM)
                    : endPoint.getY())
                    - this.positionTop;
        newHeight = newHeight < 0 ? 0 : newHeight;
        if (newHeight > this.height)
        {
            float percentage = ((float) newHeight) / this.height;
            int heightSum = 0;
            for (int i = 0; i < topList.size(); i++)
            {
                if (i == topList.size() - 1)
                {
                    topMap.put(topList.get(i), newHeight - heightSum);
                }
                else
                {
                    int heightTmp =
                        Integer.parseInt(topMap.get(topList.get(i)).toString());
                    heightTmp *= percentage;
                    topMap.put(topList.get(i), heightTmp);
                    heightSum += heightTmp;
                }
            }
        }
        else if (newHeight < this.height)
        {
            float percentage = ((float) newHeight) / this.height;
            int heightSum = 0;
            if (minHeight * percentage < Constants.TABLE_DEFAULT_DRAGINTERVAL)
            {
                percentage =
                    ((float) Constants.TABLE_DEFAULT_DRAGINTERVAL) / minHeight;
            }
            for (int i = 0; i < topList.size(); i++)
            {
                if (i == topList.size() - 1)
                {
                    int heightTmp =
                        (int) (this.height * percentage) - heightSum;
                    heightTmp =
                        heightTmp < Constants.TABLE_DEFAULT_DRAGINTERVAL ? Constants.TABLE_DEFAULT_DRAGINTERVAL
                                : heightTmp;
                    topMap.put(topList.get(i), heightTmp);
                }
                else
                {
                    int heightTmp =
                        Integer.parseInt(topMap.get(topList.get(i)).toString());
                    heightTmp *= percentage;
                    heightTmp =
                        heightTmp < Constants.TABLE_DEFAULT_DRAGINTERVAL ? Constants.TABLE_DEFAULT_DRAGINTERVAL
                                : heightTmp;
                    topMap.put(topList.get(i), heightTmp);
                    heightSum += heightTmp;
                }
            }
        }
        
        for (int i = 0; i < tdList.size(); i++)
        {
            TdBean tdBean = (TdBean) tdList.get(i);
            int topTmp = this.positionTop;
            int heightTmp = 0;
            for (int j = 0; j < topList.size(); j++)
            {
                if (tdBean.getLeftTop().getY() > Integer.parseInt(topList
                        .get(j).toString()))
                {
                    topTmp +=
                        Integer.parseInt(topMap.get(topList.get(j)).toString());
                }
                else if (tdBean.getLeftTop().getY() <= Integer.parseInt(topList
                        .get(j).toString())
                        && tdBean.getLeftBottom().getY() > Integer
                                .parseInt(topList.get(j).toString()))
                {
                    heightTmp +=
                        Integer.parseInt(topMap.get(topList.get(j)).toString());
                }
                else if (tdBean.getLeftBottom().getY() >= Integer
                        .parseInt(topList.get(j).toString()))
                {
                    break;
                }
            }
            tdBean.setPositionTop(topTmp);
            tdBean.setHeight(heightTmp);
        }
    }
    
    /**
     * drag the whole table percentage
     * 
     * @param startPoint
     * @param endPoint
     */
    public void dragWholeTableTop(PointObject startPoint, PointObject endPoint)
    {
        
    }
    
    /**
     * gather td from trList
     */
    public void parseTdFromHtml()
    {
        tdList.clear();
        // sort leftList and topList first
        List leftListTmp = new ArrayList();
        leftListTmp.add(getPositionLeft());
        for (int i = 0; i < leftList.size(); i++)
        {
            int leftTmp = Integer.parseInt(leftList.get(i).toString());
            int leftLast =
                Integer.parseInt(leftListTmp.get(leftListTmp.size() - 1)
                        .toString());
            leftListTmp.add(leftLast + leftTmp);
        }
        leftList = leftListTmp;
        List topListTmp = new ArrayList();
        topListTmp.add(getPositionTop());
        for (int i = 0; i < topList.size(); i++)
        {
            int topTmp = Integer.parseInt(topList.get(i).toString());
            int topLast =
                Integer.parseInt(topListTmp.get(topListTmp.size() - 1)
                        .toString());
            topListTmp.add(topLast + topTmp);
        }
        topList = topListTmp;
        
        // then set tdBean topPosition
        for (int i = 0; i < trList.size(); i++)
        {
            List tr = (List) trList.get(i);
            for (int j = 0; j < tr.size(); j++)
            {
                TdBean td = (TdBean) tr.get(j);
                td.setPositionTop(Integer.parseInt(topList.get(i).toString()));
                td.setHeight(Integer.parseInt(topList.get(i + td.getRowspan())
                        .toString())
                        - td.getPositionTop());
            }
        }
        
        int sumWidth = 0;
        // set the tdBean leftPosition
        for (int i = 0; i < trList.size(); i++)
        {
            List tr = (List) trList.get(i);
            if (i == 0)
            {
                int colSum = 0;
                for (int j = 0; j < tr.size(); j++)
                {
                    TdBean td = (TdBean) tr.get(j);
                    td.setPositionLeft(Integer.parseInt(leftList.get(colSum)
                            .toString()));
                    colSum += td.getColspan();
                    td.setWidth(Integer.parseInt(leftList.get(colSum)
                            .toString())
                            - td.getPositionLeft());
                    tdList.add(td);
                    sumWidth += td.getWidth();
                }
            }
            else
            {
                // get bottom border lines first
                Map bottomBorders = new HashMap();
                for (int j = 0; j < tdList.size(); j++)
                {
                    TdBean td = (TdBean) tdList.get(j);
                    if (td.getLeftBottom().getY() == Integer.parseInt(topList
                            .get(i).toString()))
                    {
                        bottomBorders.put(td.getLeftBottom().getX(),
                                new LineObject(td.getLeftBottom(), td
                                        .getRightBottom()));
                    }
                }
                List lines =
                    Arrays
                            .asList(bottomBorders.values().toArray(
                                    new Object[0]));
                Collections.sort(lines, new LongtitudeLineComparator());
                
                List finalLines = new ArrayList();
                LineObject tmpLine = null;
                for (int j = 0; j < lines.size(); j++)
                {
                    if (null == tmpLine)
                    {
                        tmpLine = (LineObject) lines.get(j);
                    }
                    else
                    {
                        LineObject tmp = (LineObject) lines.get(j);
                        if (tmpLine.getEnd().equals(tmp.getStart()))
                        {
                            tmpLine.setEnd(tmp.getEnd());
                        }
                        else
                        {
                            finalLines.add(tmpLine);
                            tmpLine = tmp;
                        }
                    }
                }
                if (null != tmpLine)
                {
                    finalLines.add(tmpLine);
                }
                
                // then put the td one by one
                for (int j = 0; j < tr.size(); j++)
                {
                    TdBean td = (TdBean) tr.get(j);
                    LineObject line = (LineObject) finalLines.get(0);
                    td.setPositionLeft(line.getStart().getX());
                    int m = 0;
                    for (; m < leftList.size(); m++)
                    {
                        if (Integer.parseInt(leftList.get(m).toString()) == td
                                .getPositionLeft())
                        {
                            break;
                        }
                    }
                    td.setWidth(Integer.parseInt(leftList.get(
                            m + td.getColspan()).toString())
                            - td.getPositionLeft());
                    tdList.add(td);
                    if (line.getLength() == td.getWidth())
                    {
                        finalLines.remove(0);
                    }
                    else
                    {
                        line.setStart(new PointObject(line.getStart().getX()
                                + td.getWidth(), line.getStart().getY()));
                    }
                }
            }
        }
        setWidth(sumWidth);
        analyseTableHeight();
    }
    
    @Override
    protected String tagContent()
    {
        analyseTableHeight();
        parseTrGather();
        String str = "";
        leftList.add(getPositionLeft() + getWidth());
        topList.add(getPositionTop() + getHeight());
        for (int i = 0; i < trList.size(); i++)
        {
            List tr = (List) trList.get(i);
            str += Constants.TRSPACEPREFIX + "<tr>" + Constants.LINE_SEPARATOR;
            for (int j = 0; j < tr.size(); j++)
            {
                TdBean td = (TdBean) tr.get(j);
                if (td.getPositionLeft() == getPositionLeft()
                        || td.getPositionTop() == getPositionTop())
                {
                    str +=
                        Constants.TDSPACEPREFIX
                                + "<td style=\""
                                + (td.getPositionLeft() == getPositionLeft() ? "border-left-width:1px;"
                                        : "")
                                + (td.getPositionTop() == getPositionTop() ? "border-top-width:1px;"
                                        : "")
                                + "\""
                                + (td.getColspan() <= 1 ? "" : (" colspan=\""
                                        + td.getColspan() + "\""))
                                + (td.getRowspan() <= 1 ? "" : (" rowspan=\""
                                        + td.getRowspan() + "\""))
                                + ">&nbsp;</td>" + Constants.LINE_SEPARATOR;
                }
                else
                {
                    str +=
                        Constants.TDSPACEPREFIX
                                + "<td"
                                + (td.getColspan() <= 1 ? "" : (" colspan=\""
                                        + td.getColspan() + "\""))
                                + (td.getRowspan() <= 1 ? "" : (" rowspan=\""
                                        + td.getRowspan() + "\""))
                                + ">&nbsp;</td>" + Constants.LINE_SEPARATOR;
                }
            }
            str +=
                Constants.TDSPACEPREFIX
                        + "<td style=\"visibility:hidden;height:"
                        + (Integer.parseInt(topList.get(i + 1).toString()) - Integer
                                .parseInt(topList.get(i).toString()))
                        + "px;\"></td>" + Constants.LINE_SEPARATOR;
            str += Constants.TRSPACEPREFIX + "</tr>" + Constants.LINE_SEPARATOR;
        }
        str +=
            Constants.TRSPACEPREFIX + "<tr style=\"visibility:hidden;\">"
                    + Constants.LINE_SEPARATOR;
        for (int i = 0; i < leftList.size() - 1; i++)
        {
            str +=
                Constants.TDSPACEPREFIX
                        + "<td style=\"width:"
                        + (Integer.parseInt(leftList.get(i + 1).toString())
                                - Integer.parseInt(leftList.get(i).toString()) - 1)
                        + "px;\"></td>" + Constants.LINE_SEPARATOR;
        }
        str +=
            Constants.TDSPACEPREFIX + "<td style=\"width:1px;\"></td>"
                    + Constants.LINE_SEPARATOR;
        str += Constants.TRSPACEPREFIX + "</tr>" + Constants.LINE_SEPARATOR;
        return str;
    }
    
    @Override
    protected String tagPrefix()
    {
        return super.tagPrefix()
                + "cellpadding=\"0\" cellspacing=\"0\" style=\"position:"
                + getPositionCss() + ";left:"
                + (getPositionLeft() - Constants.A4_LEFT) + "px;top:"
                + (getPositionTop() - Constants.A4_TOP) + "px;\" ";
    }
    
    public void phPaint(Graphics g)
    {
        for (int i = 0; i < tdList.size(); i++)
        {
            TdBean tdBean = (TdBean) tdList.get(i);
            tdBean.phPaint(g);
        }
    }
    
    public void phSelectPaint(Graphics g)
    {
        analyseTableHeight();
        Color oldColor = g.getColor();
        g.setColor(Color.BLUE);
        g.drawRect(getPositionLeft() - 1, getPositionTop() - 1, getWidth() + 2,
                getHeight() + 2);
        g.setColor(oldColor);
    }
    
    public List getTrList()
    {
        return trList;
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
    public void moveWholeObject(PointObject pointNow)
    {
    }
    
    public void cloneMe(FormObject formObj)
    {
        super.cloneMe(formObj);
        if (null != formObj)
        {
            TableBean table = (TableBean) formObj;
            this.columns = table.columns;
            this.rows = table.rows;
            FormUtil.copyList(this.leftList, table.leftList);
            FormUtil.copyList(this.topList, table.topList);
            //Collections.copy(this.leftList, table.leftList);
            //Collections.copy(this.topList, table.topList);
            for (int i = 0; i < table.tdList.size(); i++)
            {
                TdBean td = new TdBean();
                td.cloneMe((FormObject) table.tdList.get(i));
                td.setTableBean(this);
                this.tdList.add(td);
            }
        }
    }
    
    public void addTd(TdBean td)
    {
        this.tdList.add(td);
    }

    public PointObject getStandardPoint()
    {
        return standardPoint;
    }
    
    public boolean valideTable()
    {
        return !tdList.isEmpty();
    }

    public List getTdList()
    {
        return tdList;
    }
}
