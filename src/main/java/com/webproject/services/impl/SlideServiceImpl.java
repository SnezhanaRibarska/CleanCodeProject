package com.webproject.services.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

import java.io.IOException;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.List;

import com.webproject.models.Presentation;
import com.webproject.models.Slide;
import com.webproject.services.CloudinaryService;
import com.webproject.services.SlideService;

import javax.imageio.ImageIO;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFSlide;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class SlideServiceImpl implements SlideService {

	@Value("${domain.name}")
	private String domainName;

	private CloudinaryService cloudinaryService;

	@Autowired
	public SlideServiceImpl(CloudinaryService cloudinaryService) {
		this.cloudinaryService = cloudinaryService;
	}

	public List<Slide> createSlides(InputStream fis, Presentation presentation) throws IOException {
		List<Slide> slides = new ArrayList<>();
		XMLSlideShow ppt = new XMLSlideShow(fis);
		List<XSLFSlide> xslfSlides = ppt.getSlides();
		for (int i = 0; i < xslfSlides.size(); i++) {
			String qrCodeUrl = cloudinaryService
					.uploadToCloudinary(QRCode.from(domainName + "/presentations/p/" + presentation.getId() + "/s/" + i)
							.to(ImageType.PNG).withSize(250, 250).file());
			;
			slides.add(Slide.builder().index(i).presentation(presentation).qrCodeUrl(qrCodeUrl)
					.imageUrl(createAndSaveSlideImage(ppt.getPageSize(), xslfSlides.get(i))).build());
		}
		return slides;
	}

	private String createAndSaveSlideImage(Dimension pageSize, XSLFSlide xslfSlide) throws IOException {
		BufferedImage slideImage = new BufferedImage(pageSize.width, pageSize.height, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = slideImage.createGraphics();
		graphics.setPaint(Color.white);
		graphics.fill(new Rectangle2D.Float(0, 0, pageSize.width, pageSize.height));
		xslfSlide.draw(graphics);
		File tempFile = File.createTempFile("temp", ".png");
		ImageIO.write(slideImage, "png", tempFile);
		return cloudinaryService.uploadToCloudinary(tempFile);

	}

}
