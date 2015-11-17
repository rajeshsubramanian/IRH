package com.ab.irh.model;

import java.util.ArrayList;
import java.util.Collections;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.ab.irh.web.form.PublicationForm;

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

	@Column(name = "research_focus", length = 1000)
	private String researchFocus;

	@Column(name = "theory_description", length = 1000)
	private String theoryDescription;

	@Column(name = "gaps_in_literature", length = 1000)
	private String gapsInLiterature;

	@Column(name = "measures", length = 1000)
	private String measures;

	@Column(name = "findings", length = 1000)
	private String findings;

	@Column(name = "neural_areas_implicated", length = 1000)
	private String neuralAreasImplicated;

	@Column(name = "limitations", length = 1000)
	private String limitations;

	@Column(name = "queries", length = 1000)
	private String queries;

	@Column(name = "thinking_strategies", length = 1000)
	private String thinkingStrategies;

	@Column(name = "idea_connectome", length = 1000)
	private String ideaConnectome;

	@Column(name = "quizzlet", length = 1000)
	private String quizzlet;

	@Column(name = "answers", length = 1000)
	private String answers;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "publication_author", joinColumns = {
			@JoinColumn(name = "publication_id") }, inverseJoinColumns = { @JoinColumn(name = "author_id") })
	private List<Author> authors;

	@OneToMany(mappedBy = "publication", cascade = CascadeType.ALL)
	private List<PublicationImage> publicationImages;

	public Publication() {
	}

	public Publication(PublicationForm publicationForm) {

		this.id = publicationForm.getId();
		this.title = publicationForm.getTitle();
		this.year = publicationForm.getYear();
		this.researchFocus = publicationForm.getResearchFocus();
		this.theoryDescription = publicationForm.getTheoryDescription();
		this.gapsInLiterature = publicationForm.getGapsInLiterature();
		this.measures = publicationForm.getMeasures();
		this.findings = publicationForm.getFindings();
		this.neuralAreasImplicated = publicationForm.getNeuralAreasImplicated();
		this.limitations = publicationForm.getLimitations();
		this.queries = publicationForm.getQueries();
		this.thinkingStrategies = publicationForm.getThinkingStrategies();
		this.ideaConnectome = publicationForm.getIdeaConnectome();
		this.quizzlet = publicationForm.getQuizzlet();
		this.answers = publicationForm.getAnswers();
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

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public String getResearchFocus() {
		return researchFocus;
	}

	public void setResearchFocus(String researchFocus) {
		this.researchFocus = researchFocus;
	}

	public String getTheoryDescription() {
		return theoryDescription;
	}

	public void setTheoryDescription(String theoryDescription) {
		this.theoryDescription = theoryDescription;
	}

	public String getGapsInLiterature() {
		return gapsInLiterature;
	}

	public void setGapsInLiterature(String gapsInLiterature) {
		this.gapsInLiterature = gapsInLiterature;
	}

	public String getMeasures() {
		return measures;
	}

	public void setMeasures(String measures) {
		this.measures = measures;
	}

	public String getFindings() {
		return findings;
	}

	public void setFindings(String findings) {
		this.findings = findings;
	}

	public String getNeuralAreasImplicated() {
		return neuralAreasImplicated;
	}

	public void setNeuralAreasImplicated(String neuralAreasImplicated) {
		this.neuralAreasImplicated = neuralAreasImplicated;
	}

	public String getLimitations() {
		return limitations;
	}

	public void setLimitations(String limitations) {
		this.limitations = limitations;
	}

	public String getQueries() {
		return queries;
	}

	public void setQueries(String queries) {
		this.queries = queries;
	}

	public String getThinkingStrategies() {
		return thinkingStrategies;
	}

	public void setThinkingStrategies(String thinkingStrategies) {
		this.thinkingStrategies = thinkingStrategies;
	}

	public String getIdeaConnectome() {
		return ideaConnectome;
	}

	public void setIdeaConnectome(String ideaConnectome) {
		this.ideaConnectome = ideaConnectome;
	}

	public String getQuizzlet() {
		return quizzlet;
	}

	public void setQuizzlet(String quizzlet) {
		this.quizzlet = quizzlet;
	}

	public String getAnswers() {
		return answers;
	}

	public void setAnswers(String answers) {
		this.answers = answers;
	}

	public List<Author> getAuthors() {
		return authors;
	}

	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}

	public void addAuthor(Author author) {
		if (authors == null) {
			authors = new ArrayList<>();
		}
		authors.add(author);
	}
	
	public List<PublicationImage> getPublicationImages() {
		return publicationImages;
	}
	
	public void setPublicationImages(List<PublicationImage> publicationImages) {
		this.publicationImages = publicationImages;
	}

	public void addPublicationImage(PublicationImage publicationImage) {
		if (publicationImages == null) {
			publicationImages = new ArrayList<>();
		}
		publicationImage.setPublication(this);
		publicationImages.add(publicationImage);
	}
}
