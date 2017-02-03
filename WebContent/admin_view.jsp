<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/admincheck.jspf" %>

<!DOCTYPE html>
<html lang="${language}">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="style.css">
</head>

<body>
	<mytags:navigation showhome="false" showprofile="false" />
	<fieldset class="wide">
		<legend><fmt:message key="tests"/></legend>
		<form method="post" action="controller">
			<input type="hidden" name="command" value="navigateToTestCreation" />
			<input type="submit" value="<fmt:message key="create_test"/>" />
		</form>
		<br><br>
		<table>
			<c:forEach items="${sessionScope.listOfTests}" var="test">
				<tr>
					<td>
						<p>${test.name}</p>
					</td>
					<td>
						<form method="post" action="controller">
							<input type="hidden" name="command" value="navigateToTestEdit" />
							<input type="hidden" name="testId" value="${test.id}" />
							<input type="submit" value="<fmt:message key="edit"/>" />
						</form>
						
					</td>
					<td>
						<form method="post" action="controller">
							<input type="hidden" name="command" value="deleteTest" />
							<input type="hidden" name="testId" value="${test.id}" />
							<input type="submit" value="<fmt:message key="delete"/>" />
						</form>
					</td>
				</tr>
			</c:forEach>
		</table>
	</fieldset>
	<fieldset class="wide">
		<legend><fmt:message key="students"/></legend>
			<table>
				<c:forEach items="${sessionScope.listOfClients}" var="student">
					<tr class="${student.status.name}">
						<td>
							<p>${student.name}</p>
						</td>
						<td>
							<p>${student.email}</p>
						</td>
						<td>
							<p><fmt:message key="${student.status.name}"/></p>
						</td>
						<td>
							<form method="post" action="controller">
								<input type="hidden" name="command" value="changeStatus" />
								<input type="hidden" name="userId" value="${student.id}" />
								<fmt:message key="block" var="block" />
								<fmt:message key="unblock" var="unblock" />
								<input type="submit" value="${student.status.name eq 'blocked' ? unblock : block} " />
							</form>
						</td>
						<td>
						<p>${student.averageResult}</p>
						</td>
					</tr>
				</c:forEach>
		</table>
	</fieldset>
</body>
</html>