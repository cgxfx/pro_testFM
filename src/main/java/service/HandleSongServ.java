package service;

import javax.servlet.http.HttpServletRequest;

import model.Menu;
import tool.ResponseObject;

public interface HandleSongServ {
	
	public ResponseObject<Boolean> appendFavorSong(HttpServletRequest req, String songId);
	
	public ResponseObject<Boolean> delFavorSong(HttpServletRequest req, String songId);

	public  ResponseObject<Boolean> appendMenuSong(HttpServletRequest req, String menuId, String songId);
	
	public ResponseObject<Boolean> delMenuSong(HttpServletRequest req, String menuId, String songId);
	
	public ResponseObject<Boolean> createMenu(HttpServletRequest req, Menu menu);
	
	public ResponseObject<Boolean> delMenu(HttpServletRequest req, String menuId);
	
	public ResponseObject<Boolean> shareMenu(HttpServletRequest req, Menu menu);
	
	public ResponseObject<Boolean> editShareMenu(HttpServletRequest req, Menu menu);
	
}
