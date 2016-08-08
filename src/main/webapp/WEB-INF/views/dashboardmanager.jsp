<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ page session="false"%>

<header>
	<h1>iQYY Dashboard Manager</h1>
</header>
<section id="list">
	<h1>Dashboard Browser</h1>
	<div class="mode-toolbar">
		<div class="right">
			<button id="platform" class="green"
				onclick="location='<c:url value="/" />'">Back to Platform</button>
			<button id="dashboard_settings" class="green"
				onclick="location='<c:url value="/dashboardmanager/settings?type=Test" />'">Dashboard
				Settings</button>
		</div>
	</div>

	<details open id="createDashboard">
		<summary>Create a dashboard</summary>

		<sf:form method="post" commandName="dashboard">
			<table border="0">
				<tr>
					<td align="left">Name:</td>
					<td align="left"><sf:input path="name" size="20" /></td>
				</tr>
				<tr>
					<td align="left">Author:</td>
					<td align="left"><sf:input path="author" size="20" /></td>
				</tr>
				<tr>
					<td align="left"><input type="submit" value="Submit" /></td>
				</tr>
			</table>
		</sf:form>
	</details>

	<details open>
		<summary>
			<c:out value="${fn:length(dashboardList)}" />
			Dashboards
		</summary>

		<table border="1">
			<tr>
				<th>Name</th>
				<th>Author</th>
			</tr>
			<c:forEach items="${dashboardList}" var="dashboard">
				<tr>
					<td align="left"><a
						href="<c:url value="/dashboardmanager/dashboardeditor?name=${dashboard.name}" />"><c:out
								value="${dashboard.name}" /></a></td>
					<td align="left"><c:out value="${dashboard.author}" /></td>
				</tr>
			</c:forEach>
		</table>
	</details>
</section>