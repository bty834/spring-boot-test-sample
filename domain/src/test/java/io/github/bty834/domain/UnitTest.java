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

        PowerMockito
            .when(SampleUtil.getSomething(eq("1")))
            .thenReturn(1L);

        // PowerMockito.when(xxxObj.xxMethod(argThat(new GreaterOrEqual<>(updatedAtBaseLine))))
        //     .thenThrow(new IllegalArgumentException());
        //
        // PowerMockito.when(xxxObj.xxMethod(argThat(new GreaterOrEqual<>(updatedAtBaseLine))))
        //     .thenThrow(new IllegalArgumentException());


        // 这里有any(),anyString()等
        // 如果参数是String，mock方法传入的是null，则mock不生效，传null需指定为any()
        Mockito
            .when(sampleRepository.selectSamples(any()))
            .thenReturn(new ArrayList<>());

        List<Sample> samples = sampleService.listSamples("1");

        Assert.assertEquals(samples.size(),0);


        // 比较JSON
        JSONAssert.assertEquals("{\"a\":1}","{\"a\":1}",false);
        // 解析JSON
        Assert.assertEquals(JsonPath.parse("{\"a\":1}").read("$.a").getClass(),Integer.class);
    }

}
