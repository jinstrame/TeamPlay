package jdbc.connection;

import org.junit.Test;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConnectionPoolTest {

    @Test
    public void TestConnectionPool() throws Exception{
        final ConnectionPool connectionPool = new ConnectionPool("src\\test\\resources\\jdbc.property");

        System.out.println("get connection 1");
        Connection c1 = connectionPool.get();
        System.out.println("get connection 2");
        Connection c2 = connectionPool.get();
        System.out.println("get connection 3");
        Connection c3 = connectionPool.get();

        Thread t = new Thread(() -> {
            System.out.println("thread tries to get a new connection");
            try (Connection c = connectionPool.get()) {
                System.out.println("connection success");
                Statement statement = c.createStatement();
                ResultSet set = statement.executeQuery("SELECT count(*) AS cnt FROM web_app.accounts");
                set.next();
                System.out.println("set recieved");
                System.out.println(set.getInt(1));
            } catch (SQLException e) {
                System.out.println("fail");
                e.printStackTrace();
            }
        });
        t.start();
        Thread.sleep(100);

        c1.close();
        System.out.println("connection 1 released");
        c2.close();
        System.out.println("connection 2 released");
        c3.close();
        System.out.println("connection 3 released");

        t.join();
    }
}