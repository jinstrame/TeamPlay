package servlets.controllers;


import core.Entities.Page;
import core.Entities.PageTypes;
import jdbc.DaoProvider;
import jdbc.dao.core.PageDao;
import jdbc.dao.core.PostDao;
import langSupport.LocaleKeyWords;
import servlets.listeners.DefaultSessionParams;
import servlets.listeners.Initer;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Locale;
import java.util.TimeZone;

import static servlets.listeners.DefaultSessionParams.AUTH;
import static servlets.listeners.DefaultSessionParams.LOCALE;

@WebServlet("/settings")
public class Settings extends HttpServlet {
    private PageDao pageDao;
    private PostDao postDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Page me = (Page)req.getSession().getAttribute(AUTH);
        if (me.getPageType() == PageTypes.PERSON)
            req.getRequestDispatcher("jsppages/settings/person_settings.jsp").forward(req, resp);
        else  req.getRequestDispatcher("jsppages/settings/team_settings.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Page me = (Page)req.getSession().getAttribute(AUTH);

        String attr = req.getParameter("attr");

        if (attr.contains("upd_firstname"))
            pageDao.updateName(me, req.getParameter("upd_firstname"), null, null, null);
        else if (attr.contains("upd_lastname"))
            pageDao.updateName(me,null, req.getParameter("upd_lastname"),null, null);
        else if (attr.contains("upd_nickname"))
            pageDao.updateName(me,null, null, req.getParameter("upd_nickname"), null);
        else if (attr.contains("upd_dob"))
            pageDao.updateContext(me, LocalDate.parse(req.getParameter("upd_dob")), null, null);
        else if (attr.contains("upd_language")){
            pageDao.updateContext(me, null, req.getParameter("upd_language"), null);
            LocaleKeyWords lkw = new LocaleKeyWords(new Locale(me.getLanguage()));
            req.getSession().setAttribute(LOCALE, lkw);
        }
        else if (attr.contains("upd_timezone")) {
            pageDao.updateContext(me, null, null, TimeZone.getTimeZone(req.getParameter("upd_timezone")));
            req.getSession().setAttribute(DefaultSessionParams.TIMEZONE, me.getTimeZone());
        }
        else if (attr.contains("upd_about"))
            pageDao.updateName(me,null, null,null, req.getParameter("upd_about"));

        resp.sendRedirect("/settings");
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        ServletContext servletContext = config.getServletContext();
        DaoProvider provider = (DaoProvider) servletContext.getAttribute(Initer.DAO_PROVIDER);
        pageDao = provider.getPageDao();
        postDao = provider.getPostDao();
    }

}
