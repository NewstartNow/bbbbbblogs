package com.shell845.myblog.service;

import com.shell845.myblog.po.Comment;

import java.util.List;


public interface CommentService {
    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);
}
