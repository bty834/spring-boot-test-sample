package io.github.bty834.controller;

import io.github.bty834.controller.api.SampleController;
import io.github.bty834.controller.converter.SampleConverter;
import io.github.bty834.domain.service.SampleService;
import io.github.bty834.domain.util.SampleUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareOnlyThisForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.ArgumentMatchers.eq;

@WebMvcTest(SampleController.class)
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@PrepareOnlyThisForTest({SampleUtil.class})
@ContextConfiguration(classes = ControllerSliceTestWithPowerMockito.SampleControllerSliceTestContext.class)

public class ControllerSliceTestWithPowerMockito {

    @Import(SampleController.class)
    static class SampleControllerSliceTestContext {

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
    }

    @Test
    public void test() {

    }

}
