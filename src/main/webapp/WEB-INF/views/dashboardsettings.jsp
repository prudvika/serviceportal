<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ page session="false"%>

<!DOCTYPE html>

<html lang="en">
<head>
<meta charsert="utf-8" />
<title>iQYY Dashboard Manager</title>
<script type="text/javascript" src="<c:url value="/dashboardsettings.js" />"></script>
<link rel="stylesheet" href="<c:url value="/style.css" />">
</head>
<body>
    <header>
		<h1>iQYY Dashboard Manager</h1>
	</header>
	<section id="list">
		<h1>Dashboard Settings</h1>
		<div class="mode-toolbar">
         <div class="left">
           <div>Device Type:</div>
	            <c:set var="deviceTypeIndex" value="0" />
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
						<c:set var="deviceTypeIndex" value="${status.index}" />
					</c:if>

					<button id="<c:out value="${deviceType.id}" />"
						class="<c:out value="${btnClass}" />"
						onclick="location='<c:url value="/dashboardmanager/settings?type=${deviceType.name}" />'">
						<c:out value="${deviceType.name}" />
					</button>
				</c:forEach>
         </div>
         <div class="right">
           <button id="dashboard_manager" class="green" onclick="location='<c:url value="/dashboardmanager" />'">Dashboard Browser</button>
         </div>
       </div>
       
       
	<div class="mode-toolbar">
         <div class="left">
           <div>Dashboard Type:</div>
           
           <c:set var="dashboardObjectIndex" value="-1" />
           <c:forEach items="${dashboardObjectList}" var="dashboardObject" varStatus="status">
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

					<c:if test="${dashboardObject.name==param.dashboardtype || (null==param.dashboardtype && dashboardObject.name==dashboardObjectMapping.dashboardType)}">
						<c:set var="btnClass" value="${btnClass}${' active'}" />
						<c:set var="dashboardObjectIndex" value="${status.index}" />
					</c:if>
					
					
					<button id="<c:out value="${dashboardObject.name}" />"
						class="<c:out value="${btnClass}" />"
						onclick="location='<c:url value="/dashboardmanager/settings?type=${deviceTypeList[deviceTypeIndex].name}&dashboardtype=${dashboardObject.name}" />'">
						<c:out value="${dashboardObject.name}" />
				    </button>
           </c:forEach>
         </div>
    </div>
	
	<details open id="dashboardMapping">
		<summary>Map <c:out value="${deviceTypeList[deviceTypeIndex].name}" /> To <c:out value="${dashboardObjectList[dashboardObjectIndex].name}" /></summary>
		
	
	    <form method="POST" action="<c:url value="/dashboardmanager/settings" />">
	    <input type="hidden" name="type"
			value="<c:out value="${deviceTypeList[deviceTypeIndex].name}" />" /> 
	    <input type="hidden"
			name="dashboardtype" value="<c:out value="${dashboardObjectList[dashboardObjectIndex].name}" />" />
	    <c:forEach items="${dashboardObjectList[dashboardObjectIndex].properties}" var="property">
	       <details>
	          <summary>Mappings for "<c:out value="${property.name}" />"</summary>
	          
	          <button type="button" onclick="addMapping('<c:out value="${property.name}" />')">Add Mapping</button>
	          
		      <table id="<c:out value="${property.name}" />-table">
				    <tr>
				        <th>Device Attribute</th>
				        <th>Operator</th>
				        <th>Device Value</th>
				        <th>Constant value</th>
			        </tr>
			        
			        <c:set var="dashAttrMappingIndex" value="-1" />
			        <c:forEach items="${dashboardObjectMapping.mappings[property.name]}" var="dashAttrMapping" varStatus="status">
			        <c:set var="dashAttrMappingIndex" value="${status.index}" />
					<tr>
						<td>
						<select name="<c:out value="${'device-'}${property.name}${'-'}${status.index}" />">
								<option value="" />
								<c:forEach items="${deviceTypeList[deviceTypeIndex].attributes}" var="attribute">
								   <c:choose>
								     <c:when test="${attribute.name==dashAttrMapping.deviceAttribute}">
								        <option value="<c:out value="${attribute.name}" />" selected><c:out value="${attribute.name}" /></option>
								     </c:when>
								     <c:otherwise>
								        <option value="<c:out value="${attribute.name}" />"><c:out value="${attribute.name}" /></option>
								     </c:otherwise>
								   </c:choose>
								   
								</c:forEach>	
						</select>
						</td>
						<td>
						<select name="<c:out value="${'operator-'}${property.name}${'-'}${status.index}" />">
						    <c:choose>
						       <c:when test="${'>'==dashAttrMapping.operator}">
						         <option value="&gt;" selected>&gt;</option>
						       </c:when>
						       <c:otherwise>
						         <option value="&gt;">&gt;</option>
						       </c:otherwise>
						    </c:choose>
						    <c:choose>
						       <c:when test="${'>='==dashAttrMapping.operator}">
						         <option value="&gt;=" selected>&gt;=</option>
						       </c:when>
						       <c:otherwise>
						         <option value="&gt;=">&gt;=</option>
						       </c:otherwise>
						    </c:choose>
						    <c:choose>
						       <c:when test="${'=='==dashAttrMapping.operator}">
						         <option value="==" selected>==</option>
						       </c:when>
						       <c:otherwise>
						         <option value="==">==</option>
						       </c:otherwise>
						    </c:choose>
						    <c:choose>
						       <c:when test="${'<='==dashAttrMapping.operator}">
						         <option value="&lt;=" selected>&lt;=</option>
						       </c:when>
						       <c:otherwise>
						         <option value="&lt;=">&lt;=</option>
						       </c:otherwise>
						    </c:choose>
						    <c:choose>
						       <c:when test="${'<'==dashAttrMapping.operator}">
						         <option value="&lt;" selected>&lt;</option>
						       </c:when>
						       <c:otherwise>
						         <option value="&lt;">&lt;</option>
						       </c:otherwise>
						    </c:choose>
						</select>
						</td>
						<td><input type="text"
							name="<c:out value="${'value-'}${property.name}${'-'}${status.index}" />" value="<c:out value="${dashAttrMapping.deviceValue}" />" /></td>
						<td>
						    <c:choose>
						      <c:when test="${'java.awt.Color'==property.type}">
						        <input type="color" name="<c:out value="${'constant-'}${property.name}${'-'}${status.index}" />"
							         value="<c:out value="${dashAttrMapping.constantValue}" />" />
						      </c:when>
						      <c:otherwise>
						        <input type="text"
							      name="<c:out value="${'constant-'}${property.name}${'-'}${status.index}" />" value="<c:out value="${dashAttrMapping.constantValue}" />" />
						      </c:otherwise>
						    </c:choose> 
						</td>
					</tr>
					</c:forEach>
					
					<c:if test="${-1==dashAttrMappingIndex}">
					  <tr>
						<td>
						<select name="<c:out value="${'device-'}${property.name}${'-0'}" />">
							<option value="" />
							<c:forEach items="${deviceTypeList[deviceTypeIndex].attributes}" var="attribute">
								<option value="<c:out value="${attribute.name}" />"><c:out value="${attribute.name}" /></option>
							</c:forEach>	
						</select>
						</td>
						<td>
						<select name="<c:out value="${'operator-'}${property.name}${'-0'}" />">
						    <option value="&gt;">&gt;</option>
						    <option value="&gt;=">&gt;=</option>
						    <option value="==">==</option>
						    <option value="&lt;=">&lt;=</option>
						    <option value="&lt;">&lt;</option>
						</select>
						</td>
						<td><input type="text"
							name="<c:out value="${'value-'}${property.name}${'-0'}" />" /></td>
						<td>
						    <c:choose>
						      <c:when test="${'java.awt.Color'==property.type}">
						        <input type="color" name="<c:out value="${'constant-'}${property.name}${'-0'}" />" />
						      </c:when>
						      <c:otherwise>
						        <input type="text"
							      name="<c:out value="${'constant-'}${property.name}${'-0'}" />" />
						      </c:otherwise>
						    </c:choose> 
						</td>
					</tr>
					</c:if>
			  </table>
	       </details>
	    </c:forEach>
	    <input type="submit" value="Save" />
	    </form>
	</details>
	</section>
</body>
</html>