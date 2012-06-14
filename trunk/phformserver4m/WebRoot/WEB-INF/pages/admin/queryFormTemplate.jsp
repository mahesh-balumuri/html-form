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
	function showTemplate(formId)
	{
		window.open("<c:url value='/formio/formOut.action'/>?formId=" + formId,"newwindow","toolbar=no,menubar=no,scrollbars=yes,resizable=yes,location=no,status=no");
		return;
	}
	function downLoadTemplate(formId)
	{
		window.location="<c:url value='/formpro/formDownload.action'/>?formId=" + formId;
	}
	function addDocument()
	{
		var docFile = document.getElementById("formFile").value;
		if(!isNull(docFile))
		{
			alert("请上传附件!");
			return false;
		}
		
	    var form = document.getElementById("search");
	    form.submit(); 
	}
	function delDocument(id)
	{
		if(confirm("确认删除？"))
		{
			window.location='<c:url value="/formpro/deleteFormTemplate.action"/>' + "?form.id=" + id;		
		}
	}
	</script>
	</head>
	<body>
		<div class="caption">
			模板详情
		</div>
		<form name="search" id="search" method="post" action='<c:url value="/formpro/modifyFormTemplate.action"/>' enctype="multipart/form-data">
			<div class="box search">
				<table width="100%">
					<tr>
						<td width="10%" style="text-align: center" class="field">
							模板Id
						</td>
						<td width="40%">
							${form.formId }
							<input type="hidden" name="form.id" id="form.id" value="${form.id }"/>
						</td>
						<td width="10%" style="text-align: center" class="field">
							模板名称
						</td>
						<td width="40%">
							${form.formName }
						</td>
					</tr>
					<tr>
						<td width="10%" style="text-align: center" class="field">
							模板附件
						</td>
						<td width="90%" colspan="3">
							<input type="file" name="formFile" id="formFile" size="40">
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<input type="button" class="btn" value="查看模板" onclick="showTemplate('${form.formId }')" />
							<input type="button" class="btn" value="下载模板" onclick="downLoadTemplate('${form.formId }')" />
							<input type="button" class="btn" value=" 修 改 " onclick="addDocument()" />
							<input type="button" class="btn" value=" 删 除 " onclick="delDocument('${form.id }')" />
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>