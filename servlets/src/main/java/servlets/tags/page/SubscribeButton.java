package servlets.tags.page;

import core.Entities.Page;
import core.Entities.PageTypes;
import langSupport.LocaleKeyWords;
import lombok.SneakyThrows;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import static servlets.listeners.DefaultSessionParams.*;


/**
 * Places subscribe button, if it should be
 */
public class SubscribeButton extends TagSupport {

    private Page myPage;
    private Page page;
    private LocaleKeyWords lkw;

    @Override
    public int doStartTag() throws JspException{
        myPage = (Page)pageContext.getSession().getAttribute(AUTH);
        page = (Page)pageContext.getSession().getAttribute(PAGE);
        lkw = (LocaleKeyWords) pageContext.getSession().getAttribute(LOCALE);

        if (myPage.getPageType() == PageTypes.TEAM)
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
                "\" method=\"post\"><input class=\"hvr-fade-back header_link\" type=\"submit\" value=\"" +
                lkw.get(LocaleKeyWords.SUBSCRIBE) +
                "\"/>";

        pageContext.getOut().print(s);

        return SKIP_BODY;
    }

    @SneakyThrows
    private int unSubscribeButton(){
        if (!myPage.getSubscribeList().contains(page.getId()))
            return subscribeButton();

        String buffer = "<form action=\"unsubscribe?source=" + page.getId() +
                "\" method=\"post\"><input class=\"hvr-fade-back header_link\" type=\"submit\" value=\"" +
                lkw.get(LocaleKeyWords.UNSUBSCRIBE) +
                "\">";

        pageContext.getOut().print(buffer);

        return SKIP_BODY;
    }
}
