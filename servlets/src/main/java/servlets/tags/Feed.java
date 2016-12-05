package servlets.tags;

import core.DateTimeUtil;
import core.Entities.Post;
import langSupport.LocaleKeyWords;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;
import java.util.TimeZone;

import static servlets.listeners.DefaultSessionParams.*;

/**
 * Provide news feed for current user
 */
@Log4j2
public class Feed extends TagSupport {

    @SneakyThrows
    public int doStartTag() throws JspException {

        @SuppressWarnings("unchecked") List<Post> posts = (List<Post>)pageContext.getSession().getAttribute(POSTS);
        pageContext.removeAttribute(POSTS);
        TimeZone tz = (TimeZone) pageContext.getSession().getAttribute(TIMEZONE);
        LocaleKeyWords lkw = (LocaleKeyWords) pageContext.getSession().getAttribute(LOCALE);

        StringBuilder sb = new StringBuilder();

        for(Post p: posts) {
            sb.append(" <div class=\"post_block\">\n <a href=\"/page?id=").append(p.getPageId()).append("\">")
                    .append(p.getFirstName()).append(" ");
            if(p.getNickname() != null && !p.getNickname().equals("") && !p.getNickname().equals("null"))
                sb.append(p.getNickname()).append(" ");
            sb.append(p.getLastName()).append("</a>")
                    .append("<div class=\"bost_time_block\">\n")
                    .append(DateTimeUtil.getDateTimeString(p.getTime(), tz, lkw.getLocale()))
                    .append(" </div>\n<div>\n<p>\n")
                    .append(p.getContent().replaceAll("\n", "</p><p>>"))
                    .append("</p>\n</div>\n")
                    .append("<div class=\"post_buttons\">\n")
                    .append("<a href=\"").append("post?id=").append(p.getId()).append("&page=").append(p.getPageId())
                    .append("\" class=\"hvr-fade post_link\">")
                    .append(lkw.get(LocaleKeyWords.COMMENTS)).append("</a>\n</div>\n</div>");
        }

        if (posts.size() > 0){
            sb.append("<button class=\"hvr-fade send_button\" id=\"loadPostsButton\" onclick=\"loadMore('feed')\">")
                    .append(lkw.get(LocaleKeyWords.LOAD)).append("</button>");
        }

        pageContext.getOut().print(sb.toString());
        return SKIP_BODY;
    }
}
