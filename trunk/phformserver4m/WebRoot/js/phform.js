function gatherIframeValues(frameId)
{
	var iiframe = window.frames[frameId];
	var iiframeDoc = iiframe.document;
	var input_elements = iiframeDoc.getElementsByTagName("input");
	var textarea_elements = iiframeDoc.getElementsByTagName("textarea");
	var values = "";
	for(var i = 0; i < input_elements.length; i++)
	{
		if(input_elements[i].type == "text")
		{
			if(input_elements[i].value.length > 1000)
			{
				alert("字段值过长!");
				return "";
			}
			else
			{
				if(testSpecial(input_elements[i].value))
				{
					alert("不能包含特殊字符#=!");
					return "";
				}
			}
			values += "#" + input_elements[i].id + "=" + input_elements[i].value;
		}
		else if(input_elements[i].type == "checkbox")
		{
			if(input_elements[i].checked)
			{
				values += "#" + input_elements[i].id + "=" + "1";
			}
		}
		else if(input_elements[i].type == "hidden")
		{
			values += "#" + input_elements[i].id + "=" + input_elements[i].value;
		}
	}
	for(var j = 0; j < textarea_elements.length; j++)
	{
		if(textarea_elements[j].value.length > 1000)
		{
			alert("字段值过长!");
			return "";
		}
		else
		{
			if(testSpecial(textarea_elements[j].value))
			{
				alert("不能包含特殊字符#=!");
				return "";
			}
		}
		values += "#" + textarea_elements[j].id + "=" + textarea_elements[j].value;
	}
	//return "iframeValue=" + encodeURIComponent(values);
	return encodeURIComponent(values).replace(/%/g,"08ba728cc56545fcbb8a3524a0e9ac68");
}

function gatherURI(frameId)
{
	var tmp = gatherIframeValues(frameId);
	if(tmp == "")
	{
		return "";
	}
	else
	{
		return "iframeValue=" + tmp;
	}
}

function testSpecial(str)
{
	var reg = /[#=]/;
	return reg.test(str);
}