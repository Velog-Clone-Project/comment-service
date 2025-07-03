package com.example.commentservice.controller;

import com.example.commentservice.dto.*;
import com.example.commentservice.service.CommentService;
import com.example.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<ApiResponse<CreateCommentResponse>> createComment(
            @RequestHeader("X-User-Id") String userId,
            @Valid @RequestBody CreateCommentRequest request) {

        CreateCommentResponse response = commentService.createComment(request, userId);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Comment created successfully", response));

    }

    @PatchMapping("/{commentId}")
    public ResponseEntity<ApiResponse<UpdateCommentResponse>> updateComment(
            @PathVariable Long commentId,
            @RequestHeader("X-User-Id") String userId,
            @Valid @RequestBody UpdateCommentRequest request) {

        UpdateCommentResponse response = commentService.updateComment(request, userId, commentId);

        return ResponseEntity.ok(new ApiResponse<>("Comment updated successfully", response));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<DeleteCommentResponse>> deleteComment(
            @PathVariable Long commentId,
            @RequestHeader("X-User-Id") String userId) {

        DeleteCommentResponse response = commentService.deleteComment(commentId, userId);
        return ResponseEntity.ok(new ApiResponse<>("Comment deleted successfully", response));
    }
}
