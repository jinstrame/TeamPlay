package jdbc.dao.postgre_dao;


import Entities.Post;
import jdbc.dao.core.PostDao;
import jdbc.dao.core.agregation.Agregator;
import lombok.AllArgsConstructor;

import java.util.List;


@AllArgsConstructor
public class PostgrePostDao implements PostDao {

    // TODO: 22.10.2016 implement post dao

    private int pageId;

    @Override
    public Post get(int postId, int personId) {
        return null;
    }

    @Override
    public Agregator agregator(List<Integer> personIds, int from) {
        return null;
    }
}
