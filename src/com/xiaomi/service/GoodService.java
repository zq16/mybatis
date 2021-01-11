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
		// ����HashMap�洢GoodName��Good����
		HashMap<String, Good> goodsMap = new HashMap<>();
		// ʹ��Stream������ʽ����goodsList������GoodName��Good�������goodsMap��
		// ����Map��(GoodName)���ظ��������������ֵ�������ļ�����ظ�������滻Ϊ�µ�ֵ
		goodsList.stream().forEach(goods -> goodsMap.put(goods.getGoodName(), goods));
		// ���goodsList
		goodsList.clear();
		// ����goodsMap�ļ�����goodsMap������ֵ����goodsList
		goodsMap.keySet().stream().forEach(name -> goodsList.add(goodsMap.get(name)));
		return goodsList;
	}
}
