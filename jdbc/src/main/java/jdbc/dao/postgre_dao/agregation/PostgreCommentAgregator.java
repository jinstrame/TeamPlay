package jdbc.dao.postgre_dao.agregation;

import core.Entities.Comment;
import core.Entities.Post;
import jdbc.connection.ConnectionPool;
import jdbc.dao.core.agregation.Agregator;
import lombok.extern.log4j.Log4j2;
import lombok.val;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;

@Log4j2
public class PostgreCommentAgregator implements Agregator<Comment> {
    private Post post;
    private ConnectionPool pool;

    private boolean avalible;
    private int readed;

    public PostgreCommentAgregator(Post post, ConnectionPool pool) {
        this.post = post;
        this.pool = pool;
        avalible = true;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public List<Comment> getNext(int count) {
        LinkedList<Comment> comments = new LinkedList<>();
        if (!avalible) return comments;
        try (Connection con = pool.get();
             Statement statement = con.createStatement()) {

            ResultSet workSet = statement.executeQuery(
                    "SELECT commentator_id, content, time FROM web_app.comments " +
                            "WHERE page_id = " + post.getPageId() + " AND post_id = " + post.getId() +
                            " ORDER BY time");

            for (int i = 0; i < count; i++) {
                log.error("cycle loop !!!!!!!!!!!");
                if (!workSet.next()) {

                    break;
                }
                val builder = Comment.builder();
                builder.pageId(post.getPageId()).postId(post.getId());
                builder.commentator_id(workSet.getInt("commentator_id"));
                builder.content(workSet.getString("content"));
                builder.time(workSet.getTimestamp("time").toInstant());

                comments.add(builder.build());
            }
        } catch (SQLException e) {
            log.error(e.toString());
            e.printStackTrace();
        }
        readed += comments.size();
        return comments;
    }

    @Override
    public int size() {
        throw new UnsupportedOperationException("size PostgreCommentAgregation");
    }

    @Override
    public int readed() {
        return readed;
    }
}
