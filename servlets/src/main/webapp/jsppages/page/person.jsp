<%--suppress JspAbsolutePathInspection --%>
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
<%--suppress JSUnresolvedLibraryURL --%>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
    <script>
        function loadPosts() {
            var xhr = new XMLHttpRequest();
            xhr.open('GET', '/page');
            xhr.onload = function() {
                    alert('adasdasdsad');
            };
            xhr.send();
        }
    </script>
</head>
<body>
<header>
    <div class="header_block">
        <form class="search_form" >
            <input type="search" placeholder="Поиск">
        </form>
        <a href="http://google.com" class="hvr-fade-back header_link"><common:localeTag key="settings"/></a>
        <a href="/logout" class="hvr-fade-back header_link"><common:localeTag key="quit"/></a>
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
        <div class="content_block">
            <div class="info_text_block">
                <page:pageInfo/>
                <page:teams/>
            </div>
            <common:placeContent>
                <div class="post_block">
<%--suppress HtmlFormInputWithoutLabel --%>
                    <textarea class="content" form="postform" rows="10" name="content">Какой-то контент</textarea>
                    <form id="postform" name="postform" method="post" accept-charset="UTF-8" >
                        <input class="hvr-fade send_button" type="submit">
                    </form>
                </div>
            </common:placeContent>
            <common:posts/>
            <button id="loadPosts" onclick="loadPosts()">load</button>
        </div>
        <div class="right_block">
            <div class="info_image_block">
                <img src="../../navi.jpg" class="info_image"/>
            </div>
            <div>
                <page:subscribeButton/>
                <team:teamButton/>
            </div>
        </div>
    </div>
</div>
</body>
</html>
