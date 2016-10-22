package Entities;

import lombok.*;

import java.time.LocalDate;
import java.util.List;


@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
        (exclude = {"pageType", "nickname", "firstName", "secondName",
                "dob", "language", "mainPostId", "lastPostId", "teamIds", "gameIds"})
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
    private List<Integer> teamIds;
    private List<Integer> gameIds;
}
