package model;

public class Singer extends BaseModel{
	
	private String name;
	private String avatrImg;
	private String intro;
	private Boolean archive;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAvatrImg() {
		return avatrImg;
	}
	public void setAvatrImg(String avatrImg) {
		this.avatrImg = avatrImg;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public Boolean getArchive() {
		return archive;
	}
	public void setArchive(Boolean archive) {
		this.archive = archive;
	}
	
}
