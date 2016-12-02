package servlets.tags.page;

import Entities.Page;
import Entities.PageTypes;
import lombok.SneakyThrows;
import servlets.filters.AuthFilter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class SubscribeButton extends TagSupport {

    private Page myPage;
    private Page page;

    @Override
    public int doStartTag() throws JspException{
        myPage = (Page)pageContext.getSession().getAttribute(AuthFilter.AUTH);
        page = (Page)pageContext.getSession().getAttribute("page");

        if (myPage.getPageType() == PageTypes.TEAM)
            return noButton();
        if (page.getPageType() == PageTypes.TEAM)
            return noButton();
        if (myPage.getId() == page.getId()){
            return noButton();
        }
        else {
            return subscribeButton();
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
}
