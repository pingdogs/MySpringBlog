package com.myblog.myblog.controller.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myblog.myblog.Service.TypeService;
import com.myblog.myblog.entity.Type;


@Controller
@RequestMapping("/admin")
public class TypeController {
	
	@Autowired
	private TypeService typeService;
	private final static int PAGEDEFAULTSIZE = 5;
	
	@GetMapping("/types")
	public String types(@PageableDefault(size=PAGEDEFAULTSIZE, sort= {"id"}, direction=Sort.Direction.DESC) Pageable pageable, Model model) {
		model.addAttribute("page", typeService.listType(pageable));
		return "admin/types";
	}
	
//	@GetMapping("/types/input")
//	public String input() {
//		return "admin/types-input";
//	}
	@GetMapping("/types/input")
	public String input(Model model) {
		model.addAttribute("type", new Type());
		return "admin/types-input";
	}
	
	@GetMapping("/types/{id}/input")
	public String editInput(@PathVariable Long id, Model model) {
		model.addAttribute("type", typeService.getType(id));
		return "admin/types-input";
	}
	
	@PostMapping("/types")
	public String post(@Valid Type type, RedirectAttributes attributes, BindingResult result) {
		Type t;
		t = typeService.getTypeByName(type.getName());
		if(t != null) {
			result.rejectValue("name", "nameError", "name exist");
		}
		if(result.hasErrors()) {
			return "admin/types-input";
		}
		t = typeService.save(type);
		if(t== null) {
			attributes.addFlashAttribute("message", "Fail");
		}else {
			attributes.addFlashAttribute("message", "Success");
			
		}
		return "redirect:/admin/types";
	}
	
	@PostMapping("/types/{id}")
	public String editPost(@Valid Type type, BindingResult result, RedirectAttributes attributes,  @PathVariable Long id) {
		Type t;
		t = typeService.getTypeByName(type.getName());
		if(t != null) {
			result.rejectValue("name", "nameError", "name exist");
		}
		if(result.hasErrors()) {
			return "admin/types-input";
		}
		t = typeService.updateType(id, type);
		if(t== null) {
			attributes.addFlashAttribute("message", "Fail");
		}else {
			attributes.addFlashAttribute("message", "Success");
			
		}
		return "redirect:/admin/types";
	}
	
	@GetMapping("/types/{id}/delete")
	public String delete(@PathVariable Long id, RedirectAttributes attributes) {
		typeService.deleteType(id);
		attributes.addFlashAttribute("message", "Success");
		return "redirect:/admin/types";
	}
	

}
