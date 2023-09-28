package tr.unvercanunlu.sample.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import tr.unvercanunlu.sample.model.entity.Sum;
import tr.unvercanunlu.sample.repository.ISumRepository;
import tr.unvercanunlu.sample.service.ISumService;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SumService implements ISumService {

    private final ISumRepository sumRepository;

    @Override
    public List<Sum> getAll() {
        return this.sumRepository.findAll()
                .stream()
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public Sum get(UUID id) {
        return this.sumRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void delete(UUID id) {
        this.sumRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));

        this.sumRepository.deleteById(id);
    }

}
