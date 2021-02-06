package com.myblog.myblog.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.myblog.myblog.Service.TypeService;
import com.myblog.myblog.constant.ServiceExceptionEnum;
import com.myblog.myblog.core.vo.CommonResult;
import com.myblog.myblog.entity.Blog;
import com.myblog.myblog.entity.Tag;
import com.myblog.myblog.entity.Type;
import com.myblog.myblog.exceptionHandler.ServiceException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/type/api")
@CrossOrigin(value = "*")
public class TypeRestController {

	@Autowired
	TypeService TypeService;
	
    @GetMapping("/list")
    public Flux<Type> list() {
    	List<Type> Types = TypeService.listType();
    	for(Type Type: Types) {
    		Type.setBlogs(null);
    	}
        return Flux.fromIterable(Types);
    }

    @GetMapping("/get/{id}")
//    @PostMapping("/get")
    public Mono<CommonResult<Type>> get(@PathVariable Long id) {
    	Type Type = TypeService.getType(id);
    	if(Type == null) {
    		ServiceException se = new ServiceException(ServiceExceptionEnum.ID_NOT_FOUND);
    		return Mono.just(CommonResult.error(se.getCode(), se.getMessage()));
    	}
    	for(Blog blog : Type.getBlogs()) {
    		blog.getType().setBlogs(null); 
    		List<Tag> tags = blog.getTags();
    		for(Tag t:tags) {
    			t.setBlogs(null);
    		}
    		blog.setUser(null);
    	}
    	return Mono.just(CommonResult.success(Type));
    }


}
