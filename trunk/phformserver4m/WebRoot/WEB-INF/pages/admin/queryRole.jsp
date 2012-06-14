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
	function delDocument(id)
	{
		if(confirm("确认删除？"))
		{
			window.location='<c:url value="/formpro/deleteRole.action"/>' + "?formRole.id=" + id;		
		}
	}
	</script>
	</head>
	<body>
		<div class="caption">
			角色详情
		</div>
		<form name="search" id="search" method="post" action='<c:url value="/formpro/modifyRole.action"/>'>
			<div class="box search">
				<table width="100%">
					<tr>
						<td width="10%" style="text-align: center" class="field">
							角色名称
						</td>
						<td width="90%">
							<input type="text" name="formRole.roleName" id="formRole.roleName" value="${formRole.roleName }"/>
							<input type="hidden" name="formRole.id" id="formRole.id" value="${formRole.id }"/>
						</td>
					</tr>
					<tr>
						<td colspan="2" align="center">
							<input type="button" class="btn" value=" 修 改 " onclick="addDocument()" />
							<input type="button" class="btn" value=" 删 除 " onclick="delDocument('${formRole.id }')" />
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>