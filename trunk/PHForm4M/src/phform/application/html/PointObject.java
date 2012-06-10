package phform.application.html;

public class PointObject
{
    private int x;
    
    private int y;
    
    public PointObject()
    {
        x = 0;
        y = 0;
    }
    
    public PointObject(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
    
    public PointObject(PointObject point)
    {
        this.x = point.getX();
        this.y = point.getY();
    }
    
    public void setX(int x)
    {
        this.x = x;
    }
    
    public void setY(int y)
    {
        this.y = y;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        PointObject form = (PointObject) obj;
        return form.getX() == this.x && form.getY() == this.y;
    }
    
    public int getX()
    {
        return x;
    }
    
    public int getY()
    {
        return y;
    }

    @Override
    public String toString()
    {
        return "x=" + x + ",y=" + y;
    }
    
    
}
