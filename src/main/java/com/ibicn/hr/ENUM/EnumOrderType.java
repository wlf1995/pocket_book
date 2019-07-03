package com.ibicn.hr.ENUM;

/**
 * 订单状态（）
 * @author Administrator
 *
 */
public enum EnumOrderType implements IntegerValuedEnum  {
	销售("销售", 0, "销售"), 有账期订单("有账期订单", 4, "有账期订单"),换货("换货", 1, "换货"),补发票("补发票", 2, "补发票"),补发货("补发货", 2, "补发货"),费用("费用", 3, "费用"),集采("集采", 6, "集采"),试样("试样", 5, "试样"),秒杀("秒杀", 7, "秒杀");

	// 成员变量
	private String name;

	private int index;
	
	private String description;
	
	//构造方法
	private EnumOrderType(String name, int index, String description) {
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
