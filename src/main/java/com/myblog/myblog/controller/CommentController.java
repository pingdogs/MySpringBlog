package com.myblog.myblog.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.myblog.myblog.Service.BlogService;
import com.myblog.myblog.Service.CommentService;
import com.myblog.myblog.entity.Comment;
import com.myblog.myblog.entity.User;

@Controller
public class CommentController {
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private BlogService blogService;
	
	@Value("${comment.avatar}")
	private String avatar;
	
	@GetMapping("/comments/{id}")
	public String comments(@PathVariable Long id, Model model) {
		System.out.println(avatar);
		model.addAttribute("comments", commentService.listCommentByBlogId(id));
		return "blog :: commentList";
	}
	
	
	@PostMapping("/comments")
	public String post(Comment comment, HttpSession session) {
		Long blogid = comment.getBlog().getId();
		comment.setBlog(blogService.getBlog(blogid));
		User user = (User)session.getAttribute("user");
		if(user != null) {
			comment.setAvatar(user.getAvatar());
			comment.setNickname(user.getNickname());
		}
		
		
		comment.setAvatar(avatar);
		commentService.saveComment(comment);
		return "redirect:/comments/" + comment.getBlog().getId();
	}

}
