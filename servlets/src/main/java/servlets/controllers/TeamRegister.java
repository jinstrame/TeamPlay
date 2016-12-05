package servlets.controllers;

import core.Entities.Account;
import core.Entities.Page;
import core.Entities.PageTypes;
import jdbc.DaoProvider;
import jdbc.dao.core.AccountDao;
import lombok.SneakyThrows;
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
import java.util.TimeZone;

@Log4j2
@WebServlet("/auth/team_register")

public class TeamRegister extends HttpServlet {

    private AccountDao accountDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("/jsppages/register/team_register.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    @SneakyThrows
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        val pageBuilder = Page.builder();


        pageBuilder.firstName(req.getParameter("firstname"))
                .lastName(req.getParameter("lastname"))
                .language(req.getParameter("language"))
                .timeZone(TimeZone.getTimeZone(req.getParameter("timezone")))
                .dob(LocalDate.now())
                .pageType(PageTypes.TEAM);

        Page p = pageBuilder.build();

        Account account = accountDao.register(p, req.getParameter("reg_email"), req.getParameter("reg_password"));
        resp.sendRedirect("/page?id=" + account.getPageId());
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        DaoProvider provider = (DaoProvider) servletContext.getAttribute(Initer.DAO_PROVIDER);
        accountDao = provider.getAccountDao();
    }
}
