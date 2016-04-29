package service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Menu;
import model.User;
import model.Visitor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.HandleSongDao;
import dao.UserDao;
import tool.Constants;
import tool.MD5Util;

@Service
public class BaseServImpl {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private HandleSongDao handleSongDao;
	
	@Transactional
	public Visitor setVisitorSession(HttpServletRequest req, Long...visitorIds) {
		Visitor visitor = new Visitor();
		if (visitorIds == null || visitorIds.length == 0) {
			if (this.userDao.createVisitor(visitor) != 1) {
				return null;
			}
			Menu menu = new Menu();
			menu.setName(Constants.DEFAULT_MENU_FAVOR_NAMEs);
			menu.setAvatrImg(Constants.DEFAULT_MENU_AVATRs);
			menu.setVisitorId(visitor.getId());
			handleSongDao.createMenu(menu);
		} else {
			visitor.setId(visitorIds[0]);
		}
		HttpSession session = req.getSession();
		session.setAttribute(Constants.VISITOR_CODEs, visitor.getId());
		session.setMaxInactiveInterval(-1);
		return visitor;
	}
	
	public void setUserCookie(HttpServletResponse response, String email) {
		try {
			email = MD5Util.encrypt(email);
		} catch(Exception e) {
			
		}
		Cookie cookie = new Cookie(Constants.USER_CODEs, email);
		cookie.setPath("/");
		cookie.setMaxAge(12*3600);
		response.addCookie(cookie);
	}
	
//	public String getCookieValue(Cookie[] cookies, String key) {
//		if (cookies == null || cookies.length == 0 || StringUtils.isBlank(key))
//			return null;
//		for (int i = 0, size = cookies.length; i < size; i++) {
//			if (cookies[i].getName().equals(key)) {
//				return cookies[i].getValue();
//			}
//		}
//		return null;
//	}
	
	public User getUserByEmail(String email) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userEmail", email);
		return this.userDao.getUser(params);
	}

}
