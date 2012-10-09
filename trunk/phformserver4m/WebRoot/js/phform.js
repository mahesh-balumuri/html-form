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
			values += "#" + encodeValue(input_elements[i].id) + "@" + encodeValue(input_elements[i].value);
		}
		else if(input_elements[i].type == "checkbox")
		{
			if(input_elements[i].checked)
			{
				values += "#" + encodeValue(input_elements[i].id) + "@" + "1";
			}
		}
		else if(input_elements[i].type == "hidden")
		{
			values += "#" + encodeValue(input_elements[i].id) + "@" + encodeValue(input_elements[i].value);
		}
	}
	for(var j = 0; j < textarea_elements.length; j++)
	{
		if(textarea_elements[j].value.length > 1000)
		{
			alert("字段值过长!");
			return "";
		}
		values += "#" + encodeValue(textarea_elements[j].id) + "@" + encodeValue(textarea_elements[j].value);
	}
	//return "iframeValue=" + encodeURIComponent(values);
	return values;
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

function encodeValue(value2Encode)
{
	return encodeURIComponent(value2Encode).replace(/%/g,",");
}