package tr.unvercanunlu.sample.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import tr.unvercanunlu.sample.model.entity.Sample;
import tr.unvercanunlu.sample.model.request.SampleRequest;
import tr.unvercanunlu.sample.repository.ISampleRepository;
import tr.unvercanunlu.sample.service.IRandomService;
import tr.unvercanunlu.sample.service.ISampleService;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class SampleService implements ISampleService {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final ISampleRepository sampleRepository;

    private final IRandomService randomService;

    @Override
    public List<Sample> getAll() {
        return this.sampleRepository.findAll()
                .stream()
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public Sample get(UUID id) {
        return this.sampleRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void delete(UUID id) {
        this.sampleRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));

        this.sampleRepository.deleteById(id);
    }

    @Override
    public Sample add(SampleRequest request) {
        Sample sample = Sample.builder()
                .first(request.getFirst())
                .second(request.getSecond())
                .build();

        sample = this.sampleRepository.save(sample);

        this.logger.log(Level.INFO, String.format("Sample is created. Sample: %s", sample));

        return this.sampleRepository.save(sample);
    }

    @Override
    public List<Sample> randomize(Integer count) {
        List<Sample> sampleList = IntStream.range(0, count)
                .mapToObj(i -> Sample.builder()
                        .first(this.randomService.generate(1, 10))
                        .second(this.randomService.generate(1, 10))
                        .build())
                .toList();

        sampleList = sampleList.stream().map(this.sampleRepository::save).toList();

        sampleList.forEach(sample -> this.logger.log(Level.INFO, String.format("Sample is created. Sample: %s", sample)));

        return sampleList;
    }

}
