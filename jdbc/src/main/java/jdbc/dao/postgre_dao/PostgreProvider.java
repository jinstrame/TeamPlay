package jdbc.dao.postgre_dao;

import jdbc.DaoProvider;
import jdbc.connection.ConnectionPool;
import jdbc.dao.core.AccountDao;
import jdbc.dao.core.PageDao;
import jdbc.dao.core.PostDao;
import lombok.AllArgsConstructor;
import lombok.Setter;


public class PostgreProvider implements DaoProvider {

    private PostgreAccountDao accountDao;
    private PostgrePageDao pageDao;
    private PostgrePostDao postDao;

    public PostgreProvider(String propertyFile){
        ConnectionPool connectionPool = new ConnectionPool(propertyFile);
        accountDao = new PostgreAccountDao();
        pageDao = new PostgrePageDao(connectionPool);
        postDao = new PostgrePostDao(0);
    }

    @Override
    public AccountDao getAccountDao() {
        throw new RuntimeException(new UnsupportedOperationException());
    }

    @Override
    public PageDao getPageDao() {
        return pageDao;
    }

    @Override
    public PostDao getPosttDao() {
        throw new RuntimeException(new UnsupportedOperationException());
    }
}
