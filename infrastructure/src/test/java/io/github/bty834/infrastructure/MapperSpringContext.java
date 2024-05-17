package io.github.bty834.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Slf4j
@Configuration
// @EnableAspectJAutoProxy
@ComponentScan(basePackages = "io.github.bty834.infrastructure.repository")
public class MapperSpringContext {

}
