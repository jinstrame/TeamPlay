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
import java.util.LinkedList;
import java.util.List;

import static servlets.listeners.DefaultSessionParams.SEARCH;

@Log4j2
@WebServlet("/search")
public class Search extends HttpServlet {


    private PageDao pageDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String attr = req.getParameter("req");
        while (attr.startsWith(" "))
            attr = attr.substring(1);
        while (attr.endsWith(" "))
            attr = attr.substring(0, attr.length() - 1);

        log.error(attr);

        String[] wodrs = attr.split(" ", 2);

        List<Page> searchResult;

        if (wodrs.length == 2)
            searchResult = pageDao.search(wodrs[0], wodrs[1]);
        else if (wodrs.length == 1)
            searchResult = pageDao.search(wodrs[0]);
        else searchResult = new LinkedList<>();

        req.getSession().setAttribute(SEARCH, searchResult);
        req.getRequestDispatcher("jsppages/search.jsp").forward(req, resp);

    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        DaoProvider provider = (DaoProvider) servletContext.getAttribute(Initer.DAO_PROVIDER);
        pageDao = provider.getPageDao();
    }
}
