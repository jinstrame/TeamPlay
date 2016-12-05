package servlets.tags;

import core.Entities.Page;
import lombok.Setter;
import lombok.SneakyThrows;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import static servlets.listeners.DefaultSessionParams.AUTH;
import static servlets.listeners.DefaultSessionParams.PAGE;


/**
 * Prints "about" field of current page
 */
public class PageAbout extends TagSupport {

    @Setter
    private boolean authPage;

    @Setter
    private boolean addNls;

    @SneakyThrows
    public int doStartTag() throws JspException {
        Page page;
        if (authPage)
            page = (Page) pageContext.getSession().getAttribute(AUTH);
        else
            page = (Page) pageContext.getSession().getAttribute(PAGE);
        String about = page.getAbout();
        if (addNls){
            about = about.replace("\n", "<br>");
        }
        pageContext.getOut().print(about);
        return SKIP_BODY;
    }
}