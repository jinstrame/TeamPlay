package jdbc.dao.core;

import Entities.Account;

public interface AccountDao {
    Account get(String login);

    boolean create(String login);
}
