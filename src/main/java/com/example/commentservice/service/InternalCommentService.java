package com.example.commentservice.service;

import com.example.commentservice.domain.CommentEntity;
import com.example.commentservice.dto.CommentDto;
import com.example.commentservice.event.UpdateAuthorInfoEvent;
import com.example.commentservice.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class InternalCommentService {

    private final CommentRepository commentRepository;


    // TODO: 이거 왜 @Transactional이 필요한지 확인 필요
    @Transactional
    public void updateAuthorInfo(UpdateAuthorInfoEvent request) {

        commentRepository.updateAuthorInfoByUserId(
                request.getUserId(),
                request.getAuthorName(),
                request.getAuthorProfileImageUrl()
        );
    }

    public void deleteAllCommentsByUserId(String userId) {
        commentRepository.deleteByUserId(userId);
    }

    public void deleteAllCommentsByPostId(Long postId) {
        commentRepository.deleteByPostId(postId);
    }

    public List<CommentDto> getCommentsByPostId(Long postId) {

        List<CommentEntity> comments = commentRepository.findByPostIdOrderByCreatedAtAsc(postId);

        // 모든 댓글을 DTO로 변환 후 HashMap에 key: commentId, value: CommentDto 형태로 저장
        Map<Long, CommentDto> dtoMap = new LinkedHashMap<>();
        // 최상위 댓글(루트 댓글)들을 저장할 리스트
        List<CommentDto> rootComments = new ArrayList<>();

        for (CommentEntity comment : comments) {
            // 각 commentEntity들을 CommentDto로 변환
            CommentDto dto = CommentDto.builder()
                    .commentId(comment.getId())
                    .content(comment.getContent())
                    .createdAt(comment.getCreatedAt())
                    .authorName(comment.getAuthorName())
                    .authorProfileImageUrl(comment.getAuthorProfileImageUrl())
                    .replies(new ArrayList<>())
                    .build();

            // dtoMap에 저장
            dtoMap.put(comment.getId(), dto);

            if (comment.getParentId() == null) {
                rootComments.add(dto); // 루트 댓글로 추가
            } else {
                // 부모 댓글을 참조(reference)하여 대댓글로 추가
                CommentDto parentComment = dtoMap.get(comment.getParentId());
                if (parentComment != null) {
                    parentComment.getReplies().add(dto);
                }
            }
        }

        return rootComments;
    }
}
