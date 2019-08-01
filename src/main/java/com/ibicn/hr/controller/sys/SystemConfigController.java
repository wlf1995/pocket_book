package com.ibicn.hr.controller.sys;

import com.alibaba.fastjson.JSONObject;
import com.ibicn.hr.controller.base.BaseController;
import com.ibicn.hr.util.Result;
import com.ibicnCloud.util.StringUtil;
import com.qiniu.util.Auth;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
@RequestMapping("/config")
public class SystemConfigController extends BaseController {

    @RequestMapping("/getToken")
    @ResponseBody
    public void  getToken(HttpServletResponse response) throws IOException {
        Auth auth = Auth.create(systemConfigService.getValue("qiniuAK"), systemConfigService.getValue("qiniuSK"));
        String uptoken = auth.uploadToken(systemConfigService.getValue("bucketname"));
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uptoken", uptoken);
        response.setContentType("text/html;charset=utf-8");
        response.getWriter().write(jsonObject.toJSONString());
        response.getWriter().flush();
        response.getWriter().close();
    }

    @RequestMapping("/getFile")
    @ResponseBody
    public void  getFile(String file, HttpServletResponse response) throws IOException {
        Auth auth = Auth.create(systemConfigService.getValue("qiniuAK"), systemConfigService.getValue("qiniuSK"));
        String url = auth.privateDownloadUrl(file);
        response.sendRedirect(url);
    }


    @RequestMapping("/get")
    @ResponseBody
    public Result get(String keyInfos) {
       return Result.ok(getJsonObject(keyInfos));
    }

    @RequestMapping("/updateOK")
    @ResponseBody
    public Result  updateOK(String keyInfos, HttpServletRequest request) {
        String[] keys = keyInfos.split(",");
        for (String key : keys) {
            systemConfigService.put(key,StringUtil.format(request.getParameter(key)));
        }
        return  Result.ok();
    }

    private Result getJsonObject (String keyInfos) {
        String[] keys = keyInfos.split(",");
        JSONObject jsonObject = new JSONObject();
        for (String key : keys) {
            jsonObject.put(key, systemConfigService.getValue(key));
        }
        return Result.ok();
    }


}