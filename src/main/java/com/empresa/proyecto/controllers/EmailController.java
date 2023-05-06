package com.empresa.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.proyecto.models.entity.Usuario;
import com.empresa.proyecto.models.service.IUsuarioService;
import com.empresa.proyecto.models.service.UsuarioService;
import com.empresa.proyecto.services.IEmailService;


import java.security.SecureRandom;


@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200/","*"})

public class EmailController {
	
	
	@Autowired
	IEmailService emailService;
	
	@Autowired
	IUsuarioService usuarioService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	private static String generarContrasena(int len) {
		
		// Rango ASCII – alfanumérico (0-9, a-z, A-Z)
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
 
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
 
        // cada iteración del bucle elige aleatoriamente un carácter del dado
        // rango ASCII y lo agrega a la instancia `StringBuilder`
 
        for (int i = 0; i < len; i++)
        {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
 
        return sb.toString();
	}
	
	@GetMapping("/mail")
	public void enviarMail() {
		//System.out.println("Esto se esta ejecutando");
		Usuario usuario = null;
		usuario = usuarioService.findByEmail("cristobalavalos09@gmail.com");
		System.out.println(usuario.getNombre());
		String nuevaContrasena = generarContrasena(10);
		usuario.setPassword(passwordEncoder.encode(nuevaContrasena));
		Usuario nuevoUsuario = usuarioService.save(usuario);
		emailService.send("shinesadecv170@gmail.com", "cristobalavalos09@gmail.com", "pruebasSpringBoot", "Tu nueva contraseña es: "+nuevaContrasena);
	}
	
	/*
    @PostMapping("/sendEmail")
    public String sendEmail(@RequestBody EmailRequest emailRequest) {
        emailService.sendSimpleMessage(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getText());
        return "Correo electrónico enviado";
    }*/
}