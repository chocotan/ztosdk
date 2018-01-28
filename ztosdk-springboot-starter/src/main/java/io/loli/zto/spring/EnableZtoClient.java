package io.loli.zto.spring;

import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(ZtoClientConfigurationSelector.class)
public @interface EnableZtoClient {
    AdviceMode mode() default AdviceMode.PROXY;
}
