package com.eocampo.util;

public enum EnumRol {
	ADMIN("ROLE_ADMIN"),
	USER("ROLE_USER");
	
	private String role;
	EnumRol(String role){
		this.role = role;
	}
	
	public String getRole() {
		return role;
	}
}
