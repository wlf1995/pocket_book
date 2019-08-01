package com.ibicn.hr.controller.base;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibicn.hr.ENUM.EnumMenuType;
import com.ibicn.hr.entity.sys.SystemMenu;
import com.ibicn.hr.entity.sys.SystemUser;
import com.ibicn.hr.util.ImageUtil;
import com.ibicn.hr.util.Result;
import com.ibicnCloud.util.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Controller
public class IndexController extends BaseController {
    /**
     * 进入首页
     *
     * @return
     */
    @RequestMapping(value = {"index", "/", ""}, method = RequestMethod.GET)
    public String index(Model model) {
        SystemUser user = getUser();
        List<SystemMenu> list = systemMenuServiceI.getMenuByUser(user);
        List<String> menuPaths = systemMenuServiceI.getMenyPathIdByUser(user, EnumMenuType.MENU);
        JSONArray array = new JSONArray();
        for (int i = 0; i < CollectionUtil.size(list); i++) {
            JSONObject object = new JSONObject();
            object.put("name", list.get(i).getName());
            object.put("path", list.get(i).getPath());
            JSONArray childarray = new JSONArray();
            if (CollectionUtil.size(list.get(i).getChilds()) > 0) {
                List<SystemMenu> menus = list.get(i).getSortChilds();
                for (int j = 0; j < CollectionUtil.size(menus); j++) {
                    if (menus.get(j).getType().getIndex() == EnumMenuType.FEATURES.getIndex()) {
                        continue;
                    }
                    if (!menuPaths.contains(menus.get(j).getPath())) {
                        continue;
                    }
                    JSONObject childobj = new JSONObject();
                    childobj.put("name", menus.get(j).getName());
                    childobj.put("path", menus.get(j).getPath());
                    childarray.add(childobj);
                }
            }
            if (CollectionUtil.size(childarray) == 0) {
                continue;
            }
            object.put("childs", childarray);
            array.add(object);
        }
        model.addAttribute("menu", array);
        model.addAttribute("realName", getUser().getRealName());
        return "index";
    }
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 获得验证码图片
     */
    @ResponseBody
    @RequestMapping("/getImage")
    public Result getImage(String guid) {
        HashMap<String, String> sRand = ImageUtil.getVerificationCode();
        //把验证码存入redis中,5分钟有效期
        stringRedisTemplate.opsForValue().set("checkCode" + guid, sRand.get("code"), 5 * 60, TimeUnit.SECONDS);
        return  Result.ok(sRand.get("img"));
    }
    /**
     * @Author 王立方
     * @Description 跳转至首页
     * @Date 14:37 2019/8/1
     * @return java.lang.String
     **/
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    @RequestMapping("/logout")
    public String logout() {
        log.info("退出登录方法");
        return "login";
    }
}
