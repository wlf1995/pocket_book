package com.ibicn.hr.controller.sys;

import com.ibicn.hr.ENUM.EnumBaseStatus;
import com.ibicn.hr.entity.sys.department;
import com.ibicn.hr.entity.sys.systemUser;
import com.ibicn.hr.controller.base.BaseController;
import com.ibicn.hr.dao.sys.SystemDeptDao;
import com.ibicn.hr.util.BaseModel;
import com.ibicn.hr.util.PageResult;
import com.ibicn.hr.util.Result;
import com.ibicnCloud.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.*;

@RestController
@RequestMapping("/systemdept")
public class SystemDeptController extends BaseController {

    @Autowired
    SystemDeptDao systemDeptDao;

    @RequestMapping("list")
    public Result list(department data, BaseModel baseModel) {
        PageResult pr = systemDeptServiceI.list(data, baseModel.setOrder("asc"));
        List<department> content = pr.getContent();
        List<Map> list = new ArrayList<>();
        for (department role : content) {
            list.add(getByMap(role));
        }
        return Result.ok(PageResult.getPageResult(pr, list));
    }

    @RequestMapping("get")
    public Result get(department data) {
        department role = systemDeptServiceI.getById(data.getId());
        return Result.ok(getByMap(role));
    }

    @RequestMapping("saveOK")
    public Result saveOK(department data) {
        Result check = check(data);
        if (!check.getCode().equals(Result.StatusCode.SUCCESS_CODE)) {
            return check;
        }
        if(data.getParent_id()!=null&&data.getParent_id().getId()==0){
            data.setParent_id(null);
        }
        data.setCreatedTime(new Date());
        data.setStatus(EnumBaseStatus.正常);
        systemDeptServiceI.save(data);
        return Result.ok();
    }

    @RequestMapping("updateOK")
    public Result updateOK(department data) {
        department systemDept = systemDeptServiceI.getById(data.getId());
        if (systemDept == null) {
            return Result.failure("未获取到部门");
        }
        Result check = check(data);
        if (!check.getCode().equals(Result.StatusCode.SUCCESS_CODE)) {
            return check;
        }
        if (data.getParent_id()!=null&&data.getParent_id().getId()!=null){
            Boolean die = this.isDie(data.getId(), data.getParent_id().getId());
            if (die){
                return Result.failure("设置父部门冲突,请重新选择");
            }
        }else {
            data.setParent_id(null);
        }
        systemDept.setDepartmentName(data.getDepartmentName());
        systemDept.setParent_id(data.getParent_id());
        systemDeptServiceI.update(systemDept);
        return Result.ok();
    }

    @RequestMapping("deleteOK")
    public Result deleteOK(Integer id) {
        if (id == null) {
            return Result.failure("未获取到办公区");
        }
        systemDeptServiceI.deleteById(id);
        return Result.ok();
    }
    @RequestMapping("getByDict")
    public Result getByDict() {
        List<department> content = systemDeptServiceI.getAllBangonqu();
        List<Map> list = new ArrayList<>();
        for (department systemDept : content) {
            HashMap<String,Object> map=new HashMap<>();
            map.put("id", systemDept.getId());
            map.put("name", systemDept.getDepartmentName());
            list.add(map);
        }
        return Result.ok(list);
    }

    private Boolean isDie(Integer bijiao,Integer parent){
        if (bijiao.intValue()==parent){
            return true;
        }
        Specification<department> specification = (Specification<department>) (root, query, criteriaBuilder) -> {
            List<Predicate> list = new ArrayList<>();
            Join<department, department> join = root.join("parentDept", JoinType.LEFT);
            list.add(criteriaBuilder.equal(join.get("id"), bijiao));
            return criteriaBuilder.and(list.toArray(new Predicate[0]));
        };
        List<department> all = systemDeptDao.findAll(specification);
        for (department dept:all){
            Boolean die = this.isDie(dept.getId(), parent);
            if (die){
                return true;
            }
        }
        return false;
    }

    private Result check(department data) {
        if (StringUtil.isBlank(data.getDepartmentName())) {
            return Result.failure("名称不能为空");
        }
        return Result.ok();
    }

    private Map getByMap(department data) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", data.getId());
        map.put("name", data.getDepartmentName());
        map.put("createdTime", data.getCreatedTime());
        if (data.getParent_id()!=null){
            department dept=new department();
            dept.setId(data.getParent_id().getId());
            dept.setDepartmentName(data.getParent_id().getDepartmentName());
            map.put("parentDept",dept);
            map.put("parentDeptName",data.getParent_id().getDepartmentName());
            map.put("parentDeptId",data.getParent_id().getId());
        }else {
            map.put("parentDeptName","");
        }
        return map;
    }
}
