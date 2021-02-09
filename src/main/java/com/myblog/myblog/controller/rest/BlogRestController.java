package com.myblog.myblog.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.myblog.myblog.Service.BlogService;
import com.myblog.myblog.constant.ServiceExceptionEnum;
import com.myblog.myblog.core.vo.CommonResult;
import com.myblog.myblog.entity.Blog;
import com.myblog.myblog.entity.Tag;
import com.myblog.myblog.exceptionHandler.ServiceException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/blog/api")
@CrossOrigin(value = "*")
@Api(tags = "Blog Api")
public class BlogRestController {

	@Autowired
	BlogService blogService;
    @GetMapping("/list")
    @ApiOperation(value = "Show all blogs", notes = "")
    public Flux<Blog> list() {
    	List<Blog> blogs = blogService.listBlog();
    	for(int i = 0 ; i < blogs.size(); i++) {
    		Blog blog = blogs.get(i);
    		blog.getType().setBlogs(null); 
    		List<Tag> tags = blog.getTags();
    		for(Tag tag:tags) {
    			tag.setBlogs(null);
    		}
    		blog.setUser(null);
    	}
        return Flux.fromIterable(blogs);
    }

    @GetMapping("/get/{id}")
//    @PostMapping("/get")
    @ApiOperation(value = "Show a blog", notes = "Select a blog based on input id")
    @ApiImplicitParam(name = "id", value = "blog id", paramType = "query", dataTypeClass = Long.class, required = true, example = "1024")
    public Mono<CommonResult<Blog>> get(@PathVariable Long id) {
    	Blog blog = blogService.getBlog(id);
    	if(blog == null) {
    		ServiceException se = new ServiceException(ServiceExceptionEnum.ID_NOT_FOUND);
    		return Mono.just(CommonResult.error(se.getCode(), se.getMessage()));
    	}
    	blog.setUser(null);
    	blog.getType().setBlogs(null);
    	for(Tag tag:blog.getTags()) {
			tag.setBlogs(null);
		}
    	return Mono.just(CommonResult.success(blog));
    }


}
