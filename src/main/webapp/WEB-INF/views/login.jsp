<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="sf"%>
<%@ page session="false"%>


<center>
	<header>
		<h1>iQYY IoT Service Portal</h1>
	</header>
	<img src="<c:url value="/mainpic.jpg" />" alt="main picture‰‡">
	<hr />
	<sf:form method="post">
    User Name:<input type="text" name="username" value="" size="20" />
    Password:<input type="password" name="password" size="20" />
		<input type="submit" name="submit" value="Login" />
	</sf:form>
</center>