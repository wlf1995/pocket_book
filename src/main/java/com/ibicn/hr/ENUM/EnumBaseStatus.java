package com.ibicn.hr.ENUM;

/**
 * 基本枚举类型，表示信息的状态
 * @author Administrator
 *
 */
public enum EnumBaseStatus implements IntegerValuedEnum  {
	正常("正常", 0, "正常"),停用("停用", 1, "停用");
	

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
