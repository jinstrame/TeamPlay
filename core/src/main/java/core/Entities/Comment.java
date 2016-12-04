package core.Entities;


import lombok.*;

import java.time.Instant;


@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
@Data
public class Comment {
    int postId;
    int pageId;
    int commentator_id;
    Instant time;
    String content;
}
