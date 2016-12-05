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
    <script src="/scripts/postLoader.js"></script>
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
                <team:teamInfo/>
                <team:players/>
                <page:pageInfo/>
                <page:teams/>
                <div class="about_block">
                    <common:pageAbout authPage="false" addNls="true"/>
                </div>
            </div>
            <common:me>
                <div class="post_block">
<%--suppress HtmlFormInputWithoutLabel --%>
                    <textarea class="content" form="postform" rows="10" name="content"><common:localeTag key="place_content"/></textarea>
                    <form id="postform" name="postform" method="post" accept-charset="UTF-8" >
                        <input class="hvr-fade send_button" type="submit">
                    </form>
                </div>
            </common:me>
            <common:posts/>
        </div>
        <div class="right_block">
            <div class="info_image_block">
<%--suppress HtmlUnknownTarget --%>
                <common:avatar/>
            </div>
            <div>
                <common:me>
                    <form action="/avaupload" id="imgform" name="imgform" method="post" enctype="multipart/form-data" >
                        <input type="file" name="image" id="fileupload">
                        <input class="hvr-fade send_button" type="submit">
                    </form>
                </common:me>
                <page:subscribeButton/>
                <team:teamButton/>
            </div>
        </div>
    </div>
</div>
</body>
</html>
