package com.myblog.myblog.exceptionHandler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.ServerWebInputException;

import com.myblog.myblog.constant.ServiceExceptionEnum;
import com.myblog.myblog.core.vo.CommonResult;

@ControllerAdvice(basePackages = "com.myblog.myblog.RestController.BlogRestController")
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = ServiceException.class)
    public CommonResult serviceExceptionHandler(ServiceException ex) {
    	System.out.println("service exception");
        return CommonResult.error(ex.getCode(), ex.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(value = ServerWebInputException.class)
    public CommonResult serverWebInputExceptionHandler(ServerWebInputException ex) {
       
        return CommonResult.error(ServiceExceptionEnum.MISSING_REQUEST_PARAM_ERROR.getCode(),
                ServiceExceptionEnum.MISSING_REQUEST_PARAM_ERROR.getMessage());
    }
    
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public CommonResult exceptionHandler(Exception e) {
        return CommonResult.error(ServiceExceptionEnum.SYS_ERROR.getCode(),
                ServiceExceptionEnum.SYS_ERROR.getMessage());
    }

}
