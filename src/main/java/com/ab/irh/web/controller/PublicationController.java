package com.ab.irh.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.ab.irh.exception.ResourceNotFoundException;
import com.ab.irh.model.Author;
import com.ab.irh.model.Publication;
import com.ab.irh.repository.AuthorRepository;
import com.ab.irh.repository.PublicationRepository;
import com.ab.irh.web.form.PublicationForm;

@Controller
public class PublicationController {

	@Autowired
	private PublicationRepository publicationRepository;

	@Autowired
	private AuthorRepository authorRepository;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String index() {
		return "home";
	}

	@RequestMapping(value = "/add_publication", method = RequestMethod.GET)
	public String publicationForm(Model model) {

		model.addAttribute("publicationForm", new PublicationForm());
		model.addAttribute("authors", authorRepository.findAll());
		return "add_publication";
	}

	@RequestMapping(value = "/publication/{id}", method = RequestMethod.GET)
	public String getPublication(@PathVariable(value = "id") Publication publication, Model model)
			throws ResourceNotFoundException {

		if (publication == null) {
			throw new ResourceNotFoundException();
		}
		model.addAttribute(publication);
		return "publication_details";
	}

	@RequestMapping(value = "/add_publication", method = RequestMethod.POST)
	public String addPublication(@ModelAttribute PublicationForm publicationForm, @RequestParam MultipartFile[] files,
			Model model) {

		Publication publication = new Publication(publicationForm);
		if (publicationForm.getAuthorIds() != null && publicationForm.getAuthorIds().length > 0) {

			for (Long authorId : publicationForm.getAuthorIds()) {
				publication.addAuthor(authorRepository.findOne(authorId));
			}
		}
		if (publicationForm.getAuthorNames() != null) {

			String[] authorNames = publicationForm.getAuthorNames().split(",");
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
		return "redirect:/publications";
	}

	@RequestMapping(value = "/publications", method = RequestMethod.GET)
	public String getPublications(Model model) {

		List<Publication> publications = publicationRepository.findAll();
		model.addAttribute("publications", publications);
		return "publications";
	}
}
