<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/loggedInCheck.jspf" %>
<%@ taglib uri="/WEB-INF/timerTag.tld" prefix="ct" %>

<c:if test="${empty test}">
<c:redirect url="/login.jsp"/>
</c:if>

<!DOCTYPE html>
<html lang="${language}">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="style.css">
<script src="timer.js"></script>

</head>

<body>
	<mytags:navigation/>
	<fieldset>
		<legend>${sessionScope.test.name}</legend>
		<div id='timer'></div>
		<ct:timer duration="${test.duration}" location="timer"/>
		<br>
		<form id="test" method="post" action="controller">
			<input type="hidden" name="command" value="submitTest" />
			<ul>
				<c:forEach items="${sessionScope.test.questions}" var="question">
					<li name="${question.id}">${question.questionText}
						<ul>
							<c:forEach items="${question.answers}" var="answer">
								<li><input type="checkbox" name="checkbox"
									value="${answer.id}">${answer.content}</li>
							</c:forEach>
						</ul>
					</li>
				</c:forEach>
			</ul>
			<input type="submit" value="<fmt:message key="submit"/>" class="button">
		</form>
	</fieldset>
</body>
</html>