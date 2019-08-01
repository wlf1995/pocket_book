package com.ibicn.hr.controller.sys;

import com.ibicn.hr.controller.base.BaseController;
import com.ibicn.hr.entity.sys.Bangongqu;
import com.ibicn.hr.util.BaseModel;
import com.ibicn.hr.util.PageResult;
import com.ibicn.hr.util.Result;
import com.ibicnCloud.util.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bangongqu")
public class BangongquController extends BaseController {

    @RequestMapping("list")
    public Result list(Bangongqu data, BaseModel baseModel) {
        PageResult asc = bangongquService.list(data, baseModel.setOrder("asc"));
        List<Bangongqu> content = asc.getContent();
        List<Map> list = new ArrayList<>();
        for (Bangongqu role : content) {
            list.add(getByMap(role));
        }
        return Result.ok(PageResult.getPageResult(asc, list));
    }

    @RequestMapping("get")
    public Result get(Bangongqu data) {
        Bangongqu role = bangongquService.getById(data.getId());
        return Result.ok(getByMap(role));
    }

    @RequestMapping("saveOK")
    public Result saveOK(Bangongqu data) {
        Result check = check(data);
        if (!check.getCode().equals(Result.StatusCode.SUCCESS_CODE)) {
            return check;
        }
        bangongquService.save(data);
        return Result.ok();
    }

    @RequestMapping("updateOK")
    public Result updateOK(Bangongqu data) {
        Bangongqu bangongqu = bangongquService.getById(data.getId());
        if (bangongqu == null) {
            return Result.failure("未获取到办公区");
        }
        Result check = check(data);
        if (!check.getCode().equals(Result.StatusCode.SUCCESS_CODE)) {
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
    public Result deleteOK(Integer id) {
        if (id == null) {
            return Result.failure("未获取到办公区");
        }
        bangongquService.delete(id);
        return Result.ok();
    }

    @RequestMapping("getByDict")
    public Result getByDict() {
        List<Bangongqu> content = bangongquService.getAllBangonqu();
        List<Map> list = new ArrayList<>();
        for (Bangongqu bangongqu : content) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", bangongqu.getId());
            map.put("name", bangongqu.getName());
            list.add(map);
        }
        return Result.ok(list);
    }

    private Result check(Bangongqu data) {
        if (StringUtil.isBlank(data.getName())) {
            return Result.failure("名称不能为空");
        }
        return Result.ok();
    }

    private Map getByMap(Bangongqu data) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", data.getId());
        map.put("name", data.getName());
        map.put("bianhao", data.getBianhao());
        map.put("address", data.getAddress());
        map.put("cityName", data.getCityName());
        map.put("longitude", data.getLongitude());
        map.put("latitude", data.getLatitude());
        map.put("createdTime", data.getCreatedTime());
        return map;
    }
}
