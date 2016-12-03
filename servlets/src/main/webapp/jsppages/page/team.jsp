<%--suppress ALL --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/teamTags.tld" prefix="team"%>
<%@ taglib uri="/WEB-INF/pageTags.tld" prefix="page"%>
<%@ taglib uri="/WEB-INF/commonTags.tld" prefix="common"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Шаблон</title>
    <style>
        @import url(../../styles/style.css);
        @import url(../../styles/main_style.css);
        @import url(../../styles/extstyles.css);
    </style>
</head>
<body>
<header>
    <div class="header_block">
        <form class="search_form" >
            <input type="search" placeholder="Поиск">
        </form>
        <a href="http://google.com" class="hvr-fade-back header_link"><common:localeTag key="settings"/></a>
        <a href="logout" class="hvr-fade-back header_link"><common:localeTag key="quit"/></a>
    </div>
</header>
<div class="main">
    <div class="left_block">
        <page:personControlls>
            <a href="/page" class="hvr-fade menu_link"><common:localeTag key="profile"/></a>
            <a href="/feed" class="hvr-fade menu_link"><common:localeTag key="feed"/></a>
            <a href="http://google.com" class="hvr-fade menu_link"><common:localeTag key="subscriptions"/></a>
        </page:personControlls>
        <team:teamControlls>
            <a href="/page" class="hvr-fade menu_link"><common:localeTag key="profile"/></a>
            <a href="http://google.com" class="hvr-fade menu_link"><common:localeTag key="team"/></a>
        </team:teamControlls>
    </div>
    <div class="main_block">
        <div class="info_block">
            <div class="info_text_block">
                <team:teamInfo/>
                <team:players/>
            </div>

        </div>
        <div class="content_block">

            <common:placeContent>
                <div class="post_block">
                    <textarea class="content" form="postform" rows="10" name="content">Какой-то контент</textarea>
                    <form id="postform" name="postform" method="post" accept-charset="UTF-8" >
                        <input class="hvr-fade send_button" type="submit">
                    </form>
                </div>
            </common:placeContent>

            <common:posts/>

            <div class="post_block">
                <div class="bost_time_block">
                    12:09 01.01.2017
                </div>
                <div>
                    <p>
                        Вилата погромче пожалуйста!!!
                    </p>
                    <iframe class="embed-responsive-item"
                            src="https://player.twitch.tv/?channel=xboct&autoplay=false"
                            width=100%
                            height=320px
                            frameborder="0"
                            scrolling="no"
                            allowfullscreen>
                    </iframe>
                </div>
                <div class="post_buttons">
                    <a href="http://google.com" class="hvr-fade post_link">Комментарии</a>
                    <a href="http://google.com" class="hvr-fade post_link">Удалить</a>
                </div>
            </div>

            <div class="post_block">
                <div class="bost_time_block">
                    00:01 27.08.2013
                </div>
                <div>
                    <p>
                        Soooooooo much coool !!!!
                    </p><p>
                    WOW AMAZING
                </p>
                    <iframe width=100% height=320px src="https://www.youtube.com/embed/js3qrAU4Jkc" frameborder="0" allowfullscreen></iframe>
                </div>
                <div class="post_buttons">
                    <a href="http://google.com" class="hvr-fade post_link">Комментарии</a>
                    <a href="http://google.com" class="hvr-fade post_link">Удалить</a>
                </div>
            </div>
        </div>

    </div>
</div>
</body>
</html>
