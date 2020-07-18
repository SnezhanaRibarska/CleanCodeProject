package com.webproject.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import com.webproject.models.Presentation;
import com.webproject.models.Slide;
import com.webproject.services.PresentationService;

public class PresentationControllerTest {

	private static final int PAGE_INDEX = 1;
	private static final String TAG = "cool";
	private static final int MAX_PAGES_SIZE = 100;
	private static final int PAGES_SIZE = 30;
	private static final String USER_ID = "1";
	private static final long PRESENTATION_ID = 1l;
	private static final int SLIDE_ID = 1;
	private static final String SLIDE_URL = "c:\\presentations";

	private PresentationController presentationController;
	private List<Presentation> presentationsAsList;
	
	@Mock
	private Page<Presentation> presentations;
	@Mock
	private PresentationService presentationService;
	@Mock
	private Presentation presentation;
	@Mock
	private Model model;
	@Mock
	private Slide slide;
	
	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		presentationController = new PresentationController(presentationService);
	}

	@Test
	public void testGetAllPresentations() throws IOException {
		presentationsAsList = new ArrayList<>();
		Mockito.when(presentationService.findAll(Mockito.anyInt(), Mockito.anyString())).thenReturn(presentations);
		Mockito.when(model.addAttribute(Mockito.anyString(), Mockito.anyObject())).thenReturn(model);
		mockPresentations();

		String allPresentationsViewPageName = presentationController.getAllPresentations(model, PAGE_INDEX, TAG);

		Assertions.assertEquals("all-presentations", allPresentationsViewPageName,
				"The expected presentations page is all-presentations, but the result is "
						+ allPresentationsViewPageName);
	}

	private void mockPresentations() {
		Mockito.when(presentations.getContent()).thenReturn(presentationsAsList);
		Mockito.when(presentations.getTotalPages()).thenReturn(MAX_PAGES_SIZE);
		Mockito.when(presentations.getNumber()).thenReturn(PAGES_SIZE);
	}
	
	@Test
	public void testGetPresentationsForUser() throws IOException {
		Mockito.when(presentationService.findAllForUser(USER_ID, PAGE_INDEX)).thenReturn(presentations);

		String userProfilePage = presentationController.getPresentationsForUser(model, USER_ID, PAGE_INDEX);

		Assertions.assertEquals("profile", userProfilePage,
				"The expected user profile page is pofile, but the result is "
						+ userProfilePage);
	}

	@Test
	public void testGetPresentationForId() throws IOException {
		Mockito.when(presentationService.getPresentationById(PRESENTATION_ID)).thenReturn(presentation);

		String presentationPage = presentationController.getPresentationForId(PRESENTATION_ID, model);

		Assertions.assertEquals("presentation", presentationPage,
				"The expected presentation page is presentation, but the result is "
						+ presentationPage);
	}
	
	@Test
	public void testGetSlideForPresentation() throws IOException {
		List<Slide> slides = new ArrayList<>();
		slides.add(slide);
		Mockito.when(presentationService.getPresentationById(PRESENTATION_ID)).thenReturn(presentation);
		Mockito.when(presentation.getSlides()).thenReturn(slides);
		
		Mockito.when(slide.getImageUrl()).thenReturn(SLIDE_URL);
		String slideUrl = presentationController.getSlideForPresentation(PRESENTATION_ID, SLIDE_ID);
		
		Assertions.assertTrue(slideUrl.contains(SLIDE_URL));
	}
	
}
