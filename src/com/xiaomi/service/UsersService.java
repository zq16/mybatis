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
		//�����Ự
		SqlSession session = DBUtil.getSession();
		//��ȡӳ�����
		UsersMapper mapper = session.getMapper(UsersMapper.class);
		//����û�
		int count = mapper.insert(user);
		//�ύ����
		session.commit();
		//�رջỰ
		session.close();
		return count > 0 ? true : false;
	}
	
	public boolean login(Users user) {
		//�����Ự
		SqlSession session = DBUtil.getSession();
		//��ȡӳ�����
		UsersMapper mapper = session.getMapper(UsersMapper.class);
		//���ò�ѯ����
		UsersExample example = new UsersExample();
		Criteria condition = example.createCriteria();
		condition.andUsernameEqualTo(user.getUsername());
		condition.andPasswordEqualTo(user.getPassword());
		int count = mapper.countByExample(example);
		//�رջỰ
		session.close();
		return count > 0 ? true : false;
	}
	
	public Users getLoginUsers(Users user) {
		//�����Ự
		SqlSession session = DBUtil.getSession();
		//��ȡӳ�����
		UsersMapper mapper = session.getMapper(UsersMapper.class);
		//���ò�ѯ����
		UsersExample example = new UsersExample();
		Criteria condition = example.createCriteria();
		condition.andUsernameEqualTo(user.getUsername());
		condition.andPasswordEqualTo(user.getPassword());
		List<Users> listUser = mapper.selectByExample(example);
		//�رջỰ
		session.close();
		if(listUser.size()>0) {
			return listUser.get(0);
		}else {
			return null;
		}
	}
	
	public boolean hasUser(String username) {
		//�����Ự
		SqlSession session = DBUtil.getSession();
		//��ȡӳ�����
		UsersMapper mapper = session.getMapper(UsersMapper.class);
		UsersExample example = new UsersExample();
		Criteria condition = example.createCriteria();
		condition.andUsernameEqualTo(username);
		int count = mapper.countByExample(example);
		//�رջỰ
		session.close();
		return count > 0 ? true : false;
	}
	
	public boolean validCode(String code,HttpServletRequest request) {
		return request.getSession().getAttribute("code").toString().toLowerCase().equals(code.toLowerCase());
	}
	
	public Users findUsersById(int id) {
		//�����Ự
		SqlSession session = DBUtil.getSession();
		//��ȡӳ�����
		UsersMapper mapper = session.getMapper(UsersMapper.class);
		//����������ѯ�û�
		Users user = mapper.selectByPrimaryKey(id);
		//�رջỰ
		session.close();
		return user;
	}
	
	public boolean updateUsers(Users user) {
		//�����Ự
		SqlSession session = DBUtil.getSession();
		//��ȡӳ�����
		UsersMapper mapper = session.getMapper(UsersMapper.class);
		//���������޸��û���Ϣ
		int count = mapper.updateByPrimaryKey(user);
		//�ύ����
		session.commit();
		//�رջỰ
		session.close();
		return count > 0 ? true : false;
	}
}
