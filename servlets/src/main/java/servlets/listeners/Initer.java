package servlets.listeners;

import jdbc.DaoProvider;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.FileInputStream;
import java.util.Properties;



@Log4j2
@WebListener
public class Initer implements ServletContextListener {

    public static final String DAO_PROVIDER = "daoProvider";
    @Override
    @SneakyThrows
    public void contextInitialized(ServletContextEvent sce) {

        ServletContext context = sce.getServletContext();

        Properties properties = new Properties();
        properties.load(new FileInputStream("C:\\Users\\USL\\IdeaProjects\\TeamPlay\\servlets\\src\\main\\resources\\launch.property"));

        DaoProvider provider = DaoProvider.getProvider(properties.getProperty("db"), properties.getProperty("dbProperties"));

        context.setAttribute(DAO_PROVIDER, provider);
    }
}