package jdbc.dao.postgre_dao;

import jdbc.DaoProvider;
import jdbc.connection.ConnectionPool;
import jdbc.dao.core.AccountDao;
import jdbc.dao.core.CommentDao;
import jdbc.dao.core.PageDao;
import jdbc.dao.core.PostDao;


public class PostgreProvider implements DaoProvider {

    private PostgreAccountDao accountDao;
    private PostgrePageDao pageDao;
    private PostgrePostDao postDao;
    private PostgreCommentDao commentDao;

    public PostgreProvider(String propertyFile){
        ConnectionPool connectionPool = new ConnectionPool(propertyFile);
        pageDao = new PostgrePageDao(connectionPool);
        postDao = new PostgrePostDao(connectionPool);
        accountDao = new PostgreAccountDao(connectionPool, pageDao);
        commentDao = new PostgreCommentDao(connectionPool);
    }

    @Override
    public AccountDao getAccountDao() {
        return accountDao;
    }

    @Override
    public PageDao getPageDao() {
        return pageDao;
    }

    @Override
    public PostDao getPostDao() {
        return postDao;
    }

    @Override
    public CommentDao getCommentDao() {
        return commentDao;
    }
}
