package jdbc.dao.core;

import Entities.Post;
import jdbc.dao.core.agregation.Agregator;

import java.util.List;

public interface PostDao {
    Post get(int postId, int personId);

    Agregator agregator(List<Integer> personIds, int from);
}
