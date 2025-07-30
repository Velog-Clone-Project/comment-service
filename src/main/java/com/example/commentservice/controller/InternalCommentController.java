package com.example.commentservice.controller;

import com.example.commentservice.dto.CommentDto;
import com.example.commentservice.dto.UpdateAuthorInfoRequest;
import com.example.commentservice.service.InternalCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@RequiredArgsConstructor
@RequestMapping("/internal/comments")
public class InternalCommentController {

    private final InternalCommentService internalCommentService;

    @PutMapping("/author-info")
    public ResponseEntity<Void> updateAuthorInfo(@RequestBody UpdateAuthorInfoRequest request) {

        internalCommentService.updateAuthorInfo(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/by-user")
    public ResponseEntity<Void> deleteAllCommentsByUser(@RequestParam String userId) {

       internalCommentService.deleteAllPostsByUserId(userId);
       return ResponseEntity.ok().build();
    }

    @DeleteMapping("/by-post")
    public ResponseEntity<Void> deleteAllCommentsByPost(@RequestParam Long postId) {

        internalCommentService.deleteAllPostsByPostId(postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@RequestParam Long postId) {

        List<CommentDto> comments = internalCommentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }
}
