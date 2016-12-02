<%--suppress HtmlFormInputWithoutLabel --%>
<%--
  Created by IntelliJ IDEA.
  User: jinst
  Date: 26.11.2016
  Time: 5:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/commonTags.tld" prefix="common"%>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <common:postComments/>

    <form name="commentform" method="post" accept-charset="UTF-8" >
        <input type="text" name="content"> <br>
        <input type="submit">
    </form>
</body>
</html>
