package mth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mth.models.Users;
import mth.service.UsersService;

@RestController
@RequestMapping("/user")
public class UsersController {
	
	@Autowired
	UsersService US;
	
	@PostMapping
	public Object Signup(@RequestBody Users U) { 
		return US.signup(U);
		
	}
	

}
