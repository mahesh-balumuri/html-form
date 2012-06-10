package phform.application.html;

import phform.application.html.util.FormUtil;

public abstract class FormObject extends BaseObject
{
    private String initialUUID = FormUtil.generateUUID();
    
    protected String name = Constants.IDPREFIX + initialUUID;
    
    protected String id = Constants.IDPREFIX + initialUUID;
    
    protected int width;
    
    protected int height;
    
    protected String positionCss = Constants.POSITION_CSS_ABSOLUTE;
    
    protected int positionLeft;
    
    protected int positionTop;
    
    @Override
    protected String tagPrefix()
    {
        return "name=\"" + getName() + "\" id=\"" + getId() + "\" ";
    }
    
    public int getHeight()
    {
        return height;
    }
    
    public void setHeight(int height)
    {
        this.height = height;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        FormObject formObject = (FormObject) obj;
        return super.equals(obj) && getName().equals(formObject.getName())
                && getId().equals(formObject.getId())
                && getPositionLeft() == formObject.getPositionLeft()
                && getPositionTop() == formObject.getPositionTop();
    }
    
    @Override
    public String toString()
    {
        StringBuffer buf = new StringBuffer();
        buf.append("name:" + getName());
        buf.append("id:" + getId());
        buf.append("width:" + getWidth());
        buf.append("height:" + getHeight());
        buf.append("positionCss:" + getPositionCss());
        buf.append("positionLeft:" + getPositionLeft());
        buf.append("positionTop:" + getPositionTop());
        return super.toString() + buf;
    }
    
    public FormObject()
    {
    }
    
    public FormObject(int objType)
    {
        super(objType);
    }
    
    public FormObject(int objType, String name, String id, int width,
            int height, String positionCss, int positionLeft, int positionTop)
    {
        this(objType);
        
        if (null != name && !"".equals(name.trim()))
        {
            this.name = name;
        }
        if (null != id && !"".equals(id.trim()))
        {
            this.id = id;
        }
        this.width = width;
        this.height = height;
        this.positionCss = positionCss;
        this.positionLeft = positionLeft;
        this.positionTop = positionTop;
    }
    
    public PointObject getLeftTop()
    {
        return new PointObject(this.positionLeft, this.positionTop);
    }
    
    public PointObject getRightTop()
    {
        return new PointObject(this.positionLeft + this.width, this.positionTop);
    }
    
    public PointObject getLeftBottom()
    {
        return new PointObject(this.positionLeft, this.positionTop
                + this.height);
    }
    
    public PointObject getRightBottom()
    {
        return new PointObject(this.positionLeft + this.width, this.positionTop
                + this.height);
    }
    
    public PointObject getCenterCenter()
    {
        return new PointObject(this.positionLeft + this.width / 2,
                this.positionTop + this.height / 2);
    }
    
    public int getArea()
    {
        return this.width * this.height;
    }
    
    public String getId()
    {
        return id == null ? "" : id.trim();
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name == null ? "" : name.trim();
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getPositionCss()
    {
        return positionCss == null ? Constants.POSITION_CSS_ABSOLUTE
                : positionCss.trim();
    }
    
    public void setPositionCss(String positionCss)
    {
        this.positionCss = positionCss;
    }
    
    public int getPositionLeft()
    {
        return positionLeft;
    }
    
    public void setPositionLeft(int positionLeft)
    {
        this.positionLeft = positionLeft;
    }
    
    public int getPositionTop()
    {
        return positionTop;
    }
    
    public void setPositionTop(int positionTop)
    {
        this.positionTop = positionTop;
    }
    
    public int getWidth()
    {
        return width;
    }
    
    public void setWidth(int width)
    {
        this.width = width;
    }
    
    protected int getMinDragedHeight()
    {
        return Constants.FORM_OBJ_DEFAULT_DRAGINTERVAL;
    }
    
    protected int getMinDragedWidth()
    {
        return Constants.FORM_OBJ_DEFAULT_DRAGINTERVAL;
    }
    
    /**
     * diagnose the cursor is in the bean or not
     * 
     * @param pointNow
     * @return
     */
    public boolean diagnoseSelectInner(PointObject pointNow)
    {
        if (null != pointNow)
        {
            if (pointNow.getX() > this.getLeftTop().getX()
                    && pointNow.getX() < this.getRightTop().getX()
                    && pointNow.getY() > this.getLeftTop().getY()
                    && pointNow.getY() < this.getLeftBottom().getY())
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * move the whole form object
     * 
     * @param pointNow
     */
    public void moveWholeObject(PointObject pointNow)
    {
        if (null != pointNow)
        {
            if (pointNow.getX() + this.width > Constants.A4_DOC_RIGHT)
            {
                this.positionLeft = Constants.A4_DOC_RIGHT - this.width;
            }
            else if (pointNow.getX() < Constants.A4_DOC_LEFT)
            {
                this.positionLeft = Constants.A4_DOC_LEFT;
            }
            else
            {
                this.positionLeft = pointNow.getX();
            }
            
            if (pointNow.getY() + this.height > Constants.A4_DOC_BOTTOM)
            {
                this.positionTop = Constants.A4_DOC_BOTTOM - this.height;
            }
            else if (pointNow.getY() < Constants.A4_DOC_TOP)
            {
                this.positionTop = Constants.A4_DOC_TOP;
            }
            else
            {
                this.positionTop = pointNow.getY();
            }
        }
    }
    
    public void moveWholeObject(PointObject pointNow, PointObject pointOld)
    {
        if (null != pointNow && null != pointOld)
        {
            int xDistance = pointNow.getX() - pointOld.getX();
            int yDistance = pointNow.getY() - pointOld.getY();
            int xLeftTop = this.positionLeft + xDistance;
            int yLeftTop = this.positionTop + yDistance;
            
            if (xLeftTop + this.width > Constants.A4_DOC_RIGHT)
            {
                this.positionLeft = Constants.A4_DOC_RIGHT - this.width;
            }
            else if (xLeftTop < Constants.A4_DOC_LEFT)
            {
                this.positionLeft = Constants.A4_DOC_LEFT;
            }
            else
            {
                this.positionLeft = xLeftTop;
            }
            
            if (yLeftTop + this.height > Constants.A4_DOC_BOTTOM)
            {
                this.positionTop = Constants.A4_DOC_BOTTOM - this.height;
            }
            else if (yLeftTop < Constants.A4_DOC_TOP)
            {
                this.positionTop = Constants.A4_DOC_TOP;
            }
            else
            {
                this.positionTop = yLeftTop;
            }
        }
    }
    
    /**
     * drag the form object's right border
     * 
     * @param pointNow
     */
    public void dragRightBorder(PointObject pointNow)
    {
        if (null != pointNow)
        {
            if (pointNow.getX() > Constants.A4_DOC_RIGHT)
            {
                this.width = Constants.A4_DOC_RIGHT - this.positionLeft;
            }
            else if (pointNow.getX() - this.positionLeft < getMinDragedWidth())
            {
                this.width = getMinDragedWidth();
            }
            else
            {
                this.width = pointNow.getX() - this.positionLeft;
            }
        }
    }
    
    /**
     * drag the form object's bottom border
     * 
     * @param pointNow
     */
    public void dragBottomBorder(PointObject pointNow)
    {
        if (null != pointNow)
        {
            if (pointNow.getY() > Constants.A4_DOC_BOTTOM)
            {
                this.height = Constants.A4_DOC_BOTTOM - this.positionTop;
            }
            else if (pointNow.getY() - this.positionTop < getMinDragedHeight())
            {
                this.height = getMinDragedHeight();
            }
            else
            {
                this.height = pointNow.getY() - this.positionTop;
            }
        }
    }
    
    /**
     * drag the right bottom point
     * 
     * @param pointNow
     */
    public void dragRightBottom(PointObject pointNow)
    {
        if (null != pointNow)
        {
            dragRightBorder(pointNow);
            dragBottomBorder(pointNow);
        }
    }
    
    /**
     * diagnose is the move cursor or not
     * 
     * @param pointNow
     * @return
     */
    public boolean diagnoseMoveCursor(PointObject pointNow)
    {
        if (null != pointNow)
        {
            if (pointNow.getX() >= this.getLeftTop().getX() - 2
                    && pointNow.getX() <= this.getLeftTop().getX()
                    && pointNow.getY() >= this.getLeftTop().getY()
                    && pointNow.getY() <= this.getLeftTop().getY() + 2)
            {
                return true;
            }
        }
        return false;
    }
    
    public boolean diagnoseMoveCursorInner(PointObject pointNow)
    {
        if (null != pointNow)
        {
            if (pointNow.getX() > this.getLeftTop().getX() + 2
                    && pointNow.getX() < this.getRightTop().getX() - 2
                    && pointNow.getY() > this.getLeftTop().getY() + 2
                    && pointNow.getY() < this.getLeftBottom().getY() - 2)
            {
                return true;
            }
        }
        return false;
    }
    
    /**
     * get the cursor direction(x or y) by mouse over the border
     * 
     * @param pointNow
     * @return 0:none 1:x like:= 2:y like:|| 3:rightBottom
     */
    public int getCursorDirectionByMove(PointObject pointNow)
    {
        int direction = 0;
        if (null != pointNow)
        {
            if (pointNow.getX() >= this.getRightBottom().getX() - 2
                    && pointNow.getX() <= this.getRightBottom().getX()
                    && pointNow.getY() >= this.getRightBottom().getY()
                    && pointNow.getY() <= this.getRightBottom().getY() + 2)
            {
                direction = 3;
            }
            else if (pointNow.getY() == this.getLeftBottom().getY()
                    && pointNow.getX() > this.getLeftBottom().getX()
                    && pointNow.getX() < this.getRightBottom().getX())
            {
                direction = 1;
            }
            else if (pointNow.getX() == this.getRightTop().getX()
                    && pointNow.getY() > this.getRightTop().getY()
                    && pointNow.getY() < this.getRightBottom().getY())
            {
                direction = 2;
            }
        }
        return direction;
    }
    
    /**
     * latitude center
     * 
     * @param pointCenter
     */
    public void centerJustify(FormObject objCenter)
    {
        this.positionLeft = objCenter.getCenterCenter().getX() - this.width / 2;
        if (this.positionLeft < Constants.A4_DOC_LEFT)
        {
            this.positionLeft = Constants.A4_DOC_LEFT;
        }
        else if (this.getRightTop().getX() > Constants.A4_DOC_RIGHT)
        {
            this.positionLeft = Constants.A4_DOC_RIGHT - this.width;
        }
    }
    
    public void leftJustify(FormObject objLeft)
    {
        this.positionLeft = objLeft.getPositionLeft();
        if (this.getRightTop().getX() > Constants.A4_DOC_RIGHT)
        {
            this.positionLeft = Constants.A4_DOC_RIGHT - this.width;
        }
    }
    
    public void rightJustify(FormObject objRight)
    {
        this.positionLeft = objRight.getRightTop().getX() - this.width;
        if (this.positionLeft < Constants.A4_DOC_LEFT)
        {
            this.positionLeft = Constants.A4_DOC_LEFT;
        }
    }
    
    public void middleJustify(FormObject objMiddle)
    {
        this.positionTop = objMiddle.getCenterCenter().getY() - this.height / 2;
        if (this.positionTop < Constants.A4_DOC_TOP)
        {
            this.positionTop = Constants.A4_DOC_TOP;
        }
        else if (this.getLeftBottom().getY() > Constants.A4_DOC_BOTTOM)
        {
            this.positionTop = Constants.A4_DOC_BOTTOM - this.height;
        }
    }
    
    public void topJustify(FormObject objTop)
    {
        this.positionTop = objTop.getPositionTop();
        if (this.getLeftBottom().getY() > Constants.A4_DOC_BOTTOM)
        {
            this.positionTop = Constants.A4_DOC_BOTTOM - this.height;
        }
    }
    
    public void bottomJustify(FormObject objBottom)
    {
        this.positionTop = objBottom.getLeftBottom().getY() - this.height;
        if (this.positionTop < Constants.A4_DOC_TOP)
        {
            this.positionTop = Constants.A4_DOC_TOP;
        }
    }
    
    public void cloneMe(FormObject formObj)
    {
        if (null != formObj)
        {
            this.objType = formObj.getObjType();
            this.id = formObj.getId();
            this.name = formObj.getName();
            this.positionCss = formObj.getPositionCss();
            this.positionLeft = formObj.getPositionLeft();
            this.positionTop = formObj.getPositionTop();
            this.width = formObj.getWidth();
            this.height = formObj.getHeight();
        }
    }
}
