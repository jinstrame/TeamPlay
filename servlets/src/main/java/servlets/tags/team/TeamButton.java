package servlets.tags.team;

import core.Entities.Page;
import core.Entities.PageTypes;
import langSupport.LocaleKeyWords;
import lombok.SneakyThrows;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import static servlets.listeners.DefaultSessionParams.*;

public class TeamButton extends TagSupport {

    private Page myPage;
    private Page page;
    private LocaleKeyWords lkw;

    @Override
    public int doStartTag() throws JspException{
        myPage = (Page)pageContext.getSession().getAttribute(AUTH);
        page = (Page)pageContext.getSession().getAttribute(PAGE);
        lkw = (LocaleKeyWords) pageContext.getSession().getAttribute(LOCALE);

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
                "<input placeholder=\"" +
                lkw.get(LocaleKeyWords.ROLE) +
                "\" type=\"text\" name=\"role\">  <br>" +
                "<input class=\"hvr-fade-back header_link\" type=\"submit\" value=\"" +
                lkw.get(LocaleKeyWords.ADD_TO_TEAM) +
                "\"/>";

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
