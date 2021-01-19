package com.myblog.myblog.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.myblog.myblog.dao.BlogRepository;
import com.myblog.myblog.dao.TypeRepository;
import com.myblog.myblog.entity.Blog;
import com.myblog.myblog.entity.Tag;
import com.myblog.myblog.entity.Type;
import com.myblog.myblog.exceptionHandler.NotFoundException;
import com.myblog.myblog.query.BlogQuery;
import com.myblog.myblog.utils.MarkdownUtils;
import com.myblog.myblog.utils.MyBeanUtils;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.PageRequest;

@Service
public class BlogServiceImpl implements BlogService {

	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private TypeRepository typeRepository;
	
	@Override
	public Blog getBlog(Long id) {
		// TODO Auto-generated method stub
		return blogRepository.findById(id).get();
	}
	
	@Override
	public Blog getAndConvert(Long id) {
		// TODO Auto-generated method stub
		Blog blog = blogRepository.findById(id).get();
		if(blog == null) {
			throw new NotFoundException("Not exist");
		}
		Blog b = new Blog();
		BeanUtils.copyProperties(blog, b);
		String contentString = blog.getContent();
		contentString = MarkdownUtils.markdownToHtmlExtensions(contentString);
		b.setContent(contentString);
		blogRepository.updateViews(id);
		return b;
	}
	
	

	@Override
	public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
		
		
		return blogRepository.findAll(new Specification<Blog>() {

			@Override
			public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				List<Predicate> predicates = new ArrayList<Predicate>();
				if(!"".equals(blog.getTitle()) && blog.getTitle() != null) {
					predicates.add(criteriaBuilder.like(root.<String>get("title"), blog.getTitle()));
				}
				if(blog.getTypeId() != null) {
					predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"), blog.getTypeId()));
				}
				if(blog.getTagId() != null && blog.getTagId() != -1) {
//					predicates.add(criteriaBuilder.equal(root.<Tag>get("Tag").get("id"), blog.getTagId()));
				}
				if(blog.getRecommend()) {
					predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"), blog.getRecommend()));
				}
				
				query.where(predicates.toArray(new Predicate[predicates.size()]));
				return null;
			}
		}, pageable);
	}
	
	@Transactional
	@Override
	public Blog saveBlog(Blog blog) {
		if(blog.getId() == null) {
		// TODO Auto-generated method stub
			blog.setCreateTime(new Date());
			blog.setViews(0);
		}
		blog.setUpdateTime(new Date());
		
		return blogRepository.save(blog);
	}

	@Override
	public Blog updateBlog(Long id, Blog blog) {
		// TODO Auto-generated method stub
		Blog b = getBlog(id);
		if(b == null) {
			throw new NotFoundException("Not Exist");
		}
		BeanUtils.copyProperties(b,  blog, MyBeanUtils.getNullPropertyNames(blog));
		return saveBlog(blog);
	}

	@Override
	public void deleteBlog(Long id) {
		blogRepository.deleteById(id);

	}
	@Override
	public Page<Blog> listBlog(Pageable pageable) {
		// TODO Auto-generated method stub
		return blogRepository.findAll(pageable);
	}
	
	@Override
	public List<Blog> listRecommandBlogTop(Integer size) {
		Pageable pageable = PageRequest.of(0, size, Sort.Direction.DESC, "updateTime");
		return blogRepository.findAll(pageable).getContent();
	}
	
	@Override
	public Page<Blog> listBlog(String query, Pageable pageable) {
		
		return blogRepository.findByQuery("%"+query+"%", pageable);
	}
	
	@Override
	public Page<Blog> listBlog(Long tagId, Pageable pageable) {
		// TODO Auto-generated method stub
		return blogRepository.findAll(new Specification<Blog>() {

			@Override
			public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
				Join join = root.join("tags");
				return criteriaBuilder.equal(join.get("id"), tagId);
			}
			
		}, pageable);
	}

}
