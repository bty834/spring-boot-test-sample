package io.github.bty834.domain.service;

import io.github.bty834.domain.model.Sample;
import io.github.bty834.domain.repository.SampleRepository;
import io.github.bty834.domain.util.SampleUtil;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SampleService {

    private final SampleRepository sampleRepository;

    public List<Sample> listSamples(String id){
        return sampleRepository.selectSamples(SampleUtil.getSomething(id));
    }

    public void doSth(){
        System.out.println("do");
    }
}
