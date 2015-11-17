package com.ab.irh.web.controller;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import com.ab.irh.model.PublicationImage;
import com.ab.irh.repository.AuthorRepository;
import com.ab.irh.repository.PublicationRepository;
import com.ab.irh.web.form.PublicationForm;
import com.amazonaws.auth.EnvironmentVariableCredentialsProvider;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;

@Controller
public class PublicationController {

	private static final Log LOG = LogFactory.getLog(PublicationController.class);

	@Value("${upload.directory.path}")
	private String uploadDirectoryPath;

	@Value("${aws.s3.bucket.name}")
	private String awsS3BucketName;

	@Autowired
	private PublicationRepository publicationRepository;

	@Autowired
	private AuthorRepository authorRepository;

	@RequestMapping(value = "/", method = RequestMethod.GET)
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

		if (files != null) {

			List<File> filesToUploadToS3 = new ArrayList<>();
			for (MultipartFile multipartFile : files) {

				try {

					String orignalFileName = multipartFile.getOriginalFilename();
					String extension = FilenameUtils.getExtension(orignalFileName);

					String fileNamePrefix = RandomStringUtils.random(30, true, true);
					String fileName = fileNamePrefix + "." + extension;
					String thumbnailFileName = fileNamePrefix + "_thumbnail." + extension;

					File thumbnailImageFile = new File(uploadDirectoryPath + thumbnailFileName);
					File imageFile = new File(uploadDirectoryPath + fileName);

					BufferedImage originalImage = ImageIO.read(multipartFile.getInputStream());
					ImageIO.write(Scalr.resize(originalImage, Scalr.Method.SPEED, Scalr.Mode.FIT_TO_WIDTH, 150, 150,
							Scalr.OP_ANTIALIAS), extension, thumbnailImageFile);
					ImageIO.write(Scalr.resize(originalImage, Scalr.Method.SPEED, Scalr.Mode.FIT_TO_WIDTH, 1024, 1024,
							Scalr.OP_ANTIALIAS), extension, imageFile);

					filesToUploadToS3.add(thumbnailImageFile);
					filesToUploadToS3.add(imageFile);

					originalImage.flush();
					originalImage = null;

					PublicationImage publicationImage = new PublicationImage(thumbnailFileName, fileName);
					publication.addPublicationImage(publicationImage);

				} catch (IllegalStateException | IOException e) {
					LOG.error("Exception while trying to upload files");
				}
			}

			uploadToS3(filesToUploadToS3);
		}

		authorRepository.save(publication.getAuthors());
		publication = publicationRepository.save(publication);
		model.addAttribute(publication);
		return "redirect:/publications";
	}

	private void uploadToS3(List<File> filesToUploadToS3) {

		AmazonS3 s3 = new AmazonS3Client(new EnvironmentVariableCredentialsProvider());
		Region region = Region.getRegion(Regions.US_EAST_1);
		s3.setRegion(region);

		for (File file : filesToUploadToS3) {

			String key = "irh/" + file.getName();
			PutObjectResult result = s3.putObject(new PutObjectRequest(awsS3BucketName, key, file));
			if (StringUtils.isNotBlank(result.getETag())) {
				file.delete();
			}
		}
	}

	@RequestMapping(value = "/publications", method = RequestMethod.GET)
	public String getPublications(Model model) {

		List<Publication> publications = publicationRepository.findAll();
		model.addAttribute("publications", publications);
		return "publications";
	}
}
