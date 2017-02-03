<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/loggedInCheck.jspf" %>


<!DOCTYPE html>
<html lang="${language}">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="style.css">
</head>
<body>
	<mytags:navigation showhome="false" showprofile="false"/>
<p><fmt:message key="blocked_message"/></p>
</body>
</html>