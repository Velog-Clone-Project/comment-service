package com.example.commentservice.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "comments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long parentId;

    @Column(nullable = false)
    private Long postId;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String authorName;
    @Column(nullable = false)
    private String authorProfileImageUrl;

    @Column(nullable = false, length = 300)
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}
