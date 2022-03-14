package com.eocampo.models.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import org.springframework.stereotype.Service;

import com.eocampo.models.dao.GenericDaoImpl;
import com.eocampo.models.dao.IGenericDao;
import com.eocampo.models.entity.Role;
import com.eocampo.models.entity.User;
import com.eocampo.models.entity.UserPagination;
import com.google.api.client.util.ArrayMap;
import com.google.cloud.firestore.Query.Direction;

@Service
public class UsuarioServiceImpl implements IUsuarioService {


	private IGenericDao<User> genericDAO = new GenericDaoImpl<User>();
	
	/**
	 * Create new user
	 */
	@Override
	public User create(User user) throws InterruptedException, ExecutionException {
		String documentId = UUID.randomUUID().toString();
		user.setCod_id_user(documentId);
		
		return genericDAO.create(user,"user", user.getCod_id_user().toString());
	}

	@Override
	public User update(User user) throws InterruptedException, ExecutionException {
		Map<String, Object> values = new ArrayMap<String, Object>();
		values.put("txt_first_name",user.getTxt_first_name());
		values.put("txt_last_name", user.getTxt_last_name());
	
		
		return genericDAO.update(user, "user", user.getCod_id_user().toString(), values);
	}

	@Override
	public User delete(User user) throws InterruptedException, ExecutionException {
		return genericDAO.delete(user, "user", user.getCod_id_user().toString());
	}
	
	@Override
	public User findByDocumentId(String documentId) throws InterruptedException, ExecutionException {
		return genericDAO.findById(User.class, "user", documentId);
	}

	@Override
	public boolean findByNomUserValidate(String nomUser) throws InterruptedException, ExecutionException {
		List<User> result = genericDAO.findByField(User.class, 
				"user", "txt_user_name", nomUser,"fec_register",Direction.DESCENDING);
		
		if (result.size()>0)
			return true;
		else
			return false;
	}

	@Override
	public User findByNomUser(String nomUser) throws InterruptedException, ExecutionException {
		List<User> result = genericDAO.findByField(User.class, 
				"user", "txt_user_name", nomUser,"fec_register",Direction.DESCENDING);
		
		if (result.size()>0)
			return result.get(0);
		else
			return null;
	}

	@Override
	public boolean findByNomRole(List<Role> lstRole, String nomRole) {
		Role rol = lstRole.stream()
				.filter(rolTemp -> rolTemp.getTxtNomRol().equals(nomRole))
				.findAny()
				.orElse(null);
		if (rol!=null)
			return true;
		else
			return false;
	}
	
	@Override
	public List<User> findAll() throws InterruptedException, ExecutionException {
		return genericDAO.findAll(User.class, "user","fec_register",Direction.DESCENDING);
	}


	@SuppressWarnings("unchecked")
	@Override
	public UserPagination findAllPaged(int currentPage, int limit, String orderedBy)
			throws InterruptedException, ExecutionException, TimeoutException {
		Map<Object, Object> objectPagination = genericDAO.findAllPaged(User.class, "user", 
				currentPage, limit, orderedBy);
		UserPagination retorno = new UserPagination();

		retorno.setListUser((List<User>)objectPagination.get("list"));
		retorno.setLimit((int)objectPagination.get("limit"));
		retorno.setCurrentIndex((int)objectPagination.get("currentIndex"));
		retorno.setTotalSize((int)objectPagination.get("totalSize"));
		
		return retorno;
	}

	@Override
	public boolean updateRole(String idUser, Role role, boolean delete)
			throws InterruptedException, ExecutionException {
		List<Object> objLstRolUpdate = new ArrayList<Object>();
		List<Object> objLstRolRemove = new ArrayList<Object>();
		
		User user = genericDAO.findById(User.class, "user", idUser);
		if (user!= null) {
			
			//Role for updating
			objLstRolUpdate.add("ArrayUnion");
			
			//Role for removing
			objLstRolRemove.add("ArrayRemove");
			
			//Get all roles
			List<Role> lstRoleBefore = user.getLstRole();	
			
			//Is it a new Role?
			if (role.getDocumentId().trim().equals("")) {
				role.setDocumentId(UUID.randomUUID().toString());
			}else {
				//Delete old role
				Role rolDelete = null;
				for (Role rolTemp: lstRoleBefore) {
					if (rolTemp.getDocumentId().equals(role.getDocumentId()))
						rolDelete = rolTemp;
				}
				if (rolDelete!=null) {
					objLstRolRemove.add(rolDelete);
					user.getLstRole().remove(rolDelete);
				}
			}
			
			//Preparing array to update
			if (delete == false)
				objLstRolUpdate.add(role);
			
			
			return genericDAO.updateInnerMap(user, "user", idUser, 
					"lstRole", objLstRolRemove, objLstRolUpdate);
				
						
		}else
			return false;
	}

	

	
}
