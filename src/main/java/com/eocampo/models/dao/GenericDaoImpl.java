package com.eocampo.models.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.eocampo.util.Util;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.Query;
import com.google.cloud.firestore.Query.Direction;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;


/**
 * Interface implementation to interact with firebase
 * @author eocampo
 *
 * @param <T>
 */
public class GenericDaoImpl<T> implements IGenericDao<T> {

	@SuppressWarnings({ "unused", "unchecked" })
	public T create(T obj, String nomColeccion, String documentId) throws InterruptedException, ExecutionException {
		Firestore dbFireStore = FirestoreClient.getFirestore();
		
		ApiFuture<WriteResult> collectionApiFuture = dbFireStore.collection(nomColeccion).document(documentId).set(obj);
		T returnObject = null;
		
		while(!collectionApiFuture.isDone()) {}
		
		returnObject = findById((Class<T>) obj.getClass(), nomColeccion, documentId);
		
		return returnObject;
		
	}

	@SuppressWarnings({ "unused", "unchecked" })
	public T update(T obj, String nomColeccion, String documentId, Map<String, Object> valuesToUpdate)
			throws InterruptedException, ExecutionException {

		Firestore dbFireStore = FirestoreClient.getFirestore();

		if (findById((Class<T>) obj.getClass(), nomColeccion, documentId)!= null) {
			ApiFuture<WriteResult> collectionApiFuture = dbFireStore.collection(nomColeccion).document(documentId)
					.update(valuesToUpdate);
			while(!collectionApiFuture.isDone()) {}
			return obj;
		}
		

		return null;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public boolean updateInnerMap(T obj, String nomColeccion, 
			String documentId, 
			String mapName, 
			List<Object> valuesToRemove,
			List<Object> valuesToUpdate)
			throws InterruptedException, ExecutionException {
		
		Firestore dbFireStore = FirestoreClient.getFirestore();
		boolean successfulProcessing = false;
		
		if (findById((Class<T>) obj.getClass(), nomColeccion, documentId)!= null) {
			
			if (valuesToRemove.size()>1) {
				ApiFuture<WriteResult> collectionApiFutureDelete = dbFireStore.collection(nomColeccion).document(documentId)
						.update(mapName, Util.convertArray(valuesToRemove));
				
				while(!collectionApiFutureDelete.isDone()) {
					//System.out.println("deleting....");
				}
			}
			
			if (valuesToUpdate.size() > 1) {
				ApiFuture<WriteResult> collectionApiFutureUnion = dbFireStore.collection(nomColeccion).document(documentId)
						.update(mapName, Util.convertArray(valuesToUpdate));
				
				while(!collectionApiFutureUnion.isDone()) {
					//System.out.println("updating....");
				}
			}
		
			
			successfulProcessing = true;
		}
		
		return successfulProcessing;
	}

	@SuppressWarnings({ "unused", "unchecked" })
	@Override
	public T delete(T obj, String nomColeccion, String documentId) throws InterruptedException, ExecutionException {
		Firestore dbFireStore = FirestoreClient.getFirestore();
		if (findById((Class<T>) obj.getClass(), nomColeccion, documentId)!= null) {
			ApiFuture<WriteResult> writeResult = dbFireStore.collection(nomColeccion).document(documentId).delete();
			while(!writeResult.isDone()) {}
			return obj;
		}
		
		return null;
	}
	
	@Override
	public T findById(Class<T> obj, String nomColeccion, String documentId)
			throws InterruptedException, ExecutionException {
		Firestore dbFireStore = FirestoreClient.getFirestore();
		DocumentReference docRef = dbFireStore.collection(nomColeccion).document(documentId);
		// asynchronously retrieve the document
		ApiFuture<DocumentSnapshot> future = docRef.get();
		// block on response
		DocumentSnapshot document = future.get();
		T objectReturn = null;
		if (document.exists()) {
		  // convert document to POJO
			objectReturn = document.toObject(obj);
		} 
		
		return objectReturn;
		
	}

	public List<T> findByField(Class<T> obj, String nomColeccion, String field, String valueToFind, 
			String orderBy, Direction orderSort)
			throws InterruptedException, ExecutionException {
		List<T> lista = new ArrayList<T>();
		Firestore dbFireStore = FirestoreClient.getFirestore();
		Query query = null;
		if (orderBy== null) {
			query = dbFireStore.collection(nomColeccion).whereEqualTo(field, valueToFind);
		}else {
			query = dbFireStore.collection(nomColeccion).whereEqualTo(field, valueToFind).orderBy(orderBy, orderSort);
		}
				
		// asynchronously retrieve multiple documents
		ApiFuture<QuerySnapshot> future = query.get();

		List<QueryDocumentSnapshot> documents = future.get().getDocuments();
		for (DocumentSnapshot document : documents) {
			lista.add(document.toObject(obj));
		}

		return lista;
	}

	public List<T> findAll(Class<T> obj, String nomColeccion,
			String orderBy, Direction orderSort) throws InterruptedException, ExecutionException {
		List<T> lista = new ArrayList<T>();
		Firestore dbFireStore = FirestoreClient.getFirestore();

		// asynchronously retrieve multiple documents
		ApiFuture<QuerySnapshot> future = null;
		if (orderBy== null) {
			future = dbFireStore.collection(nomColeccion).get();
		}else {
			future = dbFireStore.collection(nomColeccion).orderBy(orderBy, orderSort).get();
		}
				
		// future.get() blocks on response
		List<QueryDocumentSnapshot> documents = future.get().getDocuments();
		for (DocumentSnapshot document : documents) {
			lista.add(document.toObject(obj));
		}

		return lista;
	}

	@Override
	public Map< Object, Object >findAllPaged(Class<T> obj, String nomColeccion, int currentPage, 
			int limit,String orderedBy) 
					throws InterruptedException, ExecutionException, TimeoutException {
		
		Map< Object, Object > retorno = new HashMap<Object, Object>();
		List<T> listData = new ArrayList<T>();
		Firestore dbFireStore = FirestoreClient.getFirestore();
		
		///////////////////////////////////////////////////////
		// Construct query for first limit elements, ordered by orderedBy.
		CollectionReference resultados = dbFireStore.collection(nomColeccion);
		Query firstPage = resultados.orderBy(orderedBy, Direction.ASCENDING);
		
		// Wait for the results of the API call, waiting for a maximum of 30 seconds for a result.
		ApiFuture<QuerySnapshot> future = firstPage.get();
		List<QueryDocumentSnapshot> docs = future.get(30, TimeUnit.SECONDS).getDocuments();
		
		// Construct query for the next limit elements.
		// Get the last item
		int lastItem = (currentPage-1) * limit;
		int totalSize = docs.size();
		if (totalSize < lastItem)
			lastItem = totalSize - 1;
		
		
		QueryDocumentSnapshot lastDoc = docs.get(lastItem);
		Query secondPage = resultados.orderBy(orderedBy,Direction.ASCENDING)
				.startAt(lastDoc).limit(limit );

		future = secondPage.get();
		docs = future.get(30, TimeUnit.SECONDS).getDocuments();
		///////////////////////////////////////////////////////////
		

		for (DocumentSnapshot document : docs) {
			listData.add(document.toObject(obj));
		}
		
		retorno.put("list",listData);
		retorno.put("currentIndex",lastItem);
		retorno.put("limit",limit);
		retorno.put("totalSize",totalSize);

		return retorno;
		
	}

	
	
}
