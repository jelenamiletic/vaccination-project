package com.xml.vakcinacija.service.serviceImpl;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailSenderService {

	private JavaMailSender javaMailSender;

    @Autowired
    public EmailSenderService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    
    public MimeMessage createMimeMessage() {
    	Properties properties = new Properties();
    	properties.setProperty("mail.transport.protocol", "smtp");
    	properties.setProperty("mail.host", "smtp-mail.outlook.com");
    	properties.put("mail.smtp.starttls.enable", "true");
    	properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.port", "587");     
        Session session = Session.getDefaultInstance(properties, new Authenticator() {
        	@Override
        	protected PasswordAuthentication getPasswordAuthentication() {
        		return new PasswordAuthentication("mrs_isa_2021_t15_5@hotmail.com", "PassWord_FOR_MRS_ISA!");
        	}
		});
        MimeMessage mimeMessage = new MimeMessage(session);
        return mimeMessage;
    }

    @Async
    public void sendEmail(MimeMessage email) {
        javaMailSender.send(email);
    }
}
