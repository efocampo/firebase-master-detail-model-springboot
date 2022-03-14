package com.eocampo.models.entity;

import java.util.ArrayList;
import java.util.List;

/**
 * This class send data with a format to let pagination on the client side
 * @author eocampo
 *
 */
public class UserPagination {
	private List<User> listUser = new ArrayList<User>();
	private int currentIndex;
	private int limit;
	private int totalSize;
	
	public UserPagination() {
		super();
	}

	public UserPagination(List<User> listUser, int currentIndex, int limit, int totalSize) {
		super();
		this.listUser = listUser;
		this.currentIndex = currentIndex;
		this.limit = limit;
		this.totalSize = totalSize;
	}

	public List<User> getListUser() {
		return listUser;
	}

	public void setListUser(List<User> listUser) {
		this.listUser = listUser;
	}

	public int getCurrentIndex() {
		return currentIndex;
	}

	public void setCurrentIndex(int currentIndex) {
		this.currentIndex = currentIndex;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(int totalSize) {
		this.totalSize = totalSize;
	}
	
	
	
}
