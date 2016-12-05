package servlets.tags.team;

import core.Entities.Page;
import core.Entities.PageTypes;
import langSupport.LocaleKeyWords;
import lombok.SneakyThrows;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import static servlets.listeners.DefaultSessionParams.LOCALE;
import static servlets.listeners.DefaultSessionParams.PAGE;


/**
 * Places player button, if it shoud be
 */
public class Players extends TagSupport {
    @SneakyThrows
    @Override
    public int doStartTag() throws JspException {
        Page page = (Page)pageContext.getSession().getAttribute(PAGE);
        LocaleKeyWords lkw = (LocaleKeyWords) pageContext.getSession().getAttribute(LOCALE);
        if (page.getPageType() == PageTypes.PERSON)
            return SKIP_BODY;
        pageContext.getOut().print("<a href=\"players?team=" + page.getId() + "\">" +
                lkw.get(LocaleKeyWords.PLAYERS) +
                "</a>");

        return SKIP_BODY;
    }
}