package servlets.controllers;

import Entities.Page;
import Entities.PageTypes;
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
import java.time.LocalDate;

@Log4j2
@WebServlet("/register")

public class Register extends HttpServlet {

    private PageDao pageDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("register.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        val builder = Page.builder();
        builder.firstName(req.getParameter("firstname"));
        builder.secondName(req.getParameter("lastname"));
        builder.nickname(req.getParameter("nickname"));
        builder.dob(LocalDate.parse(req.getParameter("dob")));
        builder.language("ru");
        builder.id(2);
        builder.pageType(PageTypes.PERSON);
        Page p = builder.build();
        log.error(p.toString());
        pageDao.put(p);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/page");
        requestDispatcher.forward(req, resp);
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        DaoProvider provider = (DaoProvider) servletContext.getAttribute(Initer.DAO_PROVIDER);
        pageDao = provider.getPageDao();
    }
}
