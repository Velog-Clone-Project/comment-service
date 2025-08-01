package com.example.commentservice.exception;

import com.example.common.exception.BaseCustomException;

public class ParentCommentMismatchException extends BaseCustomException {
    public ParentCommentMismatchException() {
        super("Parent comment does not belong to the same post");
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
