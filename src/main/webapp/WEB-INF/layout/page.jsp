<%@ taglib uri="http://www.springframework.org/tags" prefix="s" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="t" %>
<%@ page session="false" %>
<!DOCTYPE html>
<html lang="zh">
  <head>
    <meta charset="utf-8">
    <title>iQYY IoT Service Portal</title>
    <link rel="stylesheet" href="<s:url value="/style.css" />">
  </head>
  <body>
    <div id="header">
      <t:insertAttribute name="header" />
    </div>
    
    <div id="content">
      <t:insertAttribute name="body" />
    </div>
    
    <div id="footer">
      <t:insertAttribute name="footer" />
    </div>
  </body>
</html>