package com.myblog.myblog.dao;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.myblog.myblog.entity.Tag;
import com.myblog.myblog.entity.Type;

/**
 * Created by limi on 2017/10/16.
 */
public interface TagRepository extends JpaRepository<Tag,Long> {

	@Query("select t from Tag t")
	List<Tag> findTop(Pageable pageable);
	
    Tag findByName(String name);
    
}
