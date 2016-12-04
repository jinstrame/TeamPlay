package servlets.tags;

import core.Entities.Post;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Iterator;
import java.util.List;


@Log4j2
public class Feed extends TagSupport {

    @SneakyThrows
    public int doStartTag() throws JspException {

        @SuppressWarnings("unchecked") List<Post> posts = (List<Post>)pageContext.getSession().getAttribute("sub_posts");


        StringBuilder sb = new StringBuilder();
        Iterator<Post> i = posts.iterator();

        log.error("feed " + posts.size());

        while (i.hasNext())
        {
            log.error("feed post cycle");
            Post p = i.next();
            sb.append(" <div class=\"post_block\">\n");
            sb.append(p.getFirstName()).append(" ")
                    .append(p.getNickname()).append(" ")
                    .append(p.getSecondName());
            sb.append("                <div class=\"bost_time_block\">\n");
            sb.append(p.getTime().toString()).append("\n");
            sb.append(" </div>\n" +
                    "                <div>\n");
            sb.append("<p>\n");
            sb.append(p.getContent().replaceAll("\n", "</p><p>>"));
            sb.append("</p>\n");
            sb.append("</div>\n" +
                    "                <div class=\"post_buttons\">\n" +
                    "                    <a href=\"");
            sb.append("post?id=").append(p.getId()).append("&page=").append(p.getPageId()).append("\" class=\"hvr-fade post_link\">Комментарии</a>\n" +
                    "                </div>\n" +
                    "            </div>");
        }

        pageContext.getOut().print(sb.toString());
        return SKIP_BODY;
    }
}
