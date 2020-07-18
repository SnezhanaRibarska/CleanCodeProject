package com.webproject.services;

import java.io.File;
import java.io.IOException;

public interface CloudinaryService {

	/**	
	* This method uploads a generated QR code 
	* The QR code will overwrite the existing code if it exists
	* @param  qrCode a File representation of the QR code
	* @return the generated URL
	* @see QRCode
	*/
	public String uploadToCloudinary(File qrCode) throws IOException;
	
}
