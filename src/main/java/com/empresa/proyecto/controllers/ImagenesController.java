package com.empresa.proyecto.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.empresa.proyecto.models.entity.Perfil;
import com.empresa.proyecto.models.entity.Producto;
import com.empresa.proyecto.models.service.IProductoService;
import com.empresa.proyecto.services.IFileService;

@RestController
@RequestMapping("/api/imagenes")
@CrossOrigin(origins = {"http://localhost:4200/","*"})
public class ImagenesController {
	
	@Autowired 
	IFileService fileService; 
	
	@Autowired 
	IProductoService productoService; 
	/*
	@PostMapping("/imagen/upload")
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	public ResponseEntity<?> upload(@RequestParam(name = "file") MultipartFile archivo, @RequestParam("nombre") String nombre){
		
		Map<String, Object> response = new HashMap<>();
		Producto producto = productoService.getByNombre(nombre); 
		if(!archivo.isEmpty()) {
			String nombreArchivo = null;
			try {
				nombreArchivo = fileService.uploadImage(archivo);
			} catch (IOException e) {
				response.put("mensaje", "Error al subir la imagen");
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR); 
			
			}
			fileService.deleteImage(perfil.getId());
				 
			perfil.setFoto(nombreArchivo); 
			usuarioService.saveProfile(perfil);
			response.put("perfil", perfil);
			response.put("mensaje", "Se ha subidio correctamente la imagen "+nombreArchivo);
			
		}else {
			response.put("error", "Debe seleccionar una imagen");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST); 
		}
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK); 
	}*/
	
	
	@GetMapping("/productos/{nombre_producto}/{color}")
	public ResponseEntity<Map<String,Object>> obtenerFotos(@PathVariable String nombre_producto,
			@PathVariable String color) throws IOException{
		 
		 Map<String,Object> response = new HashMap<>(); 
		 
		 String nombre_final = nombre_producto.replace(' ', '_'); 
		 
		 
		 Path rutaArchivo = Paths.get("uploads/"+nombre_final+"/"+color);

	   
		 if (!Files.exists(rutaArchivo) || !Files.isDirectory(rutaArchivo)) {
		        // Manejar el caso en que la ruta no existe o no es un directorio
		        response.put("mensaje", "La ruta de archivos especificada no existe o no es un directorio");
		        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 
		    }

		    // Obtener una lista de todos los archivos en la ruta que tengan extensiones de imagen
		    List<Path> archivos = Files.list(rutaArchivo)
		                               .filter(path -> Files.isRegularFile(path) && path.getFileName().toString().matches(".*\\.(png|jpg|jpeg|gif)"))
		                               .collect(Collectors.toList());

		    // Crear una lista de URLs de los archivos encontrados
		    List<String> urls = archivos.stream()
                    .map(rutaArchivo::relativize)
                    .map(Path::toString)
                    .collect(Collectors.toList());

		    // Agregar la lista de URLs de imágenes al mapa de respuesta
		response.put("rutas", urls);
        response.put("mensaje", "Se han encontrado las rutas"); 

	
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK); 
	}
	
	
	@GetMapping("/productos/uploads/img/{nombre_producto}/{color}/{nombreFoto:.+}")
	public ResponseEntity<Resource> verFoto(
			@PathVariable String nombre_producto,
			@PathVariable String color,
			@PathVariable String nombreFoto){
		String nombre_final = nombre_producto.replace(' ', '_'); 
		  
		System.out.println("LLEGA AQUI");
		Path rutaArchivo = Paths.get("uploads/"+nombre_final+"/"+color).resolve(nombreFoto).toAbsolutePath();
		//Path rutaArchivo = Paths.get("uploads").resolve(nombreFoto).toAbsolutePath();
		Resource recurso = null; 
		try {
			recurso = fileService.cargar(nombre_final,color,nombreFoto); 
		} catch (MalformedURLException e) {
			recurso = null; 
			e.printStackTrace();
			return new ResponseEntity<Resource>(recurso,HttpStatus.NOT_FOUND);
			
		} 
 
		/*Para que se pueda descargar el recurso*/
		HttpHeaders cabecera = new HttpHeaders();
		cabecera.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\" "+recurso.getFilename()+"\" ");
		
		return new ResponseEntity<Resource>(recurso,cabecera,HttpStatus.OK);
	}
  

}
