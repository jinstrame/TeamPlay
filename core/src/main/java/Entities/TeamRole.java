package Entities;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class TeamRole {
    int player;
    int team;
    String role;
    String firstName;
    String lastName;
    String nickName;
    String teamName;
    String teamGame;
}