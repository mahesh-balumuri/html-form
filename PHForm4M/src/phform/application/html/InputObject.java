package phform.application.html;

public abstract class InputObject extends FormObject
{
    
    public InputObject()
    {
    }
    
    public InputObject(int objType)
    {
        super(objType);
    }
    
    @Override
    public boolean equals(Object obj)
    {
        return super.equals(obj);
    }
    
    @Override
    protected String tagPrefix()
    {
        return "<input type=\"" + Constants.TYPE_MAP.get(getObjType()) + "\" "
                + super.tagPrefix();
    }
    
    @Override
    protected String tagContent()
    {
        return "";
    }
    
}
