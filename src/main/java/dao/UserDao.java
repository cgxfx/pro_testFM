package dao;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import model.User;
import model.Visitor;

public interface UserDao {
	
	public int createVisitor(Visitor visitor);
	
	public Visitor getVisitor(@Param("id")String visitorId);
	
	public User getUser(@Param("params")Map<String, Object> params);
	
	public int createUser(User user);
	
	public int updateUser(User user);
}
