<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%@ include file="/common/header.jsp"%>
<%@ page language="java" pageEncoding="UTF-8"%>
<head>
	<title>menu</title>
	<meta name="robots" content="all" />
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" type="text/css" href="<c:url value='/style.css'/>" media="screen" />
	<script type="text/javascript" src="<c:url value='/javascript/sdmenu.js'/>"></script>
    <style type="text/css">
	<!--
	html {height:100%;}
	body{background:#f9fdff}
	-->
    </style>
	<script type="text/javascript">
	// <![CDATA[
	var myMenu;
	window.onload = function() {
		myMenu = new SDMenu("my_menu");
		myMenu.init();
	};
	
	function showContentPage(forwardPage,name)
	{
		myMenu.onclick(name);
	  	parent.mainFrame.location.href = forwardPage;
	 
	  	return false;
	}
	// ]]>
	</script>
</head>
<body>
	<div class="menu" id="my_menu">
		<div class="collapsed">
			<span><font>表单模板</font></span>		
 			<a href="javascript:void(0);" onclick="showContentPage('<c:url value="/formpro/queryTemplateList.action"/>','模板列表')" >模板列表</a>
		</div>
		<div class="collapsed">
			<span><font>表单角色</font></span>		
 			<a href="javascript:void(0);" onclick="showContentPage('<c:url value="/formpro/queryRoleList.action"/>','角色列表')" >角色列表</a>
 			<a href="javascript:void(0);" onclick="showContentPage('<c:url value="/formpro/queryRoleListStep1.action"/>','表单和角色关系')" >表单和角色关系</a>
		</div>
	</div>
</body>
</html>