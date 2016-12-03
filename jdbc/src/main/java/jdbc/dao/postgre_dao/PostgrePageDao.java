package jdbc.dao.postgre_dao;

import Entities.Game;
import Entities.Page;
import Entities.PageTypes;
import Entities.TeamRole;
import jdbc.connection.ConnectionPool;
import jdbc.dao.core.PageDao;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;

import java.sql.*;
import java.sql.Date;
import java.util.*;


@Log4j2
@AllArgsConstructor
public class PostgrePageDao implements PageDao {

    private ConnectionPool pool;


        @Override
    public Optional<Page> get(int id){
        try (Connection connection = pool.get();
             Statement statement = connection.createStatement()) {
            val builder = Page.builder();


            String id_s = Integer.toString(id);
            PageTypes type;
            String firstName;
            String lastName;
            String nickName;


            ResultSet set = statement.executeQuery(
                    "SELECT id, type, nickname, first_name, second_name, dob, language, main_post_id, last_post_id" +
                            " FROM web_app.pages WHERE id = " + Integer.toString(id));
            set.next();
            type = PageTypes.valueOf(set.getString("type"));
            firstName = set.getString("first_name");
            lastName = set.getString("second_name");
            nickName = set.getString("nickname");
            builder.id(id);
            builder.pageType(type);
            builder.nickname(nickName);
            builder.firstName(firstName);
            builder.secondName(lastName);
            builder.dob(set.getDate("dob").toLocalDate());
            builder.language(set.getString("language"));
            builder.mainPostId(set.getInt("main_post_id"));
            builder.lastPostId(set.getInt("last_post_id"));

            if (type == PageTypes.PERSON) {
                set = statement.executeQuery(
                        "SELECT game_id, account_id, rank " +
                                "FROM web_app.game_participants WHERE page_id = " + Integer.toString(id));
                List<Game> games = new LinkedList<>();
                while (set.next()) {
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
                while (set.next()) {
                    sub.add(set.getInt("source_id"));
                }
                builder.subscribeList(sub);
            }else {
                set = statement.executeQuery(
                        "SELECT participant_id " +
                                "FROM web_app.org_participants WHERE organisation_id = " + id);
                List<Integer> sub = new LinkedList<>();
                while (set.next()) {
                    sub.add(set.getInt("participant_id"));
                }
                builder.subscribeList(sub);
            }

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

    @Override
    public void addToTeam(Page team, int to_add, String type) {
        try (Connection connection = pool.get();
             Statement statement = connection.createStatement()) {
            statement.execute(
                    "INSERT INTO web_app.org_participants VALUES (" +
                            to_add + ", " +
                            team.getId() + ", '" +
                            type + "')");

            team.getSubscribeList().add(to_add);

        } catch (SQLException e) {
            log.error(e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void removeFromTeam(Page team, int to_remove) {
        try (Connection connection = pool.get();
             Statement statement = connection.createStatement()) {
            statement.execute(
                    "DELETE FROM web_app.org_participants WHERE organisation_id=" +
                            team.getId() + " AND participant_id=" + to_remove);

            ListIterator<Integer> i = team.getSubscribeList().listIterator();
            while(i.hasNext())
                if (to_remove == i.next()) {
                    i.remove();
                    break;
                }

        } catch (SQLException e) {
            log.error(e.toString());
            e.printStackTrace();
        }
    }


    @Override
    public List<TeamRole> getTeamsList(Page page) {
        List<TeamRole> ret = new LinkedList<>();
        if (page.getPageType() == PageTypes.TEAM)
            return ret;
        try (Connection connection = pool.get();
             Statement statement = connection.createStatement()) {
            ResultSet set = statement.executeQuery(
                    "SELECT first_name, second_name, organisation_id, participation_type  FROM web_app.org_participants " +
                            "JOIN web_app.pages " +
                            "ON web_app.org_participants.participant_id = web_app.pages.id " +
                            "WHERE participant_id = " + page.getId());

            val teamRoleBuilder = TeamRole.builder();
            teamRoleBuilder.firstName(page.getFirstName())
                    .lastName(page.getSecondName()).nickName(page.getNickname())
                    .player(page.getId());
            while (set.next()) {
                teamRoleBuilder.teamName(set.getString("first_name"));
                teamRoleBuilder.teamGame(set.getString("second_name"));
                teamRoleBuilder.role(set.getString("participation_type"));
                teamRoleBuilder.team(set.getInt("organisation_id"));
                ret.add(teamRoleBuilder.build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public List<TeamRole> getTeamsList(int id) {
        Optional<Page> page = get(id);
        if (page.isPresent())
            return getTeamsList(page.get());
        else return new LinkedList<>();
    }

    @Override
    public List<TeamRole> getTeammatesList(Page page) {
        List<TeamRole> ret = new LinkedList<>();
        if (page.getPageType() == PageTypes.PERSON)
            return ret;

        try (Connection connection = pool.get();
             Statement statement = connection.createStatement()) {
            ResultSet set = statement.executeQuery(
                    "SELECT first_name, second_name, nickname, participant_id, participation_type  FROM web_app.org_participants " +
                            "JOIN web_app.pages " +
                            "ON web_app.org_participants.organisation_id = web_app.pages.id " +
                            "WHERE organisation_id = " + page.getId());

            val teamRoleBuilder = TeamRole.builder();
            teamRoleBuilder.firstName(page.getFirstName())
                    .lastName(page.getSecondName()).team(page.getId());
            while (set.next()) {
                teamRoleBuilder.firstName(set.getString("first_name"));
                teamRoleBuilder.lastName(set.getString("second_name"));
                teamRoleBuilder.nickName(set.getString("nickname"));
                teamRoleBuilder.role(set.getString("participation_type"));
                teamRoleBuilder.player(set.getInt("participant_id"));
                ret.add(teamRoleBuilder.build());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public List<TeamRole> getTeammatesList(int id) {
        Optional<Page> page = get(id);
        if (page.isPresent())
            return getTeammatesList(page.get());
        else return new LinkedList<>();
    }
}