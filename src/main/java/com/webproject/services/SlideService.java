package com.webproject.services;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import com.webproject.models.Presentation;
import com.webproject.models.Slide;

public interface SlideService {

	/**	
	* This method create slides
	* @param  fis          input stream for reading slides
	* @param  presentation the presentation to which the slides belong
	* @return list of created slides	
	*/
	public List<Slide> createSlides(InputStream fis, Presentation presentation) throws IOException;
	    
}
