package jdbc.dao.core;

import core.Entities.Post;
import jdbc.dao.core.agregation.Agregator;

import java.util.List;

public interface PostDao {
    Post get(int postId, int personId);

    boolean put(Post post);

    boolean delete(int pageId, int postId);

    Agregator<Post> agregator(List<Integer> personIds);
}
