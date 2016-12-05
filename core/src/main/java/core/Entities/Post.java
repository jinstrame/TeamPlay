package core.Entities;


import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Builder
@Getter
public class Post implements Comparable {
    private int id;
    private int pageId;
    private String nickname;
    private String firstName;
    private String lastName;
    private Instant time;
    private String content;
    private int nextId;

    @Override
    public int compareTo(Object o) {
        Post that = (Post) o;
        return this.time.compareTo(that.time);
    }
}
