package io.github.bty834.domain.config;

import io.github.bty834.domain.config.SampleConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.Import;

@Slf4j
@SpringBootConfiguration// 可使用
// @EnableAspectJAutoProxy
@Import({
    SampleConfig.class,
})
public class SpringTestCommonConfig {
}
