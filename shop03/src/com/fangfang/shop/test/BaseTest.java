package com.fangfang.shop.test;

import com.fangfang.shop.utils.DaoUtil;

public class BaseTest {
	public BaseTest(){
		DaoUtil.diDao(this);
	}
}
