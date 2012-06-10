package phform.application.html;

public abstract class OtherObject extends FormObject
{
    
    public OtherObject()
    {
    }
    
    public OtherObject(int objType)
    {
        super(objType);
    }
    
    public OtherObject(int objType, String name, String id, int width,
            int height, String positionCss, int positionLeft, int positionTop)
    {
        super(objType, name, id, width, height, positionCss, positionLeft,
                positionTop);
    }
    
    @Override
    protected String tagPrefix()
    {
        return "<" + Constants.TYPE_MAP.get(getObjType()) + " "
                + super.tagPrefix();
    }
    
}
