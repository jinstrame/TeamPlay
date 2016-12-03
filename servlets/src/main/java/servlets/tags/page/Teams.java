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
        pageContext.getOut().print("<a href=\"teams?player=" + page.getId() + "\">Команды</a>");

        return SKIP_BODY;
    }
}
