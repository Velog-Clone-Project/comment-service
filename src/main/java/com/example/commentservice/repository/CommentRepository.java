package com.example.commentservice.repository;

import com.example.commentservice.domain.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {


    @Modifying
    @Query("UPDATE CommentEntity c SET c.authorName= :authorName, c.authorProfileImageUrl = :authorProfileImageUrl " +
    "WHERE c.userId = :userId")
    void updateAuthorInfoByUserId(
            @Param("userId") String userId,
            @Param("authorName") String authorName,
            @Param("authorProfileImageUrl") String authorProfileImageUrl
    );

    void deleteByUserId(String userId);

    void deleteByPostId(Long postId);

    List<CommentEntity> findByPostIdOrderByCreatedAtAsc(Long postId);
}
