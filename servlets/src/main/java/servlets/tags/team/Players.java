package servlets.tags.team;

import Entities.Page;
import lombok.SneakyThrows;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class Players extends TagSupport {
    @SneakyThrows
    @Override
    public int doStartTag() throws JspException {
        Page page = (Page)pageContext.getSession().getAttribute("page");
        StringBuilder sb = new StringBuilder();
        sb.append("<a href=\"players?team=").append(page.getId()).append("\"/>");
        pageContext.getOut().print(sb.toString());

        return SKIP_BODY;
    }
}