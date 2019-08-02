package com.ibicn.hr.controller.sys;

import com.ibicn.hr.controller.base.BaseController;
import com.ibicn.hr.entity.sys.OfficeArea;
import com.ibicn.hr.util.BaseModel;
import com.ibicn.hr.util.PageResult;
import com.ibicn.hr.util.Result;
import com.ibicnCloud.util.StringUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/bangongqu")
public class BangongquController extends BaseController {

    @RequestMapping("list")
    public Result list(OfficeArea data, BaseModel baseModel) {
        PageResult asc = bangongquService.list(data, baseModel.setOrder("asc"));
        List<OfficeArea> content = asc.getContent();
        List<Map> list = new ArrayList<>();
        for (OfficeArea role : content) {
            list.add(getByMap(role));
        }
        return Result.ok(PageResult.getPageResult(asc, list));
    }

    @RequestMapping("get")
    public Result get(Integer id) {
        OfficeArea role = bangongquService.getOne(id);
        return Result.ok(getByMap(role));
    }

    @RequestMapping("saveOK")
    public Result saveOK(OfficeArea data) {
        Result check = check(data);
        if (!check.getCode().equals(Result.StatusCode.SUCCESS_CODE)) {
            return check;
        }
        bangongquService.save(data);
        return Result.ok();
    }

    @RequestMapping("updateOK")
    public Result updateOK(OfficeArea data) {
        OfficeArea bangongqu = bangongquService.getOne(data.getId());
        if (bangongqu == null) {
            return Result.failure("未获取到办公区");
        }
        Result check = check(data);
        if (!check.getCode().equals(Result.StatusCode.SUCCESS_CODE)) {
            return check;
        }
        bangongqu.setAreaName(data.getAreaName());
        bangongqu.setAddress(data.getAddress());
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
        List<OfficeArea> content = bangongquService.getAllBangonqu();
        List<Map> list = new ArrayList<>();
        for (OfficeArea bangongqu : content) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", bangongqu.getId());
            map.put("name", bangongqu.getAreaName());
            list.add(map);
        }
        return Result.ok(list);
    }

    private Result check(OfficeArea data) {
        if (StringUtil.isBlank(data.getAreaName())) {
            return Result.failure("名称不能为空");
        }
        return Result.ok();
    }

    private Map getByMap(OfficeArea data) {
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", data.getId());
        map.put("name", data.getAreaName());
        map.put("address", data.getAddress());
        map.put("createdTime", sf.format(data.getCreatedTime()));
        return map;
    }
}
