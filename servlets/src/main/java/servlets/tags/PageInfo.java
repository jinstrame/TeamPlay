package servlets.tags;


import Entities.Page;
import lombok.SneakyThrows;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.time.LocalDate;

public class PageInfo extends TagSupport {
    @Override
    @SneakyThrows
    public int doStartTag() throws JspException {
        Page page = (Page)pageContext.getSession().getAttribute("page");

        @SuppressWarnings("StringBufferReplaceableByString") StringBuilder stringBuffer = new StringBuilder("");
        stringBuffer.append("<h3>")
                .append(page.getFirstName()).append(" \"")
                .append(page.getNickname()).append("\" ")
                .append(page.getSecondName()).append("</h3>");
        stringBuffer.append("<br/>\n");
        stringBuffer.append("<table class=\"info_table\"><tr>\n");
        stringBuffer.append("<td>Дата рождения</td>\n");
        stringBuffer.append("<td>")
                .append(page.getDob()).append("</td>\n<td>")
                .append(calcAge(page.getDob())).append(" Лет</td>\n</tr>");

        stringBuffer.append("<tr><td><br/></td></tr>\n" +
                "                    <tr>\n" +
                "                        <td>Игры</td>\n" +
                "                        <td>Dota2</td>\n" +
                "                        <td>1 mmr</td>\n" +
                "                    </tr>\n" +
                "                    <tr>\n" +
                "                        <td></td>\n" +
                "                        <td>Counter Strike GO</td>\n" +
                "                        <td>Silver</td>\n" +
                "                    </tr>\n" +
                "                    <tr><td><br/></td></tr>\n" +
                "                    <tr>\n" +
                "                        <td>Команды</td>\n" +
                "                        <td>GabeN Team</td>\n" +
                "                        <td>Feader</td>\n" +
                "                    </tr>\n" +
                "                    <tr><td><br/></td></tr>\n" +
                "                    <tr>\n" +
                "                        <td>Контакты</td>\n" +
                "                        <td colspan=\"2\">\n" +
                "                            <a href=\"https://vk.com/id1\">vk.com/id9582678</a>\n" +
                "                        </td>\n" +
                "                    </tr>\n" +
                "                </table>");

        pageContext.getOut().print(stringBuffer.toString());

        return SKIP_BODY;
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
