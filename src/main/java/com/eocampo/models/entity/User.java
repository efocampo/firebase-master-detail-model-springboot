package com.eocampo.models.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User data class symplified
 * @author eocampo
 *
 */
public class User {
	//cod_id_user provided by firebase
	private String cod_id_user; 
	private String txt_first_name; 
	private String txt_last_name; 
	private String txt_user_name;
	private Date fec_register;
	
	//List of user roles
	private List<Role> lstRole = new ArrayList<Role>();

	public User() {
		
	}

	public User(String cod_id_user, String txt_first_name, String txt_last_name, String txt_user_name,
			Date fec_register, List<Role> lstRole) {
		super();
		this.cod_id_user = cod_id_user;
		this.txt_first_name = txt_first_name;
		this.txt_last_name = txt_last_name;
		this.txt_user_name = txt_user_name;
		this.fec_register = fec_register;
		this.lstRole = lstRole;
	}

	public String getCod_id_user() {
		return cod_id_user;
	}

	public void setCod_id_user(String cod_id_user) {
		this.cod_id_user = cod_id_user;
	}

	public String getTxt_first_name() {
		return txt_first_name;
	}

	public void setTxt_first_name(String txt_first_name) {
		this.txt_first_name = txt_first_name;
	}

	public String getTxt_last_name() {
		return txt_last_name;
	}

	public void setTxt_last_name(String txt_last_name) {
		this.txt_last_name = txt_last_name;
	}

	public List<Role> getLstRole() {
		return lstRole;
	}

	public void setLstRole(List<Role> lstRole) {
		this.lstRole = lstRole;
	}



	public String getTxt_user_name() {
		return txt_user_name;
	}



	public void setTxt_user_name(String txt_user_name) {
		this.txt_user_name = txt_user_name;
	}

	public Date getFec_register() {
		return fec_register;
	}

	public void setFec_register(Date fec_register) {
		this.fec_register = fec_register;
	}
	
	
	
}
