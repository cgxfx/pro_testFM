package EntityVO;

import java.util.List;

import model.Album;
import model.Song;

public class AlbumSongsVO {
	
	private Album album;
	private List<Song> songs;
	
	public Album getAlbum() {
		return album;
	}
	public void setAlbum(Album album) {
		this.album = album;
	}
	public List<Song> getSongs() {
		return songs;
	}
	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}
}
