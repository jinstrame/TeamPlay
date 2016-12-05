package servlets.controllers;

import core.Entities.Page;
import core.Entities.TeamRole;
import jdbc.DaoProvider;
import jdbc.dao.core.PageDao;
import servlets.listeners.Initer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static servlets.listeners.DefaultSessionParams.AUTH;
import static servlets.listeners.DefaultSessionParams.TEAMMATES_LIST;

@WebServlet("/teammates")
public class Teammates extends HttpServlet {

    private PageDao pageDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Page me = (Page) req.getSession().getAttribute(AUTH);
        List<TeamRole> teammates = pageDao.getTeammatesList(me);
        req.getSession().setAttribute(TEAMMATES_LIST, teammates);
        req.getRequestDispatcher("jsppages/teammates.jsp").forward(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        DaoProvider provider = (DaoProvider) servletContext.getAttribute(Initer.DAO_PROVIDER);
        pageDao = provider.getPageDao();
    }
}
