package com.ssafy.Member.model;

public class MemberDto {
	private String id;
	private String name;
	private String password;
    private String email;
    private String role;
    
	public MemberDto() {
		super();
	}
	
	public MemberDto(String id, String name, String password, String email, String role) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.email = email;
		this.role = role;
	}

	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "MemberDto [id=" + id + ", name=" + name + ", password=" + password + ", email=" + email + ", role="
				+ role + "]";
	}

	

}
