package com.myblog.myblog.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.myblog.myblog.entity.Blog;

public interface BlogRepository extends JpaSpecificationExecutor<Blog>, JpaRepository<Blog, Long>{

	
	@Query("select b from Blog b where b.title like ?1 or b.content like ?1")
	Page<Blog> findByQuery(String query, Pageable pageable);
	
	@Transactional
	@Modifying
	@Query("update Blog b set b.views = b.views+1 where b.id = ?1")
	int updateViews(Long id);
}
