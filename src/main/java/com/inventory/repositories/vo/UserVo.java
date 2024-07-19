package com.inventory.repositories.vo;

public class UserVo {

	private Long no;
	
	private String name;
	private String email;
	
	private String password;
	
	private String branchId;
	private String branchName;
	
	private String authCode;
	
	
	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	//	생성자
	public UserVo () {
		
	}

	public UserVo(String branchId, String branchName) {
		this.branchId = branchId;
		this.branchName = branchName;
	}

	public UserVo(Long no, String name, String password, String branchId, String authCode) {
		super();
		this.no = no;
		this.name = name;
		this.password = password;
		this.branchId = branchId;
		this.authCode = authCode;
	}
	

	public UserVo(Long no, String name, String email, String password, String branchId, String branchName,
			String authCode) {
		super();
		this.no = no;
		this.name = name;
		this.email = email;
		this.password = password;
		this.branchId = branchId;
		this.branchName = branchName;
		this.authCode = authCode;
	}

	public UserVo (Long no, String name, String branchId, String authCode) {
		this.no = no;
		this.name = name;
		this.branchId = branchId;
		this.authCode = authCode;
	}
	
	public UserVo(Long no, String name) {
		super();
		this.no = no;
		this.name = name;
	}

	//	getter/setter
	public Long getNo() {
		return no;
	}

	public void setNo(Long no) {
		this.no = no;
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

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	@Override
	public String toString() {
		return "UserVo [no=" + no + ", name=" + name + ", password=" + password + ", branchId=" + branchId
				+ ", authCode=" + authCode + "]";
	}
	
}