package servlets.tags;

import core.Entities.Comment;
import core.Entities.Post;
import lombok.SneakyThrows;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;


public class CommentPost extends TagSupport {
    @Override
    @SneakyThrows
    public int doStartTag() throws JspException {
        Post post = (Post)pageContext.getSession().getAttribute("post");
        @SuppressWarnings("unchecked") List<Comment> comments = (List<Comment>)pageContext.getSession().getAttribute("comments");

        StringBuilder sb = new StringBuilder();

        sb.append(post.getFirstName()).append(" \"").append(post.getNickname()).append(" \"").append(post.getSecondName());
        sb.append("<br/>");
        sb.append(post.getTime().toString()).append("<br/>");
        sb.append(post.getContent()).append("<br/>").append("<br/>");

        for (Comment comment : comments){
            sb.append(comment.getContent()).append("<br/>");
        }


        pageContext.getOut().print(sb.toString());
        return SKIP_BODY;
    }
}
