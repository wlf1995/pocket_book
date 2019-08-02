package com.ibicn.hr.model.person;

import com.ibicn.hr.entity.person.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @param
 * @Author 王立方
 * @Description 用来接收前台传来的 person 对象
 * @Date 17:16 2019/8/2
 * @return
 **/
@Getter
@Setter
public class PersonModel {

    private Person person;
    /**
     * 对应的扫描文件
     **/
    private List<Attachment> attachmentSet;

    /**
     * 对应的人事档案公司变动履历表
     **/
    private List<CompanyCV> companyCVSet;

    /**
     * 对应的学习和工作简历表
     **/
    private List<CV> cvSet;

    /**
     * 对应的家庭成员表
     **/
    private List<Family> familySet;

}
