package tr.unvercanunlu.calculator_kafka.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import tr.unvercanunlu.calculator_kafka.model.entity.Operand;
import tr.unvercanunlu.calculator_kafka.repository.IOperandRepository;
import tr.unvercanunlu.calculator_kafka.service.IOperandService;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OperandService implements IOperandService {

    private final IOperandRepository operandRepository;

    @Override
    public List<Operand> getAll() {
        return this.operandRepository.findAll()
                .stream()
                .filter(Objects::nonNull)
                .toList();
    }

    @Override
    public Operand get(UUID id) {
        return this.operandRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @Override
    public Operand create(Operand operand) {
        return this.operandRepository.save(operand);
    }

}
