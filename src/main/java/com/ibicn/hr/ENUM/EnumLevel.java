package com.ibicn.hr.ENUM;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 奖惩级别
 * @author Administrator
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumLevel implements IntegerValuedEnum  {
	惩3("正常", -3, "正常"),
	惩2("正常", -2, "正常"),
	惩1("正常", -1, "正常"),
	无奖惩("正常", 0, "正常"),
	奖3("正常", 3, "正常"),
	奖2("正常", 2, "正常"),
	奖1("正常", 1, "正常");



	// 成员变量
	private String name;

	private int index;

	private String description;

	//构造方法
	private EnumLevel(String name, int index, String description) {
		this.name = name;
		this.index = index;
		this.description = description;
	}
	
	// get set 方法
	@Override
	public String getName() {
		return name;
	}


	@Override
	public int getIndex() {
		return index;
	}


	public String getDescription() {
		return description;
	}

}
