package jdbc.dao.postgre_dao;

import Entities.Page;
import Entities.PageTypes;
import jdbc.connection.ConnectionPool;
import jdbc.dao.core.PageDao;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;

import java.sql.*;
import java.util.List;
import java.util.Optional;


@Log4j2
@AllArgsConstructor
public class PostgrePageDao implements PageDao {

    private ConnectionPool pool;

    @Override
    public Optional<Page> get(int id){
        try (Connection connection = pool.get();
             Statement statement = connection.createStatement()) {
            val builder = Page.builder();
            ResultSet set = statement.executeQuery(
                    "SELECT id, type, nickname, first_name, second_name, dob, language, main_post_id, last_post_id" +
                            " FROM web_app.pages WHERE id = " + Integer.toString(id));

            set.next();
            builder.id(id);
            builder.pageType(PageTypes.valueOf(set.getString("type")));
            builder.nickname(set.getString("nickname"));
            builder.firstName(set.getString("first_name"));
            builder.secondName(set.getString("second_name"));
            builder.dob(set.getDate("dob").toLocalDate());
            builder.language(set.getString("language"));
            builder.mainPostId(set.getInt("main_post_id"));
            builder.lastPostId(set.getInt("last_post_id"));

            return Optional.of(builder.build());
        }
        catch (Exception e){
           log.error("failed to get page from database. " + e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public int getLastPostId(int id) {
        try (Connection connection = pool.get();
             Statement statement = connection.createStatement()) {
            ResultSet set = statement.executeQuery(
                    "SELECT last_post_id FROM web_app.pages WHERE id = "
                            + Integer.toString(id));
            if (set.next())
                return set.getInt("last_post_id");
            else return -1;
        } catch (SQLException e) {
            return -1;
        }
    }

    @Override
    public List<Page> search(String nick) {
        return null;
        /// TODO: 22.10.2016 create and test search methods
    }

    @Override
    public List<Page> search(String firstName, String secondName) {
        return null;
    }

    @Override
    public boolean put (Page page) {
        try (Connection connection = pool.get();
             Statement statement = connection.createStatement()){
            statement.execute(
                    "INSERT INTO web_app.pages VALUES (" +
                            page.getId() + ", " +
                            "'" + page.getPageType().name() + "', " +
                            "'" + page.getNickname() + "', " +
                            "'" + page.getFirstName() + "', " +
                            "'" + page.getSecondName() + "', " +
                            "'" + new Date(page.getDob().toEpochDay()).toString() + "', " +
                            "'" + page.getLanguage() + "', " +
                            0 + ", " +
                            0 +
                            ")");
            return true;
        } catch (SQLException e) {
            log.warn("cant add page to database");
            System.out.println(e.getMessage());
        }
        return false;
    }
}
