package dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import model.Album;
import model.Menu;
import model.Singer;
import model.Song;

public interface HandleSongDao {
	
	public  Integer appendMenuSong(@Param("menuId")String menuId, @Param("songId")String songId);
	
	public Integer delMenuSong(@Param("menuId")String menuId, @Param("songId")String songId);
	
	public Integer createMenu(Menu menu);
	
	public Integer delMenu(@Param("id")String menuId); //逻辑删除
	
	public Integer shareMenu(Menu menu);
	
	public Integer editShareMenu(Menu menu);
	
	public Integer insertSingerByBatch(Singer singer);
	
	public Integer insertAlbumByBatch(Album Album);
	
	public Integer insertSongByBatch(List<Song> songs);
}
