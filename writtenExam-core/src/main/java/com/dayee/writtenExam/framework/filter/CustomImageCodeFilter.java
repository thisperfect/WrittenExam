package com.dayee.writtenExam.framework.filter;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.filter.OncePerRequestFilter;

import com.dayee.writtenExam.framework.util.ImageUtil;

/**
 * 生成验证码 Author:xiaoyu Date:2016年4月11日 类的作用：
 */
public class CustomImageCodeFilter extends OncePerRequestFilter {

    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 设置验证码响应类型
        response.setDateHeader("Expires", 0L);
        response.setHeader("Cache-Control",
                           "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        // 生成验证码
        Map<String, BufferedImage> map = ImageUtil.createImage();
        String key = map.keySet().iterator().next();

        // 将验证码放入session中
        HttpSession httpSession = request.getSession();

        httpSession.setAttribute("imageCodeCreated", key);
        // 生成验证码图片
        BufferedImage bufferedImage = map.get(key);
        try {
            ServletOutputStream os = response.getOutputStream();
            // 写出图片到浏览器中
            ImageIO.write(bufferedImage, "png", os);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}