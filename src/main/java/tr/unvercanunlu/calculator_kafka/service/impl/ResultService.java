package tr.unvercanunlu.calculator_kafka.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import tr.unvercanunlu.calculator_kafka.model.entity.Result;
import tr.unvercanunlu.calculator_kafka.repository.IResultRepository;
import tr.unvercanunlu.calculator_kafka.service.IResultService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ResultService implements IResultService {

    private final IResultRepository resultRepository;

    @Override
    public List<Result> getAll() {
        return this.resultRepository.findAll();
    }

    @Override
    public Result get(UUID id) {
        return this.resultRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Result create(Result result) {
        return this.resultRepository.save(result);
    }

}
