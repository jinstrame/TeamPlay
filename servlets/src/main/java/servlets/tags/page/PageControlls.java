package servlets.tags.page;

import Entities.Page;
import Entities.PageTypes;
import lombok.SneakyThrows;
import servlets.filters.AuthFilter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class PageControlls extends TagSupport {
    @SneakyThrows
    public int doStartTag() throws JspException {
        Page me = (Page) pageContext.getSession().getAttribute(AuthFilter.AUTH);
        if (me.getPageType() != PageTypes.PERSON)
            return SKIP_BODY;
        else return EVAL_BODY_INCLUDE;
    }
}