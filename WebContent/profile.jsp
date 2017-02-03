<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/loggedInCheck.jspf" %>


<!DOCTYPE html>
<html lang="${language}">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="style.css">
</head>
<body>
	<mytags:navigation showprofile="false"/>
	<fieldset class="wide" > 
		<legend><fmt:message key="profile"/></legend>

		<p>${sessionScope.user.name} (${sessionScope.user.email})</p>
		<br>
		
		<table>
			<tr>
				<td>
					<p><b><fmt:message key="subject"/></b></p>
				</td>
				<td>
					<p><b><fmt:message key="test"/></b></p>
				</td>
				<td>
					<p><b><fmt:message key="result"/></b></p>
				</td>
			</tr>
			<c:forEach items="${sessionScope.user.results}" var="result">
				<tr>
					<td>
						<p>${result.test.subject.name}</p>
					</td>
					<td>
						<p>${result.test.name}</p>
					</td>
					<td>
						<p>${result.result}</p>
					</td>
				</tr>
			</c:forEach>
			<tr><td>
			<p>Average ${user.averageResult}</p>
			</td></tr>
		</table>
	</fieldset>
</body>
</html>