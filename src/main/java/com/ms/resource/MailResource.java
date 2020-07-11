package com.ms.resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ms.model.Message;
import com.ms.service.MailService;

@RestController
public class MailResource {

	private static final Logger LOGGER = LoggerFactory.getLogger(MailResource.class);
	
	@Autowired
	private MailService mailService;
	
	@PostMapping("/service/mail")
	public void sendMail(@RequestBody Message message) {
		LOGGER.info("entry sendMail");
		mailService.prepareMail(message.getTo(), message.getCc(), message.getBcc(), message.getSubject(), message.getText());
		LOGGER.info("exit sendMail");
	}
	
}
