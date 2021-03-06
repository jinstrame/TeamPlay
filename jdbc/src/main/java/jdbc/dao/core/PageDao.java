package jdbc.dao.core;

import core.Entities.Game;
import core.Entities.Page;
import core.Entities.TeamRole;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

public interface PageDao {

    Optional<Page> get(int id);

    int getLastPostId(int id);

    List<TeamRole> getTeamsList(Page page);

    List<TeamRole> getTeamsList(int id);

    List<TeamRole> getTeammatesList (Page page);

    List<TeamRole> getTeammatesList (int id);

    List<Page> search(String nick);

    List<Page> search(String firstName, String secondName);

    boolean put(Page page);

    void addGame(Game game);

    void subscribe(Page subscriber, int to_id);

    void unSubscribe(Page subscriber, int from_id);

    void addToTeam(Page team, int to_add, String type);

    void removeFromTeam(Page team, int to_remove);

    void updateAvatar(Page page, String avaId);

    void updateName(Page page, String firstName, String lastName, String nickName, String about);

    void updateContext(Page page, LocalDate date, String lang, TimeZone tz);
}
