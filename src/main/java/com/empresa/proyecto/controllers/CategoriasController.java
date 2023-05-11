package com.empresa.proyecto.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.proyecto.models.entity.CategoriaProducto;
import com.empresa.proyecto.models.entity.Color;
import com.empresa.proyecto.models.entity.Talla;
import com.empresa.proyecto.models.service.ICategoriaProductoService;
import com.empresa.proyecto.models.service.IColoresService;
import com.empresa.proyecto.models.service.ItallaService;

@RestController
@RequestMapping("/api/categorias")
public class CategoriasController {
	
	@Autowired
	private IColoresService coloresService; 
	
	@Autowired 
	private ItallaService tallasService; 
	
	@Autowired
	private ICategoriaProductoService categoriaService; 
	
	
	@GetMapping("/colores")
	public ResponseEntity<Map<String, Object>> obtenerColores(){
		Map<String, Object> response = new HashMap<>();
		
		List<Color> colores = null; 
		try {
			colores = coloresService.findAll(); 
		}catch(Exception e) {
			response.put("mensaje", "Ha ocurrido un error"); 
			response.put("error", e.getMessage()); 
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		if(colores.size()==0) {
			response.put("mensaje", "no se han podido encontrar los colores"); 
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		response.put("mensaje", "se han encotrado los colores"); 
		response.put("colores", colores); 
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK); 

	}
	
	@GetMapping("/tallas/{isSuperior}")
	public ResponseEntity<Map<String, Object>> obtenertallas(@PathVariable boolean isSuperior){
		Map<String, Object> response = new HashMap<>();
		
		List<Talla> tallas = null; 
		try {
			tallas = tallasService.findByTronco(isSuperior); 
		}catch(Exception e) {
			response.put("mensaje", "Ha ocurrido un error"); 
			response.put("error", e.getMessage()); 
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		if(tallas.size()==0) {
			response.put("mensaje", "no se han podido encontrar las tallas"); 
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		response.put("mensaje", "se han encotrado las tallas"); 
		response.put("tallas", tallas); 
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK); 

	}
	@GetMapping("/tallas")
	public ResponseEntity<Map<String, Object>> obtenerTallas(){
		Map<String, Object> response = new HashMap<>();
		
		List<Talla> tallas = null; 
		try {
			tallas = tallasService.findAll(); 
		}catch(Exception e) {
			response.put("mensaje", "Ha ocurrido un error"); 
			response.put("error", e.getMessage()); 
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		if(tallas.size()==0) {
			response.put("mensaje", "no se han podido encontrar las tallas"); 
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		response.put("mensaje", "se han encotrado las tallas"); 
		response.put("tallas", tallas); 
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK); 

	}
	
	@GetMapping("/categorias")
	public ResponseEntity<Map<String, Object>> obtenerCategorias(){
		Map<String, Object> response = new HashMap<>();
		
		List<CategoriaProducto> categorias = null;
		try {
			categorias = categoriaService.findAll();  
		}catch(Exception e) {
			response.put("mensaje", "Ha ocurrido un error"); 
			response.put("error", e.getMessage());  
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 
		}
		if(categorias.size()==0) {
			response.put("mensaje", "no se han podido encontrar las categorias"); 
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 
		}
		
		response.put("mensaje", "se han encotrado las categorias"); 
		response.put("categorias", categorias); 
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK); 

	}
	
	

}
