<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/header.jsp"%>
<html>
	<head>
		<title></title>
		<link rel="stylesheet" type="text/css" href='<c:url value="/style.css"/>'/>
		<script src="<c:url value="/javascript/common.js"/>" type='text/javascript'></script>
		<script src='<c:url value="/javascript/jquery-1.3.2.min.js"/>' type='text/javascript'></script>
	<style>
	</style>
	<script type="text/javascript">
	function addDocument()
	{
		var docFile = document.getElementById("formFile").value;
		var formId = document.getElementById("form.formId").value;
		if(jQuery.trim(formId) == "")
		{
			alert("请输入模板Id!");
			return;
		}
		if(!isEngNum(jQuery.trim(formId)))
		{
			alert("模板Id只能是英文和数字!");
			return;
		}
		if(!isNull(docFile))
		{
			alert("请上传附件!");
			return false;
		}
		
	    var form = document.getElementById("search");
	    form.submit(); 
	}
	</script>
	</head>
	<body>
		<div class="caption">
			增加模板
		</div>
		<form name="search" id="search" method="post" action='<c:url value="/formpro/addFormTemplate.action"/>' enctype="multipart/form-data">
			<div class="box search">
				<table width="100%">
					<tr>
						<td width="10%" style="text-align: center" class="field">
							模板Id
						</td>
						<td width="40%">
							<input type="text" name="form.formId" id="form.formId"/>
						</td>
						<td width="10%" style="text-align: center" class="field">
							模板附件
						</td>
						<td width="40%">
							<input type="file" name="formFile" id="formFile" size="40">
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center">
							<input type="button" class="btn" value=" 增 加 " onclick="addDocument()" />
						</td>
					</tr>
				</table>
			</div>
		</form>
	</body>
</html>