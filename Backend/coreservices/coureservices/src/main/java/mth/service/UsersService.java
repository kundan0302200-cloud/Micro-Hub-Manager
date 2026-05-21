package mth.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mth.models.Users;
import mth.repository.UsersRepository;

@Service
public class UsersService {

	@Autowired
	UsersRepository UR;
	public Object signup(Users U)
	  {
	    Map<String, Object> response = new HashMap<>();
	    try
	    {
	      Object id = UR.checkByEmail(U.getEmail());
	      if(id != null)
	      {        
	        response.put("code", 501);
	        response.put("message", "Email ID already registered");
	      }
	      else
	      {
	        U.setRole(1);    //Setting default role to the new user
	        U.setStatus(1);    //Make the status of the user as active
	        
	        UR.save(U);      //Insert into the database table (users)
	        
	        response.put("code", 200);
	        response.put("message", "User account has been created.");
	      }
	    }catch(Exception e)
	    {
	      response.put("code", 500);
	      response.put("message", e.getMessage());
	    }
	    return response;
	  }
}
