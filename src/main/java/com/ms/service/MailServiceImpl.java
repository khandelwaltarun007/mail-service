package com.ms.service;

import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamSource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.HtmlUtils;

@Service
public class MailServiceImpl implements MailService {

	private static final Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);

	@Autowired
	private JavaMailSender mailSender;

	@Override
	public void prepareMail(String to, String cc, String bcc, String subject, String text, int priority, MultipartFile[] attachments) {
		LOGGER.info("entry prepareMail");
		try {
		MimeMessagePreparator preparator = mimeMessage -> {
			MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, attachments.length!=0);
			messageHelper.setTo(to.split(";"));
			if (cc != null) {
				messageHelper.setCc(cc.split(";"));
			}
			if (bcc != null) {
				messageHelper.setBcc(bcc.split(";"));
			}
			messageHelper.setSubject(subject);
			if(!text.equals(HtmlUtils.htmlEscape(text))) {
				messageHelper.setText(text,true);
			}else {
				messageHelper.setText(text);
			}
			if(priority != 0) {
				messageHelper.setPriority(priority);
			}
			if(attachments.length!=0) {
				for(MultipartFile attachment : attachments) {
					messageHelper.addAttachment(attachment.getOriginalFilename(), new InputStreamSource() {
						@Override
						public InputStream getInputStream() throws IOException {
							return attachment.getInputStream();
						}
					});
				}
			}
		};
			mailSender.send(preparator);
		} catch (Exception e) {
			e.printStackTrace();
		}
		LOGGER.info("exit prepareMail");

	}
}
