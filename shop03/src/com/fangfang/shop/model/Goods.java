package com.fangfang.shop.model;

import org.apache.commons.io.FilenameUtils;

public class Goods {
	private int id;
	@ValidateForm(type=ValidateType.NotNull,errorMsg="商品名称不能为空")
	private String name;
	@ValidateForm(type=ValidateType.Number,errorMsg="商品的价格格式不正确，必须为数字")
	private int price;
	private String intro;
	private String img;
	@ValidateForm(type=ValidateType.Number,errorMsg="商品的库存格式不正确，必须为数字")
	private int stock;
	private int status;
	private Category category;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public String getShortName(){
		return FilenameUtils.getName(img);
	}
}
