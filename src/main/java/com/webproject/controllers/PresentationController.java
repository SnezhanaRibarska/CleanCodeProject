package com.webproject.controllers;

import com.webproject.models.Presentation;
import com.webproject.services.PresentationService;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import com.webproject.form.PresentationForm;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("presentations")
public class PresentationController {

	private PresentationService presentationService;

	@Autowired
	public PresentationController(PresentationService presentationService) {
		this.presentationService = presentationService;
	}

	@GetMapping
	public String getAllPresentations(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "tag", required = false) String tag) {
		addAttributes(presentationService.findAll(page, tag), model);
		model.addAttribute("tags", presentationService.getAllDistinctTags());
		model.addAttribute("selectedTag", tag);
		return "all-presentations";
	}

	@GetMapping(value = "/u/{userID}")
	public String getPresentationsForUser(Model model, @PathVariable("userID") String userID,
			@RequestParam(name = "page", defaultValue = "0") int page) {
		addAttributes(presentationService.findAllForUser(userID, page), model);
		return "profile";
	}

	@GetMapping("/p/{presentationID}")
	public String getPresentationForId(@PathVariable Long presentationID, Model model) {
		model.addAttribute("presentation", presentationService.getPresentationById(presentationID).get());
		return "presentation";

	}

	@GetMapping("/p/{presentationID}/s/{slideID}")
	public String getSlideForPresentation(@PathVariable("presentationID") Long presentationID,
			@PathVariable(value = "slideID") Integer slideID) {
		Presentation presentation = presentationService.getPresentationById(presentationID).get();
		return "redirect:" + presentation.getSlides().get(slideID).getImageUrl();

	}

	@GetMapping("/delete/p/{presentationID}")
	public String deletePresentationForId(@PathVariable Long presentationID, Model model) {
		presentationService.deletePresentationForId(presentationID);
		return getAllPresentations(model, 0, null);

	}

	@GetMapping(value = "/export")
	@ResponseBody
	public String export() {
		presentationService.exportAll();
		return null;
	}

	private void addAttributes(Page<Presentation> presentations, Model model) {
		model.addAttribute("presentations", presentations.getContent());
		model.addAttribute("pages", presentations.getTotalPages());
		model.addAttribute("pageNumber", presentations.getNumber());
	}

	@PostMapping("/upload")
	public String handleFileUpload(Model model, @Valid PresentationForm form, BindingResult bindingResult,
			HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			StringBuffer message = new StringBuffer();
			for (FieldError fe : bindingResult.getFieldErrors()) {
				message.append(fe.getDefaultMessage() + " \n");
			}
			if (form.getZipFile().getName().isEmpty()) {
				message.append("Невалиден файл\n");
			}
			model.addAttribute("message", message.toString());

			return "upload";
		}
		if (request.getSession().getAttribute("userID") == null) {
			return "redirect:/login";
		}
		if (form.getZipFile().getOriginalFilename().contains(".pptx")
				|| form.getZipFile().getOriginalFilename().contains(".ppt")) {
			try {
				presentationService.createPresentation(form, form.getZipFile().getInputStream(),
						(Long) request.getSession().getAttribute("userID"));
			} catch (IOException e) {
				model.addAttribute("message",
						"Неуспешно качване на презентация; Сигурни ли сте, че презентацията е с разширение .pptx или .ppt ?");
				e.printStackTrace();
				return "upload";
			}
		} else {
			try {
				File destination = presentationService.decompressZipToDestination(form.getZipFile());
				presentationService.createPresentations(destination, form, 1L);
			} catch (IOException e) {
				model.addAttribute("message",
						"Неуспешно разахивиран файл; Сигурни ли сте, че сте покрили горните критерии?");
				e.printStackTrace();
				return "upload";
			} catch (StringIndexOutOfBoundsException | NumberFormatException e) {
				model.addAttribute("message", "Грешна обработка на името на файла.");
				return "upload";
			}
		}

		return "redirect:/presentations";
	}

	private ClassLoader getContextClassLoader() {
		return Thread.currentThread().getContextClassLoader();
	}

	@GetMapping("/upload")
	public String getUploadPage(PresentationForm form) {
		return "upload";
	}

}
