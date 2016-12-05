<%--suppress ALL --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="common" uri="http://TeamPlay.com/commonTags.jsp" %>

<html>
<head>
    <meta charset="utf-8">
    <title>Вход</title>
    <style>
        @import url(/styles/style.css);
        @import url(/styles/main_style.css);
        @import url(/styles/extstyles.css);
    </style>
</head>
<body>
<div class="main">
    <div class="main_block">
        <div class="post_block">
            <form name="loginform" method="post" accept-charset="UTF-8" >
                <table>
                    <tr><td><common:localeTag key="email"/> </td><td><input type="text" name="email"> </td></tr>
                    <tr><td><common:localeTag key="password"/></td><td> <input type="text" name="password"> </td></tr>
                    <tr><td></td><td>  <button class="hvr-fade send_button" type="submit" >
                            <common:localeTag key="send"/>
                        </button>
                        <a class="hvr-fade send_button" href="/auth/register"><common:localeTag key="register"/></a>
                    </td></tr>
                </table>
            </form>
        </div>
    </div>
</div>
</body>
</html>