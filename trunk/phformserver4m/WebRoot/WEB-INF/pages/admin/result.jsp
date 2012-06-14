<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/common/header.jsp"%>
<html>
	<head>
		<title></title>
		<link rel="icon" href="favicon.ico" type="image/x-icon" />
		<link rel="stylesheet" type="text/css" href='<c:url value="/style.css"/>' />
	</head>
	<style>
</style>
	<body>
		<div class="caption">
			操作结果
		</div>
		<div class="box search">
			<table width="100%">
				<tr>
					<td style="text-align: center" class="field">
						<c:if test="${result.successful }">
							操作成功<c:if test="${not empty result.message }">：${result.message }</c:if>
						</c:if>
						<c:if test="${!result.successful }">
							操作失败<c:if test="${not empty result.message }">：${result.message }</c:if>
						</c:if>
					</td>
				</tr>
				<tr>
					<td align="center">
						<input type="button" class="btn" value=" 返 回 " onclick="addDocument()" style="display: none;"/>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>
