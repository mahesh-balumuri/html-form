<%@ include file="/common/header.jsp"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3c.org/TR/1999/REC-html401-19991224/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title></title>
		<meta http-equiv=Content-Type content="text/html; charset=utf-8">
		<link href="<c:url value="/css/index.css"/>" type=text/css rel=stylesheet>
		<script src="<c:url value="/javascript/jquery.js"/>" type=text/javascript></script>
		<script src="<c:url value="/js/phform.js"/>" type=text/javascript></script>
		<script type="text/javascript">
		</script>
	</head>
	<body>
		<div style="width: 100%" align="center">
			<iframe src="#" scrolling="auto" align="center" frameborder="0" style="width:590px;height: 562px;" name="iiframe0" id="iiframe0"></iframe>
			<script type="text/javascript">
				window.frames["iiframe0"].location.href="<c:url value='/formio/testFormOut.action'/>?formId=<%=request.getParameter("formId")==null?"":request.getParameter("formId").trim() %>&instanceId=<%=request.getParameter("instanceId")==null?"":request.getParameter("instanceId").trim() %>&version=<%=request.getParameter("version")==null?"":request.getParameter("version").trim() %>&roleId=<%=request.getParameter("roleId")==null?"":request.getParameter("roleId").trim() %>" + "&navigatorUserAgent=" + navigator.userAgent;
			</script>
		</div>
	</body>
</html>