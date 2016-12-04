package servlets.tags.team;

import core.Entities.Page;
import lombok.SneakyThrows;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class Players extends TagSupport {
    @SneakyThrows
    @Override
    public int doStartTag() throws JspException {
        Page page = (Page)pageContext.getSession().getAttribute("page");
        pageContext.getOut().print("<a href=\"players?team=" + page.getId() + "\">Игроки</a>");

        return SKIP_BODY;
    }
}