package com.myblog.myblog.Service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.type.ListType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Sort;
import com.myblog.myblog.dao.TypeRepository;
import com.myblog.myblog.entity.Type;
import com.myblog.myblog.exceptionHandler.NotFoundException;


@Service
public class TypeServiceImpl implements TypeService{

	@Autowired
	private TypeRepository typeRepository;
	
	@Transactional
	@Override
	public Type save(Type type) {
		// TODO Auto-generated method stub
		return typeRepository.save(type);
	}

	@Override
	public Type getType(Long id) {
		// TODO Auto-generated method stub
		return typeRepository.findById(id).get();
	}

	@Override
	public Page<Type> listType(Pageable pageable) {
		// TODO Auto-generated method stub
		return typeRepository.findAll(pageable);
	}

	@Override
	public Type updateType(Long id, Type type) {
		// TODO Auto-generated method stub
		Type t = typeRepository.findById(id).get();
		if(t== null) {
			throw new NotFoundException("Not Exist");
		}
		BeanUtils.copyProperties(type, t);
		return typeRepository.save(t);
	}

	@Override
	public void deleteType(Long id) {
		typeRepository.deleteById(id);
		
	}
	@Override
	public List<Type> listType() {
		// TODO Auto-generated method stub
		return typeRepository.findAll();
	}
	
	@Override
	public Type getTypeByName(String stringName) {
		// TODO Auto-generated method stub
		return typeRepository.findByName(stringName);
	}
	
	@Override
	public List<Type> listTypeTop(Integer size) {
		// TODO Auto-generated method stub
		List<Type> lstTypes = listType();
		List<Type> resultList = new ArrayList<Type>() ;
		int index = 0;
		for(Type t: lstTypes) {
			resultList.add(t);
			index++;
			if(index == size)
				break;
		}
		return resultList;
	}
}
