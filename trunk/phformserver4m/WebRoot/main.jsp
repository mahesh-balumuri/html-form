<%@ include file="/common/header.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
	<title>main</title>
	<meta name="robots" content="all" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<script src="<c:url value="/javascript/jquery-1.3.2.min.js"/>" language="javascript"></script>
	<script type="text/javascript">
	function switchBar()
	{  
		var switchPoint = document.getElementById("switchPoint");
		if (switchPoint.innerHTML=='<DIV class=point3></DIV>')
		{  
			switchPoint.innerHTML='<DIV class=point4></DIV>';
			document.all("menuFrame").style.display="none";
		}
		else
		{  
			switchPoint.innerHTML='<DIV class=point3></DIV>';  
			document.all("menuFrame").style.display="";
		}
	}  
	
	function closeW()
	{
		window.close();
	}
	</script>
	<style type="text/css">
	<!--
	html, body {overflow-x:hidden; overflow-y:auto; }
	* { margin:0; padding:0;}
	.menuFrame { }
	#switchPoint { overflow:hidden; width:12px; background:url(<c:url value='/image/point_bg.gif'/>); cursor:pointer; color:#4c5e6a; font-family:Webdings; }
	.point3 { display:block; background:url(<c:url value='/image/ico_Point3.gif'/>) no-repeat; width:8px; height:80px; }
	.point4 { display:block; background:url(<c:url value='/image/ico_Point4.gif'/>) no-repeat; width:8px; height:80px; }
	.mainFrame { width:100%; }
	-->
	</style>
</head>
<body>
	<table height="100%" width="100%" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td class="menuFrame"><iframe src="<c:url value='/common/menuFrame.jsp'/>" name="menuFrame" id="menuFrame" scrolling="auto" frameborder="0" style="height:100%; width:170px" ></iframe></td>
	    <td onclick="switchBar();" id="switchPoint"><DIV class=point3></DIV></td>
		<td bgcolor="fcfcfe" class="mainFrame"><iframe src="#" name="mainFrame" id="mainFrame" scrolling="auto" frameborder="0" style="height:100%; width:100%;" ></iframe></td>
	  </tr>
	</table>
</body>
</html>