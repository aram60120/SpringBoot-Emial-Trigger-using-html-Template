package com.zensar.service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import com.zensar.model.MailRequest;
import com.zensar.model.MailResponse;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service
public class EmailService {
	@Autowired
	private Configuration config;
	@Autowired
	private JavaMailSender sender;

	public EmailService(Configuration configuration, JavaMailSender javaMailSender) {
		this.config = configuration;
		this.sender = javaMailSender;
	}

	public MailResponse sendEmail(MailRequest request,Map<String,Object> model) {
		MailResponse res=new MailResponse();
		MimeMessage message= sender.createMimeMessage();
		try {
			//Set Mediatype
		MimeMessageHelper helper=	new MimeMessageHelper
				(message,MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,StandardCharsets.UTF_8.name());
		//add Attachment
		helper.addAttachment("google_gmail.png", new ClassPathResource("google_gmail.png"));
		 Template t= config.getTemplate("email-template.ftl");
		 String html=FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
		 helper.setTo(request.getTo());
		 helper.setText(html,true);
		 helper.setSubject(request.getSubject());
		 helper.setFrom(request.getFrom());
		 sender.send(message);
		 res.setMessage(" Mail  send to :"+ request.getTo() );
		 res.setStatus(Boolean.TRUE);
		 		
		} catch ( MessagingException | IOException | TemplateException  e) {
			res.setMessage(" Mail sending failure : "+e.getMessage());
			res.setStatus(Boolean.FALSE);
			
		}
		return res;
	}
}
	