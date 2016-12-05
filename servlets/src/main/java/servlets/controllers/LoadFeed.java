package servlets.controllers;

import core.Entities.Post;
import jdbc.dao.core.agregation.Agregator;
import lombok.extern.log4j.Log4j2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static servlets.listeners.DefaultSessionParams.FEED_AGREGATOR;
import static servlets.listeners.DefaultSessionParams.POSTS;


@Log4j2
@WebServlet("/loadfeed")
public class LoadFeed extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        @SuppressWarnings("unchecked") Agregator<Post> agregator =  (Agregator<Post>)req.getSession().getAttribute(FEED_AGREGATOR);
        List<Post> posts = agregator.getNext(10);
        req.getSession().setAttribute(POSTS, posts);
        log.error("loadfeed");
        req.getRequestDispatcher("jsppages/ajax/load_feed.jsp").forward(req,resp);
    }
}
