package core.Entities;

import lombok.*;

import java.time.LocalDate;
import java.util.List;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@EqualsAndHashCode
        (exclude = {"pageType", "nickname", "firstName", "secondName",
                "dob", "language", "mainPostId", "lastPostId", "team_list", "gameIds", "subscribeList"})
public class Page {

    private int id;
    private PageTypes pageType;
    private String nickname;
    private String firstName;
    private String secondName;
    private LocalDate dob;
    private String language;
    private int mainPostId;
    private int lastPostId;
    private List<TeamRole> team_list;
    private List<Game> gameIds;
    private List<Integer> subscribeList;
    private String avaId = "";
}
