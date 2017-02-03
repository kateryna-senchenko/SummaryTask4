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
	<fieldset class="wide">
	
		<fmt:message key="new_test" var="new_test"/>
		<legend>${mode_test eq 'updateTest' ? test.name : new_test }</legend>
	
		<form method="post" action="controller">
						<input type="hidden" name="command" value="${mode_test}" />
			<table>
				<tr>
					<td>
						<p><fmt:message key="subject"/></p>
					</td>
					<td>
						<select name="subject">
							<c:forEach items="${sessionScope.listOfSubjects}" var="subjectLoop">
								<option value="${subjectLoop.id}"
									${test.subject.id eq subjectLoop.id ? 'selected=selected':''}>${subjectLoop.name}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>
						<p><fmt:message key="test_name"/></p>
					</td>
					<td>
						<input name="testName" type="text" placeholder="<fmt:message key="test_name"/>" value="${test.name}" required/> 
					</td>
				</tr>
				<tr>
					<td>
						<p><fmt:message key="duration"/></p>
					</td>
					<td>
						<input name="duration" type="number" min="1" placeholder="<fmt:message key="duration"/>" value="${test.duration}" required/><p>min</p>
					</td>
				</tr>
				<tr>
					<td>
						<p><fmt:message key="complexity"/></p>
					</td>
					<td>
						<select name="complexity">
						<c:forEach items="${sessionScope.complexityLevels}" var="levelLoop">
							<option value="${levelLoop.id}"
								${test.complexity.id eq levelLoop.id ? 'selected=selected':''}>${levelLoop.name}</option>
						</c:forEach>
					</select>
					</td>
				</tr>
				<tr>
					<td>
						<input type="submit" value="<fmt:message key="${mode_test}"/>" />
					</td>
				</tr>
			</table>
			<c:if test="${not empty errorMessage}">
  				<p class="errorMessage"><fmt:message key="${errorMessage}"/></p>
			</c:if>
		</form>
	</fieldset>
	
	<c:if test="${mode_test eq 'updateTest'}">
	<fieldset class="wide">
		<legend> <fmt:message key="questions"/></legend>
		<table>
			<tr><td>
				<form method="post" action="controller">
					<input type="hidden" name="command"
						value="navigateToQuestionCreation" /> <input type="submit"
						value="<fmt:message key="add_question"/>" />
				</form>
			</td></tr>
			<c:forEach items="${sessionScope.test.questions}" var="question">
				<tr>
					<td>
						<p>${question.questionText}</p>
					</td>
					<td>
						<form method="post" action="controller">
							<input type="hidden" name="command" value="navigateToQuestionEdit" />
							<input type="hidden" name="questionId" value="${question.id}" /> <input
								type="submit" value="<fmt:message key="edit"/>" />
						</form>
					</td>
					<td>
						<form method="post" action="controller">
							<input type="hidden" name="command" value="deleteQuestion" />
							<input type="hidden" name="questionId" value="${question.id}" />
							<input type="submit" value="<fmt:message key="delete"/>" />
						</form>
					</td>
				</tr>
			</c:forEach>
		</table>
	</fieldset>
	</c:if>	
</body>
</html>