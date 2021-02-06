package com.myblog.myblog.exceptionHandler;

import com.myblog.myblog.constant.ServiceExceptionEnum;

public final class ServiceException extends RuntimeException {
    private final Integer code;

    public ServiceException(ServiceExceptionEnum serviceExceptionEnum) {
        super(serviceExceptionEnum.getMessage());
        this.code = serviceExceptionEnum.getCode();
    }

    public Integer getCode() {
        return code;
    }

	@Override
	public String toString() {
		return "ServiceException [code=" + code + "]";
	}
    
    

}
