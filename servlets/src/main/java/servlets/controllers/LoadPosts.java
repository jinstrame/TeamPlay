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


@WebServlet("/loadposts")
public class LoadPosts extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        @SuppressWarnings("unchecked") Agregator<Post> agregator =  (Agregator<Post>)req.getSession().getAttribute("agregator");
        List<Post> initPosts = agregator.getNext(10);
        req.getSession().setAttribute("posts", initPosts);

        req.getRequestDispatcher("jsppages/ajax/load_posts.jsp").forward(req,resp);
    }


}
