<%@ include file="/common/header.jsp"%>
<head>
    <title><fmt:message key="404.title"/></title>
    <meta name="heading" content="<fmt:message key='404.title'/>"/>
</head>

<p>
    <fmt:message key="404.message">
        <fmt:param><c:url value="/index.jsp"/></fmt:param>
    </fmt:message>
</p>

