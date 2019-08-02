package com.ibicn.hr.ENUM;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 基本枚举类型，表示信息的状态
 * @author Administrator
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumBaseStatus implements IntegerValuedEnum  {
	停用("停用", 0, "停用"),正常("正常", 1, "正常"),;
	

	// 成员变量
	private String name;

	private int index;
	
	private String description;
	
	//构造方法
	private EnumBaseStatus(String name, int index, String description) {
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
