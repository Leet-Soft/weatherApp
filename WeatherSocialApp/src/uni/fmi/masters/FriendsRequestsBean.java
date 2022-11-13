package uni.fmi.masters;

public class FriendsRequestsBean {

	public FriendsRequestsBean(UserBean friend1, UserBean user, 
			String type, boolean approved) {
		
		this.fromUser = friend1;
		this.toUser = user;
		this.type = type;
		this.approved = approved;
		
	}
	
	private UserBean fromUser;
	private UserBean toUser;
	private String type;
	private boolean approved;
	
	public UserBean getFromUser() {
		return fromUser;
	}
	public void setFromUser(UserBean fromUser) {
		this.fromUser = fromUser;
	}
	public UserBean getToUser() {
		return toUser;
	}
	public void setToUser(UserBean toUser) {
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
