package phform.application.menu;

import java.awt.Font;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;

import phform.application.canvas.PureHtmlFormSingleCanvas;
import phform.application.html.Constants;
import phform.application.html.FormObject;
import phform.application.html.bean.CheckBoxBean;
import phform.application.html.bean.FontBean;
import phform.application.html.bean.TableBean;
import phform.application.html.bean.TdBean;
import phform.application.html.bean.TextAreaBean;
import phform.application.html.bean.TextBean;
import phform.application.html.util.FormUtil;

public class SingleCallback extends ParserCallback
{
    private PureHtmlFormSingleCanvas canvas;
    
    private List tableList = new ArrayList();
    
    private List htmlBeanList = new ArrayList();
    
    private String htmlStr;
    
    private int lastPos = -1;
    
    private HTML.Tag startTag;
    
    private boolean isLastTr;
    
    public SingleCallback(PureHtmlFormSingleCanvas canvas, String htmlStr)
    {
        this.canvas = canvas;
        this.htmlStr = htmlStr;
        this.isLastTr = false;
    }
    
    @Override
    public void handleEndTag(HTML.Tag tag, int pos)
    {
        // System.out.println("END " + tag);
        if (tag.equals(HTML.Tag.HTML))
        {
            canvas.setTableList(tableList);
            canvas.setHtmlBeanList(htmlBeanList);
            canvas.newOper();
            canvas.repaint();
        }
        else if (tag.equals(HTML.Tag.TABLE))
        {
            TableBean table = (TableBean) tableList.get(tableList.size() - 1);
            table.parseTdFromHtml();
        }
        else if (tag.equals(startTag) && startTag.equals(HTML.Tag.TITLE)
                && lastPos != -1)
        {
            canvas.setCanvasName(htmlStr.substring(lastPos, pos));
        }
        else if (tag.equals(startTag) && startTag.equals(HTML.Tag.FONT)
                && lastPos != -1)
        {
            FontBean font =
                (FontBean) htmlBeanList.get(htmlBeanList.size() - 1);
            font.setInnerString(htmlStr.substring(lastPos, pos).replace(
                    "&nbsp;", " ").replace("<br>", Constants.BR));
        }
        else if (tag.equals(HTML.Tag.TR))
        {
            isLastTr = false;
        }
        startTag = null;
        lastPos = -1;
    }
    
    @Override
    public void handleSimpleTag(HTML.Tag tag, MutableAttributeSet att, int pos)
    {
        // System.out.println("SIMPLE " + tag);
        if (tag.equals(HTML.Tag.INPUT))
        {
            startTag = tag;
            String type =
                att.getAttribute(HTML.Attribute.TYPE).toString().trim();
            if (type.equals("text"))
            {
                TextBean text = new TextBean();
                text.setHeight(Constants.TEXTHEIGHT);
                Map attMap = new HashMap();
                FormUtil.parseStyleAtt(attMap, att);
                text.setWidth(gatherIntPro(attMap.get("width").toString()) + 7);
                text
                        .setHeight(gatherIntPro(attMap.get("height").toString()) + 2);
                text
                        .setPositionLeft(gatherIntPro(attMap.get("left")
                                .toString())
                                + Constants.A4_LEFT);
                text.setPositionTop(gatherIntPro(attMap.get("top").toString())
                        + Constants.A4_TOP);
                setNameAndId(text, att);
                htmlBeanList.add(text);
            }
            else if (type.equals("checkbox"))
            {
                CheckBoxBean checkBox = new CheckBoxBean();
                checkBox.setWidth(Constants.CHECKBOXWHOLEWIDTH);
                checkBox.setHeight(Constants.CHECKBOXWHOLEHEIGHT);
                Map attMap = new HashMap();
                FormUtil.parseStyleAtt(attMap, att);
                checkBox.setPositionLeft(gatherIntPro(attMap.get("left")
                        .toString())
                        + Constants.A4_LEFT);
                checkBox.setPositionTop(gatherIntPro(attMap.get("top")
                        .toString())
                        + Constants.A4_TOP);
                setNameAndId(checkBox, att);
                htmlBeanList.add(checkBox);
            }
        }
        // else if(tag.equals(HTML.Tag.BR))
        // {
        // if (null != startTag && startTag.equals(HTML.Tag.FONT))
        // {
        // FontBean font =
        // (FontBean) htmlBeanList.get(htmlBeanList.size() - 1);
        // font.setInnerString(font.getInnerString() + Constants.BR);
        // }
        // }
    }
    
    @Override
    public void handleStartTag(HTML.Tag tag, MutableAttributeSet att, int pos)
    {
        // System.out.println("START " + tag);
        startTag = tag;
        if (tag.equals(HTML.Tag.TABLE))
        {
            TableBean table = new TableBean();
            Map attMap = new HashMap();
            FormUtil.parseStyleAtt(attMap, att);
            table.setPositionLeft(gatherIntPro(attMap.get("left").toString())
                    + Constants.A4_LEFT);
            table.setPositionTop(gatherIntPro(attMap.get("top").toString())
                    + Constants.A4_TOP);
            setNameAndId(table, att);
            tableList.add(table);
        }
        else if (tag.equals(HTML.Tag.TR))
        {
            Map attMap = new HashMap();
            FormUtil.parseStyleAtt(attMap, att);
            if (attMap.containsKey("visibility")
                    && "hidden".equals(attMap.get("visibility").toString()
                            .trim()))
            {
                isLastTr = true;
            }
            else
            {
                TableBean table =
                    (TableBean) tableList.get(tableList.size() - 1);
                table.getTrList().add(new ArrayList());
            }
        }
        else if (tag.equals(HTML.Tag.TD))
        {
            Map attMap = new HashMap();
            FormUtil.parseStyleAtt(attMap, att);
            if (!isLastTr)
            {
                if (!attMap.containsKey("visibility")
                        || !"hidden".equals(attMap.get("visibility").toString()
                                .trim()))
                {
                    TableBean table =
                        (TableBean) tableList.get(tableList.size() - 1);
                    List tr =
                        (List) table.getTrList().get(
                                table.getTrList().size() - 1);
                    TdBean td = new TdBean();
                    td.setTableBean(table);
                    td.setColspan(1);
                    td.setRowspan(1);
                    try
                    {
                        td.setColspan(Integer.parseInt(att.getAttribute(
                                HTML.Attribute.COLSPAN).toString().trim()));
                    }
                    catch (Exception e)
                    {
                    }
                    try
                    {
                        td.setRowspan(Integer.parseInt(att.getAttribute(
                                HTML.Attribute.ROWSPAN).toString().trim()));
                    }
                    catch (Exception e)
                    {
                    }
                    // td
                    // .setWidth(gatherIntPro(attMap.get("width")
                    // .toString()) + 1);
                    // td
                    // .setHeight(gatherIntPro(attMap.get("height")
                    // .toString()) + 1);
                    tr.add(td);
                }
                else
                {
                    TableBean table =
                        (TableBean) tableList.get(tableList.size() - 1);
                    List topList = table.getTopList();
                    topList.add(gatherIntPro(attMap.get("height").toString()));
                }
            }
            else
            {
                TableBean table =
                    (TableBean) tableList.get(tableList.size() - 1);
                List leftList = table.getLeftList();
                leftList.add(gatherIntPro(attMap.get("width").toString()) + 1);
            }
        }
        else if (tag.equals(HTML.Tag.TEXTAREA))
        {
            TextAreaBean textArea = new TextAreaBean();
            Map attMap = new HashMap();
            FormUtil.parseStyleAtt(attMap, att);
            textArea.setWidth(gatherIntPro(attMap.get("width").toString()) + 7);
            textArea
                    .setHeight(gatherIntPro(attMap.get("height").toString()) + 2);
            textArea
                    .setPositionLeft(gatherIntPro(attMap.get("left").toString())
                            + Constants.A4_LEFT);
            textArea.setPositionTop(gatherIntPro(attMap.get("top").toString())
                    + Constants.A4_TOP);
            setNameAndId(textArea, att);
            htmlBeanList.add(textArea);
        }
        else if (tag.equals(HTML.Tag.FONT))
        {
            FontBean fontBean = new FontBean();
            Map attMap = new HashMap();
            FormUtil.parseStyleAtt(attMap, att);
            fontBean.setWidth(gatherIntPro(attMap.get("width").toString()));
            fontBean.setHeight(gatherIntPro(attMap.get("height").toString()));
            fontBean
                    .setPositionLeft(gatherIntPro(attMap.get("left").toString())
                            + Constants.A4_LEFT);
            fontBean.setPositionTop(gatherIntPro(attMap.get("top").toString())
                    + Constants.A4_TOP);
            Font font =
                new Font(attMap.get("font-family").toString(), attMap.get(
                        "font-weight").toString().equals("bold") ? Font.BOLD
                        : Font.PLAIN, gatherIntPro(attMap.get("font-size")
                        .toString()));
            fontBean.setFont(font);
            setNameAndId(fontBean, att);
            htmlBeanList.add(fontBean);
        }
    }
    
    public void handleText(char[] text, int pos)
    {
        // System.out.println(pos + " " + new String(text));
        if (null != startTag
                && (startTag.equals(HTML.Tag.TITLE) || startTag
                        .equals(HTML.Tag.FONT)) && -1 == lastPos)
        {
            lastPos = pos;
        }
        // if (null != text && null != startTag &&
        // startTag.equals(HTML.Tag.FONT))
        // {
        // String str = new String(text);
        // str = str.replace("&nbsp;", " ").replace("<br>", Constants.BR);
        // FontBean font =
        // (FontBean) htmlBeanList.get(htmlBeanList.size() - 1);
        // font.setInnerString(font.getInnerString() + str);
        // }
        // else if (null != text && null != startTag
        // && startTag.equals(HTML.Tag.TITLE))
        // {
        // String str = new String(text);
        // canvas.setCanvasName(str);
        // }
    }
    
    private void setNameAndId(FormObject formObj, MutableAttributeSet att)
    {
        if (null != formObj && null != att)
        {
            formObj
                    .setId(att.getAttribute(HTML.Attribute.ID).toString()
                            .trim());
            formObj.setName(att.getAttribute(HTML.Attribute.NAME).toString()
                    .trim());
            
            if (null != formObj.getId() && !"".equals(formObj.getId().trim()))
            {
                if (formObj.getId().trim().startsWith(Constants.IDPREFIX))
                {
                    try
                    {
                        String tmp =
                            formObj.getId().trim().substring(
                                    Constants.IDPREFIX.length());
                        if (FormUtil.getMaxId() < Long.parseLong(tmp))
                        {
                            FormUtil.setMaxId(Long.parseLong(tmp));
                        }
                        
                    }
                    catch (Exception e)
                    {
                    }
                }
            }
            if (null != formObj.getName()
                    && !"".equals(formObj.getName().trim()))
            {
                if (formObj.getName().trim().startsWith(Constants.IDPREFIX))
                {
                    try
                    {
                        String tmp =
                            formObj.getName().trim().substring(
                                    Constants.IDPREFIX.length());
                        if (FormUtil.getMaxId() < Long.parseLong(tmp))
                        {
                            FormUtil.setMaxId(Long.parseLong(tmp));
                        }
                        
                    }
                    catch (Exception e)
                    {
                    }
                }
            }
        }
    }
    
    private int gatherIntPro(String intPx)
    {
        if (null != intPx && !"".equals(intPx.trim())
                && intPx.indexOf("px") != -1)
        {
            return Integer.parseInt(intPx.substring(0, intPx.indexOf("px")));
        }
        else if (null != intPx && !"".equals(intPx.trim())
                && intPx.indexOf("px") == -1)
        {
            return Integer.parseInt(intPx);
        }
        return 0;
    }
}
