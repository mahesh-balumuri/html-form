<%@ include file="/common/header.jsp"%>

<title>Data Access Error</title>

<head>
    <meta name="heading" content="Data Access Failure"/>
    <meta name="menu" content="AdminMenu"/>
</head>

<p>
    <c:out value="${requestScope.exception.message}"/>
</p>

<!--
<% 
//Exception ex = (Exception) request.getAttribute("exception");
//ex.printStackTrace(new java.io.PrintWriter(out)); 
%>
-->

<a href="index.jsp" onclick="history.back();return false">&#171; Back</a>
