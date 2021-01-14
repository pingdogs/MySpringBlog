package com.myblog.myblog.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.myblog.myblog.Service.BlogService;
import com.myblog.myblog.Service.TagService;
import com.myblog.myblog.entity.Tag;
import com.myblog.myblog.query.BlogQuery;

@Controller
public class TagShowController {
	
	@Autowired
	private TagService tagService;
	
	@Autowired
	private BlogService blogService;
	
	@GetMapping("/tags/{id}")
	public String types(@PageableDefault(size=10, sort = {"updateTime"}, direction=Sort.Direction.DESC) Pageable pageable, Model model,@PathVariable Long id) {
		List<Tag> tags = tagService.listTagTop(100000);
		if(id == -1) {
			id = tags.get(0).getId();
		}
		BlogQuery blogQuery = new BlogQuery();
		blogQuery.setTagId(id);
		model.addAttribute("tags", tags);
		model.addAttribute("page", blogService.listBlog(id, pageable));
		model.addAttribute("activeTagId", id);
		return "tags";
	}
}
