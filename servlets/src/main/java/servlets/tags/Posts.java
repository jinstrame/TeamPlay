package servlets.tags;

import core.Entities.Post;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

@Log4j2
public class Posts extends TagSupport {


    @Setter
    private String start;

    @SneakyThrows
    public int doStartTag() throws JspException {
        @SuppressWarnings("unchecked") List<Post> posts = (List<Post>) pageContext.getSession().getAttribute("posts");
        //Page me = (Page)pageContext.getSession().getAttribute(AuthFilter.AUTH);
       //@SuppressWarnings("unchecked") Agregator<Post> agregator =  (Agregator<Post>)pageContext.getSession().getAttribute("agregator");

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
                    "                    <a href=\"/removepost?id=").append(p.getPageId()).append("&post=").append(p.getId())
                                    .append("\" class=\"hvr-fade post_link\">Удалить</a>\n" +
                    "                </div>\n" +
                    "            </div>\n");
        }

        log.error("loaded " + posts.size() + " posts");



        if (posts.size() > 0){
            log.error("appended button");
            sb.append("<button id=\"loadPostsButton\" onclick=\"loadPosts()\">load</button>");
        }

        pageContext.getOut().print(sb.toString());

        return SKIP_BODY;
    }
}
