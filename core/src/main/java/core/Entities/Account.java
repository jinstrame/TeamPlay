package core.Entities;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Account {
    private String login;
    private String h_password;
    private int token;
    private int pageId;
}
