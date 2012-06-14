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
	    var form = document.getElementById("search");
	    form.submit(); 
	}
	</script>
	</head>
	<body>
		<div class="caption">
			已选择角色：${formRole.roleName }&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;已选择模板：${form.formName }
		</div>
		<form name="search" id="search" method="post" action='<c:url value="/formpro/addRoleRelation.action"/>'>
			<div class="box search">
				<input type="hidden" name="formRole.id" id="formRole.id" value="${formRole.id }"/>
				<input type="hidden" name="form.id" id="form.id" value="${form.id }"/>
				<table width="100%">
					<tr>
						<td width="8%" style="text-align: center;background-image: none;background-color: #FFE4C4;" class="field">
							只读
						</td>
						<td width="25%" style="text-align: center;background-image: none;background-color: #FFE4C4;" class="field">
							元素Id
						</td>
						<td width="8%" style="text-align: center;background-image: none;background-color: #F0E68C;" class="field">
							只读
						</td>
						<td width="25%" style="text-align: center;background-image: none;background-color: #F0E68C;" class="field">
							元素Id
						</td>
						<td width="8%" style="text-align: center;background-image: none;background-color: #C0C0C0;" class="field">
							只读
						</td>
						<td width="25%" style="text-align: center;background-image: none;background-color: #C0C0C0;" class="field">
							元素Id
						</td>
					</tr>
					<tr>
						<td colspan="6" align="center" style="height: 200px;" width="100%">
							<div style="height: 200px;width: 100%;overflow-x: hidden;overflow: auto;">
								<table width="100%">
								<%
								if(null != request.getAttribute("newMap"))
								{
									Map newMap = (Map)request.getAttribute("newMap");
									Iterator it = newMap.entrySet().iterator();
									int i = 0;
									while(it.hasNext())
									{
										if(i % 3 == 0)
										{
								%>
								<tr>
								<%
										}
										java.util.Map.Entry entry = (java.util.Map.Entry)it.next();
										String entryId = entry.getKey().toString();
										boolean entryValue = entry.getValue().toString().equals("true") ? true : false;
								%>
						<td width="8%" style="text-align: center;">
							<input type="checkbox" name="elementIds" value="<%=entryId %>" <%=entryValue ? "checked" : "" %>/>
						</td>
						<td width="25%" style="text-align: center;">
							<%=entryId %>
						</td>
								<%		
										if(i % 3 == 2 || i == newMap.size() - 1)
										{
								%>
								</tr>
								<%
										}
										i++;
									}
								}
								%>
								</table>
							</div>
						</td>
					</tr>
					<tr>
						<td colspan="6" align="center">
							<input type="button" class="btn" value=" 保 存 " onclick="addDocument()" />
						</td>
					</tr>
				</table>
			</div>
		</form>
		<div class="box search" id="my_box_div">
			<iframe src="<c:url value='/formpro/formRoleOut.action'/>?formId=${form.formId }" scrolling="auto" align="center" frameborder="0" style="width:100%;height: 300px;overflow-x: hidden;" name="iiframe" id="iiframe"></iframe>
		</div>
		<script type="text/javascript">
			try
			{
				var hei = window.parent.document.body.scrollHeight - 155;
				document.getElementById("my_box_div").style.height = hei - 144;
				document.getElementById("iiframe").style.height = hei - 156;
			}
			catch(e)
			{
			}
		</script>
	</body>
</html>