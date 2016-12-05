package langSupport;

import lombok.Getter;

import java.util.Locale;
import java.util.ResourceBundle;


/**
 * Provides key words depending ob current locale/
 */
public class LocaleKeyWords {
    public static final String RU_KEYS = "ry_keys";
    public static final String EN_KEYS = "en_keys";
    public static final String DEF_KEYS = "def_keys";

    public static final Locale locale_ru = new Locale("ru");
    public static final Locale locale_en = new Locale("en");

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
    public static final String GAME = "game";
    public static final String TEAMS = "teams";
    public static final String TEAM = "team";
    public static final String YEAR = "year";
    public static final String YEARS = "years";
    public static final String COMMENTS = "comments";
    public static final String REMOVE = "remove";
    public static final String ADD_GAME = "add_game";
    public static final String NO_SUBSCRIBTIONS = "no_subscribtions";
    public static final String NOT_IN_TEAM = "not_in_team";
    public static final String SUBSCRIBE = "subscribe";
    public static final String UNSUBSCRIBE = "unsubscribe";
    public static final String PLAYERS = "players";
    public static final String NO_PLAYERS = "no_players";
    public static final String ROLE = "role";
    public static final String ADD_TO_TEAM = "add_to_team";
    public static final String LOAD = "load";
    public static final String PLACE_CONTENT = "place_content";
    public static final String EMAIL = "email";
    public static final String PASSWORD = "password";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String NICKNAME = "nickname";
    public static final String TIME_ZONE = "time_zone";
    public static final String LANGUAGE_WORD = "lang_word";
    public static final String SEND = "send";
    public static final String REGISTER = "register";
    public static final String TEAM_REGISTER = "team_register";


    @Getter
    private Locale locale;
    private ResourceBundle rb;

    public LocaleKeyWords(Locale locale){
        rb = ResourceBundle.getBundle("KeyWords", locale);
        this.locale = locale;
    }

    public void changeLocale(Locale locale){
        rb = ResourceBundle.getBundle("KeyWords", locale);
        this.locale = locale;
    }

    public String get(String key){
        return rb.getString(key);
    }
}
