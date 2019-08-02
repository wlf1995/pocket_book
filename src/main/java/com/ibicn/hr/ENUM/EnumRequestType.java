package com.ibicn.hr.ENUM;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 基本枚举类型，请求类型
 * @author Administrator
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumRequestType implements IntegerValuedEnum  {
	Post("post", 2, "post"),Get("get", 1, "get");
	// 成员变量
	private String name;

	private int index;

	private String description;

	//构造方法
	private EnumRequestType(String name, int index, String description) {
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
