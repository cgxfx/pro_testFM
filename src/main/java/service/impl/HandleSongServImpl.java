package service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.GetterSongDao;
import dao.HandleSongDao;
import dao.UserDao;
import model.Menu;
import model.Song;
import model.User;
import model.Visitor;
import service.HandleSongServ;
import tool.BizException;
import tool.Constants;
import tool.CookieValue;
import tool.MD5Util;
import tool.ResponseObject;

@Service
public class HandleSongServImpl extends BaseServImpl implements HandleSongServ{
	
	@Autowired
	private GetterSongDao getterSongDao;
	
	@Autowired
	private HandleSongDao handleSongDao;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public ResponseObject<Boolean> appendFavorSong(HttpServletRequest req, String songId) {
		if (StringUtils.isBlank(songId)) {
			return new ResponseObject<>(new BizException(Constants.INVALID_ARGUMENT, Constants.INVALID_ARGUMENTs));
		}
		Song song = this.isExistSong(songId, false);
		if (song == null) {
			return new ResponseObject<>(new BizException(Constants.ERROR_ARGUMENT, Constants.UNKNOWs));
		}
		HttpSession session = req.getSession();
		String visitorId = String.valueOf(session.getAttribute(Constants.VISITOR_CODEs));
		if (StringUtils.isBlank(visitorId) || "null".equals(visitorId)) {
			Visitor visitor = setVisitorSession(req);
			if (visitor != null) {
				visitorId = String.valueOf(visitor.getId());
			}
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("visitorId", visitorId);
		List<Menu> visitorMenus = this.getterSongDao.getMenus(params);
		if (visitorMenus == null || visitorMenus.size() != 1) {
			return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
		}
		try {
			if (this.handleSongDao.appendMenuSong(String.valueOf(visitorMenus.get(0).getId()), songId) != 1) {
				return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
			} 
		} catch(Exception e) {
			return new ResponseObject<>(new BizException(Constants.ALREADY_BEEN_EXIST, e.getMessage()));
		}
		return new ResponseObject<Boolean>(true);
	}
	
	@Override 
	public ResponseObject<Boolean> delFavorSong(HttpServletRequest req, String songId) {
		if (StringUtils.isBlank(songId)) {
			return new ResponseObject<>(new BizException(Constants.INVALID_ARGUMENT, Constants.INVALID_ARGUMENTs));
		}
		Song song = this.isExistSong(songId, false);
		if (song == null) {
			return new ResponseObject<>(new BizException(Constants.ERROR_ARGUMENT, Constants.UNKNOWs));
		}
		HttpSession session = req.getSession();
		String visitorId = String.valueOf(session.getAttribute(Constants.VISITOR_CODEs));
		if (StringUtils.isBlank(visitorId) || "null".equals(visitorId)) {
			Visitor visitor = setVisitorSession(req);
			if (visitor != null) {
				visitorId = String.valueOf(visitor.getId());
			}
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("visitorId", visitorId);
		List<Menu> visitorMenus = this.getterSongDao.getMenus(params);
		if (visitorMenus == null || visitorMenus.size() != 1) {
			return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
		}
		List<String> songIds = this.getterSongDao.getMenuSongIds(params);
		if (songIds == null || songIds.size() == 0) {
			return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
		}
		for (int i = 0, size = songIds.size(); i < size; i++) {
			if (songIds.get(i).equals(songId)) {
				if (this.handleSongDao.delMenuSong(String.valueOf(visitorMenus.get(0).getId()), songId) != 1) {
					return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
				} else {
					return new ResponseObject<Boolean>(true);
				}
			}
		}
		return new ResponseObject<>(new BizException(Constants.ERROR_ARGUMENT, Constants.UNKNOWs));
	}
	

	@Override
	@Transactional
	public ResponseObject<Boolean> appendMenuSong(HttpServletRequest req, String menuId, String songId) {
		if (StringUtils.isBlank(menuId) || StringUtils.isBlank(songId)) 
			return new ResponseObject<>(new BizException(Constants.INVALID_ARGUMENT, Constants.INVALID_ARGUMENTs));
		Song song = this.isExistSong(songId, false);
		if (!isUserMenu(req, menuId) || song == null)
			return new ResponseObject<>(new BizException(Constants.ERROR_ARGUMENT, Constants.UNKNOWs));
		try {
			if (this.handleSongDao.appendMenuSong(menuId.toString(), songId.toString()) != 1) 
				return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
		} catch(Exception e) {
			return new ResponseObject<>(new BizException(Constants.ALREADY_BEEN_EXIST, e.getMessage()));
		}
		if (!song.getArchive()) {
			Menu menu = new Menu();
			menu.setId(Long.parseLong(menuId));
			menu.setAvatrImg(song.getAvatrImg());
			if (this.handleSongDao.editShareMenu(menu) != 1)
				return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
		}
		return new ResponseObject<Boolean>(true);
	}

	@Override
	@Transactional
	public ResponseObject<Boolean> delMenuSong(HttpServletRequest req, String menuId, String songId) {
		if (StringUtils.isBlank(menuId) || StringUtils.isBlank(songId)) 
			return new ResponseObject<>(new BizException(Constants.INVALID_ARGUMENT, Constants.INVALID_ARGUMENTs));
		Song song = this.isExistSong(songId, true, menuId);
		if (!isUserMenu(req, menuId) || song == null)
			return new ResponseObject<>(new BizException(Constants.ERROR_ARGUMENT, Constants.UNKNOWs));
		if (this.handleSongDao.delMenuSong(menuId.toString(), songId.toString()) != 1)
			return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
		Menu menu = new Menu();
		menu.setId(Long.parseLong(menuId));
		menu.setAvatrImg(song.getAvatrImg());
		if (this.handleSongDao.editShareMenu(menu) != 1) 
			return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
		return new ResponseObject<Boolean>(true);
	}

	@Override
	public ResponseObject<Boolean> createMenu(HttpServletRequest req, Menu menu) {
		if (menu == null)
			return new ResponseObject<>(new BizException(Constants.INVALID_ARGUMENT, Constants.INVALID_ARGUMENTs));
		if (menu.getVisitorId() != null) {
			return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
		}
		HttpSession session = req.getSession();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("visitorId", session.getAttribute(Constants.VISITOR_CODEs));
		User user = this.userDao.getUser(params);
		String code = CookieValue.getCookieValue(req.getCookies(), Constants.USER_CODEs);
		if (user == null || StringUtils.isBlank(code))
			return new ResponseObject<>(new BizException(Constants.LOGIN_OR_REGISTER_FIRST, Constants.LOGIN_OR_REGISTER_FIRSTs));
		try {
			String email = MD5Util.encrypt(user.getUserEmail());
			if (!code.equals(email))
				return new ResponseObject<>(new BizException(Constants.ERROR_ARGUMENT, Constants.UNKNOWs));
		} catch(Exception e) {
			return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
		}
		menu.setUserId(user.getId());
		if (StringUtils.isBlank(menu.getName())) {
			params.put("userId", user.getId());
			params.put("visitorId", null);
			List<Menu> userMenus = this.getterSongDao.getMenus(params);
			menu.setName(Constants.DEFAULT_USER_MENU_NAMEs + "-" + (userMenus.size() + 1));
		}
		menu.setAvatrImg(Constants.DEFAULT_MENU_AVATRs);
		if (this.handleSongDao.createMenu(menu) != 1)
			return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
		return new ResponseObject<Boolean>(true);
	}

	@Override
	public ResponseObject<Boolean> delMenu(HttpServletRequest req, String menuId) {
		HttpSession session = req.getSession();
		String visitorId = String.valueOf(session.getAttribute(Constants.VISITOR_CODEs));
		if (StringUtils.isBlank(visitorId) || "null".equals(visitorId)) {
			setVisitorSession(req);
			return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("visitorId", visitorId);
		List<Menu> visitorMenus = this.getterSongDao.getMenus(params);
		if (visitorMenus == null || visitorMenus.size() != 1) 
			return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
		if (visitorMenus.get(0).getId().toString().equals(menuId))
			return new ResponseObject<>(new BizException(Constants.UNAUTHORIZED, Constants.UNAUTHORIZEDs));
		User user = this.userDao.getUser(params);
		String code = CookieValue.getCookieValue(req.getCookies(), Constants.USER_CODEs);
		if (user == null || StringUtils.isBlank(code))
			return new ResponseObject<>(new BizException(Constants.LOGIN_OR_REGISTER_FIRST, Constants.LOGIN_OR_REGISTER_FIRSTs));
		try {
			String email = MD5Util.encrypt(user.getUserEmail());
			if (!code.equals(email))
				return new ResponseObject<>(new BizException(Constants.UNAUTHORIZED, Constants.UNAUTHORIZEDs));
		} catch(Exception e) {
			return new ResponseObject<Boolean>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
		}
		
		params.put("userId", user.getId());
		params.put("visitorId", null);
		List<Menu> menus = this.getterSongDao.getMenus(params);
		if (menus == null || menus.size() == 0)
			return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
		for (int i = 0, size = menus.size(); i < size; i++) {
			if (menus.get(i).getId().toString().equals(menuId) && this.handleSongDao.delMenu(menuId) == 1)
				return new ResponseObject<Boolean>(true);
		}
		
		return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
	}

	@Override
	public ResponseObject<Boolean> shareMenu(HttpServletRequest req, Menu menu) {
		if (menu == null || menu.getId() == null || StringUtils.isBlank(menu.getShareTitle()) || menu.getMenuType() == null) 
			return new ResponseObject<>(new BizException(Constants.INVALID_ARGUMENT, Constants.INVALID_ARGUMENTs));
		
		if (!isUserMenu(req, menu.getId().toString()))
			return new ResponseObject<>(new BizException(Constants.UNAUTHORIZED, Constants.UNAUTHORIZEDs));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", menu.getId());
		List<Menu> menus = this.getterSongDao.getMenus(params);
		if (menus.get(0).getValid())
			return new ResponseObject<Boolean>(new BizException(Constants.ALREADY_BEEN_SHARED, Constants.ALREADY_BEEN_SHAREDs));
		if (this.handleSongDao.shareMenu(menu) == 1)
			return new ResponseObject<Boolean>(true);
		return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
	}

	@Override
	public ResponseObject<Boolean> editShareMenu(HttpServletRequest req, Menu menu) {
		if (menu == null || menu.getId() == null) 
			return new ResponseObject<>(new BizException(Constants.INVALID_ARGUMENT, Constants.INVALID_ARGUMENTs));
		if (!isUserMenu(req, menu.getId().toString()))
			return new ResponseObject<>(new BizException(Constants.UNAUTHORIZED, Constants.UNAUTHORIZEDs));
		if (this.handleSongDao.editShareMenu(menu) == 1)
			return new ResponseObject<Boolean>(true);
		return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
	}
	
	private Song isExistSong(String songId, Boolean is4Del, String... menuId) {
		String[] songIds = new String[]{songId.toString()};
		String lastAvatrImg = "";
		List<Song> songs = this.getterSongDao.getSongsByIds(songIds);
		if (songs == null || songs.size() != 1)
			return null;
		if (menuId == null || menuId.length == 0) {
			return songs.get(0);
		}
		songs = this.getterSongDao.getMenuSongs(menuId.toString());
		if (songs == null || songs.size() == 0)
			return null;
		for (int i = songs.size() - 1; i > 0; i--) {
			if (is4Del && !songs.get(i).getArchive() && StringUtils.isBlank(lastAvatrImg)
					&& !songs.get(i).getId().equals(songId))
				lastAvatrImg = songs.get(i).getAvatrImg();
			if (songs.get(i).getId().toString().equals(songId)) {
				Song song = songs.get(i);
				if (StringUtils.isBlank(lastAvatrImg))
					lastAvatrImg = Constants.DEFAULT_MENU_AVATRs;
				song.setAvatrImg(lastAvatrImg);
				return song;
			}
		}
			
		return null;
	}
	
	private Boolean isUserMenu(HttpServletRequest req, String menuId) {
		HttpSession session = req.getSession();
		String visitorId = String.valueOf(session.getAttribute(Constants.VISITOR_CODEs));
		if (StringUtils.isBlank(visitorId) || "null".equals(visitorId)) {
			setVisitorSession(req);
			return false;
		}
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("visitorId", visitorId);
//		List<Menu> visitorMenus = this.getterSongDao.getMenus(params);
//		if (visitorMenus == null || visitorMenus.size() != 1) 
//			return false;
//		if (visitorMenus.get(0).getId().toString().equals(menuId))
//			return true;
		User user = this.userDao.getUser(params);
		String code = CookieValue.getCookieValue(req.getCookies(), Constants.USER_CODEs);
		if (user == null || StringUtils.isBlank(code))
			return false;
		try {
			String email = MD5Util.encrypt(user.getUserEmail());
			if (!code.equals(email))
				return false;
		} catch(Exception e) {
			return false;
		}
		params.put("userId", user.getId());
		params.put("visitorId", null);
		List<Menu> menus = this.getterSongDao.getMenus(params);
		if (menus == null || menus.size() == 0)
			return false;
		for (int i = 0, size = menus.size(); i < size; i++) {
			if (menus.get(i).getId().toString().equals(menuId))
				return true;
		}
		return false;
	}
}
