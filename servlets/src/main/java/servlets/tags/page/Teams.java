package servlets.tags.page;

import Entities.Page;
import lombok.SneakyThrows;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class Teams extends TagSupport {
    @SneakyThrows
    @Override
    public int doStartTag() throws JspException {
        Page page = (Page)pageContext.getSession().getAttribute("page");
        StringBuilder sb = new StringBuilder();
        sb.append("<a href=\"teams?player=").append(page.getId()).append("\"/>");
        pageContext.getOut().print(sb.toString());

        return SKIP_BODY;
    }
}
