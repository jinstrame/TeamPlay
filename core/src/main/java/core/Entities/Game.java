package core.Entities;


import lombok.*;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Getter
@ToString
public class Game {
    private int page;
    private String game;
    private String account;
    private String rank;
}
