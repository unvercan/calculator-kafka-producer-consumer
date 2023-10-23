package tr.unvercanunlu.calculator_kafka_producer_consumer.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.unvercanunlu.calculator_kafka_producer_consumer.model.entity.Operation;
import tr.unvercanunlu.calculator_kafka_producer_consumer.repository.IOperationRepository;
import tr.unvercanunlu.calculator_kafka_producer_consumer.service.IOperationService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OperationService implements IOperationService {

    private final Logger logger = LoggerFactory.getLogger(OperationService.class);

    private final IOperationRepository operationRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Operation> retrieveAll() {
        List<Operation> operationList = this.operationRepository.findAll();

        this.logger.info("All Operations are fetched from the database.");

        this.logger.debug("Fetched Operations: " + operationList);

        return operationList;
    }

    @Override
    @Transactional(readOnly = true)
    public Operation retrieve(Integer operationCode) {
        Operation operation = this.operationRepository.findById(operationCode)
                .orElseThrow(() -> new RuntimeException("Operation with '" + operationCode + "' Code cannot be found."));

        this.logger.info("Operation with '" + operationCode + "' Code is fetched from the database.");

        this.logger.debug("Fetched Operation: " + operation);

        return operation;
    }
}
