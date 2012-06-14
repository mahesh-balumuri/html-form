<%@page import="com.opensymphony.xwork2.util.ValueStack"%>

<style type="text/css">
.error {
	color: red;
	text-align:center;
	font: 15px "微软雅黑", "宋体";
}
.message {
	color: #ff0000;
	text-align:center;
	font: 15px "微软雅黑", "宋体";
}
</style>

<script type="text/javascript">

    function showMyValidateMessages(message) {
        document.getElementById("myValidateMessagesTab").style.display = 'block';
        document.getElementById("myValidateMessages").innerHTML = message;
        window.scrollTo(0, 0);
        window.setTimeout("hideMessages('myValidateMessagesTab')", 1000);
    }

    function hideMessages(divId) {
        document.getElementById(divId).style.display = 'none';
    }

    function timeOutMessage(funName) {
        window.setTimeout("hideMessages('"+funName+"')", 1000);
    }
    function showAllMessages(message) {
        if (document.getElementById("myValidateMessages").innerHTML.indexOf(message) == -1) {
            document.getElementById("myValidateMessagesTab").style.display = "block";
            document.getElementById("myValidateMessages").innerHTML = document.getElementById("myValidateMessages").innerHTML + message;
            document.getElementById("myValidateImg").style.display = "";
            window.scrollTo(0, 0);
        }
    }

    function clearMessages() {
        document.getElementById("myValidateMessages").innerHTML = '';
        document.getElementById("myValidateImg").style.display = "none";
    }
</script>

<%
	if (request.getAttribute("struts.valueStack") != null) {
%>
<!--Action Message set in Actions-->
<s:if test="hasActionMessages()">
	<div class="message" id="actionMessages">
		<s:iterator value="actionMessages">
			<!--<img src='<c:url value="/images/right.png"/>'
				alt='<fmt:message key="icon.warning"/>' class="icon" />-->
			<s:property />
			<br />
		</s:iterator>
		<script type="text/javascript">
        timeOutMessage("actionMessages");
    </script>
	</div>
</s:if>

<%-- ActionError Messages - usually set in Actions --%>
<s:if test="hasActionErrors()">
	<div class="error" id="actionErrors" >		
		<c:forEach var="st" items="${actionErrors}">
			<!--<img src='<c:url value="/images/wrong.png"/>'
				alt='<fmt:message key="icon.warning"/>' class="icon" />-->
            <c:if test="${fn:contains(st, 'the request was rejected')}">
		     <s:text name="appcase.upload.maxsize.validate"/>
			</c:if>
			<c:if test="${!fn:contains(st, 'the request was rejected')}">
			<c:out value="${st}"/>
			</c:if>		
			<br />
		</c:forEach>
		<script type="text/javascript">
        timeOutMessage("actionErrors");
    </script>
	</div>
</s:if>

<%-- FieldError Messages - usually set by validation rules --%>
<s:if test="hasFieldErrors()">
    <div class="error" id="fieldErrors">
      <s:iterator value="fieldErrors">
          <s:iterator value="value">
            <!--<img src='<c:url value="/images/wrong.png"/>'
                alt='<fmt:message key="icon.warning"/>' class="icon" />-->
             <s:property/><br />
          </s:iterator>
      </s:iterator>
        <script type="text/javascript">
        timeOutMessage("fieldErrors");
    </script>
   </div>
</s:if>

<%
	}
%>
<s:property value="exception.message" />
<!--SuccessMessages (from session for redirect-->
  <c:if test="${ not empty messages}">
    <div class="message" id="messages">
		<c:forEach var="msg" items="${messages}">
			<!--<img src='<c:url value="/images/right.png"/>'
				alt='<fmt:message key="icon.information"/>' class="icon" />-->
			<c:out value="${msg}" />
			<br />
		</c:forEach>
		<script type="text/javascript">
            timeOutMessage("messages");
        </script>
	</div>
	<c:remove var="messages" scope="session" />
</c:if>

<%-- Error Messages (from session on JSPs, not through Struts --%>
 <c:if test="${not empty errorsMessages}">
    <div class="error" id="errorsMessages">
        <c:forEach var="msg" items="${errorsMessages}">
            <!--  <img src='<c:url value="/images/wrong.png"/>'
                alt='<fmt:message key="icon.information"/>' class="icon" />-->
            <c:out value="${msg}"/><br />
        </c:forEach>
        <script type="text/javascript">
            timeOutMessage("errorsMessages");
        </script>
    </div>
    <c:remove var="errorsMessages" scope="session"/>
</c:if>

<div id="myValidateMessagesTab" style="display: none;" class="error">
	<!--img id="myValidateImg" src='<c:url value="/images/wrong.png"/>' alt=""-->
	&nbsp;&nbsp;&nbsp;&nbsp;
	<span id="myValidateMessages"></span>
</div>