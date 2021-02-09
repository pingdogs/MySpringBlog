package com.myblog.myblog.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.myblog.myblog.Service.BlogService;
import com.myblog.myblog.Service.TagService;
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
@RequestMapping("/tag/api")
@CrossOrigin(value = "*")
@Api(tags = "Tag Api")
public class TagRestController {

	@Autowired
	TagService tagService;
    @GetMapping("/list")
    @ApiOperation(value = "Show all tags", notes = "")
    public Flux<Tag> list() {
    	List<Tag> tags = tagService.listTag();
    	for(Tag tag: tags) {
    		tag.setBlogs(null);
    	}
        return Flux.fromIterable(tags);
    }

    @GetMapping("/get/{id}")
//    @PostMapping("/get")
    @ApiOperation(value = "Show a tag", notes = "Select a tag based on input id")
    @ApiImplicitParam(name = "id", value = "tag id", paramType = "query", dataTypeClass = Long.class, required = true, example = "1024")
    public Mono<CommonResult<Tag>> get(@PathVariable Long id) {
    	Tag tag = tagService.getTag(id);
    	if(tag == null) {
    		ServiceException se = new ServiceException(ServiceExceptionEnum.ID_NOT_FOUND);
    		return Mono.just(CommonResult.error(se.getCode(), se.getMessage()));
    	}
    	for(Blog blog : tag.getBlogs()) {
    		blog.getType().setBlogs(null); 
    		List<Tag> tags = blog.getTags();
    		for(Tag t:tags) {
    			t.setBlogs(null);
    		}
    		blog.setUser(null);
    	}
    	return Mono.just(CommonResult.success(tag));
    }


}
