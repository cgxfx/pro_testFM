package controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import service.UserServ;
import tool.ResponseObject;

@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserServ userServ;
	
	@ResponseBody
	@RequestMapping(value = "/visit", method = RequestMethod.POST)
	public ResponseObject<User> getOrRegisterVisitor(HttpServletRequest req) {
		return this.userServ.userVisit(req);
	}
	
	@ResponseBody
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseObject<User> loginAsUser(
			HttpServletRequest req, 
			HttpServletResponse response,
			@RequestBody Map<String,Object> params) {
		User user = new User();
		user.setUserEmail(params.get("userEmail").toString());
		user.setUserPwd(params.get("userPwd").toString());
		return this.userServ.loginOperation(req, response, user);
	}
	
	@ResponseBody
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseObject<Boolean> registerAsUser(
			HttpServletResponse response, @RequestBody Map<String,Object> params) {
		return this.userServ.registerUser(response, params.get("userEmail").toString());
	}
	
	@ResponseBody
	@RequestMapping(value = "/verifyIdentity", method = RequestMethod.POST)
	public ResponseObject<Boolean> verifyIdentity(
			HttpServletRequest req,
			@RequestBody Map<String,Object> params) {
		return this.userServ.verifyIdentity(req, params.get("userEmail").toString(), 
				params.get("identity").toString(), Boolean.parseBoolean(params.get("is4ReLogin").toString()));
	}
	
	@ResponseBody
	@RequestMapping(value = "/doRegister", method = RequestMethod.POST)
	public ResponseObject<User> doRegisterAsUser(
			HttpServletRequest req,
			HttpServletResponse response,
			@RequestBody Map<String,Object> params) {
		User user = new User();
		user.setUserEmail(params.get("userEmail").toString());
		user.setUserPwd(params.get("userPwd").toString());
		if (params.get("nickName") != null)
			user.setNickName(params.get("nickName").toString());
		return this.userServ.doRegisterUser(req, response, user);
	}
 }
