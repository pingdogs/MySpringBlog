package com.myblog.myblog.aspect;

import java.util.Arrays;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;



@Aspect
@Component
public class LogAspect {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Pointcut("execution(* com.myblog.myblog.controller.*.*(..))")
	public void log() {
		
	}
	
	@Before("log()")
	public void doBefore(JoinPoint joinPoint) {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		RequestLog log = new RequestLog(request.getRequestURL().toString(), request.getRemoteAddr(), joinPoint.getSignature().getDeclaringTypeName() + 
				"." + joinPoint.getSignature().getName(), joinPoint.getArgs());
		logger.info("Request : {}", log);
	}
	
	@After("log()")
	public void doAfter() {
		
	}
	
	@AfterReturning(returning = "result", pointcut = "log()")
	public void doAfterReturn(Object result) {
		logger.info("Result : " + result);
	}
	
	private class RequestLog{
		private String url;
		private String ip;
		private String classMethod;
		private Object[] argsObjects;
		public RequestLog(String url, String ip, String classMethod, Object[] argsObjects) {
			super();
			this.url = url;
			this.ip = ip;
			this.classMethod = classMethod;
			this.argsObjects = argsObjects;
		}
		@Override
		public String toString() {
			return "RequestLog [url=" + url + ", ip=" + ip + ", classMethod=" + classMethod + ", argsObjects="
					+ Arrays.toString(argsObjects) + "]";
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getIp() {
			return ip;
		}
		public void setIp(String ip) {
			this.ip = ip;
		}
		public String getClassMethod() {
			return classMethod;
		}
		public void setClassMethod(String classMethod) {
			this.classMethod = classMethod;
		}
		public Object[] getArgsObjects() {
			return argsObjects;
		}
		public void setArgsObjects(Object[] argsObjects) {
			this.argsObjects = argsObjects;
		}
		
		
	}

}
