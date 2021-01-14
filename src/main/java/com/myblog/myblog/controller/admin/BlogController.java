package com.myblog.myblog.controller.admin;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Sort;

import com.myblog.myblog.Service.BlogService;
import com.myblog.myblog.Service.TagService;
import com.myblog.myblog.Service.TypeService;
import com.myblog.myblog.entity.Blog;
import com.myblog.myblog.entity.User;
import com.myblog.myblog.query.BlogQuery;

import javassist.expr.NewArray;

@Controller
@RequestMapping("/admin")
public class BlogController {
	
	@Autowired
	private BlogService blogService;
	@Autowired
	private TypeService typeService;
	@Autowired
	private TagService tagService;
	
	private static final String INPUT = "admin/blogs-input";
	private static final String LIST = "admin/blogs";
	private static final String REDIRECT_LIST = "redirect:blogs";
	private final static int PAGEDEFAULTSIZE = 5;
	
	@GetMapping("/blogs")
	public String blogs(@PageableDefault(size=PAGEDEFAULTSIZE, sort = {"updateTime"}, direction=Sort.Direction.DESC) Pageable pageable, Model model, BlogQuery blog) {
//		Blog blog = blogService.getBlog(Long.parseLong((String)model.getAttribute("id")));
		
		model.addAttribute("page", blogService.listBlog(pageable, blog));
		model.addAttribute("types", typeService.listType());
		return LIST;
	}
	
	@PostMapping("/blogs/search")
	public String search(@PageableDefault(size=PAGEDEFAULTSIZE, sort = {"updateTime"}, direction=Sort.Direction.DESC) Pageable pageable, Model model, BlogQuery blog) {
//		Blog blog = blogService.getBlog(Long.parseLong((String)model.getAttribute("id")));
		model.addAttribute("types", typeService.listType());
		Page<Blog> blogList = blogService.listBlog(pageable, blog);
		model.addAttribute("page", blogList);
		return "admin/blogs ::blogList";
	}
	
	@GetMapping("/blogs/input")
	public String input(Model model) {
		model.addAttribute("blog", new Blog());
		setTypeAndTag(model);
		return INPUT;
	}
	
	private void setTypeAndTag(Model model) {
		// TODO Auto-generated method stub
		
		model.addAttribute("tags", tagService.listTag());
		model.addAttribute("types", typeService.listType());

	}
	
	@GetMapping("/blogs/{id}/input")
	public String editInput(@PathVariable Long id, Model model) {
		setTypeAndTag(model);
		Blog blog = blogService.getBlog(id);
		blog.init();
		model.addAttribute("blog", blog);
		return INPUT;
	}
	
	@PostMapping("/blogs")
	public String post(Blog blog,RedirectAttributes attributes, HttpSession session) {
		blog.setUser((User)(session.getAttribute("user")));
		blog.setType(typeService.getType(blog.getType().getId()));
		blog.setTags(tagService.listTag(blog.getTagIds()));
		Blog b = blogService.saveBlog(blog);
		if(b== null) {
			attributes.addFlashAttribute("message", "Fail");
		}else {
			attributes.addFlashAttribute("message", "Success");
		}
		return REDIRECT_LIST;
	}
	
	@GetMapping("/blogs/{id}/delete")
	public String delete(@PathVariable Long id, RedirectAttributes attributes) {
		blogService.deleteBlog(id);
		attributes.addFlashAttribute("message", "Success");
		
		return REDIRECT_LIST;
		
		
	}

}
