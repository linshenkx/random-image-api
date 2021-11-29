package cn.linshenkx.randomimageapi.modules.alioss;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@Component
@Validated
//@ConditionalOnProperty(prefix = "my.global", name = "sourceType", havingValue = "ALI_OSS")
@ConfigurationProperties(prefix = "my.ali-oss")
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
    @Min(1)
    @Max(20971520)
    private Long maxFileSize;
    @NotNull
    private ImageStyle imageStyle;

    @Data
    public static class ImageStyle {
        private String min;
        private String medium;
        private String max;
    }
}
