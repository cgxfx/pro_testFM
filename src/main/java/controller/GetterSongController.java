package controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import model.Album;
import model.Menu;
import model.MenuType;
import model.Singer;
import model.Song;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import EntityVO.AlbumSongsVO;
import EntityVO.MenuSongsVO;
import EntityVO.SingerSongsVO;
import service.GetterSongServ;
import tool.ResponseObject;

@Controller
@RequestMapping("/getter")
public class GetterSongController {
	
	@Autowired
	private GetterSongServ songSev;

	@ResponseBody
	@RequestMapping(value = "/songs/visitor", method = RequestMethod.POST)
	public ResponseObject<MenuSongsVO> getVisitorSongs(HttpServletRequest req) {
		return this.songSev.getVisitorSongs(req);
	}
	
	@ResponseBody
	@RequestMapping(value = "/songs/user", method = RequestMethod.POST)
	public ResponseObject<List<MenuSongsVO>> getUserSongs(HttpServletRequest req, @RequestBody Map<String, Object> params) {
		if (params.get("menuId") != null) {
			String[] menuId = new String[]{params.get("menuId").toString()};
			return this.songSev.getUserSongs(req, menuId);
		}
		return this.songSev.getUserSongs(req);
	}
	
	@ResponseBody
	@RequestMapping(value = "/menus/user", method = RequestMethod.POST)
	public ResponseObject<List<Menu>> getUserMenus(HttpServletRequest req) {
		return this.songSev.getUserMenus(req);
	}
	
	@ResponseBody
	@RequestMapping(value = "/songs/discovery", method = RequestMethod.POST)
	public ResponseObject<List<MenuSongsVO>> getDiscoverySongs(HttpServletRequest req, @RequestBody Map<String, Object> params) {
		Map<String, Object> param = new HashMap<String, Object>();
		if (params.get("menuType") != null) {
			param.put("menuType", getMenuType(String.valueOf(params.get("menuType"))));
		}
		return this.songSev.getMenuSongs(req, param);
	}
	
	@ResponseBody
	@RequestMapping(value = "/songs/name", method = RequestMethod.POST) 
	public ResponseObject<List<Song>> getSongsBySongName(
			HttpServletRequest req, @RequestBody Map<String,Object> params) {
		return this.songSev.query4SongByName(req, params.get("songName").toString());
	}
	
	@ResponseBody
	@RequestMapping(value = "/singers/name", method = RequestMethod.POST)
	public ResponseObject<List<Singer>> getSingersBySingerName(@RequestBody Map<String, Object> params) {
		return this.songSev.query4SingersByName(params.get("singerName").toString());
	}
	
	@ResponseBody
	@RequestMapping(value = "/songs/singerId", method = RequestMethod.POST)
 	public ResponseObject<SingerSongsVO> doGetSongsBySingerName(
 			HttpServletRequest req, @RequestBody Map<String, Object> params) {
		return this.songSev.query4SingerSongsById(req, params.get("singerId").toString());
	}
	
	@ResponseBody
	@RequestMapping(value = "/albums/name", method = RequestMethod.POST) 
	public ResponseObject<List<Album>> getAlbumByAlbumName(@RequestBody Map<String, Object> params) {
		return this.songSev.query4AlbumsByName(params.get("albumName").toString());
	}
	
	@ResponseBody
	@RequestMapping(value = "/songs/albumId", method = RequestMethod.POST) 
	public ResponseObject<AlbumSongsVO> doGetSongsByAlbumName(
			HttpServletRequest req, @RequestBody Map<String, Object> params) {
		return this.songSev.query4AlbumSongById(req, params.get("albumId").toString());
	}
	
	private MenuType getMenuType(String menuType) {
		switch(menuType) {
			case "SADNESS":
				return MenuType.SADNESS;
			case "QUIETNESS":
				return MenuType.QUIETNESS;
			case "HAPPINESS":
				return MenuType.HAPPINESS;
			case "HEARTBROKEN":
				return MenuType.HEARTBROKEN;
			case "MISSING":
				return MenuType.MISSING;
			case "SWEETIE":
				return MenuType.SWEETIE;
			case "INSPIRATION":
				return MenuType.INSPIRATION;
			case "CATHARSIS":
				return MenuType.CATHARSIS;
			case "WARMING":
				return MenuType.WARMING;
			case "LONELINESS":
				return MenuType.LONELINESS;
			case "REBELLIOUS":
				return MenuType.REBELLIOUS;
			case "EXCEPTATION":
				return MenuType.EXCEPTATION;
		}
		return null;
	}
	
}
