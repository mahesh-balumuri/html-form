	var _isNull = function(str)
	{
        var reg = /(\s|　)+/gi;
        str = str.replace(reg,'');
        var regTextNull = /.+/;
        return regTextNull.test(str);
	};

    var _isEng = function(str)
    {
		var regTextEng = /^[a-zA-Z]*$/;
		return regTextEng.test(str);
    };

    var _isNumber = function(str)
	{   if(_isNull(str)){
           var regTextInteger = /^[0-9]*$/;
           return regTextInteger.test(str);
        }
        return false;
    };

    var _isInteger = function(str)
	{
        var regTextInteger = /^[^0](\d)*$/;
        if(_isNumber(str)){
//            if(str.length == 1)
//                return false;
            return !regTextInteger.test(str);
        }
        return true;
    };

    var _isIntegerScope = function(str,obj)
	{
        if(_isNumber(str)){

          var minNum = obj.getAttribute("minNum");
          var maxNum = obj.getAttribute("maxNum");
          return (parseInt(str)>= parseInt(minNum) && parseInt(str)<= parseInt(maxNum))
        }
        return false;
	};

     var _isPhone = function(str)
	{
//        var regTextPhone = /^[0-9]{3,4}\-[0-9]{3,8}$/;
        var regTextPhone = /^[0-9\-\/]*$/;
        return regTextPhone.test(str);
	};

	var _isEmail=function(str){
		var regTextEmail= /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
		return regTextEmail.test(str);
	}



    var _isDouble = function(str)
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

    var _isDoublerScope = function(str,obj)
	{
        if(_isDouble(str)){
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

    var _isFloatScope = function(str,obj)
	{
        if(_isDouble(str)){
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

    var _isNormal = function(str)
	{
        var regTextChar = /([\"\'<>\/])+/;
        return !regTextChar.test(str);
    };

    var _isEngNum = function(str)
    {
		var regTextEngNum = /^[a-zA-Z0-9]*$/;
		return regTextEngNum.test(str);
    };

    //英文数字
    var _engNumSpe = function(str)
	{
	    var reg = /^([a-zA-Z0-9]|[._+*%<>()=\"\'\s])*$/;
		return reg.test(str);
	};

//[^\u4e00-\u9fa5] !chinese
function judgeChinese(str)
{
	var reg = /^[\u4e00-\u9fa5]$/;
	return reg.test(str);
}
function _calcLength(str,zhLengthInDB)
{
	var length = 0;
	for(var i = 0; i < str.length; i++)
	{
		if(judgeChinese(str.charAt(i)))
		{
			length += zhLengthInDB;
		}
		else
		{
			length++;
		}
	}
	return length;
}
function calcLength(str,zhLengthInDB)
{
	var len = 0;  
	for (var i = 0; i < str.length; i++)
	{  
		if (str.charCodeAt(i) > 127 || str.charCodeAt(i) == 94) 
		{  
			len += zhLengthInDB;  
		} 
		else 
		{  
			len ++;  
		}  
	}  
	return len;  
}
function _validateEnglishAndNumber(str)
{
	var reg = /^[a-zA-Z\d]*$/;
	return reg.test(str);
}

function _validateFloat(str)
{
	var reg = /^[\d]+(\.[\d]+)?$/;
	return reg.test(str);
}