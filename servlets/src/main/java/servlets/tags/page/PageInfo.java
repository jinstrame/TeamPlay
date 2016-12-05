package servlets.tags.page;


import core.DateTimeUtil;
import core.Entities.Game;
import core.Entities.Page;
import core.Entities.PageTypes;
import langSupport.LocaleKeyWords;
import lombok.SneakyThrows;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import static servlets.listeners.DefaultSessionParams.*;


/**
 * Places Person name, games and other info
 */
public class PageInfo extends TagSupport {
    @Override
    @SneakyThrows
    public int doStartTag() throws JspException {
        Page page = (Page) pageContext.getSession().getAttribute(PAGE);
        Page me = (Page) pageContext.getSession().getAttribute(AUTH);
        if (page.getPageType() == PageTypes.TEAM)
            return SKIP_BODY;
        LocaleKeyWords lkw = (LocaleKeyWords) pageContext.getSession().getAttribute(LOCALE);

        StringBuilder sb = new StringBuilder("");
        sb.append("<h3>")
                .append(page.getFirstName()).append(" \"")
                .append(page.getNickname()).append("\" ")
                .append(page.getLastName()).append("</h3>");
        sb.append("<br/>\n");
        sb.append("<table class=\"info_table\"><tr>\n");
        sb.append("<td>").append(lkw.get(LocaleKeyWords.DATE_OF_BIRTH)).append("</td>\n");
        sb.append("<td>")
                .append(DateTimeUtil.getDateString(page.getDob(), me.getTimeZone(), new Locale(me.getLanguage())))
                .append("</td>\n<td>")
                .append(calcAge(page.getDob(), page.getTimeZone())).append(" ")
                .append(lkw.get(LocaleKeyWords.YEARS)).append("</td>\n</tr>");

        sb.append("<tr><td><br/></td></tr>\n<tr>\n<td>")
                .append(lkw.get(LocaleKeyWords.GAMES))
                .append("</td>\n")
                .append("<td>");
        if (page.getId() == me.getId())
            sb.append("<a href=\"addgame?page=").append(page.getId())
                    .append("\" >")
                    .append(lkw.get(LocaleKeyWords.ADD_GAME)).append("</a>");
        sb.append("</td>\n<td></td>\n</tr>\n");

        List<Game> games = page.getGameIds();
        for (Game game : games) {
            StringBuilder account = new StringBuilder();
            if (game.getAccount().equals("")) {
                account.append(game.getGame());
            } else {
                account.append("<a href=\"").append(game.getAccount())
                        .append("\" >").append(game.getGame()).append("</a>");
            }
            sb.append("<tr>\n<td></td>\n<td>")
                    .append(account)
                    .append("</td>\n").append("<td>")
                    .append(game.getRank())
                    .append("</td>\n").append(" </tr>\n");
        }
        sb.append("</table>");

        pageContext.getOut().print(sb.toString());

        return EVAL_BODY_INCLUDE;
    }

    private int calcAge(LocalDate t, TimeZone tz) {
        int now = LocalDate.now(tz.toZoneId()).getYear();
        int from = t.getYear();
        return now - from;
    }
}