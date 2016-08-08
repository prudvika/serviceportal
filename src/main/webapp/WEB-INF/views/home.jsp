<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page session="false"%>

<center>
	<header>
		<h1>iQYY IoT Service Portal</h1>
	</header>
	<img src="<c:url value="/mainpic.jpg" />" alt="工业互联网图片">
	<hr>
	<sf:form method="post" action="logout">
		<button id="devicemanager" class="green" type="button"
			onclick="location='<c:url value="/devicemanager?type=Test" />'">Device
			Manager</button>
		<button id="monitor" class="green" type="button"
			onclick="location='<c:url value="/dashboardmanager" />'">Device
			Monitor</button>
		<button id="incidentmanager" class="green" type="button"
			onclick="location='<c:url value="/incidentmanager" />'">Incident
			Manager</button>
		<security:authorize url="/settings">
		<button id="settings" class="green" type="button"
			onclick="location='<c:url value="/settings" />'">Settings</button>
		</security:authorize>
		<input type="submit" name="logout" value="Logout" />
	</sf:form>
</center>