package com.ibicn.hr.entity.sys;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ibicn.hr.ENUM.EnumMenuType;
import com.ibicn.hr.ENUM.EnumUtil;
import com.ibicnCloud.util.CollectionUtil;
import com.ibicnCloud.util.StringUtil;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "systemMenu")
public class SystemMenu implements Serializable {

    /**
     * 主键
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 菜单的名称
     */
    @Column(name = "menuname")
    private String menuName;

    /**
     * 菜单的访问url路径
     */
    @Column(name = "path")
    private String path;

    /**
     * 0为菜单，1为功能。功能只校验权限，不做显示。
     */
    @Column(name = "type")
    @Enumerated
    private EnumMenuType type;
    @Transient
    private String typeIndex;

    /**
     * 上级菜单的外键
     */
    @ManyToOne
    @JoinColumn(name = "parent_id")
    private SystemMenu parent_id;
    @Transient
    private String pid;
    /**
     * 创建的时间
     */
    @Column(name = "creattime")
    private Date createdTime;

    /**
     * 最后修改的时间
     */
    @Column(name = "updatetime")
    private Date updateedTime;

    /**
     * 修改的次数
     */
    @Column(name = "updatedcount")
    private Integer updatedCount;

    /**
     * 菜单排序号
     */
    @Column(name = "sort")
    private Integer sort;

    @JSONField(serialize = false)
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Set<SystemMenu> childs = new HashSet<>();


    @Transient
    @JSONField(serialize = false)
    private List<SystemMenu> sortChilds = new ArrayList<>();


    @JSONField(serialize = false)
    @ManyToMany(targetEntity = SystemRole.class)
    @JoinTable(name = "systemRoleMenu",
            joinColumns = @JoinColumn(name = "systemMenu_id", referencedColumnName = "id", nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "systemRole_id", referencedColumnName = "id", nullable = false, updatable = false))
    private Set<SystemRole> roles = new HashSet<>();

    @JsonIgnore
    public void setType(String type) {
        this.type = (EnumMenuType) EnumUtil.valueOf(EnumMenuType.class, type);
    }
    public void setType(EnumMenuType type) {
        this.type = type;
    }
    public void setTypeIndex(String typeIndex) {
        this.typeIndex = typeIndex;
        this.type = (EnumMenuType) EnumUtil.valueOf(EnumMenuType.class, typeIndex);
    }

    public boolean isIfSetParent(SystemMenu systemMenu) {
        if (systemMenu == null) {
            //如果是空，则该节点将被设置成根节点
            return true;
        } else if (this.equals(systemMenu)) {
            //必须满足，1 该节点不是本身节点；2 该节点不能是自己的子节点
            return false;
        } else if (this.isParentOf(systemMenu)) {
            //必须满足，1 该节点不是本身节点；2 该节点不能是自己的子节点
            return false;
        }
        return true;
    }

    /**
     * 覆写equals方法
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof SystemMenu)) {
            return false;
        }
        SystemMenu systemMenu = (SystemMenu) o;
        return systemMenu.id ==(this.id);
    }

    /**
     * 是否是根节点
     */
    public boolean ifRoot() {
        return (parent_id == null);
    }

    /**
     * 是否是另一个的父节点
     */
    public boolean isParentOf(SystemMenu systemMenu) {
        if (systemMenu == null || this.equals(systemMenu)) {
            /*如果对方为空*/
            return false;
        } else if (systemMenu.ifRoot()) {
            /*如果对方为根,返回FALSE*/
            return false;
        } else {
            SystemMenu parentMe = systemMenu.getParent_id();
            if (this.equals(parentMe)) {
                /*如果对方的父节点是自己,则返回TRUE*/
                return true;
            } else if (parentMe == null) { //假如父节点为空,则返回false
                return false;
            } else {
                /*判断对方的父节点是否是自己的孩子,进行递归*/
                return isParentOf(parentMe);
            }
        }
    }

    public String getPid() {
        if (this.parent_id != null) {
            this.pid = StringUtil.format(this.parent_id.getId() + "");
        }
        return pid;
    }

    public List<SystemMenu> getSortChilds() {
        if (CollectionUtil.size(this.childs) > 0) {
            List<SystemMenu> list = new ArrayList<>(this.childs);
            Collections.sort(list, new Comparator<SystemMenu>() {
                @Override
                public int compare(SystemMenu o1, SystemMenu o2) {
                    if (o1.getSort().intValue() == o2.getSort()) {
                        return o1.getId().compareTo(o2.getId());
                    } else {
                        return o1.getSort().compareTo(o2.getSort());
                    }
                }
            });
            return list;
        }
        return Collections.emptyList();
    }

}
