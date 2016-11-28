package servlets.tags;

import Entities.Page;
import Entities.Post;
import lombok.SneakyThrows;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Iterator;
import java.util.List;


public class PagePosts extends TagSupport {

    @SneakyThrows
    public int doStartTag() throws JspException {
        @SuppressWarnings("unchecked") List<Post> posts = (List<Post>) pageContext.getSession().getAttribute("posts");

        StringBuffer outBuffer = new StringBuffer();

        Iterator<Post> i = posts.iterator();
        while (i.hasNext())
        {
            Post p = i.next();
            outBuffer.append(" <div class=\"post_block\">\n" +
                    "                <div class=\"bost_time_block\">\n");
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
                    "                    <a href=\"http://google.com\" class=\"hvr-fade post_link\">Удалить</a>\n" +
                    "                </div>\n" +
                    "            </div>");
        }

        pageContext.getOut().print(outBuffer.toString());

        return SKIP_BODY;
    }
}
