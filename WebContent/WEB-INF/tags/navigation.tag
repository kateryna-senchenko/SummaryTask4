<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags" prefix="mytags" %>

<%@ attribute name="showprofile" required="false" %>
<%@ attribute name="showlangselector" required="false" %>
<%@ attribute name="showhome" required="false" %>
<%@ attribute name="showlogout" required="false" %>
<%@ attribute name="showregister" required="false" %>
<%@ attribute name="showlogin" required="false" %>

<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="resources" />

<table>
	<tr>
		<c:if test="${false ne showhome}">
			<td>
				<form method="post" action="controller">
					<input type="hidden" name="command" value="goHome" />
					<button class="link"><fmt:message key="home"/></button>
				</form>
			</td>
		</c:if>
		<c:if test="${false ne showprofile}">
			<td>
				<form method="post" action="controller" >
					<input type="hidden" name="command" value="goToProfile" /> 
					<button class="link"><fmt:message key="profile"/></button>
				</form>
			</td>
		</c:if>
		<c:if test="${false ne showlangselector}">
			<td>
				<mytags:lang-select lang="ru"/> 
			</td>
		 	<td>
				<mytags:lang-select lang="en"/>
			</td>
		</c:if>
		<c:if test="${false ne showlogout}">
			<td>
				<form method="post" action="controller">
					<input type="hidden" name="command" value="logout" /> 
					<button class="link"><fmt:message key="logout"/></button>
				</form>
			</td>
		</c:if>
		<c:if test="${showregister}">
			<td>
				<a href='registration.jsp'><fmt:message key="link_register"/></a>
			</td>
		</c:if>
		<c:if test="${showlogin}">
			<td>
				<a href='login.jsp'><fmt:message key="link_login"/></a>
			</td>
		</c:if>
		
	</tr>
</table>