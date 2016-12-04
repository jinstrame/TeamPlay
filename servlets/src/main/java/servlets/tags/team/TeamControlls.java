package servlets.tags.team;

import core.Entities.Page;
import core.Entities.PageTypes;
import lombok.SneakyThrows;
import servlets.filters.AuthFilter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class TeamControlls extends TagSupport {
    @SneakyThrows
    public int doStartTag() throws JspException {
        Page me = (Page) pageContext.getSession().getAttribute(AuthFilter.AUTH);
        if (me.getPageType() != PageTypes.TEAM)
            return SKIP_BODY;
        else return EVAL_BODY_INCLUDE;
    }
}