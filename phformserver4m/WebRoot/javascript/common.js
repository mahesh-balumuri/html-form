// for pagenation
function goPage(pageURL){
   var curObj = event.srcElement;
    while (curObj.tagName != "FORM") {
        curObj = curObj.parentElement;
    }
    var form;
    if(curObj.id!=null && curObj.id!=''){
        form= document.getElementById(curObj.id);
    }
    else{
        form=document.getElementsByName(curObj.name)[0]
    }
    form.action = pageURL;
    form.submit();
}


//table row change background
function SetWinHeight(obj)
{
    var win = obj;
    if (document.getElementById)
    {
        if (win && !window.opera)
        {
            if (win.contentDocument && win.contentDocument.body.offsetHeight)

                win.height = win.contentDocument.body.offsetHeight;
            else if (win.Document && win.Document.body.scrollHeight)
                win.height = win.Document.body.scrollHeight;
        }
    }
}

//nfe("表格名称","奇数行背景","偶数行背景","鼠标经过背景","点击后背景");
function senfe(o, a, b, c, d)
{   
    a="#FFFFFF";b="#FFFFFF";c="#e6f1f8";d="#e3ebf7";
    if(document.getElementById(o)== null|| document.getElementById(o)=="undefine"){
    return ;
    }
    var t = document.getElementById(o).getElementsByTagName("tr");
    for (var i = 1; i < t.length; i++)
    {
        t[i].style.backgroundColor = (t[i].sectionRowIndex % 2 == 0) ? a : b;
        t[i].onclick = function()
        {
            if (this.x != "1")
            {
                this.x = "1";
                this.style.backgroundColor = (this.sectionRowIndex % 2 == 0) ? a : b;
            }
            else
            {
                this.x = "0";
                this.style.backgroundColor = (this.sectionRowIndex % 2 == 0) ? a : b;
            }
        }
        t[i].onmouseover = function()
        {
            if (this.x != "1")this.style.backgroundColor = c;
        }
        t[i].onmouseout = function()
        {
            if (this.x != "1")this.style.backgroundColor = (this.sectionRowIndex % 2 == 0) ? a : b;
        }
    }
}
function selectGlobalCheckBox(checked, tableId) {
    var contentTable = eval("document.all." + tableId);
    for (i = 1; i < (contentTable.rows.length); i++) {
        if (contentTable.rows[i].cells[0].childNodes.length != 0) {
            var onerow = contentTable.rows[i].cells[0].childNodes[0];
            if (onerow.tagName == 'INPUT' && onerow.type == 'checkbox') {
                onerow.checked = checked;
            }
        }
    }
}

function checkSelectRow(tableId) {
    var contentTable = eval("document.all." + tableId);
    var count = 0;
    for (i = 1; i < (contentTable.rows.length); i++) {
        if (contentTable.rows[i].cells[0].childNodes.length != 0) {
            var onerow = contentTable.rows[i].cells[0].childNodes[0];
            if (onerow.tagName == 'INPUT' && onerow.type == 'checkbox') {
                if (onerow.checked) {
                    count = count + 1;
                }
            }
        }
    }
    if (0 == count) {
        return false;
    } else {
        return true;
    }
}

var isNull = function(str)
	{
        var reg = /(\s|　)+/gi;
        str = str.replace(reg,'');
        var regTextNull = /.+/;
        return regTextNull.test(str);
	};

    var isEng = function(str)
    {
		var regTextEng = /^[a-zA-Z]*$/;
		return regTextEng.test(str);
    };

    var isNumber = function(str)
	{   if(isNull(str)){
           var regTextInteger = /^[0-9]*$/;
           return regTextInteger.test(str);
        }
        return false;
    };

    var isInteger = function(str)
	{
        var regTextInteger = /^[^0](\d)*$/;
        if(isNumber(str)){
//            if(str.length == 1)
//                return false;
            return !regTextInteger.test(str);
        }
        return true;
    };

    var isIntegerScope = function(str,obj)
	{
        if(isNumber(str)){

          var minNum = obj.getAttribute("minNum");
          var maxNum = obj.getAttribute("maxNum");
          return (parseInt(str)>= parseInt(minNum) && parseInt(str)<= parseInt(maxNum))
        }
        return false;
	};

     var isPhone = function(str)
	{
//        var regTextPhone = /^[0-9]{3,4}\-[0-9]{3,8}$/;
        var regTextPhone = /^[0-9\-\/]*$/;
        return regTextPhone.test(str);
	};

	var isEmail=function(str){
		var regTextEmail= /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		return regTextEmail.test(str);
	}



     var isDouble = function(str)
	{
        var regTextDouble = /^[\-\+]?([0-9]\d*|0|[1-9]\d{0,2}(,\d{3})*)(\.\d+)?$/;
        var regTextfirst = /^[^0](\d)*$/
        if(regTextDouble.test(str)){
            var length = str.length;
            if(str.indexOf('.') != -1)
                length = str.indexOf('.');
            str = str.substring(0,length);
            if(str.length>1)
               return regTextfirst.test(str);
            else
               return true;
        }
        return false;
	};

     var isDoublerScope = function(str,obj)
	{
        if(isDouble(str)){
            var length = str.length;
            if(str.indexOf('.') != -1)
                length = str.indexOf('.');
            str = str.substring(0,length);
            var minNum = obj.getAttribute("minNum");
            var maxNum = obj.getAttribute("maxNum");
            return (parseInt(str)>= parseInt(minNum) && parseInt(str)<= parseInt(maxNum))
        }
        return false;
    };

     var isFloatScope = function(str,obj)
	{
        if(isDouble(str)){
            var intNum = obj.getAttribute("IntNum");
            var decimalNum = obj.getAttribute("DecimalNum");
            var length = str.length;
            if(str.indexOf('.') != -1){
                if(parseInt(length - str.indexOf('.') -1) > parseInt(decimalNum))
                    return false;
                length = str.indexOf('.');
            }
            if(length > parseInt(intNum))
               return false;
            return true;
        }
        return false;
    };

    var isNormal = function(str)
	{
        var regTextChar = /([\"\'<>\/])+/;
        return !regTextChar.test(str);
    };

    var isEngNum = function(str)
    {
		var regTextEngNum = /^[a-zA-Z0-9]*$/;
		return regTextEngNum.test(str);
    };

    //英文数字
    var engNumSpe = function(str)
	{
	    var reg = /^([a-zA-Z0-9]|[._+*%<>()=\"\'\s])*$/;
		return reg.test(str);
	};

	/**
     *  时间验证
     */
    var RegExp_time = function(t) {
        if(!/\d{4}-\d{1,2}-\d{1,2}/.test(t))
            return false;
        var MonthDays = new Array(31,28,31,30,31,30,31,31,30,31,30,31);
        var tempArray = t.split("-");
        var year = tempArray[0];
        var month = tempArray[1];
        var day = tempArray[2];
        if(year <=0 || year > 9999)
            return false;
        if(month <=0 || month > 12)
            return false;
        var rightDay = 0;
        if(month == 2)
            rightDay = ((year%4 == 0) && (year%100 != 0)||(year%400 == 0)) ? 29 : 28;
        else
            rightDay = MonthDays[month - 1];
        if(day <= 0 || day > rightDay)
            return false;
        return true;
    };


    /**
     *	身份证验证
     */
    var RegExp_idCard = function(sId) {
    	var aCity={11:'北京',12:'天津',13:'河北',14:'山西',15:'内蒙古',21:'辽宁',22:'吉林',23:'黑龙江',31:'上海',32:'江苏',33:'浙江',34:'安徽',35:'福建',36:'江西',37:'山东',41:'河南',42:'湖北',43:'湖南',44:'广东',45:'广西',46:'海南',50:'重庆',51:'四川',52:'贵州',53:'云南',54:'西藏',61:'陕西',62:'甘肃',63:'青海',64:'宁夏',65:'新疆',71:'台湾',81:'香港',82:'澳门',91:'国外'};
		var iSum=0;
		var info='';
        if(sId.length == 15){
            if(!/^[1-9]\d{7}((0\d)|(1[0-2]))(([0|1|2]\d)|3[0-1])\d{3}$/.test(sId))
                return false;
            else {
                if(aCity[parseInt(sId.substr(0,2))]==null)
			        return false;
                return true;
            }
        }
        if(!/^\d{17}(\d|x)$/i.test(sId))
			return false;
		sId=sId.replace(/x$/i,"a");
		if(aCity[parseInt(sId.substr(0,2))]==null)
			return false;
		var sBirthday=sId.substr(6,4)+"/"+Number(sId.substr(10,2))+"/"+Number(sId.substr(12,2));
		var d=new Date(sBirthday);
		if(sBirthday!=(d.getFullYear()+"/"+ (d.getMonth()+1) + "/" + d.getDate()))
			return false;
		for(var i = 17;i>=0;i--)
			iSum += (Math.pow(2,i) % 11) * parseInt(sId.charAt(17 - i),11)
		if(iSum%11!=1)
			return false;
		return true;
    };

function trimLRBankSpace(){
      var inputs=document.getElementsByTagName("input");
      for(var i=0;i<inputs.length;i++){
          var ipt=inputs[i];
        ipt.value=ipt.value.replace(/(^\s*)|(\s*$)/g, "");
      }
      var textAreas=document.getElementsByTagName("textarea");
     for(var i=0;i<textAreas.length;i++){
         var tas=textAreas[i];
         tas.value=tas.value.replace(/(^\s*)|(\s*$)/g, "");
     }
}

function setInputValue(strValue){
    if(strValue==null){
        strValue="";
    }
    var inputs=document.getElementsByTagName("input");
    for(var i=0;i<inputs.length;i++){
        var ipt=inputs[i];
        if(inputs[i].type!="button"){
            ipt.value=strValue;
        }
    }
     var textAreas=document.getElementsByTagName("textarea");
     for(var i=0;i<textAreas.length;i++){
         var tas=textAreas[i];
         tas.value=strValue;
     }
}

function isMaxLen(o,nMaxLen){
 if(o.getAttribute && o.value.length>nMaxLen){
 o.value=o.value.substring(0,nMaxLen)
 }
}
//[^\u4e00-\u9fa5] !chinese
function judgeChinese(str)
{
	var reg = /^[\u4e00-\u9fa5]$/;
	return reg.test(str);
}
function calcLength(str,chinaLength)
{
	var length = 0;
	for(var i = 0; i < str.length; i++)
	{
		if(judgeChinese(str.charAt(i)))
		{
			length += chinaLength;
		}
		else
		{
			length++;
		}
	}
	return length;
}
function validateEnglishAndNumber(str)
{
	var reg = /^[a-zA-Z\d]*$/;
	return reg.test(str);
}

function validateFloat(str)
{
	var reg = /^[\d]+(\.[\d]+)?$/;
	return reg.test(str);
}
 
 
function trim(str)
{
   return str.replace(/(^\s+)|(\s+$)/g,"");
}

function isEmpty(s)
{
   return ((trim(s) == null)||(trim(s).length == 0));
}
