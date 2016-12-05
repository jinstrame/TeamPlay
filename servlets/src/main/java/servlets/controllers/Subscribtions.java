package servlets.controllers;


import core.Entities.Page;
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
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static servlets.listeners.DefaultSessionParams.AUTH;
import static servlets.listeners.DefaultSessionParams.SUBSCRIBERS_LIST;

@WebServlet("/subscribtions")
public class Subscribtions extends HttpServlet{

    private PageDao pageDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Page me = (Page) req.getSession().getAttribute(AUTH);
        List<Integer> subList = me.getSubscribeList();
        List<Page> pageSubList = new LinkedList<>();
        for (Integer i: subList) {
            Optional<Page> opt = pageDao.get(i);
            //noinspection OptionalIsPresent
            if (opt.isPresent())
                pageSubList.add(opt.get());
        }
        req.getSession().setAttribute(SUBSCRIBERS_LIST, pageSubList);
        req.getRequestDispatcher("jsppages/subsrcribtions.jsp").forward(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        DaoProvider provider = (DaoProvider) servletContext.getAttribute(Initer.DAO_PROVIDER);
        pageDao = provider.getPageDao();
    }
}
