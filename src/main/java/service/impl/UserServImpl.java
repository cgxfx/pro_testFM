package service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import dao.HandleSongDao;
import dao.UserDao;
import model.Menu;
import model.User;
import model.Visitor;
import service.UserServ;
import tool.BizException;
import tool.Constants;
import tool.CookieValue;
import tool.MD5Util;
import tool.ResponseObject;
import tool.SendEmail;

@Service
public class UserServImpl extends BaseServImpl implements UserServ{
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private HandleSongDao handleSongDao;

	@Override
	@Transactional
	public ResponseObject<Visitor> getOrRegisterVisitor(HttpServletRequest req) {
		HttpSession session = req.getSession();
		Visitor visitor = this.userDao.getVisitor(String.valueOf(session.getAttribute(Constants.VISITOR_CODEs)));
		if (visitor == null) {
			visitor = setVisitorSession(req);
			if (visitor == null)
				return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
		}
		return new ResponseObject<Visitor>(visitor);
	}

	@Override
	public ResponseObject<User> loginOperation(HttpServletRequest req, HttpServletResponse response, User user) {
		if (StringUtils.isBlank(user.getUserEmail()) || StringUtils.isBlank(user.getUserPwd())) 
			return new ResponseObject<>(new BizException(Constants.INVALID_ARGUMENT, Constants.INVALID_EMAIL_PWDs));
		User exUser = getUserByEmail(user.getUserEmail());
		if (exUser == null)
			return new ResponseObject<>(new BizException(Constants.INVALID_ARGUMENT, Constants.NOT_FOUND_USERs));
		String password = "";
		try {
			password = MD5Util.encrypt(user.getUserPwd());
		} catch(Exception e) {
			return new ResponseObject<User>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
		}
		if (!exUser.getUserPwd().equals(password))
			return new ResponseObject<>(new BizException(Constants.ERROR_ARGUMENT, Constants.ERROR_PWDs));
		setUserCookie(response, user.getUserEmail());
		setVisitorSession(req, exUser.getVisitorId());
		return new ResponseObject<User>(exUser);
	}

	@Override
	public ResponseObject<Boolean> registerUser(HttpServletResponse response, String email) {
		if (StringUtils.isBlank(email))
			return new ResponseObject<>(new BizException(Constants.INVALID_ARGUMENT, Constants.INVALID_EMAILs));
		if (getUserByEmail(email) != null) 
			return new ResponseObject<>(new BizException(Constants.INVALID_ARGUMENT, Constants.INVALID_EXEMAILs));
		try {
			String randomStr = SendEmail.sendEmail(email);
			randomStr += "&" + email;
			randomStr = MD5Util.encrypt(randomStr);
			Cookie cookie = new Cookie(Constants.VERIFICATION_CODEs, randomStr);
			cookie.setMaxAge(120);
			response.addCookie(cookie);
		} catch(Exception e) {
			return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.INVALID_SEND_EMAILs));
		}
		return new ResponseObject<>(true);
	}

	@Override
	public ResponseObject<Boolean> verifyIdentity(HttpServletRequest req, String email, String identity, Boolean is4ReLogin) {
		if (StringUtils.isBlank(identity))
			return new ResponseObject<>(new BizException(Constants.INVALID_ARGUMENT, Constants.INVALID_VERIFICATION_CODEs));
		String code = CookieValue.getCookieValue(req.getCookies(), Constants.VERIFICATION_CODEs);
		if (StringUtils.isBlank(code))
			return new ResponseObject<>(new BizException(Constants.OUT_OF_TIME, Constants.OUT_OF_TIMEs));
		try {
			User user = getUserByEmail(email);
			if (user == null && is4ReLogin)
				return new ResponseObject<>(new BizException(Constants.INVALID_ARGUMENT, Constants.INVALID_EMAILs));
			if (user != null && !is4ReLogin)
				return new ResponseObject<>(new BizException(Constants.INVALID_ARGUMENT, Constants.INVALID_EXEMAILs));
			identity = MD5Util.encrypt(identity + "&" + email);
			if (!identity.equals(code))
				return new ResponseObject<>(new BizException(Constants.ERROR_ARGUMENT, Constants.ERROR_VERIFICATION_CODEs));
			
		} catch(Exception e) {
			return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
		}
		return new ResponseObject<Boolean>(true);
	}
	
	@Override
	public ResponseObject<User> doRegisterUser(HttpServletRequest req, HttpServletResponse response, User user) {
		if (StringUtils.isBlank(user.getUserPwd()) || StringUtils.isBlank(user.getUserEmail()))
			return new ResponseObject<>(new BizException(Constants.INVALID_ARGUMENT, Constants.INVALID_EMAIL_PWDs));
//		if (StringUtils.isBlank(identity))
//			return new ResponseObject<>(new BizException(Constants.INVALID_ARGUMENT, Constants.INVALID_VERIFICATION_CODEs));
//		String code = CookieValue.getCookieValue(req.getCookies(), Constants.VERIFICATION_CODEs);
//		if (StringUtils.isBlank(code))
//			return new ResponseObject<>(new BizException(Constants.OUT_OF_TIME, Constants.OUT_OF_TIMEs));
		try {
//			if (getUserByEmail(user.getUserEmail()) != null)
//				return new ResponseObject<>(new BizException(Constants.INVALID_ARGUMENT, Constants.INVALID_EXEMAILs));
//			identity = MD5Util.encrypt(identity + "&" + user.getUserEmail());
			user.setUserPwd(MD5Util.encrypt(user.getUserPwd()));
//			if (!identity.equals(code))
//				return new ResponseObject<>(new BizException(Constants.ERROR_ARGUMENT, Constants.ERROR_VERIFICATION_CODEs));
//			
		} catch(Exception e) {
			return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
		}
		HttpSession session = req.getSession();
		Visitor visitor = this.userDao.getVisitor(String.valueOf(session.getAttribute(Constants.VISITOR_CODEs)));
		if (visitor == null) {
			visitor = setVisitorSession(req);
		} else {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("visitorId", visitor.getId());
			User tUser = this.userDao.getUser(params);
			if (tUser != null)
				visitor = setVisitorSession(req);
		}
		if (!StringUtils.isBlank(user.getNickName())) {
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("nickName", user.getNickName());
			User tUser = this.userDao.getUser(params);
			if (tUser != null)
				return new ResponseObject<User>(new BizException(Constants.ALREADY_BEEN_RENAME, Constants.ALREADY_BEEN_RENAMEs));
		} else {
			user.setNickName("tudou_" + visitor.getId());
		}
		user.setAvatrImg(Constants.DEFAULT_USER_AVATRs);
		user.setVisitorId(visitor.getId());
		if (this.userDao.createUser(user) != 1)
			return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
		setUserCookie(response, user.getUserEmail());
		
		return new ResponseObject<User>(user);
	}

	@Override
	public ResponseObject<User> updateUser(HttpServletRequest req, User user) {
		if (StringUtils.isBlank(user.getUserEmail()) || user.getId() == null)
			return new ResponseObject<>(new BizException(Constants.INVALID_ARGUMENT, Constants.INVALID_EMAILs));
		String code = CookieValue.getCookieValue(req.getCookies(), Constants.USER_CODEs);
		if (StringUtils.isBlank(code))
			return new ResponseObject<>(new BizException(Constants.LOGIN_OR_REGISTER_FIRST, Constants.LOGIN_OR_REGISTER_FIRSTs));
		try {
			String email = MD5Util.encrypt(user.getUserEmail());
			if (!email.equals(code))
				return new ResponseObject<>(new BizException(Constants.UNAUTHORIZED, Constants.UNAUTHORIZEDs));
			if (!StringUtils.isBlank(user.getUserPwd()))
				user.setUserPwd(MD5Util.encrypt(user.getUserPwd()));
			if (!StringUtils.isBlank(user.getNickName())) {
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("nickName", user.getNickName());
				if (this.userDao.getUser(params) != null)
					return new ResponseObject<>(new BizException(Constants.ALREADY_BEEN_RENAME, Constants.ALREADY_BEEN_RENAMEs));
			}
		} catch(Exception e) {
			return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
		}
		
		if (this.userDao.updateUser(user) != 1) {
			return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
		}
		
		return new ResponseObject<User>(user);
	}
	
	@Override
	public ResponseObject<Boolean> forgetPwd(HttpServletResponse response, String email) {
		if (StringUtils.isBlank(email))
			return new ResponseObject<>(new BizException(Constants.INVALID_ARGUMENT, Constants.INVALID_ARGUMENTs));
		if (getUserByEmail(email) == null)
			return new ResponseObject<>(new BizException(Constants.ERROR_ARGUMENT, Constants.UNREGISTERS));
		try {
			String randomStr = SendEmail.sendEmail(email);
			randomStr += "&" + email;
			randomStr = MD5Util.encrypt(randomStr);
			Cookie cookie = new Cookie(Constants.VERIFICATION_CODEs, randomStr);
			cookie.setMaxAge(120);
			response.addCookie(cookie);
		} catch(Exception e) {
			return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.INVALID_SEND_EMAILs));
		}
		return new ResponseObject<Boolean>(true);
	}
	
	@Override
	public ResponseObject<User> reLogin4ForgetPwd(HttpServletRequest req, HttpServletResponse response, User user) {
		if (StringUtils.isBlank(user.getUserEmail()) || StringUtils.isBlank(user.getUserPwd()))
			return new ResponseObject<>(new BizException(Constants.INVALID_ARGUMENT, Constants.INVALID_ARGUMENTs));
//		String code = CookieValue.getCookieValue(req.getCookies(), Constants.VERIFICATION_CODEs);
//		if (StringUtils.isBlank(code)) 
//			return new ResponseObject<>(new BizException(Constants.OUT_OF_TIME, Constants.OUT_OF_TIMEs));
		try {
//			identity = MD5Util.encrypt(identity + "&" + email);
//			if (!identity.equals(code)) 
//				return new ResponseObject<>(new BizException(Constants.ERROR_ARGUMENT, Constants.ERROR_VERIFICATION_CODEs));
			user.setUserPwd(MD5Util.encrypt(user.getUserPwd()));
		} catch(Exception e) {
			return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
		}
		User exUser = getUserByEmail(user.getUserEmail());
		if (exUser == null)
			return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
		user.setId(exUser.getId());
		if (this.userDao.updateUser(user) != 1) {
			return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
		}
		setUserCookie(response, user.getUserEmail());
		setVisitorSession(req, user.getVisitorId());
		return new ResponseObject<User>(user);
	}
	
	@Override
	public ResponseObject<User> userVisit(HttpServletRequest req) {
		User user = new User();
		String userCodes = CookieValue.getCookieValue(req.getCookies(), Constants.USER_CODEs);
		if (StringUtils.isBlank(userCodes)) {
			ResponseObject<Visitor> obj = this.getOrRegisterVisitor(req);
			if (obj.getCode() == 200) {
				user.setVisitorId(obj.getClazz().getId());
			} else {
				return new ResponseObject<>(new BizException(obj.getCode(), obj.getMsg()));
			}
		} else {
			HttpSession session = req.getSession();
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("visitorId", session.getAttribute(Constants.VISITOR_CODEs));
			user = this.userDao.getUser(params);
			if (user == null) {
				return new ResponseObject<>(new BizException(Constants.UNKNOW, Constants.UNKNOWs));
			}
		}
		return new ResponseObject<User>(user);
	}
	
//	private Visitor setVisitorSession(HttpServletRequest req, Long...visitorIds) {
//		Visitor visitor = new Visitor();
//		if (visitorIds == null || visitorIds.length == 0) {
//			visitor = this.userDao.createVisitor();
//		} else {
//			visitor.setId(visitorIds[0]);
//		}
//		HttpSession session = req.getSession();
//		session.setAttribute(Constants.VISITOR_CODEs, visitor.getId());
//		session.setMaxInactiveInterval(-1);
//		return visitor;
//	}
//	
//	private void setUserCookie(HttpServletResponse response, String email) {
//		try {
//			email = MD5Util.encrypt(email);
//		} catch(Exception e) {
//			
//		}
//		Cookie cookie = new Cookie(Constants.USER_CODEs, email);
//		cookie.setPath("/");
//		cookie.setMaxAge(12*3600);
//		response.addCookie(cookie);
//	}
//	
//	private String getCookieValue(Cookie[] cookies, String key) {
//		if (cookies == null || cookies.length == 0 || StringUtils.isBlank(key))
//			return null;
//		for (int i = 0, size = cookies.length; i < size; i++) {
//			if (cookies[i].getName().equals(key)) {
//				return cookies[i].getValue();
//			}
//		}
//		return null;
//	}
//	
//	private User getUserByEmail(String email) {
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("userEmail", email);
//		return this.userDao.getUser(params);
//	}

}
