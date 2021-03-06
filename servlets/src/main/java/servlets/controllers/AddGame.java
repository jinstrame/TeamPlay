package servlets.controllers;

import core.Entities.Game;
import core.Entities.Page;
import jdbc.DaoProvider;
import jdbc.dao.core.PageDao;
import lombok.extern.log4j.Log4j2;
import lombok.val;
import servlets.listeners.Initer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static servlets.listeners.DefaultSessionParams.AUTH;
import static servlets.listeners.DefaultSessionParams.PAGE;

@Log4j2
@WebServlet("/addgame")
public class AddGame extends HttpServlet {
    private PageDao pageDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("jsppages/addgame.jsp");
        requestDispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        val builder = Game.builder();
        int pageId = Integer.parseInt(req.getParameter(PAGE));
        Page me = (Page) req.getSession().getAttribute(AUTH);
        if (me.getId() == pageId) {
            builder.page(Integer.parseInt(req.getParameter("page")))
                    .game(req.getParameter("gameId"))
                    .account(req.getParameter("account"))
                    .rank(req.getParameter("rank"));

            Game game = builder.build();
            pageDao.addGame(builder.build());
        }

        resp.sendRedirect("/page");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        DaoProvider provider = (DaoProvider) servletContext.getAttribute(Initer.DAO_PROVIDER);
        pageDao = provider.getPageDao();
    }
}
