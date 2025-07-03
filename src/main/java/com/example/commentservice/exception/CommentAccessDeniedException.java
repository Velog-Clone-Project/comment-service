package com.example.commentservice.exception;

import com.example.common.exception.BaseCustomException;

public class CommentAccessDeniedException extends BaseCustomException {
    public CommentAccessDeniedException() {
        super("Access denied");
    }

    @Override
    public int getStatusCode() {
        return 403;
    }
}
