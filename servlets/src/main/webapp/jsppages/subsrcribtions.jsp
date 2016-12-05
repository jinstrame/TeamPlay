<%--suppress JspAbsolutePathInspection --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="/WEB-INF/pageTags.tld" prefix="page"%>
<%@ taglib uri="/WEB-INF/teamTags.tld" prefix="team"%>
<%@ taglib uri="/WEB-INF/commonTags.tld" prefix="common"%>
<html>
<head>
    <meta charset="utf-8">
    <title>Подписки</title>
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
            <page:subList/>
        </div>
    </div>
</div>
</body>
</html>
