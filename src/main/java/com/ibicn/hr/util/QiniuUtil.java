package com.ibicn.hr.util;

import com.alibaba.fastjson.JSONObject;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.UUID;

/**
 * Created by zhonghang on 2016/10/11.
 * 七牛上传工具类
 */
public class QiniuUtil {
    private static final Logger logger = Logger.getLogger(QiniuUtil.class);
    //设置好账号的ACCESS_KEY和SECRET_KEY
    String ACCESS_KEY ;
    String SECRET_KEY;
    //要上传的空间
    String bucketname;
    //密钥配置
    Auth auth ;

    UploadManager uploadManager ;
    public QiniuUtil(String ACCESS_KEY, String SECRET_KEY, String bucketname){
        this.ACCESS_KEY = ACCESS_KEY;
        this.SECRET_KEY = SECRET_KEY;
        this.bucketname = bucketname;
        auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        //创建上传对象
        Configuration cfg = new Configuration(Zone.zone0());
        uploadManager = new UploadManager(cfg);
    }

    public QiniuUtil(String ACCESS_KEY, String SECRET_KEY, String bucketname, Zone zone){
        this.ACCESS_KEY = ACCESS_KEY;
        this.SECRET_KEY = SECRET_KEY;
        this.bucketname = bucketname;
        auth = Auth.create(ACCESS_KEY, SECRET_KEY);
        //创建上传对象
        Configuration cfg = new Configuration(zone);
        uploadManager = new UploadManager(cfg);
    }

    public String privateDownloadUrl(String url){
        return auth.privateDownloadUrl(url);
    }

    //简单上传，使用默认策略，只需要设置上传的空间名就可以了
    public String getUpToken(){
        return auth.uploadToken(bucketname);
    }

    /**
     * 通过路径上传至七牛
     * @param filePath
     * @return
     */
    public String upload(String filePath){
        try {
            //调用put方法上传
            File file = new File(filePath);
            String fileName = file.getName().replace( file.getName().substring(0 , file.getName().indexOf('.')) , UUID.randomUUID().toString());
            Response res = uploadManager.put(filePath, fileName , getUpToken());
            //打印返回的信息
            JSONObject jsonObject = JSONObject.parseObject(res.bodyString());
            return jsonObject.get("key").toString();
        } catch (QiniuException e) {
            logger.error(e);
        }
        return "";
    }

    public String upload(byte[] bytes , String fileName) throws QiniuException {
        Response res = uploadManager.put(bytes , fileName , getUpToken());
        JSONObject jsonObject = JSONObject.parseObject(res.bodyString());
        return jsonObject.get("key").toString();
    }
    public String uploadResultUrl(String url){
        return "http://7xnd0f.com2.z0.glb.qiniucdn.com/"+this.upload(url);
    }

//    public String uploadPDFUrl(String url , ConfigServiceI configServiceI){
//        return configServiceI.getQiNiuCDNDomain()+this.upload(url);
//    }

    public String uploadHeTongUrl(String url) {return "https://authcdn.toodudu.com/"+this.upload(url); }

    public static void main(String args[]){
        /**
         * 路径前缀  http://7xnd0f.com2.z0.glb.qiniucdn.com/
         * key作为后缀即可访问图片
         * {"hash":"FhaYx5UgBxvxhGkNEU7WKZi8DKM6","key":"21ac6ae8-ebc4-4e82-8cb9-8834f984c8ab.jpg"}
         */
//        QiniuUtil qiniuUtil = new QiniuUtil("lUCpqFy9SqzmzwelBE4kCeJcqMQ-C4E2dRgrtkaW","w_b5lCNXzDaTw9w2A-PNmUypJJZmyva0wRjZZn49","secretcontract");
        QiniuUtil qiniuUtil = new QiniuUtil("lUCpqFy9SqzmzwelBE4kCeJcqMQ-C4E2dRgrtkaW","w_b5lCNXzDaTw9w2A-PNmUypJJZmyva0wRjZZn49","zonghefile");
        String url = qiniuUtil.uploadResultUrl("C:\\Users\\Administrator\\Desktop\\QQ截图20180713095946.png");
        System.out.println("/page_doNotNeedSecurity_getImg.do?img=${config.qiNiuCDNDomain}"+url);
    }

    public static void test(){
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone1());
        //...其他参数参考类注释

        String accessKey = "lUCpqFy9SqzmzwelBE4kCeJcqMQ-C4E2dRgrtkaW";
        String secretKey = "w_b5lCNXzDaTw9w2A-PNmUypJJZmyva0wRjZZn49";

        String fromBucket = "secretcontract";
        String fromKey = "0.png";
        String toBucket = "ygbid";
        String toKey = "0.png";

        Auth auth = Auth.create(accessKey, secretKey);
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try {
            bucketManager.copy(fromBucket, fromKey, toBucket, toKey);
        } catch (QiniuException ex) {
            //如果遇到异常，说明复制失败
            System.err.println(ex.code());
        }

    }

}
