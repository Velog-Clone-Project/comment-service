package com.example.commentservice.controller;

import com.example.commentservice.dto.CommentDto;
import com.example.commentservice.event.UpdateAuthorInfoEvent;
import com.example.commentservice.service.InternalCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/internal/comments")
public class InternalCommentController {

    private final InternalCommentService internalCommentService;

    @PutMapping("/author-info")
    public ResponseEntity<Void> updateAuthorInfo(@RequestBody UpdateAuthorInfoEvent request) {

        internalCommentService.updateAuthorInfo(request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/by-user")
    public ResponseEntity<Void> deleteAllCommentsByUser(@RequestParam String userId) {

       internalCommentService.deleteAllCommentsByUserId(userId);
       return ResponseEntity.ok().build();
    }

    @DeleteMapping("/by-post")
    public ResponseEntity<Void> deleteAllCommentsByPost(@RequestParam Long postId) {

        internalCommentService.deleteAllCommentsByPostId(postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@RequestParam Long postId) {

        List<CommentDto> comments = internalCommentService.getCommentsByPostId(postId);
        return ResponseEntity.ok(comments);
    }
}
