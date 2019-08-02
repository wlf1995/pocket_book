package com.ibicn.hr.ENUM;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 性别
 * @author Administrator
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumXueli implements IntegerValuedEnum  {
    小学("小学", 0, "小学"),
    初中("初中", 1, "初中"),
    高中("高中", 2, "高中"),
    大专("大专", 3, "大专"),
	本科("本科", 4, "本科"),
	硕士研究生("硕士研究生", 5, "硕士研究生"),
	博士研究生("博士研究生", 6, "博士研究生");
	// 成员变量
	private String name;

	private int index;

	private String description;

	//构造方法
	private EnumXueli(String name, int index, String description) {
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
