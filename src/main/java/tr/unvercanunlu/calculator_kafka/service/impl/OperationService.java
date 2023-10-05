package tr.unvercanunlu.calculator_kafka.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import tr.unvercanunlu.calculator_kafka.model.entity.Operation;
import tr.unvercanunlu.calculator_kafka.repository.IOperationRepository;
import tr.unvercanunlu.calculator_kafka.service.IOperationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationService implements IOperationService {

    private final IOperationRepository operationRepository;

    @Override
    public List<Operation> getAll() {
        return this.operationRepository.findAll();
    }

    @Override
    public Operation get(Integer code) {
        return this.operationRepository.findById(code)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

}
