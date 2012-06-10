package phform.application.canvas;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import phform.application.dialog.PureHtmlFormFontProperDialog;
import phform.application.frame.PureHtmlFormSingleFrame;
import phform.application.html.BaseObject;
import phform.application.html.Constants;
import phform.application.html.FormObject;
import phform.application.html.PointObject;
import phform.application.html.bean.CheckBoxBean;
import phform.application.html.bean.FontBean;
import phform.application.html.bean.TableBean;
import phform.application.html.bean.TdBean;
import phform.application.html.bean.TextAreaBean;
import phform.application.html.bean.TextBean;
import phform.application.html.util.FormUtil;
import phform.application.html.util.TdUtil;
import phform.application.popmenu.PureHtmlFormSinglePopMenu;
import phform.application.swing.PaintInterface;
import phform.application.toolbar.PureHtmlFormSingleToolBar;

public class PureHtmlFormSingleCanvas extends JPanel
{
    private PureHtmlFormSingleFrame mainFrame;
    
    private String filePath;
    
    private boolean saved;
    
    private String canvasName;
    
    // tables in this canvas
    private List tableList = new ArrayList();
    
    // other elements in this canvas
    private List htmlBeanList = new ArrayList();
    
    // elements selected
    private List beanSelected = new ArrayList();
    
    private BaseObject objDraged = null;
    
    private boolean ctrl = false;
    
    // cursor style now
    private int cursorStyle = 0;
    
    // 1:fontRec
    private int controlElement = 0;
    
    // mouse click start
    private PointObject startPoint = new PointObject(0, 0);
    
    // td list draged
    private List dragedTdLineList = new ArrayList();
    
    private PointObject lastPoint = new PointObject(-1, -1);
    
    private PointObject nowPoint = new PointObject(-1, -1);
    
    private PureHtmlFormSinglePopMenu popM = null;
    
    private PureHtmlFormSingleCanvasRedo reDoUnDo =
        new PureHtmlFormSingleCanvasRedo();
    
    public PureHtmlFormSingleCanvas(PureHtmlFormSingleFrame f)
    {
        this.mainFrame = f;
        addMouseMotionListener(new MouseMotionListener()
        {
            
            public void mouseDragged(MouseEvent e)
            {
                nowPoint.setX(e.getX());
                nowPoint.setY(e.getY());
                if (null != objDraged)
                {
                    if (objDraged instanceof TableBean)
                    {
                        switch (cursorStyle)
                        {
                        case Cursor.S_RESIZE_CURSOR:
                        case Cursor.W_RESIZE_CURSOR:
                        case Cursor.MOVE_CURSOR:
                        case Cursor.SE_RESIZE_CURSOR:
                            repaint();
                            lastPoint.setX(nowPoint.getX());
                            lastPoint.setY(nowPoint.getY());
                            break;
                        default:
                        }
                        return;
                    }
                    else
                    {
                        switch (cursorStyle)
                        {
                        case Cursor.S_RESIZE_CURSOR:
                        case Cursor.W_RESIZE_CURSOR:
                        case Cursor.MOVE_CURSOR:
                        case Cursor.SE_RESIZE_CURSOR:
                            repaint();
                            lastPoint.setX(nowPoint.getX());
                            lastPoint.setY(nowPoint.getY());
                            break;
                        default:
                        }
                        return;
                    }
                }
                else
                {
                    if (cursorStyle == Cursor.CROSSHAIR_CURSOR)
                    {
                        switch (controlElement)
                        {
                        case Constants.FONTELEMENT:
                        case Constants.CHECKBOXELEMENT:
                        case Constants.TEXTAREAELEMENT:
                        case Constants.TEXTELEMENT:
                            repaint();
                            lastPoint.setX(nowPoint.getX());
                            lastPoint.setY(nowPoint.getY());
                            break;
                        default:
                        }
                        return;
                    }
                }
            }
            
            public void mouseMoved(MouseEvent e)
            {
                // not the jtogglebutton first
                if (cursorStyle != Cursor.CROSSHAIR_CURSOR)
                {
                    PointObject pointNow =
                        translatePoint(new PointObject(e.getX(), e.getY()));
                    
                    // diagnose selected bean first
                    if (beanSelected.size() == 1
                            && !(beanSelected.get(0) instanceof TableBean))
                    {
                        FormObject formObj = (FormObject) beanSelected.get(0);
                        if (formObj.diagnoseMoveCursorInner(pointNow))
                        {
                            setCursor(new Cursor(Cursor.MOVE_CURSOR));
                            cursorStyle = Cursor.MOVE_CURSOR;
                            objDraged = formObj;
                            return;
                        }
                        else
                        {
                            int direction =
                                formObj.getCursorDirectionByMove(pointNow);
                            if (direction == 1)
                            {
                                setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
                                cursorStyle = Cursor.S_RESIZE_CURSOR;
                                objDraged = formObj;
                                return;
                            }
                            else if (direction == 2)
                            {
                                setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
                                cursorStyle = Cursor.W_RESIZE_CURSOR;
                                objDraged = formObj;
                                return;
                            }
                            else if (direction == 3)
                            {
                                setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
                                cursorStyle = Cursor.SE_RESIZE_CURSOR;
                                objDraged = formObj;
                                return;
                            }
                        }
                    }
                    
                    // not the selected bean
                    BaseObject tmp = null;
                    // then diagnose the html beans
                    for (int i = 0; i < htmlBeanList.size(); i++)
                    {
                        FormObject formObj = (FormObject) htmlBeanList.get(i);
                        if (formObj.diagnoseSelectInner(pointNow))
                        {
                            tmp = formObj;
                            break;
                        }
                    }
                    
                    if ((null != tmp && null == objDraged)
                            || (null != tmp && null != objDraged && !tmp
                                    .equals(objDraged)))
                    {
                        setCursor(Cursor.getDefaultCursor());
                        cursorStyle = Cursor.getDefaultCursor().getType();
                        objDraged = tmp;
                        repaint();
                        return;
                    }
                    else if (null != tmp && null != objDraged
                            && tmp.equals(objDraged))
                    {
                        setCursor(Cursor.getDefaultCursor());
                        cursorStyle = Cursor.getDefaultCursor().getType();
                        return;
                    }
                    else
                    {
                        if (null == tmp && null != objDraged
                                && !(objDraged instanceof TableBean))
                        {
                            setCursor(Cursor.getDefaultCursor());
                            cursorStyle = Cursor.getDefaultCursor().getType();
                            objDraged = null;
                            repaint();
                            return;
                        }
                        
                        // diagnose the tableRec
                        for (int i = 0; i < tableList.size(); i++)
                        {
                            TableBean table = (TableBean) tableList.get(i);
                            if (table.diagnoseTabelLeftTop(pointNow))
                            {
                                setCursor(new Cursor(Cursor.MOVE_CURSOR));
                            }
                            else if (table.diagnoseTabelRightBottom(pointNow))
                            {
                                setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
                            }
                            else
                            {
                                // diagnose x||y coursors
                                int cursorDirection =
                                    table.getCursorDirectionByMove(pointNow);
                                // =
                                if (cursorDirection == 1)
                                {
                                    setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
                                }
                                // ||
                                else if (cursorDirection == 2)
                                {
                                    setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
                                }
                                else
                                {
                                    setCursor(Cursor.getDefaultCursor());
                                }
                            }
                            cursorStyle = getCursor().getType();
                            if (cursorStyle != Cursor.getDefaultCursor()
                                    .getType())
                            {
                                objDraged = table;
                                return;
                            }
                            else
                            {
                                objDraged = null;
                            }
                        }
                    }
                }
            }
            
        });
        addMouseListener(new MouseListener()
        {
            
            public void mouseClicked(MouseEvent e)
            {
            }
            
            public void mouseEntered(MouseEvent e)
            {
                requestFocus();
                switch (getCursorStyle())
                {
                case Cursor.CROSSHAIR_CURSOR:
                    if (controlElement == Constants.FONTELEMENT
                            || controlElement == Constants.CHECKBOXELEMENT
                            || controlElement == Constants.TEXTAREAELEMENT
                            || controlElement == Constants.TEXTELEMENT)
                    {
                        setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
                    }
                    break;
                default:
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
            
            public void mouseExited(MouseEvent e)
            {
                
            }
            
            public void mousePressed(MouseEvent e)
            {
                startPoint.setX(e.getX());
                startPoint.setY(e.getY());
                switch (cursorStyle)
                {
                case Cursor.S_RESIZE_CURSOR:
                case Cursor.W_RESIZE_CURSOR:
                    if (null != objDraged)
                    {
                        if (objDraged instanceof TableBean)
                        {
                            TableBean tableBean = (TableBean) objDraged;
                            if (ctrl)
                            {
                                dragedTdLineList =
                                    tableBean
                                            .getMaxDragedTdLineByClick(
                                                    translatePoint(startPoint),
                                                    cursorStyle == Cursor.S_RESIZE_CURSOR ? 1
                                                            : 2);
                                startPoint.setX(restorePoint(
                                        tableBean.getStandardPoint()).getX());
                                startPoint.setY(restorePoint(
                                        tableBean.getStandardPoint()).getY());
                            }
                            else
                            {
                                dragedTdLineList =
                                    tableBean
                                            .getMiniDragedTdLineByClick(
                                                    translatePoint(startPoint),
                                                    cursorStyle == Cursor.S_RESIZE_CURSOR ? 1
                                                            : 2);
                                startPoint.setX(restorePoint(
                                        tableBean.getStandardPoint()).getX());
                                startPoint.setY(restorePoint(
                                        tableBean.getStandardPoint()).getY());
                            }
                        }
                    }
                    break;
                default:
                }
            }
            
            public void mouseReleased(MouseEvent e)
            {
                nowPoint.setX(e.getX());
                nowPoint.setY(e.getY());
                switch (cursorStyle)
                {
                case Cursor.DEFAULT_CURSOR:
                    if (e.isPopupTrigger())
                    {
                        if (null == popM)
                        {
                            popM = new PureHtmlFormSinglePopMenu(mainFrame);
                        }
                        popM.show(e.getComponent(), e.getX(), e.getY());
                    }
                    else
                    {
                        if (!ctrl)
                        {
                            beanSelected.clear();
                        }
                        if (null != objDraged)
                        {
                            if ((objDraged instanceof TableBean && cursorStyle == Cursor.MOVE_CURSOR)
                                    || !(objDraged instanceof TableBean))
                            {
                                if (beanSelected.indexOf(objDraged) != -1)
                                {
                                    beanSelected.remove(objDraged);
                                }
                                else
                                {
                                    beanSelected.add(objDraged);
                                }
                            }
                        }
                        else
                        {
                            for (int i = 0; i < tableList.size(); i++)
                            {
                                TableBean tableBean =
                                    (TableBean) tableList.get(i);
                                TdBean td =
                                    tableBean
                                            .getTdByClick(translatePoint(nowPoint));
                                if (null != td)
                                {
                                    if (beanSelected.indexOf(td) != -1)
                                    {
                                        beanSelected.remove(beanSelected
                                                .indexOf(td));
                                    }
                                    else
                                    {
                                        beanSelected.add(td);
                                    }
                                    break;
                                }
                            }
                        }
                        repaint();
                    }
                    break;
                case Cursor.S_RESIZE_CURSOR:
                case Cursor.W_RESIZE_CURSOR:
                case Cursor.MOVE_CURSOR:
                case Cursor.SE_RESIZE_CURSOR:
                    if (null != objDraged)
                    {
                        if (objDraged instanceof TableBean
                                && null != dragedTdLineList)
                        {
                            TableBean tableBean = (TableBean) objDraged;
                            switch (cursorStyle)
                            {
                            case Cursor.MOVE_CURSOR:
                                if (e.isPopupTrigger())
                                {
                                    if (null == popM)
                                    {
                                        popM =
                                            new PureHtmlFormSinglePopMenu(
                                                    mainFrame);
                                    }
                                    if (beanSelected.size() == 1)
                                    {
                                        if (beanSelected.get(beanSelected
                                                .size() - 1) instanceof TableBean
                                                && objDraged
                                                        .equals(beanSelected
                                                                .get(beanSelected
                                                                        .size() - 1)))
                                        {
                                            popM.show(e.getComponent(), e
                                                    .getX(), e.getY());
                                        }
                                    }
                                }
                                else
                                {
                                    if (!ctrl)
                                    {
                                        beanSelected.clear();
                                    }
                                    if (beanSelected.indexOf(objDraged) != -1)
                                    {
                                        beanSelected.remove(objDraged);
                                    }
                                    else
                                    {
                                        beanSelected.add(objDraged);
                                    }
                                }
                                PointObject pot =
                                    tableBean
                                            .diagnoseMoveTable(translatePoint(nowPoint));
                                if (null != pot)
                                {
                                    tableBean.moveTable(pot);
                                    setSaved(false);
                                }
                                break;
                            case Cursor.SE_RESIZE_CURSOR:
                                tableBean
                                        .dragWholeTableBottom(translatePoint(nowPoint));
                                setSaved(false);
                                break;
                            case Cursor.S_RESIZE_CURSOR:
                            case Cursor.W_RESIZE_CURSOR:
                                PointObject po =
                                    tableBean
                                            .diagnoseDragTdBorder(
                                                    dragedTdLineList,
                                                    translatePoint(nowPoint),
                                                    cursorStyle == Cursor.S_RESIZE_CURSOR ? 1
                                                            : 2);
                                if (null != po)
                                {
                                    tableBean
                                            .dragTdBorder(
                                                    translatePoint(startPoint),
                                                    po,
                                                    dragedTdLineList,
                                                    cursorStyle == Cursor.S_RESIZE_CURSOR ? 1
                                                            : 2);
                                    setSaved(false);
                                }
                                break;
                            default:
                            }
                        }
                        else if (!(objDraged instanceof TableBean))
                        {
                            FormObject bean = (FormObject) objDraged;
                            switch (cursorStyle)
                            {
                            case Cursor.MOVE_CURSOR:
                                if (e.isPopupTrigger())
                                {
                                    if (null == popM)
                                    {
                                        popM =
                                            new PureHtmlFormSinglePopMenu(
                                                    mainFrame);
                                    }
                                    if (beanSelected.size() == 1)
                                    {
                                        if (objDraged.equals(beanSelected
                                                .get(beanSelected.size() - 1)))
                                        {
                                            popM.show(e.getComponent(), e
                                                    .getX(), e.getY());
                                        }
                                    }
                                }
                                else
                                {
                                    PointObject initPoint =
                                        new PointObject(translatePoint(
                                                startPoint).getX(),
                                                translatePoint(startPoint)
                                                        .getY());
                                    initPoint =
                                        FormUtil
                                                .calcLeftTop(
                                                        initPoint,
                                                        translatePoint(
                                                                startPoint)
                                                                .getX()
                                                                - bean
                                                                        .getPositionLeft(),
                                                        translatePoint(
                                                                startPoint)
                                                                .getY()
                                                                - bean
                                                                        .getPositionTop());
                                    
                                    PointObject afterPoint =
                                        new PointObject(
                                                translatePoint(nowPoint).getX(),
                                                translatePoint(nowPoint).getY());
                                    afterPoint =
                                        FormUtil
                                                .calcLeftTop(
                                                        afterPoint,
                                                        translatePoint(
                                                                startPoint)
                                                                .getX()
                                                                - bean
                                                                        .getPositionLeft(),
                                                        translatePoint(
                                                                startPoint)
                                                                .getY()
                                                                - bean
                                                                        .getPositionTop());
                                    bean.moveWholeObject(afterPoint, initPoint);
                                    
                                    setSaved(false);
                                    if (bean instanceof FontBean)
                                    {
                                        ((FontBean) bean).parseFontPosition();
                                    }
                                }
                                break;
                            case Cursor.SE_RESIZE_CURSOR:
                                bean.dragRightBottom(translatePoint(nowPoint));
                                setSaved(false);
                                if (bean instanceof FontBean)
                                {
                                    ((FontBean) bean).parseFontPosition();
                                }
                                break;
                            case Cursor.S_RESIZE_CURSOR:
                                bean.dragBottomBorder(translatePoint(nowPoint));
                                setSaved(false);
                                if (bean instanceof FontBean)
                                {
                                    ((FontBean) bean).parseFontPosition();
                                }
                                break;
                            case Cursor.W_RESIZE_CURSOR:
                                bean.dragRightBorder(translatePoint(nowPoint));
                                setSaved(false);
                                if (bean instanceof FontBean)
                                {
                                    ((FontBean) bean).parseFontPosition();
                                }
                                break;
                            default:
                            }
                        }
                    }
                    setCursor(Cursor.getDefaultCursor());
                    cursorStyle = getCursor().getType();
                    
                    repaint();
                    break;
                case Cursor.CROSSHAIR_CURSOR:
                    mainFrame.getPhToolBar().toggleAllBut();
                    if (controlElement == Constants.FONTELEMENT)
                    {
                        if (nowPoint.getX() > startPoint.getX()
                                && nowPoint.getY() > startPoint.getY()
                                && FormUtil
                                        .diagnoseInnerDoc(translatePoint(startPoint))
                                && FormUtil
                                        .diagnoseInnerDoc(translatePoint(nowPoint)))
                        {
                            PureHtmlFormSingleFrame frame = mainFrame;
                            ((PureHtmlFormSingleToolBar) frame.getPhToolBar())
                                    .toggleStringBut(false);
                            
                            FontBean fontBean =
                                new FontBean(nowPoint.getX()
                                        - startPoint.getX(), nowPoint.getY()
                                        - startPoint.getY(), translatePoint(
                                        startPoint).getX(), translatePoint(
                                        startPoint).getY());
                            objDraged = fontBean;
                            htmlBeanList.add(fontBean);
                            beanSelected.add(fontBean);
                            setSaved(false);
                            PureHtmlFormFontProperDialog fontDialog =
                                new PureHtmlFormFontProperDialog(frame,
                                        fontBean);
                            fontDialog.show();
                        }
                        else
                        {
                            objDraged = null;
                        }
                    }
                    else if (controlElement == Constants.CHECKBOXELEMENT)
                    {
                        if (nowPoint.getX() > startPoint.getX()
                                && nowPoint.getY() > startPoint.getY()
                                && FormUtil
                                        .diagnoseInnerDoc(translatePoint(startPoint))
                                && FormUtil
                                        .diagnoseInnerDoc(translatePoint(nowPoint)))
                        {
                            CheckBoxBean checkBox =
                                new CheckBoxBean(translatePoint(startPoint)
                                        .getX(), translatePoint(startPoint)
                                        .getY(), getThisCanvas());
                            objDraged = checkBox;
                            htmlBeanList.add(checkBox);
                            beanSelected.add(checkBox);
                            setSaved(false);
                        }
                    }
                    else if (controlElement == Constants.TEXTAREAELEMENT)
                    {
                        if (nowPoint.getX() > startPoint.getX()
                                && nowPoint.getY() > startPoint.getY()
                                && FormUtil
                                        .diagnoseInnerDoc(translatePoint(startPoint))
                                && FormUtil
                                        .diagnoseInnerDoc(translatePoint(nowPoint)))
                        {
                            TextAreaBean textArea =
                                new TextAreaBean(nowPoint.getX()
                                        - startPoint.getX(), nowPoint.getY()
                                        - startPoint.getY(), translatePoint(
                                        startPoint).getX(), translatePoint(
                                        startPoint).getY(), getThisCanvas());
                            objDraged = textArea;
                            htmlBeanList.add(textArea);
                            beanSelected.add(textArea);
                            setSaved(false);
                        }
                    }
                    else if (controlElement == Constants.TEXTELEMENT)
                    {
                        if (nowPoint.getX() > startPoint.getX()
                                && nowPoint.getY() > startPoint.getY()
                                && FormUtil
                                        .diagnoseInnerDoc(translatePoint(startPoint))
                                && FormUtil
                                        .diagnoseInnerDoc(translatePoint(nowPoint)))
                        {
                            TextBean textBean =
                                new TextBean(nowPoint.getX()
                                        - startPoint.getX(), translatePoint(
                                        startPoint).getX(), translatePoint(
                                        startPoint).getY());
                            objDraged = textBean;
                            htmlBeanList.add(textBean);
                            beanSelected.add(textBean);
                            setSaved(false);
                        }
                    }
                    setCursor(Cursor.getDefaultCursor());
                    cursorStyle = getCursor().getType();
                    
                    repaint();
                    break;
                default:
                }
            }
        });
        
        addKeyListener(new KeyListener()
        {
            
            public void keyPressed(KeyEvent e)
            {
                switch (e.getKeyCode())
                {
                case KeyEvent.VK_CONTROL:
                    ctrl = true;
                    break;
                }
            }
            
            public void keyReleased(KeyEvent e)
            {
                switch (e.getKeyCode())
                {
                case KeyEvent.VK_CONTROL:
                    ctrl = false;
                    break;
                case KeyEvent.VK_S:
                    if (ctrl)
                    {
                        mainFrame.getPhMenuBar().getFileMenu()
                                .getSaveMenuItem().doClick();
                        ctrl = false;
                    }
                    break;
                }
            }
            
            public void keyTyped(KeyEvent e)
            {
            }
            
        });
    }
    
    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        
        double nowWidth = getSize().getWidth();
        if (nowWidth > Constants.A4_SCREEN_WIDTH)
        {
            g.translate((int) (nowWidth - Constants.A4_SCREEN_WIDTH) / 2, 0);
        }
        else
        {
            g.translate(0, 0);
        }
        
        // ===================================
        setBackground(Color.WHITE);
        //
        FormUtil.paintBackground(g);
        //
        paintElements(g);
        
        if (null != lastPoint && null != nowPoint
                && !lastPoint.equals(new PointObject(-1, -1))
                && !nowPoint.equals(new PointObject(-1, -1)))
        {
            switch (cursorStyle)
            {
            // 1
            case Cursor.S_RESIZE_CURSOR:
                if (null != objDraged)
                {
                    if (objDraged instanceof TableBean)
                    {
                        PointObject lastPoint1 =
                            new PointObject(0, translatePoint(lastPoint).getY());
                        PointObject lastPoint2 =
                            new PointObject(Constants.A4_SCREEN_WIDTH,
                                    translatePoint(lastPoint).getY());
                        PointObject nowPoint1 =
                            new PointObject(0, translatePoint(nowPoint).getY());
                        PointObject nowPoint2 =
                            new PointObject(Constants.A4_SCREEN_WIDTH,
                                    translatePoint(nowPoint).getY());
                        FormUtil.paintStrokeLine(g, lastPoint1, lastPoint2,
                                nowPoint1, nowPoint2);
                    }
                    else if (objDraged instanceof FontBean)
                    {
                        FontBean bean = (FontBean) objDraged;
                        PointObject lastRect1 =
                            new PointObject(bean.getLeftTop());
                        PointObject lastRect2 =
                            new PointObject(bean.getRightBottom().getX(),
                                    translatePoint(lastPoint).getY());
                        PointObject nowRect1 = new PointObject(lastRect1);
                        PointObject nowRect2 =
                            new PointObject(bean.getRightBottom().getX(),
                                    translatePoint(nowPoint).getY());
                        FormUtil.paintStrokeRec(g, lastRect1, lastRect2,
                                nowRect1, nowRect2);
                    }
                    else
                    {
                        FormObject bean = (FormObject) objDraged;
                        PointObject lastRect1 =
                            new PointObject(bean.getLeftTop().getX() - 1, bean
                                    .getLeftTop().getY() - 1);
                        PointObject lastRect2 =
                            new PointObject(bean.getRightBottom().getX() + 1,
                                    translatePoint(lastPoint).getY() + 1);
                        PointObject nowRect1 = new PointObject(lastRect1);
                        PointObject nowRect2 =
                            new PointObject(bean.getRightBottom().getX() + 1,
                                    translatePoint(nowPoint).getY() + 1);
                        FormUtil.paintStrokeRec(g, lastRect1, lastRect2,
                                nowRect1, nowRect2);
                    }
                }
                break;
            // 2
            case Cursor.W_RESIZE_CURSOR:
                if (null != objDraged)
                {
                    if (objDraged instanceof TableBean)
                    {
                        PointObject lastPoint1 =
                            new PointObject(translatePoint(lastPoint).getX(), 0);
                        PointObject lastPoint2 =
                            new PointObject(translatePoint(lastPoint).getX(),
                                    Constants.A4_SCREEN_HEIGHT);
                        PointObject nowPoint1 =
                            new PointObject(translatePoint(nowPoint).getX(), 0);
                        PointObject nowPoint2 =
                            new PointObject(translatePoint(nowPoint).getX(),
                                    Constants.A4_SCREEN_HEIGHT);
                        FormUtil.paintStrokeLine(g, lastPoint1, lastPoint2,
                                nowPoint1, nowPoint2);
                    }
                    else if (objDraged instanceof FontBean)
                    {
                        FontBean bean = (FontBean) objDraged;
                        PointObject lastRect1 =
                            new PointObject(bean.getLeftTop());
                        PointObject lastRect2 =
                            new PointObject(translatePoint(lastPoint).getX(),
                                    bean.getRightBottom().getY());
                        PointObject nowRect1 = new PointObject(lastRect1);
                        PointObject nowRect2 =
                            new PointObject(translatePoint(nowPoint).getX(),
                                    bean.getRightBottom().getY());
                        FormUtil.paintStrokeRec(g, lastRect1, lastRect2,
                                nowRect1, nowRect2);
                    }
                    else
                    {
                        FormObject bean = (FormObject) objDraged;
                        PointObject lastRect1 =
                            new PointObject(bean.getLeftTop().getX() - 1, bean
                                    .getLeftTop().getY() - 1);
                        PointObject lastRect2 =
                            new PointObject(
                                    translatePoint(lastPoint).getX() + 1, bean
                                            .getRightBottom().getY() + 1);
                        PointObject nowRect1 = new PointObject(lastRect1);
                        PointObject nowRect2 =
                            new PointObject(
                                    translatePoint(nowPoint).getX() + 1, bean
                                            .getRightBottom().getY() + 1);
                        FormUtil.paintStrokeRec(g, lastRect1, lastRect2,
                                nowRect1, nowRect2);
                    }
                }
                break;
            case Cursor.MOVE_CURSOR:
                if (null != objDraged)
                {
                    if (objDraged instanceof TableBean)
                    {
                        TableBean bean = (TableBean) objDraged;
                        bean.analyseTableHeight();
                        PointObject lastRect =
                            new PointObject(Constants.A4_DOC_LEFT, translatePoint(
                                    lastPoint).getY());
                        PointObject nowRect =
                            new PointObject(Constants.A4_DOC_LEFT, translatePoint(
                                    nowPoint).getY());
                        FormUtil.paintStrokeRec(g, lastRect, nowRect, bean
                                .getWidth(), bean.getHeight());
                    }
                    else
                    {
                        FormObject bean = (FormObject) objDraged;
                        PointObject lastRect =
                            new PointObject(translatePoint(lastPoint).getX(),
                                    translatePoint(lastPoint).getY());
                        lastRect =
                            FormUtil.calcLeftTop(lastRect, translatePoint(
                                    startPoint).getX()
                                    - bean.getPositionLeft(), translatePoint(
                                    startPoint).getY()
                                    - bean.getPositionTop());
                        
                        PointObject nowRect =
                            new PointObject(translatePoint(nowPoint).getX(),
                                    translatePoint(nowPoint).getY());
                        nowRect =
                            FormUtil.calcLeftTop(nowRect, translatePoint(
                                    startPoint).getX()
                                    - bean.getPositionLeft(), translatePoint(
                                    startPoint).getY()
                                    - bean.getPositionTop());
                        
                        FormUtil.paintStrokeRec(g, lastRect, nowRect, bean
                                .getWidth(), bean.getHeight());
                    }
                }
                break;
            case Cursor.SE_RESIZE_CURSOR:
                if (null != objDraged)
                {
                    if (objDraged instanceof TableBean)
                    {
                        PointObject lastPoint1 =
                            new PointObject(0, translatePoint(lastPoint).getY());
                        PointObject lastPoint2 =
                            new PointObject(Constants.A4_SCREEN_WIDTH,
                                    translatePoint(lastPoint).getY());
                        PointObject nowPoint1 =
                            new PointObject(0, translatePoint(nowPoint).getY());
                        PointObject nowPoint2 =
                            new PointObject(Constants.A4_SCREEN_WIDTH,
                                    translatePoint(nowPoint).getY());
                        FormUtil.paintStrokeLine(g, lastPoint1, lastPoint2,
                                nowPoint1, nowPoint2);
                    }
                    else if (objDraged instanceof FontBean)
                    {
                        FontBean bean = (FontBean) objDraged;
                        PointObject lastRect1 =
                            new PointObject(bean.getLeftTop());
                        PointObject lastRect2 =
                            new PointObject(translatePoint(lastPoint).getX(),
                                    translatePoint(lastPoint).getY());
                        PointObject nowRect1 = new PointObject(lastRect1);
                        PointObject nowRect2 =
                            new PointObject(translatePoint(nowPoint).getX(),
                                    translatePoint(nowPoint).getY());
                        FormUtil.paintStrokeRec(g, lastRect1, lastRect2,
                                nowRect1, nowRect2);
                    }
                    else
                    {
                        FormObject bean = (FormObject) objDraged;
                        PointObject lastRect1 =
                            new PointObject(bean.getLeftTop().getX() - 1, bean
                                    .getLeftTop().getY() - 1);
                        PointObject lastRect2 =
                            new PointObject(
                                    translatePoint(lastPoint).getX() + 1,
                                    translatePoint(lastPoint).getY() + 1);
                        PointObject nowRect1 = new PointObject(lastRect1);
                        PointObject nowRect2 =
                            new PointObject(
                                    translatePoint(nowPoint).getX() + 1,
                                    translatePoint(nowPoint).getY() + 1);
                        FormUtil.paintStrokeRec(g, lastRect1, lastRect2,
                                nowRect1, nowRect2);
                    }
                }
                break;
            case Cursor.CROSSHAIR_CURSOR:
                if (controlElement == Constants.FONTELEMENT
                        || controlElement == Constants.CHECKBOXELEMENT
                        || controlElement == Constants.TEXTAREAELEMENT
                        || controlElement == Constants.TEXTELEMENT)
                {
                    PointObject lastRect1 =
                        new PointObject(translatePoint(startPoint).getX(),
                                translatePoint(startPoint).getY());
                    PointObject lastRect2 =
                        new PointObject(translatePoint(lastPoint).getX(),
                                translatePoint(lastPoint).getY());
                    PointObject nowRect1 =
                        new PointObject(translatePoint(startPoint).getX(),
                                translatePoint(startPoint).getY());
                    PointObject nowRect2 =
                        new PointObject(translatePoint(nowPoint).getX(),
                                translatePoint(nowPoint).getY());
                    FormUtil.paintStrokeRec(g, lastRect1, lastRect2, nowRect1,
                            nowRect2);
                }
                break;
            default:
            }
        }
    }
    
    @Override
    public Dimension getPreferredSize()
    {
        return new Dimension(Constants.A4_SCREEN_WIDTH,
                Constants.A4_SCREEN_HEIGHT);
    }
    
    public void update(Graphics g)
    {
        paint(g);
    }
    
    /**
     * translate the physicle point 2 logic point
     * 
     * @param point2Translate
     * @return
     */
    private PointObject translatePoint(PointObject point2Translate)
    {
        PointObject pointZero = new PointObject(0, 0);
        double nowWidth = getSize().getWidth();
        if (nowWidth > Constants.A4_SCREEN_WIDTH)
        {
            pointZero =
                new PointObject(
                        (int) (nowWidth - Constants.A4_SCREEN_WIDTH) / 2, 0);
        }
        PointObject pointNow =
            new PointObject(point2Translate.getX() - pointZero.getX(),
                    point2Translate.getY() - pointZero.getY());
        
        return pointNow;
    }
    
    private PointObject restorePoint(PointObject point2Restore)
    {
        PointObject pointZero = new PointObject(0, 0);
        double nowWidth = getSize().getWidth();
        if (nowWidth > Constants.A4_SCREEN_WIDTH)
        {
            pointZero =
                new PointObject(
                        (int) (nowWidth - Constants.A4_SCREEN_WIDTH) / 2, 0);
        }
        PointObject pointNow =
            new PointObject(point2Restore.getX() + pointZero.getX(),
                    point2Restore.getY() + pointZero.getY());
        
        return pointNow;
    }
    
    public List getBeanSelected()
    {
        return beanSelected;
    }
    
    public void setBeanSelected(List beanSelected)
    {
        this.beanSelected = beanSelected;
    }
    
    public void addTable(TableBean table)
    {
        if (null != table)
        {
            tableList.add(table);
            repaint();
        }
    }
    
    public void deleteTable(TableBean table)
    {
        if (null != table)
        {
            if (tableList.indexOf(table) != -1)
            {
                tableList.remove(tableList.indexOf(table));
            }
        }
    }
    
    public void deleteFormBean(FormObject formObj)
    {
        if (null != formObj)
        {
            if (htmlBeanList.indexOf(formObj) != -1)
            {
                htmlBeanList.remove(htmlBeanList.indexOf(formObj));
            }
        }
    }
    
    public String getCanvasName()
    {
        return canvasName == null ? "" : canvasName.trim();
    }
    
    public void setCanvasName(String canvasName)
    {
        this.canvasName = canvasName;
    }
    
    public void setCursorStyle(int cursorStyle)
    {
        this.cursorStyle = cursorStyle;
        if (cursorStyle != Cursor.DEFAULT_CURSOR)
        {
            objDraged = null;
            beanSelected.clear();
        }
    }
    
    public int getCursorStyle()
    {
        return cursorStyle;
    }
    
    public void setControlElement(int controlElement)
    {
        this.controlElement = controlElement;
    }
    
    private PureHtmlFormSingleCanvas getThisCanvas()
    {
        return this;
    }
    
    private void paintElements(Graphics g)
    {
        for (int i = 0; i < tableList.size(); i++)
        {
            TableBean table = (TableBean) tableList.get(i);
            table.phPaint(g);
        }
        for (int i = 0; i < htmlBeanList.size(); i++)
        {
            PaintInterface paintObject = (PaintInterface) htmlBeanList.get(i);
            paintObject.phPaint(g);
            if (FormUtil.getTextJustifyFlag())
            {
                if (htmlBeanList.get(i) instanceof FontBean)
                {
                    ((FontBean) htmlBeanList.get(i)).textJustify();
                }
            }
        }
        FormUtil.releaseTextJustify();
        for (int i = 0; i < beanSelected.size(); i++)
        {
            PaintInterface paintObject = (PaintInterface) beanSelected.get(i);
            paintObject.phSelectPaint(g);
        }
        // if (null != objDraged)
        // {
        // if (objDraged instanceof FontBean)
        // {
        // ((PaintInterface) objDraged).phSelectPaint(g);
        // }
        // }
    }
    
    public String tagHtml()
    {
        String str = FormUtil.gatherHtmlPrefix(getCanvasName());
        for (int i = 0; i < tableList.size(); i++)
        {
            str += ((BaseObject) tableList.get(i)).tagHtml();
        }
        for (int i = 0; i < htmlBeanList.size(); i++)
        {
            str += ((BaseObject) htmlBeanList.get(i)).tagHtml();
        }
        str += FormUtil.gatherHtmlPostfix();
        return str;
    }
    
    public void setHtmlBeanList(List htmlBeanList)
    {
        this.htmlBeanList = htmlBeanList;
    }
    
    public void setTableList(List tableList)
    {
        this.tableList = tableList;
    }
    
    public String getFilePath()
    {
        return filePath == null ? "" : filePath.trim();
    }
    
    public void setFilePath(String filePath)
    {
        this.filePath = filePath;
    }
    
    public boolean isSaved()
    {
        return saved;
    }
    
    public void setSaved(boolean saved)
    {
        this.saved = saved;
        if (!saved)
        {
            mainFrame.getRight().setTitleAt(0, getCanvasName() + "*");
            newOper();
        }
        else
        {
            mainFrame.getRight().setTitleAt(0, getCanvasName());
        }
    }
    
    public void autoGenerateInput()
    {
        mainFrame.getPhToolBar().toggleAllBut();
        beanSelected.clear();
        objDraged = null;
        ctrl = false;
        cursorStyle = 0;
        controlElement = 0;
        dragedTdLineList.clear();
        if (null != tableList && !tableList.isEmpty())
        {
            int beanSize = htmlBeanList.size();
            for (int i = 0; i < tableList.size(); i++)
            {
                TableBean t = (TableBean) tableList.get(i);
                if (null != t.getTdList() && !t.getTdList().isEmpty())
                {
                    for (int j = 0; j < t.getTdList().size(); j++)
                    {
                        TdBean td = (TdBean) t.getTdList().get(j);
                        if (td.getWidth() - 8 < Constants.FORM_OBJ_TEXTAREA_DRAGINTERVAL_W
                                || td.getHeight() - 8 < Constants.FORM_OBJ_TEXTAREA_DRAGINTERVAL_H)
                        {
                            continue;
                        }
                        boolean b = false;
                        for (int k = 0; k < beanSize; k++)
                        {
                            if (TdUtil.diagnoseConflict(
                                    (FormObject) htmlBeanList.get(k), td))
                            {
                                b = true;
                                break;
                            }
                        }
                        if (b)
                        {
                            continue;
                        }
                        TextAreaBean textArea =
                            new TextAreaBean(td.getWidth() - 8,
                                    td.getHeight() - 8,
                                    td.getPositionLeft() + 4, td
                                            .getPositionTop() + 4,
                                    getThisCanvas());
                        htmlBeanList.add(textArea);
                        beanSelected.add(textArea);
                    }
                }
            }
            if (beanSize != htmlBeanList.size())
            {
                setSaved(false);
                repaint();
            }
        }
    }
    
    public void centerJustify()
    {
        if (null != beanSelected && beanSelected.size() > 1
                && diagnoseAllSelected())
        {
            FormObject formObj = (FormObject) beanSelected.get(0);
            for (int i = 1; i < beanSelected.size(); i++)
            {
                ((FormObject) beanSelected.get(i)).centerJustify(formObj);
            }
            setSaved(false);
            repaint();
        }
    }
    
    public void leftJustify()
    {
        if (null != beanSelected && beanSelected.size() > 1
                && diagnoseAllSelected())
        {
            FormObject formObj = (FormObject) beanSelected.get(0);
            for (int i = 1; i < beanSelected.size(); i++)
            {
                ((FormObject) beanSelected.get(i)).leftJustify(formObj);
            }
            setSaved(false);
            repaint();
        }
    }
    
    public void rightJustify()
    {
        if (null != beanSelected && beanSelected.size() > 1
                && diagnoseAllSelected())
        {
            FormObject formObj = (FormObject) beanSelected.get(0);
            for (int i = 1; i < beanSelected.size(); i++)
            {
                ((FormObject) beanSelected.get(i)).rightJustify(formObj);
            }
            setSaved(false);
            repaint();
        }
    }
    
    public void middleJustify()
    {
        if (null != beanSelected && beanSelected.size() > 1
                && diagnoseAllSelected())
        {
            FormObject formObj = (FormObject) beanSelected.get(0);
            for (int i = 1; i < beanSelected.size(); i++)
            {
                ((FormObject) beanSelected.get(i)).middleJustify(formObj);
            }
            setSaved(false);
            repaint();
        }
    }
    
    public void topJustify()
    {
        if (null != beanSelected && beanSelected.size() > 1
                && diagnoseAllSelected())
        {
            FormObject formObj = (FormObject) beanSelected.get(0);
            for (int i = 1; i < beanSelected.size(); i++)
            {
                ((FormObject) beanSelected.get(i)).topJustify(formObj);
            }
            setSaved(false);
            repaint();
        }
    }
    
    public void bottomJustify()
    {
        if (null != beanSelected && beanSelected.size() > 1
                && diagnoseAllSelected())
        {
            FormObject formObj = (FormObject) beanSelected.get(0);
            for (int i = 1; i < beanSelected.size(); i++)
            {
                ((FormObject) beanSelected.get(i)).bottomJustify(formObj);
            }
            setSaved(false);
            repaint();
        }
    }
    
    public void textJustify()
    {
        if (null != beanSelected && !beanSelected.isEmpty()
                && diagnoseAllSelected())
        {
            boolean bool = false;
            for (int i = 0; i < beanSelected.size(); i++)
            {
                if (beanSelected.get(i) instanceof FontBean)
                {
                    ((FontBean) beanSelected.get(i)).textJustify();
                    bool = true;
                }
            }
            if (bool)
            {
                setSaved(false);
                repaint();
            }
        }
    }
    
    private void reDoUnDoReset()
    {
        this.saved = false;
        mainFrame.getRight().setTitleAt(0, getCanvasName() + "*");
        beanSelected.clear();
        objDraged = null;
        setCursorStyle(0);
        setControlElement(0);
        dragedTdLineList.clear();
        startPoint.setX(0);
        startPoint.setY(0);
        lastPoint.setX(0);
        lastPoint.setY(0);
        nowPoint.setX(0);
        nowPoint.setY(0);
        mainFrame.getPhToolBar().toggleAllBut();
    }
    
    public void redo()
    {
        if (reDoUnDo.canRedo())
        {
            reDoUnDoReset();
            RedoBean redo = reDoUnDo.redoOper();
            setTableList(redo.getTableList());
            setHtmlBeanList(redo.getHtmlBeanList());
            repaint();
            toggleReDoUnDoBut();
        }
    }
    
    private void toggleReDoUnDoBut()
    {
        if (reDoUnDo.canRedo())
        {
            mainFrame.getPhToolBar().enableRedoBut(true);
        }
        else
        {
            mainFrame.getPhToolBar().enableRedoBut(false);
        }
        if (reDoUnDo.canUndo())
        {
            mainFrame.getPhToolBar().enableUndoBut(true);
        }
        else
        {
            mainFrame.getPhToolBar().enableUndoBut(false);
        }
    }
    
    public void undo()
    {
        if (reDoUnDo.canUndo())
        {
            reDoUnDoReset();
            RedoBean redo = reDoUnDo.undoOper();
            setTableList(redo.getTableList());
            setHtmlBeanList(redo.getHtmlBeanList());
            repaint();
            toggleReDoUnDoBut();
        }
    }
    
    public void newOper()
    {
        reDoUnDo.newOper(this.tableList, this.htmlBeanList);
        toggleReDoUnDoBut();
    }
    
    /**
     * diagnose the selected list can justify or not
     * 
     * @return
     */
    private boolean diagnoseAllSelected()
    {
        boolean ret = true;
        for (int i = 0; i < beanSelected.size(); i++)
        {
            if (!(beanSelected.get(i) instanceof CheckBoxBean
                    || beanSelected.get(i) instanceof FontBean
                    || beanSelected.get(i) instanceof TextAreaBean || beanSelected
                    .get(i) instanceof TextBean))
            {
                ret = false;
                break;
            }
        }
        return ret;
    }
    
    public void removeInvalidTable()
    {
        for (int i = 0; i < tableList.size(); i++)
        {
            if (!((TableBean) tableList.get(i)).valideTable())
            {
                tableList.remove(i);
                return;
            }
        }
    }
}
