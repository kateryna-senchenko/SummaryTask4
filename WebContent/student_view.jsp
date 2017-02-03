<%@ include file="/WEB-INF/jspf/head.jspf" %>
<%@ include file="/WEB-INF/jspf/loggedInCheck.jspf" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>


<!DOCTYPE html>
<html lang="${language}">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link rel="stylesheet" href="style.css">
</head>

<body>
	<mytags:navigation showhome="false"/>
	<fieldset>
		<legend>
			<fmt:message key="hello"/> ${sessionScope.user.name}
		</legend>

		<form method="post" action="controller">
			<input type="hidden" name="command" value="chooseSubject" /> 
			<p>
				<fmt:message key="subject"/>
			</p>
			<select name="subject">
				<c:forEach items="${sessionScope.listOfSubjects}" var="subjectLoop">
					<option value="${subjectLoop.id}" ${subject.id eq subjectLoop.id ? 'selected=selected':''}>${subjectLoop.name}</option>
				</c:forEach>
			</select>
			<br><br>
			
			<p>
				<fmt:message key="sort"/>
			</p>
				<ul>
					<c:forEach items="${sessionScope.sortCriteria}" var="criteria">
						<li>
							<input type="checkbox" name="checkbox" value="${criteria}"
							${fn:contains(checkedSortCriteria, criteria) ? 'checked' : ''}/><fmt:message key="${criteria}"/>
						</li>
					</c:forEach>	
				</ul>
			<input type="submit" value="<fmt:message key="select"/>"/>
		</form>
		
		<br><br>
		<form method="post" action="controller">
			<input type="hidden" name="command" value="chooseTest" />
			<p>
				<fmt:message key="tests"/>
			</p>
			<ul>
				<c:forEach items="${sessionScope.listOfTests}" var="test">
					<li>
						<input type="radio" name="testId" value="${test.id}" />
						${test.name}
					</li>
				</c:forEach>
			</ul>
			<input type="submit" value="<fmt:message key="select"/>"/>
		</form>
		
		<c:if test="${not empty errorMessage}">
  				<p class="errorMessage"><fmt:message key="${errorMessage}"/></p>
		</c:if>
	</fieldset>
</body>
</html>
 