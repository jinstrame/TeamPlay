package jdbc.dao.postgre_dao;


import core.Entities.Comment;
import core.Entities.Page;
import core.Entities.Post;
import jdbc.connection.ConnectionPool;
import jdbc.dao.core.CommentDao;
import jdbc.dao.core.agregation.Agregator;
import jdbc.dao.postgre_dao.agregation.PostgreCommentAgregator;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.Instant;

@AllArgsConstructor
@Log4j2
public class PostgreCommentDao implements CommentDao {

    private ConnectionPool pool;

    @Override
    public Agregator<Comment> agregator(Post post) {
        return new PostgreCommentAgregator(post, pool);
    }

    @Override
    public Comment get(Post post, Page commentator, Instant time) {
        return null;
    }

    @Override
    public void put(Comment comment) {
        try(Connection con = pool.get();
            Statement statement = con.createStatement()) {
            statement.execute("INSERT INTO web_app.comments VALUES(" +
                    comment.getPageId() + ", " +
                    comment.getPostId() + ", " +
                    comment.getCommentator_id() + ", " +
                    0 + ", '" +
                    comment.getContent() + "', '" +
                    Timestamp.from(comment.getTime()).toString()  + "')"
            );
        } catch (SQLException e) {
            log.error(e.toString());
            e.printStackTrace();
        }

    }
}
