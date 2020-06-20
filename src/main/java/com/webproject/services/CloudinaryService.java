package com.webproject.services;

import java.io.File;
import java.io.IOException;

public interface CloudinaryService {

	public String uploadToCloudinary(File qrCode) throws IOException;
	
}
