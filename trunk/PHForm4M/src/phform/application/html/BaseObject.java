package phform.application.html;

public abstract class BaseObject
{
    protected int objType;
    
    // example:<input type="text"
    // example:<textarea name="example"
    protected abstract String tagPrefix();
    
    // example:/>
    // example:>
    protected String tagPrefixEnd()
    {
        // return ((Boolean) Constants.CLOSE_MAP.get(objType)).booleanValue() ?
        // ">"
        // : "/>";
        return ">";
    }
    
    protected abstract String tagContent();
    
    // example:(null)
    // example:</textarea>
    protected String tagPostfix()
    {
        return ((Boolean) Constants.CLOSE_MAP.get(objType)).booleanValue() ? ("</"
                + Constants.TYPE_MAP.get(objType) + ">")
                : "";
    }
    
    public String tagHtml()
    {
        return Constants.SPACE_MAP.get(objType) + tagPrefix() + tagPrefixEnd()
                + tagContent() + tagPostfix() + Constants.LINE_SEPARATOR;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        return objType == ((BaseObject) obj).getObjType();
    }
    
    @Override
    public String toString()
    {
        return "The object type:" + objType;
    }
    
    public BaseObject()
    {
    }
    
    public BaseObject(int objType)
    {
        this.objType = objType;
    }
    
    public int getObjType()
    {
        return objType;
    }
    
    public void setObjType(int objType)
    {
        this.objType = objType;
    }
}
