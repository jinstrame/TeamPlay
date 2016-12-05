package servlets.tags;

import core.DateTimeUtil;
import core.Entities.Comment;
import core.Entities.Page;
import core.Entities.Post;
import langSupport.LocaleKeyWords;
import lombok.SneakyThrows;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;
import java.util.TimeZone;

import static servlets.listeners.DefaultSessionParams.*;

/**
 * Prints post and its comments with delete buttons
 */
public class CommentPost extends TagSupport {
    @Override
    @SneakyThrows
    public int doStartTag() throws JspException {
        Page me = (Page) pageContext.getSession().getAttribute(AUTH);
        Post post = (Post)pageContext.getSession().getAttribute(POST);
        @SuppressWarnings("unchecked") List<Comment> comments = (List<Comment>)pageContext.getSession().getAttribute(COMMENTS);
        TimeZone tz = (TimeZone) pageContext.getSession().getAttribute(TIMEZONE);
        LocaleKeyWords lkw = (LocaleKeyWords) pageContext.getSession().getAttribute(LOCALE);

        StringBuilder sb = new StringBuilder();

        sb.append(" <div class=\"post_block\">\n <a href=\"/page?id=").append(post.getPageId()).append("\">")
                .append(post.getFirstName()).append(" ");
        if(post.getNickname() != null && !post.getNickname().equals("") && !post.getNickname().equals("null"))
            sb.append(post.getNickname()).append(" ");
        sb.append(post.getLastName()).append("</a>")
                .append("<div class=\"bost_time_block\">\n")
                .append(DateTimeUtil.getDateTimeString(post.getTime(), tz, lkw.getLocale()))
                .append(" </div>\n<div>\n<p>\n")
                .append(post.getContent().replaceAll("\n", "</p><p>>"))
                .append("</p>\n</div>\n</div>");

        pageContext.getOut().print(sb);

        for (Comment comment : comments){
            sb = new StringBuilder();

            sb.append("<div class=\"post_block\">\n <a href=\"/page?id=").append(comment.getCommentator_id()).append("\">")
                    .append(comment.getFirstName()).append(" ");
            if(comment.getNickName() != null && !comment.getNickName().equals("") && !comment.getNickName().equals("null"))
                sb.append(comment.getNickName()).append(" ");
            sb.append(comment.getLastName()).append("</a>")
                    .append("<div class=\"bost_time_block\">\n")
                    .append(DateTimeUtil.getDateTimeString(comment.getTime(), tz, lkw.getLocale()))
                    .append(" </div>\n<div>\n<p>\n")
                    .append(comment.getContent())
                    .append("</p>\n</div>\n");
            if (me.getId() == comment.getCommentator_id()) {
                sb.append("<div class=\"post_buttons\">\n")
                        .append("<a class=\"hvr-fade send_button\" href=\"/remcomment?page=")
                        .append(comment.getPageId()).append("&post=")
                        .append(comment.getPostId()).append("&commentator=")
                        .append(comment.getCommentator_id()).append("&id=")
                        .append(comment.getTime().toEpochMilli()).append("\" >")
                        .append(lkw.get(LocaleKeyWords.REMOVE)).append("</a>\n</div>\n");
            }

            sb.append("</div>");

            pageContext.getOut().print(sb.toString());
        }
        return SKIP_BODY;
    }
}
