package jdbc.dao.core;

import core.Entities.Comment;
import core.Entities.Post;
import jdbc.dao.core.agregation.Agregator;

public interface CommentDao {
    Agregator<Comment> agregator(Post post);

    void delete(String page, String post, String commentator, String id);

    void put(Comment comment);
}
