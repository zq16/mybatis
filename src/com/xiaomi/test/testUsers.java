package com.xiaomi.test;

import org.junit.Test;

import com.xiaomi.entity.Users;
import com.xiaomi.service.UsersService;


class testUsers {

	@Test
	void test() {
		Users user = new Users("addd", "123456", "123456", "123456", "123456", "123456");
	}
	
	@Test
	void test1() {
		UsersService service = new UsersService();
		Users user = service.findUsersById(1);
		System.out.println(user);
	}
	
	@Test
	void test2() {
		UsersService service = new UsersService();
		Users user = service.findUsersById(1);
		user.setPassword("123456");
		service.updateUsers(user);
	}
	

}
