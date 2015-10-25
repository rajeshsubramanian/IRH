package com.ab.irh.web.form;

public class PublicationForm {

	private Long id;

	private String title;

	private Integer year;

	private Long[] authorIds;

	private String authorNames;

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

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public Long[] getAuthorIds() {
		return authorIds;
	}

	public void setAuthorIds(Long[] authorIds) {
		this.authorIds = authorIds;
	}

	public String getAuthorNames() {
		return authorNames;
	}

	public void setAuthorNames(String authorNames) {
		this.authorNames = authorNames;
	}
}
