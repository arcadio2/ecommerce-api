package com.empresa.proyecto.services;


import javax.mail.MessagingException;

import org.springframework.core.io.InputStreamSource;

public interface IEmailService {
	public void send(String from, String to, String subject, String text);
	
	public void sendWithAttach(String from, String to, String subject,
            String text, String attachName,
            InputStreamSource inputStream) throws MessagingException;
}