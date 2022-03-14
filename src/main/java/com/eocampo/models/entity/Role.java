/**
 * 
 */
package com.eocampo.models.entity;

/**
 * @author eocampo
 * User Rol: e.g. ADMIN, USER
 */
public class Role {
	//id provideb by firebase
	private String documentId; 
	private String txtNomRol;
	
	public Role() {
	
	}
	
	public Role(String documentId, String txtNomRol) {
		super();
		this.documentId = documentId;
		this.txtNomRol = txtNomRol;
	}
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String documentId) {
		this.documentId = documentId;
	}
	public String getTxtNomRol() {
		return txtNomRol;
	}
	public void setTxtNomRol(String txtNomRol) {
		this.txtNomRol = txtNomRol;
	}
}
