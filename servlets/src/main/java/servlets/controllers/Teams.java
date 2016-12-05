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
import static servlets.listeners.DefaultSessionParams.TEAMS_LIST;

@WebServlet("/teams")
public class Teams extends HttpServlet {

    private PageDao pageDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String player_s = req.getParameter("player");
        if (player_s == null)
            return;

        List<TeamRole> teammates;
        @SuppressWarnings("UnusedAssignment") int player = Integer.parseInt(player_s);

        Page me = (Page) req.getSession().getAttribute(AUTH);
        Page page = (Page) req.getSession().getAttribute("page");
        if (player == me.getId())
            teammates = pageDao.getTeamsList(me);
        else if (player == page.getId())
            teammates = pageDao.getTeamsList(page);
        else
            teammates = pageDao.getTeamsList(player);

        req.getSession().setAttribute(TEAMS_LIST, teammates);
        req.getRequestDispatcher("jsppages/teams.jsp").forward(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        DaoProvider provider = (DaoProvider) servletContext.getAttribute(Initer.DAO_PROVIDER);
        pageDao = provider.getPageDao();
    }
}
