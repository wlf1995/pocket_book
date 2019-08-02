package com.ibicn.hr.controller.person;

import com.ibicn.hr.controller.base.BaseController;
import com.ibicn.hr.entity.person.Person;
import com.ibicn.hr.model.person.PersonModel;
import com.ibicn.hr.util.BaseModel;
import com.ibicn.hr.util.PageResult;
import com.ibicn.hr.util.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * @Author 王立方
 * @Description 人事档案
 * @Date 16:43 2019/8/2
 **/
@RestController
@RequestMapping("/Person")
public class PersonController extends BaseController {

    @RequestMapping("list")
    public Result list(Person data, BaseModel baseModel) {
        PageResult asc = personServiceI.list(data, baseModel.setOrder("asc"));
        List<Person> content = asc.getContent();
        List<Map> list = new ArrayList<>();
        for (Person role : content) {
            list.add(getByMap(role));
        }
        return Result.ok(PageResult.getPageResult(asc, list));
    }

    @RequestMapping("get")
    public Result get(Integer id) {
        if(id==null){
            return Result.failure("id为空");
        }
        Person role = personServiceI.getOne(id);
        if(role==null){
            return Result.failure("记录不存在");
        }
        return Result.ok(getByMap(role));
    }

    @RequestMapping("saveOK")
    public Result saveOK(PersonModel data) {
//        personServiceI.save(data);
        return Result.ok();
    }

    @RequestMapping("updateOK")
    public Result updateOK(PersonModel data) {
//        Person bangongqu = personServiceI.getOne(data.getId());
//        if (bangongqu == null) {
//            return Result.failure("未获取到办公区");
//        }
//
//        bangongqu.setAddress(data.getAddress());
//        personServiceI.update(bangongqu);
        return Result.ok();
    }

    @RequestMapping("deleteOK")
    public Result deleteOK(Integer id) {
        if (id == null) {
            return Result.failure("未获取到办公区");
        }
        personServiceI.deleteById(id);
        return Result.ok();
    }


    private Map getByMap(Person data) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", data.getId());
        map.put("address", data.getAddress());
        map.put("createdTime", data.getCreatedTime());
        return map;
    }
}
