package cn.linshenkx.blog.config;

import cn.linshenkx.blog.props.AliOssProp;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;

@Configuration
public class AliOssConfig {

    @Resource
    private AliOssProp aliOssProp;

    @Bean
    public OSS aliOssClient(){
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = "yourEndpoint";
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = "yourAccessKeyId";
        String accessKeySecret = "yourAccessKeySecret";

        // 关闭OSSClient。
        //ossClient.shutdown();
        return new OSSClientBuilder().build(aliOssProp.getEndpoint(), aliOssProp.getAccessKeyId(), aliOssProp.getAccessKeySecret());
    }
}
