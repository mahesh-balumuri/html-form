package com.phform.server.web.action;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.MutableAttributeSet;
import javax.swing.text.html.HTML;
import javax.swing.text.html.HTMLEditorKit.ParserCallback;
import javax.swing.text.html.parser.ParserDelegator;

import com.phform.server.Constants;

public class PrintCallback extends ParserCallback
{
    private String htmlStr;
    
    private Map valueMap;
    
    private Map permissionMap;
    
    private String formId;
    
    private String instanceId;
    
    private String roleId;
    
    private String retStr;
    
    private String prefixStr;
    
    private String postStr;
    
    private String divStr;
    
    private int lastPos;
    
    private int divPos;
    
    private String lastId;
    
    private String lastName;
    
    private boolean isLastTr;
    
    private int printCount;
    
    public PrintCallback(String htmlStr, Map valueMap, Map permissionMap,
            String formId, String instanceId, String roleId, int printCount)
    {
        this.htmlStr = htmlStr;
        this.valueMap = valueMap;
        this.permissionMap = permissionMap;
        this.formId = (formId == null ? "" : formId.trim());
        this.instanceId = (instanceId == null ? "" : instanceId.trim());
        this.roleId = (roleId == null ? "" : roleId.trim());
        this.retStr = "";
        this.prefixStr = "";
        this.divStr = "";
        this.postStr = "";
        lastPos = 0;
        divPos = 0;
        this.isLastTr = false;
        this.printCount = printCount;
    }
    
    @Override
    public void handleEndTag(HTML.Tag tag, int pos)
    {
        if (tag.equals(HTML.Tag.HTML))
        {
            retStr += htmlStr.substring(lastPos, htmlStr.length());
            
            postStr += htmlStr.substring(divPos, htmlStr.length());
        }
        else if (tag.equals(HTML.Tag.TEXTAREA))
        {
            retStr += htmlStr.substring(lastPos, pos);
            // retStr += valueStr;
            if (null != valueMap)
            {
                if (valueMap.containsKey(lastId))
                {
                    retStr += valueMap.get(lastId).toString();
                }
            }
            lastPos = pos;
            
            divStr += htmlStr.substring(divPos, pos);
            if (null != valueMap)
            {
                if (valueMap.containsKey(lastId))
                {
                    divStr += valueMap.get(lastId).toString();
                }
            }
            divPos = pos;
        }
        else if (tag.equals(HTML.Tag.BODY))
        {
            retStr += htmlStr.substring(lastPos, pos);
            // retStr +=
            // "<input type=\"hidden\" name=\"formId\" id=\"formId\" value=\""
            // + formId
            // + "\"><input type=\"hidden\" name=\"instanceId\"
            // id=\"instanceId\" value=\""
            // + instanceId
            // + "\"><input type=\"hidden\" name=\"roleId\" id=\"roleId\"
            // value=\""
            // + roleId + "\">";
            lastPos = pos;
            
            divStr += htmlStr.substring(divPos, pos);
            divPos = pos;
        }
        else if (tag.equals(HTML.Tag.TR))
        {
            isLastTr = false;
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
                if (null != permissionMap)
                {
                    if (permissionMap.containsKey(lastId))
                    {
                        String p = permissionMap.get(lastId).toString().trim();
                        if (p.equals(Constants.ELEMENT_READ))
                        {
                            retStr += " readonly=\"readonly\"";
                        }
                    }
                }
                if (null != valueMap)
                {
                    if (valueMap.containsKey(lastId))
                    {
                        retStr +=
                            " value=\"" + valueMap.get(lastId).toString()
                                    + "\"";
                    }
                }
                
                lastPos = pos + "input".length() + 1;
                
                divStr += htmlStr.substring(divPos, pos + "input".length() + 1);
                // setValue and ReadOnly
                if (null != permissionMap)
                {
                    if (permissionMap.containsKey(lastId))
                    {
                        String p = permissionMap.get(lastId).toString().trim();
                        if (p.equals(Constants.ELEMENT_READ))
                        {
                            divStr += " readonly=\"readonly\"";
                        }
                    }
                }
                if (null != valueMap)
                {
                    if (valueMap.containsKey(lastId))
                    {
                        divStr +=
                            " value=\"" + valueMap.get(lastId).toString()
                                    + "\"";
                    }
                }
                
                divPos = pos + "input".length() + 1;
            }
            else if (type.equals("checkbox"))
            {
                setNameAndId(att);
                retStr +=
                    htmlStr.substring(lastPos, pos + "input".length() + 1);
                // setValue and ReadOnly
                if (null != permissionMap)
                {
                    if (permissionMap.containsKey(lastId))
                    {
                        String p = permissionMap.get(lastId).toString().trim();
                        if (p.equals(Constants.ELEMENT_READ))
                        {
                            retStr += " disabled=\"true\"";
                        }
                    }
                }
                if (null != valueMap)
                {
                    if (valueMap.containsKey(lastId))
                    {
                        if (valueMap.get(lastId).toString().equals(
                                Constants.CHECKBOX_TRUE))
                        {
                            retStr += " checked=\"checked\"";
                        }
                    }
                }
                lastPos = pos + "input".length() + 1;
                
                divStr += htmlStr.substring(divPos, pos + "input".length() + 1);
                // setValue and ReadOnly
                if (null != permissionMap)
                {
                    if (permissionMap.containsKey(lastId))
                    {
                        String p = permissionMap.get(lastId).toString().trim();
                        if (p.equals(Constants.ELEMENT_READ))
                        {
                            divStr += " disabled=\"true\"";
                        }
                    }
                }
                if (null != valueMap)
                {
                    if (valueMap.containsKey(lastId))
                    {
                        if (valueMap.get(lastId).toString().equals(
                                Constants.CHECKBOX_TRUE))
                        {
                            divStr += " checked=\"checked\"";
                        }
                    }
                }
                divPos = pos + "input".length() + 1;
            }
        }
    }
    
    @Override
    public void handleStartTag(HTML.Tag tag, MutableAttributeSet att, int pos)
    {
        if (tag.equals(HTML.Tag.TEXTAREA))
        {
            setNameAndId(att);
            retStr += htmlStr.substring(lastPos, pos + "textarea".length() + 1);
            // setReadOnly
            if (null != permissionMap)
            {
                if (permissionMap.containsKey(lastId))
                {
                    String p = permissionMap.get(lastId).toString().trim();
                    if (p.equals(Constants.ELEMENT_READ))
                    {
                        retStr += " readonly=\"readonly\"";
                    }
                }
            }
            lastPos = pos + "textarea".length() + 1;
            
            divStr += htmlStr.substring(divPos, pos + "textarea".length() + 1);
            // setReadOnly
            if (null != permissionMap)
            {
                if (permissionMap.containsKey(lastId))
                {
                    String p = permissionMap.get(lastId).toString().trim();
                    if (p.equals(Constants.ELEMENT_READ))
                    {
                        divStr += " readonly=\"readonly\"";
                    }
                }
            }
            divPos = pos + "textarea".length() + 1;
        }
        else if (tag.equals(HTML.Tag.TR))
        {
            Map attMap = new HashMap();
            parseStyleAtt(attMap, att);
            if (attMap.containsKey("visibility")
                    && "hidden".equals(attMap.get("visibility").toString()
                            .trim()))
            {
                isLastTr = true;
            }
        }
        else if (tag.equals(HTML.Tag.TD))
        {
            Map attMap = new HashMap();
            parseStyleAtt(attMap, att);
            if (!isLastTr
                    && attMap.containsKey("visibility")
                    && "hidden".equals(attMap.get("visibility").toString()
                            .trim()))
            {
                retStr +=
                    htmlStr.substring(lastPos, pos
                            + "td style=\"visibility:hidden;height:".length()
                            + 1);
                int heightTmp = gatherIntPro(attMap.get("height").toString());
                retStr += heightTmp - 1;
                lastPos =
                    pos + "td style=\"visibility:hidden;height:".length()
                            + String.valueOf(heightTmp).length() + 1;
                
                divStr +=
                    htmlStr.substring(divPos, pos
                            + "td style=\"visibility:hidden;height:".length()
                            + 1);
                divStr += heightTmp - 1;
                divPos =
                    pos + "td style=\"visibility:hidden;height:".length()
                            + String.valueOf(heightTmp).length() + 1;
            }
        }
        else if (tag.equals(HTML.Tag.DIV))
        {
            Map attMap = new HashMap();
            parseStyleAtt(attMap, att);
            divStr +=
                htmlStr
                        .substring(
                                divPos,
                                pos
                                        + "div style=\"width: 555px;height: 932px;position:absolute;"
                                                .length() + 1);
            // int heightTmp = gatherIntPro(attMap.get("height").toString()) -
            // 1;
            // retStr += heightTmp;
            // lastPos =
            // pos + "td style=\"visibility:hidden;height:".length()
            // + String.valueOf(heightTmp).length() + 1;
            divStr += "top: " + 932 * printCount + "px;";
            divPos =
                pos
                        + "div style=\"width: 555px;height: 932px;position:absolute;"
                                .length() + 1;
        }
        else if (tag.equals(HTML.Tag.BODY))
        {
            prefixStr += htmlStr.substring(divPos, pos + "body>".length() + 1);
            divPos = pos + "body>".length() + 1;
        }
    }
    
    private void setNameAndId(MutableAttributeSet att)
    {
        this.lastId = att.getAttribute(HTML.Attribute.ID).toString().trim();
        this.lastName = att.getAttribute(HTML.Attribute.NAME).toString().trim();
    }
    
    public static void main(String[] args)
    {
        Reader reader = null;
        char[] c = new char[4096];
        List ret = new ArrayList();
        List retSum = new ArrayList();
        int sum = 0;
        try
        {
            reader =
                new BufferedReader(new InputStreamReader(new FileInputStream(
                        "E:/sss.html"), Charset.forName("UTF-8")));
            int k = -1;
            while (-1 != (k = reader.read(c)))
            {
                ret.add(c);
                retSum.add(k);
                sum += k;
                c = new char[4096];
            }
        }
        catch (FileNotFoundException e1)
        {
            e1.printStackTrace();
        }
        catch (IOException e2)
        {
            e2.printStackTrace();
        }
        finally
        {
            if (null != reader)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
        c = new char[sum];
        int k = 0;
        for (int i = 0; i < ret.size(); i++)
        {
            char[] tmp = (char[]) ret.get(i);
            int tmpLength = Integer.parseInt(retSum.get(i).toString());
            for (int j = 0; j < tmpLength; j++)
            {
                c[k++] = tmp[j];
            }
        }
        
        ret.clear();
        retSum.clear();
        
        PrintCallback call =
            new PrintCallback(new String(c), null, null, null, null, null, 1);
        try
        {
            reader = new StringReader(new String(c));
            new ParserDelegator().parse(reader, call, true);
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }
        finally
        {
            if (null != reader)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e1)
                {
                    e1.printStackTrace();
                }
            }
        }
        
        Writer writer = null;
        try
        {
            writer =
                new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                        "E:/sss1.html"), Charset.forName("UTF-8")));
            // writer.write(call.getRetStr());
            writer.write(call.getPrefixStr() + call.getDivStr()
                    + call.getPostStr());
            writer.flush();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != writer)
            {
                try
                {
                    writer.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public String getRetStr()
    {
        return retStr == null ? "" : retStr;
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
    
    private void parseStyleAtt(Map map, MutableAttributeSet attSet)
    {
        if (null != attSet && null != attSet.getAttribute(HTML.Attribute.STYLE))
        {
            String att = attSet.getAttribute(HTML.Attribute.STYLE).toString();
            if (null != att && !"".equals(att.trim()))
            {
                String[] attes = att.split(";");
                if (null != attes)
                {
                    for (int i = 0; i < attes.length; i++)
                    {
                        String attribute = attes[i];
                        String[] attKeyValue = attribute.split(":");
                        map.put(attKeyValue[0].toString().trim(),
                                attKeyValue[1].toString().trim());
                    }
                }
            }
        }
    }
    
    public String getDivStr()
    {
        return divStr == null ? "" : divStr.trim();
    }
    
    public String getPostStr()
    {
        return postStr == null ? "" : postStr.trim();
    }
    
    public String getPrefixStr()
    {
        return prefixStr == null ? "" : prefixStr.trim();
    }
}
