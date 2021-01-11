package com.xiaomi.test;

import java.util.List;

import org.junit.Test;

import com.xiaomi.entity.Good;
import com.xiaomi.service.GoodService;

class testGood {

	@Test
	void test() {
		GoodService service = new GoodService();
		List<Good> goodList = service.selectByGoodNameVagueLike("a");
		for(Good g:goodList) {
			System.out.println(g);
		}
	}
	
	@Test
	void test1() {
		GoodService service = new GoodService();
		List<Good> goodList = service.findGoodByGoodName("С��5");
		System.out.println(goodList);
	}

}
