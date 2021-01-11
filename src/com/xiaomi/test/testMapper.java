package com.xiaomi.test;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;

import com.xiaomi.entity.Users;
import com.xiaomi.entity.UsersExample;
import com.xiaomi.entity.UsersExample.Criteria;
import com.xiaomi.mapper.UsersMapper;
import com.xiaomi.util.DBUtil;

class testMapper {
	
	SqlSessionFactory factory = DBUtil.getFactory();

	@Test
	void test() {
		SqlSession openSession = factory.openSession();
		UsersMapper mapper = openSession.getMapper(UsersMapper.class);
		UsersExample usersExample = new UsersExample();
		Criteria condition = usersExample.createCriteria();
		condition.andPasswordEqualTo("123456");
		List<Users> usersList = mapper.selectByExample(usersExample);
		for (Users users : usersList) {
			System.out.println(users);
		}
	}

}
