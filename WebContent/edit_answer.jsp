<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/admincheck.jspf" %>

<!DOCTYPE html>
<html lang="${language}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="style.css">
</head>

<body>
	<mytags:navigation showprofile="false" />
	<fieldset>
		<legend> <fmt:message key="answer_option"/> </legend>

		<form method="post" action="controller">
			<input type="hidden" name="command" value="${mode_answer}" /> 
			<input name="content" type="text" placeholder="<fmt:message key="answer_option"/>" value="${answer.content}" required/>
			<input type="checkbox" name="checkbox" value="correct" ${answer.correct ? 'checked':''}/>
			
			<input type="submit" value="<fmt:message key="${mode_answer}"/>" />
			<c:if test="${not empty errorMessage}">
  				<p class="errorMessage"><fmt:message key="${errorMessage}"/></p>
			</c:if>
		</form>
	</fieldset>
</body>
</html>	