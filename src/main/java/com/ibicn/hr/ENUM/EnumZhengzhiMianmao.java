package com.ibicn.hr.ENUM;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 政治面貌
 * @author Administrator
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumZhengzhiMianmao implements IntegerValuedEnum  {
    党员("党员", 0, "党员"),团员("团员", 1, "团员"),
	群众("群众", 2, "群众");
	// 成员变量
	private String name;

	private int index;

	private String description;

	//构造方法
	private EnumZhengzhiMianmao(String name, int index, String description) {
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
