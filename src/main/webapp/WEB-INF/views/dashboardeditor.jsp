<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ page session="false"%>
<!DOCTYPE html>

<html lang="en">
  <head>
    <meta charsert="utf-8" />
    <title>Dashboard Editor</title>
    <link rel="stylesheet" href="<c:url value="/style.css" />">
  </head>
  <body>
    <header>
		<h1>iQYY Dashboard Manager</h1>
	</header>
	
	<section id="list">
	    <h1>Dashboard Editor</h1>
		<div class="mode-toolbar">
         <div class="right">
           <button id="dashboard_manager" class="green" onclick="location='<c:url value="/dashboardmanager" />'">Dashboard Browser</button>
         </div>
       </div>
    
    <details open id="selectDevices">
	   <summary>Select devices in <c:out value="${dashboard.name}" /> </summary>
       
       <form action="<c:url value="/dashboardmanager/dashboardeditor" />" method="post">
		<input type="hidden" name="name" value="<c:out value="${dashboard.name}" />" />
       <c:forEach items="${deviceTypeList}" var="deviceType">
       <details open>
          <summary><c:out value="${deviceType.name}" /></summary>
          <c:forEach items="${deviceList}" var="device">
             <c:if test="${device.type==deviceType.name}">
                <c:choose>
                  <c:when test="${fn:contains(dashboard.devices,device.id)}">
                     <input type="checkbox" name="device" 
                        value="<c:out value="${device.id}" />" checked><c:out value="${device.name}" /></input><br />
                  </c:when>
                  <c:otherwise>
                     <input type="checkbox" name="device" 
                        value="<c:out value="${device.id}" />"><c:out value="${device.name}" /></input><br />
                  </c:otherwise>
                </c:choose>
             </c:if>
          </c:forEach>
       </details>   
       </c:forEach>
       <input type="submit" value="Save" />
       </form>
    </details>
    
    <details>
       <summary>Preview</summary>
       <button id="dashboard_preview" onclick="Session.sendMessage('SetDashboard:'+'<c:out value="${dashboard.name}" />')">Preview</button>
    
       <canvas id="canvas">
        Download Chrome to use this dashboard now!
       </canvas>
       <img src="<c:url value="/china.png" />" id="china_png" width="0" height="0">
    
       <script type="text/javascript" src="<c:url value="/dashboard.js" />"></script>
    </details>
    </section>
  </body>
</html>