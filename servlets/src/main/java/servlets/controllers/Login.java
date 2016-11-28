package servlets.controllers;

import Entities.Page;
import servlets.filters.AuthFilter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/login")
public class Login extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Page page = (Page)req.getSession().getAttribute(AuthFilter.AUTH);
        resp.sendRedirect("page?id="+page.getId());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Page page = (Page)req.getSession().getAttribute(AuthFilter.AUTH);
        if (page != null) {
            resp.sendRedirect("page?id=" + page.getId());
            return;
        }
        req.getRequestDispatcher("login.html").forward(req,resp);
    }
}
