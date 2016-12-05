package jdbc.dao.postgre_dao;

import core.Entities.Account;
import core.Entities.Page;
import jdbc.connection.ConnectionPool;
import jdbc.dao.core.AccountDao;
import jdbc.dao.core.PageDao;
import lombok.AllArgsConstructor;
import lombok.val;
import security.Hash;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;


@AllArgsConstructor
public class PostgreAccountDao implements AccountDao {

    private ConnectionPool pool;
    private PageDao pageDao;

    @Override
    public Optional<Page> isAutorized(String login, String h_password) {

        val accountBuilder = Account.builder();
        accountBuilder.pageId(-1);
        Account account = accountBuilder.build();

        try (Connection connection = pool.get();
             Statement statement = connection.createStatement()) {

            ResultSet set = statement.executeQuery(
                    "SELECT login, h_password, token, person_id FROM " +
                            "web_app.accounts WHERE login = '"+ login + "'");

            set.next();
            accountBuilder.pageId(set.getInt("person_id"));
            accountBuilder.login(set.getString("login"));
            accountBuilder.h_password(set.getString("h_password"));
            accountBuilder.token(set.getInt("token"));


            Account t_account = accountBuilder.build();
            if (t_account.getH_password().equals(h_password))
                account = t_account;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return pageDao.get(account.getPageId());
    }


    private void putAccount(Account account, Statement statement) throws SQLException {
        statement.execute(
                "INSERT INTO web_app.accounts VALUES ('" +
                        account.getLogin() + "','" +
                        account.getH_password() + "','" +
                        account.getToken() + "','" +
                        account.getPageId() + "')");

    }

    @Override
    public Account register(Page page, String login, String password) throws Exception {
        try (Connection connection = pool.get();
             Statement statement = connection.createStatement()) {

            ResultSet set = statement.executeQuery(
                    "SELECT max(person_id) FROM web_app.accounts");
            set.next();

            int lastId = set.getInt(1);
            Hash hash = new Hash(password);

            val accountBuilder = Account.builder();
            accountBuilder.login(login)
                    .h_password(hash.toHexString())
                    .token(0)
                    .pageId(lastId + 1);

            Account account = accountBuilder.build();

            page.setId(account.getPageId());

            pageDao.put(page);
            putAccount(account, statement);

            return account;
        }
    }
}
