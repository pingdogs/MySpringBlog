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
import com.myblog.myblog.Service.TypeService;
import com.myblog.myblog.entity.Type;
import com.myblog.myblog.query.BlogQuery;

@Controller
public class TypeShowController {
	
	@Autowired
	private TypeService typeService;
	
	@Autowired
	private BlogService blogService;
	
	@GetMapping("/types/{id}")
	public String types(@PageableDefault(size=10, sort = {"updateTime"}, direction=Sort.Direction.DESC) Pageable pageable, Model model,@PathVariable Long id) {
		List<Type> types = typeService.listTypeTop(100000);
		if(id == -1) {
			id = types.get(0).getId();
		}
		BlogQuery blogQuery = new BlogQuery();
		blogQuery.setTypeId(id);
		model.addAttribute("types", types);
		model.addAttribute("page", blogService.listBlog(pageable, blogQuery));
		model.addAttribute("activeTypeId", id);
		return "types";
	}
}
