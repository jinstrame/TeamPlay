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

        String s = "<form action=\"addplayer?player=" + page.getId() +
                "\" method=\"post\">" +
                "<input placeholder=\"Роль\" type=\"text\" name=\"role\">  <br>" +
                "<input class=\"hvr-fade-back header_link\" type=\"submit\" value=\"" + "Добавить в команду" + "\"/>";

        pageContext.getOut().print(s);

        return SKIP_BODY;
    }

    @SneakyThrows
    private int removeFromTeamButton(){
        if (!myPage.getSubscribeList().contains(page.getId()))
            return addToTeamButton();

        String buffer = "<form action=\"removefromteam?player=" + page.getId() +
                "\" method=\"post\"><input class=\"hvr-fade-back header_link\" type=\"submit\" value=\"" + "Исключить из команды" + "\"/>";

        pageContext.getOut().print(buffer);

        return SKIP_BODY;
    }

}
