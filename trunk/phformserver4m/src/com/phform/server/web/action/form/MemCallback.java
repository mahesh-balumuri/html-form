package com.phform.server.web.action.form;

import java.util.Map;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;

import com.phform.server.Constants;

public class MemCallback extends ParserCallback
{
    private String htmlStr;
    
    private Map formElementType;
    
    private String htmlTitle;
    
    private int lastPos;
    
    private String lastId;
    
    private String lastName;
    
    private HTML.Tag startTag;
    
    public MemCallback(String htmlStr, Map formElementType)
    {
        this.htmlStr = htmlStr;
        this.formElementType = formElementType;
        lastPos = 0;
    }
    
    @Override
    public void handleEndTag(HTML.Tag tag, int pos)
    {
        startTag = null;
        if (tag.equals(HTML.Tag.TITLE) && lastPos != 0)
        {
            htmlTitle = htmlStr.substring(lastPos, pos);
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
                formElementType.put(lastId, Constants.FORM_MEM_TYPE_TEXT);
            }
            else if (type.equals("checkbox"))
            {
                setNameAndId(att);
                formElementType.put(lastId, Constants.FORM_MEM_TYPE_CHECKBOX);
            }
        }
    }
    
    @Override
    public void handleStartTag(HTML.Tag tag, MutableAttributeSet att, int pos)
    {
        startTag = tag;
        if (tag.equals(HTML.Tag.TEXTAREA))
        {
            setNameAndId(att);
            formElementType.put(lastId, Constants.FORM_MEM_TYPE_TEXTAREA);
        }
    }
    
    public void handleText(char[] text, int pos)
    {
        if (null != startTag && startTag.equals(HTML.Tag.TITLE))
        {
            lastPos = pos;
        }
    }
    
    private void setNameAndId(MutableAttributeSet att)
    {
        this.lastId = att.getAttribute(HTML.Attribute.ID).toString().trim();
        this.lastName = att.getAttribute(HTML.Attribute.NAME).toString().trim();
    }

    public String getHtmlTitle()
    {
        return htmlTitle == null ? "" : htmlTitle.trim();
    }
    
}
