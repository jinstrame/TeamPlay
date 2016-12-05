package servlets.controllers;

import core.Entities.Page;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static servlets.listeners.DefaultSessionParams.AUTH;

@WebServlet("/auth/login")
public class Login extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Page page = (Page)req.getSession().getAttribute(AUTH);
        resp.sendRedirect("page?id="+page.getId());

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Page page = (Page)req.getSession().getAttribute(AUTH);
        if (page != null) {
            resp.sendRedirect("page?id=" + page.getId());
            return;
        }
        req.getRequestDispatcher("/jsppages/login.jsp").forward(req,resp);
    }
}
