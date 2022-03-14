package com.eocampo.util;

import java.util.List;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder; 


import com.google.cloud.firestore.FieldValue;

public class Util {
	
	

	/** Helper function to convert test values in a list to Firestore API types. */
	@SuppressWarnings("unchecked")
	public static Object convertArray(List<Object> list) {
		if (!list.isEmpty() && list.get(0).equals("ArrayUnion")) {
			return FieldValue.arrayUnion(((List<Object>) convertArray(list.subList(1, list.size()))).toArray());
		} else if (!list.isEmpty() && list.get(0).equals("ArrayRemove")) {
			return FieldValue.arrayRemove(((List<Object>) convertArray(list.subList(1, list.size()))).toArray());
		} else {
			for (int i = 0; i < list.size(); ++i) {
				list.set(i, (Object) list.get(i));
			}
			return list;
		}
	}
	
	public static String encodePassword(String passwd) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(passwd);
	}
	
	public static void main(String[] args) {
		System.out.println(encodePassword("1234567"));
	}
}