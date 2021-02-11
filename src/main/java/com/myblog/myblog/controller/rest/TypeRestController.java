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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/type")
@CrossOrigin(value = "*")
@Api(tags = "Type Api")
public class TypeRestController {

	@Autowired
	TypeService TypeService;
	
    @GetMapping(value = "/list", produces = "application/json; charset=utf-8")
    @ApiOperation(value = "Show all types", notes = "")
    public Flux<Type> list() {
    	List<Type> Types = TypeService.listType();
//    	for(Type Type: Types) {
//    		Type.setBlogs(null);
//    	}
        return Flux.fromIterable(Types);
    }

    @GetMapping(value = "/get/{id}", produces = "application/json; charset=utf-8")
//    @PostMapping("/get")
    @ApiOperation(value = "Show a type", notes = "Select a type based on input id")
    @ApiImplicitParam(name = "id", value = "type id", paramType = "query", dataTypeClass = Long.class, required = true, example = "1024")
    public Mono<CommonResult<Type>> get(@PathVariable Long id) {
    	Type Type = TypeService.getType(id);
    	if(Type == null) {
    		ServiceException se = new ServiceException(ServiceExceptionEnum.ID_NOT_FOUND);
    		return Mono.just(CommonResult.error(se.getCode(), se.getMessage()));
    	}
//    	for(Blog blog : Type.getBlogs()) {
//    		blog.getType().setBlogs(null); 
//    		List<Tag> tags = blog.getTags();
//    		for(Tag t:tags) {
//    			t.setBlogs(null);
//    		}
//    		blog.setUser(null);
//    	}
    	return Mono.just(CommonResult.success(Type));
    }


}
