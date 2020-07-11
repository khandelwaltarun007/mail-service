package com.ms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void prepareMail(String to, String cc, String bcc, String subject, String text) {
		LOGGER.info("entry prepareMail");
		MimeMessagePreparator preparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setTo(to);
			if (cc != null) {
				messageHelper.setCc(cc.split(";"));
			}
			if (bcc != null) {
				messageHelper.setBcc(bcc.split(";"));
			}
			messageHelper.setSubject(subject);
			messageHelper.setText(text);
		};
		try {
			mailSender.send(preparator);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("exit prepareMail");

	}
}
