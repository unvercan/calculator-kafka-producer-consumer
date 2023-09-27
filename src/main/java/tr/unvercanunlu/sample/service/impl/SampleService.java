package tr.unvercanunlu.sample.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import tr.unvercanunlu.sample.model.entity.Sample;
import tr.unvercanunlu.sample.repository.ISampleRepository;
import tr.unvercanunlu.sample.service.ISampleService;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SampleService implements ISampleService {

    private final ISampleRepository sampleRepository;

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

}
