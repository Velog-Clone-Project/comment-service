package com.example.commentservice.service;

import com.example.commentservice.domain.CommentEntity;
import com.example.commentservice.dto.*;
import com.example.commentservice.exception.CommentAccessDeniedException;
import com.example.commentservice.exception.CommentNotFoundException;
import com.example.commentservice.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public CreateCommentResponse createComment(CreateCommentRequest request, String userId){

        // parentId 검증
        // TODO: 예외처리 리팩토링
        if (request.getParentId() != null) {
            CommentEntity parentComment = commentRepository.findById(request.getParentId())
                    .orElseThrow(() -> new IllegalArgumentException("Parent comment not found"));

            if (!parentComment.getPostId().equals(request.getPostId())) {
                throw new IllegalArgumentException("Parent comment does not belong to the same post");
            }
        }
        // TODO: postId 자체가 유효한지도 post-service 연동 후 캐시/검증 필요

        // TODO: 유저 정보(userId)로 기반으로 user-service에서
        //      authorName, authorProfileImageUrl을 조회하는 로직 추가 필요(event-driven)

        CommentEntity comment = CommentEntity.builder()
                .postId(request.getPostId())
                .parentId(request.getParentId())
                .userId(userId)
                .content(request.getContent())
                .authorName("jihyun") // mock
                .authorProfileImageUrl("https://example.com/profile/jihyun.jpg") // mock
                .createdAt(LocalDateTime.now())
                .build();

        CommentEntity saved = commentRepository.save(comment);

        return CreateCommentResponse.builder()
                .commentId(saved.getId())
                .postId(saved.getPostId())
                .content(saved.getContent())
                .authorName(saved.getAuthorName())
                .authorProfileImageUrl(saved.getAuthorProfileImageUrl())
                .createdAt(saved.getCreatedAt().toString())
                .build();
    }

    public UpdateCommentResponse updateComment(UpdateCommentRequest request, String userId, Long commentId) {

        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        if (!comment.getUserId().equals(userId)) {
            throw new CommentAccessDeniedException();
        }

        // JPA의 dirty checking
        // 기존 엔티티의 상태가 변경되었음을 JPA가 갑지하고, 트랜잭션 커밋 시 자동으로 업데이트 쿼리 실행
        // 1. findById()로 조회한 comment는 영속 상태(persistent state)로 관리됨
        // 2. setContent() 호출로 필드 값이 변경됨
        // 3. 트랜잭션 커밋 시점(Spring @Transactional 안에서) JPA는 변경된 필드만 UPDATE
        // 4. save() 호출은 필요 없음
        comment.setContent(request.getContent());
        commentRepository.save(comment);

        return UpdateCommentResponse.builder()
                .commentId(comment.getId())
                .content(comment.getContent())
                .build();
    }


    public DeleteCommentResponse deleteComment(Long commentId, String userId) {
        CommentEntity comment = commentRepository.findById(commentId)
                .orElseThrow(CommentNotFoundException::new);

        if (!comment.getUserId().equals(userId)) {
            throw new CommentAccessDeniedException();
        }

        commentRepository.delete(comment);

        return DeleteCommentResponse.builder()
                .commentId(comment.getId())
                .build();
    }
}
