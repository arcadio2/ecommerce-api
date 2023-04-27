package com.empresa.proyecto.services;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IFileService {

	public Resource cargar(String nombre) throws MalformedURLException;
	
	public Resource cargar(String nombre_producto,String color,String nombre) throws MalformedURLException;
	
	public boolean deleteImage(Long id) ;
	
	public String uploadImage(MultipartFile archivo) throws IOException;
	
	public String nombreUnico(String nombre);
	
	public Path getPath(String nombre); 
	
}
