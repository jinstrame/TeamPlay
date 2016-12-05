package servlets.tags.team;

import core.Entities.TeamRole;
import langSupport.LocaleKeyWords;
import lombok.SneakyThrows;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.List;

import static servlets.listeners.DefaultSessionParams.LOCALE;
import static servlets.listeners.DefaultSessionParams.PLAYERS_LIST;

/**
 * Provides list of players in team
 */

public class PlayersList extends TagSupport {
    @SneakyThrows
    @Override
    public int doStartTag() throws JspException {
        @SuppressWarnings("unchecked") List<TeamRole> team = (List<TeamRole>)pageContext.getSession().getAttribute(PLAYERS_LIST);
        pageContext.getSession().removeAttribute(PLAYERS_LIST);

        LocaleKeyWords lkw = (LocaleKeyWords) pageContext.getSession().getAttribute(LOCALE);


        StringBuilder sb = new StringBuilder();

        for (TeamRole r: team){
            sb.append("<div class=\"post_block\">\n")
                    .append("<h4>").append("<a href=\"/page?id=").append(r.getPlayer()).append("\">")
                    .append(r.getFirstName()).append(" \"")
                    .append(r.getNickName()).append("\" ")
                    .append(r.getLastName()).append(" ")
                    .append(" - ").append(r.getRole())
                    .append("</a>")
                    .append("</h4>").append("</div>");
        }

        if (team.size() == 0) {
            sb.append("<div class=\"post_block\">\n")
                    .append("<h4>")
                    .append(lkw.get(LocaleKeyWords.NO_PLAYERS))
                    .append("</h4>").append("</div>");
        }

        pageContext.getOut().print(sb.toString());
        return SKIP_BODY;
    }
}