package com.itheima;

import com.itheima.utils.OssTemplate;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

@SpringBootTest
@Disabled("依赖本机文件 D:\\多级文件夹\\2.jpeg 和 OSS 环境变量凭证，无法在其他机器上运行")
public class OssTemplateTest {

    @Autowired
    private OssTemplate ossTemplate;

    @Test
    void testOss() throws FileNotFoundException {
        InputStream inputStream = new FileInputStream("D:\\多级文件夹\\2.jpeg");
        String url = ossTemplate.upload("2.jpeg", inputStream);
        System.out.println(url);
    }

}