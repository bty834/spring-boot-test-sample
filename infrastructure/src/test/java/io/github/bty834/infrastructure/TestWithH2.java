package io.github.bty834.infrastructure;

import java.util.Arrays;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 继承该类做测试
 */
@Slf4j
@ActiveProfiles("test")
@RunWith(SpringJUnit4ClassRunner.class)
@Import(MapperSpringContext.class)
public class TestWithH2 {



    // 注意H2的语法和MySQL不太一样，建表不要加index,和 ENGINE=InnoDB DEFAULT CHARSET=utf8mb4
    private static final DataSource H2_DATASOURCE = new EmbeddedDatabaseBuilder()
                                                        .setType(EmbeddedDatabaseType.H2)
                                                        .setName("finance-settlement")
                                                        .setScriptEncoding("UTF-8")
                                                        .addScripts("h2/init-db-mode.sql")
                                                        .build();


    private static final ResourceDatabasePopulator POPULATOR = new ResourceDatabasePopulator();

    protected static void executeSQL(String... scripts) {
        ClassPathResource[] resources = Arrays.stream(scripts).map(ClassPathResource::new).toArray(ClassPathResource[]::new);
        POPULATOR.setScripts(resources);
        POPULATOR.execute(H2_DATASOURCE);
    }

    @BeforeClass
    public static void setup() {
        executeSQL("h2/init-db-schema.sql", "h2/init-db-data.sql");
    }

    @AfterClass
    public static void reset() {
        executeSQL("h2/reset-db-data.sql");
    }

    /**
     * 占位，不能删，不然报错
     */
    @Test
    public void test(){}

}
