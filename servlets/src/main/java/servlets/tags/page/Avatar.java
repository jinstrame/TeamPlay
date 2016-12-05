package servlets.tags.page;

import core.Entities.Page;
import lombok.SneakyThrows;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import static servlets.listeners.DefaultSessionParams.PAGE;


/**
 * Tag wor placing page avataar in html.
 */
public class Avatar extends TagSupport {

    @SneakyThrows
    public int doStartTag() throws JspException {
        Page me = (Page) pageContext.getSession().getAttribute(PAGE);

        pageContext.getOut().print("<img src=\"/images/" + me.getAvaId() + "\" class=\"info_image\"/>");
        return SKIP_BODY;
    }
}
