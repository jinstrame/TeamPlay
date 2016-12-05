package jdbc.dao.postgre_dao;


import core.Entities.Post;
import jdbc.connection.ConnectionPool;
import jdbc.dao.core.PostDao;
import jdbc.dao.core.agregation.Agregator;
import jdbc.dao.postgre_dao.agregation.PostgrePostAgregator;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;


@Log4j2
@AllArgsConstructor
class PostgrePostDao implements PostDao {

    // TODO: 22.10.2016 implement post dao

    private ConnectionPool pool;

    @Override
    public Post get(int postId, int pageId) {
        try (Connection connection = pool.get();
             Statement statement = connection.createStatement()) {
            ResultSet set = statement.executeQuery(
                    "SELECT post_time, content from web_app.posts WHERE " +
                            "page_id = " + pageId + " AND " +
                            "post_id = " + postId );
            set.next();
            val builder = Post.builder();
            builder.id(postId).pageId(pageId)
                    .time(set.getTimestamp("post_time").toInstant())
                    .content(set.getString("content"));

            set = statement.executeQuery(
                    "SELECT nickname, first_name, second_name FROM web_app.pages " +
                            "WHERE id = " + pageId );
            set.next();
            builder.firstName(set.getString("first_name"))
                    .lastName(set.getString("second_name"))
                    .nickname(set.getString("nickname"));

            return builder.build();

        } catch (SQLException e) {
            log.error(e.toString());
            e.printStackTrace();
        }

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
    public boolean delete(int pageId, int postId) {
        try (Connection connection = pool.get();
             Statement statement = connection.createStatement()) {
            String q =  "DELETE FROM web_app.posts WHERE page_id = " + pageId +
                    " AND post_id = " + postId;
            statement.execute(q);
            return true;

        } catch (SQLException e) {
            log.warn("cant delete post");
            e.printStackTrace();
        }
        return false;
    }



    @Override
    public Agregator<Post> agregator(List<Integer> pageIds) {
        return new PostgrePostAgregator(pageIds, pool);
    }
}
