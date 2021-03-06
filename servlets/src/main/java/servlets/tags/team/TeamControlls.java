package servlets.tags.team;

import core.Entities.Page;
import core.Entities.PageTypes;
import lombok.SneakyThrows;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import static servlets.listeners.DefaultSessionParams.AUTH;

public class TeamControlls extends TagSupport {
    @SneakyThrows
    public int doStartTag() throws JspException {
        Page me = (Page) pageContext.getSession().getAttribute(AUTH);
        if (me.getPageType() != PageTypes.TEAM)
            return SKIP_BODY;
        else return EVAL_BODY_INCLUDE;
    }
}