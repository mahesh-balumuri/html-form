<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/header.jsp"%>
<html>
	<head>
		<title></title>
		<link rel="icon" href="favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" type="text/css" href='<c:url value="/style.css"/>' />
	</head>
	<style type="text/css">
	.w1 {
		width: 30%;
	}
	
	.w2 {
		width: 60%;
	}
	
	.w3 {
		width: 10%;
	}
	</style>
	<body>
		<div class="caption">
			角色列表 
		</div>
		<form name="search" method="post" action='<c:url value="/formpro/queryRoleList.action"/>'>
			<div class="box search">
				<table width="100%">
					<tr>
						<td width="20%" style="text-align: center" class="field">
							角色名称
						</td>
						<td width="80%">
							<input type="text" name="formRole.roleName" id="formRole.roleName" size="30" value="${formRole.roleName }"/>
						</td>
					</tr>
					<tr>
						<td width="100%" align="center" colspan="2">
							<input type="button" class="btn" value=" 查 询 " onclick="document.forms['search'].submit();" />
							<input type="button" class="btn" value=" 新 增 " onclick="window.location='<c:url value="/formpro/preAddRole.action"/>'" />
						</td>
					</tr>
				</table>
			</div>
			<div align="center" class="box list">
				<display:table name="pagination" style="width:100%" cellspacing="0" cellpadding="0" requestURI="" defaultsort="1" id="row" pagesize="10" htmlId="adminTable" class="tablelist">
					<display:column headerClass="w2" title="角色名称">
						${row.roleName }
					</display:column>
					<display:column headerClass="w3" title="详情">
						<a href='<c:url value="/formpro/queryRole.action"/>?formRole.id=${row.id }'>详情</a>
					</display:column>
				</display:table>
			</div>
		</form>
	</body>
</html>