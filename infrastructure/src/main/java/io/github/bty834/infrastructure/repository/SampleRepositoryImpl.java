package io.github.bty834.infrastructure.repository;


import io.github.bty834.domain.model.Sample;
import io.github.bty834.domain.repository.SampleRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class SampleRepositoryImpl implements SampleRepository {

    public List<Sample> selectSamples(Long id){
        return new ArrayList<>();
    }
}
