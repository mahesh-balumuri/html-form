<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/header.jsp"%>
<html>
	<head>
		<title></title>
		<link rel="stylesheet" type="text/css" href='<c:url value="/style.css"/>'/>
		<script src="<c:url value="/javascript/common.js"/>" type=text/javascript></script>
	<style>
	</style>
	<script type="text/javascript">
	function addDocument()
	{
		var docFile = document.getElementById("formRole.roleName").value;
		if(!isNull(docFile))
		{
			alert("请输入角色名称!");
			return false;
		}
		
	    var form = document.getElementById("search");
	    form.submit(); 
	}
	</script>
	</head>
	<body>
		<div class="caption">
			增加角色
		</div>
		<form name="search" id="search" method="post" action='<c:url value="/formpro/addRole.action"/>'>
			<div class="box search">
				<table width="100%">
					<tr>
						<td width="10%" style="text-align: center" class="field">
							角色名称
						</td>
						<td width="90%">
							<input type="text" name="formRole.roleName" id="formRole.roleName" value="${formRole.roleName }"/>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="button" class="btn" value=" 增 加 " onclick="addDocument()" />
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>