package io.github.bty834.infrastructure.repository;


import io.github.bty834.domain.model.Sample;
import io.github.bty834.domain.repository.SampleRepository;
import io.github.bty834.infrastructure.repository.mapper.SampleMapper;
import io.github.bty834.infrastructure.repository.mapper.po.SamplePO;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
public class SampleRepositoryImpl implements SampleRepository {


    @Autowired
    SampleMapper sampleMapper;


    public List<Sample> selectSamples(Long id) {
        List<SamplePO> samplePOS = sampleMapper.selectById(id);
        if (CollectionUtils.isEmpty(samplePOS)) {
            return new ArrayList<>();
        }
        return samplePOS.stream()
                   .map(p -> {
                       Sample sample = new Sample();
                       sample.setId(p.getId());
                       sample.setUsername(p.getUsername());
                       return sample;
                   })
                   .collect(Collectors.toList());
    }
}
