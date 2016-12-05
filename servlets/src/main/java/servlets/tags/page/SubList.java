package servlets.tags.page;

import core.Entities.Page;
import core.Entities.PageTypes;
import langSupport.LocaleKeyWords;
import lombok.SneakyThrows;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

import static servlets.listeners.DefaultSessionParams.LOCALE;
import static servlets.listeners.DefaultSessionParams.SUBSCRIBERS_LIST;


/**
 * Prints subscribers list
 */

public class SubList extends TagSupport {
    @SneakyThrows
    @Override
    public int doStartTag() throws JspException {
        @SuppressWarnings("unchecked") List<Page> subList = (List<Page>)pageContext.getSession().getAttribute(SUBSCRIBERS_LIST);
        pageContext.getSession().removeAttribute(SUBSCRIBERS_LIST);

        LocaleKeyWords lkw = (LocaleKeyWords) pageContext.getSession().getAttribute(LOCALE);

        StringBuilder sb = new StringBuilder();

        for (Page p: subList){
            sb.append("<div class=\"post_block\">\n")
                    .append("<h4>").append("<a href=\"/page?id=").append(p.getId()).append("\">")
                    .append(p.getFirstName()).append(" ");
            if (p.getPageType() == PageTypes.PERSON)
                sb.append(p.getNickname()).append(" ");

            sb.append(p.getLastName()).append("</a>")
                    .append("</h4>").append("</div>");
        }

        if (subList.size() == 0){
            sb.append("<div class=\"post_block\">\n")
                    .append("<h4>")
                    .append(lkw.get(LocaleKeyWords.NO_SUBSCRIBTIONS))
                    .append("</h4>").append("</div>");
        }

        pageContext.getOut().print(sb.toString());
        return SKIP_BODY;
    }
}
