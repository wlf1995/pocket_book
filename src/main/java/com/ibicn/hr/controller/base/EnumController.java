package com.ibicn.hr.controller.base;

import com.ibicn.hr.ENUM.EnumUtil;
import com.ibicn.hr.util.Result;
import com.ibicnCloud.util.StringUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnumController extends BaseController {

    @ResponseBody
    @RequestMapping("/selectEnum")
    public Result getEnumClass(String name, String index) {
        if(StringUtil.isBlank(name)){
            return Result.failure();
        }
        String str=null;
        try {
            Class clazz=Class.forName("com.ibicn.hr.ENUM."+name);
            str= EnumUtil.getDrop_down(clazz,index);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
       return Result.ok(str);
    }

}
