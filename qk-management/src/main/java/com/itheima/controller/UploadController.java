package com.itheima.controller;

import com.itheima.common.Result;
import com.itheima.utils.OssTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * 客户端的文件上传请求,文件参数在服务器端 需要使用 MultipartFile 类型进行接收
 * 注意 :
 * - 形参名称要与前端文件参数名称保持一致
 * - 如果不一致需要使用 @RequestParam("image") 注解指定前端请求的文件参数名称
 */
@RestController
@RequiredArgsConstructor
public class UploadController {

    private final OssTemplate ossTemplate;

    @PostMapping("/upload")
    public Result upload(@RequestParam("image") MultipartFile file) throws IOException {
        // 调用阿里云文件上传工具类上传文件
        //返回值：url = https://bucket-wuhan-yhf.oss-cn-wuhan-lr.aliyuncs.com/2026/10/26/9c1e3db4c9c644e1ab27d5bbc8cd3844.jpeg
        String url = ossTemplate.upload(file.getOriginalFilename(), file.getInputStream());
        return Result.success(url);
    }


    // @PostMapping("/upload")
    // public Result upload(@RequestParam("image") MultipartFile file) throws IOException {
    //     // 获取文件原始名称
    //     String originalFilename = file.getOriginalFilename();
    //     System.out.println("原始文件名称:" + originalFilename);
    //     // 获取文件参数名称
    //     String name = file.getName();
    //     System.out.println("文件参数名称:" + name);
    //     // 获取文件字节数组
    //     byte[] bytes = file.getBytes();
    //     System.out.println("文件字节数组长度:" + bytes.length);
    //     // 获取文件大小(字节数)
    //     long size = file.getSize();
    //     System.out.println("文件字节数:" + size);
    //     // 获取文件流数据
    //     InputStream inputStream = file.getInputStream();
    //     System.out.println("文件流对象:" + inputStream);
    //     // 判断上传文件是否存在
    //     boolean empty = file.isEmpty();
    //     System.out.println("文件是否为空:" + empty);
    //
    //     return Result.success();
    // }
}