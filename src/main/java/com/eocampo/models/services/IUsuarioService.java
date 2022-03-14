package com.eocampo.models.services;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import com.eocampo.models.entity.Role;
import com.eocampo.models.entity.User;
import com.eocampo.models.entity.UserPagination;

public interface IUsuarioService {
	public User create(User user) throws InterruptedException, ExecutionException;
	public User update(User user) throws InterruptedException, ExecutionException;
	public User delete(User user) throws InterruptedException, ExecutionException;
	
	public boolean findByNomUserValidate(String nomUser) throws InterruptedException, ExecutionException;
	public User findByNomUser(String nomUser) throws InterruptedException, ExecutionException;
	public User findByDocumentId(String documentId) throws InterruptedException, ExecutionException;
	
	public boolean findByNomRole(List<Role> lstRole, String nomRole);
	
	public List<User> findAll() throws InterruptedException, ExecutionException;
	public UserPagination findAllPaged(int currentPage, 
			int limit,String orderedBy) throws InterruptedException, ExecutionException,TimeoutException;
	
	public boolean updateRole(String idUser, Role role, boolean delite) throws InterruptedException, ExecutionException;
}
