package servlets.tags;

import core.Entities.Page;
import lombok.SneakyThrows;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import static servlets.listeners.DefaultSessionParams.AUTH;
import static servlets.listeners.DefaultSessionParams.PAGE;


public class IfMe extends TagSupport {
    @SneakyThrows
    public int doStartTag() throws JspException {
        Page me = (Page) pageContext.getSession().getAttribute(AUTH);
        Page page = (Page) pageContext.getSession().getAttribute(PAGE);
        if (me.getId() != page.getId())
            return SKIP_BODY;
        else return EVAL_BODY_INCLUDE;
    }
}
