package com.ibicn.hr.controller.sys;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * 获得验证码图片
 */

@RestController
@RequestMapping("/verificationImage")
public class VerificationImageController {
    @RequestMapping("/getVerificationCode")
    public void getVerificationCode(HttpSession session, HttpServletResponse response) throws IOException {
        int width = 120, height = 40;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // 获取图形上下文
        Graphics g = image.createGraphics();
        //生成随机类
        Random random = new Random();
        // 设定背景色
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        //设定字体
        g.setFont(new Font("Times New Roman", Font.PLAIN, 36));
        //画边框
        //g.setColor(new Color());
        //g.drawRect(0,0,width-1,height-1);
        // 随机产生155条干扰线，使图象中的认证码不易被其它程序探测到
        g.setColor(getRandColor(60, 200));
        for (int i = 0; i < 155; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
        // 取随机产生的认证码(2个数字的加法)
        int sRand = 0;
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += Integer.valueOf(rand);
            //为1的时候,插入一个+号
            if (i == 1) {
                sRand -= Integer.valueOf(rand);
                rand = "+";
            }
            if (i == 3) {
                sRand -= Integer.valueOf(rand);
                rand = "=";
            }
            // 将认证码显示到图象中
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            //调用函数出来的颜色相同，可能是因为种子太接近，所以只能直接生成
            g.drawString(rand, 23 * i + 6, 30);
        }
        // 将认证码存入SESSION
        session.setAttribute("checkCode", sRand + "");
        // 图象生效
        g.dispose();
        // 输出图象到页面
        OutputStream os = response.getOutputStream();
        ImageIO.write(image, "JPEG", os);
        os.flush();
        os.close();
        os = null;
        response.flushBuffer();
        //        out.clear();
        //        out = pageContext.pushBody();

    }

    public static Color getRandColor(int fc, int bc) {//给定范围获得随机颜色
        Random random = new Random();
        if (fc > 255) fc = 255;
        if (bc > 255) bc = 255;
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }
}
