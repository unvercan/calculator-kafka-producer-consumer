package tr.unvercanunlu.calculator_kafka_producer_consumer.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.unvercanunlu.calculator_kafka_producer_consumer.kafka.producer.IKafkaProducer;
import tr.unvercanunlu.calculator_kafka_producer_consumer.model.dto.CalculationDto;
import tr.unvercanunlu.calculator_kafka_producer_consumer.model.entity.Calculation;
import tr.unvercanunlu.calculator_kafka_producer_consumer.model.entity.Operation;
import tr.unvercanunlu.calculator_kafka_producer_consumer.model.request.CreateCalculationRequest;
import tr.unvercanunlu.calculator_kafka_producer_consumer.repository.ICalculationRepository;
import tr.unvercanunlu.calculator_kafka_producer_consumer.service.ICalculationService;
import tr.unvercanunlu.calculator_kafka_producer_consumer.service.IOperationService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.BiFunction;

@Service
@RequiredArgsConstructor
public class CalculationService implements ICalculationService {

    private final Logger logger = LoggerFactory.getLogger(CalculationService.class);

    private final IKafkaProducer<UUID, Calculation> calculationKafkaProducer;

    private final ICalculationRepository calculationRepository;

    private final IOperationService operationService;

    private final BiFunction<Calculation, Operation, CalculationDto> calculationToCalculationDtoMapper =
            (calculation, operation) -> CalculationDto.builder()
                    .id(calculation.getId())
                    .first(calculation.getFirst())
                    .second(calculation.getSecond())
                    .result(calculation.getResult())
                    .operation(operation)
                    .done(calculation.getDone())
                    .build();

    @Override
    @Transactional
    public void create(CreateCalculationRequest request) {
        Calculation calculation = Calculation.builder()
                .first(request.getFirst())
                .second(request.getSecond())
                .operationCode(request.getOperationCode())
                .done(Boolean.FALSE)
                .build();

        this.logger.info("Calculation is created.");

        this.logger.debug("Created Calculation: " + calculation);

        calculation = this.calculationRepository.save(calculation);

        this.logger.info("Calculation with '" + calculation.getId() + "' ID is saved to database.");

        this.logger.debug("Saved Calculation: " + calculation);

        this.calculationKafkaProducer.send(calculation.getId(), calculation);

        this.logger.info("Calculation with '" + calculation.getId() + "' ID is sent to Kafka Topic using Kafka Calculation Producer.");
    }

    @Override
    @Transactional(readOnly = true)
    public List<CalculationDto> retrieveAll() {
        List<Calculation> calculationList = this.calculationRepository.findAll();

        this.logger.info("All Calculations are fetched from the database.");

        this.logger.debug("Fetched Calculations: " + calculationList);

        Map<Integer, Operation> operationCache = new HashMap<>();

        List<Integer> operationCodes = calculationList.stream()
                .map(Calculation::getOperationCode)
                .distinct()
                .toList();

        operationCodes.forEach(operationCode -> {
            if (!operationCache.containsKey(operationCode)) {
                Operation operation = this.operationService.retrieve(operationCode);

                operationCache.put(operationCode, operation);
            }
        });

        return calculationList.stream()
                .map(calculation -> this.calculationToCalculationDtoMapper.apply(calculation,
                        operationCache.get(calculation.getOperationCode())))
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public CalculationDto retrieve(UUID calculationId) {
        Calculation calculation = this.calculationRepository.findById(calculationId)
                .orElseThrow(() -> new RuntimeException("Calculation with '" + calculationId + "' ID cannot be found."));

        this.logger.info("Calculation with '" + calculationId + "' ID is fetched from the database.");

        this.logger.debug("Fetched Calculation: " + calculation);

        Operation operation = this.operationService.retrieve(calculation.getOperationCode());

        return this.calculationToCalculationDtoMapper.apply(calculation, operation);
    }

    @Override
    @Transactional
    public void setResult(UUID calculationId, Double result) {
        this.calculationRepository.setResult(calculationId, result);

        this.logger.info("Result of Calculation with '" + calculationId + "' ID is set to " + result + ".");
    }

    @Override
    @Transactional
    public void setCompleteness(UUID calculationId, Boolean done) {
        this.calculationRepository.setCompleteness(calculationId, done);

        this.logger.info("Completeness of Calculation with '" + calculationId + "' ID is set to " + done + ".");
    }
}
