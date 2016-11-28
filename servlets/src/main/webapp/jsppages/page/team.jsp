<%--
  Created by IntelliJ IDEA.
  User: jinst
  Date: 10.11.2016
  Time: 4:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/pageTags.tld" prefix="pagetags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <title>Шаблон</title>
    <style>
        @import url(../../style.css);
        @import url(../../main_style.css);
        @import url(../../extstyles.css);
    </style>
</head>
<body>
<header>
    <div class="header_block">
        <form class="search_form" >
            <input type="search" placeholder="Поиск">
        </form>
        <a href="http://google.com" class="hvr-fade-back header_link"><pagetags:localeTag key="settings"/></a>
        <a href="logout" class="hvr-fade-back header_link"><pagetags:localeTag key="quit"/></a>
    </div>
</header>
<div class="main">
    <div class="left_block">
        <a href="" class="hvr-fade menu_link"><pagetags:localeTag key="profile"/></a>
        <a href="feed" class="hvr-fade menu_link"><pagetags:localeTag key="feed"/></a>
        <a href="http://google.com" class="hvr-fade menu_link"><pagetags:localeTag key="subscriptions"/></a>
    </div>
    <div class="main_block">
        <div class="info_block">
            <div class="info_text_block">
                <pagetags:pageInfo/>
            </div>
            <div class="info_image_block">
                <img src="../../navi.jpg" class="info_image"/>

                <div class="post_buttons">
                    <pagetags:subscribeButton/>
                </div>
            </div>
        </div>
        <div class="content_block">

            <form name="postform" method="post" accept-charset="UTF-8" >
                <%--suppress HtmlFormInputWithoutLabel --%>
                content<input type="text" name="content"> <br>
                <input type="submit">
            </form>

            <pagetags:pagePosts/>

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