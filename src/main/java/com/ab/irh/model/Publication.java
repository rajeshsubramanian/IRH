package com.ab.irh.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "publication")
public class Publication {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "year", nullable = false)
	private Integer year;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "publication_author", joinColumns = {
			@JoinColumn(name = "publication_id") }, inverseJoinColumns = { @JoinColumn(name = "author_id") })
	private List<Author> authors;

	@Transient
	private Long[] authorIds;

	@Transient
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

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
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

	public void addAuthor(Author author) {
		if (authors == null) {
			authors = new ArrayList<>();
		}
		authors.add(author);
	}
}
