<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ page session="false"%>

<header>
	<h1>iQYY Device Manager</h1>
</header>
<section id="list">
	<h1>Device Browser</h1>

	<div class="mode-toolbar">
		<div class="left">
			<div>Device Type:</div>

			<c:set var="index" value="0" />
			<c:forEach items="${deviceTypeList}" var="deviceType"
				varStatus="status">
				<c:set var="btnClass" value="" />
				<c:choose>
					<c:when test="${status.first}">
						<c:set var="btnClass" value="split_left" />
					</c:when>
					<c:when test="${status.last}">
						<c:set var="btnClass" value="split_right" />
					</c:when>
					<c:otherwise>
					</c:otherwise>
				</c:choose>

				<c:if test="${deviceType.name==param.type}">
					<c:set var="btnClass" value="${btnClass}${' active'}" />
					<c:set var="index" value="${status.index}" />
				</c:if>

				<button id="<c:out value="${deviceType.id}" />"
					class="<c:out value="${btnClass}" />"
					onclick="location='<c:url value="/devicemanager?type=${deviceType.name}" />'">
					<c:out value="${deviceType.name}" />
				</button>
			</c:forEach>
		</div>
		<div class="right">
			<button id="platform" class="green"
				onclick="location='<c:url value="/" />'">Back to Platform</button>
			<button id="device_type" class="green"
				onclick="location='<c:url value="/devicemanager/devicetype?type=${deviceTypeList[index].name}" />'">Manage
				Device Type</button>
		</div>
	</div>
	
	<details open id="registerDevice">
			<summary>Register a new <c:out value="${deviceTypeList[index].name}" /></summary>
			
			<sf:form method="post" commandName="device">
				<table>
					<tr>
						<td align="left">Name:</td>
						<td align="left"><sf:input path="name" size="10" /></td>
					</tr>
			        <c:forEach items="${deviceTypeList[index].attributes}" var="attribute">
					<tr>
						<td align="left"><c:out value="${attribute.name}" /></td>
						<td align="left"><input type="text"
							name="<c:out value="${attribute.name}" />" size="50" /></td>
					</tr>
					</c:forEach>

					<tr>
						<td align="left"><input type="submit" value="Register" /></td>
					</tr>
				</table>
			</sf:form>

			<div class="note">
				Download client <a
					href="http://www.i-qyy.com/devicemanager-1.0-client.jar">here</a>
				and execute the following command with Java: <br /> <i>java
					-jar devicemanager-1.0-client.jar "Device Name" "Device Type" "Device Number" 
					http://www.i-qyy.com/devicemanager-1.0</i>
			</div>
		</details>

	<details open>
		<summary>
			Devices -
			<c:out value="${fn:length(deviceList)}" />
			<c:out value="${param.type}" />
			s
		</summary>
		<table border="1">
			<tr>
				<th>ID</th>
				<th>Name</th>

				<c:forEach items="${deviceTypeList[index].attributes}"
					var="attribute">
					<th><c:out value="${attribute.name}" /></th>
				</c:forEach>
			</tr>
			<c:forEach items="${deviceList}" var="device">
				<tr>
					<td align="left"><c:out value="${device.id}" /></td>
					<td align="left"><a href="<c:url value="${'/devicemanager/device?id='}${device.id}" />"><c:out value="${device.name}" /></a></td>

					<c:forEach items="${deviceTypeList[index].attributes}"
						var="attribute">
						<td><c:out value="${device.attributes[attribute.name]}" /></td>
					</c:forEach>
				</tr>
			</c:forEach>
		</table>
	</details>
</section>