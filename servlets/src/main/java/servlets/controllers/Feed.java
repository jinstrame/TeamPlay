package servlets.controllers;

import Entities.Page;
import Entities.Post;
import jdbc.DaoProvider;
import jdbc.dao.core.PostDao;
import lombok.extern.log4j.Log4j2;
import servlets.filters.AuthFilter;
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


@Log4j2
@WebServlet("/feed")
public class Feed  extends HttpServlet {
    private PostDao postDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Page page = (Page)req.getSession().getAttribute(AuthFilter.AUTH);

        List<Post> posts = postDao.agregator(page.getSubscribeList()).getNext(15);
        req.getSession().setAttribute("sub_posts", posts);

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
