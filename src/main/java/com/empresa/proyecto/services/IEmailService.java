package com.empresa.proyecto.services;


import java.util.Map;

import javax.mail.MessagingException;

import org.springframework.core.io.InputStreamSource;

public interface IEmailService {
	public void send(String from, String to, String subject, String text);
	
	public void sendWithAttach(String from, String to, String subject,
            String text, Map<String, Object> model) throws MessagingException;
}