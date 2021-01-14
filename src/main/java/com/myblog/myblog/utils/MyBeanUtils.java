package com.myblog.myblog.utils;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class MyBeanUtils {
	
	public static String[] getNullPropertyNames(Object source) {
		BeanWrapper beanWrapper = new BeanWrapperImpl(source);
		PropertyDescriptor[] pdsDescriptors = beanWrapper.getPropertyDescriptors();
		List<String> nullPropertyNameStrings = new ArrayList<String>();
		for(PropertyDescriptor pd : pdsDescriptors) {
			String propertyNameString = pd.getName();
			if(beanWrapper.getPropertyValue(propertyNameString) == null) {
				nullPropertyNameStrings.add(propertyNameString);
			}
		}
		return nullPropertyNameStrings.toArray(new String[nullPropertyNameStrings.size()]);
	}

}
