package Entities;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class Account {
    @Getter
    private String login;

    private byte[] password;
    @Getter
    private int token;
    @Getter
    private int pageId;

    private Page getAssociatedPage(){
        return null;
    }
}
