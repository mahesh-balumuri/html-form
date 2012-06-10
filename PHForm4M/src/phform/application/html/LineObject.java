package phform.application.html;

public class LineObject
{
    private PointObject start;
    
    private PointObject end;
    
    public LineObject(PointObject start, PointObject end)
    {
        this.start = start;
        this.end = end;
    }
    
    public LineObject(LineObject line)
    {
        this.start = new PointObject(line.getStart());
        this.end = new PointObject(line.getEnd());
    }
    
    public PointObject getEnd()
    {
        return end;
    }
    
    public PointObject getStart()
    {
        return start;
    }
    
    public int getLength()
    {
        if (start.getX() == end.getX())
        {
            return Math.abs(start.getY() - end.getY());
        }
        else if (start.getY() == end.getY())
        {
            return Math.abs(start.getX() - end.getX());
        }
        
        return (int) Math.sqrt(Math.pow(Math.abs(start.getX() - end.getX()), 2)
                + Math.pow(Math.abs(start.getY() - end.getY()), 2));
    }
    
    public void setEnd(PointObject end)
    {
        this.end = end;
    }

    public void setStart(PointObject start)
    {
        this.start = start;
    }

}
