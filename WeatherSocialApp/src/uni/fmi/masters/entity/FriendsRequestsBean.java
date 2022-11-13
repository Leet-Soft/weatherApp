package uni.fmi.masters.entity;

public class FriendsRequestsBean {

	public FriendsRequestsBean(UserEntity friend1, UserEntity user, 
			String type, boolean approved) {
		
		this.fromUser = friend1;
		this.toUser = user;
		this.type = type;
		this.approved = approved;
		
	}
	
	private UserEntity fromUser;
	private UserEntity toUser;
	private String type;
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
}
