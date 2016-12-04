package jdbc.dao.core;

import core.Entities.Comment;
import core.Entities.Page;
import core.Entities.Post;
import jdbc.dao.core.agregation.Agregator;

import java.time.Instant;

public interface CommentDao {
    Agregator<Comment> agregator(Post post);

    Comment get(Post post, Page commentator, Instant time);

    void put(Comment comment);
}
