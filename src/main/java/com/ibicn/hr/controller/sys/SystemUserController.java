package com.ibicn.hr.controller.sys;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibicn.hr.ENUM.EnumYesOrNo;
import com.ibicn.hr.controller.base.BaseController;
import com.ibicn.hr.entity.sys.officeArea;
import com.ibicn.hr.entity.sys.systemRole;
import com.ibicn.hr.entity.sys.systemUser;
import com.ibicn.hr.util.BaseModel;
import com.ibicn.hr.util.PageResult;
import com.ibicn.hr.util.Result;
import com.ibicnCloud.util.CollectionUtil;
import com.ibicnCloud.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

import static com.ibicn.hr.config.RespData.writeString;


@RestController
@RequestMapping("/user")
public class SystemUserController extends BaseController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("list")
    public Result list(systemUser data, BaseModel baseModel) {
        PageResult pr = userService.list(data, baseModel);
        List<systemUser> result = pr.getContent();
        List<Map> userVector = new ArrayList<>();
        for (systemUser user : result) {
            userVector.add(getByMap(user));
        }
        PageResult pageUtil = PageResult.getPageResult(pr, userVector);
        return Result.ok(pageUtil);
    }

    @RequestMapping("get")
    public Result get(systemUser data) {
        systemUser user = userService.getById(data.getId());
        return Result.ok(getByMap(user));
    }

    @RequestMapping("saveOK")
    public Result saveOK(systemUser data) {
        if (StringUtil.isBlank(data.getUserName())) {
            return Result.failure("用户名必填");
        }
        systemUser checkUser = userService.getUsesByNameAndBianhaoNoId(data.getUserName(), "", 0);
        if (checkUser != null) {
            return Result.failure("用户名重复，请重新设置");
        }
        if (data.getStatus() == null) {
            return Result.failure("用户状态不能为空");
        }

        data.setCreatedTime(new Date());
        data.setUpdatedTime(new Date());
        data.setPassword(passwordEncoder.encode(data.getPassword()));
        userService.save(data);
        return Result.ok();
    }

    @RequestMapping("updateOK")
    public Result updateOK(systemUser data, HttpServletRequest request) {
        systemUser user = userService.getById(data.getId());
        if (user == null) {
            return Result.failure("未获取到用户");
        }
        systemUser checkUser = userService.getUsesByNameAndBianhaoNoId(data.getUserName(), "", data.getId());
        if (checkUser != null) {
            return Result.failure("用户名重复，请重新设置");
        }

        if (data.getStatus() == null) {
            return Result.failure("用户状态不能为空");
        }


        user.setUserName(data.getUserName());
        user.setRealName(data.getRealName());
        user.setStatus(data.getUserStatusIndex());
        userService.update(user);
        return Result.ok();
    }

    /**
     * 用户角色授权
     */
    @RequestMapping("/authoUser")
    public Result authoUser(systemUser data) {
        systemUser user = userService.getById(data.getId());
        if (user == null) {
            return Result.failure("未获取到用户");
        }
        JSONArray array = new JSONArray();
        List<systemRole> roles = systemRoleServiceI.getAllRole();
        for (int i = 0; i < CollectionUtil.size(roles); i++) {
            JSONObject object = new JSONObject();
            object.put("id", roles.get(i).getId());
            object.put("name", roles.get(i).getRoleName());
            array.add(object);
        }
        List<Integer> checks = new ArrayList<>();
        if (CollectionUtil.size(user.getRoles()) > 0) {
            for (systemRole role : user.getRoles()) {
                checks.add(role.getId());
            }
        }
        JSONObject result = new JSONObject();
        result.put("roles", array);
        result.put("checks", checks);
        writeString("", result);
        return Result.ok(result);
    }

    /**
     * 用户角色授权
     */
    @RequestMapping("/saveAutho")
    public Result saveAutho(systemUser data, String ids) {
        systemUser user = userService.getById(data.getId());
        if (user == null) {
            return Result.failure("未获取到用户");
        }
        if (StringUtil.isBlank(ids)) {
            return Result.failure("必须要有一个角色");
        }
        String[] id = ids.split(",");
        Set<systemRole> roles = new HashSet<>();
        for (int i = 0; i < CollectionUtil.size(id); i++) {
            if (StringUtil.isEmpty(id[i])) {
                continue;
            }
            systemRole role = systemRoleServiceI.getById(StringUtil.parseInt(id[i]));
            if (role == null) {
                return Result.failure("未获取到角色");
            }
            roles.add(role);
        }
        user.setRoles(roles);
        userService.update(user);
        return Result.ok();
    }

    //根据用户名搜索
    @RequestMapping("/getSystemUserByName")
    public Result getSystemUserByName(systemUser data, HttpServletRequest request) {
        List<systemUser> systemUserByName = userService.getSystemUserByName(data.getRealName(), data.getId());
        List<Map> list = new ArrayList<>();
        for (systemUser user : systemUserByName) {
            list.add(getByMap(user));
        }
        return Result.ok(list);
    }

    //仅根据用户名搜索
    @RequestMapping("/getbyname")
    public Result getByName(systemUser data) {
        List<systemUser> systemUserByName = userService.getSystemUserByName(data.getRealName(), 0);
        List<Map> list = new ArrayList<>();
        for (systemUser user : systemUserByName) {
            list.add(getByMap(user));
        }
        return Result.ok(list);
    }


    /**
     * 修改密码
     */
    @RequestMapping("/updatePassword")
    public Result updatePassword(String oldPassword, String password, String repassword) {
        if (StringUtil.isBlank(oldPassword)) {
            return Result.failure("当前密码不能为空");
        }
        if (StringUtil.isBlank(password)) {
            return Result.failure("新密码不能为空");
        }
        if (StringUtil.isBlank(repassword)) {
            return Result.failure("确认密码不能为空");
        }
        if (!passwordEncoder.encode(oldPassword).equals(getUser().getPassword())) {
            return Result.failure("当前密码错误");
        }
        if (!StringUtil.equals(password, repassword)) {
            return Result.failure("两次密码输入不一致");
        }
        systemUser user = userService.getById(getUser().getId());
        user.setPassword(passwordEncoder.encode(repassword));
        userService.update(user);
        return Result.ok("修改成功");
    }

    /**
     * 根据id，和name获得用户，只获得用户的id和name
     */
    @RequestMapping("getuser")
    public Result getuser(systemUser data) {
        List<systemUser> list = userService.getUser(data.getRealName(), data.getId());
        List<systemUser> listUser = new ArrayList<>();
        for (systemUser user : list) {
            systemUser user1 = new systemUser();
            user1.setId(user.getId());
            user1.setRealName(user.getRealName());
            listUser.add(user1);
        }
        return Result.ok(listUser);
    }

    /**
     * 入职离职总人数
     * 可根据部门进行统计,部门为空时为所有
     * 时间为空时为所有
     */
    @RequestMapping("getRLzhi")
    public Result getRLzhi(Integer deptid, String beginDate, String endDate) {
        HashMap<String, Object> map = userService.getRLzhi(deptid, beginDate, endDate);
        return Result.ok(map);
    }

    /**
     * 入职离职总人数
     */
    @RequestMapping("getRLzhiByDept")
    public Result getRLzhiByDept(String beginDate, String endDate) {
        List<HashMap<String, Object>> map = userService.getRLzhiByDept(beginDate, endDate);
        return Result.ok(map);
    }

    private Map getByMap(systemUser user) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("realName", user.getRealName());
        map.put("userName", user.getUserName());
        map.put("userStatusIndex", user.getUserStatusIndex());
        map.put("createdTime", user.getCreatedTime());
        map.put("status", user.getStatus());
        return map;
    }

}