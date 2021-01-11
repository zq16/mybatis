package com.xiaomi.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.catalina.User;
import org.apache.ibatis.session.SqlSession;

import com.xiaomi.entity.Users;
import com.xiaomi.entity.UsersExample;
import com.xiaomi.entity.UsersExample.Criteria;
import com.xiaomi.mapper.UsersMapper;
import com.xiaomi.util.CommonUtil;
import com.xiaomi.util.DBUtil;

public class UsersService {
	public boolean register(Users user) {
		//开启会话
		SqlSession session = DBUtil.getSession();
		//获取映射对象
		UsersMapper mapper = session.getMapper(UsersMapper.class);
		//添加用户
		int count = mapper.insert(user);
		//提交事务
		session.commit();
		//关闭会话
		session.close();
		return count > 0 ? true : false;
	}
	
	public boolean login(Users user) {
		//开启会话
		SqlSession session = DBUtil.getSession();
		//获取映射对象
		UsersMapper mapper = session.getMapper(UsersMapper.class);
		//设置查询条件
		UsersExample example = new UsersExample();
		Criteria condition = example.createCriteria();
		condition.andUsernameEqualTo(user.getUsername());
		condition.andPasswordEqualTo(user.getPassword());
		int count = mapper.countByExample(example);
		//关闭会话
		session.close();
		return count > 0 ? true : false;
	}
	
	public Users getLoginUsers(Users user) {
		//开启会话
		SqlSession session = DBUtil.getSession();
		//获取映射对象
		UsersMapper mapper = session.getMapper(UsersMapper.class);
		//设置查询条件
		UsersExample example = new UsersExample();
		Criteria condition = example.createCriteria();
		condition.andUsernameEqualTo(user.getUsername());
		condition.andPasswordEqualTo(user.getPassword());
		List<Users> listUser = mapper.selectByExample(example);
		//关闭会话
		session.close();
		if(listUser.size()>0) {
			return listUser.get(0);
		}else {
			return null;
		}
	}
	
	public boolean hasUser(String username) {
		//开启会话
		SqlSession session = DBUtil.getSession();
		//获取映射对象
		UsersMapper mapper = session.getMapper(UsersMapper.class);
		UsersExample example = new UsersExample();
		Criteria condition = example.createCriteria();
		condition.andUsernameEqualTo(username);
		int count = mapper.countByExample(example);
		//关闭会话
		session.close();
		return count > 0 ? true : false;
	}
	
	public boolean validCode(String code,HttpServletRequest request) {
		return request.getSession().getAttribute("code").toString().toLowerCase().equals(code.toLowerCase());
	}
	
	public Users findUsersById(int id) {
		//开启会话
		SqlSession session = DBUtil.getSession();
		//获取映射对象
		UsersMapper mapper = session.getMapper(UsersMapper.class);
		//根据主键查询用户
		Users user = mapper.selectByPrimaryKey(id);
		//关闭会话
		session.close();
		return user;
	}
	
	public boolean updateUsers(Users user) {
		//开启会话
		SqlSession session = DBUtil.getSession();
		//获取映射对象
		UsersMapper mapper = session.getMapper(UsersMapper.class);
		//根据主键修改用户信息
		int count = mapper.updateByPrimaryKey(user);
		//提交事务
		session.commit();
		//关闭会话
		session.close();
		return count > 0 ? true : false;
	}
}
