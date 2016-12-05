package servlets.tags;

import core.DateTimeUtil;
import core.Entities.Page;
import core.Entities.Post;
import langSupport.LocaleKeyWords;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;
import java.util.Locale;

import static servlets.listeners.DefaultSessionParams.*;

/**
 * Prints posts for current page
 */

@Log4j2
public class Posts extends TagSupport {

    @Setter
    private String start;

    @SneakyThrows
    public int doStartTag() throws JspException {
        @SuppressWarnings("unchecked") List<Post> posts = (List<Post>) pageContext.getSession().getAttribute(POSTS);
        Page me = (Page) pageContext.getSession().getAttribute(AUTH);
        LocaleKeyWords lkw = (LocaleKeyWords) pageContext.getSession().getAttribute(LOCALE);

        StringBuilder sb = new StringBuilder();

        for (Post p : posts) {
            sb.append("<div class=\"post_block\">\n<div class=\"bost_time_block\">\n")
                    .append(DateTimeUtil.getDateTimeString(p.getTime(), me.getTimeZone(), new Locale(me.getLanguage())))
                    .append("\n")
                    .append("</div>\n<div>\n<p>\n")
                    .append(p.getContent().replaceAll("\n", "</p><p>>"))
                    .append("</p>\n</div>\n<div class=\"post_buttons\">\n<a href=\"")
                    .append("post?id=").append(p.getId()).append("&page=").append(p.getPageId())
                    .append("\" class=\"hvr-fade post_link\">")
                    .append(lkw.get(LocaleKeyWords.COMMENTS)).append("</a>\n");

            if (me.getId() == p.getPageId()) {
                sb.append("<a href=\"/removepost?id=")
                        .append(p.getPageId()).append("&post=").append(p.getId())
                        .append("\" class=\"hvr-fade post_link\">")
                        .append(lkw.get(LocaleKeyWords.REMOVE)).append("</a>\n");
            }

            sb.append("</div>\n</div>\n");
        }

        if (posts.size() > 0){
            sb.append("<button id=\"loadPostsButton\" onclick=\"loadMore('posts')\">")
                    .append(lkw.get(LocaleKeyWords.LOAD)).append("</button>");
        }

        pageContext.getOut().print(sb.toString());

        return SKIP_BODY;
    }
}
