<%@ include file="/WEB-INF/jspf/head.jspf" %>

<!DOCTYPE html>
<html lang="${language}">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="style.css">
</head>

<body>
	<mytags:navigation showhome="false" showprofile="false" showlogout="false" showregister="true"/>
	<fieldset>
		<legend>
			<fmt:message key="legend_login"/>
		</legend>
		<form method="post" action="controller" accept-charset="UTF-8">
			<input type="hidden" name="command" value="login"/>
			<input name="email" type="text" placeholder="<fmt:message key="email"/>" value="${email}" required/>
			<br>
			<br>
			<input name="password" type="password" placeholder="<fmt:message key="password"/>" required/>
			<br>
			<br>
			<input id="log_button" class="button" type="submit" value="<fmt:message key="submit_login"/>"/>
			<br><br>
			<c:if test="${not empty loginErrorMessage}">
		   		<p class="errorMessage"> <fmt:message key="${loginErrorMessage}"/> </p>
			</c:if>
		</form>
	</fieldset>
</body>

</html>