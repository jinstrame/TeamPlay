package servlets.tags;

import langSupport.LocaleKeyWords;
import org.junit.Test;

import java.util.Locale;

import static org.junit.Assert.*;


public class LocaleTagTest {
    @Test
    public void Test(){
        LocaleKeyWords lkw = new LocaleKeyWords(new Locale("ru"));
        System.out.println(lkw.get(LocaleKeyWords.SETTINGS));
    }

}