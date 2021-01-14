package com.myblog.myblog.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.thymeleaf.standard.expression.Each;

import com.myblog.myblog.dao.CommentRepository;
import com.myblog.myblog.entity.Comment;


@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository commentRepository;
	
	@Override
	public List<Comment> listCommentByBlogId(Long id) {
		// TODO Auto-generated method stub
		Sort sort = Sort.by(Sort.Order.desc("createTime"));
		return eachComment(commentRepository.findByBlogIdAndParentCommentNull(id, sort));
	}
    private List<Comment> eachComment(List<Comment> comments) {
        List<Comment> commentsView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }
    private void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            List<Comment> replys1 = comment.getReplyComments();
            for(Comment reply1 : replys1) {
                recursively(reply1);
            }
            comment.setReplyComments(tempReplys);
            tempReplys = new ArrayList<>();
        }
    }

    private List<Comment> tempReplys = new ArrayList<>();
    private void recursively(Comment comment) {
        tempReplys.add(comment);
        if (comment.getReplyComments().size()>0) {
            List<Comment> replys = comment.getReplyComments();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComments().size()>0) {
                    recursively(reply);
                }
            }
        }
    }

	@Transactional
	@Override
	public Comment saveComment(Comment comment) {
		// TODO Auto-generated method stub
		Long parentCommentId = comment.getParentComment().getId();
		if (parentCommentId != -1) {
			comment.setParentComment(commentRepository.getOne(parentCommentId));
		}else {
			comment.setParentComment(null);
		}
		comment.setCreateTime(new Date());
		return commentRepository.save(comment);
	}

}
