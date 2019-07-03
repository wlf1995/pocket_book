package com.ibicn.hr.controller.sys;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
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
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;
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
    public Result get(SystemUser data, HttpServletRequest request) {
        SystemUser user = userService.getById(data.getId());
        return Result.ok(getByMap(user));
    }

    @RequestMapping("saveOK")
    public Result saveOK(SystemUser data, HttpServletRequest request) {
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
        SystemUser user = new SystemUser();
        user.setRegTime(new Date());
        user.setCreatedTime(new Date());
        user.setUpdateedTime(new Date());
        user.setEmail(data.getEmail());
        user.setType(0);
        user.setUserBianhao(data.getUserBianhao());
        user.setUserName(data.getUserName());
        user.setPassword(MD5Util.md5(data.getPassword()));
        user.setRealName(data.getRealName());
        user.setUserStatus(data.getUserStatusIndex());
        user.setUpdatePassWordDay(new Date());
        userService.save(user);
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
        user.setEmail(data.getEmail());
        user.setUserName(data.getUserName());
        if (StringUtil.isNotBlank(data.getPassword())) {
            user.setPassword(MD5Util.md5(data.getPassword()));
            user.setUpdatePassWordDay(new Date());
        }
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
     * 根据用户编号进行登录方法
     *
     * @return
     */
    @RequestMapping("/bianhaoBYloginOk")
    public Result bianhaoBYloginOk(String userBianhao, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (StringUtil.isBlank(userBianhao)) {
            return Result.failure("未获取到用户编号，无法登陆。请重试");
        }
        if (userBianhao.indexOf("&") != -1) {
            userBianhao = userBianhao.split("&")[0];
        }
        if (userBianhao.indexOf("=") != -1) {
            userBianhao = userBianhao.split("=")[1];
        }
        SystemUser user = userService.getSystemUserByBianhao(userBianhao);
        PrincipalCollection principals = new SimplePrincipalCollection(user, "MobileRealm");
        WebSubject.Builder builder = new WebSubject.Builder(request, response);
        builder.principals(principals);
        builder.authenticated(true);
        WebSubject subject = builder.buildWebSubject();
        ThreadContext.bind(subject);
        CookieUtil.setCookie(request, response, "userName", user.getUserName(), 60 * 60 * 12, true);
        CookieUtil.setCookie(request, response, "ZongheUrl", systemConfigService.getZongheUrl(), 60 * 60 * 12);
        CookieUtil.setCookie(request, response, "heSystemId", systemConfigService.getZongheSystemId(), 60 * 60 * 12);
        CookieUtil.setCookie(request, response, "ZongheToken", systemConfigService.getZongheToken(), 60 * 60 * 12);
        request.getSession().setAttribute("admin", user);
        SystemUser user1 = new SystemUser();
        user1.setRealName(user.getRealName());
        user1.setId(user.getId());
        user1.setUserBianhao(user.getUserBianhao());
        return Result.ok(user1);
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
            if(StringUtil.isEmpty(id[i])){
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

    /**
     * 用户公司授权
     */
    /*@RequestMapping("/saveAuthoCompany")
    public Result saveAuthoCompany(SystemUser data, String ids, HttpServletRequest request) {
        SystemUser user = userService.getById(data.getId());
        if (user == null) {
            writeString("未获取到用户", null);
            return;
        }
        if (StringUtil.isBlank(ids)) {
            writeString("", null);
            return;
        }
        String[] id = ids.split(",");
        Set<Company> companys = new HashSet<>();
        for (int i = 0; i < CollectionUtil.size(id); i++) {
            if (StringUtil.parseInt(id[i]) == 0) {
                continue;
            }
            Company company = companyServiceI.getById(StringUtil.parseInt(id[i]));
            if (company == null) {
                writeString("未获取到公司", null);
                return;
            }
            companys.add(company);
        }
        user.setCompanys(companys);
        userService.update(user);
        writeString("", null);
    }*/
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

    private Map getByMap(SystemUser user) {
        Map<String, Object> map = new HashMap<>();
        map.put("id", user.getId());
        map.put("type", user.getType());
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
        return map;
    }
    /**
     * 根据当前用户获得用户
     */
   /* @RequestMapping("getByuser")
    public Result getByuser(Integer companyId) {
        List<SystemUser> getCompany = new ArrayList<>();
        if (companyId != null) {
            getCompany = userService.getByCompany(companyId);
        } else {
            SystemUser systemUser = userService.getById(TokenManager.getToken().getId());
            Set<Company> userCompanys = systemUser.getCompanys();
            for (Company company : userCompanys) {
                getCompany.addAll(userService.getByCompany(company.getCompanyId()));
            }
        }
        Set<SystemUser> res = new HashSet<>(getCompany);
        JSONArray array = new JSONArray();
        for (SystemUser user : res) {
            JSONObject object = new JSONObject();
            object.put("id", user.getId());
            object.put("realName", user.getRealName());
            array.add(object);
        }
        writeString("", array);

    }*/

}