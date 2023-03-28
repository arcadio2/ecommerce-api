package com.empresa.proyecto.services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
	public String uploadImage(MultipartFile archivo) throws IOException {
		String nombreArchivo = this.nombreUnico(archivo.getOriginalFilename());
		Path rutaArchivo = this.getPath(nombreArchivo);
		Files.copy(archivo.getInputStream(), rutaArchivo);
		return nombreArchivo; 

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
}
