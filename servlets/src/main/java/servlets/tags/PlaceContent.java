package servlets.tags;

import Entities.Page;
import langSupport.LocaleKeyWords;
import lombok.Setter;
import lombok.SneakyThrows;
import servlets.filters.AuthFilter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class PlaceContent extends TagSupport {


    @SneakyThrows
    public int doStartTag() throws JspException {
        Page me = (Page) pageContext.getSession().getAttribute(AuthFilter.AUTH);
        Page page = (Page) pageContext.getSession().getAttribute("page");
        if (me.getId() != page.getId())
            return SKIP_BODY;
        else return EVAL_BODY_INCLUDE;
    }
}