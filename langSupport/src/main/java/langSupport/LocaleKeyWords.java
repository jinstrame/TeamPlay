package langSupport;

import java.util.Locale;
import java.util.ResourceBundle;


@SuppressWarnings("unused")
public class LocaleKeyWords {
    public static final String RU_KEYS = "ry_keys";
    public static final String EN_KEYS = "en_keys";
    public static final String DEF_KEYS = "def_keys";

    public static final Locale locale_ru = new Locale("ru", "RU");
    public static final Locale locale_en = new Locale("en", "US");

    public static final String LANGUAGE_CODE = "language_code";
    public static final String LANGUAGE = "language";
    public static final String PROFILE = "profile";
    public static final String FEED = "feed";
    public static final String SUBSCRIPTIONS = "subscriptions";
    public static final String SEARCH = "search";
    public static final String SETTINGS = "settings";
    public static final String QUIT = "quit";
    public static final String DATE_OF_BIRTH = "date_of_birth";
    public static final String GAMES = "games";
    public static final String TEAMS = "teams";
    public static final String YEAR = "year";
    public static final String YEARS = "years";
    public static final String COMMENTS = "comments";
    public static final String REMOVE = "remove";



    private ResourceBundle rb;

    public LocaleKeyWords(Locale locale){
        rb = ResourceBundle.getBundle("KeyWords", locale);
    }

    public void changeLocale(Locale locale){
        rb = ResourceBundle.getBundle("KeyWords", locale);
    }

    public String get(String key){
        return rb.getString(key);
    }
}
