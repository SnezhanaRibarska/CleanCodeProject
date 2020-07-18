package com.webproject.services.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.webproject.form.PresentationForm;
import com.webproject.models.Presentation;
import com.webproject.models.User;
import com.webproject.repositories.PresentationRepository;
import com.webproject.repositories.UserRepository;
import com.webproject.services.SlideService;

public class PresentationServiceImplTest {

	private PresentationServiceImpl presentationService;
	private List<Presentation> expectedPresentationsAsList;
	
	private static final int PAGE_INDEX = 1;
	private static final String TAG = "cool";
	private static final Long USER_ID = 1l;
	private static final String USER_ID_AS_STRING = "1";
	private static final Long PRESENTATION_ID = 1l;
	
	@Mock
	private Presentation presentation;
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
	@Mock
	private PageRequest pageRequest;
	@Mock
	private Page<Presentation> expectedPresentations;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		presentationService = new PresentationServiceImpl(presentationRepository, userRepository, slideService);		
	}
	
	@Nested
    @DisplayName("Tests for find methods")
    public class TestFindMethods {
 
		Page<Presentation> presentations;
			
		@BeforeEach
        public void beforeEach() {
        	Mockito.when(presentationRepository.findByTagsContaining(Mockito.anyString(), Mockito.any(PageRequest.class))).thenReturn(expectedPresentations);		
        	Mockito.when(presentationRepository.findAll(Mockito.any(PageRequest.class))).thenReturn(expectedPresentations);
        	Mockito.when(presentationRepository.findAll(Mockito.any(PageRequest.class))).thenReturn(expectedPresentations);
        	
        }
 
        @Test
        public void testFindAllWithTag() {
        	presentations = presentationService.findAll(PAGE_INDEX, TAG);
    		Mockito.verify(presentationRepository).findByTagsContaining(Mockito.anyString(), Mockito.any(PageRequest.class));
        }
        
        @Test
        public void testFindAllWithTagNull() {
        	presentations = presentationService.findAll(PAGE_INDEX, null);
    		Mockito.verify(presentationRepository, Mockito.times(0)).findByTagsContaining(Mockito.anyString(), Mockito.any(PageRequest.class));
    		Mockito.verify(presentationRepository).findAll(Mockito.any(PageRequest.class)); 	       
        }
        
        @Test
        public void testFindAllForTag() {
        	presentations = presentationService.findAllForTag(TAG, PAGE_INDEX);
    		Mockito.verify(presentationRepository).findByTagsContaining(Mockito.anyString(), Mockito.any(PageRequest.class));
        }
        
        @Test
        public void testFindAll() {
        	presentations = presentationService.findAll(PAGE_INDEX);
        	Mockito.verify(presentationRepository).findAll(Mockito.any(PageRequest.class)); 
        }
        
        @Test
        public void testFindAllForUser() {
        	presentations = presentationService.findAllForUser(USER_ID_AS_STRING, PAGE_INDEX);  		
    	}
        
        @AfterEach
        public void afterEach() {
            Assertions.assertEquals(expectedPresentations, presentations);
        }
    }
	
	@Test
	public void testGetAllDistinctTags() {
		expectedPresentationsAsList = new ArrayList<>();
		expectedPresentationsAsList.add(presentation);
		
		Mockito.when(presentation.getTags()).thenReturn(TAG);
		Mockito.when(presentationRepository.findAll()).thenReturn(expectedPresentationsAsList);
		Set<String> allTags = presentationService.getAllDistinctTags();
		
		Assertions.assertTrue(allTags.contains(TAG));
	}
	 
	@Test
	public void testDeletePresentationForId() {
		Mockito.doNothing().when(presentationRepository).deleteById(PRESENTATION_ID);
		presentationService.deletePresentationForId(PRESENTATION_ID);
		
		Mockito.verify(presentationRepository).deleteById(PRESENTATION_ID);
	}
	
	@Test
	public void testCreatePresentation() throws IOException {
		Mockito.when(form.getName()).thenReturn("CleanCodeLecture2");
		Mockito.when(form.getTags()).thenReturn("cool,nice presentation");
		Mockito.when(userRepository.getOne(USER_ID)).thenReturn(presentationOwner);
		Presentation presentation = presentationService.createPresentation(form, fis, USER_ID);

		Assertions.assertNotNull(presentation);
		Mockito.verify(presentationRepository).save(presentation);
		Mockito.verify(slideService).createSlides(fis, presentation);
	}
}
