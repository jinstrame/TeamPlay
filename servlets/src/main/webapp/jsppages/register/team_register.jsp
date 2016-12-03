<%--suppress ALL --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<html>
<head>
    <meta charset="utf-8">
    <title>Регистрация команды</title>
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
            <form class="search_form" name="regform" method="post" accept-charset="UTF-8" >
                <table>
                    <tr><td>e-mail</td><td><input type="text" name="reg_email"> </td></tr>
                    <tr><td>password</td><td> <input type="text" name="reg_password"> </td></tr>
                    <tr><td>Имя</td><td><input type="text" name="firstname"></td></tr>
                    <tr><td>Игра</td><td><input  type="text" name="lastname"></td></tr>
                    <tr><td></td><td>  <input class="hvr-fade send_button" type="submit"></td></tr>
                    <tr></tr>
                    <tr><td></td><td> <a class="hvr-fade post_link" href="/auth/register">Register</a></td></tr>
                </table>
            </form>
        </div>
    </div>
</div>
</body>
</html>

