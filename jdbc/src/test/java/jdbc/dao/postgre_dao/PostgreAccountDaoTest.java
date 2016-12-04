package jdbc.dao.postgre_dao;

import core.Entities.Page;
import jdbc.connection.ConnectionPool;
import jdbc.dao.core.AccountDao;
import jdbc.dao.core.PageDao;
import org.junit.Test;
import security.Hash;

import java.util.Optional;

import static org.junit.Assert.*;


public class PostgreAccountDaoTest {
    @Test
    public void TestAuth(){
        String login = "jinstrame@gmail.com";
        String password = "68531149";

        Hash hash = new Hash(password);

        ConnectionPool pool = new ConnectionPool("src\\test\\resources\\jdbc.property");
        PageDao pageDao = new PostgrePageDao(pool);
        AccountDao accountDao = new PostgreAccountDao(pool, pageDao);

        Optional<Page> autorization = accountDao.isAutorized(login, hash.toHexString());
        assertTrue(autorization.isPresent());
    }
}