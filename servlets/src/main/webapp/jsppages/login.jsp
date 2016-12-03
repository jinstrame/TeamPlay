<%--suppress HtmlFormInputWithoutLabel --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
                    <tr><td>e-mail</td><td><input type="text" name="email"> </td></tr>
                    <tr><td>password</td><td> <input type="text" name="password"> </td></tr>
                    <tr><td></td><td>  <input class="hvr-fade send_button" type="submit"></td></tr>
                </table>
            </form>
        </div>
    </div>
</div>
</body>
</html>