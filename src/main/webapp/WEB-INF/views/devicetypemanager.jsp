<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ page session="false"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="utf-8">
<title>iQYY Device Type Manager</title>
<link rel="stylesheet" href="<c:url value="/style.css" />">
</head>
<body>
	<header>
		<h1>iQYY Device Manager</h1>
	</header>
	<section id="list">
		<h1>Device Type Management</h1>

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
						onclick="location='<c:url value="/devicemanager/devicetype?type=${deviceType.name}" />'">
						<c:out value="${deviceType.name}" />
					</button>
				</c:forEach>
			</div>
			<div class="right">
				<button id="device_manager" class="green"
					onclick="location='<c:url value="/devicemanager?type=${deviceTypeList[index].name}" />'">
					Device Browser</button>
			</div>
		</div>

		<details open>
			<summary>
				Customize <c:out value="${deviceTypeList[index].name}" />
			</summary>
			
			<form method="put" action="devicetype/${deviceTypeList[index].name}/datafile">
			   <table border="0">
			     <tr>
			        <td>Client Data File:</td>
			        <td><input type="text" name="dataFile" value="${deviceTypeList[index].dataFile}" /></td>
			        <td><input type="submit" value="Update" /></td>
			     </tr>
			   </table>
			</form>
			
			<sf:form method="post" action="devicetype/${deviceTypeList[index].name}/addattribute" commandName="attribute">
			<table border="1">
				<tr>
					<th>Attribute Name</th>
					<th>Attribute Type</th>
					<th>Keep History</th>
					<th>Client Data Field</th>
				</tr>
				<c:forEach items="${deviceTypeList[index].attributes}" var="attribute">
					<tr>
						<td align="left"><c:out value="${attribute.name}" /></td>
						<td align="left"><c:out value="${attribute.type }" /></td>
						<td align="left"><c:out value="${attribute.keepHistory}" /></td>
						<td align="left"><c:out value="${attribute.dataField}" /></td>
				    </tr>
				</c:forEach>
				<tr>
				    <td><sf:input path="name" size="10" /></td>
			        <td>
			            <sf:select path="type">
			               <sf:option value="String">String</sf:option>
			               <sf:option value="Int">Int</sf:option>
			            </sf:select>
			        </td>
			        <td><sf:checkbox path="keepHistory" /></td>
			        <td>
			            <sf:select path="dataField">
			              <c:forEach items="${deviceTypeList[index].dataFields}" var="dataField">
			                 <sf:option value="${dataField}">${dataField}</sf:option>
			              </c:forEach>
			            </sf:select>
			        </td>
				</tr>
				<tr>
				    <td />
				    <td />
				    <td />
				    <td><input type="submit" value="Add Attribute" /></td>
				</tr>
			</table>
			</sf:form>
		</details>
		
		<details>
		    <summary>Create New Device Type</summary>
		    
		    <sf:form method="post" commandName="deviceType">
		      <table border="0">
		        <tr>
		          <td>Name:</td>
		          <td><sf:input path="name" size="10" /></td>
		        </tr>
		        <tr>
		          <td>Data File:</td>
		          <td><sf:input path="dataFile" size="10" /></td>
		        </tr>
		        <tr>
		          <td />
		          <td><input type="submit" value="Create" /></td>
		        </tr>
		      </table>
		    </sf:form>
		</details>
	</section>
</body>
</html>