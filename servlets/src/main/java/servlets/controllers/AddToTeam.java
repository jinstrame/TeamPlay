package servlets.controllers;


import core.Entities.Page;
import jdbc.DaoProvider;
import jdbc.dao.core.PageDao;
import lombok.extern.log4j.Log4j2;
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

@Log4j2
@WebServlet("/addplayer")
public class AddToTeam extends HttpServlet {
    private PageDao pageDao;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int player = Integer.parseInt(req.getParameter("player"));
        String role = req.getParameter("role");
        if (role == null) role = "";
        Page teamPage = (Page)req.getSession().getAttribute(AUTH);
        pageDao.addToTeam(teamPage, player, role);

        resp.sendRedirect("/page?id=" + player);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        DaoProvider provider = (DaoProvider) servletContext.getAttribute(Initer.DAO_PROVIDER);
        pageDao = provider.getPageDao();
    }
}
