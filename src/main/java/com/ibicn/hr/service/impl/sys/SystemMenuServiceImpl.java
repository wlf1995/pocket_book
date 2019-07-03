package com.ibicn.hr.service.impl.sys;

import com.ibicn.hr.ENUM.EnumMenuType;
import com.ibicn.hr.bean.sys.SystemMenu;
import com.ibicn.hr.bean.sys.SystemUser;
import com.ibicn.hr.dao.sys.SystemMenuDao;
import com.ibicn.hr.service.sys.SystemMenuServiceI;
import com.ibicn.hr.util.BaseModel;
import com.ibicnCloud.util.CollectionUtil;
import com.ibicnCloud.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Transactional
@Service
public class SystemMenuServiceImpl implements SystemMenuServiceI {


    @Autowired
    SystemMenuDao menuDao;

    /**
     * 根据名称搜索菜单,如果传递一个菜单ID则不获取该菜单ID其他的
     *
     * @param name
     * @param id
     * @return
     */
    @Override
    public List<SystemMenu> getMenuOrNotInMenu(String name, int id) {
        Specification<SystemMenu> specification = (Specification<SystemMenu>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            // 第一个userId为CloudServerDao中的字段，第二个userId为参数
            Predicate p1 = criteriaBuilder.like(root.get("name"), name);
            list.add(p1);
            if (id > 0) {
                Predicate p2 = criteriaBuilder.equal(root.get("id"), id);
                list.add(p2);
            }
            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        };
        List<SystemMenu> list = menuDao.findAll(specification);
        if (CollectionUtil.size(list) > 0) {
            return list;
        }
        return Collections.emptyList();
    }

    /**
     * 获取全部菜单
     *
     * @return
     */
    @Override
    public List<SystemMenu> getAllMenu(EnumMenuType type) {
        Specification<SystemMenu> specification = (Specification<SystemMenu>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            // 第一个userId为CloudServerDao中的字段，第二个userId为参数
            if(type!=null){
                Predicate p1 = criteriaBuilder.equal(root.get("type"), type.getIndex());
                list.add(p1);
            }
            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        };
        List<SystemMenu> list = menuDao.findAll(specification);
        if (CollectionUtil.size(list) > 0) {
            return list;
        }
        return Collections.emptyList();
    }

    /**
     * 根据角色ID获取菜单
     *
     * @param ids
     * @return
     */
    @Override
    public List<SystemMenu> getMenyByRoleIds(String ids) {
        if (StringUtil.isBlank(ids)) {
            return Collections.emptyList();
        }
        Specification<SystemMenu> specification = (Specification<SystemMenu>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            // 第一个userId为CloudServerDao中的字段，第二个userId为参数
            Join<Object, Object> parentMenu = root.join("parentMenu", JoinType.LEFT);
            Predicate p1 = criteriaBuilder.isNotNull(parentMenu.get("id"));
            list.add(p1);
            CriteriaBuilder.In<Integer> in = criteriaBuilder.in(root.get("id"));
            String idlist[] = ids.split(",");
            for (String id : idlist) {
                if (StringUtil.isNotEmpty(id)) {
                    in.value(StringUtil.parseInt(id));
                }
            }
            list.add(in);
            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        };
        List<SystemMenu> list = menuDao.findAll(specification);
        if (CollectionUtil.size(list) > 0) {
            return list;
        }
        return Collections.emptyList();
    }

    /**
     * 获取父节点为空的父级菜单
     *
     * @return
     */
    @Override
    public List<SystemMenu> getPanentMenu(EnumMenuType type) {
        Specification<SystemMenu> specification = (Specification<SystemMenu>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            // 第一个userId为CloudServerDao中的字段，第二个userId为参数
            Join<Object, Object> parentMenu = root.join("parentMenu", JoinType.LEFT);
            Predicate p1 = criteriaBuilder.isNull(parentMenu.get("id"));
            list.add(p1);
            if(type!=null){
                Predicate p2 = criteriaBuilder.equal(root.get("type"), type.getIndex());
                list.add(p2);
            }
            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        };
        List<SystemMenu> list = menuDao.findAll(specification);
        if (CollectionUtil.size(list) > 0) {
            return list;
        }
        return null;
    }

    /**
     * 根据用户获取菜单树
     */
    @Override
    public List<SystemMenu> getMenuByUser(SystemUser user) {
        List<SystemMenu> list = menuDao.getMenuByUser(user.getId());
        if (CollectionUtil.size(list) > 0) {
            return list;
        }
        return Collections.emptyList();
    }

    /**
     * 获取用户所有角色
     */
    @Override
    public List<String> getRoleIdByUser(SystemUser user) {
        List<String> list = menuDao.getRoleIdByUser(user.getId());
        if (CollectionUtil.size(list) > 0) {
            return list;
        }
        return Collections.emptyList();
    }

    /**
     * 获取用户所有菜单路径
     */
    @Override
    public List<String> getMenyPathIdByUser(SystemUser user, EnumMenuType type) {
        List<String> list = menuDao.getMenyPathIdByUser(user.getId(), type.getIndex());
        if (CollectionUtil.size(list) > 0) {
            return list;
        }
        return Collections.emptyList();
    }

    @Override
    public SystemMenu getById(Integer id) {
        return menuDao.getOne(id);
    }

    @Override
    public void save(SystemMenu menu) {
        menuDao.save(menu);
    }

    @Override
    public void update(SystemMenu menu) {
        menuDao.save(menu);
    }


    @Override
    public Page<SystemMenu> list(SystemMenu data, BaseModel baseModel) {
        Pageable pageable = PageRequest.of(baseModel.getPage() - 1, baseModel.getLimit());

        Specification<SystemMenu> specification = (Specification<SystemMenu>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            // 第一个userId为CloudServerDao中的字段，第二个userId为参数
            if (StringUtil.isNotEmpty(data.getName())) {
                Predicate p1 = criteriaBuilder.like(root.get("name"), data.getName());
                list.add(p1);
            }
            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        };

        Page<SystemMenu> all = menuDao.findAll(specification, pageable);
        return all;
    }
}
