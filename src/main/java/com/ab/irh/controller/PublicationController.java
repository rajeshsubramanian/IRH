package com.ab.irh.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.ab.irh.model.Author;
import com.ab.irh.model.Publication;
import com.ab.irh.repository.AuthorRepository;
import com.ab.irh.repository.PublicationRepository;

@Controller
public class PublicationController {

	@Autowired
	private PublicationRepository publicationRepository;

	@Autowired
	private AuthorRepository authorRepository;

	@RequestMapping(value = "/publication", method = RequestMethod.GET)
	public String publicationForm(Model model) {

		model.addAttribute("publication", new Publication());
		model.addAttribute("authors", authorRepository.findAll());
		return "publication";
	}

	@RequestMapping(value = "/publication", method = RequestMethod.POST)
	public String addPublication(@ModelAttribute Publication publication, Model model) {

		if (publication.getAuthorIds() != null && publication.getAuthorIds().length > 0) {

			for (Long authorId : publication.getAuthorIds()) {
				publication.addAuthor(authorRepository.findOne(authorId));
			}
		} else if (publication.getAuthorNames() != null) {

			String[] authorNames = publication.getAuthorNames().split(",");
			if (authorNames != null && authorNames.length > 0) {

				for (String authorName : authorNames) {

					Author author = new Author(authorName.trim());
					publication.addAuthor(author);
				}
			}
		}

		authorRepository.save(publication.getAuthors());
		publication = publicationRepository.save(publication);
		model.addAttribute(publication);
		return "result";
	}

	@RequestMapping(value = "/publications", method = RequestMethod.GET)
	public String getPublications(Model model) {

		List<Publication> publications = publicationRepository.findAll();
		model.addAttribute("publications", publications);
		return "publications";
	}
}
