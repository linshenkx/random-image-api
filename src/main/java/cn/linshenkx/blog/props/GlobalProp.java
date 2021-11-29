package cn.linshenkx.blog.props;

import cn.linshenkx.blog.enums.SourceType;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@Component
@Validated
@ConfigurationProperties(prefix = "my.global")
public class GlobalProp {
    @NotNull
    private SourceType sourceType;
}
