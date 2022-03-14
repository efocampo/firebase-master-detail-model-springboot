package com.eocampo.models.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.eocampo.models.entity.Role;
import com.eocampo.models.entity.User;
import com.eocampo.models.entity.UserPagination;
import com.eocampo.models.services.IUsuarioService;
import com.eocampo.util.EnumRol;
import com.google.api.gax.rpc.FailedPreconditionException;

@CrossOrigin(origins = {"http://localhost:4200","*"})
@RestController
@RequestMapping("/api/user")
public class UserRestController {
	@Autowired
	private IUsuarioService usuarioService;
	
	@PostMapping("/create")
	public ResponseEntity<?> create(@RequestBody User user) {

		Map<String, Object> response = new HashMap<String, Object>();
		User userCreated = null;
		boolean userRepeated = false;

		try {
			//Setting user name in lower case
			user.setTxt_user_name(user.getTxt_user_name().toLowerCase());
			
			try {
				userRepeated = usuarioService.findByNomUserValidate(user.getTxt_user_name());
			}catch (ExecutionException e) {
				userRepeated = false;
			}

			if (userRepeated == false) {
				userCreated = usuarioService.create(user);
				
				//Assigning default role
				if (userCreated != null && userCreated.getLstRole()!= null 
						&& userCreated.getLstRole().size()==0) {
					usuarioService.updateRole(user.getCod_id_user().toString(),
							new Role("1", EnumRol.USER.getRole()),false);
				}
			}else {
				response.put("user", null);
				response.put("message", "The user name is repeated");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
			}
		} catch (InterruptedException ie) {
			response.put("message","db error" );
			response.put("error", ie.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (ExecutionException ee) {
			response.put("message", "db error");
			response.put("error", ee.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (userCreated!= null) {
			response.put("user", userCreated);
			response.put("message", "ok");
			
		}
		else {
			response.put("message", "db error");
			response.put("user", null);
			
		}
			
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED); // 201
		

		
	}
	
	
	@PostMapping("/update")
	public ResponseEntity<?> update(@RequestBody User user) {

		Map<String, Object> response = new HashMap<String, Object>();
		User updatedUser = null;

		try {
			updatedUser = usuarioService.update(user);
		} catch (InterruptedException ie) {
			response.put("message", "db error");
			response.put("error", ie.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (ExecutionException ee) {
			response.put("message", "db error");
			response.put("error", ee.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (updatedUser!=null) {
			response.put("message", "ok");
			response.put("user", updatedUser);
		}
		else {
			response.put("message", "db error");
			response.put("user", null);
		}
		
		

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(@RequestBody User user) {

		Map<String, Object> response = new HashMap<String, Object>();
		User  deletedUser = null;

		try {
			deletedUser = usuarioService.delete(user);
		} catch (InterruptedException ie) {
			response.put("message", "db error");
			response.put("error", ie.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (ExecutionException ee) {
			response.put("message", "db error");
			response.put("error", ee.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (deletedUser!= null) {
			response.put("message", "ok");
			response.put("user", deletedUser);
		}
		else {
			response.put("message", "db error");
			response.put("user", null);
		}
		
		

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK);
	}
	

	@GetMapping("/get")
	public ResponseEntity<?> getAll() {

		Map<String, Object> response = new HashMap<String, Object>();
		List<User> lstUser = null;
		
		try {
			lstUser  =  usuarioService.findAll();
			
		} catch (InterruptedException ie) {
			response.put("message", "db error");
			response.put("error", ie.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (ExecutionException ee) {
			response.put("message", "db error");
			response.put("error", ee.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "ok");
		response.put("user", lstUser );
		
		
		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK); // 200
		
	}
	

	@GetMapping("/get/byDocumentID")
	public ResponseEntity<?> getByDocumentId(@RequestParam String documentId) {
		Map<String, Object> response = new HashMap<String, Object>();
		User user = null;
		
		try {
			user =  usuarioService.findByDocumentId(documentId);
		} catch (InterruptedException ie) {
			response.put("message", "db error");
			response.put("error", ie.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (ExecutionException ee) {
			response.put("message", "db error");
			response.put("error", ee.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("message", "ok");
		response.put("user", user);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK); // 200
	}
	
	@GetMapping("/get/byUserName")
	public ResponseEntity<?> getByUserName(@RequestParam String userName) {
		Map<String, Object> response = new HashMap<String, Object>();
		User user = null;
		
		try {
			user =  usuarioService.findByNomUser(userName);
		} catch (InterruptedException | ExecutionException ee) {
			response.put("message", "db error");
			response.put("error", ee.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.put("user", user);

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK); // 200
	}
	
	
	@GetMapping("/get/paged/{currentPage}/{limit}")
	public ResponseEntity<?> getAllPaged(@PathVariable int currentPage,@PathVariable int limit ) {

		Map<String, Object> response = new HashMap<String, Object>();
		UserPagination userPaged = null;
		
		try {
			userPaged  =  usuarioService.findAllPaged(currentPage, limit, "cod_id_user");
			
		} catch (InterruptedException ie) {
			response.put("message", "db error");
			response.put("error", ie.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (ExecutionException ee) {
			response.put("message", "db error");
			response.put("error", ee.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (TimeoutException te) {
			response.put("message", "db error");
			response.put("error", te.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	
		}
		
		response.put("message", "ok");
		response.put("propietarioPaged", userPaged );
		
		

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.OK); // 200
		
	}
	
	@PutMapping("/update/rol/{idUser}/{delete}")
	public ResponseEntity<?> updateRol(
			@RequestBody Role role,
			@PathVariable String idUser,
			@PathVariable boolean delete
			) {

		Map<String, Object> response = new HashMap<String, Object>();
		boolean updatedRole = false;

		try {
			
			User user = usuarioService.findByDocumentId(idUser);
			if (user!=null) {
				//If the user exists, check the role isn't repeared
				if (usuarioService.findByNomRole(user.getLstRole(), 
						role.getTxtNomRol())==false || (delete==true)) {
					updatedRole = usuarioService.updateRole(idUser, role,delete);
				}else {
					response.put("rol", null);
					response.put("message", "Duplicated role");
					return new ResponseEntity<Map<String, Object>>(response, HttpStatus.BAD_REQUEST);
				
				}
			}else 
			{
				response.put("rol", null);
				response.put("message", "user not found");
				return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
			}
			
			
		} catch (InterruptedException ie) {
			response.put("message", "db error");
			response.put("error", ie.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (ExecutionException ee) {
			response.put("message", "db error");
			response.put("error", ee.getMessage().concat(": "));
			return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		if (updatedRole) {
			response.put("message", "ok");
			response.put("rol", updatedRole);
		}
		else {
			response.put("message", "db error");
			response.put("rol", null);
		}
		

		return new ResponseEntity<Map<String, Object>>(response, HttpStatus.CREATED);
	}
	
	
	
}
