package club.xubowei.epframework.annotation;

import java.lang.annotation.*;

/**
 * @author kpgoing
 * @date 2018/8/18.
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Aspect {
    Class<? extends Annotation> value();
}
