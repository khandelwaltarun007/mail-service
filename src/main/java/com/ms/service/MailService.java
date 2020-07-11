package com.ms.service;

import org.springframework.stereotype.Component;

@Component
public interface MailService {

	public void prepareMail(String to, String cc, String bcc, String subject, String text);

}
