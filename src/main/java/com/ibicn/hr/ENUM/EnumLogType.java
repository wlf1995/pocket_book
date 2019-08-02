package com.ibicn.hr.ENUM;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 基本枚举类型，表示信息的状态
 * @author Administrator
 *
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumLogType implements IntegerValuedEnum  {
    新增数据("新增数据", 0, "新增数据"),查看数据("查看数据", 1, "查看数据"),删除数据("删除数据", 2, "删除数据"),修改数据("修改数据", 3, "修改数据")
    ,查询数据("查询数据", 4, "查询数据"),导出数据("导出数据", 5, "导出数据"),下载文件("下载文件", 6, "下载文件");
	// 成员变量
	private String name;

	private int index;

	private String description;

	//构造方法
	private EnumLogType(String name, int index, String description) {
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
