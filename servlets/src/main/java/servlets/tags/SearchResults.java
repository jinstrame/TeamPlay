package servlets.tags;

import core.Entities.Page;
import core.Entities.PageTypes;
import lombok.SneakyThrows;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

import static servlets.listeners.DefaultSessionParams.SEARCH;


/**
 * Prints search results
 */
public class SearchResults extends TagSupport {
    @SneakyThrows
    @Override
    public int doStartTag() throws JspException {
        @SuppressWarnings("unchecked") List<Page> results = (List<Page>) pageContext.getSession().getAttribute(SEARCH);

        StringBuilder sb = new StringBuilder();

        for (Page page: results){
            sb.append("<div class=\"post_block\">\n")
                    .append("<h4>").append("<a href=\"/page?id=").append(page.getId()).append("\">")
                    .append(page.getFirstName()).append(" ");
            if (page.getPageType() == PageTypes.PERSON)
                sb.append("\"").append(page.getNickname()).append("\" ");
                    sb.append(page.getLastName())
                    .append("</a>")
                    .append("</h4>").append("</div>");
        }

        pageContext.getOut().print(sb.toString());

        return SKIP_BODY;
    }
}
