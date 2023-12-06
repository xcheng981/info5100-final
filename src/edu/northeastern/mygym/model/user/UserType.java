package edu.northeastern.mygym.model.user;

public enum UserType {
	ADMIN("Admin",0),
    MEMBER("Member",1);
	
	private String name;
	private int index;
	
	private UserType(String name,int index) {
		this.name = name;
		this.index = index;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
	
}
