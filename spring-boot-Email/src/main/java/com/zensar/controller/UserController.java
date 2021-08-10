package com.zensar.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.zensar.model.MailRequest;
import com.zensar.model.MailResponse;
import com.zensar.service.EmailService;

@RestController

public class UserController {
   
	@Autowired
	private EmailService emailService;
	
	@PostMapping("/sendingEmail")
	public MailResponse register(@RequestBody MailRequest req) {
		Map<String,Object> model=new HashMap<>();
		model.put("Name",req.getName());
		model.put("location", "Bangalore,India");
	   System.out.println("Hii");
		 return emailService.sendEmail(req,model);
	}
}
