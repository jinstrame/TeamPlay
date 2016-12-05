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
import static servlets.listeners.DefaultSessionParams.PLAYERS_LIST;


@WebServlet("/players")
public class Players extends HttpServlet {

    private PageDao pageDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String team_s = req.getParameter("team");
        if (team_s == null)
            return;

        List<TeamRole> players;
        @SuppressWarnings("UnusedAssignment") int team = Integer.parseInt(team_s);

        Page me = (Page) req.getSession().getAttribute(AUTH);
        Page page = (Page) req.getSession().getAttribute("page");
        if (team == me.getId())
            players = pageDao.getTeammatesList(me);
        else if (team == page.getId())
            players = pageDao.getTeammatesList(page);
        else
            players = pageDao.getTeammatesList(team);

        req.getSession().setAttribute(PLAYERS_LIST, players);
        req.getRequestDispatcher("jsppages/players.jsp").forward(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        DaoProvider provider = (DaoProvider) servletContext.getAttribute(Initer.DAO_PROVIDER);
        pageDao = provider.getPageDao();
    }
}