package servlets.filters;


import core.Entities.Page;
import jdbc.DaoProvider;
import jdbc.dao.core.AccountDao;
import langSupport.LocaleKeyWords;
import lombok.extern.log4j.Log4j2;
import security.Hash;
import servlets.listeners.DefaultSessionParams;
import servlets.listeners.Initer;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;

/**
 * Provides basic authentication using login and password.
 */

@Log4j2
@WebFilter("/*")
public class AuthFilter extends HttpFilter {

    public static final String AUTH = DefaultSessionParams.AUTH;

    private AccountDao accountDao;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        ServletContext servletContext = filterConfig.getServletContext();
        DaoProvider provider = (DaoProvider) servletContext.getAttribute(Initer.DAO_PROVIDER);
        accountDao = provider.getAccountDao();
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpSession session = request.getSession(true);

        request.setCharacterEncoding("UTF-8");

        if (session.getAttribute(AUTH) != null) {
            chain.doFilter(request, response);
            return;
        }

        Map<String, String[]> parameterMap = request.getParameterMap();
        if (parameterMap.containsKey("password") && parameterMap.containsKey("email")) {
            session.setAttribute("locale", new LocaleKeyWords(Locale.getDefault()));


            Optional<Page> authorize = authorize(parameterMap);
            if (authorize.isPresent()) {

                Page auth = authorize.get();

                session.setAttribute(AUTH, auth);
                session.setAttribute(DefaultSessionParams.LOCALE, new LocaleKeyWords(new Locale(auth.getLanguage())));
                session.setAttribute(DefaultSessionParams.TIMEZONE, auth.getTimeZone());

                response.sendRedirect("/page");
            } else {
                request.getRequestDispatcher("/error.html").forward(request, response);
            }

        } else {
            if (request.getRequestURI().startsWith("/auth")
                    ||request.getRequestURI().startsWith("/styles")) {
                chain.doFilter(request, response);
                return;
            }
            if (request.getRequestURI().contains("loadposts"))
                return;
            request.getRequestDispatcher("/auth/login").forward(request, response);
        }
    }

    private Optional<Page> authorize(Map<String, String[]> parameterMap) {
        String login = parameterMap.get("email")[0];
        String password = parameterMap.get("password")[0];
        String hash = new Hash(password).toHexString();

        return accountDao.isAutorized(login, hash);
    }




}
