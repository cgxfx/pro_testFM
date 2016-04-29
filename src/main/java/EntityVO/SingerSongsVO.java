package EntityVO;

import java.util.List;

import model.Singer;

public class SingerSongsVO {
	
	private Singer singer;
	private List<AlbumSongsVO> albumSongs;
	
	public Singer getSinger() {
		return singer;
	}
	public void setSinger(Singer singer) {
		this.singer = singer;
	}
	public List<AlbumSongsVO> getAlbumSongs() {
		return albumSongs;
	}
	public void setAlbumSongs(List<AlbumSongsVO> albumSongs) {
		this.albumSongs = albumSongs;
	}
}
