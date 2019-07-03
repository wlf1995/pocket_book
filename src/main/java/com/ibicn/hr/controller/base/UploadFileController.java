package com.ibicn.hr.controller.base;

import com.ibicn.hr.util.QiniuUtil;
import com.ibicn.hr.util.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * 上传文件
 */
@RestController
@RequestMapping("/uploadFile")
public class UploadFileController extends BaseController {
    @RequestMapping("/uploadImg")
    public Result uploadPicture(@RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();//获取文件名加后缀
        if (fileName != null && fileName != "") {
            byte[] bytes = file.getBytes();
            QiniuUtil qiniuUtil = new QiniuUtil(systemConfigService.getValue("qiniuAK"), systemConfigService.getValue("qiniuSK"), "zonghefile");
            fileName = fileName.replace(fileName.substring(0, fileName.indexOf('.')), UUID.randomUUID().toString());
            String fileUrl = qiniuUtil.upload(bytes, fileName);
            return  Result.ok("http://file.bjguolian.cn/"+fileUrl);
        }
        return Result.failure();
    }
}
