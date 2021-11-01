package com.franzoo;



//import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "users")
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long uid;
	
	@Column(nullable = true , unique = true , length = 50)
	private String email;
	
	@Column(nullable = true , unique = true , length = 10)
	private String mob;
	
	@Column(nullable = true , length = 50)
	private String name;
	
	@Column(nullable = true , length = 64)
	private String password;
	
	@Column(nullable = true,length = 100)
	private String created_at="";
	
	@Column(nullable = false)
	private String is_Private = "0";
	
	@Column(nullable = true , length = 4)
	private String OTP;
	
	public String getIs_Private() {
		return is_Private;
	}
	public void setIs_Private(String is_Private) {
		this.is_Private = is_Private;
	}
	public Long getUid() {
		return uid;
	}
	public void setUid(Long uid) {
		this.uid = uid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMob() {
		return mob;
	}
	public void setMob(String mob) {
		this.mob = mob;
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
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getOTP() {
		return OTP;
	}
	public void setOTP(String oTP) {
		OTP = oTP;
	}
	
	
}

	
	

	
	

