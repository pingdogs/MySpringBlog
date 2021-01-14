package com.myblog.myblog.dao;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.myblog.myblog.entity.Comment;


public interface CommentRepository extends JpaRepository<Comment, Long>{

	
	List<Comment> findByBlogIdAndParentCommentNull(Long id, Sort sort);
}
