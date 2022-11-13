package uni.fmi.masters;

import java.io.Serializable;

public class UserBean implements Serializable{
	
//	CREATE TABLE USERS(
//			ID INT PRIMARY KEY AUTO_INCREMENT, 
//			USERNAME VARCHAR(50)  NOT NULL UNIQUE,
//			PASSWORD VARCHAR(32) NOT NULL,
//			EMAIL VARCHAR(250) NOT NULL UNIQUE,
//			AVATAR VARCHAR(250) DEFAULT 'https://static.wikia.nocookie.net/familyguyfanon/images/0/02/Stewie_Griffin.png/revision/latest?cb=20161119043926');


	private String username;
	private String password;
	private String email;
	private int id;
	private String avatar;
			
	public UserBean(String username, String password, String email) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public UserBean(String username, String email) {
		this.username = username;
		this.email = email;
	}

	public UserBean() {
		// TODO Auto-generated constructor stub
	}

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
		
}
