package phform.application.html;

import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;

import phform.application.html.util.ImageIconUtil;

public final class Constants
{
    // style = "position:relative;left:0px;top:0px;"
    public static final String POSITION_CSS_RELATIVE = "relative";
    
    public static final String POSITION_CSS_ABSOLUTE = "absolute";
    
    // html element types
    
    public static final int TYPE_DIV = 0x00000010;
    
    public static final int TYPE_FONT = 0x00000020;
    
    public static final int TYPE_IMG = 0x00000030;
    
    public static final int TYPE_INPUT = 0x00000040;
    
    public static final int TYPE_INPUT_CHECKBOX = 0x00000043;
    
    public static final int TYPE_INPUT_HIDDEN = 0x00000046;
    
    public static final int TYPE_INPUT_RADIO = 0x00000049;
    
    public static final int TYPE_INPUT_TEXT = 0x0000004a;
    
    public static final int TYPE_OPTION = 0x00000050;
    
    public static final int TYPE_SELECT = 0x00000060;
    
    public static final int TYPE_SPAN = 0x00000070;
    
    public static final int TYPE_STRONG = 0x00000080;
    
    public static final int TYPE_TABLE = 0x00000090;
    
    public static final int TYPE_TD = 0x000000a0;
    
    public static final int TYPE_TEXTAREA = 0x000000b0;
    
    public static final int TYPE_TR = 0x000000c0;
    
    public static final Map TYPE_MAP = new HashMap();
    
    static
    {
        TYPE_MAP.put(TYPE_DIV, "div");
        TYPE_MAP.put(TYPE_FONT, "font");
        TYPE_MAP.put(TYPE_IMG, "img");
        TYPE_MAP.put(TYPE_INPUT, "input");
        TYPE_MAP.put(TYPE_INPUT_CHECKBOX, "checkbox");
        TYPE_MAP.put(TYPE_INPUT_HIDDEN, "hidden");
        TYPE_MAP.put(TYPE_INPUT_RADIO, "radio");
        TYPE_MAP.put(TYPE_INPUT_TEXT, "text");
        TYPE_MAP.put(TYPE_OPTION, "option");
        TYPE_MAP.put(TYPE_SELECT, "select");
        TYPE_MAP.put(TYPE_SPAN, "span");
        TYPE_MAP.put(TYPE_STRONG, "strong");
        TYPE_MAP.put(TYPE_TABLE, "table");
        TYPE_MAP.put(TYPE_TD, "td");
        TYPE_MAP.put(TYPE_TEXTAREA, "textarea");
        TYPE_MAP.put(TYPE_TR, "tr");
    }
    
    // the tag need closed. <input type=""/>:false <div></div>:true
    public static final Map CLOSE_MAP = new HashMap();
    
    static
    {
        CLOSE_MAP.put(TYPE_DIV, true);
        CLOSE_MAP.put(TYPE_FONT, true);
        CLOSE_MAP.put(TYPE_IMG, false);
        CLOSE_MAP.put(TYPE_OPTION, true);
        CLOSE_MAP.put(TYPE_SELECT, true);
        CLOSE_MAP.put(TYPE_SPAN, true);
        CLOSE_MAP.put(TYPE_STRONG, true);
        CLOSE_MAP.put(TYPE_TABLE, true);
        CLOSE_MAP.put(TYPE_TD, true);
        CLOSE_MAP.put(TYPE_TEXTAREA, true);
        CLOSE_MAP.put(TYPE_TR, true);
        CLOSE_MAP.put(TYPE_INPUT_CHECKBOX, false);
        CLOSE_MAP.put(TYPE_INPUT_HIDDEN, false);
        CLOSE_MAP.put(TYPE_INPUT_RADIO, false);
        CLOSE_MAP.put(TYPE_INPUT_TEXT, false);
    }
    
    public static final int A4_WIDTH_BAK = 210;
    
    public static final int A4_HEIGHT_BAK = 297;
    
    public static final float A4_PARAMETER_BAK = 0.7f;
    
    // Based on the doc template
    public static final int A4_SCREEN_WIDTH = 795;
    
    public static final int A4_SCREEN_HEIGHT = 1124;
    
    public static final int A4_TOP = 96;
    
    public static final int A4_LEFT = 120;
    
    public static final int CORNER = 24;
    
    public static final int A4_DOC_RIGHT = A4_SCREEN_WIDTH - A4_LEFT;
    
    public static final int A4_DOC_LEFT = A4_LEFT;
    
    public static final int A4_DOC_TOP = A4_TOP;
    
    public static final int A4_DOC_BOTTOM = A4_SCREEN_HEIGHT - A4_TOP;
    
    public static final int A4_DOC_WIDTH = A4_SCREEN_WIDTH - 2 * A4_LEFT;
    
    public static final int A4_DOC_HEIGHT = A4_SCREEN_HEIGHT - 2 * A4_TOP;
    
    // public static final int A4_SCREEN_WIDTH = 555;
    //    
    // public static final int A4_SCREEN_HEIGHT = 932;
    //    
    // public static final int A4_TOP = 0;
    //    
    // public static final int A4_LEFT = 0;
    //    
    // public static final int CORNER = 0;

    //
    public static final int TABLE_DEFAULT_HEIGHT = 30;
    
    public static final int TABLE_DEFAULT_DRAGINTERVAL = 10;
    
    public static final int FORM_OBJ_DEFAULT_DRAGINTERVAL = 10;
    
    public static final int FORM_OBJ_TEXT_DRAGINTERVAL = 19;
    
    public static final int FORM_OBJ_TEXTAREA_DRAGINTERVAL_W = 32;
    
    public static final int FORM_OBJ_TEXTAREA_DRAGINTERVAL_H = 19;

    public static final String BR_BAK = System.getProperty("line.separator");
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");
    public static final String BR = "\n";
    public static final String HTML_DOC_TYPE =
        "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">";
    public static final String HTML_HTMLTAG =
        "<html xmlns=\"http://www.w3.org/1999/xhtml\">";
    public static final String HTML_METATAG =
        "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />";
    public static final String HTML_METATAG2 =
        "<meta http-equiv=\"x-ua-compatible\" content=\"ie=6\" />";
    
    public static final int FONTELEMENT = 1;
    public static final int CHECKBOXELEMENT = 2;
    public static final int TEXTAREAELEMENT = 3;
    public static final int TEXTELEMENT = 4;
    
    public static int CHECKBOXWIDTH = 13;
    public static int CHECKBOXHEIGHT = 13;
    public static int CHECKBOXWHOLEWIDTH = 22;
    public static int CHECKBOXWHOLEHEIGHT = 22;
    public static ImageIcon CHECKBOXIMAGE =
        ImageIconUtil.getImageIcon("check-box-b.gif");
    
    public static int TEXTAREAWIDTH = 16;
    public static int TEXTAREAHEIGHT = 17;
    public static ImageIcon TEXTAREATOPIMAGE =
        ImageIconUtil.getImageIcon("text-area-top.gif");
    
    public static ImageIcon TEXTAREABOTTOMIMAGE =
        ImageIconUtil.getImageIcon("text-area-bottom.gif");
    
    public static int TEXTHEIGHT = 19;
    
    public static String BODYSPACEPREFIX = "    ";//4
    public static String HEADSPACEPREFIX = "    ";//4
    public static String METASPACEPREFIX = "        ";//8
    public static String STYLESPACEPREFIX = "        ";//8
    public static String TITLESPACEPREFIX = "        ";//8
    public static String DIVSPACEPREFIX = "        ";//8
    public static String TRSPACEPREFIX = "                ";//16
    public static String TDSPACEPREFIX = "                    ";//20
    public static String OTHERSPACEPREFIX = "            ";//12
    
    public static final Map SPACE_MAP = new HashMap();
    
    static
    {
        SPACE_MAP.put(TYPE_FONT, OTHERSPACEPREFIX);
        SPACE_MAP.put(TYPE_INPUT_CHECKBOX, OTHERSPACEPREFIX);
        SPACE_MAP.put(TYPE_INPUT_TEXT, OTHERSPACEPREFIX);
        SPACE_MAP.put(TYPE_TABLE, OTHERSPACEPREFIX);
        SPACE_MAP.put(TYPE_TEXTAREA, OTHERSPACEPREFIX);
        
        SPACE_MAP.put(TYPE_TR, TRSPACEPREFIX);
        SPACE_MAP.put(TYPE_TD, TDSPACEPREFIX);
    }
    
    public static final String IDPREFIX = "PH_";
}
