package jdbc;

import jdbc.dao.core.AccountDao;
import jdbc.dao.core.CommentDao;
import jdbc.dao.core.PageDao;
import jdbc.dao.core.PostDao;
import jdbc.dao.postgre_dao.PostgreProvider;
import lombok.SneakyThrows;

public interface DaoProvider {

    @SneakyThrows
    static DaoProvider getProvider(String strategy, String propertyPath){
        switch (strategy) {
            case ("postgres"):{
                return new PostgreProvider(propertyPath);
            }
            default:{
                throw new UnsupportedOperationException();
            }
        }
    }

    AccountDao getAccountDao();
    PageDao getPageDao();
    PostDao getPostDao();
    CommentDao getCommentDao();
}
