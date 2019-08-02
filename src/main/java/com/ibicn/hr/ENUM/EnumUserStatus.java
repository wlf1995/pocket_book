package com.ibicn.hr.ENUM;


import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author wzg
 * @ClassName projectcontroller
 * @date 2018年5月4日
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumUserStatus implements IntegerValuedEnum  {
    TINGYONG("停用", 0, "停用"),ZHENGCHANG("正常",1,"正常");
    // 成员变量
    private String name;

    private int index;

    private String description;

    private EnumUserStatus(){


    }
    //构造方法
    private EnumUserStatus(String name, int index, String description) {
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
