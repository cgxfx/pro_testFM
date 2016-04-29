package model;

public class Song extends BaseModel{

	private Long albumId;
	private String albumName;
	private Long singerId;
	private String singerName;
	private String name;
	private String lyric;
	private String avatrImg;
	private String intro;
	private String linkUrl;
	private Boolean archive;
	private String duration;
	private Boolean isFavor=false;
	
	public Long getAlbumId() {
		return albumId;
	}
	public void setAlbumId(Long albumId) {
		this.albumId = albumId;
	}
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
	public String getLyric() {
		return lyric;
	}
	public void setLyric(String lyric) {
		this.lyric = lyric;
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
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public String getAlbumName() {
		return albumName;
	}
	public void setAlbumName(String albumName) {
		this.albumName = albumName;
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
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public Boolean getIsFavor() {
		return isFavor;
	}
	public void setIsFavor(Boolean isFavor) {
		this.isFavor = isFavor;
	}
	public String getArchiveColor() {
		if (this.archive == null || this.archive)
			return "#999999";
		return "#333333";
	}
	
}
