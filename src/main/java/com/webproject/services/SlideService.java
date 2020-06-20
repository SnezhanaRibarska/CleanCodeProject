package com.webproject.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.webproject.models.Presentation;
import com.webproject.models.Slide;

public interface SlideService {

	public List<Slide> createSlides(InputStream fis, Presentation presentation) throws IOException;
	    
}
