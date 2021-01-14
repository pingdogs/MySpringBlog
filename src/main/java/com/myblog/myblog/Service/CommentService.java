package com.myblog.myblog.Service;

import java.util.List;

import com.myblog.myblog.entity.Comment;

public interface CommentService {

	List<Comment> listCommentByBlogId(Long id);
	Comment saveComment(Comment comment);
}
