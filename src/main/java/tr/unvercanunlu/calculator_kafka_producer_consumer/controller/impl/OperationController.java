package tr.unvercanunlu.calculator_kafka_producer_consumer.controller.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tr.unvercanunlu.calculator_kafka_producer_consumer.controller.ApiConfig;
import tr.unvercanunlu.calculator_kafka_producer_consumer.controller.IOperationController;
import tr.unvercanunlu.calculator_kafka_producer_consumer.model.entity.Operation;
import tr.unvercanunlu.calculator_kafka_producer_consumer.service.IOperationService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = ApiConfig.OPERATION_API)
public class OperationController implements IOperationController {

    private final Logger logger = LoggerFactory.getLogger(OperationController.class);

    private final IOperationService operationService;

    @Override
    @GetMapping
    public ResponseEntity<List<Operation>> retrieveAll() {
        this.logger.info("Retrieve ALl Operations Request is received.");

        List<Operation> operationList = this.operationService.retrieveAll();

        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(operationList);
    }

    @Override
    @GetMapping(path = "/{code}")
    public ResponseEntity<Operation> retrieve(@PathVariable(name = "code") Integer operationCode) {
        this.logger.info("Retrieve Operation with '" + operationCode + "' Code is received.");

        Operation operation = this.operationService.retrieve(operationCode);

        return ResponseEntity.status(HttpStatus.OK.value())
                .contentType(MediaType.APPLICATION_JSON)
                .body(operation);
    }
}
