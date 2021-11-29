package cn.linshenkx.randomimageapi.enums;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface SourceTypeImpl {
    SourceType value();

}
