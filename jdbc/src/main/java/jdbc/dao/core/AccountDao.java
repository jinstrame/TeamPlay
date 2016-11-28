package jdbc.dao.core;

import Entities.Account;
import Entities.Page;

import java.util.Optional;

public interface AccountDao {
    Optional<Page> isAutorized(String login, String h_password);

    Account register(Page page, String login, String password) throws Exception;
}
