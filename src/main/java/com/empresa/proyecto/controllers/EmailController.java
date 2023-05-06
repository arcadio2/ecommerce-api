package com.empresa.proyecto.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.proyecto.services.IEmailService;




@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:4200/","*"})

public class EmailController {
	
	
	@Autowired
	IEmailService emailService;
	
	@GetMapping("/mail")
	public void enviarMail() {
		//System.out.println("Esto se esta ejecutando");
		emailService.send("shinesadecv170@gmail.com", "cristobalavalos09@gmail.com", "pruebasSpringBoot", "Hola mundito desde spring email(>w<)");
	}
	
	/*
    @PostMapping("/sendEmail")
    public String sendEmail(@RequestBody EmailRequest emailRequest) {
        emailService.sendSimpleMessage(emailRequest.getTo(), emailRequest.getSubject(), emailRequest.getText());
        return "Correo electrónico enviado";
    }*/
}