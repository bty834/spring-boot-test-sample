package io.github.bty834.domain;

import com.jayway.jsonpath.JsonPath;
import io.github.bty834.domain.model.Sample;
import io.github.bty834.domain.repository.SampleRepository;
import io.github.bty834.domain.service.SampleService;
import io.github.bty834.domain.util.SampleUtil;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.internal.matchers.GreaterOrEqual;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareOnlyThisForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.skyscreamer.jsonassert.JSONAssert;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;


/**
 * unit test -> integration test -> e2e
 */
@RunWith(PowerMockRunner.class)
// mock static method
@PrepareOnlyThisForTest({SampleUtil.class})
@PowerMockIgnore({"javax.net.ssl.*","javax.management.*", "javax.security.*", "javax.crypto.*"})
public class UnitTest {


    @Mock
    private SampleRepository sampleRepository;

    @InjectMocks
    private SampleService sampleService;

    @BeforeClass
    public static void beforeAll(){
        System.out.print("\n\n\n++++++++++++++\n\n\n");
    }

    @AfterClass
    public static void afterAll(){
        System.out.print("\n\n\n==============\n\n\n");
    }

    @Before
    public void before(){}

    @After
    public void after(){}

    @Test
    public void getSamples() throws JSONException {

        PowerMockito.mockStatic(SampleUtil.class);

        // 注意所有when内部的方法参数必须用org.mockito.ArgumentMatchers的方法包一层，不能直接传
        PowerMockito
            .when(SampleUtil.getSomething(eq("1"))) // 反例：.when(SampleUtil.getSomething("1"))
            .thenReturn(1L);


        PowerMockito.when(sampleRepository.selectSamples(argThat(id -> id.equals(1L))))
                        .thenReturn(new ArrayList<>());

        PowerMockito.when(sampleRepository.selectSamples(argThat(new GreaterOrEqual<>(1L))))
            .thenReturn(new ArrayList<>());

        // 这里有any(),anyString()等
        // 如果参数是String，mock方法传入的是null，则mock不生效，传null需指定为any()
        Mockito
            .when(sampleRepository.selectSamples(any()))
            .thenReturn(new ArrayList<>());

        // verify方法调用次数
        Mockito.verify(sampleRepository, Mockito.times(1)).selectSamples(any());
        // Mockito.verify(sampleRepository, Mockito.times(1)).selectSamples(argThat(i->i.equals(1)));

        // capture参数验证
        ArgumentCaptor<Long> paramCap = ArgumentCaptor.forClass(Long.class);
        Mockito.verify(sampleRepository, Mockito.times(1)).selectSamples(paramCap.capture());
        Assert.assertNotNull(paramCap.getValue());

       // 运行参数为Runnable的方式
        Mockito.doAnswer(invocation -> {
            Object[] arguments = invocation.getArguments();
            Runnable runnable = (Runnable)arguments[0];
            runnable.run();
            return null;
        }).when(sampleRepository).run(any(Runnable.class));

        Mockito.verify(sampleRepository, Mockito.times(1)).selectSamples(any());

        List<Sample> samples = sampleService.listSamples("1");

        // 如果sample.size()返回Long，需要加一个 sample.size().longValue()方法
        Assert.assertEquals(0,samples.size());

        // 比较JSON
        JSONAssert.assertEquals("{a:1,b: \"bVal\"}","{\"a\":1}",false);
        // 解析JSON
        Assert.assertEquals(JsonPath.parse("{\"a\":1}").read("$.a").getClass(),Integer.class);
    }

}
