package com.ibicn.hr.controller.sys;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibicn.hr.ENUM.EnumUserStatus;
import com.ibicn.hr.ENUM.EnumYesOrNo;
import com.ibicn.hr.bean.sys.Bangongqu;
import com.ibicn.hr.bean.sys.SystemRole;
import com.ibicn.hr.bean.sys.SystemUser;
import com.ibicn.hr.controller.base.BaseController;
import com.ibicn.hr.shiro.token.TokenManager;
import com.ibicn.hr.util.*;
import com.ibicnCloud.util.CollectionUtil;
import com.ibicnCloud.util.MD5Util;
import com.ibicnCloud.util.StringUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

import static com.ibicn.hr.config.RespData.writeString;


@RestController
@RequestMapping("/user")
public class SystemUserController extends BaseController {

    @RequestMapping("list")
    public Result list(SystemUser data, BaseModel baseModel) {
        Page<SystemUser> pr = userService.list(data, baseModel);
        List<SystemUser> result = pr.getContent();
        Vector<Map> userVector = new Vector<>();
        for (SystemUser user : result) {
            userVector.add(getByMap(user));
        }
        PageUtil pageUtil = PageUtil.getPageUtil(pr, userVector);
        return Result.ok(pageUtil);
    }

    @RequestMapping("get")
    public Result get(SystemUser data) {
        SystemUser user = userService.getById(data.getId());
        return Result.ok(getByMap(user));
    }

    @RequestMapping("saveOK")
    public Result saveOK(SystemUser data) {
        if (StringUtil.isBlank(data.getUserName())) {
            return Result.failure("用户名必填");
        }
        SystemUser checkUser = userService.getUsesByNameAndBianhaoNoId(data.getUserName(), "", 0);
        if (checkUser != null) {
            return Result.failure("用户名重复，请重新设置");
        }
        if (StringUtil.isBlank(data.getUserBianhao())) {
            return Result.failure("用户编号必填");
        }
        checkUser = userService.getUsesByNameAndBianhaoNoId("", data.getUserBianhao(), 0);
        if (checkUser != null) {
            return Result.failure("用户编号重复，请重新设置");
        }
        if (data.getUserStatus() == null) {
            return Result.failure("用户状态不能为空");
        }
        if (data.getSex() == null) {
            return Result.failure("性别不能为空");
        }
        if (StringUtil.isEmpty(data.getIdCard())) {
            return Result.failure("身份证号不能为空");
        }
        if (StringUtil.isEmpty(data.getMobile())) {
            return Result.failure("手机号不能为空");
        }
        if (data.getZhengzhiMianmao() == null) {
            return Result.failure("政治面貌不能为空");
        }
        if (StringUtil.isEmpty(data.getBangongquId())) {
            return Result.failure("办公区不能为空");
        }
        if (data.getXueli() == null) {
            return Result.failure("学历不能为空");
        }
        if (data.getRuzhiDate() == null) {
            return Result.failure("入职时间不能为空");
        }

        Bangongqu byId = bangongquService.getById(StringUtil.parseInt(data.getBangongquId()));
        data.setBangongqu(byId);
        data.setRegTime(new Date());
        data.setCreatedTime(new Date());
        data.setUpdateedTime(new Date());
        data.setPassword(MD5Util.md5(data.getPassword()));
        data.setUpdatePassWordDay(new Date());
        data.setZazhiStatus(EnumYesOrNo.YES);
        userService.save(data);
        return Result.ok();
    }

    @RequestMapping("updateOK")
    public Result updateOK(SystemUser data, HttpServletRequest request) {
        SystemUser user = userService.getById(data.getId());
        if (user == null) {
            return Result.failure("未获取到用户");
        }
        SystemUser checkUser = userService.getUsesByNameAndBianhaoNoId(data.getUserName(), "", data.getId());
        if (checkUser != null) {
            return Result.failure("用户名重复，请重新设置");
        }

        if (StringUtil.isBlank(data.getUserBianhao())) {
            return Result.failure("用户编号必填");
        }
        checkUser = userService.getUsesByNameAndBianhaoNoId("", data.getUserBianhao(), data.getId());
        if (checkUser != null) {
            return Result.failure("用户编号重复，请重新设置");
        }
        if (data.getUserStatus() == null) {
            return Result.failure("用户状态不能为空");
        }
        if (data.getSex() == null) {
            return Result.failure("性别不能为空");
        }
        if (StringUtil.isEmpty(data.getIdCard())) {
            return Result.failure("身份证号不能为空");
        }
        if (StringUtil.isEmpty(data.getMobile())) {
            return Result.failure("手机号不能为空");
        }
        if (data.getZhengzhiMianmao() == null) {
            return Result.failure("政治面貌不能为空");
        }
        if (StringUtil.isEmpty(data.getBangongquId())) {
            return Result.failure("办公区不能为空");
        }
        if (data.getXueli() == null) {
            return Result.failure("学历不能为空");
        }
        if (data.getRuzhiDate() == null) {
            return Result.failure("入职时间不能为空");
        }
        if(data.getLizhiDate()!=null){
            if(data.getLizhiDate().compareTo(data.getRuzhiDate())==-1){
                return Result.failure("离职时间不能早于入职时间");
            }
            user.setZazhiStatus(EnumYesOrNo.NO);
        }else {
            user.setZazhiStatus(EnumYesOrNo.YES);
        }
        user.setLizhiDate(data.getLizhiDate());
        user.setMobile(data.getMobile());
        user.setSex(data.getSex());
        user.setChushengRiqi(data.getChushengRiqi());
        user.setXueli(data.getXueli());
        user.setZhengzhiMianmao(data.getZhengzhiMianmao());
        user.setIdCard(data.getIdCard());
        user.setRuzhiDate(data.getRuzhiDate());

        user.setEmail(data.getEmail());
        user.setUserName(data.getUserName());
        if (StringUtil.isNotBlank(data.getPassword())) {
            user.setPassword(MD5Util.md5(data.getPassword()));
            user.setUpdatePassWordDay(new Date());
        }
        Bangongqu byId = bangongquService.getById(StringUtil.parseInt(data.getBangongquId()));
        if(byId==null){
            return Result.failure("办公区不存在");
        }
        user.setBangongqu(byId);
        user.setRealName(data.getRealName());
        user.setUserBianhao(data.getUserBianhao());
        user.setUserStatus(data.getUserStatusIndex());
        userService.update(user);
        return Result.ok();
    }

    /**
     * 登录方法
     *
     * @param user
     * @return
     */
    @RequestMapping("/loginOK")
    public Result loginOk(SystemUser user, String vercode, HttpServletRequest request, HttpSession session, HttpServletResponse response) throws Exception {
        String code = (String) session.getAttribute("checkCode");
        if (!code.equals(vercode)) {
            return Result.failure("验证码不正确");
        }

        if (StringUtil.isEmpty(user.getUserName())) {
            return Result.failure("用户名不能为空");
        }
        if (StringUtil.isEmpty(user.getPassword())) {
            return Result.failure("密码不能为空");
        }
        SystemUser usesByNameAndBianhaoNoId = userService.getUsesByNameAndBianhaoNoId(user.getUserName(), user.getUserBianhao(), 0);
        if (usesByNameAndBianhaoNoId == null) {
            return Result.failure("此用户不存在");
        }
        if (usesByNameAndBianhaoNoId.getLizhiDate() != null) {
            return Result.failure("此用户已离职");
        }
        if (usesByNameAndBianhaoNoId.getUserStatus() != null && usesByNameAndBianhaoNoId.getUserStatus().getIndex() == EnumUserStatus.TINGYONG.getIndex()) {
            return Result.failure("此用户已停用");
        }
        try {
            JSONObject jsonObject = new JSONObject();
            SystemUser entity = TokenManager.login(user, false);
            int dayNumber = userService.getUpdatePassWordDay(entity.getId());
            jsonObject.put("dayNumber", dayNumber);
            request.getSession().setAttribute("admin", entity);
            jsonObject.put("id", entity.getId());
            jsonObject.put("userName", entity.getUserName());
            jsonObject.put("realName", entity.getRealName());
            jsonObject.put("userBianhao", entity.getUserBianhao());
            jsonObject.put("menus", "");
            //将用户添加到cookie中 过期时间12个小时
            CookieUtil.setCookie(request, response, "userName", user.getUserName(), 60 * 60 * 12, true);
            CookieUtil.setCookie(request, response, "ZongheUrl", systemConfigService.getZongheUrl(), 60 * 60 * 12);
            CookieUtil.setCookie(request, response, "heSystemId", systemConfigService.getZongheSystemId(), 60 * 60 * 12);
            CookieUtil.setCookie(request, response, "ZongheToken", systemConfigService.getZongheToken(), 60 * 60 * 12);
            return Result.ok(jsonObject);
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            return Result.failure("密码错误");
        } catch (LockedAccountException e) {
            return Result.failure("登录失败，该用户已被冻结");
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return Result.failure("该用户不存在");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Result.ok("出现错误");
    }

    /**
     * 用户角色授权
     */
    @RequestMapping("/authoUser")
    public Result authoUser(SystemUser data) {
        SystemUser user = userService.getById(data.getId());
        if (user == null) {
            return Result.failure("未获取到用户");
        }
        JSONArray array = new JSONArray();
        List<SystemRole> roles = systemRoleServiceI.getAllRole();
        for (int i = 0; i < CollectionUtil.size(roles); i++) {
            JSONObject object = new JSONObject();
            object.put("id", roles.get(i).getId());
            object.put("name", roles.get(i).getName());
            array.add(object);
        }
        List<Integer> checks = new ArrayList<>();
        if (CollectionUtil.size(user.getRoles()) > 0) {
            for (SystemRole role : user.getRoles()) {
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
    public Result saveAutho(SystemUser data, String ids) {
        SystemUser user = userService.getById(data.getId());
        if (user == null) {
            return Result.failure("未获取到用户");
        }
        if (StringUtil.isBlank(ids)) {
            return Result.failure("必须要有一个角色");
        }
        String[] id = ids.split(",");
        Set<SystemRole> roles = new HashSet<>();
        for (int i = 0; i < CollectionUtil.size(id); i++) {
            if (StringUtil.isEmpty(id[i])) {
                continue;
            }
            SystemRole role = systemRoleServiceI.getById(StringUtil.parseInt(id[i]));
            if (role == null) {
                return Result.failure("未获取到角色");
            }
            roles.add(role);
        }
        user.setRoles(roles);
        userService.update(user);
        return Result.ok();
    }

    @RequestMapping("/loginout")
    public Result logout() {
        TokenManager.logout();
        return Result.ok("退出成功");
    }

    //根据用户名搜索
    @RequestMapping("/getSystemUserByName")
    public Result getSystemUserByName(SystemUser data, HttpServletRequest request) {
        List<SystemUser> systemUserByName = userService.getSystemUserByName(data.getRealName(), data.getId());
        List<Map> list = new ArrayList<>();
        for (SystemUser user : systemUserByName) {
            list.add(getByMap(user));
        }
        return Result.ok(list);
    }

    //仅根据用户名搜索
    @RequestMapping("/getbyname")
    public Result getByName(SystemUser data) {
        List<SystemUser> systemUserByName = userService.getSystemUserByName(data.getRealName(), 0);
        List<Map> list = new ArrayList<>();
        for (SystemUser user : systemUserByName) {
            list.add(getByMap(user));
        }
        return Result.ok(list);
    }

    /**
     * 获得验证码图片
     */
    @RequestMapping("/getImage")
    public void getImage(HttpSession session, HttpServletResponse response, String t) {
        try {
            ImageUtil.getVerificationCode(session, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改密码
     */
    @RequestMapping("/updatePassword")
    public Result updatePassword(String oldPassword, String password, String repassword, HttpServletRequest request) {
        if (StringUtil.isBlank(oldPassword)) {
            return Result.failure("当前密码不能为空");
        }
        if (StringUtil.isBlank(password)) {
            return Result.failure("新密码不能为空");
        }
        if (StringUtil.isBlank(repassword)) {
            return Result.failure("确认密码不能为空");
        }
        if (!Md5Util.md5(oldPassword).equals(getUser(request).getPassword())) {
            return Result.failure("当前密码错误");
        }
        if (!StringUtil.equals(password, repassword)) {
            return Result.failure("两次密码输入不一致");
        }
        SystemUser user = userService.getById(getUser(request).getId());
        user.setPassword(Md5Util.md5(repassword));
        user.setUpdatePassWordDay(new Date());
        userService.update(user);
        return Result.ok("修改成功");
    }

    /**
     * 根据id，和name获得用户，只获得用户的id和name
     */
    @RequestMapping("getuser")
    public Result getuser(SystemUser data) {
        List<SystemUser> list = userService.getUser(data.getRealName(), data.getId());
        List<SystemUser> listUser = new ArrayList<>();
        for (SystemUser user : list) {
            SystemUser user1 = new SystemUser();
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
    public Result getRLzhi(Integer deptid,String beginDate,String endDate){
        HashMap<String,Object> map= userService.getRLzhi(deptid,beginDate,endDate);
        return Result.ok(map);
    }

    /**
     * 入职离职总人数
     */
    @RequestMapping("getRLzhiByDept")
    public Result getRLzhiByDept(String beginDate,String endDate){
        HashMap<String,Object> map= userService.getRLzhiByDept(beginDate,endDate);
        return Result.ok();
    }
    private Map getByMap(SystemUser user) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("realName", user.getRealName());
        map.put("userName", user.getUserName());
        map.put("email", user.getEmail());
        map.put("userBianhao", user.getUserBianhao());
        map.put("userStatusIndex", user.getUserStatusIndex());
        map.put("updatePassWordDay", user.getUpdatePassWordDay());
        map.put("createdTime", user.getCreatedTime());
        map.put("regTime", user.getRegTime());
        map.put("userStatus", user.getUserStatus());
        map.put("avatar", user.getAvatar());

        map.put("mobile", user.getMobile());
        map.put("sex", user.getSex());
        map.put("chushengRiqi", user.getChushengRiqi());
        map.put("xueli", user.getXueli());
        map.put("zhengzhiMianmao", user.getZhengzhiMianmao());
        map.put("IdCard", user.getIdCard());
        map.put("ruzhiDate", user.getRuzhiDate());
        map.put("lizhiDate", user.getLizhiDate());
        map.put("zazhiStatus", user.getZazhiStatus());
        if (user.getBangongqu() != null) {
            map.put("bangongquId", user.getBangongqu().getId());
            map.put("bangongquName", user.getBangongqu().getName());
        }
        return map;
    }

}