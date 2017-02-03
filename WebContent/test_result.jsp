<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/loggedInCheck.jspf" %>

<!DOCTYPE html>
<html lang="${language}">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="style.css">
</head>
<body>
	<mytags:navigation />
	<fieldset>
		<legend>${sessionScope.test.name}</legend>

		<p>
			<fmt:message key="result"/> 
			${sessionScope.testResult.result} %
		</p>
		
		
	</fieldset>
</body>
</html>