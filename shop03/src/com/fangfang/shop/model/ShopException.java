package com.fangfang.shop.model;

public class ShopException extends RuntimeException {
	private static final long serialVersionUID = 1486097986998746859L;
	
	public ShopException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public ShopException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
		// TODO Auto-generated constructor stub
	}

	public ShopException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public ShopException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public ShopException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}
}
