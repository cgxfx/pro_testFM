package dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import model.Album;
import model.Menu;
import model.Singer;
import model.Song;

public interface GetterSongDao {
	
	List<Song> getSongs(@Param("params")Map<String, Object> params);
	
	List<Song> getSongsByIds(@Param("ids")String[] songIds);
	
	List<Album> getAlbumsByIds(@Param("ids")String[] albumIds);
	
	List<Album> getAlbums(@Param("params")Map<String, Object> params);
	
	List<Singer> getSingersByIds(@Param("ids")String[] singerIds);
	
	List<Singer> getSingers(@Param("params")Map<String,	Object> params);
	
	List<String> getMenuSongIds(@Param("params")Map<String, Object> params); //menu  songlist
	
	List<Menu> getMenus(@Param("params")Map<String, Object> params);
	
	List<Menu> getMenus4Discovery(@Param("params")Map<String, Object> params);
	
	List<Song> getMenuSongs(@Param("id")String menuId);    //menu songlist  song
	
}
