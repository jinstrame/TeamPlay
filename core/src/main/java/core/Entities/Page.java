package core.Entities;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.TimeZone;


@Builder
@Setter
@Getter
public class Page {

    private int id;
    private PageTypes pageType;
    private String nickname;
    private String firstName;
    private String lastName;
    private LocalDate dob;
    private String language;
    private int lastPostId;
    private String about;
    private TimeZone timeZone;
    private List<TeamRole> team_list;
    private List<Game> gameIds;
    private List<Integer> subscribeList;
    private String avaId = "";
}
