package com.example.commentservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateCommentRequest {

    @NotNull(message = "postId is required")
    private Long postId;
    private Long parentId;

    @NotBlank(message = "Content must not be empty")
    @Size(max = 300, message = "Content must not exceed 300 characters")
    private String content;
}
