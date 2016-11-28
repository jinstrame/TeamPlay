package servlets.tags;

import langSupport.LocaleKeyWords;
import lombok.Setter;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;


@Log4j2
public class LocaleTag extends TagSupport {

    @Setter
    private String key;

    @SneakyThrows
    public int doStartTag() throws JspException {
        LocaleKeyWords lkw = (LocaleKeyWords) pageContext.getSession().getAttribute("locale");
        pageContext.getOut().print(lkw.get(key));
        return SKIP_BODY;
    }
}
