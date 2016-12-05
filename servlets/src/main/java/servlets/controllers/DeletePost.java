package servlets.controllers;

import core.Entities.Page;
import jdbc.DaoProvider;
import jdbc.dao.core.PostDao;
import servlets.listeners.Initer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static servlets.listeners.DefaultSessionParams.AUTH;

@WebServlet("/removepost")
public class DeletePost extends HttpServlet {
    private PostDao postDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int page = Integer.parseInt(req.getParameter("id"));
        int post = Integer.parseInt(req.getParameter("post"));

        Page me = (Page) req.getSession().getAttribute(AUTH);
        if (me.getId() == page)
            postDao.delete(page, post);
        resp.sendRedirect("/page");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        DaoProvider provider = (DaoProvider) servletContext.getAttribute(Initer.DAO_PROVIDER);
        postDao = provider.getPostDao();
    }
}
