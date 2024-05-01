package io.github.bty834.controller.dto;

import io.github.bty834.domain.model.Sample;
import lombok.Data;

@Data
public class SampleDTO {

    private Long id;
    private String username;

    public static SampleDTO convert(Sample sample){
        SampleDTO dto = new SampleDTO();
        dto.setId(sample.getId());
        dto.setUsername(sample.getUsername());
        return dto;
    }
}
