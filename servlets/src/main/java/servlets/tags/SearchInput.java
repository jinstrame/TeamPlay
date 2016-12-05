package servlets.tags;

import langSupport.LocaleKeyWords;
import lombok.SneakyThrows;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import static servlets.listeners.DefaultSessionParams.LOCALE;

/**
 * Places serch field with localized placeholder
 */
public class SearchInput extends TagSupport {
    @SneakyThrows
    @Override
    public int doStartTag() throws JspException {
        LocaleKeyWords lkw = (LocaleKeyWords) pageContext.getSession().getAttribute(LOCALE);
        pageContext.getOut().print("<input type=\"search\" name=\"req\" placeholder=\"" +
                lkw.get(LocaleKeyWords.SEARCH) +
                "\">");

        return SKIP_BODY;
    }
}
