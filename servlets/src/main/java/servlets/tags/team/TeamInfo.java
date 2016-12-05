package servlets.tags.team;


import core.Entities.Page;
import core.Entities.PageTypes;
import lombok.SneakyThrows;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import static servlets.listeners.DefaultSessionParams.PAGE;


/**
 * Prints information about team, including name.
 */
public class TeamInfo extends TagSupport {
    @Override
    @SneakyThrows
    public int doStartTag() throws JspException {
        Page page = (Page) pageContext.getSession().getAttribute(PAGE);
        if (page.getPageType() == PageTypes.PERSON)
            return SKIP_BODY;

        String sb = "" + "<h3>" +
                page.getFirstName() + " " +
                page.getLastName() + "</h3>" +
                "<br/>\n";

        pageContext.getOut().print(sb);

        return EVAL_BODY_INCLUDE;
    }
}

