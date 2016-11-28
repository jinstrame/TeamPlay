package jdbc.dao.core;

import Entities.Comment;
import Entities.Page;
import Entities.Post;
import jdbc.dao.core.agregation.Agregator;

import java.time.Instant;

public interface CommentDao {
    Agregator<Comment> agregator(Post post);

    Comment get(Post post, Page commentator, Instant time);

    void put(Comment comment);
}
