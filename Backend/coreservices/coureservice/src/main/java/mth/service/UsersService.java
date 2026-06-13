package mth.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import mth.models.Users;
import mth.repository.RoleRepository;
import mth.repository.UsersRepository;

@Service
public class UsersService {

	@Autowired
	UsersRepository UR;

	@Autowired
	RoleRepository RR;
	
	@Autowired
	JwtService JWT;
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
	public Object signin(Map<String, Object> data)
	  {
	    Map<String, Object> response = new HashMap<>();
	    try
	    {
	      Users U = (Users) UR.validateCredentials(data.get("username").toString(), data.get("password").toString());   //Validate user name and password
	      if(U != null)
	      {
	        response.put("code", 200);
	        response.put("jwt", JWT.generateJWT(data.get("username"), U.getRole(), U.getId())); //Generate JWT token and return as response
	      }
	      else
	      {
	        response.put("code", 404);
	        response.put("message", "Invalid Credentials!");
	      }
	    }catch(Exception e)
	    {
	      response.put("code", 500);
	      response.put("message", e.getMessage());
	    }
	    return response;
	  }
	public Object uinfo(String token)
    {
        Map<String, Object> response = new HashMap<>();
        try
        {
            Map<String, Object> payload = JWT.validateJWT(token);
            String email = (String) payload.get("username");
            Users U = (Users) UR.findByEmail(email);

            List<Object>menuList = UR.getMenus(Long.valueOf(U.getRole()));

            response.put("code", 200);
            response.put("fullname", U.getFullname());
            response.put("menuList",menuList);
        }catch(Exception e)
        {
            response.put("code", 500);
            response.put("message", e.getMessage());
        }
        return response;
    }
	
	public Object getProfile(String token)
	  {
	    Map<String, Object> response = new HashMap<>();
	    try
	    {
	      Map<String, Object> payload = JWT.validateJWT(token);
	          String email = (String) payload.get("username");
	          Object user = UR.profileByEmail(email);
	      
	          response.put("code", 200);
	          response.put("user", user);
	    }catch(Exception e)
	    {
	      response.put("code", 500);
	      response.put("message", e.getMessage());
	    }
	    return response;
	  }

	public Object getAllUsers(int page, int size, String token) {
		Map<String, Object> response = new HashMap<>();
		try {
			JWT.validateJWT(token);
			Pageable pageable = PageRequest.of(page -1,  size);
			Page<Users> users = UR.findAll(pageable);
			response.put("code", 200);
			response.put("page", page);
			response.put("size", size);
			response.put("totalpages", users.getTotalPages());
			response.put("users", users.getContent());
			response.put("roles", RR.findAll());
			
			
		} catch(Exception e) {
			response.put("code", 500);
			response.put("message", e.getMessage());
		}
		return response;
		
	}
	
	 public Object saveUser(Users U, String token)
	 {
		 Map<String,Object>response = new HashMap<>();
		 try
	        {
			 JWT.validateJWT(token);
				Object id=UR.checkByEmail(U.getEmail());
				if(id!=null)
					throw new Exception("Email ID already registered");
				UR.save(U);
				response.put("code", 200);
				response.put("message","new user account has been created .");
	  
	        }catch(Exception e)
	        {
	            response.put("code", 500);
	            response.put("message", e.getMessage());
	        }
	        return response; 
	 }
	 public Object updateUser(Long id, Users U, String token)
	 {
		 Map<String,Object>response = new HashMap<>();
		 try
	        {
			 JWT.validateJWT(token);
			 Users existingUser = UR.findById(id).orElseThrow(() -> new Exception("User not found"));
			 Object existingEmailId = UR.checkByEmail(U.getEmail());
			 if(existingEmailId != null && !Long.valueOf(existingEmailId.toString()).equals(id))
				 throw new Exception("Email ID already registered");
			 
			 existingUser.setFullname(U.getFullname());
			 existingUser.setPhone(U.getPhone());
			 existingUser.setEmail(U.getEmail());
			 existingUser.setPassword(U.getPassword());
			 existingUser.setRole(U.getRole());
			 existingUser.setStatus(U.getStatus());
			 UR.save(existingUser);
			 
			 response.put("code", 200);
			 response.put("message","User account has been updated.");
	        }catch(Exception e)
	        {
	            response.put("code", 500);
	            response.put("message", e.getMessage());
	        }
	        return response; 
	 }
	 public Object deleteuser( Long id,String token) {
		 Map<String, Object> response = new HashMap<>();
		 try {
			 JWT.validateJWT(token);
			 UR.deleteById(id);
			 response.put("code",200);
			 response.put("message", "user has been deleted");
			 
		 } catch(Exception e) {
			 response.put("code",  500);
			 response.put("message", e.getMessage());
		 }
		 return response;
	 }
	 public Object getUserById(Long id, String token)
	  {
	    Map<String, Object> response = new HashMap<>();
	    try
	    {
	      JWT.validateJWT(token); //Authorization
	      Users user = UR.findById(id).get();
	      
	          response.put("code", 200);
	          response.put("user", user);
	    }catch(Exception e)
	    {
	      response.put("code", 500);
	      response.put("message", e.getMessage());
	    }
	    return response;
	  }
	 public Object searchUser(String key, String token)
	  {
	    Map<String, Object> response = new HashMap<>();
	    try
	    {
	      JWT.validateJWT(token);
	      List<Object> users = UR.searchUser(key);
	      response.put("code", 200);
	      response.put("users", users);
	    }catch(Exception e)
	    {
	      response.put("code", 500);
	      response.put("message", e.getMessage());
	    }
	    return response;
	  }
}
