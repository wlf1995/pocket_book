package com.ibicn.hr.entity.personnelfile;

import com.ibicn.hr.entity.base.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author 王立方
 * @Description 资料扫描件
 * @Date 9:13 2019/8/2
 **/
@Getter
@Setter
@Entity
@Table(name = "attachment")
public class Attachment extends BaseEntity implements Serializable {
    /**
     * 主键
     **/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    /**
     * 扫描件名称-扫描件名称，比如身份证正面、身份证反面
     **/
    @Column(name = "attachmentname")
    private String attachmentName;

    /**
     * 扫描件路径-一般是相对地址或者网络地址。多个图片，用英文逗号分隔
     **/
    @Column(name = "attachmenturl")
    private String attachmentUrl;
}
