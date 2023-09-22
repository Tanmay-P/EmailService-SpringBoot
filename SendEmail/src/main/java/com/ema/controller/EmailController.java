package com.ema.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ema.entiy.EmailDetails;
import com.ema.service.EmailService;

@RestController
public class EmailController {

	@Autowired
	private EmailService emailService;
	
	@PostMapping("/sendemail")
	public String sendSimpleMail(@RequestBody EmailDetails details) {
		return emailService.sendSimpleMail(details);
	}
	
	@PostMapping(value="/sendemailwithattachment")
	public String sendMailWithAttachment(@RequestParam(value="file", required=false) MultipartFile[] file, 
											String to, 
											String subject, 
											String body) {
		return emailService.sendMailWithAttachment(file, to, subject, body);
	}
}
