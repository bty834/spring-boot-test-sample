package io.github.bty834.controller;

import io.github.bty834.controller.api.SampleController;
import io.github.bty834.controller.converter.SampleConverter;
import io.github.bty834.domain.service.SampleService;
import io.github.bty834.domain.util.SampleUtil;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareOnlyThisForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.ArgumentMatchers.eq;

@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@PrepareOnlyThisForTest({SampleUtil.class})
@ContextConfiguration(classes = ControllerSliceTestWithPowerMockito.Context.class)
public class ControllerSliceTestWithPowerMockito {

    // @Import加入需要扫描的Bean
    @Import(SampleController.class)
    static class Context {

    }

    @MockBean
    private SampleService sampleService;

    @SpyBean
    private SampleConverter sampleConverter;


    @Before
    public void zkSetup() {
        PowerMockito.mockStatic(SampleUtil.class);
        PowerMockito.when(SampleUtil.getSomething(eq("a")))
            .thenReturn(1L);

        sampleConverter.test();

        // assert, verify
    }

}
