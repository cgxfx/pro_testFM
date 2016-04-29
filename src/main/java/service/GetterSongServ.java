package service;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import model.Album;
import model.Menu;
import model.Singer;
import model.Song;
import tool.ResponseObject;
import EntityVO.AlbumSongsVO;
import EntityVO.MenuSongsVO;
import EntityVO.SingerSongsVO;

public interface GetterSongServ {
	
	public ResponseObject<List<Song>> query4SongByName(HttpServletRequest req, String songName);   

	public ResponseObject<List<Singer>> query4SingersByName(String singerName); 
	
	public ResponseObject<List<Album>> query4AlbumsByName(String albumName);
	
	public ResponseObject<SingerSongsVO> query4SingerSongsById(HttpServletRequest req, String singerId);  
	
	public ResponseObject<AlbumSongsVO> query4AlbumSongById(HttpServletRequest req, String albumId);  
	
	public ResponseObject<MenuSongsVO> getVisitorSongs(HttpServletRequest req);
	
	public ResponseObject<List<MenuSongsVO>> getUserSongs(HttpServletRequest req, String... menuId);
	
	public ResponseObject<List<MenuSongsVO>> getMenuSongs(HttpServletRequest req, Map<String, Object> param);
	
	public ResponseObject<List<Menu>> getUserMenus(HttpServletRequest req);
	
}
