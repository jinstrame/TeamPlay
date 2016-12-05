package core.Entities;


import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Game {
    private int page;
    private String game;
    private String account;
    private String rank;
}
