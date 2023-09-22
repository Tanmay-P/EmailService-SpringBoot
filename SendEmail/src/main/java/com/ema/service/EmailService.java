package com.ema.service;


import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ema.entiy.EmailDetails;

@Service
public class EmailService {

	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	private String sender;

	public String sendSimpleMail(EmailDetails details) {

		try {
			SimpleMailMessage mailMessage = new SimpleMailMessage();
			mailMessage.setFrom(sender);
			mailMessage.setTo(details.getTo());
			mailMessage.setSubject(details.getSubject());
			mailMessage.setText(details.getBody());

			javaMailSender.send(mailMessage);

			return "Mail sent successfully.";
		} catch (Exception e) {
			return "Error while sending mail. \n Error : " + e.getMessage();
		}
	}

	public String sendMailWithAttachment(MultipartFile[] file,
											String to, 
											String subject, 
											String body) {

		try {
			MimeMessage mailMessage = javaMailSender.createMimeMessage();

			MimeMessageHelper helper = new MimeMessageHelper(mailMessage, true);
			
			helper.setFrom(sender);
			helper.setTo(to);
			helper.setSubject(subject);
			helper.setText(body);

			// FileSystemResource file = new FileSystemResource(new File(attachedFile));

			// adding attachment
			for (int i = 0; i < file.length; i++) {
				helper.addAttachment(
						file[i].getOriginalFilename(),
						new ByteArrayResource(file[i].getBytes()));
			}

			javaMailSender.send(mailMessage);

			return "Mail sent successfully.";
		} catch (Exception e) {
			return "Error while sending mail. \n Error : " + e.getMessage();
		}
	}

}
