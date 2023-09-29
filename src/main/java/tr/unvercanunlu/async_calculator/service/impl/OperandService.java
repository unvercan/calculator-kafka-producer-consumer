package tr.unvercanunlu.async_calculator.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import tr.unvercanunlu.async_calculator.model.entity.Operand;
import tr.unvercanunlu.async_calculator.model.request.OperandRequest;
import tr.unvercanunlu.async_calculator.repository.IOperandRepository;
import tr.unvercanunlu.async_calculator.service.IOperandService;
import tr.unvercanunlu.async_calculator.service.IRandomService;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
public class OperandService implements IOperandService {

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final IOperandRepository operandRepository;

    private final IRandomService randomService;

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
    public void delete(UUID id) {
        this.operandRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));

        this.operandRepository.deleteById(id);
    }

    @Override
    public Operand add(OperandRequest request) {
        Operand operand = Operand.builder()
                .first(request.getFirst())
                .second(request.getSecond())
                .build();

        this.logger.log(Level.INFO, String.format("Operand is created. %s", operand));

        operand = this.operandRepository.save(operand);

        this.logger.log(Level.INFO, String.format("Operand is saved to SQL database. %s", operand));

        return operand;
    }

    @Override
    public List<Operand> randomize(Integer count) {
        List<Operand> operandList = IntStream.range(0, count)
                .mapToObj(i -> {
                    Operand operand = Operand.builder()
                            .first(this.randomService.generate(1, 10))
                            .second(this.randomService.generate(1, 10))
                            .build();

                    this.logger.log(Level.INFO, String.format("Operand is created. %s", operand));

                    return operand;
                }).toList();

        operandList = operandList.stream().map(this.operandRepository::save).toList();

        operandList.forEach(operand -> this.logger.log(Level.INFO, String.format("Operand is saved to SQL Database. %s", operand)));

        return operandList;
    }

}
