package com.webproject.services.impl;

import java.io.IOException;
import java.io.InputStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.webproject.form.PresentationForm;
import com.webproject.models.Presentation;
import com.webproject.models.User;
import com.webproject.repositories.PresentationRepository;
import com.webproject.repositories.UserRepository;
import com.webproject.services.SlideService;

@SpringBootTest
public class PresentationServiceImplTest {

	private PresentationServiceImpl presentationService;

	@Mock
	private PresentationRepository presentationRepository;
	@Mock
	private UserRepository userRepository;
	@Mock
	private SlideService slideService;
	@Mock
	private PresentationForm form;
	@Mock
	private InputStream fis;
	@Mock
	private User presentationOwner;

	private static final Long userId = 1l;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testCreatePresentation() throws IOException {
		presentationService = new PresentationServiceImpl(presentationRepository, userRepository, slideService);

		Mockito.when(form.getName()).thenReturn("CleanCodeLecture2");
		Mockito.when(form.getTags()).thenReturn("cool,nice presentation");
		Mockito.when(userRepository.getOne(userId)).thenReturn(presentationOwner);
		Presentation presentation = presentationService.createPresentation(form, fis, userId);

		Assertions.assertNotNull(presentation);
		Mockito.verify(presentationRepository.save(presentation), Mockito.times(1));
		Mockito.verify(slideService.createSlides(fis, presentation), Mockito.times(1));
	}

}
