package phform.application.html.swing;

import java.awt.Graphics;

public abstract class PaintBean
{
    protected Object getPaintObject()
    {
        return checkInstance();
    }
    
    protected abstract Object checkInstance();
    
    protected abstract void phPaint(Graphics g);
}
