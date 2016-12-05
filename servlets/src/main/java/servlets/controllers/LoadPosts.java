package servlets.controllers;


import core.Entities.Post;
import jdbc.dao.core.agregation.Agregator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static servlets.listeners.DefaultSessionParams.AGREGATOR;
import static servlets.listeners.DefaultSessionParams.POSTS;


@WebServlet("/loadposts")
public class LoadPosts extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        @SuppressWarnings("unchecked") Agregator<Post> agregator =  (Agregator<Post>)req.getSession().getAttribute(AGREGATOR);
        List<Post> posts = agregator.getNext(10);
        req.getSession().setAttribute(POSTS, posts);

        req.getRequestDispatcher("jsppages/ajax/load_posts.jsp").forward(req,resp);
    }


}
