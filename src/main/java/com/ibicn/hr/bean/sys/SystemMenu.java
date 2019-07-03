package com.ibicn.hr.bean.sys;

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
@Table(name = "systemmenu")
public class SystemMenu implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "path")
    private String path;

    @Column(name = "type")
    @Enumerated
    private EnumMenuType type;
    @Transient
    private String typeIndex;

    @ManyToOne
    @JoinColumn(name = "parentmenu")
    private SystemMenu parentMenu;

    @Column(name = "createdtime")
    private Date createdTime;

    @Column(name = "icon")
    private String icon;

    @Column(name = "sort")
    private Integer sort;
    @Transient
    private boolean ifSetParent;
    @Transient
    private String pid;

    @JSONField(serialize = false)
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "parentmenu")
    private Set<SystemMenu> childs = new HashSet<>();


    @Transient
    @JSONField(serialize = false)
    private List<SystemMenu> sortChilds = new ArrayList<>();


    @JSONField(serialize = false)
    @ManyToMany(targetEntity = SystemRole.class)
    @JoinTable(name = "role_menu",
            joinColumns = @JoinColumn(name = "menuid", referencedColumnName = "id", nullable = false, updatable = false),
            inverseJoinColumns = @JoinColumn(name = "roleid", referencedColumnName = "id", nullable = false, updatable = false))
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
        this.type = this.type = (EnumMenuType) EnumUtil.valueOf(EnumMenuType.class, typeIndex);
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
        return (parentMenu == null);
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
            SystemMenu parentMe = systemMenu.getParentMenu();
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
        if (this.parentMenu != null) {
            this.pid = StringUtil.format(this.parentMenu.getId() + "");
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
