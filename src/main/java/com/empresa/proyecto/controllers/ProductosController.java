package com.empresa.proyecto.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.security.PermitAll;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.empresa.proyecto.models.dao.IDetalleProductoDao;
import com.empresa.proyecto.models.entity.Bolsa;
import com.empresa.proyecto.models.entity.CategoriaProducto;
import com.empresa.proyecto.models.entity.ComentarioValoracion;
import com.empresa.proyecto.models.entity.Comentarios;
import com.empresa.proyecto.models.entity.ComentariosDto;
import com.empresa.proyecto.models.entity.DetalleProducto;
import com.empresa.proyecto.models.entity.DetalleProductoDto;
import com.empresa.proyecto.models.entity.Perfil;
import com.empresa.proyecto.models.entity.Producto;
import com.empresa.proyecto.models.entity.Talla;
import com.empresa.proyecto.models.service.IBolsaService;
import com.empresa.proyecto.models.service.ICategoriaProductoService;
import com.empresa.proyecto.models.service.IComentariosService;
import com.empresa.proyecto.models.service.IDetalleProductoService;
import com.empresa.proyecto.models.service.IProductoService;
import com.empresa.proyecto.models.service.IUsuarioService;
import com.empresa.proyecto.models.service.ItallaService;
import com.empresa.proyecto.services.IFileService;
import com.empresa.proyecto.services.IValidationService;


@RestController
@RequestMapping("/api/productos")
@CrossOrigin(origins = {"http://localhost:4200/","*"})
public class ProductosController {
	
	@Autowired 
	IProductoService productoService; 
	
	@Autowired
	IComentariosService comentarioService; 
	
	@Autowired
	IUsuarioService usuarioService; 
	
	@Autowired
	IDetalleProductoService detalleService; 
	
	@Autowired
	IBolsaService bolsaService; 
	
	@Autowired 
	ItallaService tallaService; 

	
	@Autowired
	IValidationService validationService; 
	
	@Autowired 
	ICategoriaProductoService categoriaService; 
	
	@Autowired
	IFileService fileService; 
	
	
	@GetMapping("")
	//@ResponseStatus(HttpStatus.OK)
	public List<Producto> getProductos(){
		List<Producto> productos = productoService.findAll(); 
		
		return productos; 

	}

	@GetMapping("/novedades")
	//@ResponseStatus(HttpStatus.OK)
	public List<Producto> getProductosNovedades(){
		List<Producto> productos = productoService.getNovedades(); 
		
		return productos; 

	}
	@GetMapping("/get/{nombre}")
	//@ResponseStatus(HttpStatus.OK)
	public List<Producto> getProductosNombre(@PathVariable String nombre){
		List<Producto> productos = productoService.findByNombre(nombre); 
		

		return productos; 
	}  
	
	@GetMapping("/listado")
	//@ResponseStatus(HttpStatus.OK)
	@PermitAll
	public ResponseEntity<?> getSimilares(@RequestParam(required = false) String nombre,
										@RequestParam(required = false) String categoria,
										@RequestParam(required = false) String genero,
										@RequestParam(required = false) String color,
										@RequestParam(required = false) String talla,
										Authentication auth){
		Map<String, Object> response = new HashMap<>();
		System.out.println("Nombre "+nombre);
		System.out.println("Categoria "+categoria);
		System.out.println("Genero "+genero);
		
		if(nombre==null) {
			System.out.println("Hola");
		}
		
		if(!nombre.equals("null") && categoria.equals("null") && genero.equals("null")) {
			Producto encontrado = null; 
			try {
				
					encontrado = productoService.getByNombre(nombre); 
				
				/*if(nombre == null && categoria!=null && genero!=null) {
					encontrado = productoService.getByCategoria(categoria); 
				}
				if(nombre== null && categoria==null && genero!=null) {
					
				}*/
				
				
				
			}catch(Exception e) {
				response.put("error", "No se encontró el producto"); 
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 	
			}
			String categoria_obtenida; 
			List<String> tallas,colores = new ArrayList<>();
			tallas = encontrado.getDetalle().stream().map(d->d.getTalla().getTalla()).collect(Collectors.toList());
			colores = encontrado.getDetalle().stream().map(d->d.getColor().getColor()).collect(Collectors.toList());
			
			System.out.println("XCD"); 
			System.out.println(tallas);
			System.out.println(colores);
			categoria_obtenida = encontrado.getCategoria().getTipo(); 
			System.out.println(categoria_obtenida);
			//talla = encontrado.getDetalle().
			
			List<Producto> productos = productoService.getByColorOrTallaOrCategoria(tallas,colores,categoria_obtenida); 
			
			response.put("success", "Se han encontrado los productos"); 
			response.put("productos", productos); 
			
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK); 
		}else {
			try {
				if(nombre.equals("null") && !categoria.equals("null") && genero.equals("null")) {
					System.out.println("CHALEEEEE ");
					List<Producto> productos = null; 
					
			
					if(categoria.equalsIgnoreCase("Para ti")) {
						String username = auth.getName(); 
						Perfil perfil = usuarioService.getProfileByUsername(username);
						boolean isHombre = true ? perfil.getSexo().getSexo().equalsIgnoreCase("Hombre") : false; 
						Talla talla_s = tallaService.findById(perfil.getTalla_camisa().longValue());  
						
						String talla_camisa = talla_s.getTalla(); 
						String talla_pantalon = ((int) perfil.getTalla_pantalon().doubleValue())+"";
						
						System.out.println(isHombre+" "+talla_camisa+" "+talla_pantalon);
						
						productos = productoService.getByPrefil(isHombre, talla_pantalon, talla_camisa); 
					}else if(categoria.equalsIgnoreCase("Novedades")) {
			
						productos = productoService.getNovedades(); 
					}else {
		
						productos = productoService.getByCategoria(categoria); 
					}
					response.put("success", "Se han encontrado los productos"); 
					response.put("productos", productos); 
					return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK); 
				}
				if(nombre.equals("null") && categoria.equals("null") && !genero.equals("null")) {
					List<Producto> productos=null;
					if(genero.equalsIgnoreCase("Hombre")) {
						productos = productoService.getBySexo(true); 
					}else {
						productos = productoService.getBySexo(false); 
					}
					 
					response.put("success", "Se han encontrado los productos"); 
					response.put("productos", productos); 
					return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK); 
				}
				if(nombre.equals("null") && !categoria.equals("null") && !genero.equals("null")) {
				
					List<Producto> productos=null;
					if(genero.equalsIgnoreCase("Hombre")) {
						productos = productoService.getBySexoAndCategoria(true,categoria); 
					}else {  
						productos = productoService.getBySexoAndCategoria(false,categoria); 
					} 
					//List<Producto> productos = productoService.getByCategoria(categoria);

					response.put("success", "Se han encontrado los productos"); 
					response.put("productos", productos); 
					return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK); 
				} 
			}catch(Exception e) { 
				response.put("error", "No se encontró el producto"); 
				System.out.println(e.getMessage());
				return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 	
			}
			
		}
		response.put("error", "Ha ocurrido un error"); 
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	 
	@GetMapping("/producto/{producto}")
	public ResponseEntity<?> getProducto(@PathVariable String producto){
		Map<String, Object> response = new HashMap<>();

		Producto producto_response = null; 
		try {
			producto_response = productoService.getByNombre(producto); 
			
			
		}catch(Exception e) {
			response.put("error", "No se encontró el producto"); 
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 	
		}
		if(producto_response==null) {
			response.put("error", "No se encontró el producto"); 
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 	
		}
		
		response.put("mensaje", "Se ha encontrado el producto"); 
		response.put("producto", producto_response); 
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK); 	
	}
	
	
	
	
	
	@PostMapping("/create")
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@ResponseStatus(HttpStatus.CREATED)//201
	public ResponseEntity<?> createProducto(@RequestBody @Valid Producto producto, BindingResult result){
	
		Map<String, Object> response = new HashMap<>();
		producto.setId(null);
		if(result.hasErrors()) { 

			response = validationService.responseErrors(result);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}  
		System.out.println(producto.getNombre() + "XDJKCSBCASJCJSA");
		
		try {
			Producto prodcuto_saved = productoService.save(producto); 
			response.put("mensaje", "El producto ha sido agregado con éxito");
			response.put("producto",prodcuto_saved); 
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
			
		}catch(Exception e) {
			response.put("Error", "Ha ocurrido un error al guardar \n"+e.getCause());
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 	
		}
	
	}
	

	@PutMapping("/edit") 
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	@ResponseStatus(HttpStatus.CREATED)//201
	public ResponseEntity<?> editProducto(@RequestBody @Valid Producto producto, BindingResult result){
	
		Map<String, Object> response = new HashMap<>();
		if(result.hasErrors()) {

			response = validationService.responseErrors(result);
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}  
	
		Producto producto_encontrado = null;  
		try {
			producto_encontrado = productoService.findById(producto.getId()); 
			producto_encontrado.setPrecio(producto.getPrecio()); 
			producto_encontrado.setDescripcion(producto.getDescripcion()); 
			producto_encontrado.setNombre(producto.getNombre()); 
		}catch (Exception e){ 
			response.put("Error", "Ha ocurrido un error al editar \n"+e.getCause());
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 	
		}
			
		
		try {
			Producto prodcuto_saved = productoService.save(producto_encontrado); 
			response.put("mensaje", "El producto ha sido agregado con éxito");
			response.put("producto",prodcuto_saved); 
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
			
		}catch(Exception e) {
			response.put("Error", "Ha ocurrido un error al guardar \n"+e.getCause());
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR); 	
		}
	
	}
	
	@DeleteMapping("/delete/{producto}")
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	public ResponseEntity<?> deleteProducto(@PathVariable String producto){
		Map<String, Object> response = new HashMap<>();
		
		System.out.println(producto+"XDDD");
		Producto producto_obtenido = null;
		
		try {
			producto_obtenido = productoService.getByNombre(producto); 
			
		 
		}catch(Exception e) {
			response.put("mensaje", "Ha ocurrido un error al econtrar el producto");
			response.put("error", e.getMessage()); 
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		System.out.println(producto_obtenido);
		
		try {
			productoService.delete(producto_obtenido); 
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
			response.put("mensaje", "No se ha podido borrar el producto");
			response.put("error", e.getMessage()); 
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
		}
		response.put("mensaje","El producto se ha eliminado con exito");
		
		
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK);
		
	}
	
	/**DETALLE PRODUCTO**/
	
	
	
	
	/*BOLSA*/
	@PutMapping("/detalle")
	@Secured({"ROLE_ADMIN","ROLE_USER"})
	public ResponseEntity<?> changeDetalle(@RequestBody DetalleProducto detalle){
		Map<String, Object> response = new HashMap<>();
		
		DetalleProducto detalle_response = null; 
		try {
			detalle_response = detalleService.save(detalle); 
	
		}catch(Exception e) {
			response.put("error", "No se encontró el producto");  
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 	
		}
		if(detalle_response==null) {
			response.put("error", "No se encontró el producto"); 
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 	
		}
		response.put("mensaje", "Se ha encontrado el producto"); 
		response.put("detalle", detalle_response); 
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK); 	
	}
	
	

	/*
	 * CATEGORIA
	 * */
	@GetMapping("/categoria/{sexo}")
	public List<CategoriaProducto> obetenerCategoriasBySexo(@PathVariable String sexo){
		System.out.println("SEX "+sexo);
		return categoriaService.finBySexo(sexo);  
	}
	
	
	/**
	 * SUBIR FOTO
	 */
	
	@PostMapping("/productos/upload")
	@Secured({"ROLE_ADMIN"})
	public ResponseEntity<?> upload(@RequestParam(name = "file") MultipartFile[] archivo, 
			@RequestParam("id_producto") String id,@RequestParam("color") String color){
		Long id_producto = Long.parseLong(id);
		System.out.println("ENTRAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		System.out.println(archivo.length);
		System.out.println(id+" "+color);
		
		Map<String, Object> response = new HashMap<>();
		Producto producto = productoService.findById(id_producto); 
		if(archivo.length!=0) {
			String nombreArchivo = null;
			try {
				
				//for(int i=0;i<archivo.length;i++) {
					nombreArchivo = fileService.uploadImage(archivo,id,color);
				//}
				
			} catch (IOException e) {
				response.put("mensaje", "Error al subir la imagen");
				return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR); 
			
			}
			//fileService.deleteImage(producto.getId());
				 
			//producto.setFoto(nombreArchivo); 
			//usuarioService.saveProfile(producto);
			response.put("producto", producto);
			response.put("mensaje", "Se ha subidio correctamente la imagen "+nombreArchivo);
			
		}else {
			response.put("error", "Debe seleccionar una imagen");
			return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST); 
		}
		return new ResponseEntity<Map<String,Object>>(response,HttpStatus.OK); 
	}
	
	/*
	@Secured({"permitAll()"})
	@GetMapping("/get/detalle/{detalle_id}")
	public ResponseEntity<?> getComentariosByU(@PathVariable Long producto_id){
		Map<String, Object> response = new HashMap<>();
		Producto producto = null;  
		try {
			comentario = comentarioService.getByUsernameAndPerfil(username,producto_id);
		}catch(Exception e) {
			response.put("error", "No se encontró el comentario"); 
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 	
		}
		if(comentario == null) {
			response.put("error", "No se ha encontrado el comentario"); 
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 	
		}
		response.put("mensaje", "Se ha encontrado el comentario"); 
		response.put("valoracion", comentario); 
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK); 	
	}*/

	/*
	@GetMapping("/get/detalle/{detalle_id}")
	@Secured({"ROLE_ADMIN","ROLE_USER","ROLE_INSTRUCTOR"})
	public ResponseEntity<?> getComentariosByU(@PathVariable Long producto_id){
		Map<String, Object> response = new HashMap<>();
		Producto producto = null;  
		try {
			comentario = comentarioService.getByUsernameAndPerfil(username,producto_id);
		}catch(Exception e) {
			response.put("error", "No se encontró el comentario"); 
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 	
		}
		if(comentario == null) {
			response.put("error", "No se ha encontrado el comentario"); 
			return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND); 	
		}
		response.put("mensaje", "Se ha encontrado el comentario"); 
		response.put("valoracion", comentario); 
		
		return new ResponseEntity<Map<String,Object>>(response, HttpStatus.OK); 	
	}*/
	
}
