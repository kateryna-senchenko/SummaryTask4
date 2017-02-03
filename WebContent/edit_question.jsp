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
		<legend> <fmt:message key="question"/> </legend>

		<form method="post" action="controller">
			<input type="hidden" name="command" value="${mode_question}" /> 
			<input
				name="questionText" type="text" placeholder="<fmt:message key="question"/>"
				value="${question.questionText}" required/> 
				
			<input type="submit" value="<fmt:message key="${mode_question}"/>" />
			<c:if test="${not empty errorMessage}">
  				<p class="errorMessage"><fmt:message key="${errorMessage}"/></p>
			</c:if>
		</form>
	</fieldset>
	
	<c:if test="${mode_question eq 'updateQuestion'}">	
	<fieldset>
		<legend><fmt:message key="answers"/></legend>
		<table>
			<tr><td>
				<form method="post" action="controller">
					<input type="hidden" name="command" value="navigateToAnswerCreation" />
					<input type="submit" value="<fmt:message key="add_answer"/>" />
				</form>
			</td></tr>
			
			<c:forEach items="${question.answers}" var="answer">
				<tr>
					<td>
						<fmt:message key="correct" var="correct" />
						<p>${answer.content} - ${answer.correct ? correct : ''}</p>
					</td>
					<td>
						<form method="post" action="controller">
							<input type="hidden" name="command" value="navigateToAnswerEdit" />
							<input type="hidden" name="answerId" value="${answer.id}" /> <input
								type="submit" value="<fmt:message key="edit"/>" />
						</form>
					</td>
					<td>
						<form method="post" action="controller">
							<input type="hidden" name="command" value="deleteAnswer" />
							<input type="hidden" name="answerId" value="${answer.id}" /> <input
								type="submit" value="<fmt:message key="delete"/>" />
						</form>
					</td>
				</tr>
			</c:forEach>
		</table>
	</fieldset>
	</c:if>
</body>
</html>
