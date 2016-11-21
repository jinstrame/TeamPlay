package jdbc.dao.postgre_dao;


import Entities.Post;
import jdbc.connection.ConnectionPool;
import jdbc.dao.core.PostDao;
import jdbc.dao.core.agregation.Agregator;
import jdbc.dao.postgre_dao.agregation.PostgreAgregator;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;


@Log4j2
@AllArgsConstructor
class PostgrePostDao implements PostDao {

    // TODO: 22.10.2016 implement post dao

    private ConnectionPool pool;

    @Override
    public Post get(int postId, int pageId) {
        return null;
    }

    @Override
    public boolean put(Post post) {
        try (Connection connection = pool.get();
             Statement statement = connection.createStatement()) {
            String q =  "INSERT INTO web_app.posts VALUES (" +
                    post.getPageId() + ", " +
                    post.getId() + ", " +
                    0 + ", '" +
                    Timestamp.valueOf(LocalDateTime.now())  + "', '" +
                    post.getContent() + "')" ;
            System.out.println(q);
            statement.execute(q);
            statement.execute(
                    "UPDATE web_app.pages SET last_post_id = " +
                            post.getId() + " WHERE id = " + post.getPageId());


            return true;

        } catch (SQLException e) {
            log.warn("cant put post into db");
            e.printStackTrace();
        }
        return false;

    }

    @Override
    public Agregator<Post> agregator(List<Integer> pageIds) {
        return new PostgreAgregator(pageIds, pool);
    }
}
