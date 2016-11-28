package servlets.tags;

import Entities.Comment;
import Entities.Page;
import Entities.Post;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import servlets.filters.AuthFilter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Iterator;
import java.util.List;


@Log4j2
public class Feed extends TagSupport {

    @SneakyThrows
    public int doStartTag() throws JspException {

        @SuppressWarnings("unchecked") List<Post> posts = (List<Post>)pageContext.getSession().getAttribute("sub_posts");


        StringBuffer outBuffer = new StringBuffer();
        Iterator<Post> i = posts.iterator();

        log.error("feed " + posts.size());

        while (i.hasNext())
        {
            log.error("feed post cycle");
            Post p = i.next();
            outBuffer.append(" <div class=\"post_block\">\n");
            outBuffer.append(p.getFirstName()).append(" ")
                    .append(p.getNickname()).append(" ")
                    .append(p.getSecondName());
            outBuffer.append("                <div class=\"bost_time_block\">\n");
            outBuffer.append(p.getTime().toString()).append("\n");
            outBuffer.append(" </div>\n" +
                    "                <div>\n");
            outBuffer.append("<p>\n");
            outBuffer.append(p.getContent().replaceAll("\n", "</p><p>>"));
            outBuffer.append("</p>\n");
            outBuffer.append("</div>\n" +
                    "                <div class=\"post_buttons\">\n" +
                    "                    <a href=\"");
            outBuffer.append("post?id=").append(p.getId()).append("&page=").append(p.getPageId()).append("\" class=\"hvr-fade post_link\">Комментарии</a>\n" +
                    "                </div>\n" +
                    "            </div>");
        }

        pageContext.getOut().print(outBuffer.toString());
        return SKIP_BODY;
    }
}
