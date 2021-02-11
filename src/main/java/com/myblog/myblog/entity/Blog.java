package com.myblog.myblog.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.springframework.data.redis.core.RedisHash;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@Entity
@Table(name="blog")
public class Blog implements Serializable{
	
	@Id
	@GeneratedValue
	private Long id;
	
	private String title;
	
	@Basic(fetch = FetchType.LAZY)
	@Lob
	private String content;
	private String firstPicture;
	private String flag;
	private Integer views;
	private String description;
	private boolean appreciation;
	private boolean shareStatement;
	private boolean commentabled;
	private boolean published;
	private boolean recommend; 
	
	@Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;
    
    @ManyToOne
    @JsonIgnoreProperties(value="blogs")
	private Type type;
	@ManyToMany(cascade = {CascadeType.PERSIST})
    @JsonIgnoreProperties(value="blogs")
    private List<Tag> tags = new ArrayList<>();


    @ManyToOne
    @JsonIgnore
    private User user;

    @OneToMany(mappedBy = "blog")
    @JsonIgnoreProperties(value="blog")
    private List<Comment> comments = new ArrayList<>();
    
    @Transient
    @JsonIgnore
    private String tagIds;
	
	public void init() {
		this.tagIds = tagsToIds(this.getTags());
	}
	
	private String tagsToIds(List<Tag> tags) {
		if(!tags.isEmpty()) {
			StringBuffer idStringBuffer = new StringBuffer();
			boolean flag = false;
			for(Tag tag: tags) {
				if(flag)
					idStringBuffer.append(",");
				else {
					flag = true;
				}
				idStringBuffer.append(tag.getId());
			}
			return idStringBuffer.toString();
		}
		return tagIds;
	}
	
	public String getTagIds() {
		return tagIds;
	}

	public void setTagIds(String tagIds) {
		this.tagIds = tagIds;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public Blog() {
		
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getFirstPicture() {
		return firstPicture;
	}

	public void setFirstPicture(String firstPicture) {
		this.firstPicture = firstPicture;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public boolean isAppreciation() {
		return appreciation;
	}

	public void setAppreciation(boolean appreciation) {
		this.appreciation = appreciation;
	}

	public boolean isShareStatement() {
		return shareStatement;
	}

	public void setShareStatement(boolean shareStatement) {
		this.shareStatement = shareStatement;
	}

	public boolean isCommentabled() {
		return commentabled;
	}

	public void setCommentabled(boolean commentabled) {
		this.commentabled = commentabled;
	}

	public boolean isPublished() {
		return published;
	}

	public void setPublished(boolean published) {
		this.published = published;
	}

	public boolean isRecommend() {
		return recommend;
	}

	public void setRecommend(boolean recommend) {
		this.recommend = recommend;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public String toString() {
		return "Blog [id=" + id + ", title=" + title + ", content=" + content + ", firstPicture=" + firstPicture
				+ ", flag=" + flag + ", views=" + views + ", appreciation=" + appreciation + ", shareStatement="
				+ shareStatement + ", commentabled=" + commentabled + ", published=" + published + ", recommend="
				+ recommend + ", createTime=" + createTime + ", updateTime=" + updateTime + "]";
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	

}
