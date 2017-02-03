<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ attribute name="lang" required="true" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resources" />


<form method="post" action="controller" accept-charset="UTF-8">
	<input type="hidden" name="command" value="changeLang"/>
	<input type="hidden" name="lang" value="${lang}"/>
	<input type="hidden" name="currentPage" value="${pageContext.request.requestURI}"/>
	<button id="log_button" class="link"><fmt:message key="lang_${lang}"/></button>
</form>