package jdbc.dao.postgre_dao;

import core.Entities.Game;
import core.Entities.Page;
import core.Entities.PageTypes;
import core.Entities.TeamRole;
import jdbc.connection.ConnectionPool;
import jdbc.dao.core.PageDao;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import lombok.val;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
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
                    "SELECT id, type, nickname, first_name, second_name, dob, language, last_post_id, about, timezone" +
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
            builder.lastName(lastName);
            builder.dob(set.getDate("dob").toLocalDate());
            builder.language(set.getString("language"));
            builder.lastPostId(set.getInt("last_post_id"));
            builder.about(set.getString("about"));
            builder.timeZone(TimeZone.getTimeZone(set.getString("timezone")));

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

            set = statement.executeQuery(
                    "SELECT ava_id FROM web_app.page_avatars WHERE page_id = " + Integer.toString(id));
            if (set.next())
                builder.avaId(set.getString("ava_id"));

            return Optional.of(builder.build());
        }
        catch (Exception e){
           log.error("failed to delete page from database. " + e.getMessage());
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
    public List<Page> search(String word) {
        try (Connection connection = pool.get();
             Statement statement = connection.createStatement()){
            ResultSet set = statement.executeQuery(
                    "SELECT id, type, nickname, first_name, second_name " +
                            "FROM web_app.pages WHERE " +
                            "nickname='" + word + "' OR " +
                            "first_name='" + word + "' OR " +
                            "second_name='" + word + "'"
            );

            LinkedList<Page> ret = new LinkedList<>();
            val builder = Page.builder();
            while (set.next()){
                builder.id(set.getInt("id"))
                        .pageType(PageTypes.valueOf(set.getString("type")))
                        .nickname(set.getString("nickname"))
                        .firstName(set.getString("first_name"))
                        .lastName(set.getString("second_name"));
                ret.add(builder.build());
            }

            return ret;

        } catch (SQLException e) {
            log.error(e.toString());
            e.printStackTrace();
        }

        return new LinkedList<>();
    }

    @Override
    public List<Page> search(String word1, String word2) {

        try (Connection connection = pool.get();
             Statement statement = connection.createStatement()){
            ResultSet set = statement.executeQuery(
                    "SELECT id, type, nickname, first_name, second_name " +
                            "FROM web_app.pages WHERE (" +
                            "first_name='" + word1 + "' AND second_name='" + word2 + "') OR (" +
                            "first_name='" + word2 + "' AND second_name='" + word1 + "')"
            );

            LinkedList<Page> ret = new LinkedList<>();
            val builder = Page.builder();
            while (set.next()){
                builder.id(set.getInt("id"))
                        .pageType(PageTypes.valueOf(set.getString("type")))
                        .nickname(set.getString("nickname"))
                        .firstName(set.getString("first_name"))
                        .lastName(set.getString("second_name"));
                ret.add(builder.build());
            }

            return ret;

        } catch (SQLException e) {
            log.error(e.toString());
            e.printStackTrace();
        }

        return new LinkedList<>();
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
                            "'" + page.getLastName() + "', " +
                            "'" + java.sql.Date.valueOf(page.getDob()).toString() + "', " +
                            "'" + page.getTimeZone().getID() + "', " +
                            "'" + page.getLanguage() + "', " +
                            0 + ", " +
                            "''" +
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
                            "ON web_app.org_participants.organisation_id = web_app.pages.id " +
                            "WHERE participant_id = " + page.getId());

            val teamRoleBuilder = TeamRole.builder();
            teamRoleBuilder.firstName(page.getFirstName())
                    .lastName(page.getLastName()).nickName(page.getNickname())
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
                            "ON web_app.org_participants.participant_id = web_app.pages.id " +
                            "WHERE organisation_id = " + page.getId());

            val teamRoleBuilder = TeamRole.builder();
            teamRoleBuilder.firstName(page.getFirstName())
                    .lastName(page.getLastName()).team(page.getId());
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

    @Override
    public void updateAvatar(Page page, String avaId){
        try (Connection connection = pool.get();
             Statement statement = connection.createStatement()) {
            statement.execute("DELETE FROM web_app.page_avatars WHERE page_id = " + page.getId());
            statement.execute("INSERT INTO web_app.page_avatars VALUES (" +
                            page.getId() + ", '" + avaId +  "')");

            page.setAvaId(avaId);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateName(Page page, String firstName, String lastName, String nickName, String about){
        String separator = "";
        StringBuilder sb = new StringBuilder("UPDATE web_app.pages SET ");
        if (firstName != null){
            sb.append(separator).append("first_name='").append(firstName).append("'");
            page.setFirstName(firstName);
            separator = ", ";
        }

        if (lastName != null) {
            sb.append(separator).append("second_name='").append(lastName).append("'");
            page.setLastName(lastName);
            separator = ", ";
        }

        if (about != null) {
            sb.append(separator).append("about='").append(about).append("'");
            page.setAbout(about);
            separator = ", ";
        }
        if (nickName != null) {
            sb.append(separator).append("nickname='").append(nickName).append("'");
            page.setNickname(nickName);
        }

        sb.append(" WHERE id=").append(page.getId());

        try (Connection connection = pool.get();
             Statement statement = connection.createStatement()) {
            statement.execute(sb.toString());
        } catch (SQLException e) {
            log.error(e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void updateContext(Page page, LocalDate date, String lang, TimeZone tz){
        String separator = "";
        StringBuilder sb = new StringBuilder("UPDATE web_app.pages SET ");
        if (date != null){
            sb.append(separator).append("dob='")
                    .append(java.sql.Date.valueOf(date).toString()).append("'");
            separator = ", ";
            page.setDob(date);
        }

        if (lang != null) {
            sb.append(separator).append("language='").append(lang).append("'");
            separator = ", ";
            page.setLanguage(lang);
        }
        if (tz != null) {
            sb.append(separator).append("timezone='").append(tz.getID()).append("'");
            page.setTimeZone(tz);
        }

        sb.append(" WHERE id=").append(page.getId());

        try (Connection connection = pool.get();
             Statement statement = connection.createStatement()) {
            statement.execute(sb.toString());
        } catch (SQLException e) {
            log.error(e.toString());
            e.printStackTrace();
        }


    }
}