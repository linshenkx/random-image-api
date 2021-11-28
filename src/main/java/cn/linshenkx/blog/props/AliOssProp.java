package cn.linshenkx.blog.props;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@Component
@Validated
@ConfigurationProperties(prefix = "ali.oss")
public class AliOssProp {
    @NotNull
    private String endpoint;
    @NotNull
    private String accessKeyId;
    @NotNull
    private String accessKeySecret;
    @NotNull
    private String bucket;
    @NotNull
    private String prefix;
    @NotNull
    private String hostname;
}
