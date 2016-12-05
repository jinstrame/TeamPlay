package servlets.controllers;


import core.Entities.Page;
import jdbc.DaoProvider;
import jdbc.dao.core.CommentDao;
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


//remcomment?page='+page+'&post='+post+'&id='+id
@WebServlet("/remcomment")
public class RemoveComment extends HttpServlet {
    private CommentDao commentDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String page = req.getParameter("page");
        String post = req.getParameter("post");
        String commentator = req.getParameter("commentator");
        String id = req.getParameter("id");

        Page me = (Page) req.getSession().getAttribute(AUTH);

        if (me.getId() == Integer.parseInt(commentator))
            commentDao.delete(page, post, commentator, id);

        resp.sendRedirect("/post?id=" +
                post +
                "&page=" +
                page);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        DaoProvider provider = (DaoProvider) servletContext.getAttribute(Initer.DAO_PROVIDER);
        commentDao = provider.getCommentDao();
    }
}

