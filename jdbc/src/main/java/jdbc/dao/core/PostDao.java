package jdbc.dao.core;

import Entities.Post;
import jdbc.dao.core.agregation.Agregator;

import java.util.List;

public interface PostDao {
    Post get(int postId, int personId);

    boolean put(Post post);

    Agregator<Post> agregator(List<Integer> personIds);
}
