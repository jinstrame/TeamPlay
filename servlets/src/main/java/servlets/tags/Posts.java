package servlets.tags;

import Entities.Post;
import lombok.SneakyThrows;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;


public class Posts extends TagSupport {

    @SneakyThrows
    public int doStartTag() throws JspException {
        @SuppressWarnings("unchecked") List<Post> posts = (List<Post>) pageContext.getSession().getAttribute("posts");

        StringBuilder sb = new StringBuilder();

        for (Post p : posts) {
            sb.append(" <div class=\"post_block\">\n" +
                    "                <div class=\"bost_time_block\">\n");
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
                    "                    <a href=\"http://google.com\" class=\"hvr-fade post_link\">Удалить</a>\n" +
                    "                </div>\n" +
                    "            </div>");
        }

        pageContext.getOut().print(sb.toString());

        return SKIP_BODY;
    }
}
