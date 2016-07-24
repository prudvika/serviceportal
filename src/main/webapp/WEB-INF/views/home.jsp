<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>

<!DOCTYPE html>
<html lang="zh">
<head>
  <meta charset="utf-8">
  <title>iQYY IoT Service Portal</title>
  <link rel="stylesheet" href="<c:url value="/style.css" />">
</head>
<body>
  <center>
  <header>
		<h1>iQYY IoT Service Portal</h1>
  </header>
  <img src="<c:url value="/mainpic.jpg" />" alt="工业互联网图片">
  <hr>
  <button id="devicemanager" class="green" onclick="location='<c:url value="/devicemanager?type=Test" />'">Device Manager</button>
  <button id="monitor" class="green" onclick="location='<c:url value="/dashboardmanager" />'">Device Monitor</button>
  <button id="incidentmanager" class="green" onclick="location='<c:url value="/incidentmanager" />'">Incident Manager</button>
  </center>
</body>
</html>