package com.tutorial.cloudinaryrest.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryService {

	Cloudinary cloudinary;

	private Map<String, String> valuesMap = new HashMap<>();
	
	// Se conecta directamente a la cuenta de cloudinary
	public CloudinaryService() {
		valuesMap.put("cloud_name", "");
		valuesMap.put("api_key", "");
		valuesMap.put("api_secret", "");
		cloudinary = new Cloudinary(valuesMap);
	}
	
	public Map upload(MultipartFile multipartFile) throws IOException {
		File file = convert(multipartFile);
		
		// Metodo de cloudinary que se va devolver
		Map result = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
		file.delete();
		return result;
	}
	
	public Map delete(String id) throws IOException {
		Map result = cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
		return result;
	}
	
	// Para convertir multipart en un File
	private File convert(MultipartFile multipartFile) throws IOException {
		// Para ubuntu
		// File file = new File(multipartFile.getOriginalFilename());
		File file = new File(multipartFile.getOriginalFilename());
		FileOutputStream fo = new FileOutputStream(file);
		fo.write(multipartFile.getBytes());
		fo.close();
		return file;
	}
	
}
