package servlets.listeners;

import langSupport.LocaleKeyWords;
import lombok.extern.log4j.Log4j2;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Locale;
import java.util.TimeZone;


/**
 * Setup default parameters for session and provides constants.
 */

@Log4j2
@WebListener
public class DefaultSessionParams implements HttpSessionListener{

    public static final String LOCALE = "locale";
    public static final String TIMEZONE = "timezone";

    public static final String AUTH = "auth";
    public static final String PAGE = "page";
    public static final String POST = "post";
    public static final String COMMENTS = "comments";
    public static final String POSTS = "posts";
    public static final String AGREGATOR = "agregator";
    public static final String FEED_AGREGATOR = "feed_agregator";
    public static final String AGREGATOR_SOURCES = "agregator_sources";
    public static final String PLAYERS_LIST = "players_list";
    public static final String SUBSCRIBERS_LIST = "sub_list";
    public static final String TEAMMATES_LIST = "mates_list";
    public static final String TEAMS_LIST = "teams_list";
    public static final String SEARCH = "search";


    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        session.setAttribute(LOCALE, new LocaleKeyWords(new Locale("en")));
        session.setAttribute(TIMEZONE, TimeZone.getTimeZone("GMT"));
    }
}
