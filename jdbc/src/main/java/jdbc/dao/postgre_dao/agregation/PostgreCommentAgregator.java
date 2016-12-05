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


    /**
     * Loads Comments from db.
     * Actually loads all comments for single post.
     * @param count count of elements to load
     * @return List of Comments
     */
    @Override
    public List<Comment> getNext(int count) {
        LinkedList<Comment> comments = new LinkedList<>();
        if (!avalible) return comments;
        try (Connection con = pool.get();
             Statement statement = con.createStatement()) {

            ResultSet workSet = statement.executeQuery(
                    "SELECT commentator_id, content, time, first_name, second_name, nickname " +
                            "FROM web_app.comments JOIN web_app.pages ON commentator_id=id " +
                            "WHERE page_id = " + post.getPageId() + " AND post_id = " + post.getId() +
                            " ORDER BY time DESC");


            val builder = Comment.builder();
            while (workSet.next()){

                builder.pageId(post.getPageId()).postId(post.getId())
                        .commentator_id(workSet.getInt("commentator_id"))
                        .content(workSet.getString("content"))
                        .time(workSet.getTimestamp("time").toInstant())
                        .firstName(workSet.getString("first_name"))
                        .lastName(workSet.getString("second_name"))
                        .nickName(workSet.getString("nickname"));

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
    public int readed() {
        return readed;
    }
}
