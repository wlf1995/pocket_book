package com.ibicn.hr.ENUM;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 性别
 * @author Administrator
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumSex implements IntegerValuedEnum  {
    男("男", 0, "男"),女("女", 1, "女");
	// 成员变量
	private String name;

	private int index;

	private String description;

	//构造方法
	private EnumSex(String name, int index, String description) {
		this.name = name;
		this.index = index;
		this.description = description;
	}
	
	// get set 方法
	public String getName() {
		return name;
	}


	public int getIndex() {
		return index;
	}


	public String getDescription() {
		return description;
	}

}
