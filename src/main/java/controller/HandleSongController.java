package controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import model.Menu;
import model.MenuType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.HandleSongServ;
import tool.ResponseObject;

@Controller
@RequestMapping("/handle")
public class HandleSongController {
	
	@Autowired
	private HandleSongServ songServ;

	@ResponseBody
	@RequestMapping(value = "/append/song/favor", method = RequestMethod.POST) 
	public ResponseObject<Boolean> appendFavorSong(
			HttpServletRequest req, @RequestBody Map<String, Object> params) {
		return this.songServ.appendFavorSong(req, String.valueOf(params.get("songId")));
	}
	
	@ResponseBody
	@RequestMapping(value = "/del/song/favor", method = RequestMethod.POST) 
	public ResponseObject<Boolean> delFavorSong(
			HttpServletRequest req, @RequestBody Map<String, Object> params) {
		return this.songServ.delFavorSong(req, String.valueOf(params.get("songId")));
	}

	@ResponseBody
	@RequestMapping(value = "/append/song/menu", method = RequestMethod.POST) 
	public ResponseObject<Boolean> appendMenuSong(
			HttpServletRequest req, @RequestBody Map<String, Object> params) {
		return this.songServ.appendMenuSong(req, String.valueOf(params.get("menuId")), String.valueOf(params.get("songId")));
	}
	
	@ResponseBody
	@RequestMapping(value = "/del/song/menu", method = RequestMethod.POST) 
	public ResponseObject<Boolean> delMenuSong(
			HttpServletRequest req, @RequestBody Map<String, Object> params) {
		return this.songServ.delMenuSong(req, String.valueOf(params.get("menuId")), String.valueOf(params.get("songId")));
	}
	
	@ResponseBody
	@RequestMapping(value = "/create/menu", method = RequestMethod.POST)
	public ResponseObject<Boolean> createMenu(
			HttpServletRequest req, @RequestBody Map<String, Object> params) {
		Menu menu = new Menu();
		if (params.get("menuName") != null) {
			menu.setName(String.valueOf(params.get("menuName")));
		}
		return this.songServ.createMenu(req, menu);
	}
	
	@ResponseBody
	@RequestMapping(value = "/del/menu", method = RequestMethod.POST)
	public ResponseObject<Boolean> delMenu(
			HttpServletRequest req, @RequestBody Map<String, Object> params) {
		return this.songServ.delMenu(req, String.valueOf(params.get("menuId")));
	}
	
	
	@ResponseBody
	@RequestMapping(value = "/share/menu", method = RequestMethod.POST) 
	public ResponseObject<Boolean> shareMenu(
			HttpServletRequest req, @RequestBody Map<String, Object> params) {
		Menu menu = new Menu();
		if (params.get("menuId") != null) {
			menu.setId((Long) params.get("menuId"));
		}
		if (params.get("shareTitle") != null) {
			menu.setShareTitle(String.valueOf(params.get("shareTitle")));
		}
		if (params.get("menuType") != null) {
			menu.setMenuType(getMenuType(String.valueOf(params.get("menuType"))));
		}
		return this.songServ.shareMenu(req, menu);
	}
	
	@ResponseBody
	@RequestMapping(value = "/edit/menu", method = RequestMethod.POST) 
	public ResponseObject<Boolean> editMenu(
			HttpServletRequest req, @RequestBody Map<String, Object> params) {
		Menu menu = new Menu();
		if (params.get("menuId") != null) {
			menu.setId(Long.parseLong(String.valueOf(params.get("menuId"))));
		}
		if (params.get("menuName") != null) {
			menu.setName(String.valueOf(params.get("menuName")));
		}
		if (params.get("shareTitle") != null) {
			menu.setShareTitle(String.valueOf(params.get("shareTitle")));
		}
		if (params.get("menuType") != null) {
			menu.setMenuType(getMenuType(String.valueOf(params.get("menuType"))));
		}
		if (params.get("valid") != null) {
			menu.setValid((Boolean) params.get("valid"));
		}
		return this.songServ.editShareMenu(req, menu);
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
