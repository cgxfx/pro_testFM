package model;

public class User extends BaseModel{
	
	private Long visitorId;
	private String nickName;
	private String userEmail;
	private String userPwd;
	private String avatrImg;
	public Long getVisitorId() {
		return visitorId;
	}
	public void setVisitorId(Long visitorId) {
		this.visitorId = visitorId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserPwd() {
		return userPwd;
	}
	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}
	public String getAvatrImg() {
		return avatrImg;
	}
	public void setAvatrImg(String avatrImg) {
		this.avatrImg = avatrImg;
	}
}
