package com.webproject.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.webproject.models.Presentation;
import com.webproject.models.Slide;
import com.webproject.models.User;
import com.webproject.repositories.UserRepository;
import com.webproject.services.CloudinaryService;

public class SlideServiceImplTest {

	private SlideServiceImpl slideService;
	
	@Mock
	private CloudinaryService cloudinaryService;
		
	@Mock
	private User testUser;
	
	@Mock
	private InputStream fis;
	
	@Mock
	private Presentation presentation;
	
	@Mock
	private Slide slide;
	
	@BeforeEach
	public void setUp() {
	    MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testCreateSlides() throws IOException {
		Mockito.when(fis.read(Mockito.anyObject())).thenReturn(1);
		slideService = new SlideServiceImpl(cloudinaryService);
		slideService.createSlides(fis, presentation);		
	}
}
