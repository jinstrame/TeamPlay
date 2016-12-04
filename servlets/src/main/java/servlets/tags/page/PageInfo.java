package servlets.tags.page;


import core.Entities.Game;
import core.Entities.Page;
import langSupport.LocaleKeyWords;
import lombok.SneakyThrows;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.time.LocalDate;
import java.util.List;

public class PageInfo extends TagSupport {
    @Override
    @SneakyThrows
    public int doStartTag() throws JspException {
        Page page = (Page)pageContext.getSession().getAttribute("page");
        LocaleKeyWords lkw = (LocaleKeyWords)pageContext.getSession().getAttribute("locale") ;

        @SuppressWarnings("StringBufferReplaceableByString") StringBuilder sb = new StringBuilder("");
        sb.append("<h3>")
                .append(page.getFirstName()).append(" \"")
                .append(page.getNickname()).append("\" ")
                .append(page.getSecondName()).append("</h3>");
        sb.append("<br/>\n");
        sb.append("<table class=\"info_table\"><tr>\n");
        sb.append("<td>").append(lkw.get(LocaleKeyWords.DATE_OF_BIRTH)).append("</td>\n");
        sb.append("<td>")
                .append(page.getDob()).append("</td>\n<td>")
                .append(calcAge(page.getDob())).append(" ").append(lkw.get(LocaleKeyWords.YEARS)).append("</td>\n</tr>");

        sb.append("<tr><td><br/></td></tr>\n" +
                "                       <tr>\n" +
                "                        <td>").append(lkw.get(LocaleKeyWords.GAMES)).append("</td>\n\n" +
                "                        <td>");

        sb.append("<a href=\"addgame?page=").append(page.getId())
                .append("\" >").append("Добавить игру").append("</a>");
        sb.append("</td>\n\n" +
                "                        <td></td>\n\n" +
                "                    </tr>\n");

        List<Game> games = page.getGameIds();
        for (Game game: games) {
            StringBuilder account = new StringBuilder();
            if (game.getAccount().equals("")){
                account.append(game.getGame());
            }
            else {
                account.append("<a href=\"").append(game.getAccount())
                    .append("\" >").append(game.getGame()).append("</a>");
            }
            sb.append("<tr>\n" +
                    "<td></td>\n" +
                    "<td>")
                    .append(account).append("</td>\n").append("<td>")
                    .append(game.getRank()).append("</td>\n").append(" </tr>\n");
        }

        sb.append("</table>");

        pageContext.getOut().print(sb.toString());

        return EVAL_BODY_INCLUDE;
    }

    private int calcAge(LocalDate t){
        int now = LocalDate.now().getYear();
        int from = t.getYear();
        return now - from;
    }


    /*
    <h3>Александр "USL" Малько</h3>
                <br/>
                <table class="info_table">
                    <tr>
                        <td>Дата рождения</td>
                        <td>Выход Dota2</td>
                        <td>6 лет</td>
                    </tr>
                    <tr><td><br/></td></tr>
                    <tr>
                        <td>Игры</td>
                        <td>Dota2</td>
                        <td>1 mmr</td>
                    </tr>
                    <tr>
                        <td></td>
                        <td>Counter Strike GO</td>
                        <td>Silver</td>
                    </tr>
                    <tr><td><br/></td></tr>
                    <tr>
                        <td>Команды</td>
                        <td>GabeN Team</td>
                        <td>Feader</td>
                    </tr>
                    <tr><td><br/></td></tr>
                    <tr>
                        <td>Контакты</td>
                        <td colspan="2">
                            <a href="https://vk.com/id1">vk.com/id9582678</a>
                        </td>
                    </tr>
                </table>
     */
}
