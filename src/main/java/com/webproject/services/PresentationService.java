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

	Page<Presentation> findAll(int pageIndex, String tag);

	Optional<Presentation> getPresentationById(Long id);

	Page<Presentation> findAll(int pageIndex);

	Page<Presentation> findAllForTag(String tag, int pageIndex);

	Page<Presentation> findAllForUser(String userId, int pageIndex);

	Set<String> getAllDistinctTags();

	void deletePresentationForId(Long presentationID);

	void exportAll();

	File decompressZipToDestination(MultipartFile zipFile) throws IOException;

	List<Presentation> createPresentations(File destination, PresentationForm input, Long userId)
			throws StringIndexOutOfBoundsException, NumberFormatException, IOException;

	Presentation createPresentation(PresentationForm form, InputStream fis, Long userId) throws IOException;

}
