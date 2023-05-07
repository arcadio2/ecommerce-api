package com.empresa.proyecto.services;

import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;


@Service
public class EmailService implements IEmailService {
	
	private JavaMailSender mailSender;
	 
	@Autowired
	private Configuration config;
	 
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }
    @Override
    public void send(String from, String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
    @Override
    public void sendWithAttach(String from, String to, String subject,
                               String text, Map<String, Object> model) throws MessagingException{
    	
    	try {
    		MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            
            Template plantilla = config.getTemplate("email.html"); 
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(plantilla, model);
            
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            //helper.setText(html, true);
            message.setContent(html, "text/html; charset=utf-8");
            
            mailSender.send(message);
    	}catch(Exception e) {
	    	System.out.println("ha ocurrido un error inesperado");
	    	System.out.println(e.getMessage());
	    	System.out.println(e.getStackTrace());
    	}
    }
}
