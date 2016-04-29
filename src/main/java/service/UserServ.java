package service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.User;
import model.Visitor;
import tool.ResponseObject;

public interface UserServ {
	
	ResponseObject<Visitor> getOrRegisterVisitor(HttpServletRequest req);
	
	ResponseObject<User> loginOperation(HttpServletRequest req, HttpServletResponse response, User user);
	
	ResponseObject<Boolean> registerUser(HttpServletResponse response, String email);
	
	ResponseObject<Boolean> verifyIdentity(HttpServletRequest req, String email, String identity, Boolean is4ReLogin);
	
	ResponseObject<User> doRegisterUser(HttpServletRequest req, HttpServletResponse response, User user);
	
	ResponseObject<User> updateUser(HttpServletRequest req, User user);
	
	ResponseObject<Boolean> forgetPwd(HttpServletResponse response, String email);
	
	ResponseObject<User> reLogin4ForgetPwd(HttpServletRequest req, HttpServletResponse response, User user);
	
	ResponseObject<User> userVisit(HttpServletRequest req);
}
