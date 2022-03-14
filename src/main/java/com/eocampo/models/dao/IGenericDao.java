package com.eocampo.models.dao;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import com.google.cloud.firestore.Query.Direction;

/**
 * Interface to interact with firebase
 * @author eocampo
 *
 * @param <T>
 */

public interface IGenericDao<T> {
	public T create(T obj, String nomColeccion, String documentId) 
			throws InterruptedException, ExecutionException;
	
	public T update(T obj, String nomColeccion, String documentId, 
			Map<String, Object> valuesToUpdate) 
			throws InterruptedException, ExecutionException;
	
	public boolean updateInnerMap(T obj, String nomColeccion, String documentId, 
			String mapName,
			List<Object> valuesToRemove,
			List<Object> valuesToUpdate) 
			throws InterruptedException, ExecutionException;
	
	public T delete(T obj, String nomColeccion, String documentId) 
			throws InterruptedException, ExecutionException;
	
	
	public  T findById(Class<T>  obj, String nomColeccion, String documentId) 
			throws InterruptedException, ExecutionException;
	
	public List<T> findByField(Class<T>  obj, String nomColeccion, String field, 
			String valueToFind, String orderBy, Direction orderSort) 
			throws InterruptedException, ExecutionException;
	
	public List<T> findAll(Class<T> obj, String nomColeccion,String orderBy, Direction orderSort) 
			throws InterruptedException, ExecutionException;
	
	public Map< Object, Object > findAllPaged(Class<T> obj, String nomColeccion, 
			int currentPage, int limit, String orderedBy ) 
			throws InterruptedException, ExecutionException, TimeoutException;
}
