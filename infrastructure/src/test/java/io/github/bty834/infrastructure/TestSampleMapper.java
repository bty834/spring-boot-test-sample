package io.github.bty834.infrastructure;

import io.github.bty834.infrastructure.repository.mapper.SampleMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestSampleMapper extends TestWithH2 {


    @Autowired
    SampleMapper sampleMapper;

    @Test
    public void test() {

    }
}
