package servlets.tags.page;

import core.Entities.Page;
import core.Entities.PageTypes;
import langSupport.LocaleKeyWords;
import lombok.SneakyThrows;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import static servlets.listeners.DefaultSessionParams.LOCALE;
import static servlets.listeners.DefaultSessionParams.PAGE;


/**
 * places team button, if it shoud be
 */
public class Teams extends TagSupport {
    @SneakyThrows
    @Override
    public int doStartTag() throws JspException {
        Page page = (Page)pageContext.getSession().getAttribute(PAGE);
        LocaleKeyWords lkw = (LocaleKeyWords) pageContext.getSession().getAttribute(LOCALE);
        if (page.getPageType() == PageTypes.TEAM)
            return SKIP_BODY;

        pageContext.getOut().print("<a href=\"teams?player=" + page.getId() + "\">" +
                lkw.get(LocaleKeyWords.TEAMS) + "</a>");

        return SKIP_BODY;
    }
}
