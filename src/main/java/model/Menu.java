package model;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Menu extends BaseModel{
	private Long userId;
	private String userName;
	private Long visitorId;
	private String name;
	private String shareTitle;
	private MenuType menuType;
	private String avatrImg;
	private Boolean valid;  
	private Timestamp createdAt;
	private Boolean archive;
	
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public Long getVisitorId() {
		return visitorId;
	}
	public void setVisitorId(Long visitorId) {
		this.visitorId = visitorId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getShareTitle() {
		return shareTitle;
	}
	public void setShareTitle(String shareTitle) {
		this.shareTitle = shareTitle;
	}
	public String getAvatrImg() {
		return avatrImg;
	}
	public void setAvatrImg(String avatrImg) {
		this.avatrImg = avatrImg;
	}
	public Boolean getValid() {
		return valid;
	}
	public void setValid(Boolean valid) {
		this.valid = valid;
	}
	public Timestamp getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	public MenuType getMenuType() {
		return menuType;
	}
	public void setMenuType(MenuType menuType) {
		this.menuType = menuType;
	}
	public Boolean getArchive() {
		return archive;
	}
	public void setArchive(Boolean archive) {
		this.archive = archive;
	}
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCreatedAtStr() {
		if (this.createdAt == null)
			return null;
		 DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		 return formatter.format(this.createdAt);
	}
	
}
