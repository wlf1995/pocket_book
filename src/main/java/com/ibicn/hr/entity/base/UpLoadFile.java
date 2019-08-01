package com.ibicn.hr.entity.base;

import com.ibicn.hr.entity.sys.SystemUser;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "uploadfile")
public class UpLoadFile {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;



    @ManyToOne
    @JoinColumn(name = "systemuser")
    private SystemUser systemUser;

    @Column(name = "fileurls", columnDefinition = "TEXT")
    private String fileUrls;

    @Column(name = "note", columnDefinition = "TEXT")
    private String note;

    @Column(name = "addtime")
    private String addTime;

    @Column(name = "creattime")
    private Date createdTime;

    @Column(name = "updatetime")
    private Date updateedTime;

}
