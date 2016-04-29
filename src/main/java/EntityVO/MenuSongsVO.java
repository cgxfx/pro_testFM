package EntityVO;

import java.util.List;

import model.Menu;
import model.Song;

public class MenuSongsVO {
	
	private Menu menu;
	private List<Song> songs;
	
	public Menu getMenu() {
		return menu;
	}
	public void setMenu(Menu menu) {
		this.menu = menu;
	}
	public List<Song> getSongs() {
		return songs;
	}
	public void setSongs(List<Song> songs) {
		this.songs = songs;
	}
}
