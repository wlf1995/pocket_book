package com.ibicn.hr.controller.base;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibicn.hr.ENUM.EnumMenuType;
import com.ibicn.hr.bean.sys.SystemMenu;
import com.ibicn.hr.bean.sys.SystemUser;
import com.ibicn.hr.shiro.token.TokenManager;
import com.ibicnCloud.util.CollectionUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController extends BaseController {
    /**
     * 进入首页
     *
     * @return
     */
    @RequestMapping(value = {"index","/",""}, method = RequestMethod.GET)
    public String index(Model model, HttpServletRequest request) {

        SystemUser user = TokenManager.getToken();
        List<SystemMenu> list = systemMenuServiceI.getMenuByUser(user);
        List<String> menuPaths=systemMenuServiceI.getMenyPathIdByUser(user, EnumMenuType.MENU);
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
                    if(!menuPaths.contains(menus.get(j).getPath())){
                        continue;
                    }
                    JSONObject childobj = new JSONObject();
                    childobj.put("name", menus.get(j).getName());
                    childobj.put("path", menus.get(j).getPath());
                    childarray.add(childobj);
                }
            }
            if(CollectionUtil.size(childarray)==0){
                continue;
            }
            object.put("childs", childarray);
            array.add(object);
        }
        model.addAttribute("menu", array);
        model.addAttribute("realName", getUser().getRealName());
        return "index";
    }

}
