package jdbc.dao.postgre_dao;

import Entities.Game;
import Entities.Page;
import Entities.PageTypes;
import Entities.Post;
import jdbc.connection.ConnectionPool;
import jdbc.dao.core.PageDao;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
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


            set = statement.executeQuery(
                    "SELECT game_id, account_id, rank " +
                            "FROM web_app.game_participants WHERE page_id = " + Integer.toString(id));

            List<Game> games = new LinkedList<>();
            while (set.next()){
                val gameBuilder = Game.builder();
                gameBuilder.page(id)
                        .game(set.getString("game_id"))
                        .account(set.getString("account_id"))
                        .rank(set.getString("rank"));
                games.add(gameBuilder.build());
            }
            builder.gameIds(games);


            set = statement.executeQuery(
                    "SELECT source_id " +
                            "FROM web_app.subscribe WHERE subscriber_id = " + Integer.toString(id));
            List<Integer> sub = new LinkedList<>();
            while (set.next()){
                sub.add(set.getInt("source_id"));
            }
            builder.subscribeList(sub);



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
                            "'" + Date.valueOf(page.getDob()).toString() + "', " +
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

    @Override
    public void addGame(Game game) {
        try (Connection connection = pool.get();
             Statement statement = connection.createStatement()){
            statement.execute(
                    "INSERT INTO web_app.game_participants VALUES (" +
                            game.getPage() + ", " +
                            "'" + game.getGame() + "', " +
                            "'" + game.getAccount() + "', " +
                            "'" + game.getRank() + "')");
        } catch (SQLException e) {
            log.error(e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void subscribe(Page subscriber, int to_id) {
        try (Connection connection = pool.get();
             Statement statement = connection.createStatement()) {
            statement.execute(
                    "INSERT INTO web_app.subscribe VALUES (" +
                            subscriber.getId() + ", " +
                            to_id + ")");

            subscriber.getSubscribeList().add(to_id);

        } catch (SQLException e) {
            log.error(e.toString());
            e.printStackTrace();
        }
    }

        @Override
    public void unSubscribe(Page subscriber, int from_id) {
        try (Connection connection = pool.get();
            Statement statement = connection.createStatement()) {
            statement.execute(
                    "DELETE FROM web_app.subscribe WHERE subscriber_id=" +
                            subscriber.getId() + " AND source_id=" + from_id);

            ListIterator<Integer> i = subscriber.getSubscribeList().listIterator();
            while(i.hasNext())
                if (from_id == i.next()) {
                    i.remove();
                    break;
                }

        } catch (SQLException e) {
            log.error(e.toString());
            e.printStackTrace();
        }
    }
}
