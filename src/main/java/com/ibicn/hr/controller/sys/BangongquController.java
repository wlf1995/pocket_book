package com.ibicn.hr.controller.sys;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ibicn.hr.bean.sys.SystemMenu;
import com.ibicn.hr.bean.sys.Bangongqu;
import com.ibicn.hr.controller.base.BaseController;
import com.ibicn.hr.util.BaseModel;
import com.ibicn.hr.util.PageUtil;
import com.ibicn.hr.util.Result;
import com.ibicn.hr.util.StatusCode;
import com.ibicnCloud.util.CollectionUtil;
import com.ibicnCloud.util.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@RestController
@RequestMapping("/bangongqu")
public class BangongquController extends BaseController {

    @RequestMapping("list")
    public Result list(Bangongqu data, BaseModel baseModel, HttpServletRequest request) {
        Page<Bangongqu> pr = bangongquService.list(data, baseModel.setOrder("asc"));
        List<Bangongqu> content = pr.getContent();
        List<Map> list=new ArrayList<>();
        for(Bangongqu role:content){
            list.add(getByMap(role));
        }
        return Result.ok(PageUtil.getPageUtil(pr,list));
    }

    @RequestMapping("get")
    public Result get(Bangongqu data, HttpServletRequest request) {
        Bangongqu role = bangongquService.getById(data.getId());
        return Result.ok(getByMap(role));
    }

    @RequestMapping("saveOK")
    public Result saveOK(Bangongqu data, HttpServletRequest request) {
        Result check = check(data);
        if (!check.getCode().equals(StatusCode.SUCCESS_CODE)) {
            return check;
        }
        bangongquService.save(data);
        return Result.ok();
    }

    @RequestMapping("updateOK")
    public Result updateOK(Bangongqu data, HttpServletRequest request) {
        Bangongqu bangongqu = bangongquService.getById(data.getId());
        if (bangongqu == null) {
            return Result.failure("未获取到办公区");
        }
        Result check = check(data);
        if (!check.getCode().equals(StatusCode.SUCCESS_CODE)) {
            return check;
        }
        bangongqu.setName(data.getName());
        bangongqu.setBianhao(data.getBianhao());
        bangongqu.setAddress(data.getAddress());
        bangongqu.setCityName(data.getCityName());
        bangongqu.setLatitude(data.getLatitude());
        bangongqu.setLongitude(data.getLongitude());
        bangongquService.update(bangongqu);
        return Result.ok();
    }

    @RequestMapping("deleteOK")
    public Result deleteOK(Integer id, HttpServletRequest request) {
        if (id == null) {
            return Result.failure("未获取到办公区");
        }
        bangongquService.delete(id);
        return Result.ok();
    }

    private Result check(Bangongqu data) {
        if (StringUtil.isBlank(data.getName())) {
            return Result.failure("名称不能为空");
        }
        return Result.ok();
    }

    private Map getByMap(Bangongqu data){
        HashMap<String,Object> map=new HashMap<>();
        map.put("id",data.getId());
        map.put("name",data.getName());
        map.put("bianhao",data.getBianhao());
        map.put("address",data.getAddress());
        map.put("cityName",data.getCityName());
        map.put("longitude",data.getLongitude());
        map.put("latitude",data.getLatitude());
        map.put("createdTime",data.getCreatedTime());
        return map;
    }
}
