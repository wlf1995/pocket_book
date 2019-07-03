package com.ibicn.hr.ENUM;


import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author wzg
 * @ClassName projectcontroller
 * @date 2018年5月4日
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum EnumMenuType implements IntegerValuedEnum  {
    MENU("菜单", 0, "菜单"),FEATURES("功能",1,"功能");
    // 成员变量
    private String name;

    private int index;

    private String description;

    private EnumMenuType(){


    }
    //构造方法
    private EnumMenuType(String name, int index, String description) {
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
