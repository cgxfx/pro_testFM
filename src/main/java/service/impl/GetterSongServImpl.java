package service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import model.Album;
import model.Menu;
import model.Singer;
import model.Song;
import model.User;
import model.Visitor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.GetterSongDao;
import dao.UserDao;
import EntityVO.AlbumSongsVO;
import EntityVO.MenuSongsVO;
import EntityVO.SingerSongsVO;
import service.GetterSongServ;
import tool.BizException;
import tool.Constants;
import tool.CookieValue;
import tool.MD5Util;
import tool.ResponseObject;

@Service
public class GetterSongServImpl extends BaseServImpl implements GetterSongServ{

	@Autowired
	private GetterSongDao getterSongDao;
	
	@Autowired
	private UserDao userDao;
	
	@Override
	public ResponseObject<List<Song>> query4SongByName(HttpServletRequest req, String songName) {
		if (StringUtils.isBlank(songName))
			return new ResponseObject<>(new BizException(Constants.INVALID_ARGUMENT, Constants.INVALID_ARGUMENTs));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("songName", songName);
		List<Song> songs = this.getterSongDao.getSongs(params);
		List<String> favorSongIds = getVisitorFavorSongIds(req);
		if (favorSongIds != null && favorSongIds.size() > 0 && songs != null && songs.size() > 0) {
			for (int i = 0, size = songs.size(); i < size; i++) {
				for (int j = 0, fsize = favorSongIds.size(); j < fsize; j++) {
					if (String.valueOf(songs.get(i).getId()).equals(favorSongIds.get(j))) {
						songs.get(i).setIsFavor(true);
						favorSongIds.remove(j);
						break;
					}
				}
			}
		}
		return new ResponseObject<List<Song>>(songs);
	}

	@Override
	public ResponseObject<List<Singer>> query4SingersByName(String singerName) {
		if (StringUtils.isBlank(singerName))
			return new ResponseObject<>(new BizException(Constants.INVALID_ARGUMENT, Constants.INVALID_ARGUMENTs));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("singerName", singerName);
		return new ResponseObject<List<Singer>>(this.getterSongDao.getSingers(params));
	}

	@Override
	public ResponseObject<List<Album>> query4AlbumsByName(String albumName) {
		if (StringUtils.isBlank(albumName))
			return new ResponseObject<>(new BizException(Constants.INVALID_ARGUMENT, Constants.INVALID_ARGUMENTs));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("albumName", albumName);
		return new ResponseObject<List<Album>>(this.getterSongDao.getAlbums(params));
	}

	@Override
	public ResponseObject<SingerSongsVO> query4SingerSongsById(HttpServletRequest req, String singerId) {
		if (singerId == null) 
			return new ResponseObject<>(new BizException(Constants.INVALID_ARGUMENT, Constants.INVALID_ARGUMENTs));
		String[] singerIds = new String[]{singerId};
		SingerSongsVO singerSongsVO = new SingerSongsVO();
		List<Singer> singers = this.getterSongDao.getSingersByIds(singerIds);
		if (singers == null || singers.size() != 1) 
			return new ResponseObject<SingerSongsVO>(singerSongsVO);
		
		singerSongsVO.setSinger(singers.get(0));
		List<AlbumSongsVO> albumsSongsVOs = new ArrayList<AlbumSongsVO>();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("singerId", singers.get(0).getId());
		List<Album> albums = this.getterSongDao.getAlbums(params);
		if (albums == null || albums.size() == 0)
			return new ResponseObject<SingerSongsVO>(singerSongsVO);
		List<String> favorSongIds = getVisitorFavorSongIds(req);
		for (int i = 0, size = albums.size(); i < size; i++) {
			AlbumSongsVO albumSongsVO = new AlbumSongsVO();
			albumSongsVO.setAlbum(albums.get(i));
			params.put("albumId", albums.get(i).getId());
			List<Song> songs = this.getterSongDao.getSongs(params);
			if (favorSongIds != null && favorSongIds.size() > 0 && songs != null && songs.size() > 0) {
				for (int k = 0, ssize = songs.size(); k < ssize; k++) {
					for (int j = 0, fsize = favorSongIds.size(); j < fsize; j++) {
						if (String.valueOf(songs.get(k).getId()).equals(favorSongIds.get(j))) {
							songs.get(k).setIsFavor(true);
							break;
						}
					}
				}
			}
			albumSongsVO.setSongs(songs);
			albumsSongsVOs.add(albumSongsVO);
		}
		singerSongsVO.setAlbumSongs(albumsSongsVOs);
		return new ResponseObject<SingerSongsVO>(singerSongsVO);
	}

	@Override
	public ResponseObject<AlbumSongsVO> query4AlbumSongById(HttpServletRequest req, String albumId) {
		if (albumId == null)
			return new ResponseObject<>(new BizException(Constants.INVALID_ARGUMENT, Constants.INVALID_ARGUMENTs));
		AlbumSongsVO albumSongsVO = new AlbumSongsVO();
		String[] albumIds = new String[]{albumId};
		List<Album> albums = this.getterSongDao.getAlbumsByIds(albumIds);
		if (albums == null || albums.size() != 1)
			return new ResponseObject<AlbumSongsVO>(albumSongsVO);
		
		albumSongsVO.setAlbum(albums.get(0));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("albumId", albumId);
		List<Song> songs = this.getterSongDao.getSongs(params);
		List<String> favorSongIds = getVisitorFavorSongIds(req);
		if (favorSongIds != null && favorSongIds.size() > 0 && songs != null && songs.size() > 0) {
			for (int i = 0, size = songs.size(); i < size; i++) {
				for (int j = 0, fsize = favorSongIds.size(); j < fsize; j++) {
					if (String.valueOf(songs.get(i).getId()).equals(favorSongIds.get(j))) {
						songs.get(i).setIsFavor(true);
						favorSongIds.remove(j);
						break;
					}
				}
			}
		}
		albumSongsVO.setSongs(songs);
		return new ResponseObject<AlbumSongsVO>(albumSongsVO);
	}
	
	@Override
	public ResponseObject<MenuSongsVO> getVisitorSongs(HttpServletRequest req) {
		HttpSession session = req.getSession();
		String visitorId = String.valueOf(session.getAttribute(Constants.VISITOR_CODEs));
		if (StringUtils.isBlank(visitorId) || "null".equals(visitorId)) {
			setVisitorSession(req);
			return new ResponseObject<MenuSongsVO>(new MenuSongsVO());
		}
		Visitor visitor = this.userDao.getVisitor(visitorId);
		if (visitor == null) 
			return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("visitorId", visitor.getId());
		List<String> menuSongIds = this.getterSongDao.getMenuSongIds(params);
		if (menuSongIds == null) 
			return new ResponseObject<>(new MenuSongsVO());
		//我喜欢的 列表
		MenuSongsVO menuSongsVO = new MenuSongsVO();
		List<Menu> menus = this.getterSongDao.getMenus(params);
		menuSongsVO.setMenu(menus.get(0));
		if (menuSongIds != null && menuSongIds.size() > 0) {
			String[] songIds = (String[]) menuSongIds.toArray(new String[menuSongIds.size()]);
			List<Song> songs = this.getterSongDao.getSongsByIds(songIds);
			for (int i = 0, size = songs.size(); i < size; i++) {
				songs.get(i).setIsFavor(true);
			}
			menuSongsVO.setSongs(songs);
		}
		return new ResponseObject<MenuSongsVO>(menuSongsVO);
	}

	@Override
	public ResponseObject<List<MenuSongsVO>> getUserSongs(HttpServletRequest req, String... menuId) {
		HttpSession session = req.getSession();
		String visitorId = String.valueOf(session.getAttribute(Constants.VISITOR_CODEs));
		if (StringUtils.isBlank(visitorId) || "null".equals(visitorId)) {
			setVisitorSession(req);
			return new ResponseObject<List<MenuSongsVO>>(new ArrayList<MenuSongsVO>());
		}
		Visitor visitor = this.userDao.getVisitor(visitorId);
		if (visitor == null) 
			return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
		List<MenuSongsVO> menuSongsVOs = new ArrayList<MenuSongsVO>();
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("visitorId", visitor.getId());
//		List<String> menuSongIds = this.getterSongDao.getMenuSongIds(params);
//		if (menuSongIds == null || menuSongIds.size() == 0) 
//			return new ResponseObject<List<MenuSongsVO>>(menuSongsVOs);
//		String[] songIds = (String[]) menuSongIds.toArray();
//		List<Song> songs = this.getterSongDao.getSongsByIds(songIds);
//		//我喜欢的 列表
//		MenuSongsVO menuSongsVO = new MenuSongsVO();
//		List<Menu> menus = this.getterSongDao.getMenus(params);
//		menuSongsVO.setMenu(menus.get(0));
//		menuSongsVO.setSongs(songs);
//		menuSongsVOs.add(menuSongsVO);
//		
		User user = this.userDao.getUser(params);
		String code = CookieValue.getCookieValue(req.getCookies(), Constants.USER_CODEs);
		if (StringUtils.isBlank(code) || user == null)
			return new ResponseObject<>(new BizException(Constants.LOGIN_OR_REGISTER_FIRST, Constants.LOGIN_OR_REGISTER_FIRSTs));
		try {
			String email = MD5Util.encrypt(user.getUserEmail());
			if (!code.equals(email)) 
				return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
		} catch(Exception e) {
			return new ResponseObject<>(menuSongsVOs);
		}
		
		params.put("userId", user.getId());
		params.put("visitorId", null);
		List<Menu> menus = this.getterSongDao.getMenus(params);
		if (menus == null || menus.size() == 0)
			return new ResponseObject<List<MenuSongsVO>>(menuSongsVOs);
		List<String> favorSongIds = getVisitorFavorSongIds(req);
		for (int i = 0, size = menus.size(); i < size; i++) {
			if (menuId != null && menuId.length != 0 && !menus.get(i).getId().toString().equals(menuId[0]))
				continue;
			List<Song> songs = this.getterSongDao.getMenuSongs(menus.get(i).getId().toString());
			if (favorSongIds != null && favorSongIds.size() > 0 && songs != null && songs.size() > 0) {
				for (int k = 0, ssize = songs.size(); k < ssize; k++) {
					for (int j = 0, fsize = favorSongIds.size(); j < fsize; j++) {
						if (String.valueOf(songs.get(k).getId()).equals(favorSongIds.get(j))) {
							songs.get(k).setIsFavor(true);
							break;
						}
					}
				}
			}
			MenuSongsVO menuSongsVO = new MenuSongsVO();
			menuSongsVO.setMenu(menus.get(i));
			menuSongsVO.setSongs(songs);
			menuSongsVOs.add(menuSongsVO);
		}
		return new ResponseObject<List<MenuSongsVO>>(menuSongsVOs);
	}
	
	@Override
	public ResponseObject<List<MenuSongsVO>> getMenuSongs(HttpServletRequest req, Map<String, Object> param) {
		List<Menu> menus = this.getterSongDao.getMenus4Discovery(param);
		List<MenuSongsVO> menuSongsVOs = new ArrayList<MenuSongsVO>();
		if (menus == null || menus.size() == 0)
			return new ResponseObject<List<MenuSongsVO>>(menuSongsVOs);
		List<String> favorSongIds = getVisitorFavorSongIds(req);
		for (int i = 0, size = menus.size(); i < size; i++) {
			Boolean onlyArchive = true;
			List<Song> songs = this.getterSongDao.getMenuSongs(menus.get(i).getId().toString());
			if (songs != null && songs.size() > 0) {
				for (int k = 0, ssize = songs.size(); k < ssize; k++) {
					if (favorSongIds != null && favorSongIds.size() > 0) {
						for (int j = 0, fsize = favorSongIds.size(); j < fsize; j++) {
							if (String.valueOf(songs.get(k).getId()).equals(favorSongIds.get(j))) {
								songs.get(k).setIsFavor(true);
								break;
							}
						}
					}
					if (!songs.get(k).getArchive()) {
						onlyArchive = false;
					}
				}
				if (!onlyArchive) {
					MenuSongsVO menuSongsVO = new MenuSongsVO();
					menuSongsVO.setMenu(menus.get(i));
					menuSongsVO.setSongs(songs);
					menuSongsVOs.add(menuSongsVO);
				}
			}
		}
		return new ResponseObject<List<MenuSongsVO>>(menuSongsVOs);
	}
	
	@Override
	public ResponseObject<List<Menu>> getUserMenus(HttpServletRequest req) {
		HttpSession session = req.getSession();
		String visitorId = String.valueOf(session.getAttribute(Constants.VISITOR_CODEs));
		if (StringUtils.isBlank(visitorId) || "null".equals(visitorId)) {
			setVisitorSession(req);
			return new ResponseObject<List<Menu>>(new ArrayList<Menu>());
		}
		Visitor visitor = this.userDao.getVisitor(visitorId);
		if (visitor == null) 
			return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
		
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("visitorId", visitor.getId());
		
		User user = this.userDao.getUser(params);
		String code = CookieValue.getCookieValue(req.getCookies(), Constants.USER_CODEs);
		if (StringUtils.isBlank(code) || user == null)
			return new ResponseObject<>(new BizException(Constants.LOGIN_OR_REGISTER_FIRST, Constants.LOGIN_OR_REGISTER_FIRSTs));
		try {
			String email = MD5Util.encrypt(user.getUserEmail());
			if (!code.equals(email)) 
				return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
		} catch(Exception e) {
			return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
		}
		
		params.put("visitorId", null);
		params.put("userId", user.getId());
		return new ResponseObject<List<Menu>>(this.getterSongDao.getMenus(params));
	}

	private List<String> getVisitorFavorSongIds(HttpServletRequest req) {
		HttpSession session = req.getSession();
		String visitorId = String.valueOf(session.getAttribute(Constants.VISITOR_CODEs));
		if (StringUtils.isBlank(visitorId) || "null".equals(visitorId)) {
			setVisitorSession(req);
			return null;
		}
		Visitor visitor = this.userDao.getVisitor(visitorId);
		if (visitor == null) return null;
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("visitorId", visitor.getId());
		return this.getterSongDao.getMenuSongIds(params);
	}
}
