package io.github.bty834.domain.repository;

import io.github.bty834.domain.model.Sample;
import java.util.List;

public interface SampleRepository {

    List<Sample> selectSamples(Long id);
}
