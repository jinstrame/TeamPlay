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
    public Optional<Page> sinmpleGet(int id) {
        try (Connection connection = pool.get();
             Statement statement = connection.createStatement()) {
            val builder = Page.builder();


            ResultSet set = statement.executeQuery(
                    "SELECT id, type, nickname, first_name, second_name" +
                            " FROM web_app.pages WHERE id = " + Integer.toString(id));
            set.next();
            builder.id(id);
            builder.pageType(PageTypes.valueOf(set.getString("type")));
            builder.nickname(set.getString("nickname"));
            builder.firstName(set.getString("first_name"));
            builder.secondName(set.getString("second_name"));

            return Optional.of(builder.build());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

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


            String one;
            String list;
            if (type.equals(PageTypes.PERSON)){
                one = "participant_id";
                list = "organisation_id";
            } else {
                one = "organisation_id";
                list = "participant_id";
            }
            //noinspection SqlResolve
            set = statement.executeQuery(
                    "SELECT " + list + ", participation_type" +
                            "FROM web_app.org_participants WHERE " + one + " = " + id_s);
            List <TeamRole> teamRoleList = new LinkedList<>();
            if (type == PageTypes.PERSON){
                val teamRoleBuilder = TeamRole.builder();
                teamRoleBuilder.firstName(firstName)
                        .lastName(lastName).nickName(nickName);
                while (set.next()){
                    teamRoleBuilder.role(set.getString("participation_type"));
                    teamRoleBuilder.team(set.getInt(list));
                    teamRoleList.add(teamRoleBuilder.build());
                }
            }else {
                while (set.next()) {
                    TeamRole role = get(set.getInt(list), id, set.getString("participation_type"));
                    teamRoleList.add(role);
                }
            }

            builder.team_list(teamRoleList);

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

            team.getTeam_list().put(to_add, type);

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

            team.getTeam_list().remove(to_remove);

        } catch (SQLException e) {
            log.error(e.toString());
            e.printStackTrace();
        }
    }

    private TeamRole get(int player, int team, String role){
        try (Connection connection = pool.get();
             Statement statement = connection.createStatement()){
            ResultSet set = statement.executeQuery(
                    "SELECT first_name, second_name, nickname FROM web_app.pages WHERE id = "
                            + player);
            if (set.next()) {
                val builder = TeamRole.builder();
                builder.firstName(set.getString("first_name"))
                        .lastName(set.getString("second_name"))
                        .nickName(set.getString("nickname"))
                        .player(player).team(team).role(role);
                return builder.build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<TeamRole> getTeamsList(Page page) {
        List<TeamRole> ret = new LinkedList<>();
        if (page.getPageType() == PageTypes.TEAM)
            return ret;

        try (Connection connection = pool.get();
             Statement statement = connection.createStatement()) {
            ResultSet set = statement.executeQuery(
                    "SELECT organisation_id, participation_type" +
                            "FROM web_app.org_participants WHERE participant_id = " + page.getId());

            val teamRoleBuilder = TeamRole.builder();
            teamRoleBuilder.firstName(page.getFirstName())
                    .lastName(page.getSecondName()).nickName(page.getNickname())
                    .player(page.getId());
            while (set.next()) {
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
                    "SELECT participant_id, participation_type" +
                            "FROM web_app.org_participants WHERE  = organisation_id" + page.getId() +
                            "");

            while (set.next()) {
                TeamRole role = get(set.getInt(list), id, set.getString("participation_type"));
                ret.add(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public List<TeamRole> getTeammatesList(int id) {
        return null;
    }
}