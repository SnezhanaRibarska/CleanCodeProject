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

	/**	
	* This method shows all presentations
	* @param  model   a holder for attributes
	* @param  page    a current page index
	* @param  tag     all existing tags
	* @return the page with all presentations
	*/
	@GetMapping
	public String getAllPresentations(Model model, @RequestParam(name = "page", defaultValue = "0") int page,
			@RequestParam(name = "tag", required = false) String tag) {
		addAttributes(presentationService.findAll(page, tag), model);
		model.addAttribute("tags", presentationService.getAllDistinctTags());
		model.addAttribute("selectedTag", tag);
		return "all-presentations";
	}

	/**	
	* This method gets all user presentations
	* @param  model   a holder for attributes
	* @param  userID  the id of the user for whom you want to take the presentations
	* @param  page    a current page index
	* @return user profile
	*/
	@GetMapping(value = "/u/{userID}")
	public String getPresentationsForUser(Model model, @PathVariable("userID") String userID,
			@RequestParam(name = "page", defaultValue = "0") int page) {
		addAttributes(presentationService.findAllForUser(userID, page), model);
		return "profile";
	}

	/**	
	* This method gets a presentation by id
	* @param  model   a holder for attributes
	* @param  presentationID  the id of the presentation
	* @return the presentation page
	*/
	@GetMapping("/p/{presentationID}")
	public String getPresentationForId(@PathVariable Long presentationID, Model model) {
		model.addAttribute("presentation", presentationService.getPresentationById(presentationID).get());
		return "presentation";
	}

	/**	
	* This method gets a slide from a presentation
	* @param  slideID         the id of the slide 
	* @param  presentationID  the id of the presentation
	* @return the slide page
	*/
	@GetMapping("/p/{presentationID}/s/{slideID}")
	public String getSlideForPresentation(@PathVariable("presentationID") Long presentationID,
			@PathVariable(value = "slideID") Integer slideID) {
		Presentation presentation = presentationService.getPresentationById(presentationID).get();
		return "redirect:" + presentation.getSlides().get(slideID).getImageUrl();
	}

	/**	
	* This method deletes a presentation
	* @param  presentationID  the id of the presentation
	* @param  model           a holder for attributes
	* @return the presentations without the one that has been deleted
	*/
	@GetMapping("/delete/p/{presentationID}")
	public String deletePresentationForId(@PathVariable Long presentationID, Model model) {
		presentationService.deletePresentationForId(presentationID);
		return getAllPresentations(model, 0, null);
	}

	/**	
	* This method exports all presentation in a zip file with name "presentations.zip"
	*/
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

	/**	
	* This method uploads a presentation or a zip file with presentations.
	* The file should be ".pptx" or ".ppt", if we want to upload one presentation and ".zip", if we want 
	* to upload a lot of presentations
	*/
	@PostMapping("/upload")
	public String handleFileUpload(Model model, @Valid PresentationForm form, BindingResult bindingResult,
			HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			StringBuffer message = new StringBuffer();
			for (FieldError fe : bindingResult.getFieldErrors()) {
				message.append(fe.getDefaultMessage() + " \n");
			}
			if (form.getZipFile().getName().isEmpty()) {
				message.append("Invalid file\n");
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
						"Failed to upload a presentation. Are you sure the presentation has an extension .pptx or .ppt ?");
				e.printStackTrace();
				return "upload";
			}
		} else {
			try {
				File destination = presentationService.decompressZipToDestination(form.getZipFile());
				presentationService.createPresentations(destination, form, 1L);
			} catch (IOException e) {
				model.addAttribute("message",
						"Failed to unzip a file. Are you sure you have met the above criteria?");
				e.printStackTrace();
				return "upload";
			} catch (StringIndexOutOfBoundsException | NumberFormatException e) {
				model.addAttribute("message", "Incorrect file name processing.");
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
