package jdbc.dao.core;

import Entities.Game;
import Entities.Page;

import java.util.List;
import java.util.Optional;

public interface PageDao {
    Optional<Page> get(int id);

    int getLastPostId(int id);

    List<Page> search(String nick);

    List<Page> search(String firstName, String secondName);

    boolean put(Page page);

    void addGame(Game game);

    void subscribe(Page subscriber, int to_id);

    void unSubscribe(Page subscriber, int from_id);
}
