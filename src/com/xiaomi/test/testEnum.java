package com.xiaomi.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import com.xiaomi.entity.Good;
import com.xiaomi.entity.GoodColor;
import com.xiaomi.service.GoodService;

class testEnum {

	@Test
	void test1() {
		GoodService service = new GoodService();
		List<Good> goodsList = new ArrayList<>();
		Good g1 = new Good();
		g1.setGoodColor("red");
		Good g2 = new Good();
		g2.setGoodColor("blue");
		goodsList.add(g1);
		goodsList.add(g2);
		goodsList.add(g1);
		List<GoodColor> goodCList = service.getGoodcList(goodsList);
		for (GoodColor goodColor : goodCList) {
			System.out.println(goodColor);
		}
	}

}
