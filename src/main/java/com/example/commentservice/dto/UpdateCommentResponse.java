package com.example.commentservice.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateCommentResponse {

    private Long commentId;
    private String content;
}
