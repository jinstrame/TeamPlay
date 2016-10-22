package jdbc.dao.core;

import Entities.Page;

import java.util.List;
import java.util.Optional;

public interface PageDao {
    Optional<Page> get(int id);

    int getLastPostId(int id);

    List<Page> search(String nick);

    List<Page> search(String firstName, String secondName);

    boolean put(Page page);
}
