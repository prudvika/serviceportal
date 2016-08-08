<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ page session="false"%>

<header>
	<h1>iQYY Device Manager</h1>
</header>
<section id="list">
	<h1>View Device Details</h1>

	<div class="mode-toolbar">
		<div class="right">
			<button id="device_manager" class="green"
				onclick="location='<c:url value="/devicemanager?type=${device.type}" />'">
				Device Browser</button>
		</div>
	</div>

	<details open id="updateDevice">
		<summary>
			Details of the Device "
			<c:out value="${device.name}" />
			"
		</summary>

		<sf:form method="post" commandName="device">
			<table>
				<tr>
					<td align="left">Id:</td>
					<td align="left"><c:out value="${device.id}" /></td>
				<tr>
					<td align="left">Name:</td>
					<td align="left"><sf:input path="name" size="10" /></td>
				</tr>
				<c:forEach items="${device.attributes}" var="attribute">
					<tr>
						<td align="left"><c:out value="${attribute.key}" /></td>
						<td align="left"><input type="text"
							name="<c:out value="${attribute.key}" />"
							value="<c:out value="${attribute.value}" />" size="50" /></td>
					</tr>
				</c:forEach>

				<tr>
					<td align="left"><input type="submit" value="Update" /></td>
				</tr>
			</table>
		</sf:form>
	</details>

	<c:forEach items="${deviceHistory.historyData}" var="historyData">
		<details open id="history_<c:out value="${historyData.key}" />">
			<summary>
				History of the attribute "
				<c:out value="${historyData.key}" />
				"
			</summary>
			<table>
				<tr>
					<th>Time</th>
					<th>Value</th>
				<tr>

					<c:forEach items="${historyData.value}" var="historyDataValue">
						<tr>
							<td><c:out value="${historyDataValue.timeInFormat}" /></td>
							<td><c:out value="${historyDataValue.value}" /></td>
						</tr>
					</c:forEach>
			</table>
		</details>
	</c:forEach>
</section>