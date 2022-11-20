package uni.fmi.masters.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "requests")
public class RequestEntity implements Serializable{

	private static final long serialVersionUID = 1L;

	public RequestEntity(UserEntity fromUser, UserEntity toUser, 
			String type, boolean approved) {
		
		this.fromUser = fromUser;
		this.toUser = toUser;
		this.message = type;
		this.approved = approved;
		
	}
	
	public RequestEntity() { }
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private UserEntity fromUser;
	
	@ManyToOne
	@JoinColumn(referencedColumnName = "id")
	private UserEntity toUser;
	
	@Column(length = 400, nullable = false)
	private String message;
	
	@Column(length = 50, nullable = false)
	private String type;
	
	@Column()
	private boolean approved;
	
	public UserEntity getFromUser() {
		return fromUser;
	}
	public void setFromUser(UserEntity fromUser) {
		this.fromUser = fromUser;
	}
	public UserEntity getToUser() {
		return toUser;
	}
	public void setToUser(UserEntity toUser) {
		this.toUser = toUser;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}	
}
