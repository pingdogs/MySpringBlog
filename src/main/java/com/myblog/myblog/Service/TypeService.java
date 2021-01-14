package com.myblog.myblog.Service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.myblog.myblog.entity.Type;

public interface TypeService {
	Type save(Type type);
	Type getType(Long id);
	Page<Type> listType(Pageable pageable);
	Type updateType(Long id, Type type);
	List<Type> listType();
	void deleteType(Long id);
	Type getTypeByName(String stringName);
	List<Type> listTypeTop(Integer size);
}
