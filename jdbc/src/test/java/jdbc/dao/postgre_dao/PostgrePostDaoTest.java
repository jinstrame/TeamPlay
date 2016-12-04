package jdbc.dao.postgre_dao;

import core.Entities.Page;
import core.Entities.Post;
import jdbc.connection.ConnectionPool;
import jdbc.dao.core.PageDao;
import jdbc.dao.core.PostDao;
import jdbc.dao.core.agregation.Agregator;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class PostgrePostDaoTest {

    private ConnectionPool connectionPool = new ConnectionPool("src\\test\\resources\\jdbc.property");
    private PageDao pageDao = new PostgrePageDao(connectionPool);
    private PostDao postDao = new PostgrePostDao(connectionPool);

    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    public void putAgregatorTest() throws Exception {
        Page p1 = pageDao.get(1).get();
        Page p2 = pageDao.get(2).get();

        Post.PostBuilder builder = Post.builder();
        builder.content("asdasdasdas");
        builder.id(1);
        builder.pageId(p1.getId());

        Post post1 = builder.build();

        builder.pageId(p2.getId());
        builder.content("asdasdasdasasdasdasdas");
        Post post2 = builder.build();


        builder.pageId(p1.getId());
        builder.id(2);
        builder.content("asdasdasdasasdasdasdasasdasdasdasasdasdasdas");
        Post post3 = builder.build();

        postDao.put(post1);
        Thread.sleep(100);
        postDao.put(post2);
        Thread.sleep(100);
        postDao.put(post3);

        LinkedList<Integer> a = new LinkedList<>();
        a.add(1);
        a.add(2);

        Agregator<Post> agregator = postDao.agregator(a);
        List<Post> l = agregator.getNext(3);

        assertThat(l.get(0).getContent(), equalTo(post3.getContent()));
        assertThat(l.get(1).getContent(), equalTo(post2.getContent()));
        assertThat(l.get(2).getContent(), equalTo(post1.getContent()));
    }


    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Test
    public void putAgregatorTest2() throws Exception {
        Page p1 = pageDao.get(1).get();
        List<Integer> ids = new LinkedList<>();
        ids.add(p1.getId());
        Agregator<Post> posts = postDao.agregator(ids);

        int s1 = posts.getNext(10).size();
        int s2 = posts.getNext(10).size();

        System.out.println(s1);
        System.out.println(s2);
    }


}