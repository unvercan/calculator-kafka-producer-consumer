package tr.unvercanunlu.calculator_kafka.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import tr.unvercanunlu.calculator_kafka.kafka.producer.IKafkaProducer;
import tr.unvercanunlu.calculator_kafka.model.entity.Calculation;
import tr.unvercanunlu.calculator_kafka.model.entity.Operand;
import tr.unvercanunlu.calculator_kafka.model.entity.Operation;
import tr.unvercanunlu.calculator_kafka.model.request.CalculationRequest;
import tr.unvercanunlu.calculator_kafka.repository.ICalculationRepository;
import tr.unvercanunlu.calculator_kafka.service.ICalculationService;
import tr.unvercanunlu.calculator_kafka.service.IOperandService;
import tr.unvercanunlu.calculator_kafka.service.IOperationService;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CalculationService implements ICalculationService {

    private final ICalculationRepository calculationRepository;

    private final IOperandService operandService;

    private final IOperationService operationService;

    private final IKafkaProducer<UUID, Operand> operandKafkaProducer;

    private final IKafkaProducer<UUID, Operation> operationKafkaProducer;

    @Override
    public List<Calculation> getAll() {
        return this.calculationRepository.findAll();
    }

    @Override
    public Calculation get(UUID id) {
        return this.calculationRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @Override
    public void create(CalculationRequest request) {
        Operand operand = Operand.builder()
                .first(request.getFirst())
                .second(request.getSecond())
                .build();

        System.out.println(operand + " is created.");

        operand = this.operandService.create(operand);

        System.out.println(operand + " is saved to database.");

        Calculation calculation = Calculation.builder()
                .operandId(operand.getId())
                .operationCode(request.getOperationCode())
                .completed(Boolean.FALSE)
                .build();

        System.out.println(calculation + " is created.");

        calculation = this.calculationRepository.save(calculation);

        System.out.println(calculation + " is saved to database.");

        Operation operation = this.operationService.get(request.getOperationCode());

        System.out.println(operation + " is fetched from database.");

        System.out.println(operand + " is sending to Kafka.");

        this.operandKafkaProducer.send(calculation.getId(), operand);

        System.out.println(operation + " is sending to Kafka.");

        this.operationKafkaProducer.send(calculation.getId(), operation);

    }

    @Override
    public void update(UUID id, Calculation calculation) {
        this.calculationRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));

        this.calculationRepository.save(calculation);
    }

    /*
    @Override
    public void randomize(Integer count) {

        List<Operand> operandList = IntStream.range(0, count)
                .mapToObj(i -> {
                    Operand operand = Operand.builder()
                            .first(this.randomService.generate(1, 10))
                            .second(this.randomService.generate(1, 10))
                            .build();

                    return operand;
                }).toList();

        operandList = operandList.stream().map(this.operandRepository::save).toList();

        return operandList;
    }
     */

}
