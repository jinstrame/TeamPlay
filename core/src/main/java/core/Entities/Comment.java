package core.Entities;


import lombok.Builder;
import lombok.Data;

import java.time.Instant;


@Builder
@Data
public class Comment {
    int postId;
    int pageId;
    int commentator_id;
    String firstName;
    String lastName;
    String nickName;
    Instant time;
    String content;
}
