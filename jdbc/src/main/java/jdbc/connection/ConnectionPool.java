package jdbc.connection;

import lombok.extern.log4j.Log4j2;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Supplier;

@Log4j2
public class ConnectionPool implements Supplier<Connection>, AutoCloseable {

    private static final Integer DEFAULT_POOL_SIZE = 15;
    private BlockingQueue<PooledConnection> connections;

    public ConnectionPool(String propertyFile){
        Properties properties = new Properties();
        try(FileInputStream fis = new FileInputStream(propertyFile)) {
            properties.load(fis);
        }
        catch (IOException e){
            log.error("Load database properties failed");
        }

        String driver = properties.getProperty("driver");
        String url = properties.getProperty("url");
        String userName = properties.getProperty("user");
        String password = properties.getProperty("password");
        Integer poolSize;
        if (properties.containsKey("pool_size"))
            poolSize = Integer.valueOf(properties.getProperty("pool_size"));
        else poolSize = DEFAULT_POOL_SIZE;

        connections = new LinkedBlockingQueue<>();


        try {
            log.error("!!! Driver = " + driver);
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            log.error("Driver load for database failed:" + driver);
        }

        try {
            for (int i = 0; i < poolSize; i++) {
                Connection con = DriverManager.getConnection(url, userName, password);
                connections.add(PooledConnection.create(con, connections));
            }
        }catch (SQLException e) {
            log.error("Can`t establish connection with database." + e.getMessage());
        }
    }

    @Override
    public Connection get(){
        try {
            return connections.take();
        } catch (InterruptedException e) {
            log.warn("interrupted while waiting for connection to database. " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() throws Exception {
        for (PooledConnection pooledCon: connections) {
            try {
                pooledCon.reallyClose();
            } catch (SQLException e){
                log.warn(e.getMessage());
            }
        }
    }
}
