package servlets.tags;

import Entities.Page;
import Entities.PageTypes;
import lombok.SneakyThrows;
import servlets.filters.AuthFilter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class SubscribeButton extends TagSupport {

    private Page myPage;
    private Page page;

    @Override
    public int doStartTag() throws JspException{
        myPage = (Page)pageContext.getSession().getAttribute(AuthFilter.AUTH);
        page = (Page)pageContext.getSession().getAttribute("page");


        if (myPage.getId() == page.getId()){
            return noButton();
        }
        else if (page.getPageType() == PageTypes.PERSON){
            return subscribeButton();
        }
        else {
            return addToTeam();
        }
    }

    private int noButton(){
        return SKIP_BODY;
    }

    @SneakyThrows
    private int subscribeButton() {
        if (myPage.getSubscribeList().contains(page.getId()))
            return unSubscribeButton();

        StringBuffer buffer = new StringBuffer();
        buffer.append("<form action=\"subscribe?source=").append(page.getId())
                .append("\" method=\"post\"><button type=\"submit\">").append("Подписаться").append("</button>");

        pageContext.getOut().print(buffer.toString());

        return SKIP_BODY;
    }

    @SneakyThrows
    private int unSubscribeButton(){
        if (!myPage.getSubscribeList().contains(page.getId()))
            return subscribeButton();

        StringBuffer buffer = new StringBuffer();
        buffer.append("<form action=\"unsubscribe?source=").append(page.getId())
                .append("\" method=\"post\"><button type=\"submit\">").append("Вы подписаны").append("</button>");

        pageContext.getOut().print(buffer.toString());

        return SKIP_BODY;
    }

    @SneakyThrows
    private int addToTeam(){
        StringBuffer buffer = new StringBuffer();
        buffer.append("<a href=\"subscribe?source=").append(page.getId())
                .append("\">").append("В команду").append("</a>");

        pageContext.getOut().print(buffer.toString());

        return SKIP_BODY;
    }
}
