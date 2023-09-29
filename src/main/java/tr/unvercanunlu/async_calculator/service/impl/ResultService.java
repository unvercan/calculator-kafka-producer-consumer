package tr.unvercanunlu.async_calculator.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import tr.unvercanunlu.async_calculator.model.entity.Result;
import tr.unvercanunlu.async_calculator.repository.IResultRepository;
import tr.unvercanunlu.async_calculator.service.IResultService;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResultService implements IResultService {

    private final IResultRepository resultRepository;

    @Override
    public List<Result> getAll() {
        return this.resultRepository.findAll()
                .stream()
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public Result get(UUID id) {
        return this.resultRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void delete(UUID id) {
        this.resultRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));

        this.resultRepository.deleteById(id);
    }

}
