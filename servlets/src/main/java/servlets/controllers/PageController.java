package servlets.controllers;

import Entities.Page;
import jdbc.DaoProvider;
import jdbc.dao.core.PageDao;
import lombok.extern.log4j.Log4j2;
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

@Log4j2
@WebServlet("/id")
public class PageController extends HttpServlet {

    private PageDao pageDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String s = (String)req.getParameterMap().keySet().toArray()[0];
        int a = Integer.parseInt(req.getParameter(s));

        @SuppressWarnings("OptionalGetWithoutIsPresent") Page page = pageDao.get(a).get();
        if (page != null)
            System.out.println("OK OK OK OK OK OK OK OK OK OK ");
        else System.out.println("ERROR ERROR ERROR ERROR ERROR");
        req.getSession().setAttribute("page", page);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("dindex.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        DaoProvider provider = (DaoProvider) servletContext.getAttribute(Initer.DAO_PROVIDER);
        pageDao = provider.getPageDao();
    }
}
