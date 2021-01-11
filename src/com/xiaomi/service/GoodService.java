package com.xiaomi.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.xiaomi.entity.Good;
import com.xiaomi.entity.GoodColor;
import com.xiaomi.entity.GoodExample;
import com.xiaomi.entity.GoodExample.Criteria;
import com.xiaomi.entity.GoodType;
import com.xiaomi.mapper.GoodMapper;
import com.xiaomi.util.DBUtil;

public class GoodService {

	public List<Good> findAllGood() {
		SqlSession session = DBUtil.getSession();
		GoodMapper mapper = session.getMapper(GoodMapper.class);
		List<Good> goodList = mapper.selectByExample(new GoodExample());
		session.close();
		return goodList;
	}

	public List<Good> findGoodByGoodName(String goodName) {
		SqlSession session = DBUtil.getSession();
		GoodMapper mapper = session.getMapper(GoodMapper.class);
		GoodExample example = new GoodExample();
		Criteria criteria = example.createCriteria();
		criteria.andGoodNameEqualTo(goodName);
		List<Good> goodList = mapper.selectByExample(example);
		session.close();
		return goodList;
	}

	public Good findGoodByGoodId(int goodId) {
		SqlSession session = DBUtil.getSession();
		GoodMapper mapper = session.getMapper(GoodMapper.class);
		GoodExample example = new GoodExample();
		Criteria criteria = example.createCriteria();
		criteria.andGoodIdEqualTo(goodId);
		List<Good> goodList = mapper.selectByExample(example);
		session.close();
		return goodList.size() > 0 ? goodList.get(0) : null;
	}

	public Good findGoodByNameTypeColor(Good good) {
		SqlSession session = DBUtil.getSession();
		GoodMapper mapper = session.getMapper(GoodMapper.class);
		GoodExample example = new GoodExample();
		Criteria criteria = example.createCriteria();
		criteria.andGoodNameEqualTo(good.getGoodName());
		criteria.andGoodTypeEqualTo(good.getGoodType());
		criteria.andGoodColorEqualTo(good.getGoodColor());
		List<Good> goodList = mapper.selectByExample(example);
		session.close();
		return goodList.size() > 0 ? goodList.get(0) : null;
	}

	public List<Good> selectByGoodNameVagueLike(String goodName) {
		SqlSession session = DBUtil.getSession();
		GoodMapper mapper = session.getMapper(GoodMapper.class);
		List<Good> goodList = mapper.selectByGoodNameVagueLike(goodName);
		session.close();
		return goodList;
	}

	public List<GoodColor> getGoodcList(List<Good> goodsList) {
		List<GoodColor> goodcList = new ArrayList<>();
		for (Good good : goodsList) {
			GoodColor colorName = new GoodColor(good.getGoodColor());
			if (!goodcList.contains(colorName)) {
				goodcList.add(colorName);
			}
		}
		return goodcList;
	}

	public List<GoodType> getGoodtList(List<Good> goodsList) {
		List<GoodType> goodtList = new ArrayList<>();
		for (Good good : goodsList) {
			GoodType typeName = new GoodType(good.getGoodType(), good.getGoodPrice());
			if (!goodtList.contains(typeName)) {
				goodtList.add(typeName);
			}
		}
		return goodtList;
	}

	public List<Good> duplicateRemovalGoodName(List<Good> goodsList) {
		// 创建HashMap存储GoodName和Good对象
		HashMap<String, Good> goodsMap = new HashMap<>();
		// 使用Stream流的形式遍历goodsList，并将GoodName和Good对象存入goodsMap。
		// 利用Map键(GoodName)不重复的特性往里面放值，后放入的键如果重复，则会替换为新的值
		goodsList.stream().forEach(goods -> goodsMap.put(goods.getGoodName(), goods));
		// 清空goodsList
		goodsList.clear();
		// 遍历goodsMap的键，将goodsMap中所有值存入goodsList
		goodsMap.keySet().stream().forEach(name -> goodsList.add(goodsMap.get(name)));
		return goodsList;
	}
}
