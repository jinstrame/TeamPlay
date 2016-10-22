package jdbc.dao.postgre_dao;

import Entities.Account;
import jdbc.dao.core.AccountDao;

public class PostgreAccountDao implements AccountDao {

    // TODO: 22.10.2016 implement account dao

    @Override
    public Account get(String login) {
        return null;
    }

    @Override
    public boolean create(String login) {
        return false;
    }
}
