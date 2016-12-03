package servlets.tags.team;


import Entities.Game;
import Entities.Page;
import langSupport.LocaleKeyWords;
import lombok.SneakyThrows;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.time.LocalDate;
import java.util.List;

public class TeamInfo extends TagSupport {
    @Override
    @SneakyThrows
    public int doStartTag() throws JspException {
        Page page = (Page) pageContext.getSession().getAttribute("page");
        LocaleKeyWords lkw = (LocaleKeyWords) pageContext.getSession().getAttribute("locale");

        @SuppressWarnings("StringBufferReplaceableByString") StringBuilder sb = new StringBuilder("");
        sb.append("<h3>")
                .append(page.getFirstName()).append(" ")
                .append(page.getSecondName()).append("</h3>");
        sb.append("<br/>\n");

        pageContext.getOut().print(sb.toString());

        return EVAL_BODY_INCLUDE;
    }
}

