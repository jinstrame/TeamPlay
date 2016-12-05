<%--suppress ALL --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/pageTags.tld" prefix="page"%>
<%@ taglib uri="/WEB-INF/teamTags.tld" prefix="team"%>
<%@ taglib uri="/WEB-INF/commonTags.tld" prefix="common"%>
<html>
<head>
    <meta charset="utf-8">
    <title>Шаблон</title>
    <style>
        @import url(/styles/style.css);
        @import url(/styles/main_style.css);
        @import url(/styles/extstyles.css);
    </style>
</head>
<body>
<header>
    <div class="header_block">
        <form class="search_form" method="get" action="/search">
            <common:searchInput/>
        </form>
        <a href="/settings" class="hvr-fade-back header_link"><common:localeTag key="settings"/></a>
        <a href="/logout" class="hvr-fade-back header_link"><common:localeTag key="quit"/></a>
    </div>
</header>
<div class="main">
    <div class="left_block">
        <page:personControlls>
            <a href="/page" class="hvr-fade menu_link"><common:localeTag key="profile"/></a>
            <a href="/feed" class="hvr-fade menu_link"><common:localeTag key="feed"/></a>
            <a href="/subscribtions" class="hvr-fade menu_link"><common:localeTag key="subscriptions"/></a>
        </page:personControlls>
        <team:teamControlls>
            <a href="/page" class="hvr-fade menu_link"><common:localeTag key="profile"/></a>
            <a href="/teammates" class="hvr-fade menu_link"><common:localeTag key="team"/></a>
        </team:teamControlls>
    </div>
    <div class="main_block">
        <div id="content_block" class="content_block">
            <div class="info_text_block">
                <table>
                    <tr><td><common:localeTag key="first_name"/></td><td>
                        <form action="/settings?attr=upd_firstname" method="post"><input type="text" name="upd_firstname">
                            <button class="hvr-fade send_button" type="submit" >
                                <common:localeTag key="send"/>
                            </button></form></td></tr>

                    <tr><td><common:localeTag key="last_name"/></td><td>
                        <form action="/settings?attr=upd_lastname" method="post"><input type="text" name="upd_lastname">
                            <button class="hvr-fade send_button" type="submit" >
                                <common:localeTag key="send"/>
                            </button></form></td></tr>

                    <tr><td><common:localeTag key="nickname"/></td><td>
                        <form action="/settings?attr=upd_nickname" method="post"><input type="text" name="upd_nickname">
                            <button class="hvr-fade send_button" type="submit" >
                                <common:localeTag key="send"/>
                            </button></form></td></tr>

                    <tr><td><common:localeTag key="date_of_birth"/></td><td>
                        <form action="/settings?attr=upd_dob" method="post"><input type="date" name="upd_dob">
                            <button class="hvr-fade send_button" type="submit" >
                                <common:localeTag key="send"/>
                            </button></form></td></tr>

                    <tr><td><common:localeTag key="lang_word"/></td><td>
                        <form action="/settings?attr=upd_language" method="post"><input type="text" name="upd_language" list="lang_list">
                        <datalist id="lang_list">
                            <option value="RU">
                            <option value="EN">
                        </datalist>
                            <button class="hvr-fade send_button" type="submit" >
                                <common:localeTag key="send"/>
                            </button></form></td></tr>

                    <tr><td><common:localeTag key="time_zone"/></td><td>
                        <form action="/settings?attr=upd_timezone" method="post"><input type="text" name="upd_timezone" list="tz_list" >
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
                            <button class="hvr-fade send_button" type="submit" >
                                <common:localeTag key="send"/>
                            </button></form></td></tr>
                </table>

                <textarea class="content" form="upd_about" rows="10" name="upd_about"><common:pageAbout authPage="true" addNls="false"/></textarea>
                <form action="/settings?attr=upd_about" id="upd_about" name="upd_about" method="post">
                    <button class="hvr-fade send_button" type="submit" >
                        <common:localeTag key="send"/>
                    </button>
                </form>

            </div>
        </div>
    </div>
</div>
</body>
</html>
