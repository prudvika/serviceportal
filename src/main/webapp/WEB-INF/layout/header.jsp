<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<security:authorize access="isAuthenticated()">
<div style="background-color: #440;color: #FFFFFF;" align="right">
Welcome <security:authentication property="principal.username" />! <a href="<s:url value="/help" />">Help</a>
</div> 
</security:authorize>