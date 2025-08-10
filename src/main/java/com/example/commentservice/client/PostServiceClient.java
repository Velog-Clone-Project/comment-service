package com.example.commentservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "post-service",
//        url = "http://post-service:8003",
        path = "/internal")
public interface PostServiceClient {

    @GetMapping("/posts/exists/{postId}")
    ResponseEntity<Void> checkPostExists(@PathVariable("postId") Long postId);
}
