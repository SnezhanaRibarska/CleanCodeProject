package com.webproject.services;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.webproject.form.PresentationForm;
import com.webproject.models.Presentation;

public interface PresentationService {

	/**	
	* This method finds all presentations by tag
	* @param  pageIndex    the index of the page
	* @param  tag          the tag of the presentation
	* @return all presentations on provided page index filtered by tag, or all presentations on provided page, if the tag is null
	*/
	Page<Presentation> findAll(int pageIndex, String tag);

	/**	
	* This method gets the presentation by id
	* @param  id    the id of the presentation
	* @return the presentation that corresponds to this id
	*/
	Optional<Presentation> getPresentationById(Long id);

	/**	
	* This method gets the presentation by page index
	* @param  pageIndex    the index of the page
	* @return all presentations on provided page index
	*/
	Page<Presentation> findAll(int pageIndex);

	/**	
	* This method finds all presentations by tag
	* @param  tag          the tag of the presentation. The tag can not be null
	* @param  pageIndex    the index of the page
	* @return all presentations on provided page index filtered by tag
	*/
	Page<Presentation> findAllForTag(String tag, int pageIndex);

	/**	
	* This method finds all presentations to a user
	* @param  userId       the id of the user
	* @param  pageIndex    the index of the page
	* @return all presentations to a user
	*/
	Page<Presentation> findAllForUser(String userId, int pageIndex);

	/**	
	* This method gets all distinct tags
	* @return all existing tags
	*/
	Set<String> getAllDistinctTags();

	/**	
	* This method deletes a presentation by id
	* @param the index of the presentation to be deleted
	*/
	void deletePresentationForId(Long presentationID);

	/**	
	* This method exports all presentation in a zip file with name "presentations.zip"
	*/
	void exportAll();

	/**	
	* This method imports all presentations from provided zip file
	* @param zipFile   a file with all presentation which we want to import
	*/
	File decompressZipToDestination(MultipartFile zipFile) throws IOException;

	/**	
	* This method creates presentations
	* @param destination the directory of presentations
	* @param input       the presentation form contains the name of the presentation, zipFile and tags
	* @param userId      the user who creates the presentation
	* @return list of presentations
	*/
	List<Presentation> createPresentations(File destination, PresentationForm input, Long userId)
			throws StringIndexOutOfBoundsException, NumberFormatException, IOException;

	/**	
	* This method creates a presentation  
	* @param form   the presentation form contains the title of the presentation, zipFile and tags
	* @param fis    input stream with presentation content
	* @param userId the user who creates the presentation
	* @return presentation
	*/
	Presentation createPresentation(PresentationForm form, InputStream fis, Long userId) throws IOException;

}
