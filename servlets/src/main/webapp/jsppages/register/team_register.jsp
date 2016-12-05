<%--suppress ALL --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/commonTags.tld" prefix="common"%>
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
            <form class="search_form" name="regform" method="post">
                <table>
                    <tr><td><common:localeTag key="email"/></td><td><input type="text" name="reg_email"> </td></tr>
                    <tr><td><common:localeTag key="password"/></td><td> <input type="text" name="reg_password"> </td></tr>
                    <tr><td><common:localeTag key="team"/></td><td><input type="text" name="firstname"></td></tr>
                    <tr><td><common:localeTag key="game"/></td><td><input  type="text" name="lastname"></td></tr>
                    <tr><td><common:localeTag key="time_zone"/></td><td><input  type="text" name="timezone" list="tz_list" value="GMT+3"></td></tr>
                    <datalist id="tz_list">
                        <option value="GMT-11">
                        <option value="GMT-10">
                        <option value="GMT-9">
                        <option value="GMT-8">
                        <option value="GMT-7">
                        <option value="GMT-6">
                        <option value="GMT-5">
                        <option value="GMT-4">
                        <option value="GMT-3">
                        <option value="GMT-2">
                        <option value="GMT-1">
                        <option value="GMT">
                        <option value="GMT+1">
                        <option value="GMT+2">
                        <option value="GMT+3">
                        <option value="GMT+4">
                        <option value="GMT+5">
                        <option value="GMT+6">
                        <option value="GMT+7">
                        <option value="GMT+8">
                        <option value="GMT+9">
                        <option value="GMT+10">
                        <option value="GMT+11">
                        <option value="GMT+12">
                    </datalist>
                    <tr><td><common:localeTag key="lang_word"/></td><td><input type="text" name="language" list="lang_list"></td></tr>
                    <datalist id="lang_list">
                        <option value="EN">
                        <option value="RU">
                    </datalist>
                    <tr><td></td><td>  <button class="hvr-fade send_button" type="submit" >
                        <common:localeTag key="send"/>
                    </button></td></tr>
                    <tr></tr>
                    <tr><td></td><td> <a class="hvr-fade post_link" href="/auth/register">
                        <common:localeTag key="register"/>
                    </a></td></tr>
                </table>
            </form>
        </div>
    </div>
</div>
</body>
</html>

