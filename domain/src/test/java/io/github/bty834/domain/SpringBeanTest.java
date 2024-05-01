package io.github.bty834.domain;

import io.github.bty834.domain.config.SampleConfig;
import io.github.bty834.domain.config.SpringTestCommonConfig;
import io.github.bty834.domain.repository.SampleRepository;
import io.github.bty834.domain.util.SampleUtil;
import org.json.JSONException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(PowerMockRunner.class)
@SuppressStaticInitializationFor("com.dianping.cat.Cat")
// mock static method
@PrepareForTest({SampleUtil.class})
// spring bean
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@PowerMockIgnore({"javax.net.ssl.*","javax.management.*", "javax.security.*", "javax.crypto.*"})
// @SpringBootTest从当前包向上找@SpringBootConfiguration，或者指定
@SpringBootTest(classes = SpringTestCommonConfig.class)
public class SpringBeanTest {

    // 这个mock对象会注入Spring容器
    @MockBean
    private SampleRepository sampleRepository1;

    // 真实调用该对象逻辑
    @SpyBean
    private SampleRepository sampleRepository2;

    @Autowired
    private SampleRepository sampleRepository3;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private SampleConfig sampleConfig;

    @Test
    public void sampleBeanTest() throws JSONException {

        SampleRepository bean = applicationContext.getBean(SampleRepository.class);
        Assert.assertEquals(sampleRepository1,bean);

    }

}
