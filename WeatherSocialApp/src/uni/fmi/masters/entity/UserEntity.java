package uni.fmi.masters.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class UserEntity implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "username", nullable = false, unique = true, length = 50)
	private String username;
	
	@Column(nullable = false, length = 32)
	private String password;
	
	@Column(nullable = false, length = 250, unique = true)
	private String email;
	
	@Column(length = 250)
	private String avatar;
			
	public UserEntity(String username, String password, String email) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public UserEntity(String username, String email) {
		this.username = username;
		this.email = email;
	}

	public UserEntity() { }

	public String getUsername() {
		return username;
	}
	
	public void setUsername(String username) {
		this.username = username;
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

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
		
	
	
//	CREATE TABLE USERS(
//	ID INT PRIMARY KEY AUTO_INCREMENT, 
//	USERNAME VARCHAR(50)  NOT NULL UNIQUE,
//	PASSWORD VARCHAR(32) NOT NULL,
//	EMAIL VARCHAR(250) NOT NULL UNIQUE,
//	AVATAR VARCHAR(250) DEFAULT 'https://static.wikia.nocookie.net/familyguyfanon/images/0/02/Stewie_Griffin.png/revision/latest?cb=20161119043926');

}
