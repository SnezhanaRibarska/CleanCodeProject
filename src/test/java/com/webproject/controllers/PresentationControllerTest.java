package com.webproject.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;

import com.webproject.models.Presentation;
import com.webproject.services.PresentationService;

public class PresentationControllerTest {

	private static final int PAGE = 1;
	private static final String TAG = "cool";
	private static final int MAX_PAGES_SIZE = 100;
	private static final int PAGES_SIZE = 30;

	private PresentationController presentationController;
	private List<Presentation> presentationsAsList;
	
	@Mock
	private Page<Presentation> presentations;
	@Mock
	private PresentationService presentationService;
	@Mock
	private Model model;

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

		String allPresentationsViewPageName = presentationController.getAllPresentations(model, PAGE, TAG);

		Assertions.assertEquals("all-presentations", allPresentationsViewPageName,
				"The expected presentations page is all-presentations, but the result is "
						+ allPresentationsViewPageName);
	}

	private void mockPresentations() {
		Mockito.when(presentations.getContent()).thenReturn(presentationsAsList);
		Mockito.when(presentations.getTotalPages()).thenReturn(MAX_PAGES_SIZE);
		Mockito.when(presentations.getNumber()).thenReturn(PAGES_SIZE);
	}

}
