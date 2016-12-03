<%--
  Created by IntelliJ IDEA.
  User: jinst
  Date: 10.11.2016
  Time: 4:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/pageTags.tld" prefix="pagetags"%>
<html>
<head>
    <meta charset="utf-8">
    <title>Шаблон</title>
    <style>
        @import url(styles/style.css);
        @import url(styles/main_style.css);
        @import url(styles/extstyles.css);
    </style>
</head>
<body>
<header>
    <div class="header_block">
        <form class="search_form" >
            <input type="search" placeholder="Поиск">
        </form>
        <a href="http://google.com" class="hvr-fade-back header_link">Настройки</a>
        <a href="http://google.com" class="hvr-fade-back header_link">Выйти</a>
    </div>
</header>
<div class="main">
    <div class="left_block">
        <a href="http://google.com" class="hvr-fade menu_link">Профиль</a>
        <a href="http://google.com" class="hvr-fade menu_link">Лента</a>
        <a href="http://google.com" class="hvr-fade menu_link">Подписки</a>
        <a href="http://google.com" class="hvr-fade menu_link">Команда</a>
    </div>
    <div class="main_block">
        <div class="info_block">
            <div class="info_text_block">
                <h3>Александр "USL" Малько</h3>
                <br/>
                <table class="info_table">
                    <tr>
                        <td>Дата рождения</td>
                        <td>Выход Dota2</td>
                        <td>6 лет</td>
                    </tr>
                    <tr><td><br/></td></tr>
                    <tr>
                        <td>Игры</td>
                        <td>Dota2</td>
                        <td>1 mmr</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>Counter Strike GO</td>
                        <td>Silver</td>
                    </tr>
                    <tr><td><br/></td></tr>
                    <tr>
                        <td>Команды</td>
                        <td>GabeN Team</td>
                        <td>Feader</td>
                    </tr>
                    <tr><td><br/></td></tr>
                    <tr>
                        <td>Контакты</td>
                        <td colspan="2">
                            <a href="https://vk.com/id1">vk.com/id9582678</a>
                        </td>
                    </tr>
                </table>
            </div>
            <div class="info_image_block">
                <img src="navi.jpg" class="info_image"/>
            </div>
        </div>
        <div class="content_block">


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
