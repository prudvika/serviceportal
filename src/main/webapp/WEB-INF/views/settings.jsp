<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>

<!DOCTYPE html>

<html>
<head>
<meta charset="utf-8">
<title>iQYY IoT Service Portal Settings</title>
<link rel="stylesheet" href="<c:url value="/style.css" />">
</head>
<body>
	<header>
		<h1>iQYY IoT Service Portal Settings</h1>
	</header>
	<section id="list">
		<h1>Settings</h1>

        <div class="mode-toolbar">
         <div class="right">
           <button id="serviceportal_home" class="green"
					onclick="location='<c:url value="/" />'">Back to Platform</button>
         </div>
       </div>
		
		<details open id="settings">
			<summary>General Settings</summary>

			<form action="<c:url value="/settings" />" method="post">
				<table>
					<tr>
						<td align="left">Device Manager URL:</td>
						<td align="left"><input type="text" name="dmServiceURL" size="100"
							required autofocus value="<c:out value="${dmServiceURL}" />" /></td>
					</tr>
					<tr>
						<td align="left">Monitor Service URL:</td>
						<td align="left"><input type="text" name="msServiceURL" size="100"
							required autofocus value="<c:out value="${msServiceURL}" />" /></td>
					</tr>
					<tr>
						<td align="left"><input type="submit" value="Submit" /></td>
					</tr>
				</table>
			</form>
		</details>
	</section>
</body>
</html>