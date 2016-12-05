package servlets.controllers;

import core.Entities.Page;
import core.Entities.Post;
import jdbc.DaoProvider;
import jdbc.dao.core.PostDao;
import jdbc.dao.core.agregation.Agregator;
import lombok.extern.log4j.Log4j2;
import servlets.listeners.Initer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static servlets.listeners.DefaultSessionParams.*;


@Log4j2
@WebServlet("/feed")
public class Feed  extends HttpServlet {
    private PostDao postDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Page page = (Page)req.getSession().getAttribute(AUTH);

        Agregator<Post> agregator = postDao.agregator(page.getSubscribeList());
        List<Post> posts = agregator.getNext(10);
        req.getSession().setAttribute(POSTS, posts);
        req.getSession().setAttribute(FEED_AGREGATOR, agregator);

        log.error("feed");

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("jsppages/feed.jsp");
        requestDispatcher.forward(req,resp);
    }


    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        DaoProvider provider = (DaoProvider) servletContext.getAttribute(Initer.DAO_PROVIDER);
        postDao = provider.getPostDao();
    }
}
