package servlets.tags.page;

import core.Entities.Page;
import lombok.SneakyThrows;
import servlets.filters.AuthFilter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;



public class Avatar extends TagSupport {

    @SneakyThrows
    public int doStartTag() throws JspException {
        Page me = (Page) pageContext.getSession().getAttribute(AuthFilter.AUTH);

        pageContext.getOut().print("<img src=\"/images/" + me.getAvaId() + "\" class=\"info_image\"/>");
        return SKIP_BODY;
    }
}
