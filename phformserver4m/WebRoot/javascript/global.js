/*
    Keep the user's cookie alive
*/

function MakeKeepAliveRequest()
{
	if (!KeepAliveUrl)
		return;

	if (KeepAliveTimer != null)
		window.clearTimeout(KeepAliveTimer);

	var x = null;
	if (typeof XMLHttpRequest != "undefined") 
	{
		x = new XMLHttpRequest();
	} 
	else 
	{
		try 
		{
			x = new ActiveXObject("Msxml2.XMLHTTP");
		} 
		catch (e) 
		{
			try 
			{
				x = new ActiveXObject("Microsoft.XMLHTTP");
			} 
			catch (e) 
			{
			}
		}
	}
	
	try
	{
		// don't do this asynchronously
		x.open("GET", KeepAliveUrl, false, "", "");
		x.send(null);
	}
	catch (e)
	{
	}
	
	KeepAliveTimer = window.setTimeout(MakeKeepAliveRequest, 59999);
}

function DetermineKeepAliveUrl()
{
	var scripts = document.getElementsByTagName("SCRIPT");
	var i;
	var url;
	
	for (i = 0; i < scripts.length; i++)
	{
		url = scripts[i].src.toLowerCase();
		if (url.indexOf('js/global.js') != -1)
			return url.replace('js/global.js', 'js/keepalive.aspx');
	}
	
	return null;
}

var KeepAliveUrl = DetermineKeepAliveUrl();
var KeepAliveTimer = window.setTimeout(MakeKeepAliveRequest, 59999);