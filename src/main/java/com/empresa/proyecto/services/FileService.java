package com.empresa.proyecto.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.empresa.proyecto.models.entity.Perfil;
import com.empresa.proyecto.models.service.IUsuarioService;



@Service
public class FileService implements IFileService{
	private final static String DIR_UPLOAD="uploads"; 
	@Autowired
	private IUsuarioService usuarioService;
	


	@Override
	public String nombreUnico(String nombre) {
		return UUID.randomUUID().toString() + "_"+ nombre.replace(" ", "");
	}

	@Override
	public Path getPath(String nombre) {
		return Paths.get(DIR_UPLOAD).resolve(nombre).toAbsolutePath();
	}
	
	@Override
	public Path getPath(String nombre, String color, String id) {
		
		 Path folderPath = Paths.get(DIR_UPLOAD, id, color); // ruta de la carpeta que contendrá el archivo
		    if (!Files.exists(folderPath)) { // si la carpeta no existe, la creamos
		        try {
		            Files.createDirectories(folderPath);
		        } catch (IOException e) {
		            throw new RuntimeException("No se pudo crear la carpeta de subida", e);
		        }
		    }
		    return folderPath.resolve(nombre).toAbsolutePath(); // retornamos la ruta completa del archivo
	}
	
	
	

	@Override
	public boolean deleteImage(Long id) {
		Perfil perfil = usuarioService.getProfileById(id); 
		String nombreFotoAnterior = perfil.getFoto();
		if(nombreFotoAnterior !=null && nombreFotoAnterior.length()>0)  {
			Path rutaAnterior = this.getPath(nombreFotoAnterior);
			File archivoAnterior = rutaAnterior.toFile();
			if(archivoAnterior.exists() && archivoAnterior.canRead()) {
				archivoAnterior.delete();
				return true;
			}
		}
		return false; 
	}



	@Override
	public String uploadImage(MultipartFile[] archivo,String id,String color) throws IOException {
		//String nombreArchivo = this.nombreUnico(archivo.getOriginalFilename());
		Path rutaCarpeta = this.getPath("1.jpg", color, id);
		  // Eliminar carpeta y crearla de nuevo
	    Path carpeta = rutaCarpeta.getParent();
	    if (Files.exists(carpeta) && Files.isDirectory(carpeta)) {
	        try {
	            Files.walk(carpeta)
	                 .sorted(Comparator.reverseOrder())
	                 .forEach(archivoExistente -> {
	                    try {
	                        Files.delete(archivoExistente);
	                    } catch (IOException e) {
	                        throw new RuntimeException("No se pudo eliminar el archivo " + archivoExistente.getFileName(), e);
	                    }
	                 });
	            Files.createDirectories(carpeta);
	        } catch (IOException e) {
	            throw new RuntimeException("No se pudo eliminar o crear la carpeta de subida", e);
	        }
	    }
		for(int i=0;i<archivo.length;i++) {
			 Path rutaArchivo = this.getPath((i+1)+".jpg", color, id);
			 Files.copy(archivo[i].getInputStream(), rutaArchivo); 
		}
		 
		    

		
		    
		    
		    return id;
		
	} 


 
	@Override
	public Resource cargar(String nombre) throws MalformedURLException {
		Path rutaArchivo = Paths.get("uploads").resolve(nombre).toAbsolutePath();
		Resource recurso = null; 
		recurso = new UrlResource(rutaArchivo.toUri());
		if(!recurso.exists() && !recurso.isReadable()) {
			rutaArchivo = Paths.get("src/source/static/images").resolve("no_user.png").toAbsolutePath();
			recurso = new UrlResource(rutaArchivo.toUri());			
			System.out.println("Error, no se pudo cambiar la imagen "+nombre);
		}
		return recurso;
	}

	@Override 
	public Resource cargar(String nombre_producto, String color, String nombre) throws MalformedURLException {
		Path rutaArchivo = Paths.get("uploads/"+nombre_producto+"/"+color).resolve(nombre).toAbsolutePath();
		Resource recurso = null; 
		
		recurso = new UrlResource(rutaArchivo.toUri());
		if(!recurso.exists() && !recurso.isReadable()) {
			rutaArchivo = Paths.get("uploads/images").resolve("no_photo.png").toAbsolutePath();
			recurso = new UrlResource(rutaArchivo.toUri());			
			System.out.println("Error, no se pudo cambiar la imagen "+nombre);
		}
		return recurso; 
	}

	
}
