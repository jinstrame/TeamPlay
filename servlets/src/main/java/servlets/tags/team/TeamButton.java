package servlets.tags.team;

import Entities.Page;
import Entities.PageTypes;
import lombok.SneakyThrows;
import servlets.filters.AuthFilter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class TeamButton extends TagSupport {

    private Page myPage;
    private Page page;

    @Override
    public int doStartTag() throws JspException{
        myPage = (Page)pageContext.getSession().getAttribute(AuthFilter.AUTH);
        page = (Page)pageContext.getSession().getAttribute("page");

        if (myPage.getPageType() == PageTypes.PERSON)
            return noButton();
        if (page.getPageType() == PageTypes.TEAM)
            return noButton();
        else {
            return addToTeamButton();
        }
    }

    private int noButton(){
        return SKIP_BODY;
    }


    @SneakyThrows
    private int addToTeamButton() {
        if (myPage.getSubscribeList().contains(page.getId()))
            return removeFromTeamButton();

        String s = "<form action=\"addplayer?id=" + page.getId() +
                "\" method=\"post\">" +
                "role <input type=\"text\" name=\"role\">" +
                "<button type=\"submit\">" + "Добавить в команду" + "</button>";

        pageContext.getOut().print(s);

        return SKIP_BODY;
    }

    @SneakyThrows
    private int removeFromTeamButton(){
        if (!myPage.getSubscribeList().contains(page.getId()))
            return addToTeamButton();

        String buffer = "<form action=\"remplayer?id=" + page.getId() +
                "\" method=\"post\"><button type=\"submit\">" + "Вы подписаны" + "</button>";

        pageContext.getOut().print(buffer);

        return SKIP_BODY;
    }
}
