package com.myblog.myblog.query;

public class BlogQuery {

	private String title;
	private Long typeId;
	private Long tagId;
	private boolean recommend;
	public BlogQuery() {}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getTypeId() {
		return typeId;
	}
	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}
	public boolean getRecommend() {
		return recommend;
	}
	public void setRecommend(Boolean recommend) {
		this.recommend = recommend;
	}
	@Override
	public String toString() {
		return "BlogQuery [title=" + title + ", typeId=" + typeId + ", recommend=" + recommend + "]";
	}
	public Long getTagId() {
		return tagId;
	}
	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}
	
	
}
