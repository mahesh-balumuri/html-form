<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/header.jsp"%>
<html>
	<head>
		<title></title>
		<link rel="icon" href="favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" type="text/css" href='<c:url value="/style.css"/>' />
		<script type="text/javascript">
		function showTemplate(formId)
		{
			window.open("<c:url value='/formio/formOut.action'/>?formId=" + formId,"newwindow","toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no")
			return;
		}
		</script>
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
			待选择模板列表(step2)-已选择角色：${formRole.roleName }
		</div>
		<form name="search" method="post" action='<c:url value="/formpro/queryTemplateListStep2.action"/>'>
			<div class="box search">
				<table width="100%">
					<tr>
						<td width="20%" style="text-align: center" class="field">
							模板Id
						</td>
						<td width="30%">
							<input type="text" name="form.formId" id="form.formId" size="30" value="${form.formId }"/>
							<input type="hidden" name="formRole.id" id="formRole.id" value="${formRole.id }"/>
						</td>
						<td width="20%" style="text-align: center" class="field">
							模板名称
						</td>
						<td width="30%">
							<input type="text" name="form.formName" id="form.formName" size="30" value="${form.formName }"/>
						</td>
					</tr>
					<tr>
						<td width="100%" align="center" colspan="4">
							<input type="button" class="btn" value=" 查 询 " onclick="document.forms['search'].submit();" />
						</td>
					</tr>
				</table>
			</div>
			<div align="center" class="box list">
				<display:table name="pagination" style="width:100%" cellspacing="0" cellpadding="0" requestURI="" defaultsort="1" id="row" pagesize="10" htmlId="adminTable" class="tablelist">
					<display:column headerClass="w1" title="模板Id">
						<a href="javascript:;" onclick="showTemplate('${row.formId }')">${row.formId}</a>
					</display:column>
					<display:column headerClass="w2" title="模板名称">
						${row.formName }
					</display:column>
					<display:column headerClass="w3" title="选择">
						<a href='<c:url value="/formpro/getRoleRelationList.action"/>?form.id=${row.id }&formRole.id=${formRole.id }'>选择</a>
					</display:column>
				</display:table>
			</div>
		</form>
	</body>
</html>