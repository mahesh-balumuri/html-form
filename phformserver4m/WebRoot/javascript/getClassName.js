// JavaScript Document
 function getElementsByClassName(ClassName, Tag, parent) {
        if (arguments.length == 1) {
            parent = null;
            Tag = '*';
        }
        parent = parent || document.body;
        if (!(parent = ID(parent))) return false;

        //查找所有匹配的标签
        var allTags = (Tag == '*' && parent.all) ? parent.all : parent.getElementsByTagName(Tag);
        var targetElements = new Array();

        //判断ClassName是否正确
        ClassName = ClassName.replace(/\-/g, '\\-');
        var regex = new RegExp('(^|\\s)' + ClassName + '( \\s|$)');

        var element;
        // 检查元素

        for (var i = 0; i < allTags.length; i++) {
            element = allTags[i];
            if (regex.test(element.className)) targetElements.push(element);
        }
        return targetElements;
 }
 
 function ID() {

        if (arguments.length == 0) return false;
        var Elements = new Array();
        for (var i = 0; i < arguments.length; i++) {
            var Element = arguments[i];
            if (typeof (Element) == 'string') Element = document.getElementById(Element);
            if (arguments.length == 1) return Element;
            Elements.push(Element);
        }
        return Elements;
 } 