package model;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Album extends BaseModel{

	private Long singerId;
	private String singerName;
	private String name;
	private Timestamp publishTime;
	private String intro;
	private String avatrImg;
	private Boolean archive;
	
	public Long getSingerId() {
		return singerId;
	}
	public void setSingerId(Long singerId) {
		this.singerId = singerId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Timestamp getPublishTime() {
		return publishTime;
	}
	public void setPublishTime(Timestamp publishTime) {
		this.publishTime = publishTime;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getAvatrImg() {
		return avatrImg;
	}
	public void setAvatrImg(String avatrImg) {
		this.avatrImg = avatrImg;
	}
	public String getSingerName() {
		return singerName;
	}
	public void setSingerName(String singerName) {
		this.singerName = singerName;
	}
	public Boolean getArchive() {
		return archive;
	}
	public void setArchive(Boolean archive) {
		this.archive = archive;
	}
	
	public String getCreatedAtStr() {
		if (this.publishTime == null)
			return null;
		 DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		 return formatter.format(this.publishTime);
	}
	
}
