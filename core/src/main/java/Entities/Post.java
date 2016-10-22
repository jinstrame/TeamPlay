package Entities;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

import java.time.Instant;


@Value
@Builder
@EqualsAndHashCode(exclude = {"nickname", "firstName", "secondName", "time", "content"})
public class Post implements Comparable {
    private int id;
    private int pageId;
    private String nickname;
    private String firstName;
    private String secondName;
    private Instant time;
    private String content;
    private int nextId;

    @Override
    public int compareTo(Object o) {
        Post that = (Post) o;
        return this.time.compareTo(that.time);
    }
}
