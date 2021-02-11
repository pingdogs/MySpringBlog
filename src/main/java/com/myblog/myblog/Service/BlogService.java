package com.myblog.myblog.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.myblog.myblog.entity.Blog;
import com.myblog.myblog.entity.Type;
import com.myblog.myblog.query.BlogQuery;

public interface BlogService {
	
	Blog getBlog(Long id);
	
	
	Blog getAndConvert(Long id);
//	boolean isExist(Long id);
	Blog saveBlog(Blog blog);
	Blog updateBlog(Long id, Blog blog);
	void deleteBlog(Long id);
	
	Page<Blog> listBlog(Pageable pageable);
	Page<Blog> listBlog(String query, Pageable pageable);
	Page<Blog> listBlog(Pageable pageable, BlogQuery blog);
	Page<Blog> listBlog(Long tagId, Pageable pageable);
	List<Blog> listBlog();
	List<Blog> listRecommandBlogTop(Integer size);
	
	

}
