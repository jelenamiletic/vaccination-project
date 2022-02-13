package com.xml.vakcinacija.service.serviceImpl;

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
    	return javaMailSender.createMimeMessage();
    }

    @Async
    public void sendEmail(MimeMessage email) {
        javaMailSender.send(email);
    }
}
