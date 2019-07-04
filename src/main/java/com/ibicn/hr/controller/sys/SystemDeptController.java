package com.ibicn.hr.controller.sys;

import com.ibicn.hr.ENUM.EnumBaseStatus;
import com.ibicn.hr.bean.sys.SystemDept;
import com.ibicn.hr.bean.sys.SystemUser;
import com.ibicn.hr.controller.base.BaseController;
import com.ibicn.hr.dao.sys.SystemDeptDao;
import com.ibicn.hr.util.BaseModel;
import com.ibicn.hr.util.PageUtil;
import com.ibicn.hr.util.Result;
import com.ibicn.hr.util.StatusCode;
import com.ibicnCloud.util.StringUtil;
import org.apache.tools.ant.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/systemdept")
public class SystemDeptController extends BaseController {

    @Autowired
    SystemDeptDao systemDeptDao;

    @RequestMapping("list")
    public Result list(SystemDept data, BaseModel baseModel) {
        Page<SystemDept> pr = systemDeptServiceI.list(data, baseModel.setOrder("asc"));
        List<SystemDept> content = pr.getContent();
        List<Map> list = new ArrayList<>();
        for (SystemDept role : content) {
            list.add(getByMap(role));
        }
        return Result.ok(PageUtil.getPageUtil(pr, list));
    }

    @RequestMapping("get")
    public Result get(SystemDept data) {
        SystemDept role = systemDeptServiceI.getById(data.getId());
        return Result.ok(getByMap(role));
    }

    @RequestMapping("saveOK")
    public Result saveOK(SystemDept data) {
        Result check = check(data);
        if (!check.getCode().equals(StatusCode.SUCCESS_CODE)) {
            return check;
        }
        if(data.getParentDept()!=null&&data.getParentDept().getId()==0){
            data.setParentDept(null);
        }
        data.setCreatedTime(new Date());
        data.setStatus(EnumBaseStatus.正常);
        systemDeptServiceI.save(data);
        return Result.ok();
    }

    @RequestMapping("updateOK")
    public Result updateOK(SystemDept data, HttpServletRequest request) {
        SystemDept systemDept = systemDeptServiceI.getById(data.getId());
        if (systemDept == null) {
            return Result.failure("未获取到部门");
        }
        Result check = check(data);
        if (!check.getCode().equals(StatusCode.SUCCESS_CODE)) {
            return check;
        }
        Boolean die = this.isDie(data.getId(), data.getParentDept().getId());
        if (die){
            return Result.failure("设置父部门冲突,请重新选择");
        }
        if(data.getParentDept()!=null&&data.getParentDept().getId()==0){
            data.setParentDept(null);
        }
        systemDept.setName(data.getName());
        systemDept.setSysUser(data.getSysUser());
        if (data.getParentDept()!=null){
            systemDept.setParentDept(data.getParentDept());
        }
        systemDeptServiceI.update(systemDept);
        return Result.ok();
    }

    @RequestMapping("deleteOK")
    public Result deleteOK(Integer id) {
        if (id == null) {
            return Result.failure("未获取到办公区");
        }
        systemDeptServiceI.delete(id);
        return Result.ok();
    }
    @RequestMapping("getByDict")
    public Result getByDict() {
        List<SystemDept> content = systemDeptServiceI.getAllBangonqu();
        List<Map> list = new ArrayList<>();
        for (SystemDept systemDept : content) {
            HashMap<String,Object> map=new HashMap<>();
            map.put("id", systemDept.getId());
            map.put("name", systemDept.getName());
            list.add(map);
        }
        return Result.ok(list);
    }

    private Boolean isDie(Integer bijiao,Integer parent){
        if (bijiao.intValue()==parent){
            return true;
        }
        Specification<SystemDept> specification = (Specification<SystemDept>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            Join<SystemDept, SystemDept> join = root.join("parentDept", JoinType.LEFT);
            list.add(criteriaBuilder.equal(join.get("id"), bijiao));
            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        };
        List<SystemDept> all = systemDeptDao.findAll(specification);
        for (SystemDept dept:all){
            Boolean die = this.isDie(dept.getId(), parent);
            if (die){
                return true;
            }
        }
        return false;
    }

    private Result check(SystemDept data) {
        if (StringUtil.isBlank(data.getName())) {
            return Result.failure("名称不能为空");
        }
        return Result.ok();
    }

    private Map getByMap(SystemDept data) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", data.getId());
        map.put("name", data.getName());
        map.put("createdTime", data.getCreatedTime());
        if (data.getSysUser()!=null){
            SystemUser user=new SystemUser();
            user.setId(data.getSysUser().getId());
            user.setRealName(data.getSysUser().getRealName());
            map.put("sysUser",user);
            map.put("sysUserName",data.getSysUser().getRealName());
            map.put("sysUserId",data.getSysUser().getId());
        }else {
            map.put("sysUserName","");
        }
        if (data.getParentDept()!=null){
            SystemDept dept=new SystemDept();
            dept.setId(data.getParentDept().getId());
            dept.setName(data.getParentDept().getName());
            map.put("parentDept",dept);
            map.put("parentDeptName",data.getParentDept().getName());
            map.put("parentDeptId",data.getParentDept().getId());
        }else {
            map.put("parentDeptName","");
        }
        return map;
    }
}
