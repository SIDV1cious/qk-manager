package com.itheima.utils;

import com.aliyun.oss.ClientBuilderConfiguration;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.common.auth.CredentialsProviderFactory;
import com.aliyun.oss.common.auth.EnvironmentVariableCredentialsProvider;
import com.aliyun.oss.common.comm.SignVersion;
import com.aliyun.oss.model.PutObjectRequest;
import com.itheima.exception.QkException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 读取SpringBoot项目 application.yml 中的自定义配置
 * 方式一 : 使用 @Value("${aliyun.oss.endpoint}") 注解一个个读取
 * 方式二 : 使用 @ConfigurationProperties(prefix='配置前缀') 注解批量读取自定义配置到属性配置类对象中
 */
@Component
public class OssTemplate {

    @Autowired
    private AliyunOssProperties aliyunOssProperties;

    public String upload(String fileName, InputStream inputStream) {
        OSS ossClient = null;
        try {
            // 从环境变量中获取访问凭证。运行本代码示例之前，请确保已设置环境变量 OSS_ACCESS_KEY_ID 和 OSS_ACCESS_KEY_SECRET。
            EnvironmentVariableCredentialsProvider credentialsProvider = CredentialsProviderFactory.newEnvironmentVariableCredentialsProvider();
            // 创建OSSClient实例。
            ClientBuilderConfiguration clientBuilderConfiguration = new ClientBuilderConfiguration();
            clientBuilderConfiguration.setSignatureVersion(SignVersion.V4);
            ossClient = OSSClientBuilder.create()
                    .endpoint(aliyunOssProperties.getEndpoint())
                    .credentialsProvider(credentialsProvider)
                    .clientConfiguration(clientBuilderConfiguration)
                    .region(aliyunOssProperties.getRegion())
                    .build();
            // 生成按照时间日期划分的唯一文件名称
            String objectName = generateObjectName(fileName);

            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(aliyunOssProperties.getBucketName(), objectName, inputStream);
            // 创建PutObject请求。
            ossClient.putObject(putObjectRequest);

            return getFileUrl(objectName);
        } catch (Exception oe) {
            oe.printStackTrace();
            throw new QkException("阿里云OSS文件上传出现异常");
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
    }
    // 生成按照时间日期划分的唯一文件名称
    private static String generateObjectName(String fileName) {
        // 对原始文件名称进行改写, 生成唯一文件名(UUID)
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String suffix = fileName.substring(fileName.lastIndexOf('.'));
        String objectName = uuid + suffix; // ae518b7f7e5742a19690c8ba53050fff.jpg
        // 生成按照年月日格式化的时间目录
        String datePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd")); // 2026/09/26
        // 重新拼接objectName
        objectName = datePath+"/"+objectName; //objectName = 2026/10/26/ae518b7f7e5742a19690c8ba53050fff.jpg
        return objectName;
    }
    /**
     * 拼接文件的访问路径: https://{bucketName}.{endpoint主机}/{objectName}
     */
    private String getFileUrl(String objectName) {
        String host = aliyunOssProperties.getEndpoint().replaceFirst("^https?://", ""); // oss-cn-beijing.aliyuncs.com
        return String.format("https://%s.%s/%s", aliyunOssProperties.getBucketName(), host, objectName);
    }

}