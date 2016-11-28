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

        String s = "<form action=\"subscribe?source=" + page.getId() +
                "\" method=\"post\"><button type=\"submit\">" + "Подписаться" + "</button>";

        pageContext.getOut().print(s);

        return SKIP_BODY;
    }

    @SneakyThrows
    private int unSubscribeButton(){
        if (!myPage.getSubscribeList().contains(page.getId()))
            return subscribeButton();

        String buffer = "<form action=\"unsubscribe?source=" + page.getId() +
                "\" method=\"post\"><button type=\"submit\">" + "Вы подписаны" + "</button>";

        pageContext.getOut().print(buffer);

        return SKIP_BODY;
    }

    @SneakyThrows
    private int addToTeam(){
        String buffer = "<a href=\"subscribe?source=" + page.getId() +
                "\">" + "В команду" + "</a>";

        pageContext.getOut().print(buffer);

        return SKIP_BODY;
    }
}
