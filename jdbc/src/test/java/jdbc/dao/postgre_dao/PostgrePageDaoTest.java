package jdbc.dao.postgre_dao;

import Entities.Page;
import Entities.PageTypes;
import jdbc.connection.ConnectionPool;
import jdbc.dao.core.PageDao;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class PostgrePageDaoTest {
    @Test
    public void PutGetPageDaoTest(){
        ConnectionPool pool = new ConnectionPool("src\\test\\resources\\jdbc.property");
        Page.PageBuilder builder = Page.builder();
        builder.id(1).pageType(PageTypes.PERSON).nickname("dendi")
                .firstName("Daniil").secondName("Ishutin")
                .dob(LocalDate.now()).language("ru-RU");
        Page page = builder.build();


        PageDao pageDao = new PostgrePageDao(pool);
        boolean r = pageDao.put(page);
        System.out.println(r);

        Page page2 = null;
        Optional<Page> pageOpt = pageDao.get(1);
        if (pageOpt.isPresent()){
            page2 = pageOpt.get();
        }

        assertThat(page, equalTo(page2));
    }
}