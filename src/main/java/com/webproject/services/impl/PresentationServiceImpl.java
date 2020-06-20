package com.webproject.services.impl;

import org.springframework.stereotype.Service;
import com.webproject.form.PresentationForm;
import com.webproject.models.Presentation;
import com.webproject.repositories.PresentationRepository;
import java.io.*;
import java.nio.file.Files;
import java.util.*;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import com.webproject.repositories.UserRepository;
import com.webproject.services.PresentationService;
import com.webproject.services.SlideService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;
import org.zeroturnaround.zip.ZipUtil;

@Service
public class PresentationServiceImpl implements PresentationService {

	private static final int RESULTS_PER_PAGE = 10;

	private PresentationRepository presentationRepository;
	private UserRepository userRepository;
	private SlideService slideService;

	@Autowired
	public PresentationServiceImpl(PresentationRepository presentationRepository, UserRepository userRepository,
			SlideService slideService) {
		this.slideService = slideService;
	}

	public Page<Presentation> findAll(int pageIndex, String tag) {
		if (tag != null) {
			return findAllForTag(tag, pageIndex);
		} else {
			return findAll(pageIndex);
		}
	}

	public Optional<Presentation> getPresentationById(Long id) {
		return presentationRepository.findById(id);
	}

	public Page<Presentation> findAll(int pageIndex) {
		return presentationRepository.findAll(PageRequest.of(pageIndex, RESULTS_PER_PAGE));
	}

	public Page<Presentation> findAllForTag(String tag, int pageIndex) {
		return presentationRepository.findByTagsContaining(tag, PageRequest.of(pageIndex, RESULTS_PER_PAGE));
	}

	public Page<Presentation> findAllForUser(String userId, int pageIndex) {
		// all for user id
		return presentationRepository.findAll(PageRequest.of(pageIndex, RESULTS_PER_PAGE));
	}

	public Set<String> getAllDistinctTags() {
		Set<String> tags = new HashSet<>();
		List<Presentation> presentations = presentationRepository.findAll();
		presentations.stream().forEach(p -> tags.addAll(Arrays.asList(p.getTags().split(","))));
		return tags;
	}

	public void deletePresentationForId(Long presentationID) {
		presentationRepository.deleteById(presentationID);
	}

	public void exportAll() {
		ZipUtil.pack(new File("src/main/resources/files"), new File("src/main/resources/static/presentations.zip"));
	}

	public File decompressZipToDestination(MultipartFile zipFile) throws IOException {
		File destDir = createTempDirectory();
		byte[] buffer = new byte[1024];
		ZipInputStream zis = new ZipInputStream(zipFile.getInputStream());
		ZipEntry zipEntry = zis.getNextEntry();
		while (zipEntry != null) {
			File newFile = newFile(destDir, zipEntry);
			FileOutputStream fos = new FileOutputStream(newFile);
			int len;
			while ((len = zis.read(buffer)) > 0) {
				fos.write(buffer, 0, len);
			}
			fos.close();
			zipEntry = zis.getNextEntry();
		}
		zis.closeEntry();
		zis.close();
		return destDir;
	}

	private static File createTempDirectory() throws IOException {
		return Files.createTempDirectory("temp_folder").toFile();
	}

	private static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
		File destFile = new File(destinationDir, zipEntry.getName());

		String destDirPath = destinationDir.getCanonicalPath();
		String destFilePath = destFile.getCanonicalPath();

		if (!destFilePath.startsWith(destDirPath + File.separator)) {
			throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
		}

		return destFile;
	}

	public List<Presentation> createPresentations(File destination, PresentationForm input, Long userId)
			throws StringIndexOutOfBoundsException, NumberFormatException, IOException {
		File[] files = destination.listFiles();
		List<Presentation> presentations = new ArrayList<>();
		for (File file : files) {
			if (file.isFile()) {
				presentations.add(createPresentation(input, new FileInputStream(file), userId));
			}
		}
		return presentations;
	}

	public Presentation createPresentation(PresentationForm form, InputStream fis, Long userId) throws IOException {

		Presentation presentation = Presentation.builder().name(form.getName()).tags(form.getTags())
				.user(userRepository.getOne(userId)).build();
		presentationRepository.save(presentation);
		presentation.setSlides(slideService.createSlides(fis, presentation));
		presentationRepository.save(presentation);
		return presentation;
	}

}
