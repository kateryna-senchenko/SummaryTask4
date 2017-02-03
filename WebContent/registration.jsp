<%@ include file="/WEB-INF/jspf/head.jspf" %>

<!DOCTYPE html>
<html lang="${language}">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="stylesheet" href="style.css">

</head>

<body>
	<mytags:navigation showhome="false" showprofile="false" showlogout="false" showlogin="true"/>
	<fieldset>
		<legend>
			<fmt:message key="legend_registration"/>
		</legend>
		<form name="regForm" method="post" action="controller" accept-charset="UTF-8" >
			<input type="hidden" name="command" value="register"/>
			<input name="name" type="text" placeholder="<fmt:message key="name"/>" value="${name}" required/>
			<br>
			<br>
		        <input name="email" type="text" placeholder="<fmt:message key="email"/>" value="${email}" pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,3}$" required/>
			<br>
			<br>
			<input name="password" type="password" placeholder="<fmt:message key="password"/>" required/>
			<br>
			<br>
		        <input name="confirmPassword" type="password" placeholder="<fmt:message key="confirm_password"/>" required/>
			<br>
			<br>

			<input name="response_captcha" type="text"  placeholder="<fmt:message key="enter_captcha"/>" required/>

			<br>
			<img src="http://localhost:8080/SummaryTask4/captcha"/>
			
			<br><br>
			<input id="reg_button" class="button" type="submit" value="<fmt:message key="submit_register"/>"/>
			<br><br>
			<c:if test="${not empty registrationErrorMessage}">
   				<p class="errorMessage"><fmt:message key="${registrationErrorMessage}"/></p>
			</c:if>
	        
		</form>
	</fieldset>
</body>
</html>