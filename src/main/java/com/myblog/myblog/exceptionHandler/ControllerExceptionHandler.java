package com.myblog.myblog.exceptionHandler;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;



@ControllerAdvice
public class ControllerExceptionHandler {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@ExceptionHandler(Exception.class)
	public ModelAndView exceptionHander(HttpServletRequest request, Exception e) throws Exception {
		logger.error("Request URL : {}, Exception : {}", request.getRequestURL(),e);
		if(AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) {
			throw e;
		}
		ModelAndView mView = new ModelAndView();
		mView.addObject("url", request.getRequestURL());
		mView.addObject("exception", e);
		mView.setViewName("error/error");
		return mView;
	}

}
