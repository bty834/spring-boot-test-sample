package io.github.bty834.controller.api;

import io.github.bty834.controller.converter.SampleConverter;
import io.github.bty834.controller.dto.SampleDTO;
import io.github.bty834.domain.model.Sample;
import io.github.bty834.domain.service.SampleService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SampleController {

    private final SampleService sampleService;

    private final SampleConverter sampleConverter;

    @GetMapping("/sample")
    public ResponseEntity<List<SampleDTO>> getSamples(@RequestParam("id") String id){
        System.out.println(sampleConverter.test());
        List<Sample> samples = sampleService.listSamples(id);
        List<SampleDTO> collect = samples.stream().map(SampleDTO::convert).collect(Collectors.toList());
        return ResponseEntity.of(Optional.of(collect));
    }
}
