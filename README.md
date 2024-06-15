
# Mockito & PowerMockito 单元测试
当我们仅仅需要验证代码逻辑，不需要Spring的bean注入时，使用Mockito & PowerMockito来快速测试。

Mockito用于mock对象便于对代码逻辑进行测试&验证，但Mockito mock的方法有限，无法mock final、private、static方法，而PowerMockito框架弥补了这一点。两者可以混合使用。

案例：
```java

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

        
        List<Sample> samples = sampleService.listSamples("1");

        // 如果sample.size()返回Long，需要加一个 sample.size().longValue()方法
        Assert.assertEquals(0,samples.size());
        
        // 比较JSON
        JSONAssert.assertEquals("{\"a\":1}","{\"a\":1}",false);
        // 解析JSON
        Assert.assertEquals(JsonPath.parse("{\"a\":1}").read("$.a").getClass(),Integer.class);
    }
    
    @Test
    public void mockPrivate() {
         try {
            Method method = PowerMockito.method(Sample.class, "privateMethodName", Long.class);
            method.invoke(sampleService, 0L);
            Assert.fail();
        } catch (Exception e) {
            Assert.assertEquals("报错信息", e.getCause().getMessage());
        }
    
    }

}

```

# Spring 测试
当依赖Spring时，可以利用Spring和PowerMockito一起完成mock和test

案例：
```java
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class)
@PrepareOnlyThisForTest({SampleUtil.class})
@ContextConfiguration(classes = ControllerSliceTestWithPowerMockito.Context.class)
public class ControllerSliceTestWithPowerMockito {

    // @Import加入需要扫描的Bean
    // @Configuration配合其他都行，参考@ContextConfiguration注释
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
```



# WebMvc 切片测试

- @AutoConfigureWebMvc : Use this if you need to configure the web layer for testing but don't need to use MockMvc
- @AutoConfigureMockMvc : Use this when you just want to configure MockMvc
- @WebMvcTest : Includes both the @AutoConfigureWebMvc and the @AutoConfigureMockMvc, among other functionality.

三者区别，参考：[What's the difference between @AutoConfigureWebMvc and @AutoConfigureMockMvc?](https://stackoverflow.com/questions/46343782/whats-the-difference-between-autoconfigurewebmvc-and-autoconfiguremockmvc)

案例一：
```java
@WebMvcTest(SampleController.class)
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = TestSampleController.TestContext.class)
public class TestSampleController {

    private static final Logger log = LoggerFactory.getLogger(TestSampleController.class);

    // 这里填入需要扫描的Bean，这样就不用扫描整个project文件，加快测试速度
    @Import({SampleController.class, ControllerExceptionAdvice.class})
    @Configuration // 这里兼容老版本，高版本不用加
    static class TestContext {
    }


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SampleService sampleService;

    // 这里用SpyBean注解：当SampleController中用到了SampleConverter，但是又不需要mock，得用converter原本的逻辑
    // 或用@MockBean时,在 Mockito.when(...).thenCallRealMethod()就行。
    @SpyBean
    private SampleConverter sampleConverter;

    @Before
    public void prepareMock() {
      // 对SampleController中调用了的SampleService的方法进行mock
        Mockito
            .doNothing()
            .when(sampleService)
            .sampleMethod(Mockito.any());
    }

    @Test
    public void shouldReturnSuccess() throws Exception {

        SampleRequest req = new SampleRequest();
        req.setA(1L);
        String bodyJson = JsonUtils.toJson(req);

        mockMvc.perform(MockMvcRequestBuilders
                            .post("/test")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(bodyJson))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.content().json("{\"success\":true}"));
    }

    @Test
    public void shouldReturnErrorMsg() throws Exception {

        SampleRequest req = new SampleRequest();
        req.setB
        String bodyJson = JsonUtils.toJson(req);

        mockMvc.perform(MockMvcRequestBuilders
                            .post("/test2")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(bodyJson))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(MockMvcResultMatchers.content().json("{\"success\":false,\"errorMsg\":\"错误信息\"}"));
    }
}
```

案例二：
```java

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
```

此外我们使用h2内存数据库达到对Mapper的测试，也有testcontainers库推出用于测试与外部系统的交互，这里不赘述，详见示例代码

