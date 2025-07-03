package com.example.commentservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateCommentResponse {

    private Long commentId;
    private Long postId;
    private String content;
    private String authorName;
    private String authorProfileImageUrl;
    private String createdAt;

}
