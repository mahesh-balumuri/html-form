package com.phform.server.web.action;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;

public class ElementCallback extends ParserCallback
{
    private String htmlStr;
    
    private String retStr;
    
    private int lastPos;
    
    private String lastId;
    
    private String lastName;
    
    public ElementCallback(String htmlStr)
    {
        this.htmlStr = htmlStr;
        this.retStr = "";
        lastPos = 0;
    }
    
    @Override
    public void handleEndTag(HTML.Tag tag, int pos)
    {
        if (tag.equals(HTML.Tag.HTML))
        {
            retStr += htmlStr.substring(lastPos, htmlStr.length());
        }
        else if (tag.equals(HTML.Tag.TEXTAREA))
        {
            retStr += htmlStr.substring(lastPos, pos);
            // retStr += valueStr;
            retStr += lastId;
            lastPos = pos;
        }
    }
    
    @Override
    public void handleSimpleTag(HTML.Tag tag, MutableAttributeSet att, int pos)
    {
        if (tag.equals(HTML.Tag.INPUT))
        {
            String type =
                att.getAttribute(HTML.Attribute.TYPE).toString().trim();
            if (type.equals("text"))
            {
                setNameAndId(att);
                retStr +=
                    htmlStr.substring(lastPos, pos + "input".length() + 1);
                // setValue and ReadOnly
                retStr += " value=\"" + lastId + "\"";
                lastPos = pos + "input".length() + 1;
            }
            else if (type.equals("checkbox"))
            {
                setNameAndId(att);
                retStr +=
                    htmlStr.substring(lastPos, pos + "input".length() + 1);
                // setValue and ReadOnly
                retStr += " title=\"" + lastId + "\"";
                lastPos = pos + "input".length() + 1;
            }
        }
    }
    
    @Override
    public void handleStartTag(HTML.Tag tag, MutableAttributeSet att, int pos)
    {
        if (tag.equals(HTML.Tag.TEXTAREA))
        {
            setNameAndId(att);
        }
    }
    
    private void setNameAndId(MutableAttributeSet att)
    {
        this.lastId = att.getAttribute(HTML.Attribute.ID).toString().trim();
        this.lastName = att.getAttribute(HTML.Attribute.NAME).toString().trim();
    }
    
    public String getRetStr()
    {
        return retStr == null ? "" : retStr;
    }
}
