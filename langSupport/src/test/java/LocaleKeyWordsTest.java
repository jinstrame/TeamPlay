import langSupport.LocaleKeyWords;
import org.junit.Test;

import java.util.Locale;

public class LocaleKeyWordsTest {
    @Test
    public void get() throws Exception {
        LocaleKeyWords kw = new LocaleKeyWords(new Locale("ru"));
        System.out.println(kw.get(LocaleKeyWords.TEAMS));

        kw.changeLocale(LocaleKeyWords.locale_en);
        System.out.println(kw.get(LocaleKeyWords.TEAMS));
    }

}